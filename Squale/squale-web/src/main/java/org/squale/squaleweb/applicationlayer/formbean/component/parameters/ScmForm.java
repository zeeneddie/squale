/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.squale.squaleweb.applicationlayer.formbean.component.parameters;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import org.squale.squaleweb.transformer.component.parameters.ScmConfTransformer;
import org.squale.squaleweb.util.SqualeWebActionUtils;

/**
 * Bean to set scm task
 */
public class ScmForm
    extends AbstractParameterForm
{

    /**
     * User profile to connect to the remote repository
     */
    private String mLogin;

    /**
     * Password
     */
    private String mPassword;

    /**
     * Liste des noms de VOBs relatives à un projet
     */
    private String[] mLocation;

    /**
     * Constructor
     */
    public ScmForm()
    {
        mLocation = new String[0];
        mLogin = "";
        mPassword = "";
    }

    /**
     * Access method for the mVOBName property.
     * 
     * @return the current value of the mVOBName property
     */
    public String[] getLocation()
    {
        return mLocation;
    }

    /**
     * Sets the value of the mLocation property.
     * 
     * @param pLocation the new value of the mLocation property
     */
    public void setLocation( String[] pLocation )
    {
        mLocation = pLocation;
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
     * @see org.squale.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTransformer()
     * @return transformer class to use
     */
    public Class getTransformer()
    {
        return ScmConfTransformer.class;
    }

    /**
     * Default name
     * 
     * @see org.squale.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getNameInSession()
     * @return name currently used
     */
    public String getNameInSession()
    {
        return "scmForm";
    }

    /**
     * Parameters of the task
     * 
     * @see org.squale.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getParametersConstants()
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
        setLogin( "" );
        setPassword( "" );
        setLocation( new String[0] );
    }

    /**
     * Default task name
     * 
     * @see org.squale.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTaskName()
     *      {@inheritDoc}
     */
    public String getTaskName()
    {
        return "ScmTask";
    }

    /**
     * Check form
     * 
     * @see org.squale.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#validateConf(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    protected void validateConf( ActionMapping pMapping, HttpServletRequest pRequest )
    {
        setLocation( SqualeWebActionUtils.cleanValues( getLocation() ) );
        if ( getLocation().length == 0 )
        {
            addError( "location", new ActionError( "error.invalid_path" ) );
        }
    }
}
