package com.airfrance.squalix.tools.scm.task;

import org.apache.maven.scm.manager.BasicScmManager;
import org.apache.maven.scm.provider.cvslib.cvsjava.CvsJavaScmProvider;

/**
 * Connection to a Cvs repository
 */
public class RepositoryCvs
    extends AbstractRepository
{

    /** Constructor by default */
    public RepositoryCvs()
    {
        this.setScmManager( new BasicScmManager() );

        this.getScmManager().setScmProvider( "cvs", new CvsJavaScmProvider() );

    }
}
