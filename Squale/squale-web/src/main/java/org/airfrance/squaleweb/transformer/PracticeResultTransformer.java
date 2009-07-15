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

import java.util.List;

import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.result.ResultsDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.FactorRuleDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.PracticeRuleDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.information.PracticeInformationForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ResultForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformation des résultats de pratique
 */
public class PracticeResultTransformer
    implements WITransformer
{

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[]) {@inheritDoc}
     */
    public WActionForm objToForm( Object[] object )
        throws WTransformerException
    {
        ResultForm result = new ResultForm();
        objToForm( object, result );
        return result;
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      com.airfrance.welcom.struts.bean.WActionForm) {@inheritDoc}
     */
    public void objToForm( Object[] object, WActionForm form )
        throws WTransformerException
    {
        // Récupération des paramètres
        int index = 0;
        ResultsDTO resultDTO = (ResultsDTO) object[index++];
        FactorRuleDTO pFactorParent = (FactorRuleDTO) object[index++];
        PracticeRuleDTO pPractice = (PracticeRuleDTO) object[index++];
        ComponentDTO pProject = (ComponentDTO) object[index++];
        List audits = (List) object[index++];
        PracticeInformationForm infoForm = (PracticeInformationForm) object[index++];
        ResultForm practiceResult = (ResultForm) form;

        practiceResult.setName( pPractice.getName() );
        practiceResult.setInfoForm( infoForm );
        practiceResult.setId( pPractice.getId() + "" );
        // Dans tous les cas on positionne le type de la formule
        practiceResult.setFormulaType( pPractice.getFormulaType() );
        if ( pFactorParent != null )
        {
            practiceResult.setTreParent( pFactorParent.getName() );
            practiceResult.setParentId( "" + pFactorParent.getId() );
        }
        // Récupération de la valeur de la pratique
        Float value = (Float) ( ( (List) resultDTO.getResultMap().get( pProject ) ).get( 0 ) );
        String valueStr = "";
        if ( null != value )
        {
            valueStr += value;
        }
        if ( null != value )
        {
            // Conversion de la valeur en texte
            practiceResult.setCurrentMark( valueStr );
            practiceResult.setIntRepartition( (Integer[]) resultDTO.getIntRepartitionPracticeMap().get( audits.get( 0 ) ) );
            practiceResult.setFloatRepartition( (Integer[]) resultDTO.getFloatRepartitionPracticeMap().get(
                                                                                                            audits.get( 0 ) ) );
        }
        // Calcul d'une tendance si un audit antérieur est présent
        if ( audits.size() > 1 )
        {
            String valueStr2 = "";
            Float value2 = ( (Float) ( ( (List) resultDTO.getResultMap().get( pProject ) ).get( 1 ) ) );
            if ( null != value2 )
            {
                valueStr2 += value2;
            }
            practiceResult.setPredecessorMark( valueStr2 );
        }
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm)
     *      {@inheritDoc}
     */
    public Object[] formToObj( WActionForm form )
        throws WTransformerException
    {
        throw new WTransformerException( "not yet implemented" );
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm,
     *      java.lang.Object[]) {@inheritDoc}
     */
    public void formToObj( WActionForm form, Object[] object )
        throws WTransformerException
    {
        throw new WTransformerException( "not yet implemented" );
    }

}
