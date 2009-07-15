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
 * Données synthétiques d'un facteur
 */
public class CriteriumRuleForm
    extends QualityRuleForm
{

    /** L'id de l'objet */
    private long mId;

    /** Pratiques */
    private PracticeListForm mPractices = new PracticeListForm();

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
     * @return pratiques
     */
    public PracticeListForm getPractices()
    {
        return mPractices;
    }

    /**
     * Ajout d'une pratique
     * 
     * @param pPractice pratique
     */
    public void addPractice( PracticeRuleForm pPractice )
    {
        mPractices.getList().add( pPractice );
    }

    /**
     * @param pPractices les pratiques
     */
    public void setPractices( PracticeListForm pPractices )
    {
        mPractices = pPractices;
    }
}
