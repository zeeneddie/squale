package com.airfrance.squalix.tools.scm.task;

import org.apache.maven.scm.manager.BasicScmManager;
import org.apache.maven.scm.provider.local.LocalScmProvider;

/**
 * Connection to a local repository
 */
public class RepositoryLocal
    extends AbstractRepository
{

    /** Constructor by default */
    public RepositoryLocal()
    {
        this.setScmManager( new BasicScmManager() );
        this.getScmManager().setScmProvider( "local", new LocalScmProvider() );
    }

    /**
     * Constructor
     * 
     * @param pPath path to data to check out
     * @param pTemporaryDirectory local directory where check out are performed
     * @param pLocation location (module or directory) to check out
     * @param pLogin user profile to connect to the repository
     * @param pPassword user profile's password to access the repository
     */
    public RepositoryLocal( String pPath, String pTemporaryDirectory, String pLocation, String pLogin, String pPassword )
    {
        this.setScmManager( new BasicScmManager() );
        this.getScmManager().setScmProvider( "local", new LocalScmProvider() );
        this.setScmTemporaryDirectory( pTemporaryDirectory );
        this.setLocation( pLocation );
        this.setLogin( pLogin );
        this.setPassword( pPassword );

    }
}
