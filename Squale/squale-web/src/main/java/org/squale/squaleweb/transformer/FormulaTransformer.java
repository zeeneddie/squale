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
package com.airfrance.squaleweb.transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import com.airfrance.squalecommon.datatransfertobject.rule.AbstractFormulaDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.ConditionFormulaDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.SimpleFormulaDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.component.FormulaForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformation d'une formule
 */
public class FormulaTransformer
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
        FormulaForm form = new FormulaForm();
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
        AbstractFormulaDTO formulaDTO = (AbstractFormulaDTO) pObject[0];
        FormulaForm form = (FormulaForm) pForm;
        form.setId( formulaDTO.getId() );
        form.setComponentLevel( formulaDTO.getComponentLevel() );
        String measures = "";
        Iterator measuresIt = formulaDTO.getMeasureKinds().iterator();
        while ( measuresIt.hasNext() )
        {
            if ( measures.length() > 0 )
            {
                measures += ", ";
            }
            measures += (String) measuresIt.next();
        }
        form.setMeasures( measures );
        form.setTriggerCondition( formulaDTO.getTriggerCondition() );
        String[] conditions;
        if ( formulaDTO instanceof SimpleFormulaDTO )
        {
            conditions = new String[] { ( (SimpleFormulaDTO) formulaDTO ).getFormula() };
        }
        else
        {
            conditions = (String[]) ( (ConditionFormulaDTO) formulaDTO ).getMarkConditions().toArray( new String[] {} );
        }
        form.setConditions( conditions );
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        FormulaForm form = (FormulaForm) pForm;
        AbstractFormulaDTO dto = new SimpleFormulaDTO();
        if ( form.getConditions().length > 1 )
        {
            dto = new ConditionFormulaDTO();
        }
        formToObj( pForm, new Object[] { dto } );
        return new Object[] { dto };
    }

    /**
     * @param pObject l'objet à remplir
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        FormulaForm form = (FormulaForm) pForm;
        AbstractFormulaDTO dto = (AbstractFormulaDTO) pObject[0];
        dto.setId( form.getId() );
        dto.setComponentLevel( form.getComponentLevel() );
        dto.setTriggerCondition( form.getTriggerCondition() );
        String measures = form.getMeasures();
        // on découpe les types de mesures qui sont sous la forme d'une String dans le form
        // les valeurs sont séparés par une ","
        StringTokenizer st = new StringTokenizer( measures, "," );
        ArrayList measuresList = new ArrayList();
        while ( st.hasMoreTokens() )
        {
            measuresList.add( st.nextToken().trim() );
        }
        dto.setMeasureKinds( measuresList );
        if ( dto instanceof SimpleFormulaDTO )
        {
            ( (SimpleFormulaDTO) dto ).setFormula( form.getConditions()[0] );
        }
        else
        {
            List conditions = Arrays.asList( form.getConditions() );
            ( (ConditionFormulaDTO) dto ).setMarkConditions( conditions );
        }
    }

}
