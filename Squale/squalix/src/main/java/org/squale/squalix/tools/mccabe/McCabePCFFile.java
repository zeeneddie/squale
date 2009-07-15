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
//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\org\\squale\\squalix\\tools\\mccabe\\McCabePCFFile.java

package org.squale.squalix.tools.mccabe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import org.squale.squalix.core.TaskData;
import org.squale.squalix.core.exception.ConfigurationException;
import org.squale.squalix.util.buildpath.BuildProjectPath;
import org.squale.squalix.util.file.FileUtility;

/**
 * Crée un fichier config.pcf à partir de la configuration de l'application, et le place dans l'espace de travail
 * McCabe.
 * 
 * @author m400842
 * @version 1.0
 */
public class McCabePCFFile
{

    /**
     * Chemins des fichiers à auditer
     */
    private HashSet mFilesList = new HashSet();

    /**
     * Framework configuration
     */
    private McCabeConfiguration mConfig = null;

    /**
     * A l'initialisation, contient le dossier du projet dans l'espace local, puis juste avant la génération du fichier
     * pcf, contient le dossier parent commun le plus bas des fichiers à analyser.
     */
    private File mRoot = null;

    /**
     * Fichier PCF
     */
    private File mPcfFile = null;

    /**
     * Nom du projet
     */
    private String mProjectName = null;

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( McCabePCFFile.class );

    /**
     * Constructeur du fichier de configuration d'un projet McCabe
     * 
     * @param pConfig configuration de l'exécution
     * @param pDatas la liste des paramètres temporaires du projet
     * @throws Exception si un problème de création apparait.
     * @roseuid 429484FC0089
     */
    public McCabePCFFile( final McCabeConfiguration pConfig, final TaskData pDatas )
        throws Exception
    {
        mConfig = pConfig;
        if ( !mConfig.getSubWorkspace().exists() )
        {
            mConfig.getSubWorkspace().mkdirs();
        }
        // Un nouveau fichier est créé dans le workspace dédié au projet
        mPcfFile =
            new File( mConfig.getSubWorkspace().getAbsolutePath() + File.separator
                + McCabeMessages.getString( "pcf_name" ) );
        LOGGER.debug( McCabeMessages.getString( "logs.debug.building_file" ) + mPcfFile.getAbsolutePath() );
        mProjectName = mConfig.getProject().getName();
        mRoot = new File( (String) pDatas.getData( TaskData.VIEW_PATH ) );
        // On récupère les chemins vers les sources
        ListParameterBO sources =
            (ListParameterBO) pConfig.getProject().getParameters().getParameters().get( ParametersConstants.SOURCES );
        if ( null == sources )
        {
            throw new ConfigurationException( McCabeMessages.getString( "exception.sources.notset" ) );
        }
        List srcs = sources.getParameters();
        String viewPath = (String) pDatas.getData( TaskData.VIEW_PATH );

        // On ajoute les sources pour l'analyse
        List absSources = BuildProjectPath.buildProjectPath( viewPath, srcs );

        // Obtention des jsps si il y en a
        ListParameterBO jspsParam = (ListParameterBO) pConfig.getProject().getParameter( ParametersConstants.JSP );

        // On prend en compte les inclusions et exclusions sauf dans le cas du C++
        if ( pConfig.getProject().getParameter( ParametersConstants.CPP ) != null )
        {
            // On ajoute les sources pour l'analyse
            addFilesListParam( srcs, viewPath );
        }
        else
        {
            ListParameterBO included =
                (ListParameterBO) pConfig.getProject().getParameters().getParameters().get(
                                                                                            ParametersConstants.INCLUDED_PATTERNS );
            ListParameterBO excluded =
                (ListParameterBO) pConfig.getProject().getParameters().getParameters().get(
                                                                                            ParametersConstants.EXCLUDED_PATTERNS );
            ListParameterBO excludedDirs =
                (ListParameterBO) pConfig.getProject().getParameters().getParameters().get(
                                                                                            ParametersConstants.EXCLUDED_DIRS );
            ListParameterBO allExcludedDirs = new ListParameterBO();
            if ( null != jspsParam )
            {
                // On ajoute les jsps à la liste des fichiers sources
                List absJsps = BuildProjectPath.buildProjectPath( viewPath, jspsParam.getParameters() );
                absSources.addAll( absJsps );
                // On ajoute les répertoires des jsps exclus de la compilation car il ne faut pas les analyser
                ListParameterBO excludedJspDirs =
                    (ListParameterBO) pConfig.getProject().getParameters().getParameters().get(
                                                                                                ParametersConstants.JSP_EXCLUDED_DIRS );
                if ( null != excludedDirs )
                {
                    allExcludedDirs.getParameters().addAll( excludedDirs.getParameters() );
                }
                if ( null != excludedJspDirs )
                {
                    allExcludedDirs.getParameters().addAll( excludedJspDirs.getParameters() );
                }
            }
            List includedFileNames =
                FileUtility.getIncludedFiles( viewPath, absSources, included, excluded, allExcludedDirs,
                                              mConfig.getExtensions() );

            setFilesList( pConfig, includedFileNames );
        }
    }

