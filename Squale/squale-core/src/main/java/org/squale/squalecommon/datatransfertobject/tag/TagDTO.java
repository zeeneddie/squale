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
public class TagDTO
    implements Serializable
{

    /**
     * UID
     */
    private static final long serialVersionUID = -4115112039963461614L;

    /**
     * ID en base du tag
     */
    private long mID = -1;

    /**
     * Nom complet du tag
     */
    private String mName;

    /**
     * la description rapide du tag
     */
    private String mDescription;

    /**
     * La catégorie correspondant au Tag
     */
    private TagCategoryDTO mTagCategoryDTO;

    /**
     * Constructeur par défaut
     * 
     */
    public TagDTO()
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
    public long getId()
    {
        return mID;
    }

    /**
     * Sets the value of the mID property.
     * 
     * @param pId the new value of the mId property
     */
    public void setId( long pId )
    {
        mID = pId;
    }

    /**
     * Permet de construire un TagDTO par rapport à un id et un tag. 
     * 
     * @param pId Id du composant
     * @param pName Nom du composant
     */
    public TagDTO( long pId, String pName )
    {
        setId( pId );
        setName( pName );
    }

    /**
     * Constructeur
     * 
     * @param pId l'id
     * @param pName le nom
     * @param pDescription la description du tag
     */
    public TagDTO( long pId, String pName, String pDescription )
    {
        mID = pId;
        mName = pName;
        mDescription = pDescription;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     * @return le hashcode
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
     * @return le nom raccourcis
     */
    public String getDescription()
    {
        return mDescription;
    }

    /**
     * Sets the value of the mDescription property.
     * @param pDescription le nom raccourcis
     */
    public void setDescription( String pDescription )
    {
        mDescription = pDescription;
    }
    
    /**
     * Access method for the mTagCategoryDTO property.
     * @return la catégorie du Tag
     */
    public TagCategoryDTO getTagCategoryDTO()
    {
        return mTagCategoryDTO;
    }

    /**
     * Sets the value of the mTagCategoryDTO property.
     * @param pTagCategoryDTO la catégorie du Tag
     */
    public void setTagCategoryDTO( TagCategoryDTO pTagCategoryDTO )
    {
        mTagCategoryDTO = pTagCategoryDTO;
    }
}
