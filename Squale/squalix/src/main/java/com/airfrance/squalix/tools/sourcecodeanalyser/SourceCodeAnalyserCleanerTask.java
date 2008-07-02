package com.airfrance.squalix.tools.sourcecodeanalyser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.airfrance.squalix.core.AbstractSourceTerminationTask;
import com.airfrance.squalix.core.TaskException;
import com.airfrance.squalix.core.exception.ConfigurationException;
import com.airfrance.squalix.util.file.FileUtility;

/**
 * Supprime le répertoire crée par la tâche d'analyse du code source
 */
public class SourceCodeAnalyserCleanerTask
    extends AbstractSourceTerminationTask
{

    /**
     * Constructeur
     */
    public SourceCodeAnalyserCleanerTask()
    {
        mName = "SourceCodeAnalyserCleanerTask";
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
         // On récupère la configuration de la tâche
            SourceCodeAnalyserConfig conf = new SourceCodeAnalyserConfig();
            conf.parse( new FileInputStream( "config/sourcecodeanalyser-config.xml" ) );
         // On supprime le répertoire
            File dir = new File( conf.getRootDirectory() );
            FileUtility.deleteRecursively( dir );
        }
        catch ( FileNotFoundException e )
        {
            throw new TaskException (e);
        }
        catch ( ConfigurationException e )
        {
            throw new TaskException (e);
        }
            
        
        

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean doOptimization()
    {
        boolean doOptimization = false;
        if ( SourceCodeAnalyserMessages.getString( "properties.task.optimization" ).equals( "true" ) )
        {
            doOptimization = true;
        }
        return doOptimization;
    }

}
