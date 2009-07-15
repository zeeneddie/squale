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

import java.util.HashMap;
import java.util.Map;

import org.squale.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import org.squale.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import org.squale.squaleweb.applicationlayer.formbean.component.parameters.AnalyserForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;

/**
 * Transforme la configuration de l'analyseur de source
 */
public class AnalyserTransformer
    implements WITransformer
{

    /**
     * @param pObject le tableau des paramètres
     * @return le formulaire transformé
     * @throws WTransformerException si erreur
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[])
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        AnalyserForm form = new AnalyserForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * @param pObject les paramètres
     * @param pForm le formulaire
     * @throws WTransformerException si erreur
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      org.squale.welcom.struts.bean.WActionForm)
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        MapParameterDTO projectParam = (MapParameterDTO) pObject[0];
        AnalyserForm analyserForm = (AnalyserForm) pForm;
        MapParameterDTO taskParam = (MapParameterDTO) projectParam.getParameters().get( ParametersConstants.ANALYSER );
        // Il se peut que les paramètres soient nuls
        if ( taskParam != null )
        {
            StringParameterDTO pathParam =
                (StringParameterDTO) taskParam.getParameters().get( ParametersConstants.PATH );
            if ( pathParam != null )
            {
                analyserForm.setPath( pathParam.getValue() );
            }
        }
    }

    /**
     * @param pForm le formulaire
     * @return le tableau des objets transformés à partir du formulaire
     * @throws WTransformerException si erreur
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WActionForm)
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        Object[] obj = { new MapParameterDTO() };
        formToObj( pForm, obj );
        return obj;
    }

    /**
     * @param pForm le formulaire
     * @param pObject le tableau des paramètres
     * @throws WTransformerException si erreur
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WActionForm,
     *      java.lang.Object[])
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        MapParameterDTO params = (MapParameterDTO) pObject[0];
        AnalyserForm analyserForm = (AnalyserForm) pForm;
        // On ajoute les paramètres de la tâche aux paramètres
        // généraux du projet
        Map analyserParams = new HashMap();
        // path:
        StringParameterDTO path = new StringParameterDTO();
        path.setValue( analyserForm.getPath() );
        analyserParams.put( ParametersConstants.PATH, path );
        MapParameterDTO aMap = new MapParameterDTO();
        aMap.setParameters( analyserParams );
        params.getParameters().put( ParametersConstants.ANALYSER, aMap );
    }

}
