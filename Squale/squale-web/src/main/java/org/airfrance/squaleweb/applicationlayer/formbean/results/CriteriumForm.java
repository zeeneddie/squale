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
package com.airfrance.squaleweb.applicationlayer.formbean.results;

import java.util.ArrayList;
import java.util.List;

/**
 * Contient les résultats de niveau critère
 * 
 * @author M400842
 */
public class CriteriumForm
    extends ResultForm
{

    /**
     * Liste des pratiques contenues
     */
    private List mPractices = new ArrayList();

    /**
     * @return la liste des pratiques
     */
    public List getPractices()
    {
        return mPractices;
    }

    /**
     * @param pPractices la liste des pratiques
     */
    public void setPractices( List pPractices )
    {
        mPractices = pPractices;
    }

    /**
     * Ajoute une pratique à la liste
     * 
     * @param pPractice la pratique à ajouter
     */
    public void addPractice( ResultForm pPractice )
    {
        mPractices.add( pPractice );
    }

}
