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
package com.airfrance.squaleweb.applicationlayer.formbean.creation;

import java.util.Date;

import com.airfrance.squaleweb.applicationlayer.formbean.component.FactorListForm;
import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Configuration d'une grille qualité
 */
public class GridConfigForm
    extends RootForm
{
    /**
     * Id de la grille
     */
    private long mId;

    /**
     * Nom de la grille
     */
    private String mName = "";

    /** Date de mise à jour */
    private Date mUpdateDate;

    /**
     * @return l'id.
     */
    public long getId()
    {
        return mId;
    }

    /**
     * @return le nom.
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @return date de mise à jour
     */
    public Date getUpdateDate()
    {
        return mUpdateDate;
    }

    /**
     * @param pId l'id.
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * @param pName le nom.
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * @param pString date de mise à jour
     */
    public void setUpdateDate( Date pString )
    {
        mUpdateDate = pString;
    }

    /** Facteurs associés */
    private FactorListForm mFactors;

    /**
     * @return facteurs
     */
    public FactorListForm getFactors()
    {
        return mFactors;
    }

    /**
     * @param pFactors facteurs
     */
    public void setFactors( FactorListForm pFactors )
    {
        mFactors = pFactors;
    }
}
