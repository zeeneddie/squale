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
package org.squale.squalecommon.enterpriselayer.businessobject.result.rulechecking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.squale.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.MeasureBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.MetricBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.RuleBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.RuleSetBO;
import org.squale.squalecommon.util.ConstantRulesChecking;

/**
 * Résultat de transgression de règle
 * 
 * @hibernate.subclass discriminator-value="RulesCheckingTransgression"
 */

public class RuleCheckingTransgressionBO
    extends MeasureBO
{

    /** Le nombre maximun d'items par règle */
    public static final int MAX_DETAILS = 100;

    /**
     * Version de la Règle
     */
    protected RuleSetBO mRuleSet;

    /** Détails des transgression */
    private Collection mDetails;

    /**
     * Constructeur par défaut
     */
    public RuleCheckingTransgressionBO()
    {
        super();
        mDetails = new ArrayList();
    }

    /**
     * Rajoute une metric avec sa valeur
     * 
     * @param pMetric la metrique
     */
    public void putMetric( MetricBO pMetric )
    {
        mMetrics.put( pMetric.getName(), pMetric );
    }

    /**
     * @return le total des transgressions de niveau Error.
     */
    public int getTotalErrorNumber()
    {
        return totalNumberForLevel( ConstantRulesChecking.ERROR_LABEL );

    }

    /**
     * @return le total des transgressions de niveau Warning.
     */
    public int getTotalWarningNumber()
    {
        return totalNumberForLevel( ConstantRulesChecking.WARNING_LABEL );

    }

    /**
     * @return le total des transgressions de niveau Info.
     */
    public int getTotalInfoNumber()
    {
        return totalNumberForLevel( ConstantRulesChecking.INFO_LABEL );

    }

    /**
     * @param pLevel le niveau de transgression
     * @return le nombre total des transgressions de niveau <code>pLevel</code>
     */
    protected int totalNumberForLevel( String pLevel )
    {
        int value = 0;
        IntegerMetricBO metric = null;
        String metricName = null;
        Collection metricCol = mMetrics.values();
        Iterator itMetricCol = metricCol.iterator();
        while ( itMetricCol.hasNext() )
        {
            metric = (IntegerMetricBO) itMetricCol.next();
            metricName = metric.getName();
            if ( pLevel.equals( getSeverityOfRule( metricName ) ) )
            {
                value += ( (Integer) metric.getValue() ).intValue();
            }
        }
        return value;
    }

    /**
     * @param pCode code de la regle
     * @return le total des transgressions par categorie pCode, de niveau Error.
     */
    public int getTotalErrorNumberForCategory( String pCode )
    {
        return totalNumberForCategoryForLevel( pCode.trim(), ConstantRulesChecking.ERROR_LABEL );
    }

    /**
     * @param pCategory Catégorie de la regle
     * @param pTransgressionLevel niveau de la regle
     * @return le nombre total de transgression par categorie et par niveau
     */
    private int totalNumberForCategoryForLevel( String pCategory, String pTransgressionLevel )
    {
        int value = 0;
        IntegerMetricBO metric = null;
        String metricName = null;

        Collection metricCol = mMetrics.values();
        Iterator itMetricCol = metricCol.iterator();
        while ( itMetricCol.hasNext() )
        {
            metric = (IntegerMetricBO) itMetricCol.next();
            metricName = metric.getName();
            if ( pCategory.equals( getRuleCategory( metricName ) )
                && pTransgressionLevel.equals( getSeverityOfRule( metricName ) ) )
            {
                value += ( (Integer) metric.getValue() ).intValue();
            }
        }
        return value;
    }

    /**
     * @param pCategory la catégorie de la règle
     * @return true si le ruleset contient au moins une règle de cette catégorie
     */
    public boolean hasRule( String pCategory )
    {
        boolean has = false;
        Iterator itRuleCol = getRuleSet().getRules().values().iterator();
        while ( !has && itRuleCol.hasNext() )
        {
            RuleBO rule = (RuleBO) itRuleCol.next();
            has = rule.getCategory().equals( pCategory );
        }
        return has;
    }

    /**
     * @param pCode code de la règle
     * @return catégorie de la règle ou chaîne vide
     */
    private String getRuleCategory( String pCode )
    {
        String result = "";
        Collection ruleCol = getRuleSet().getRules().values();
        Iterator itRuleCol = ruleCol.iterator();
        while ( ( result.length() == 0 ) && ( itRuleCol.hasNext() ) )
        {
            RuleBO rule = (RuleBO) itRuleCol.next();
            if ( rule.getCode().equals( pCode ) )
            {
                result = rule.getCategory();
            }
        }
        return result;
    }

    /**
     * @param pCode Code de la regle
     * @return le niveau d'une regle(rule)
     */
    private String getSeverityOfRule( String pCode )
    {
        String severity = null;
        RuleBO rule = null;
        boolean isFind = false;
        Collection ruleCol = getRuleSet().getRules().values();
        Iterator itRuleCol = ruleCol.iterator();
        while ( !( isFind ) && ( itRuleCol.hasNext() ) )
        {
            rule = (RuleBO) itRuleCol.next();
            if ( rule.getCode().equals( pCode ) )
            {
                severity = rule.getSeverity();
                isFind = true;
            }
        }
        return severity;
    }

    /**
     * @param pCode code de la regle
     * @return le total des transgressions par categorie pCode, de niveau Warning.
     */

    public int getTotalWarningNumberForCategory( String pCode )
    {

        return totalNumberForCategoryForLevel( pCode.trim(), ConstantRulesChecking.WARNING_LABEL );
    }

    /**
     * @param pCode code de la regle
     * @return le total des transgressions par categorie pCode, de niveau Info.
     */

    public int getTotalInfoNumberForCategory( String pCode )
    {

        return totalNumberForCategoryForLevel( pCode.trim(), ConstantRulesChecking.INFO_LABEL );
    }

    /**
     * Access method for the mVersion property.
     * 
     * @return the current Version
     * @hibernate.many-to-one class="org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.RuleSetBO"
     *                        column="RuleSetId" cascade="save-update" not-null="false" outer-join="auto" update="true"
     *                        insert="true" 
     */
    public RuleSetBO getRuleSet()
    {
        return mRuleSet;
    }

    /**
     * Sets the value of the mVersion property.
     * 
     * @param pRuleSet ruleset
     */
    public void setRuleSet( RuleSetBO pRuleSet )
    {
        mRuleSet = pRuleSet;
    }

    /**
     * @return les détails des transgressions
     * @hibernate.bag table="RuleCheckingTransgressionItem" lazy="true" cascade="all"
     * @hibernate.key column="MeasureId"
     * @hibernate.one-to-many class="org.squale.squalecommon.enterpriselayer.businessobject.result.rulechecking.RuleCheckingTransgressionItemBO"
     */
    public Collection getDetails()
    {
        return mDetails;
    }

    /**
     * Modifie mDetails
     * 
     * @param pItems les détails des transgressions
     */
    public void setDetails( Collection pItems )
    {
        mDetails = pItems;
    }

}
