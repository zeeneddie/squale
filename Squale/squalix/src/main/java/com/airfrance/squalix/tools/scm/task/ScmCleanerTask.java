package com.airfrance.squalix.tools.scm.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.airfrance.squalix.core.AbstractSourceTerminationTask;
import com.airfrance.squalix.core.TaskException;
import com.airfrance.squalix.core.exception.ConfigurationException;
import com.airfrance.squalix.util.file.FileUtility;

/**
 * Suppress the directory create by the source code recovering task 
 */
public class ScmCleanerTask
    extends AbstractSourceTerminationTask
{

    /**
     * Default constructor
     */
    public ScmCleanerTask()
    {
        mName = "ScmCleanerTask";
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
            // Recovering of the task configuration
            ScmConfig conf = new ScmConfig();
            conf.parse( new FileInputStream( "config/scm-config.xml" ) );
            // Removal of the directory
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
        boolean doOptimisation = false;
        if ( ScmMessages.getString( "properties.task.optimization" ).equals("true") )
        {
            doOptimisation = true;
        }
        return doOptimisation;
    }

}
