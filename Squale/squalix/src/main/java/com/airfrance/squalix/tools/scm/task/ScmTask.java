package com.airfrance.squalix.tools.scm.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.maven.scm.ScmException;
import org.apache.maven.scm.repository.ScmRepository;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import com.airfrance.squalix.core.AbstractTask;
import com.airfrance.squalix.core.TaskData;
import com.airfrance.squalix.core.TaskException;
import com.airfrance.squalix.core.exception.ConfigurationException;
import com.airfrance.squalix.util.sourcesrecovering.SourcesRecoveringOptimisation;

/**
 * Analyseur de code source disponible sous forme d'arborescence de fichiers
 */
public class ScmTask
    extends AbstractTask
{
    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( ScmTask.class );

    /**
     * Configuration
     */
    private ScmConfig mConfiguration;

    /**
     * Constructor
     */
    public ScmTask()
    {
        mConfiguration = new ScmConfig();
        mName = "ScmTask";
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
            // Get parameters of the Scm task
            MapParameterBO taskParam = (MapParameterBO) mProject.getParameter( ParametersConstants.SCM );
            LOGGER.info( ScmMessages.getString( "logs.task.initialized" ) + mProject.getName() );
            if ( taskParam == null )
            {
                String message = ScmMessages.getString( "exception.project.not_configured" );
                LOGGER.error( message );
                // Send an exception if a configuration is missing
                throw new ConfigurationException( message );
            }
            // Retrieve absolute path of the project
            ListParameterBO pathToAudit =
                (ListParameterBO) taskParam.getParameters().get( ParametersConstants.SCMLOCATION );
            if ( pathToAudit == null )
            {
                String message = ScmMessages.getString( "exception.path.not_found" );
                LOGGER.error( message );
                // Send an exception if a configuration is missing
                throw new ConfigurationException( message );
            }
            // Update the view path relating temporary parameters
            modifyViewPathInTempMap( taskParam );

            // set date on file system size
            affectFileSystemSize( mConfiguration.getRootDirectory(), true );
        }
        catch ( Exception e )
        {
            throw new TaskException( e );
        }
    }

    /**
     * Update parameter <code>view_path</code> in temporary parameters.
     * 
     * @param pTaskParam specific parameters of the project
     * @throws Exception if an error occurs
     */
    private void modifyViewPathInTempMap( MapParameterBO pTaskParam )
        throws Exception
    {
        // Check out into a directory whose the name is set in an XML file to parse
        mConfiguration.parse( new FileInputStream( "config/scm-config.xml" ) );
        // The directory is created
        File dest = new File( mConfiguration.getRootDirectory() );
        // Append "/" to the path
        getData().putData( TaskData.VIEW_PATH, dest.getAbsolutePath() + "/" );

        // Retrieve settings of the current project
        StringParameterBO login = (StringParameterBO) pTaskParam.getParameters().get( ParametersConstants.SCMLOGIN );
        StringParameterBO password =
            (StringParameterBO) pTaskParam.getParameters().get( ParametersConstants.SCMPASSWORD );
        ListParameterBO locationListBO =
            (ListParameterBO) pTaskParam.getParameters().get( ParametersConstants.SCMLOCATION );
        List locationList = locationListBO.getParameters();

        // Initialize repository before running check-out
        initializeScmRepository( dest, locationList, login.getValue(), password.getValue() );
    }

    /**
     * Try to get sources from the repository
     * 
     * @param pSourceDirectory directory where data are analyzed
     * @param pLocationList list of directories to check out
     * @param pLogin id to connect to the remote repository
     * @param pPassword password to connect to the remote repository
     * @throws Exception exception when the remote repository connection occurs
     */
    protected void initializeScmRepository( File pSourceDirectory, List<StringParameterBO> pLocationList,
                                            String pLogin, String pPassword )

        throws Exception
    {
        AbstractRepository remoteRepository = null;
        String location = null;

        for ( StringParameterBO locationBO : pLocationList )
        {
            location = locationBO.getValue();
            if ( !SourcesRecoveringOptimisation.pathAlreadyRecovered( location, mApplication ) )
            {
                remoteRepository = null;
                if ( location != null )
                {
                    // Example : "scm:cvs:pserver:myServer:/directory/subdirectory:myModule"
                    if ( location.startsWith( "scm:cvs" ) )
                    {
                        remoteRepository =
                            new RepositoryCvs( location, mConfiguration.getScmDirectory(), location, pLogin, pPassword );
                    }
                    else
                    {
                        // Example : "scm:svn:https://svn.squale.org/squale"
                        if ( location.startsWith( "scm:svn" ) )
                        {
                            remoteRepository =
                                new RepositorySvn( location, mConfiguration.getScmDirectory(), location, pLogin,
                                                   pPassword );
                        }
                        else
                        {
                            // Example : "scm:local:/apps/myApp"
                            if ( location.startsWith( "scm:local" ) )
                            {
                                remoteRepository =
                                    new RepositoryLocal( location, mConfiguration.getScmDirectory(), location, pLogin,
                                                         pPassword );
                            }
                        }
                    }
                }
                // Execute check out from the repository
                if ( remoteRepository != null )
                {
                    checkOutFromRepository( remoteRepository, pSourceDirectory );
                }
                if ( ScmMessages.getString( "properties.task.optimization" ).equals( "true" ) )
                {
                    SourcesRecoveringOptimisation.addToPathRecovered( location, mApplication );
                }
            }
            else
            {
                LOGGER.info( ScmMessages.getString( "logs.task.optimization", location ) );
            }
        }

    }

    /**
     * Launch check out from a remote repository into a local temporary directory
     * 
     * @param pRemoteRepository remote repository type
     * @param pSourceDirectory local directory where source code is analyzed once check out is performed
     * @throws IOException exception
     * @throws TaskException exception
     */
    private void checkOutFromRepository( AbstractRepository pRemoteRepository, File pSourceDirectory )
        throws IOException, TaskException
    {
        ScmRepository scmRepository;
        String[] tab = { pRemoteRepository.getLocation(), pRemoteRepository.getScmTemporaryDirectory() };
        LOGGER.info( ScmMessages.getString( "logs.task.repository", tab ) );
        try
        {
            // Check out sources
            scmRepository = pRemoteRepository.getScmRepository();
            pRemoteRepository.checkOut( new File( mConfiguration.getScmDirectory() ), scmRepository, pSourceDirectory );

            // Does an error occur ?
            if ( !pRemoteRepository.isCheckOut() )
            {
                throw new TaskException( ScmMessages.getString( "exception.task.no_checkout" ) );
            }
        }
        catch ( ScmException e )
        {
            LOGGER.error( ScmMessages.getString( "exception.task.no_repository" ) );
            throw new TaskException( e );
        }
    }

}