    /**
     * Ajoute les fichiers à analyser
     * 
     * @param srcs la liste des sources à ajouter
     * @param viewPath le chemin de la vue
     */
    private void addFilesListParam( List srcs, String viewPath )
    {
        List paths = BuildProjectPath.buildProjectPath( viewPath, srcs );
        // Parcours de chaque répertoire source
        for ( int i = 0; i < paths.size(); i++ )
        {
            setFilesList( new File( (String) paths.get( i ) ) );
        }
    }

    /**
     * Crée la liste des fichiers à intégrer à l'analyse.
     * 
     * @param pConfig la configuration McCabe
     * @param pFiles les fichiers inclus avec les chemins relatifs
     */
    private void setFilesList( final McCabeConfiguration pConfig, List pFiles )
    {
        LOGGER.debug( McCabeMessages.getString( "logs.debug.listing_files" ) + mRoot.getAbsolutePath() );
        // Parcours de chaque fichier
        Iterator it = pFiles.iterator();
        List includes = new ArrayList();
        int rootLength = mRoot.getAbsolutePath().length() + File.separator.length();
        String filename = null;
        while ( it.hasNext() )
        {
            filename = (String) it.next();
            mFilesList.add( filename.substring( rootLength ) );
        }
    }

    /**
     * Crée la liste des fichiers à intégrer à l'analyse.
     * 
     * @param pDirectory répertoire
     */
    private void setFilesList( File pDirectory )
    {
        LOGGER.debug( McCabeMessages.getString( "logs.debug.listing_files" ) + mRoot.getAbsolutePath() );
        // Détermine la liste des fichiers à analyser
        McCabeFileFilter filter = new McCabeFileFilter( mRoot.getAbsolutePath(), mConfig.getExtensions() );
        HashSet fileList = new HashSet();
        FileUtility.createRecursiveListOfFiles( pDirectory, filter, fileList );
        Iterator it = fileList.iterator();

        String filename = null;
        int rootLength = mRoot.getAbsolutePath().length() + File.separator.length();
        while ( it.hasNext() )
        {
            filename = (String) it.next();
            mFilesList.add( filename.substring( rootLength ) );
        }
    }

    /**
     * Crée le fichier pcf d'analyse du projet.
     * 
     * @throws Exception si un probleme d'écriture survient.
     * @roseuid 42B7CAEB0048
     */
    public void build()
        throws Exception
    {
        LOGGER.debug( McCabeMessages.getString( "logs.debug.pcf_creation" ) + mPcfFile.getAbsolutePath() );
        BufferedWriter writer = new BufferedWriter( new FileWriter( mPcfFile ) );
        // Ecriture de l'en-tete
        writer.write( McCabeMessages.getString( "pcf.header.program" ) + mProjectName.replaceAll( " ", "_" ) );
        writer.newLine();
        writer.write( McCabeMessages.getString( "pcf.header.dir" ) + mRoot.getAbsolutePath() );
        writer.newLine();
        writer.write( McCabeMessages.getString( "pcf.header.misc1" ) );
        writer.newLine();
        writer.write( McCabeMessages.getString( "pcf.header.misc2" ) );
        writer.newLine();
        //Récupération du niveau de métrique et insertion s'il existe
        String lMetrics_level = mConfig.getMetrics_level();
        if ( lMetrics_level != null )
        {
            writer.write( "METRICS_LEVEL " + lMetrics_level );
            writer.newLine();
        }
       
        // Ecriture de la liste des fichiers avec les paramètres
        Iterator it = mFilesList.iterator();
        while ( it.hasNext() )
        {
            writer.write( getLine( (String) it.next() ) );
            writer.newLine();
        }
        writer.flush();
        writer.close();
    }

    /**
     * Access method for the mPcfFile property.
     * 
     * @return the current value of the mPcfFile property
     * @roseuid 42D3C693004A
     */
    public File getPcfFile()
    {
        return mPcfFile;
    }

    /**
     * Crée la ligne paramétrée pour parser le fichier.
     * 
     * @param pFilename le nom du fichier à ajouter.
     * @return la ligne à afficher.
     * @roseuid 42D3E25A0316
     */
    private String getLine( final String pFilename )
    {
        StringBuffer sb = new StringBuffer( mConfig.getParser() );
        sb.append( " \"" + pFilename + "\" " );
        // On ajoute la liste des paramètres
        for ( int i = 0; i < mConfig.getParseParameters().length; i++ )
        {
            sb.append( mConfig.getParseParameters()[i] + " " );
        }
        return sb.toString();
    }

}
