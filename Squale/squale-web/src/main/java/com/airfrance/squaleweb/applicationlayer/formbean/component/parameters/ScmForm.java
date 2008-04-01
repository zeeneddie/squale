package com.airfrance.squaleweb.applicationlayer.formbean.component.parameters;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.maven.scm.manager.BasicScmManager;
import org.apache.maven.scm.manager.ScmManager;
import org.apache.maven.scm.provider.cvslib.cvsjava.CvsJavaScmProvider;
import org.apache.maven.scm.provider.local.LocalScmProvider;
import org.apache.maven.scm.provider.svn.svnexe.SvnExeScmProvider;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.transformer.component.parameters.ScmConfTransformer;

/**
 * Bean to set scm task
 */
public class ScmForm
    extends AbstractParameterForm
{

    /**
     * Path to audit
     */
    private String mPathToAudit;

    /**
     * User profile to connect to the remote repository
     */
    private String mLogin;

    /**
     * Password
     */
    private String mPassword;

    /**
     * Constructor
     */
    public ScmForm()
    {
        mPathToAudit = "";
        mLogin = "";
        mPassword = "";
    }

    /**
     * Access method
     * 
     * @return the current value of the mPathsToAudit property
     */
    public String getPathToAudit()
    {
        return mPathToAudit;
    }

    /**
     * Sets the value of the mPathToAudit property.
     * 
     * @param pPathToAudit the new value of the mPathToAudit property
     */
    public void setPathToAudit( String pPathToAudit )
    {
        mPathToAudit = pPathToAudit;
    }

    /**
     * Access method
     * 
     * @return the current value of the mUserProfile property
     */
    public String getLogin()
    {
        return mLogin;
    }

    /**
     * Access method
     * 
     * @param pLogin the new value of mUserProfile property
     */
    public void setLogin( String pLogin )
    {
        mLogin = pLogin;
    }

    /**
     * Access method
     * 
     * @return the current value of the mPassword property
     */
    public String getPassword()
    {
        return mPassword;
    }

    /**
     * Access method
     * 
     * @param pPassword the new value of the mPassword property
     */
    public void setPassword( String pPassword )
    {
        mPassword = pPassword;
    }

    /**
     * Transformer to use
     * 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTransformer()
     * @return transformer class to use
     */
    public Class getTransformer()
    {
        return ScmConfTransformer.class;
    }

    /**
     * Default name
     * 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getNameInSession()
     * @return name currently used
     */
    public String getNameInSession()
    {
        return "scmForm";
    }

    /**
     * Parameters of the task
     * 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getParametersConstants()
     *      {@inheritDoc}
     */
    public String[] getParametersConstants()
    {
        return new String[] { ParametersConstants.SCM };
    }

    /**
     * Reinitialize the instance
     * 
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    public void reset( ActionMapping mapping, HttpServletRequest request )
    {
        setPathToAudit( "" );
        setLogin( "" );
        setPassword( "" );
    }

    /**
     * Default task name
     * 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTaskName()
     *      {@inheritDoc}
     */
    public String getTaskName()
    {
        return "ScmTask";
    }

    /**
     * Check form
     * 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#validateConf(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    protected void validateConf( ActionMapping pMapping, HttpServletRequest pRequest )
    {
        final String scmPrefix = "scm:";

        // Path to audit field is mandatory
        setPathToAudit( getPathToAudit().trim() );
        if ( getPathToAudit().length() == 0 )
        {
            addError( "pathToAudit", new ActionError( "error.field.required" ) );
        }
        else
        {
            // If the path to audit doesn't start with "scm:", it is an incorrect url
            if ( !getPathToAudit().startsWith( scmPrefix ) )
            {
                addError( "pathToAudit", new ActionError( "error.invalid_path" ) );
            }
            else
            {
                // Checks if the path to the repository is right
                if ( !isRepositoryValidated() )
                {
                    addError( "pathToAudit", new ActionError( "error.invalid_path" ) );
                }
            }
        }
    }

    /**
     * Validation of the path to the repository
     * 
     * @return boolean to indicate if the path is correct
     */
    private boolean isRepositoryValidated()
    {
        boolean bValidation = false;
        List errorsList = null;

        ScmManager scmManager = new BasicScmManager();

        // Look-up the type of repository, extracted from the path to audit (example : the url scm:svn:http://... tracks
        // svn);
        if ( getPathToAudit().startsWith( "scm:cvs" ) )
        {
            scmManager.setScmProvider( "cvs", new CvsJavaScmProvider() );
            errorsList = scmManager.validateScmRepository( getPathToAudit() );
        }
        else
        {
            // Validates the path for a local directory
            if ( getPathToAudit().startsWith( "scm:local" ) )
            {
                scmManager.setScmProvider( "local", new LocalScmProvider() );
                errorsList = scmManager.validateScmRepository( getPathToAudit() );
            }
            else
            {
                // Checks the access to a subversion repository
                if ( getPathToAudit().startsWith( "scm:svn" ) )
                {
                    scmManager.setScmProvider( "svn", new SvnExeScmProvider() );
                    errorsList = scmManager.validateScmRepository( getPathToAudit() );
                }
                else
                {
                    // The repository (cvs, svn,...) couldn't be found : so an element is added in the list to indicate
                    // errors
                    errorsList = new ArrayList();
                    errorsList.add( " " );
                }

            }
        }
        // Some errors ?
        if ( errorsList == null || errorsList.size() <= 0 )
        {
            bValidation = true;
        }
        return bValidation;
    }
}
