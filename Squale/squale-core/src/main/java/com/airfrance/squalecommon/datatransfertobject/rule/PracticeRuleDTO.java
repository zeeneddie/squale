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
package com.airfrance.squalecommon.datatransfertobject.rule;

/**
 * DTO d'une pratique qualité
 */
public class PracticeRuleDTO
    extends QualityRuleDTO
{

    /** Indication d'une pratique de type violation de règle */
    private boolean mRuleChecking = false;

    /** Formule */
    private AbstractFormulaDTO mFormula;

    /** La fonction de pondération */
    private String mWeightingFunction;

    /** parfois on a seulement besoin du type de la formule (graph de répartition) */
    private String mFormulaType;

    /** l'effort à fournie pour la correction */
    private int mEffort;

    /**
     * @return formule
     */
    public AbstractFormulaDTO getFormula()
    {
        return mFormula;
    }

    /**
     * @param pFormulaDTO formule
     */
    public void setFormula( AbstractFormulaDTO pFormulaDTO )
    {
        mFormula = pFormulaDTO;
    }

    /**
     * @return indicateur de rulechecking
     */
    public boolean isRuleChecking()
    {
        return mRuleChecking;
    }

    /**
     * @param pRuleChecking indicateur de règle
     */
    public void setRuleChecking( boolean pRuleChecking )
    {
        mRuleChecking = pRuleChecking;
    }

    /**
     * @return la fonction de pondération
     */
    public String getWeightingFunction()
    {
        return mWeightingFunction;
    }

    /**
     * @param pWeightingFormula la fonction de pondération
     */
    public void setWeightingFunction( String pWeightingFormula )
    {
        mWeightingFunction = pWeightingFormula;
    }

    /**
     * @return le type de la formule
     */
    public String getFormulaType()
    {
        return mFormulaType;
    }

    /**
     * @param pFormulaType le type de la formule
     */
    public void setFormulaType( String pFormulaType )
    {
        mFormulaType = pFormulaType;
    }

    /**
     * @return l'effort de correction
     */
    public int getEffort()
    {
        return mEffort;
    }

    /**
     * @param pEffort l'effort de correction
     */
    public void setEffort( int pEffort )
    {
        mEffort = pEffort;
    }

}
