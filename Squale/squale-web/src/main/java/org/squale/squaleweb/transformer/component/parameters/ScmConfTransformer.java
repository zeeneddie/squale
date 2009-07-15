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
package org.squale.squaleweb.transformer.component.parameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.squale.squalecommon.datatransfertobject.component.parameters.ListParameterDTO;
import org.squale.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import org.squale.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import org.squale.squaleweb.applicationlayer.formbean.component.parameters.ScmForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;

/**
 * Transformation of Scm parameters
 */
public class ScmConfTransformer
    implements WITransformer
{

    /**
     * Transformer from an object to a form
     * 
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[])
     * @param pObject array of objects
     * @return form transformed
     * @throws WTransformerException if an error occurs
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        ScmForm scmForm = new ScmForm();
        objToForm( pObject, scmForm );
        return scmForm;
    }

    /**
     * Transformer from an object to the form
     * 
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      org.squale.welcom.struts.bean.WActionForm)
     * @param pObject array of objects
     * @param pForm the form
     * @throws WTransformerException if an error occurs
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        MapParameterDTO projectParams = (MapParameterDTO) pObject[0];
        MapParameterDTO params = (MapParameterDTO) projectParams.getParameters().get( ParametersConstants.SCM );
        if ( params != null )
        {
            // Fill the form
            ScmForm scmForm = (ScmForm) pForm;
            Map ccParams = params.getParameters();

            // User profile to connect to the remote repository
            StringParameterDTO login = (StringParameterDTO) ccParams.get( ParametersConstants.SCMLOGIN );
            scmForm.setLogin( login.getValue() );
            // Password
            StringParameterDTO password = (StringParameterDTO) ccParams.get( ParametersConstants.SCMPASSWORD );
            scmForm.setPassword( password.getValue() );
            // Location of paths to audit
            ListParameterDTO locationsDTO = (ListParameterDTO) ccParams.get( ParametersConstants.SCMLOCATION );
            List locationsList = locationsDTO.getParameters();
            Iterator it = locationsList.iterator();
            String[] locations = new String[locationsList.size()];
            int index = 0;
            while ( it.hasNext() )
            {
                StringParameterDTO location = (StringParameterDTO) it.next();            
                if (location.getValue() == null) {
                    locations[index] = " ";                    
                } else {
                    locations[index] = location.getValue();
                }

                index++;
            }
            scmForm.setLocation( locations );
        }
    }

    /**
     * Transformer from the form to an object
     * 
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WActionForm)
     * @param pForm the form
     * @return array of objects that are transformed
     * @throws WTransformerException if an error occurs
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        Object[] obj = { new MapParameterDTO() };
        formToObj( pForm, obj );
        return obj;
    }

    /**
     * Transformer from the form to an object
     * 
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WActionForm,
     *      java.lang.Object[])
     * @param pForm the form
     * @param pObject array of objects
     * @throws WTransformerException if an error occurs
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        MapParameterDTO params = (MapParameterDTO) pObject[0];
        ScmForm scmForm = (ScmForm) pForm;
        // Specific parameters of Scm are added to general project's
        Map<String, Object> scmParams = new HashMap<String, Object>();
        // user profile
        StringParameterDTO login = new StringParameterDTO();
        login.setValue( scmForm.getLogin() );
        scmParams.put( ParametersConstants.SCMLOGIN, login );
        // password
        StringParameterDTO password = new StringParameterDTO();
        password.setValue( scmForm.getPassword() );
        scmParams.put( ParametersConstants.SCMPASSWORD, password );       
        // location
        ListParameterDTO locations = new ListParameterDTO();
        ArrayList<StringParameterDTO> locationsList = new ArrayList<StringParameterDTO>();
        String[] location = scmForm.getLocation();
        for ( int i = 0; i < location.length; i++ )
        {
            StringParameterDTO strParam = new StringParameterDTO();
            strParam.setValue( location[i] );
            locationsList.add( strParam );
        }
        locations.setParameters( locationsList );
        scmParams.put( ParametersConstants.SCMLOCATION, locations );      

        MapParameterDTO scmMap = new MapParameterDTO();
        scmMap.setParameters( scmParams );
        params.getParameters().put( ParametersConstants.SCM, scmMap );
    }
}
