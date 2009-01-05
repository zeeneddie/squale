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
package com.airfrance.squaleweb.applicationlayer.formbean.component;

/**
 * Données synthétiques d'une formule
 */
public class PracticeRuleForm
    extends QualityRuleForm
{

    /** L'id de l'objet */
    private long mId;

    /** Formule */
    private FormulaForm mFormula;

    /** La fonction de pondération associée */
    private String mWeightingFunction;

    /** L'effort nécessaire à la correction */
    private int mEffort = 1;

    /**
     * @return id
     */
    public long getId()
    {
        return mId;
    }

    /**
     * @param pId id
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * @return formule
     */
    public FormulaForm getFormula()
    {
        return mFormula;
    }

    /**
     * @param pForm formule
     */
    public void setFormula( FormulaForm pForm )
    {
        mFormula = pForm;
    }

    /**
     * @return la fonction de pondération associée
     */
    public String getWeightingFunction()
    {
        return mWeightingFunction;
    }

    /**
     * @param pWeightingFunction la fonction de pondération associée
     */
    public void setWeightingFunction( String pWeightingFunction )
    {
        mWeightingFunction = pWeightingFunction;
    }

    /**
     * @return l'effort
     */
    public int getEffort()
    {
        return mEffort;
    }

    /**
     * @param pEffort l'effort
     */
    public void setEffort( int pEffort )
    {
        mEffort = pEffort;
    }

}
