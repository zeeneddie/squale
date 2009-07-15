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
package org.squale.squalecommon.datatransfertobject.component.parameters;

/**
 */
public class StringParameterDTO
{

    /**
     * La valeur du paramètre
     */
    private String mValue;

    /**
     * Identifiant de l'objet
     */
    private long mId;

    /**
     * Constructeur
     * 
     * @param pValue la valeur du paramètre
     */
    public StringParameterDTO( String pValue )
    {
        mId = -1;
        mValue = pValue;
    }

    /**
     * Constructeur
     */
    public StringParameterDTO()
    {
        mId = -1;
        mValue = "";
    }

    /**
     * @return la valeur du paramètre
     */
    public String getValue()
    {
        return mValue;
    }

    /**
     * @param pValue la nouvelle valeur du paramètre
     */
    public void setValue( String pValue )
    {
        mValue = pValue;
    }

    /**
     * Access method for the mId property.
     * 
     * @return the current value of the mId property
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Sets the value of the mId property.
     * 
     * @param pId the new value of the mId property
     */
    public void setId( long pId )
    {
        mId = pId;
    }

}
