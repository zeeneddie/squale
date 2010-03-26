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
package org.squale.squalecommon.datatransfertobject.tag;

import java.io.Serializable;

/**
 * Composant
 */
public class TagCategoryDTO
    implements Serializable
{

    /**
     * UID
     */
    private static final long serialVersionUID = 5416096006460314046L;

    /**
     * the ID of the tagCategory
     */
    private long mID = -1;

    /**
     * Name of the tag category
     */
    private String mName;

    /**
     * Short description of the TagCategory
     */
    private String mDescription;

    /**
     * default constructor
     */
    public TagCategoryDTO()
    {
    }

    /**
     * Access method for the mName property.
     * 
     * @return the current value of the mName property
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Sets the value of the mName property.
     * 
     * @param pName the new value of the mName property
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * Access method for the mID property.
     * 
     * @return the current value of the mID property
     */
    public long getID()
    {
        return mID;
    }

    /**
     * Sets the value of the mID property.
     * 
     * @param pID the new value of the mID property
     */
    public void setID( long pID )
    {
        mID = pID;
    }

    /**
     * Constructs a TagCategoryDTO from a given Id and name
     * 
     * @param pID ID of the component
     * @param pName Name of the component
     */
    public TagCategoryDTO( long pID, String pName )
    {
        setID( pID );
        setName( pName );
    }

    /**
     * Constructs a TagCategoryDTO from a given Id and name and description
     * 
     * @param pId ID of the component
     * @param pName Name of the component
     * @param pDescription short description of the component
     */
    public TagCategoryDTO( long pId, String pName, String pDescription )
    {
        mID = pId;
        mName = pName;
        mDescription = pDescription;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     * @return the hashcode
     */
    public int hashCode()
    {
        int result;
        if ( getName() != null )
        {
            result = getName().hashCode();
        }
        else
        {
            result = super.hashCode();
        }
        return result;
    }

    /**
     * Access method for the mDescription property.
     * 
     * @return the short description of the tag category
     */
    public String getDescription()
    {
        return mDescription;
    }

    /**
     * Sets the value of the mDescription property.
     * 
     * @param pDescription le nom raccourcis
     */
    public void setDescription( String pDescription )
    {
        mDescription = pDescription;
    }

}
