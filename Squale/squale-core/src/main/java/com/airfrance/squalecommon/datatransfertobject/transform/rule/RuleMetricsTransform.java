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
package com.airfrance.squalecommon.datatransfertobject.transform.rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.CriteriumRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO.MeasureExtractor;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBOVisitor;

/**
 * Visiteur d'obtention des mesures filles d'une règle qualité Les mesures filles d'une règle qualité dépendent du type
 * de mesure utilisée
 */
public class RuleMetricsTransform
    implements QualityRuleBOVisitor
{

    /** Extracteur de mesure */
    private MeasureExtractor mMeasureExtractor;

    /**
     * Constructeur
     * 
     * @param pMeasureExtractor extracteur de mesure
     */
    public RuleMetricsTransform( MeasureExtractor pMeasureExtractor )
    {
        mMeasureExtractor = pMeasureExtractor;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBOVisitor#visit(com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO,
     *      java.lang.Object)
     */
    public Object visit( FactorRuleBO pFactorRule, Object pArgument )
    {
        Collection treClasses = new ArrayList(); // liste classes BO
        Iterator iterator = pFactorRule.getCriteria().keySet().iterator();
        // On convertit chaque critere
        while ( iterator.hasNext() )
        {
            treClasses.add( CriteriumRuleTransform.bo2Dto( (CriteriumRuleBO) iterator.next() ) );
        }
        return treClasses;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBOVisitor#visit(com.airfrance.squalecommon.enterpriselayer.businessobject.rule.CriteriumRuleBO,
     *      java.lang.Object)
     */
    public Object visit( CriteriumRuleBO pCriteriumRule, Object pArgument )
    {
        Collection treClasses = new ArrayList(); // liste classes BO
        Iterator iterator = pCriteriumRule.getPractices().keySet().iterator();
        // On convertit chaque pratique
        while ( iterator.hasNext() )
        {
            treClasses.add( PracticeRuleTransform.bo2Dto( (PracticeRuleBO) iterator.next() ) );
        }
        return treClasses;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBOVisitor#visit(com.airfrance.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO,
     *      java.lang.Object)
     */
    public Object visit( PracticeRuleBO pPracticeRule, Object pArgument )
    {
        String[] usedMeasures;
        if ( pPracticeRule.getFormula() != null )
        {
            usedMeasures = mMeasureExtractor.getUsedMeasures( pPracticeRule.getFormula() );
        }
        else
        {
            usedMeasures = new String[0];
        }
        return Arrays.asList( usedMeasures );
    }
}