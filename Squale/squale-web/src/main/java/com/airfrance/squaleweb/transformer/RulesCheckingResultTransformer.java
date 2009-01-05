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
/*
 * Créé le 22 juin 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squaleweb.transformer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.result.ResultsDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.FactorRuleDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.PracticeRuleDTO;
import com.airfrance.squalecommon.datatransfertobject.rulechecking.RuleCheckingDTO;
import com.airfrance.squalecommon.util.ConstantRulesChecking;
import com.airfrance.squaleweb.applicationlayer.formbean.information.PracticeInformationForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ResultRulesCheckingForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.RulesCheckingForm;
import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformation des résultats de RulesChecking
 */
public class RulesCheckingResultTransformer
    implements WITransformer
{
    /**
     * {@inheritDoc}
     * 
     * @param object {@inheritDoc}
     * @return le formulaire transformé
     */
    public WActionForm objToForm( Object[] object )
        throws WTransformerException
    {
        ResultRulesCheckingForm result = new ResultRulesCheckingForm();
        objToForm( object, result );
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @param object {@inheritDoc}
     * @param form le formulaire des résultats de RulesChecking
     */
    public void objToForm( Object[] object, WActionForm form )
        throws WTransformerException
    {
        // Récupération des paramètres
        int index = 0;
        // Index pour récupérer la locale
        final int langIndex = 6;
        ResultsDTO resultDTO = (ResultsDTO) object[index++];
        FactorRuleDTO pFactorParent = (FactorRuleDTO) object[index++];
        PracticeRuleDTO pPractice = (PracticeRuleDTO) object[index++];
        ComponentDTO pProject = (ComponentDTO) object[index++];
        List audits = (List) object[index++];
        // La locale pour traduire directement la sévérité pour le tri welcom
        Locale lang = (Locale) object[langIndex];
        PracticeInformationForm infoForm = (PracticeInformationForm) object[index++];
        ResultRulesCheckingForm practiceResult = (ResultRulesCheckingForm) form;

        practiceResult.setName( pPractice.getName() );
        practiceResult.setInfoForm( infoForm );
        practiceResult.setId( pPractice.getId() + "" );
        if ( pFactorParent != null )
        {
            practiceResult.setTreParent( pFactorParent.getName() );
            practiceResult.setParentId( "" + pFactorParent.getId() );
        }
        // Récupération de la valeur de la pratique
        Float value = (Float) ( ( (List) resultDTO.getResultMap().get( pProject ) ).get( 0 ) );
        if ( null != value )
        {
            // Conversion de la valeur en texte
            practiceResult.setCurrentMark( "" + value.floatValue() );
            List result =
                getRulesCheckingForm( (Map) resultDTO.getIntRepartitionPracticeMap().get( audits.get( 0 ) ),
                                      practiceResult, lang );
            practiceResult.setList( result );
        }
        // Calcul d'une tendance si un audit antérieur est présent
        if ( audits.size() > 1 )
        {
            Float value2 = (Float) ( ( (List) resultDTO.getResultMap().get( pProject ) ).get( 1 ) );
            practiceResult.setPredecessorMark( "" + value2 );
        }
    }

    /**
     * @param pRulesCheckingResults les résultats ruleschecking
     * @param pPracticeResult formbean
     * @param pLang la locale
     * @throws WTransformerException si erreur lors de la transformation
     * @return liste des les résultats ruleschecking sous forme de Form
     */
    private List getRulesCheckingForm( Map pRulesCheckingResults, ResultRulesCheckingForm pPracticeResult, Locale pLang )
        throws WTransformerException
    {

        RuleCheckingDTO dto = null;
        RulesCheckingForm rulesCheckingForm = null;
        Integer value = null;
        List result = new ArrayList( pRulesCheckingResults.size() );

        Set keys = pRulesCheckingResults.keySet();
        Iterator itSet = keys.iterator();

        int index = 0;
        while ( itSet.hasNext() )
        {
            dto = (RuleCheckingDTO) itSet.next();
            value = (Integer) pRulesCheckingResults.get( dto );
            rulesCheckingForm = new RulesCheckingForm();
            rulesCheckingForm.setNameRule( dto.getName() );
            rulesCheckingForm.setSeverity( dto.getSeverity() );
            rulesCheckingForm.setId( dto.getId() );
            rulesCheckingForm.setMeasureID( dto.getMeasureID() );
            if ( null != dto.getVersion() )
            {
                rulesCheckingForm.setVersion( dto.getVersion() );
            }
            rulesCheckingForm.setTransgressionsNumber( value.intValue() );
            doRepartition( rulesCheckingForm, pPracticeResult );
            // On traduit la sévérité pour pouvoir trier dans le tableau Welcom
            rulesCheckingForm.setSeverityLang( WebMessages.getString( pLang, "rulesChecking.rule.severity_"
                + dto.getSeverity() ) );
            result.add( rulesCheckingForm );
            index++;
        }

        return result;
    }

    /**
     * @param pRulesCheckingForm formbean de tous les resultat
     * @param pPracticeResult formbean de d'une transgression d'une règle checkstyle
     */
    private void doRepartition( RulesCheckingForm pRulesCheckingForm, ResultRulesCheckingForm pPracticeResult )
    {

        if ( pRulesCheckingForm.getSeverity().equals( ConstantRulesChecking.INFO_LABEL ) )
        {
            pPracticeResult.getIntRepartition()[ConstantRulesChecking.INFO_INT] +=
                pRulesCheckingForm.getTransgressionsNumber();

        }
        else
        {
            if ( pRulesCheckingForm.getSeverity().equals( ConstantRulesChecking.ERROR_LABEL ) )
            {
                pPracticeResult.getIntRepartition()[ConstantRulesChecking.ERROR_INT] +=
                    pRulesCheckingForm.getTransgressionsNumber();

            }
            else
            {
                if ( pRulesCheckingForm.getSeverity().equals( ConstantRulesChecking.WARNING_LABEL ) )
                {
                    pPracticeResult.getIntRepartition()[ConstantRulesChecking.WARNING_INT] +=
                        pRulesCheckingForm.getTransgressionsNumber();
                }
            }
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @param form {@inheritDoc}
     * @return le tableau des objets transformés
     */
    public Object[] formToObj( WActionForm form )
        throws WTransformerException
    {
        throw new WTransformerException( "not yet implemented" );
    }

    /**
     * {@inheritDoc}
     * 
     * @param form {@inheritDoc}
     * @param object {@inheritDoc}
     */
    public void formToObj( WActionForm form, Object[] object )
        throws WTransformerException
    {
        throw new WTransformerException( "not yet implemented" );
    }

}
