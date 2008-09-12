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

    /** Temporary directory * */
    private String scmTemporaryDirectory;

    /** location, directory to check out * */
    private String location;

    /** login to access the repository */
    private String login;

    /** Login's Password to access the repository */
    private String password;

    /** check out success ? */
    private boolean checkOut;

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
     * @param pScmTemporaryDirectory temporary directory
     */
    public void setScmTemporaryDirectory( String pScmTemporaryDirectory )
    {
        this.scmTemporaryDirectory = pScmTemporaryDirectory;
    }

    /**
     * Accessor
     * 
     * @return returns the local directory where check out are preformed
     */
    public String getScmTemporaryDirectory()
    {
        return this.scmTemporaryDirectory;
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
     * Accessor of the attribute checkOut
     * 
     * @return check out success or not
     */
    public boolean isCheckOut()
    {
        return checkOut;
    }

    /**
     * Accessor of the attribute checkOut
     * 
     * @param pCheckOut check out success ?
     */
    public void setCheckOut( boolean pCheckOut )
    {
        this.checkOut = pCheckOut;
    }

    /**
     * Accessor
     * 
     * @return location to check out (module or directory)
     */
    public String getLocation()
    {
        return location;
    }

    /**
     * Accessor
     * 
     * @param pLocation location to check out (module or directory)
     */
    public void setLocation( String pLocation )
    {
        this.location = pLocation;
    }

    /**
     * Accessor
     * 
     * @return login to the repository
     */
    public String getLogin()
    {
        return login;
    }

    /**
     * Accessor
     * 
     * @param pLogin to the repository
     */
    public void setLogin( String pLogin )
    {
        this.login = pLogin;
    }

    /**
     * Accessor
     * 
     * @return user profile's password to access the repository
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Accessor
     * 
     * @param pPassword user profile's password to access the repository
     */
    public void setPassword( String pPassword )
    {
        this.password = pPassword;
    }

    /**
     * Attempt to retrieve sources in a local directory
     * 
     * @return repository local or remote repository
     * @throws ScmRepositoryException Scm exception
     * @throws NoSuchScmProviderException Scm exception
     */
    public ScmRepository getScmRepository()
        throws ScmRepositoryException, NoSuchScmProviderException
    {
        ScmRepository repository = scmManager.makeScmRepository( this.location.trim() );

        repository.getProviderRepository().setPersistCheckout( true );
        if ( !StringUtils.isEmpty( this.login ) )
        {
            repository.getProviderRepository().setUser( this.login );

            if ( !StringUtils.isEmpty( this.password ) )
            {
                repository.getProviderRepository().setPassword( this.password );
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
     * @param defaultTemporaryDirectory default temporary directory where check out are performed
     * @throws ScmException Scm exception
     * @throws IOException Exception in files
     */
    public void checkOut( File defaultTemporaryDirectory, ScmRepository scmRepository, File workingDirectory )
        throws ScmException, IOException
    {
        File temporaryDirectory = new File( this.getScmTemporaryDirectory() );

        this.checkOut = true;
        // Temporary directory already exists !!
        if ( temporaryDirectory.exists() )
        {
            LOGGER.error( ScmMessages.getString( "exception.task.existing_directory",
                                                 workingDirectory.getAbsolutePath() ) );
            this.checkOut = false;
        }
        // Creation of the directory where sources are analyzed
        if ( !temporaryDirectory.mkdirs() )
        {
            LOGGER.error( ScmMessages.getString( "exception.task.creation_directory",
                                                 workingDirectory.getAbsolutePath() ) );
            this.checkOut = false;
        }

        if ( this.checkOut )
        {
            // Check-out sources into the temporary directory
            CheckOutScmResult result = scmManager.checkOut( scmRepository, new ScmFileSet( temporaryDirectory ) );

            // Copy local check out directory into local source code directory
            FileUtility.copyContentIntoDir( defaultTemporaryDirectory, workingDirectory );

            // Deletion of the temporary directory
            FileUtility.deleteRecursively( defaultTemporaryDirectory );

            // Display logs following to the scm connection in a remote repository
            if ( !result.isSuccess() && result.getProviderMessage() != null )
            {
                LOGGER.warn( ScmMessages.getString( "exception.task.provider", result.getProviderMessage() ) );
            }

            if ( LOGGER.isDebugEnabled() )
            {
                List checkedOutFiles = result.getCheckedOutFiles();
                if ( checkedOutFiles != null )
                {
                    // Display all objects in check-out
                    for ( Iterator it = checkedOutFiles.iterator(); it.hasNext(); )
                    {
                        ScmFile file = (ScmFile) it.next();
                        LOGGER.debug( ScmMessages.getString( "logs.task.checkout" ) + file.getPath() );
                    }
                }
            }
        }
    }
}
