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
package org.squale.squaleweb.applicationlayer.formbean.component;

/**
 * Information on the rule
 */
public class PracticeRuleForm
    extends QualityRuleForm
{

    /** Id of the object */
    private long mId;

    /** Formula of the rule */
    private FormulaForm mFormula;

    /** The weighting function link to the formula */
    private String mWeightingFunction;

    /** The effort link to the rule */
    private int mEffort = 1;
    
    /** Unit for the time limitation*/
    private String unit;
    
    /** Period for the time limitation */
    private String period;

    /**
     * Getter method for the attribute id
     * 
     * @return id 
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Setter method for the attribute id
     * 
     * @param pId The new id 
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * Getter for the attribute formula  
     * 
     * @return The formula
     */
    public FormulaForm getFormula()
    {
        return mFormula;
    }

    /**
     * Setter for the attribute formula
     * 
     * @param pForm The new formula
     */
    public void setFormula( FormulaForm pForm )
    {
        mFormula = pForm;
    }

    /**
     * Getter method for the attribute mWeightingFunction
     * 
     * @return The weighting function mWeightingFunction
     */
    public String getWeightingFunction()
    {
        return mWeightingFunction;
    }

    /**
     * Setter method for the attribute  
     * 
     * @param pWeightingFunction la fonction de pondération associée
     */
    public void setWeightingFunction( String pWeightingFunction )
    {
        mWeightingFunction = pWeightingFunction;
    }

    /**
     * The getter for the attribute effort
     * 
     * @return The effort
     */
    public int getEffort()
    {
        return mEffort;
    }

    /**
     * Setter for attribute effort
     * 
     * @param pEffort The new effort
     */
    public void setEffort( int pEffort )
    {
        mEffort = pEffort;
    }

    /**
     * Getter for the attribute unit
     * 
     * @return The unit
     */
    public String getUnit()
    {
        return unit;
    }

    /**
     * Setter for the attribute unit
     * 
     * @param pUnit The new unit
     */
    public void setUnit( String pUnit )
    {
        unit = pUnit;
    }

    /**
     * Getter for the attribute  period
     * 
     * @return The period
     */
    public String getPeriod()
    {
        return period;
    }

    /**
     * Setter method for the attribute 
     * 
     * @param pPeriod The new period
     */
    public void setPeriod( String pPeriod )
    {
        period = pPeriod;
    }

}
