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
package org.squale.squaleweb.applicationlayer.formbean.reference;

import java.util.Date;

import org.squale.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Données du référentiel par grille
 */
public class ReferenceGridForm
    extends RootForm
{

    /** Date de mise à jour */
    private Date mUpdateDate;

    /** Date sous la forme de String */
    private String mFormattedDate = "";

    /** nom de la grille */
    private String mName = "";

    /**
     * @return date de mise à jour formatée
     */
    public String getFormattedDate()
    {
        return mFormattedDate;
    }

    /**
     * @param pString date de mise à jour formatée
     */
    public void setFormattedDate( String pString )
    {
        mFormattedDate = pString;
    }

    /**
     * Liste des références (referencesForm)
     */
    private ReferenceListForm mReferenceListForm = new ReferenceListForm();

    /**
     * @return la liste des références
     */
    public ReferenceListForm getReferenceListForm()
    {
        return mReferenceListForm;
    }

    /**
     * @param pReferenceListForm la liste des références
     */
    public void setReferenceListForm( ReferenceListForm pReferenceListForm )
    {
        mReferenceListForm = pReferenceListForm;
    }

    /**
     * @return date de mise à jour
     */
    public Date getUpdateDate()
    {
        return mUpdateDate;
    }

    /**
     * @param pString date de mise à jour
     */
    public void setUpdateDate( Date pString )
    {
        mUpdateDate = pString;
    }

    /**
     * @return le nom de la grille
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @param pName le nom de la grille
     */
    public void setName( String pName )
    {
        mName = pName;
    }

}
