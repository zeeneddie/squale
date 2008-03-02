package com.airfrance.squalix.tools.sourcecodeanalyser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import com.airfrance.squalix.core.AbstractTask;
import com.airfrance.squalix.core.TaskData;
import com.airfrance.squalix.core.TaskException;
import com.airfrance.squalix.core.exception.ConfigurationException;
import com.airfrance.squalix.util.file.FileUtility;

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
     * @see com.airfrance.squalix.core.AbstractTask#execute()
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
            modifyViewPathInTempMap( new File( path.getValue() ) );

            // positionne les données sur la taille du file System
            affectFileSystemSize( mConfiguration.getRootDirectory(), true );
        }
        catch ( Exception e )
        {
            throw new TaskException( e );
        }
    }

    /**
     * Modifie le paramètre <code>view_path</code> des paramètres temporaires. Extrait si besoin le fichier compressé
     * 
     * @param pFile le fichier vers l'arborescence du projets
     * @throws Exception si erreur
     */
    private void modifyViewPathInTempMap( File pFile )
        throws Exception
    {
        // On va extraire ou copier les informations dans le répertoire défini
        // dans le fichier XML de la configuration
        mConfiguration.parse( new FileInputStream( "config/sourcecodeanalyser-config.xml" ) );
        // On crée ce répertoire
        File dest = new File( mConfiguration.getRootDirectory() );
        // On affecte la valeur au view_path en rajoutant un "/" en bout en cas
        getData().putData( TaskData.VIEW_PATH, dest.getAbsolutePath() + "/" );
        FileUtility.copyOrExtractInto( pFile, dest );
        if ( dest.listFiles().length == 0 )
        {
            // si le répertoire de destination est vide, on lance une exception
            String message = SourceCodeAnalyserMessages.getString( "exception.empty_dir", pFile.getAbsoluteFile() );
            throw new IOException( message );
        }
    }
}
