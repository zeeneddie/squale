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
 * DTO d'une formule simple
 */
public class SimpleFormulaDTO
    extends AbstractFormulaDTO
{

    /** Formule de calcul */
    private String mFormula;

    /**
     * @return formule
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
     * @param pVisitor visiteur
     * @return objet
     */
    public Object accept( FormulaDTOVisitor pVisitor )
    {
        return pVisitor.visit( this );
    }

}
