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
//Source file: D:\\cc_views\\squale_v0_0_act_M400843\\squale\\src\\squaleCommon\\src\\com\\airfrance\\squalecommon\\enterpriselayer\\businessobject\\tag\\TagCategoryBO.java
package com.airfrance.squalecommon.enterpriselayer.businessobject.tag;

import java.util.Collection;

/**
 * Represent a Category of tags
 * 
 * @hibernate.class table="TagCategory" mutable="true" lazy="true"
 */
public class TagCategoryBO
{
    /**
     * Technical Id
     */
    protected long mId = -1;

    /**
     * Category name
     */
    private String mName;

    /**
     * Category description
     */
    private String mDescription;

    /**
     * List of the tags linked to this category
     */
    private Collection<TagBO> mTags;

    /**
     * Returns the value of the mId property
     * 
     * @return l'id de l'objet
     * @hibernate.id generator-class="native" type="long" column="TagCategoryId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="tagCategory_sequence"
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Sets the value of the mId property
     * 
     * @param pId l'id de l'objet
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * Constructor
     * 
     * @param pName Category name.
     */
    public TagCategoryBO( final String pName )
    {
        setName( pName );
    }

    /**
     * Default constructor.
     */
    public TagCategoryBO()
    {
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
     * Access method for the mDescription property.
     * 
     * @return the current value of the mDescription property
     * @hibernate.property name="description" update="true" insert="true" column="Description" type="string"
     *                     length="1024" not-null="true" unique="false"
     */
    public String getDescription()
    {
        return mDescription;
    }

    /**
     * Sets the value of the mDescription property.
     * 
     * @param pDescription the new value of the mDescription property
     */
    public void setDescription( String pDescription )
    {
        mDescription = pDescription;
    }

    /**
     * Access method for the mName property.
     * 
     * @return the current value of the mName property
     * @hibernate.property name="name" update="true" insert="true" column="Name" type="string" not-null="true"
     *                     unique="true"
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Access method for the mTags property.
     * 
     * @return the tags of the same category
     * @hibernate.bag table="Concerned_tags" lazy="true" cascade="none"
     * @hibernate.key column="TagCategory"
     * @hibernate.one-to-many class="com.airfrance.squalecommon.enterpriselayer.businessobject.tag.TagBO"
     */
    public Collection<TagBO> getTags()
    {
        return mTags;
    }

    /**
     * Sets the value of the mTags property.
     * 
     * @param pTags the collection of tags of the same category
     */
    public void setTags( Collection<TagBO> pTags )
    {
        mTags = pTags;
    }

}
