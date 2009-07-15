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
import org.squale.squaleweb.transformer.component.parameters.CobolMcCabeProjectConfTransformer;

/**
 * Formulaire de configuration McCabe pour le C++
 */
public class CobolMcCabeForm
    extends AbstractParameterForm
{

    /** Dialect */
    private String mDialect = "";

    /**
     * @return le dialect
     */
    public String getDialect()
    {
        return mDialect;
    }

    /**
     * @param pDialect le dialect
     */
    public void setDialect( String pDialect )
    {
        mDialect = pDialect;
    }

    /**
     * @see org.squale.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTransformer()
     * @return le transformer à utiliser
     */
    public Class getTransformer()
    {
        return CobolMcCabeProjectConfTransformer.class;
    }

    /**
     * @see org.squale.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getNameInSession()
     * @return le nom utilisé en session
     */
    public String getNameInSession()
    {
        return "cobolMcCabeForm";
    }

    /**
     * @see org.squale.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getParametersConstants()
     *      {@inheritDoc}
     */
    public String[] getParametersConstants()
    {
        return new String[] { ParametersConstants.COBOL, ParametersConstants.DIALECT };
    }

    /**
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    public void reset( ActionMapping mapping, HttpServletRequest request )
    {
        setDialect( "" );
    }

    /**
     * @see org.squale.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTaskName()
     *      {@inheritDoc}
     */
    public String getTaskName()
    {
        return "CobolMcCabeTask";
    }

    /**
     * @see org.squale.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#validateConf(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    protected void validateConf( ActionMapping pMapping, HttpServletRequest pRequest )
    {
        // Traitement du dialect
        if ( getDialect().length() == 0 )
        {
            addError( "dialect", new ActionError( "error.field.required" ) );
        }
    }
}
