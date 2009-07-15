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
package org.squale.squalecommon.enterpriselayer.businessobject.rule;

/**
 * Formule simple Une formule simple s'exprime sous la forme d'une expression qui donne une note sous la forme d'un
 * entier ou d'un flottant. Cette note doit appartenir à l'intervalle 0..3, elle est ajustée si besoin lors de la
 * collecte des résultats
 * 
 * @hibernate.subclass discriminator-value="SimpleFormula"
 */
public class SimpleFormulaBO
    extends AbstractFormulaBO
{
    /** Formule de calcul */
    private String mFormula;

    /**
     * (non-Javadoc)
     * 
     * @see org.squale.squalecommon.enterpriselayer.businessobject.rule.practice.AbstractFormulaBO#accept(org.squale.squalecommon.enterpriselayer.businessobject.rule.practice.FormulaVisitor,
     *      java.lang.Object)
     */
    public Object accept( FormulaVisitor pVisitor, Object pArgument )
    {
        return pVisitor.visit( this, pArgument );
    }

    /**
     * @return formule
     * @hibernate.property name="formula" column="Formula" type="string" not-null="false" unique="false" length="4000" insert="true" update="true"
     */
    public String getFormula()
    {
        return mFormula;
    }

    /**
     * @param pFormula formule
     */
    public void setFormula( String pFormula )
    {
        mFormula = pFormula;
    }

    /**
     * @return le type de la formule
     */
    public String getType()
    {
        return TYPE_SIMPLE;
    }

}
