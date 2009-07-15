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

import java.util.Date;

import com.airfrance.squaleweb.applicationlayer.formbean.ActionIdFormSelectable;

/**
 * Données synthétiques d'une grille qualité
 */
public class GridForm
    extends ActionIdFormSelectable
{
    /**
     * Nom de la grille
     */
    private String mName = "";

    /** Date de mise à jour */
    private Date mUpdateDate;

    /**
     * Constructeur par défaut.
     */
    public GridForm()
    {
    }

    /**
     * @return le nom.
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @param pName le nom.
     */
    public void setName( String pName )
    {
        mName = pName;
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
     * le text que peut remplir l'administrateur pour indiquer aux gestionnaires d'applications utilisant cette grille
     * les différentes modifications
     */
    private String mAdminText = "";

    /**
     * @return le texte
     */
    public String getAdminText()
    {
        return mAdminText;
    }

    /**
     * @param pText le nouveau texte
     */
    public void setAdminText( String pText )
    {
        mAdminText = pText;
    }

    /**
     * Juste pour l'affichage
     */
    private String mDisplayedName = "";

    /**
     * @param pDisplayedName le nom a afficher
     */
    public void setDisplayedName( String pDisplayedName )
    {
        mDisplayedName = pDisplayedName;
    }

    /**
     * @return le nom utilisé pour l'affichage
     */
    public String getDisplayedName()
    {
        return mDisplayedName;
    }

}
