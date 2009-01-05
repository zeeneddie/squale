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

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Règle de calcul d'un critère
 * 
 * @hibernate.subclass discriminator-value="CriteriumRule" lazy="false"
 */
public class CriteriumRuleBO
    extends QualityRuleBO
{

    /**
     * Liste des pratiques avec leur poids associé permettant le calcul du critère
     */
    protected SortedMap mPractices;

    /**
     * Constructeur par défaut
     */
    public CriteriumRuleBO()
    {
        super();
        mPractices = new TreeMap();
    }

    /**
     * Access method for the mPractices property.
     * 
     * @return the current value of the mPractices property
     * @hibernate.map table="CriteriumPractice_Rule" lazy="true" cascade="all" sort="natural"
     * @hibernate.index-many-to-many column="PracticeRuleId"
     *                               class="com.airfrance.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO"
     * @hibernate.key column="CriteriumRuleId"
     * @hibernate.element column="Weight" type="float" not-null="true" unique="false"
     */
    public SortedMap getPractices()
    {
        return mPractices;
    }

    /**
     * Sets the value of the mPractices property.
     * 
     * @param pPractices the new value of the mPractices property
     */
    public void setPractices( SortedMap pPractices )
    {
        mPractices = pPractices;
    }

    /**
     * ajoute une regle de pratique
     * 
     * @param pPractice une regle de pratique
     * @param pWeight le poids de la pratique
     */
    public void addPractice( PracticeRuleBO pPractice, Float pWeight )
    {
        if ( null == mPractices )
        {
            mPractices = new TreeMap();
        }
        mPractices.put( pPractice, pWeight );
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
}
