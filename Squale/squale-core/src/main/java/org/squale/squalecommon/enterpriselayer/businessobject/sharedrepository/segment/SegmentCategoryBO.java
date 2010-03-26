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
package org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.segment;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a BO for the category of a segment
 * 
 * @hibernate.class table="SegmentCategory"
 */
public class SegmentCategoryBO
    implements Serializable
{
    /**
     * UID
     */
    private static final long serialVersionUID = -458268721828655174L;
    
    /**
     * Technical id
     */
    private long technicalId = -1;

    /**
     * The name of the category
     */
    private String categoryName;

    /**
     * The category identifier
     */
    private Long identifier;

    /**
     * The type of category (application or module)
     */
    private String type;

    /**
     * Indicate if the category is deprecated
     */
    private boolean deprecated;

    /**
     * The list of segment
     */
    private Set<SegmentBO> segmentList;

    /**
     * Constructor
     */
    public SegmentCategoryBO()
    {
        segmentList = new HashSet<SegmentBO>();
    }

    /**
     * Getter method for the attribute technical id
     * 
     * @return The technical id of the category
     * @hibernate.id generator-class="native" type="long" column="CategoryId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="segmentCategory_sequence"
     */
    public long getTechnicalId()
    {
        return technicalId;
    }

    /**
     * Setter method for the attribute technicalId
     * 
     * @param pTechnicalId The technical id
     */
    public void setTechnicalId( long pTechnicalId )
    {
        technicalId = pTechnicalId;
    }

    /**
     * Getter method for the attribute categoryName
     * 
     * @return The name of the category
     * @hibernate.property update="true" insert="true" column="Name" type="string" not-null="true" unique="true"
     */
    public String getCategoryName()
    {
        return categoryName;
    }

    /**
     * Setter method for the attribute categoryName
     * 
     * @param pCategoryName The name of the category
     */
    public void setCategoryName( String pCategoryName )
    {
        categoryName = pCategoryName;
    }

    /**
     * Getter method for the attribute identifier
     * 
     * @return The identifier of the category
     * @hibernate.property update="true" insert="true" column="Identifier" type="long" not-null="true" unique="true"
     */
    public Long getIdentifier()
    {
        return identifier;
    }

    /**
     * Setter method for the attribute identifier
     * 
     * @param pIdentifier The new category identifier
     */
    public void setIdentifier( Long pIdentifier )
    {
        identifier = pIdentifier;
    }

    /**
     * Getter method for the attribute type
     * 
     * @return The type of the category
     * @hibernate.property update="true" insert="true" column="Type" type="string" not-null="true"
     */
    public String getType()
    {
        return type;
    }

    /**
     * Setter method for the attribute type (application or module)
     * 
     * @param pType The category type (application or module)
     */
    public void setType( String pType )
    {
        type = pType;
    }

    /**
     * Getter method for the attribute deprecated
     * 
     * @return true if the category is deprecated
     * @hibernate.property update="true" insert="true" column="Deprecated" type="boolean" not-null="true"
     */
    public boolean isDeprecated()
    {
        return deprecated;
    }

    /**
     * Setter method for the attribute deprecated
     * 
     * @param pDeprecated The new deprecation state
     */
    public void setDeprecated( boolean pDeprecated )
    {
        deprecated = pDeprecated;
    }

    /**
     * Getter method for the attribute segmentList
     * 
     * @return The list of segment linked to this category
     * @hibernate.set lazy="true" inverse="true"
     * @hibernate.key column="CategoryId"
     * @hibernate.one-to-many 
     *                        class="org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.segment.SegmentBO"
     */
    public Set<SegmentBO> getSegmentList()
    {
        return segmentList;
    }

    /**
     * Setter method for the attribute segmentList
     * 
     * @param pSegmentList The new list of segment linked to the category
     */
    public void setSegmentList( Set<SegmentBO> pSegmentList )
    {
        segmentList = pSegmentList;
    }

    /**
     * This method add a segment to the category
     * 
     * @param pSegment The segment to add
     */
    public void addSegment( SegmentBO pSegment )
    {
        segmentList.add( pSegment );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( categoryName == null ) ? 0 : categoryName.hashCode() );
        result = prime * result + ( deprecated ? 1231 : 1237 );
        result = prime * result + ( ( identifier == null ) ? 0 : identifier.hashCode() );
        result = prime * result + ( ( type == null ) ? 0 : type.hashCode() );
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
        {
            return true;
        }
        if ( obj == null )
        {
            return false;
        }
        if ( getClass() != obj.getClass() )
        {
            return false;
        }
        SegmentCategoryBO other = (SegmentCategoryBO) obj;
        if ( categoryName == null )
        {
            if ( other.categoryName != null )
            {
                return false;
            }
        }
        else if ( !categoryName.equals( other.categoryName ) )
        {
            return false;
        }
        if ( deprecated != other.deprecated )
        {
            return false;
        }
        if ( identifier == null )
        {
            if ( other.identifier != null )
            {
                return false;
            }
        }
        else if ( !identifier.equals( other.identifier ) )
        {
            return false;
        }
        if ( type == null )
        {
            if ( other.type != null )
            {
                return false;
            }
        }
        else if ( !type.equals( other.type ) )
        {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return categoryName + " ( " + type + " ) ";
    }

}
