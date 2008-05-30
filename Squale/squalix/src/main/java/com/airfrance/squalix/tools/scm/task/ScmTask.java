package com.airfrance.squalix.tools.scm.task;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
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
        mConfiguration.parse( new FileInputStream( "config/sourcecodeanalyser-config.xml" ) );
        // The directory is created
        File dest = new File( mConfiguration.getRootDirectory() );
        // Append "/" to the path
        getData().putData( TaskData.VIEW_PATH, dest.getAbsolutePath() + "/" );

        // Retrieve settings of the current project
        StringParameterBO login = (StringParameterBO) pTaskParam.getParameters().get( ParametersConstants.SCMLOGIN );
        StringParameterBO password =
            (StringParameterBO) pTaskParam.getParameters().get( ParametersConstants.SCMPASSWORD );
        // Execute check-out
        ListParameterBO locationListBO =
            (ListParameterBO) pTaskParam.getParameters().get( ParametersConstants.SCMLOCATION );
        List locationList = locationListBO.getParameters();
        String path = null;

        // Create a temporary directory to store check outs
        File tempDirectory = new File( mConfiguration.getScmDirectory() );
        Iterator it = locationList.iterator();
        String[] locations = new String[locationList.size()];
        int index = 0;
        while ( it.hasNext() )
        {
            StringParameterBO location = (StringParameterBO) it.next();
            if ( location.getValue() != null )
            {
                checkOutFromRepository( tempDirectory, dest, location.getValue(), login.getValue(), password.getValue() );

            }
            index++;
        }
    }

    /**
     * Try to get sources from the repository
     * 
     * @param pTemporaryDirectory local directory where check out are stored
     * @param pSourceDirectory directory where data are analyzed
     * @param pPath path to acess to the remote repository to audit
     * @param pLogin id to connect to the remote repository
     * @param pPassword password to connect to the remote repository
     * @throws Exception exception when the remote repository connection occurs
     */
    protected void checkOutFromRepository( File pTemporaryDirectory, File pSourceDirectory, String pPath,
                                           String pLogin, String pPassword )
        throws Exception
    {
        AbstractRepository remoteRepository = null;

        // Example : "scm:cvs:pserver:myServer:/directory/subdirectory:myModule"
        if ( pPath.startsWith( "scm:cvs" ) )
        {
            remoteRepository = new RepositoryCvs();
        }
        else
        {
            // Example : "scm:svn:https://svn.squale.org/squale"
            if ( pPath.startsWith( "scm:svn" ) )
            {
                remoteRepository = new RepositorySvn();
            }
            else
            {
                // Example : "scm:local:/apps/myApp"
                if ( pPath.startsWith( "scm:local" ) )
                {
                    remoteRepository = new RepositoryLocal();
                }
            }
        }
        if ( remoteRepository != null )
        {
            ScmRepository scmRepository;
            String[] tab = { pPath, pTemporaryDirectory.getAbsolutePath() };
            LOGGER.info( ScmMessages.getString( "logs.task.repository", tab ) );
            try
            {
                scmRepository = remoteRepository.getScmRepository( pPath, pLogin, pPassword );

                // Does an error occur ?
                if ( !remoteRepository.isCheckOut( scmRepository, pTemporaryDirectory, pSourceDirectory ) )
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
}
