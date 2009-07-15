/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.squale.squalix.tools.mccabe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import org.squale.squalix.core.TaskData;
import org.squale.squalix.core.TaskException;
import org.squale.squalix.core.exception.ConfigurationException;
import org.squale.squalix.util.buildpath.BuildProjectPath;
import org.squale.squalix.util.csv.CSVParser;
import org.squale.squalix.util.file.FileUtility;
import org.squale.squalix.util.parser.CppParser;
import org.squale.squalix.util.process.ProcessManager;

/**
 * Tâche McCabe pour les projets C++ Cette tâche nécessite des ajustements pour l'analyse du code C++. Les fichiers
 * source doivent être préprocessés pour générer des fichiers .i. Un fichier myheader.dat doit être créé pour y
 * référencer l'ensemble des fichiers d'include à prendre en compte
 */
public class CppMcCabeTask
    extends OOMcCabeTask
    implements CSVParser.CSVHandler
{

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( AbstractMcCabeTask.class );

    /**
     * Constructeur
     */
    public CppMcCabeTask()
    {
        mName = "CppMcCabeTask";
    }

    /**
     * {@inheritDoc} On doit utiliser un parser C++
     * 
     * @see org.squale.squalix.tools.mccabe.AbstractMcCabeTask#setParser()
     */
    public void setParser()
    {
        mParser = new CppParser( mProject );
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.squale.squalix.tools.mccabe.AbstractMcCabeTask#setClassTemplate()
     */
    public void setClassTemplate()
    {
        mClassTemplate = "csv.template.class";
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.squale.squalix.core.Task#execute()
     */
    public void execute()
        throws TaskException
    {
        // On passe la phase de compilation avant de lancer
        // McCabe
        doCompilation();
        super.execute();
    }

    /**
     * Cette méthode réalise la compilation d'un projet C++.
     * 
     * @throws TaskException exception lors de la compilation.
     */
    protected void doCompilation()
        throws TaskException
    {
        try
        {
            /* si le fichier de script est conforme */
            File scriptFile = getCompilationScriptFile();
            if ( !scriptFile.exists() )
            {
                /* on lance une exception */
                throw new Exception( McCabeMessages.getString( "cpp.exception.task.scriptfile_not_found",
                                                               scriptFile.toString() ) );
            }
            LOGGER.info( McCabeMessages.getString( "logs.cpp.compile", scriptFile.getAbsolutePath() ) );
            String viewPath = (String) getData().getData( TaskData.VIEW_PATH );
            /* Lancement du process */
            ProcessManager mgr =
                new ProcessManager( new String[] { scriptFile.getAbsolutePath(), viewPath }, null,
                                    scriptFile.getParentFile() );
            mgr.setOutputHandler( this );
            int result = mgr.startProcess( this );

            /* si le process se termine correctement */
            if ( 0 != result )
            {
                throw new TaskException( McCabeMessages.getString( "cpp.logs.task.not_compiled" ) );
            }
        }
        catch ( Exception e )
        {
            throw new TaskException( e );
        }
    }

    /**
     * Obtention du nom du script de compilation
     * 
     * @return script de compilation
     * @throws ConfigurationException si erreur
     */
    private File getCompilationScriptFile()
        throws ConfigurationException
    {
        File result;
        // On prend la valeur stockée dans le projet
        MapParameterBO cppParams = (MapParameterBO) mProject.getParameter( ParametersConstants.CPP );
        if ( cppParams == null )
        {
            // Renvoi d'une exception de configuration
            throw new ConfigurationException( McCabeMessages.getString( "cpp.exception.variable.not_found",
                                                                        ParametersConstants.CPP ) );
        }
        else
        {
            StringParameterBO cppScript =
                (StringParameterBO) cppParams.getParameters().get( ParametersConstants.CPP_SCRIPTFILE );
            // Renvoi d'une exception de configuration
            if ( cppScript == null )
            {
                // Renvoi d'une exception de configuration
                throw new ConfigurationException( McCabeMessages.getString( "cpp.exception.variable.not_found",
                                                                            ParametersConstants.CPP_SCRIPTFILE ) );
            }
            else
            {
                File scriptFile = new File( cppScript.getValue() );
                // Si le script a un nom absolue et existe, on prend celui-ci
                if ( scriptFile.isAbsolute() && scriptFile.exists() )
                {
                    result = scriptFile;
                }
                else
                {
                    // Le script est supposé être relatif à la vue
                    String viewPath = (String) getData().getData( TaskData.VIEW_PATH );
                    if ( viewPath == null )
                    {
                        // Renvoi d'une exception de configuration
                        throw new ConfigurationException( McCabeMessages.getString( "cpp.exception.variable.not_found",
                                                                                    TaskData.VIEW_PATH ) );
                    }
                    result = new File( viewPath, scriptFile.getPath() );
                }
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.squale.squalix.tools.mccabe.AbstractMcCabeTask#createProjectConfigurationFile(org.squale.squalix.tools.mccabe.McCabePCFFile)
     */
    protected void createProjectConfigurationFile( McCabePCFFile pFile )
        throws Exception
    {
        // Création du fichier
        super.createProjectConfigurationFile( pFile );
        // Création du fichier avec les headers à prendre en compte
        File headerFile = new File( pFile.getPcfFile().getParentFile(), "myheader.dat" );
        LOGGER.info( McCabeMessages.getString( "logs.cpp.header", headerFile ) );
        Collection headerFiles = getHeaderFiles();
        BufferedWriter buf = new BufferedWriter( new FileWriter( headerFile ) );
        Iterator it = headerFiles.iterator();
        while ( it.hasNext() )
        {
            buf.write( (String) it.next() );
            buf.newLine();
        }
        buf.close();
    }

    /**
     * Obtention des fichiers headers
     * 
     * @return liste des fichiers header
     */
    protected Collection getHeaderFiles()
    {
        // Construction de la liste des fichiers .h non exclus
        // On parcourt chaque répertoire source sous la vue avec
        // l'extension requise
        File root = new File( (String) getData().getData( TaskData.VIEW_PATH ) );
        List srcs =
            ( (ListParameterBO) getProject().getParameters().getParameters().get( ParametersConstants.SOURCES ) ).getParameters();
        List paths = BuildProjectPath.buildProjectPath( (String) getData().getData( TaskData.VIEW_PATH ), srcs );
        HashSet filesList = new HashSet();
        // Parcours de chaque répertoire source
        for ( int i = 0; i < paths.size(); i++ )
        {
            McCabeFileFilter filter = new McCabeFileFilter( root.getAbsolutePath(), mConfiguration.getEntetes() );
            HashSet fileList = new HashSet();
            File pDirectory = new File( (String) paths.get( i ) );
            FileUtility.createRecursiveListOfFiles( pDirectory, filter, fileList );
            Iterator it = fileList.iterator();
            String filename = null;
            int rootLength = root.getAbsolutePath().length() + File.separator.length();
            // On ne retient que le nom du fichier
            while ( it.hasNext() )
            {
                filename = new File( (String) it.next() ).getName();
                filesList.add( filename );
            }
        }
        return filesList;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.squale.squalix.tools.mccabe.AbstractMcCabeTask#createReport(java.lang.String)
     */
    protected void createReport( String pReport )
        throws Exception
    {
        // Génération du rapport
        super.createReport( pReport );
        // Dans le cas d'un rapport de type classe
        // on va lire ce rapport pour extraire les noms de classe
        if ( pReport.startsWith( McCabeMessages.getString( "reports.profile.class" ) ) )
        {
            String fileName = computeReportFileName( pReport );
            LOGGER.info( McCabeMessages.getString( "logs.cpp.class.preprocess", fileName ) );
            // Lecture du contenu de ce fichier pour en extraire les noms de
            // classes et les stocker dans le CppParser
            CSVParser parser = new CSVParser( McCabeMessages.getString( "csv.config.file" ) );
            parser.parseLines( McCabeMessages.getString( mClassTemplate ), fileName, this );
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.squale.squalix.util.csv.CSVParser.CSVHandler#processLine(java.util.ArrayList)
     */
    public void processLine( List pLine )
    {
        ( (CppParser) mParser ).addKnownClass( (String) pLine.get( 0 ) );
    }

}