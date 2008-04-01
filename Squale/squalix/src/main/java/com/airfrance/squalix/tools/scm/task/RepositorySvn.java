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
}
