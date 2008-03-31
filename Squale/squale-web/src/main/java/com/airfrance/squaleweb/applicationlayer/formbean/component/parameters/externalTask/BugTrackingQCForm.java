package com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.externalTask;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm;

import com.airfrance.squaleweb.taskconfig.qc.ExtBugTrackingQCTaskConfig;
import com.airfrance.squaleweb.transformer.component.parameters.external.BugTrackingQCConfTransformer;

/**
 * 
 */
public class BugTrackingQCForm
    extends AbstractParameterForm
{

    /** Le login */
    private String mQCLogin;

    /** Le password */
    private String mQCPassword;

    /** L'URL */
    private String mQCUrl;

    /** L'emplacement de la trace */
    private String mQCTrace;

    /**
     * Le constructeur
     */
    public BugTrackingQCForm()
    {
        mQCLogin = "";
        mQCPassword = "";
        mQCUrl = "";
        mQCTrace = "";

    }

    /**
     * (non-Javadoc)
     * 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTransformer()
     */
    public Class getTransformer()
    {
        return BugTrackingQCConfTransformer.class;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getParametersConstants()
     */
    public String[] getParametersConstants()
    {
        return new String[] { ExtBugTrackingQCTaskConfig.TASK_NAME };
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getNameInSession()
     */
    public String getNameInSession()
    {
        return "bugTrackingQCForm";
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTaskName()
     */
    public String getTaskName()
    {
        return "ExtBugTrackingQCTask";
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#validateConf(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest)
     */
    protected void validateConf( ActionMapping pMapping, HttpServletRequest pRequest )
    {
        // Traitement du champ login
        setQCLogin( getQCLogin().trim() );
        if ( getQCLogin().length() == 0 )
        {
            addError( "ExtBugTrackingQCLogin", new ActionError( "error.field.required" ) );
        }

        setQCPassword( getQCPassword().trim() );
        if ( getQCPassword().length() == 0 )
        {
            addError( "ExtBugTrackingQCPwd", new ActionError( "error.field.required" ) );
        }

        setQCUrl( getQCUrl().trim() );
        if ( getQCUrl().length() == 0 )
        {
            addError( "ExtBugTrackingQCURL", new ActionError( "error.field.required" ) );
        }
    }

    /**
     * {@inheritdoc}
     * 
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest)
     */
    public void reset( ActionMapping mapping, HttpServletRequest request )
    {
        setQCLogin( "" );
        setQCPassword( "" );
        setQCUrl( "" );
        setQCTrace( "" );
    }

    /**
     * @return retourne le login
     */
    public String getQCLogin()
    {
        return mQCLogin;
    }

    /**
     * @return retourne l'URL
     */
    public String getQCUrl()
    {
        return mQCUrl;
    }

    /**
     * @return retourne le password
     */
    public String getQCPassword()
    {
        return mQCPassword;
    }

    /**
     * @return recupere l'emplacement de la trace
     */
    public String getQCTrace()
    {
        return mQCTrace;
    }

    /**
     * @param pQCLogin insere ce login
     */
    public void setQCLogin( String pQCLogin )
    {
        mQCLogin = pQCLogin;
    }

    /**
     * @param pQCUrl insere cet URL
     */
    public void setQCUrl( String pQCUrl )
    {
        mQCUrl = pQCUrl;
    }

    /**
     * @param pQCPassword insere ce password
     */
    public void setQCPassword( String pQCPassword )
    {
        mQCPassword = pQCPassword;
    }

    /**
     * @param pQCTrace insere cet emplacement pour la trace
     */
    public void setQCTrace( String pQCTrace )
    {
        mQCTrace = pQCTrace;
    }
}
