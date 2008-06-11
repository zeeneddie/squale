package com.airfrance.squalix.tools.scm.task;

import org.apache.maven.scm.manager.BasicScmManager;
import org.apache.maven.scm.provider.svn.svnexe.SvnExeScmProvider;

/**
 * Connection to a Svn repository
 */
public class RepositorySvn
    extends AbstractRepository
{

    /**
     * Constructor by default
     */
    public RepositorySvn()
    {
        this.setScmManager( new BasicScmManager() );
        this.getScmManager().setScmProvider( "svn", new SvnExeScmProvider() );
    }

    /**
     * Constructor
     * 
     * @param pPath path to check out
     * @param pTemporaryDirectory local directory where check out are performed
     * @param pLocation location (directory or module) to check out
     * @param pLogin user profile to connect to the repository
     * @param pPassword user profile's password to connect to the repository
     */
    public RepositorySvn( String pPath, String pTemporaryDirectory, String pLocation, String pLogin, String pPassword )
    {
        this.setScmManager( new BasicScmManager() );
        this.getScmManager().setScmProvider( "svn", new SvnExeScmProvider() );
        this.setScmTemporaryDirectory( pTemporaryDirectory );
        this.setLocation( pLocation );
        this.setLogin( pLogin );
        this.setPassword( pPassword );

    }
}
