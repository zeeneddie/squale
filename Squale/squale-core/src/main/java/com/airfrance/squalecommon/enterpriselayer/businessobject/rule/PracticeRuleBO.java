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

/**
 * Règle de calcul d'une pratique
 * 
 * @author m400842
 * @hibernate.subclass discriminator-value="PracticeRule" lazy="false"
 */
public class PracticeRuleBO
    extends QualityRuleBO
{

    /** Fonction de pondération par défaut */
    public static final String WEIGHTING_FUNCTION = "lambda x:x";

    /** Effort de correction de la pratique par défaut */
    public static final int EFFORT = 1;

    /**
     * Fonction de pondération à appliquer aux notes des composants pour le calcul de la note globale.
     */
    private String mWeightFunction = WEIGHTING_FUNCTION;

    /** Formule de calcul associée */
    private AbstractFormulaBO mFormula;

    /** Effort de correction de la pratique */
    private int mEffort = EFFORT;

    /**
     * Constructeur par défaut
     * 
     * @roseuid 42C5220A0066
     */
    public PracticeRuleBO()
    {
        super();
    }

    /**
     * @return formula
     * @hibernate.many-to-one column="Formula" cascade="all"
     *                        class="com.airfrance.squalecommon.enterpriselayer.businessobject.rule.AbstractFormulaBO"
     *                        outer-join="auto" update="true" insert="true"
     */
    public AbstractFormulaBO getFormula()
    {
        return mFormula;
    }

    /**
     * @param pFormula formula
     */
    public void setFormula( AbstractFormulaBO pFormula )
    {
        mFormula = pFormula;
    }

    /**
     * @return la fonction de pondération
     * @hibernate.property name="weightFunction" column="WeightFunction" type="string" unique="false" update="true"
     *                     insert="true"
     */
    public String getWeightFunction()
    {
        return mWeightFunction;
    }

    /**
     * @return l'effort de correction de la pratique
     * @hibernate.property name="effort" column="effort" type="integer" length="10" unique="false" update="true"
     *                     insert="true"
     */
    public int getEffort()
    {
        return mEffort;
    }

    /**
     * @param pFunction la nouvelle fonction de pondération
     */
    public void setWeightFunction( String pFunction )
    {
        mWeightFunction = pFunction;
    }

    /**
     * @param pEffort l'effort de correction de la pratique
     */
    public void setEffort( int pEffort )
    {
        mEffort = pEffort;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBO#accept(com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBOVisitor,
     *      java.lang.Object)
     */
    public Object accept( QualityRuleBOVisitor pVisitor, Object pArgument )
    {
        return pVisitor.visit( this, pArgument );
    }

    /**
     * Extracteur de mesure
     */
    public interface MeasureExtractor
    {
        /**
         * Extraction des mesures d'une formule
         * 
         * @param pFormula formule
         * @return mesures impliquées dans la formule
         */
        public String[] getUsedMeasures( AbstractFormulaBO pFormula );
    }
}
