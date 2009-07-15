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
package org.squale.squalix.tools.rsm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.ErrorBO;
import org.squale.squalix.core.AbstractTask;
import org.squale.squalix.core.TaskData;
import org.squale.squalix.core.TaskException;
import org.squale.squalix.core.exception.ConfigurationException;
import org.squale.squalix.util.buildpath.BuildProjectPath;
import org.squale.squalix.util.csv.CSVParser;
import org.squale.squalix.util.file.FileUtility;
import org.squale.squalix.util.parser.LanguageParser;
import org.squale.squalix.util.process.ProcessErrorHandler;
import org.squale.squalix.util.process.ProcessManager;
import org.squale.squalix.util.process.ProcessOutputHandler;

/**
 */
public abstract class RSMTask
    extends AbstractTask
    implements ProcessErrorHandler, ProcessOutputHandler, CSVParser.CSVHandler
{

    /** Le parser */
    protected CSVParser mParser;

    /**
     * Logger
     */
    protected static final Log LOGGER = LogFactory.getLog( RSMTask.class );

    /**
     * Instance du persisteur RSM
     */
    protected RSMPersistor mPersistor = null;

    /** le parser associé au language */
    protected LanguageParser mLanguageParser;

    /**
     * Configuration de l'outil d'analyse
     */
    protected RSMConfiguration mConfiguration;

    /** Le processManager * */
    protected ProcessManager mProcess;

    /** le fichier contenant les noms des fichiers à analyser */
    protected File mToAnalyseFileList;

    /**
     * Construction des répertoires à analyser
     * 
     * @param pData données de la tâche
     * @param pProjectParams paramètres du projet
     * @throws ConfigurationException si erreur
     * @return la liste des fichiers à analyser
     */
    private List buildFilesToProcess( TaskData pData, MapParameterBO pProjectParams )
        throws ConfigurationException
    {
        // On prend le view path
        String viewPath = (String) pData.getData( TaskData.VIEW_PATH );
        if ( viewPath == null )
        {
            String message = RSMMessages.getString( "exception.variable.not_found", TaskData.VIEW_PATH );
            LOGGER.error( message );
            // Renvoi d'une exception de configuration
            throw new ConfigurationException( message );
        }
        // Pour chaque répertoire source on ajoute celui-ci
        // On récupère les chemins relatifs des répertoires contenant les sources du projet
        ListParameterBO sources = getSourcesDirs( pProjectParams );
        if ( sources == null )
        {
            String message = RSMMessages.getString( "exception.sources.notset", "" );
            LOGGER.error( message );
            // Renvoi d'une exception de configuration
            throw new ConfigurationException( message );
        }
        List sourcesString = new ArrayList( 0 );
        List sourcesStringBO = sources.getParameters();
        for ( int i = 0; i < sourcesStringBO.size(); i++ )
        {
            sourcesString.add( ( (StringParameterBO) ( sourcesStringBO.get( i ) ) ).getValue() );
        }
        // Prise en compte des répertoires exclus
        ListParameterBO excludedDirs = getExcludedDirs( pProjectParams );
        if ( excludedDirs == null )
        {
            // On affecte une liste vide
            excludedDirs = new ListParameterBO();
        }
        // Construction des fichiers à traiter
        List extensions = new ArrayList();
        extensions.addAll( Arrays.asList( mConfiguration.getExtensions() ) );
        extensions.addAll( Arrays.asList( mConfiguration.getHeaders() ) );
        String[] setOfExtensions = (String[]) ( extensions.toArray( new String[extensions.size()] ) );
        List resultSources =
            FileUtility.getIncludedFiles(
                                          viewPath,
                                          BuildProjectPath.buildProjectPath( viewPath, sources.getParameters() ),
                                          (ListParameterBO) mProject.getParameter( ParametersConstants.INCLUDED_PATTERNS ),
                                          (ListParameterBO) mProject.getParameter( ParametersConstants.EXCLUDED_PATTERNS ),
                                          (ListParameterBO) mProject.getParameter( ParametersConstants.EXCLUDED_DIRS ),
                                          setOfExtensions );
        return resultSources;
    }

    /**
     * Obtention des répertories exclus
     * 
     * @param pProjectParams paramètres du projet
     * @return répertoires exclus sous la forme de ListParameterBO(StringParameterBO)
     */
    protected ListParameterBO getExcludedDirs( MapParameterBO pProjectParams )
    {
        return (ListParameterBO) pProjectParams.getParameters().get( ParametersConstants.EXCLUDED_DIRS );
    }

    /**
     * Obtention des sources
     * 
     * @param pProjectParams paramètres du projet
     * @return sources sous la forme de ListParameterBO(StringParameterBO)
     */
    protected ListParameterBO getSourcesDirs( MapParameterBO pProjectParams )
    {
        return (ListParameterBO) pProjectParams.getParameters().get( ParametersConstants.SOURCES );
    }

    /**
     * lance le parsing des classes, la génération du rapport et le parsing du rapport
     * 
     * @throws TaskException en cas d'échec
     */
    public void execute()
        throws TaskException
    {
        try
        {
            // initialise la tache RSM
            initialize();
            // construit la liste des répertoires à analyser (fichiers sources - exclus)
            buildDotLstFile( buildFilesToProcess( getData(), mProject.getParameters() ) );
            LOGGER.info( RSMMessages.getString( "logs.analyzing" ) + mProject.getParent().getName() + " - "
                + mProject.getName() );
            int execResult = parseSource();
            // un nombre négatif est une erreur d'exécution RSM
            if ( execResult < 0 )
            {
                throw new TaskException( RSMMessages.getString( "rsm.exec.erreur",
                                                                new Object[] { new Integer( execResult ) } ) );
            }
            // Si le parsing s'est bien déroulé, on a généré le rapport
            // On parse maintenant le rapport
            // si le parsing ne s'est pas bien déroulé, on va tombre dans l'exception
            // On ne gère pas le code retour car le comportement de RSM est étrange
            parseReport( mConfiguration.getReportPath() );
            // Une fois que tous les rapports ont été générés et parsés,
            // on peut générer les résultats de niveau projet
            mPersistor.persistProjectResult();
            // positionne les données sur la taille du file System
            affectFileSystemSize( mConfiguration.getWorkspace(), true );
            // Lance les opérations de cloture de la tâche
            FileUtility.deleteRecursivelyWithoutDeleteRootDirectory( mConfiguration.getWorkspace() );
        }
        catch ( Exception e )
        {
            throw new TaskException( e );
        }
    }

    /**
     * Construit le fichier .txt recensant l'ensemble des fichiers que RSM doit analyser
     * 
     * @param pFilesToAnalyseList la liste des fichiers à analyser
     * @throws IOException en cas d'échec
     */
    private void buildDotLstFile( List pFilesToAnalyseList )
        throws IOException
    {
        mToAnalyseFileList = new File( mConfiguration.getInputFile() );
        BufferedWriter bw = new BufferedWriter( new FileWriter( mToAnalyseFileList ) );
        for ( Iterator it = pFilesToAnalyseList.iterator(); it.hasNext(); )
        {
            String filePath = (String) it.next();
            bw.write( filePath );
            bw.newLine();
        }
        // ferme le buffer
        bw.close();
    }

    /**
     * @see org.squale.squalix.util.process.ProcessErrorHandler#processError(java.lang.String)
     */
    public void processError( String pErrorMessage )
    {
        LOGGER.error( RSMMessages.getString( "logs.error.tools_error" ) + pErrorMessage );
        ErrorBO error = new ErrorBO();
        error.setInitialMessage( pErrorMessage );
        error.setMessage( RSMMessages.getString( "error.processing" ) );
        error.setLevel( ErrorBO.CRITICITY_FATAL );
        mErrors.add( error );
    }

    /**
     * {@inheritDoc}
     * 
     * @param pOutputLine {@inheritDoc}
     * @see org.squale.squalix.util.process.ProcessOutputHandler#processOutput(java.lang.String)
     */
    public void processOutput( String pOutputLine )
    {
        // TODO Auto-generated method stub

    }

    /**
     * Prépare l'environnement d'exécution de l'analyse :
     * <ul>
     * <li>Création du dossier destination des résultats du parsing</li>
     * <li>Création du fichier config.pcf</li>
     * </ul>
     * 
     * @exception Exception si un probleme d'initialisation apparait
     */
    private void initialize()
        throws Exception
    {
        // On récupère la configuration du module RSM, personnalisée
        // avec les paramètres du projet
        mConfiguration = RSMConfiguration.build( mProject, RSMMessages.getString( "configuration.file" ), getData() );
        File workspace = mConfiguration.getWorkspace();
        if ( !workspace.exists() || !workspace.isDirectory() || !workspace.canWrite() || !workspace.canRead() )
        {
            // On va vérifier que le workspace est disponible
            throw new Exception( RSMMessages.getString( "exception.no_workspace" ) );
        }
        mPersistor = new RSMPersistor( mConfiguration, mAudit, getSession(), getData(), mName, mLanguageParser );
        LOGGER.info( RSMMessages.getString( "logs.initialized" ) + mProject.getParent().getName() + " - "
            + mProject.getName() );
    }

    /**
     * {@inheritDoc}
     * 
     * @param pLine {@inheritDoc}
     * @see org.squale.squalix.util.csv.CSVParser.CSVHandler#processLine(java.util.List)
     */
    public void processLine( List pLine )
    {
        // TODO Auto-generated method stub

    }

    /**
     * Crée le ProcessManager. On ne fait pas de new mais un set pour implémenter le pattern IOC pour pouvoir tester sur
     * un environnement windows
     * 
     * @param pArguments arguments
     * @param pDirectory répertoire de lancement
     * @return le ProcessManager normal
     */
    private ProcessManager createProcessManager( String[] pArguments, File pDirectory )
    {
        return new ProcessManager( pArguments, null, pDirectory );
    }

    /**
     * Parse les fichiers sources afin d'en extraire les métriques.
     * 
     * @return le résultat de l'exécution
     * @throws Exception si un problème de parsing apparaît.
     */
    private int parseSource()
        throws Exception
    {
        // le résultat renvoyé
        int resultParse = 0;
        // Execute le parsing des sources avec RSM
        String[] parseCommand = new String[mConfiguration.getParseParameters().length + 1];
        parseCommand[0] = mConfiguration.getExecCommand();
        // Parse tous les répertoires sources sauf les répertoires exclus un par un
        for ( int i = 1; i < parseCommand.length; i++ )
        {
            String param = mConfiguration.getParseParameters()[i - 1];
            // On construit la commande
            // On met en forme la liste des paramètres pour la passer au process
            if ( "-O".equals( param ) )
            {
                param += mConfiguration.getReportPath();
            }
            else
            {
                if ( "-F".equals( param ) )
                {
                    param += mConfiguration.getInputFile();
                }
            }
            parseCommand[i] = param;
        }
        LOGGER.info( RSMMessages.getString( "logs.running_parsing.command", parseCommand ) );
        LOGGER.info( RSMMessages.getString( "logs.running_parsing", new Object[] { mProject.getParent().getName(),
            mProject.getName() } ) );
        mProcess = createProcessManager( parseCommand, mConfiguration.getWorkspace() );
        // On veut gérer les informations lancées par le processus en sortie
        mProcess.setOutputHandler( this );
        // On cherche à avoir un comportement synchrone pour être sûr de ne pas
        // avoir un état des données incohérent
        resultParse = mProcess.startProcess( this );

        LOGGER.info( RSMMessages.getString( "logs.return_parsing", new Object[] { mProject.getParent().getName(),
            mProject.getName(), new Integer( resultParse ) } ) );
        return resultParse;
    }

    /**
     * /** Analyse un rapport de métriques RSM
     * 
     * @param pReport rapport à parser
     * @throws Exception si erreur
     */
    private void parseReport( final String pReport )
        throws Exception
    {
        mPersistor.parseReport( pReport, getData() );
    }

    /**
     * @return la configuration
     */
    public RSMConfiguration getConfiguration()
    {
        return mConfiguration;
    }

    /**
     * @param pConfiguration la configuration
     */
    public void setConfiguration( RSMConfiguration pConfiguration )
    {
        mConfiguration = pConfiguration;
    }

}