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
 * Visiteur des règles qualité Ce design pattern permet d'externaliser des traitements sur les facteurs, critères et
 * pratiques
 */
public interface QualityRuleBOVisitor
{
    /**
     * @param pFactorRule facteur
     * @param pArgument argument
     * @return objet
     */
    public Object visit( FactorRuleBO pFactorRule, Object pArgument );

    /**
     * @param pCriteriumRule critère
     * @param pArgument argument
     * @return objet
     */
    public Object visit( CriteriumRuleBO pCriteriumRule, Object pArgument );

    /**
     * @param pPracticeRule pratique
     * @param pArgument argument
     * @return objet
     */
    public Object visit( PracticeRuleBO pPracticeRule, Object pArgument );
}
