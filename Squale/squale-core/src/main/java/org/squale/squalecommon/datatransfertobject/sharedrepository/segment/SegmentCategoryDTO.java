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
package org.squale.squalecommon.datatransfertobject.sharedrepository.segment;

import java.util.List;

import org.squale.jraf.spi.dto.IDTO;

/**
 * This class is a DTO for the category of segment
 */
public class SegmentCategoryDTO
    implements IDTO, Comparable<SegmentCategoryDTO>
{

    /**
     * This field represents the beginning of the message key for a category
     */
    private static final String START_CATEGORY_REFERENCE_KEY = "reference.segmentcategory.";

    /**
     * Technical id
     */
    private long technicalId = -1;

    /**
     * The name of the category
     */
    private String categoryKeyName;

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
    private List<SegmentDTO> segmentList;

    /**
     * Constructor
     */
    public SegmentCategoryDTO()
    {

    }

    /**
     * Constructor
     * 
     * @param pCategoryName The category name
     * @param pIdentifier The category identifier
     * @param pType The category type (module or application)
     * @param pDeprecated The category deprecation state
     */
    public SegmentCategoryDTO( String pCategoryName, Long pIdentifier, String pType, boolean pDeprecated )
    {
        categoryKeyName = pCategoryName;
        identifier = pIdentifier;
        type = pType;
        deprecated = pDeprecated;
    }

    /**
     * Getter method for the attribute technical id
     * 
     * @return The technical id of the category
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
     * Getter method for the attribute categoryKeyName
     * 
     * @return The key name of the category
     */
    public String getCategoryKeyName()
    {
        return categoryKeyName;
    }

    /**
     * Setter method for the attribute categoryKeyName
     * 
     * @param pCategoryKeyName The key name of the category
     */
    public void setCategoryName( String pCategoryKeyName )
    {
        categoryKeyName = pCategoryKeyName;
    }

    /**
     * Getter method for the attribute identifier
     * 
     * @return The identifier of the category
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
     */
    public List<SegmentDTO> getSegmentList()
    {
        return segmentList;
    }

    /**
     * Getter method for the attribute segmentList
     * 
     * @param pSegmentList The new list of segment
     */
    public void setSegment( List<SegmentDTO> pSegmentList )
    {
        segmentList = pSegmentList;
    }

    /**
     * This method returns the full category key for internationalization
     * 
     * @return The internationalization key for the category
     */
    public String getFullKey()
    {
        return START_CATEGORY_REFERENCE_KEY + categoryKeyName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( categoryKeyName == null ) ? 0 : categoryKeyName.hashCode() );
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
        SegmentCategoryDTO other = (SegmentCategoryDTO) obj;
        if ( categoryKeyName == null )
        {
            if ( other.categoryKeyName != null )
            {
                return false;
            }
        }
        else if ( !categoryKeyName.equals( other.categoryKeyName ) )
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
        return categoryKeyName + " ( " + type + " ) ";
    }

    /**
     * {@inheritDoc}
     */
    public int compareTo( SegmentCategoryDTO o )
    {
        return this.categoryKeyName.compareToIgnoreCase( o.categoryKeyName );
    }

}
