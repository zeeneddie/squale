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

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Données synthétiques d'un facteur
 */
public class QualityRuleForm
    extends RootForm
{
    /** séparateur des pondérartions */
    public static final String SEPARATOR = ", ";

    /** Nom du facteur */
    private String mName;

    /** Pondération */
    private String mPonderation;

    /** l'id */
    private long id;

    /**
     * @return nom
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @param pString nom
     */
    public void setName( String pString )
    {
        mName = pString;
    }

    /**
     * @return pondération
     */
    public String getPonderation()
    {
        return mPonderation;
    }

    /**
     * @param pString pondération
     */
    public void setPonderation( String pString )
    {
        mPonderation = pString;
    }

    /**
     * @return l'id
     */
    public long getId()
    {
        return id;
    }

    /**
     * @param newId la nouvelle valeur de l'id
     */
    public void setId( long newId )
    {
        id = newId;
    }

}
