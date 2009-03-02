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
//Source file: D:\\cc_views\\squale_v0_0_act_M400843\\squale\\src\\squaleCommon\\src\\com\\airfrance\\squalecommon\\enterpriselayer\\businessobject\\tag\\TagBO.java
package com.airfrance.squalecommon.enterpriselayer.businessobject.tag;

import java.util.Collection;

/**
 * Represent a Tag. this tag could be linked to an application or a project
 * 
 * @hibernate.class table="Tag" mutable="true" lazy="false"
 */
public class TagBO
{
    /**
     * Technical id
     */
    protected long mId = -1;
    
    /**
     * Tag name
     */
    private String mName;

    /**
     * Tag description
     */
    private String mDescription;

    /**
     * Category of the tag
     */
    private TagCategoryBO mTagCategoryBO;

    /**
     * List of components linked to this tag
     */
    protected Collection mComponents;
    
    /**
     * Returns the value of the mId property
     * 
     * @return l'id de l'objet
     * @hibernate.id generator-class="native" type="long" column="TagId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="tag_sequence"
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Sets the value of the mId property
     * @param pId l'id de l'objet
     */
    public void setId( long pId )
    {
        mId = pId;
    }
    
    /**
     * Default constructor
     */
    public TagBO()
    {
    }
    
    /**
     * Constructor
     * 
     * @param pName tag name.
     */
    public TagBO( final String pName )
    {
        setName( pName );
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
     * @return the current value of the mShortName property
     * @hibernate.property name="description" update="true" insert="true" column="Description" type="string" length="1024"
     *                     not-null="true" unique="false"
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
     * @hibernate.property name="name" update="true" insert="true" column="Name" type="string"
     *                     not-null="true" unique="true"
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Access method for the mTagCategoryBO property.
     * 
     * @return the current value of the mTagCategoryBO property
     * @hibernate.many-to-one column="TagCategory"
     *                     class="com.airfrance.squalecommon.enterpriselayer.businessobject.tag.TagCategoryBO"
     *                     update="true" insert="true" not-null="false" unique="false" outer-join="auto"
     */
    public TagCategoryBO getTagCategoryBO()
    {
        return mTagCategoryBO;
    }

    /**
     * Sets the value of the mTagCategoryBO property.
     * 
     * @param pTagCategoryBO the new value of the mTagCategoryBO property
     */
    public void setTagCategoryBO( TagCategoryBO pTagCategoryBO )
    {
        mTagCategoryBO = pTagCategoryBO;
    }

    /**
     * Access method for the mComponents property.
     * 
     * @return the current value of the mComponents property
     * @hibernate.set table="Tag_Component" lazy="true" cascade="none" inverse="true" sort="unsorted"
     * @hibernate.key column="TagId"
     * @hibernate.many-to-many class="com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO"
     *                         column="ComponentId" outer-join="auto"
     */
    public Collection getComponents()
    {
        return mComponents;
    }

    /**
     * Sets the value of the mComponents property.
     * 
     * @param pComponents the new value of the mComponents property
     */
    public void setComponents( Collection pComponents )
    {
        mComponents = pComponents;
    }
}
