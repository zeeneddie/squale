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

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import com.airfrance.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.config.TaskDTO;
import com.airfrance.squalecommon.datatransfertobject.config.TaskParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.rulechecking.CheckstyleDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.CheckstyleForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Conversion des informations du formulaire de configuration Checkstyle
 */
public class CheckstyleProjectConfTransformer
    implements WITransformer
{

    /**
     * @param pObject l'objet à transformer
     * @throws WTransformerException si un pb apparait.
     * @return le formulaire.
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        CheckstyleForm form = new CheckstyleForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * @param pObject l'objet à transformer
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparait.
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        int index = 0;
        MapParameterDTO projectParamsDTO = (MapParameterDTO) pObject[index++];
        TaskDTO task = (TaskDTO) pObject[index++];
        Collection versions = (Collection) pObject[index++];

        CheckstyleForm form = (CheckstyleForm) pForm;
        StringParameterDTO params =
            (StringParameterDTO) projectParamsDTO.getParameters().get( ParametersConstants.CHECKSTYLE_RULESET_NAME );
        if ( params != null )
        {
            form.setSelectedRuleSet( params.getValue().trim() );
        }
        else if ( form.isNewConf() )
        {
            params = new StringParameterDTO( getDefaultRuleSet( task ) );
            if ( params.getValue().length() > 0 )
            {
                projectParamsDTO.getParameters().put( ParametersConstants.CHECKSTYLE_RULESET_NAME, params );
                form.setSelectedRuleSet( params.getValue() );
            }
        }

        // Mettre la liste des versions disponibles dans la formbean
        Iterator it = versions.iterator();
        // On filtre pour ne conserver que les noms de ruleset uniques
        HashSet set = new HashSet();
        while ( it.hasNext() )
        {
            set.add( ( (CheckstyleDTO) it.next() ).getName() );
        }
        form.setVersions( (String[]) set.toArray( new String[] {} ) );
    }

    /**
     * @param pTask checkstyle task
     * @return default ruleset
     */
    private String getDefaultRuleSet( TaskDTO pTask )
    {
        String result = "";
        for ( Iterator it = pTask.getParameters().iterator(); it.hasNext() && ( result.length() == 0 ); )
        {
            TaskParameterDTO param = (TaskParameterDTO) it.next();
            if ( param.getName().equals( "ruleset" ) )
            {
                result = param.getValue();
            }
        }
        return result;
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm)
     * @param form le formulaire
     * @return les objets transformés
     * @throws WTransformerException si erreur
     */
    public Object[] formToObj( WActionForm form )
        throws WTransformerException
    {
        throw new WTransformerException( "not yet implemented" );
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm,
     *      java.lang.Object[])
     * @param pForm le formulaire
     * @param pObject le tableau d'objet
     * @throws WTransformerException si erreur
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        CheckstyleForm form = (CheckstyleForm) pForm;
        MapParameterDTO projectParamsDTO = (MapParameterDTO) pObject[0];
        StringParameterDTO param = new StringParameterDTO();
        param.setValue( form.getSelectedRuleSet() );
        projectParamsDTO.getParameters().put( ParametersConstants.CHECKSTYLE_RULESET_NAME, param );
    }

}
