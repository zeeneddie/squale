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
package com.airfrance.squalecommon.enterpriselayer.businessobject.rule;

import com.airfrance.squalecommon.util.manualmark.TimeLimitationParser;

/**
 * Calculation rule of a practice
 * 
 * @author m400842
 * @hibernate.subclass discriminator-value="PracticeRule" lazy="false"
 */
public class PracticeRuleBO
    extends QualityRuleBO
{

    /** Default weighting function */
    public static final String WEIGHTING_FUNCTION = "lambda x:x";

    /** Default correction effort to do for improve the practice */
    public static final int EFFORT = 1;

    /**
     * Weighting function to use on the componeenty mark for the calculation of the global mark.
     */
    private String mWeightFunction = WEIGHTING_FUNCTION;

    /** Calculation formula */
    private AbstractFormulaBO mFormula;

    /** Correction effort to do for improve the practice */
    private int mEffort = EFFORT;

    /** Period of validity for a mark */
    private String timeLimitation; // = TIME_LIMITATION;

    /**
     * Default constructor
     */
    public PracticeRuleBO()
    {
        super();
    }

    /**
     * Getter method for the property formula
     * 
     * @return The formula
     * @hibernate.many-to-one column="Formula" cascade="all"
     *                        class="com.airfrance.squalecommon.enterpriselayer.businessobject.rule.AbstractFormulaBO"
     *                        outer-join="auto" update="true" insert="true"
     */
    public AbstractFormulaBO getFormula()
    {
        return mFormula;
    }

    /**
     * Setter method for the property formula
     * 
     * @param pFormula The new formula
     */
    public void setFormula( AbstractFormulaBO pFormula )
    {
        mFormula = pFormula;
    }

    /**
     * Getter method for the weighting function
     * 
     * @return The weighting function
     * @hibernate.property name="weightFunction" column="WeightFunction" type="string" unique="false" update="true"
     *                     insert="true"
     */
    public String getWeightFunction()
    {
        return mWeightFunction;
    }

    /**
     * Getter method for the property effort
     * 
     * @return The correction effort to do for improve the practice
     * @hibernate.property name="effort" column="effort" type="integer" length="10" unique="false" update="true"
     *                     insert="true"
     */
    public int getEffort()
    {
        return mEffort;
    }

    /**
     * Setter method for the weighting function
     * 
     * @param pFunction The new weighting function
     */
    public void setWeightFunction( String pFunction )
    {
        mWeightFunction = pFunction;
    }

    /**
     * Setter method for the property effort
     * 
     * @param pEffort The new correction effort to do for improve the practice
     */
    public void setEffort( int pEffort )
    {
        mEffort = pEffort;
    }

    /**
     * {@inheritDoc}
     */
    public Object accept( QualityRuleBOVisitor pVisitor, Object pArgument )
    {
        return pVisitor.visit( this, pArgument );
    }

    /**
     * Measure extractor
     */
    public interface MeasureExtractor
    {
        /**
         * Extraction of the measure of formula
         * 
         * @param pFormula the formula
         * @return The measures used in the formula
         */
        public String[] getUsedMeasures( AbstractFormulaBO pFormula );
    }

    /**
     * Getter methods for the timeLimitation attribute. This attribute is compose of 2 parts : The last character of the
     * String represent the unit of the period : 'D' for DAY, 'M' for MONTH, 'Y' for YEAR, 'A' like always for marks
     * which have no time limitation (In this case this 'A' is the only element of the String). The beginning of the
     * String is a number which represent the period of validity.
     * 
     * @hibernate.property name="timeLimitation" length="6" column="TimeLimitation" type="string" unique="false" update="true"
     *                     insert="true" 
     * @return The timeLimitation value
     */
    public String getTimeLimitation()
    {
        return timeLimitation;
    }

    /**
     * Setter method for the attribute timeLimitation. This attribute is compose of 2 parts : The last character of the
     * String represent the unit of the period : 'D' for DAY, 'M' for MONTH, 'Y' for YEAR, 'A' like always for marks
     * which have no time limitation (In this case this 'A' is the only element of the String). The beginning of the
     * String is a number which represent the period of validity.
     * 
     * @param mTimeLimitation The new timeLimitation attribute
     */
    public void setTimeLimitation( String mTimeLimitation )
    {
        timeLimitation = mTimeLimitation;
    }

    /**
     * This method take the period and the unit and set them in the good format in the timeLimitation property
     * 
     * @param period The duration of the validity
     * @param unit The unit of the period
     */
    public void setTimeLimitationFromXmlParse( String period, String unit )
    {
        timeLimitation = TimeLimitationParser.periodUnitToString( period, unit );
    }
}
