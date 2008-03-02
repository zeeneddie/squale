package com.airfrance.squalix.tools.compiling;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalix.core.AbstractTask;
import com.airfrance.squalix.core.TaskException;
import com.airfrance.squalix.core.exception.ConfigurationException;

/**
 * Tâche de compilation dans le cas d'un projet déjà compilé Cette tâche sert juste à mettre à jour les paramètres
 * temporaires liés à la tâche de compilation
 */
public abstract class MockCompilingTask
    extends AbstractTask
{
    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( MockCompilingTask.class );

    /** Les paramètres liés à la tâche */
    protected MapParameterBO mTaskParam;

    // Cette tache n'a pas d'influence dans le calcul de la taille max du file system

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
            mTaskParam = (MapParameterBO) mProject.getParameter( ParametersConstants.COMPILED );
            if ( mTaskParam == null )
            {
                String message = CompilingMessages.getString( "exception.project.not_configured" );
                LOGGER.error( message );
                // Renvoi d'une exception de configuration
                throw new ConfigurationException( message );
            }
            // Les paramètres doivent contenir les chemins vers les sources compilées
            ListParameterBO compiledSourcesParam =
                (ListParameterBO) mTaskParam.getParameters().get( ParametersConstants.COMPILED_SOURCES_DIRS );
            if ( compiledSourcesParam == null )
            {
                String message = CompilingMessages.getString( "exception.compiled_sources_dirs_not_set" );
                LOGGER.error( message );
                // Renvoi d'une exception de configuration
                throw new ConfigurationException( message );
            }
            List compiledSourcesDirs = compiledSourcesParam.getParameters();
            setCompiledDirInTempMap( compiledSourcesDirs );
            // positionne les données sur la taille du file System
            affectFileSystemSize( mProject.getParameter( ParametersConstants.SOURCES ), true );
        }
        catch ( Exception e )
        {
            throw new TaskException( e );
        }
    }

    /**
     * Positionne la variable donnant le chemin du répertoire racine contenant les fichiers compilés du projet.
     * 
     * @param pDirs la liste de StringParameterBO représentant les répertoires ou fichiers compressés contenant les
     *            fichiers compilés
     * @throws TaskException si erreur
     */
    protected abstract void setCompiledDirInTempMap( List pDirs )
        throws TaskException;

}
