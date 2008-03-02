package com.airfrance.squalix.tools.sourcecodeanalyser;

import java.io.File;
import java.io.FileInputStream;

import com.airfrance.squalix.core.AbstractTask;
import com.airfrance.squalix.core.TaskException;
import com.airfrance.squalix.util.file.FileUtility;

/**
 * Supprime le répertoire crée par la tâche d'analyse du code source
 */
public class SourceCodeAnalyserCleanerTask
    extends AbstractTask
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
        catch ( Exception e )
        {
            throw new TaskException( e );
        }

    }

}
