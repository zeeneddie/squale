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
import org.squale.squaleweb.transformer.component.parameters.ClearCaseConfTransformer;
import org.squale.squaleweb.util.SqualeWebActionUtils;

/**
 * Bean pour la configuration de la tâche clearcase.
 */
public class ClearCaseForm
    extends AbstractParameterForm
{

    /**
     * Liste des noms de VOBs relatives à un projet
     */
    private String[] mLocation = null;

    /**
     * Application au sens Clearcase
     */
    private String mAppli = null;

    /**
     * Label ou branche (ClearCase) en cours d'analyse
     */
    private String mLabelAudited = null;

    /**
     * Constructeur
     */
    public ClearCaseForm()
    {
        mLocation = new String[0];
        mAppli = "";
        mLabelAudited = "";
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
     * @return Label ou branche (ClearCase) en cours d'analyse
     */
    public String getLabelAudited()
    {
        return mLabelAudited;
    }

    /**
     * @param pLabelAudited Label ou branche (ClearCase) en cours d'analyse
     */
    public void setLabelAudited( String pLabelAudited )
    {
        mLabelAudited = pLabelAudited;
    }

    /**
     * @return Application au sens Clearcase (Vob d'admin)
     */
    public String getAppli()
    {
        return mAppli;
    }

    /**
     * @param string Application au sens Clearcase (Vob d'admin)
     */
    public void setAppli( String string )
    {
        mAppli = string;
    }

    /**
     * @see org.squale.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTransformer()
     * @return le transformer à utiliser
     */
    public Class getTransformer()
    {
        return ClearCaseConfTransformer.class;
    }

    /**
     * @see org.squale.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getNameInSession()
     * @return le nom utilisé
     */
    public String getNameInSession()
    {
        return "clearCaseForm";
    }

    /**
     * @see org.squale.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getParametersConstants()
     *      {@inheritDoc}
     */
    public String[] getParametersConstants()
    {
        return new String[] { ParametersConstants.CLEARCASE };
    }

    /**
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    public void reset( ActionMapping mapping, HttpServletRequest request )
    {
        setAppli( "" );
        setLabelAudited( "" );
        setLocation( new String[0] );
    }

    /**
     * @see org.squale.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTaskName()
     *      {@inheritDoc}
     */
    public String getTaskName()
    {
        return "ClearCaseTask";
    }

    /**
     * @see org.squale.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#validateConf(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    protected void validateConf( ActionMapping pMapping, HttpServletRequest pRequest )
    {
        setAppli( getAppli().trim() );
        if ( getAppli().length() == 0 )
        {
            addError( "appli", new ActionError( "error.field.required" ) );
        }
        setLabelAudited( getLabelAudited().trim() );
        if ( getLabelAudited().length() == 0 )
        {
            addError( "labelAudited", new ActionError( "error.field.required" ) );
        }
        setLocation( SqualeWebActionUtils.cleanValues( getLocation() ) );
        if ( getLocation().length == 0 )
        {
            addError( "location", new ActionError( "error.field.required" ) );
        }
    }

}
