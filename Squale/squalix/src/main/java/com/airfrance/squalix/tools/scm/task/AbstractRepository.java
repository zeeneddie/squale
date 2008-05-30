package com.airfrance.squalix.tools.scm.task;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.maven.scm.ScmException;
import org.apache.maven.scm.ScmFile;
import org.apache.maven.scm.ScmFileSet;
import org.apache.maven.scm.ScmResult;
import org.apache.maven.scm.command.checkout.CheckOutScmResult;
import org.apache.maven.scm.manager.NoSuchScmProviderException;
import org.apache.maven.scm.manager.ScmManager;
import org.apache.maven.scm.repository.ScmRepository;
import org.apache.maven.scm.repository.ScmRepositoryException;
import org.codehaus.plexus.util.StringUtils;

import com.airfrance.squalix.util.file.FileUtility;

/**
 * abstract class to connect to a repository (Cvs, Svn or local by example)
 */
public abstract class AbstractRepository
{
    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( AbstractRepository.class );

    /** Scm Manager * */
    private ScmManager scmManager;

    /**
     * Accessor
     * 
     * @return returns the scmManager property
     */
    public ScmManager getScmManager()
    {
        return this.scmManager;
    }

    /**
     * Accessor
     * 
     * @param pScmManager type of Scm manager
     */
    public void setScmManager( ScmManager pScmManager )
    {
        this.scmManager = pScmManager;
    }

    /**
     * Attempt to retrieve sources in a local directory
     * 
     * @param pScmUrl url to connect to the repository
     * @param pLogin login to connect to the repository
     * @param pPassword password to connect to the repository
     * @return repository local or remote repository
     * @throws ScmRepositoryException Scm exception
     * @throws NoSuchScmProviderException Scm exception
     */
    public ScmRepository getScmRepository( String pScmUrl, String pLogin, String pPassword )
        throws ScmRepositoryException, NoSuchScmProviderException
    {
        ScmRepository repository = scmManager.makeScmRepository( pScmUrl.trim() );

        repository.getProviderRepository().setPersistCheckout( true );
        if ( !StringUtils.isEmpty( pLogin ) )
        {
            repository.getProviderRepository().setUser( pLogin );

            if ( !StringUtils.isEmpty( pPassword ) )
            {
                repository.getProviderRepository().setPassword( pPassword );
            }
            else
            {
                repository.getProviderRepository().setPassword( "" );
            }
        }
        return repository;
    }

    /**
     * Checks if the url to the repository is right
     * 
     * @param pUrl url to check
     * @return list of errors
     */
    public List validateRepository( String pUrl )
    {
        return this.scmManager.validateScmRepository( pUrl );
    }

    /**
     * Check out files in a local directory
     * 
     * @param scmRepository repository sources are stored
     * @param workingDirectory local directory where sources are analyzed
     * @param temporaryDirectory temporary directory to store check out
     * @return true is check-out is completed
     * @throws ScmException Scm exception
     * @throws IOException Exception in files
     */
    public boolean isCheckOut( ScmRepository scmRepository, File temporaryDirectory, File workingDirectory )
        throws ScmException, IOException
    {
        boolean checkOut = true;
        // Temporary directory already exists !!
        if ( workingDirectory.exists() )
        {
            LOGGER.error( ScmMessages.getString( "exception.task.existing_directory",
                                                 workingDirectory.getAbsolutePath() ) );
            checkOut = false;
        }
        // Creation of the directory where sources are analyzed
        if ( !workingDirectory.mkdirs() )
        {
            LOGGER.error( ScmMessages.getString( "exception.task.creation_directory",
                                                 workingDirectory.getAbsolutePath() ) );
            checkOut = false;
        }
        // Check-out sources into the temporary directory
        CheckOutScmResult result = scmManager.checkOut( scmRepository, new ScmFileSet( temporaryDirectory ) );

        // Copy local check out directory into local source code directory
        FileUtility.copyDirInto( temporaryDirectory, workingDirectory );

        // Deletion of the temporary directory
        FileUtility.deleteRecursively( temporaryDirectory );

        // Log check-out
        checkResult( result );

        List checkedOutFiles = result.getCheckedOutFiles();
        if ( checkedOutFiles != null )
        {
            // Display all objects in check-out
            for ( Iterator it = checkedOutFiles.iterator(); it.hasNext(); )
            {
                ScmFile file = (ScmFile) it.next();
                LOGGER.info( ScmMessages.getString( "logs.task.checkout" ) + file.getPath() );
            }
        }
        return checkOut;
    }

    /**
     * Display logs
     * 
     * @param result validation of check-out
     */
    private void checkResult( ScmResult result )
    {
        // Display logs following to the scm connection in a remote repository
        if ( !result.isSuccess() && result.getProviderMessage() != null )
        {
            LOGGER.warn( ScmMessages.getString( "exception.task.provider", result.getProviderMessage() ) );
        }
    }
}
