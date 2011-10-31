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
package org.squale.squalix.tools.sourcecodeanalyser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import org.squale.squalix.core.AbstractTask;
import org.squale.squalix.core.TaskData;
import org.squale.squalix.core.TaskException;
import org.squale.squalix.core.exception.ConfigurationException;
import org.squale.squalix.util.file.FileUtility;
import org.squale.squalix.util.sourcesrecovering.SourcesRecoveringOptimisation;

/**
 * Analyseur de code source disponible sous forme d'arborescence de fichiers
 */
public class SourceCodeAnalyserTask
    extends AbstractTask
{
    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( SourceCodeAnalyserTask.class );

    /**
     * La configuration
     */
    private SourceCodeAnalyserConfig mConfiguration;

    /**
     * Constructeur
     */
    public SourceCodeAnalyserTask()
    {
        mName = "SourceCodeAnalyserTask";
        mConfiguration = new SourceCodeAnalyserConfig();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.squale.squalix.core.AbstractTask#execute()
     */
    public void execute()
        throws TaskException
    {
        try
        {
            // On récupère les paramètres liés à la tâche
            MapParameterBO taskParam = (MapParameterBO) mProject.getParameter( ParametersConstants.ANALYSER );
            if ( taskParam == null )
            {
                String message = SourceCodeAnalyserMessages.getString( "exception.project.not_configured" );
                LOGGER.error( message );
                // Renvoi d'une exception de configuration
                throw new ConfigurationException( message );
            }
            // On récupère le chemin absolu vers l'arborescence du projet
            StringParameterBO path = (StringParameterBO) taskParam.getParameters().get( ParametersConstants.PATH );
            if ( path == null )
            {
                String message = SourceCodeAnalyserMessages.getString( "exception.path.not_found" );
                LOGGER.error( message );
                // Renvoi d'une exception de configuration
                throw new ConfigurationException( message );
            }
            // On met à jour le view_path des paramètres temporaires
            modifyViewPathInTempMap( path );

            // positionne les données sur la taille du file System
            affectFileSystemSize( mConfiguration.getRootDirectory(), true );
        }
        catch ( Exception e )
        {
            throw new TaskException( e );
        }
    }

    /**
     * Modify the parameter <code>view_path</code> of the temporary parameter. Do the recovering of the source code,
     * and uncompress if it necessary
     * 
     * @param path the parameter which contains the path to the source code
     * @throws Exception happened during during the recovering of the source code
     */
    private void modifyViewPathInTempMap( StringParameterBO path )
        throws Exception
    {
        // we extract or copy the information into the destination directory
        // First we parse the xml configuration file in order to recover the destination directory
        mConfiguration.parse( new FileInputStream( "config/sourcecodeanalyser-config.xml" ) );
        // we create the directory
        File dest = new File( mConfiguration.getRootDirectory() );
        // we associate this destination directory to the VIEW_PATH variable
        getData().putData( TaskData.VIEW_PATH, dest.getAbsolutePath() + File.separator );

     // We create the file which is supposed to contain the source code
        File sourcePath = new File( path.getValue() );
        
        getData().putData( TaskData.SUP_PATH, sourcePath.isDirectory()? sourcePath.getAbsoluteFile().getName() : "");

        // Does this source code already recovered ?
        if ( !SourcesRecoveringOptimisation.pathAlreadyRecovered( path.getValue(), mApplication ) )
        {
            // recovering of the source code
            FileUtility.copyOrExtractInto( sourcePath, dest );
            if ( dest.listFiles().length == 0 )
            {
                // If the destination directory is empty, then we launch an exception
                String message =
                    SourceCodeAnalyserMessages.getString( "exception.empty_dir", sourcePath.getAbsoluteFile() );
                throw new IOException( message );
            }
            // We had this path to the source code to the list of path already recovered
            if ( SourceCodeAnalyserMessages.getString( "properties.task.optimization" ).equals( "true" ) )
            {
                SourcesRecoveringOptimisation.addToPathRecovered( path.getValue(), mApplication );
            }

        }
        else
        {
            // if the recovering of this source code has already been done, then we indicate this in the log
            LOGGER.info( SourceCodeAnalyserMessages.getString( "logs.task.optimization", path.getValue() ) );
        }

    }
}
