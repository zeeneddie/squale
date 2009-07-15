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
package org.squale.squalecommon.datatransfertobject.transform.rule;

import java.util.Iterator;

import org.squale.squalecommon.datatransfertobject.rule.CriteriumRuleDTO;
import org.squale.squalecommon.datatransfertobject.rule.FactorRuleDTO;
import org.squale.squalecommon.datatransfertobject.rule.PracticeRuleDTO;
import org.squale.squalecommon.datatransfertobject.rule.QualityRuleDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.CriteriumRuleBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBOVisitor;

/**
 * Transformation d'une règle qualité
 */
public class QualityRuleTransform
    implements QualityRuleBOVisitor
{

    /** Le prefixe de la règle */
    private static String RULE_PREFIX = "rule.";

    /**
     * Conversion
     * 
     * @param pQualityRuleRule objet à convertir
     * @param pQualityRuleDTO résultat de la conversion
     */
    public static void bo2Dto( QualityRuleDTO pQualityRuleDTO, QualityRuleBO pQualityRuleRule )
    {
        pQualityRuleDTO.setId( pQualityRuleRule.getId() );
        pQualityRuleDTO.setName( RULE_PREFIX + pQualityRuleRule.getName() );
        pQualityRuleDTO.setHelpKey( RULE_PREFIX + pQualityRuleRule.getHelpKey() );
        pQualityRuleDTO.setId( pQualityRuleRule.getId() );
    }

    /**
     * Conversion d'une règle qualité
     * 
     * @param pQualityRuleRule règle qualité
     * @param pDeepTransform indique si les objets liés sont aussi transformés
     * @return règle qualité
     */
    public static QualityRuleDTO bo2Dto( QualityRuleBO pQualityRuleRule, boolean pDeepTransform )
    {
        QualityRuleDTO result =
            (QualityRuleDTO) pQualityRuleRule.accept( new QualityRuleTransform(), Boolean.valueOf( pDeepTransform ) );
        return result;
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBOVisitor#visit(org.squale.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO,
     *      java.lang.Object)
     */
    public Object visit( FactorRuleBO pFactorRule, Object pArgument )
    {
        FactorRuleDTO result = new FactorRuleDTO();
        bo2Dto( result, pFactorRule );
        if ( ( (Boolean) pArgument ).booleanValue() )
        {
            Iterator factorIt = pFactorRule.getCriteria().keySet().iterator();
            while ( factorIt.hasNext() )
            {
                CriteriumRuleBO currentCriteriumBO = (CriteriumRuleBO) factorIt.next();
                result.addCriterium( (CriteriumRuleDTO) ( currentCriteriumBO ).accept( this, pArgument ),
                                     (Float) pFactorRule.getCriteria().get( currentCriteriumBO ) );
            }
        }
        return result;
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBOVisitor#visit(org.squale.squalecommon.enterpriselayer.businessobject.rule.CriteriumRuleBO,
     *      java.lang.Object)
     */
    public Object visit( CriteriumRuleBO pCriteriumRule, Object pArgument )
    {
        CriteriumRuleDTO result = new CriteriumRuleDTO();
        bo2Dto( result, pCriteriumRule );
        if ( ( (Boolean) pArgument ).booleanValue() )
        {
            Iterator practiceIt = pCriteriumRule.getPractices().keySet().iterator();
            while ( practiceIt.hasNext() )
            {
                PracticeRuleBO currentPracticeBO = (PracticeRuleBO) practiceIt.next();
                result.addPractice( (PracticeRuleDTO) currentPracticeBO.accept( this, pArgument ),
                                    (Float) pCriteriumRule.getPractices().get( currentPracticeBO ) );
            }
        }
        return result;
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBOVisitor#visit(org.squale.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO,
     *      java.lang.Object)
     */
    public Object visit( PracticeRuleBO pPracticeRule, Object pArgument )
    {
        PracticeRuleDTO result = new PracticeRuleDTO();
        bo2Dto( result, pPracticeRule );
        // Traitement de la formule
        if ( pPracticeRule.getFormula() != null )
        {
            // Traitement du cas de RuleChecking
            if ( ( pPracticeRule.getFormula() != null )
                && pPracticeRule.getFormula().getComponentLevel().equals( "project" ) )
            { // TODO externaliser cette chaîne
                result.setRuleChecking( true );
            }
            // Conversion de la formule et de la fonction de pondération si demandé
            result.setFormulaType( pPracticeRule.getFormula().getType() );
            if ( ( (Boolean) pArgument ).booleanValue() )
            {
                result.setFormula( AbstractFormulaTransform.bo2Dto( pPracticeRule.getFormula() ) );
                result.setWeightingFunction( pPracticeRule.getWeightFunction() );
            }
        }
        // time limitation
        result.setTimeLimitation( pPracticeRule.getTimeLimitation() );
        // l'effort
        result.setEffort( pPracticeRule.getEffort() );
        return result;
    }

    /**
     * dto -> bo On ne peut modifier que les formules et l'effort
     * 
     * @param practiceBO le bo
     * @param pRuleDTO le dto
     */
    public static void dto2Bo( PracticeRuleBO practiceBO, PracticeRuleDTO pRuleDTO )
    {
        practiceBO.setEffort( pRuleDTO.getEffort() );
        if ( pRuleDTO.getWeightingFunction() != null )
        {
            practiceBO.setWeightFunction( pRuleDTO.getWeightingFunction() );
        }
        if ( pRuleDTO.getFormula() != null )
        {
            practiceBO.setFormula( AbstractFormulaTransform.dto2Bo( pRuleDTO.getFormula() ) );
        }
    }
}
