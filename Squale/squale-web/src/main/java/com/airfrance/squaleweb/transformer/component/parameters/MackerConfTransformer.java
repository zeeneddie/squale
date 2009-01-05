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
package com.airfrance.squaleweb.transformer.component.parameters;

import com.airfrance.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.MackerForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformation des paramètres Macker
 */
public class MackerConfTransformer
    implements WITransformer
{

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[]) {@inheritDoc}
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        MackerForm mackerForm = new MackerForm();
        objToForm( pObject, mackerForm );
        return mackerForm;
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      com.airfrance.welcom.struts.bean.WActionForm) {@inheritDoc}
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        MapParameterDTO params = (MapParameterDTO) pObject[0];
        MackerForm mackerForm = (MackerForm) pForm;
        // On remplit le form
        // Fichier de configuration
        MapParameterDTO mackerParams = (MapParameterDTO) params.getParameters().get( ParametersConstants.MACKER );
        if ( null != mackerParams )
        {
            StringParameterDTO configFileParam =
                (StringParameterDTO) mackerParams.getParameters().get( ParametersConstants.MACKER_CONFIGURATION );
            String configFilePath = configFileParam.getValue();
            mackerForm.setConfigFile( configFilePath );
        }
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm)
     *      {@inheritDoc}
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        Object[] obj = { new MapParameterDTO() };
        formToObj( pForm, obj );
        return obj;
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm,
     *      java.lang.Object[]) {@inheritDoc}
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        MapParameterDTO params = (MapParameterDTO) pObject[0];
        MackerForm mackerForm = (MackerForm) pForm;
        // Insertion des paramètres dans la map
        // Configuration macker
        MapParameterDTO mackerParams = new MapParameterDTO();
        StringParameterDTO configFileParam = new StringParameterDTO();
        configFileParam.setValue( mackerForm.getConfigFile() );
        mackerParams.getParameters().put( ParametersConstants.MACKER_CONFIGURATION, configFileParam );
        params.getParameters().put( ParametersConstants.MACKER, mackerParams );
    }

}
