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
import java.util.Iterator;
import java.util.List;

import org.squale.squalecommon.datatransfertobject.component.parameters.ListParameterDTO;
import org.squale.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import org.squale.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import org.squale.squaleweb.applicationlayer.formbean.component.parameters.UMLQualityForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;

/**
 * Transformation des paramètres de configuration UMLQuality
 */
public class UMLQualityConfTransformer
    implements WITransformer
{

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[]) {@inheritDoc}
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        UMLQualityForm umlQualityForm = new UMLQualityForm();
        objToForm( pObject, umlQualityForm );
        return umlQualityForm;
    }

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      org.squale.welcom.struts.bean.WActionForm) {@inheritDoc}
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        MapParameterDTO params = (MapParameterDTO) pObject[0];
        UMLQualityForm umlQualityForm = (UMLQualityForm) pForm;
        // On remplit le formulaire avec l'emplacement
        // du fichier XMI du modèle
        MapParameterDTO umlQualityParams =
            (MapParameterDTO) params.getParameters().get( ParametersConstants.UMLQUALITY );
        if ( null != umlQualityParams )
        {
            StringParameterDTO xmiFileParam =
                (StringParameterDTO) umlQualityParams.getParameters().get( ParametersConstants.UMLQUALITY_SOURCE_XMI );
            String xmiFilePath = xmiFileParam.getValue();
            umlQualityForm.setXmiFile( xmiFilePath );
            // excludedClasses
            ListParameterDTO classesDTO =
                (ListParameterDTO) umlQualityParams.getParameters().get( ParametersConstants.MODEL_EXCLUDED_CLASSES );
            if ( classesDTO != null )
            {
                List classesParam = classesDTO.getParameters();
                Iterator classesIt = classesParam.iterator();
                String[] classes = new String[classesParam.size()];
                int classesIndex = 0;
                while ( classesIt.hasNext() )
                {
                    StringParameterDTO currClass = (StringParameterDTO) classesIt.next();
                    classes[classesIndex] = currClass.getValue();
                    classesIndex++;
                }
                umlQualityForm.setExcludeUMLPatterns( classes );
            }
        }
    }

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WActionForm)
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
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WActionForm,
     *      java.lang.Object[]) {@inheritDoc}
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        MapParameterDTO params = (MapParameterDTO) pObject[0];
        UMLQualityForm umlQualityForm = (UMLQualityForm) pForm;
        // Insertion des paramètres dans la map
        MapParameterDTO umlQualityParams = new MapParameterDTO();
        StringParameterDTO xmiFileParam = new StringParameterDTO();
        xmiFileParam.setValue( umlQualityForm.getXmiFile() );
        umlQualityParams.getParameters().put( ParametersConstants.UMLQUALITY_SOURCE_XMI, xmiFileParam );
        params.getParameters().put( ParametersConstants.UMLQUALITY, umlQualityParams );
        // modelExcludedClasses si non vide:
        String[] excludedPatternsTab = umlQualityForm.getExcludeUMLPatterns();
        ListParameterDTO excludedPatterns = new ListParameterDTO();
        ArrayList paramsList = new ArrayList();
        for ( int i = 0; i < excludedPatternsTab.length; i++ )
        {
            StringParameterDTO strParam = new StringParameterDTO();
            strParam.setValue( excludedPatternsTab[i] );
            paramsList.add( strParam );
        }
        excludedPatterns.setParameters( paramsList );
        umlQualityParams.getParameters().put( ParametersConstants.MODEL_EXCLUDED_CLASSES, excludedPatterns );
        if ( excludedPatternsTab.length == 0 )
        {
            umlQualityParams.getParameters().remove( ParametersConstants.MODEL_EXCLUDED_CLASSES );
        }
    }

}
