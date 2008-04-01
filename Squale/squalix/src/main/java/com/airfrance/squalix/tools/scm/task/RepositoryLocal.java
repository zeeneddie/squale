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
}
