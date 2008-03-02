package com.airfrance.squalix.tools.compiling.java;

import java.io.File;
import java.io.FileInputStream;

import com.airfrance.squalix.core.AbstractTask;
import com.airfrance.squalix.core.TaskException;
import com.airfrance.squalix.tools.compiling.java.configuration.EclipseCompilingConfiguration;
import com.airfrance.squalix.tools.compiling.java.configuration.JCompilingConfiguration;
import com.airfrance.squalix.util.file.FileUtility;

/**
 * Permet de supprimer les répertoires crées lors de la tâche de compilation
 */
public class JCompilingCleanerTask
    extends AbstractTask
{

    /**
     * Constructeur par défaut.
     */
    public JCompilingCleanerTask()
    {
        mName = "JCompilingCleanerTask";
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
            JCompilingConfiguration jCompilingConf = new JCompilingConfiguration();
            // On supprime le répertoire des plugins eclipse
            FileUtility.deleteRecursively( new File( jCompilingConf.getEclipseBundleDir() ) );
            // On supprime le répertoire des librairies exportées
            FileUtility.deleteRecursively( jCompilingConf.getExportedLibsDir() );
            // On supprime le workspace
            EclipseCompilingConfiguration conf = new EclipseCompilingConfiguration();
            conf.parse( new FileInputStream( new File( "config/eclipsecompiling-config.xml" ) ) );
            FileUtility.deleteRecursively( new File( conf.getWorkspace() ) );
            FileUtility.deleteRecursively( conf.getEclipseHome() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

}
