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

import org.squale.jraf.spi.dto.IDTO;

/**
 * This class is a DTO for the segment
 */
public class SegmentDTO
    implements IDTO, Comparable<SegmentDTO>
{
    
    /**
     * This field represents the beginning of the message key for a category
     */
    private static final String START_SEGMENT_REFERENCE_KEY = "reference.segment.";

    /**
     * Technical id
     */
    private long technicalId = -1;

    /**
     * The name of the segment
     */
    private String segmentKeyName;

    /**
     * The segment identifier
     */
    private Long identifier;

    /**
     * Indicate if the segment is deprecated
     */
    private boolean deprecated;

    /**
     * The category of the segment
     */
    private SegmentCategoryDTO segmentCategory;

    /**
     * Constructor
     */
    public SegmentDTO()
    {

    }

    /**
     * Constructor
     * 
     * @param pIdentifier the segmentIdentifier
     */
    public SegmentDTO(Long pIdentifier)
    {
        identifier = pIdentifier;
    }
    
    /**
     * Constructor
     * 
     * @param pSegmentName The segment name
     * @param pIdentifier The segment identifier
     * @param pDeprecated The segment deprecation state
     * @param pSegmentCategory The segment category
     */
    public SegmentDTO( String pSegmentName, Long pIdentifier, boolean pDeprecated, SegmentCategoryDTO pSegmentCategory )
    {
        segmentKeyName = pSegmentName;
        identifier = pIdentifier;
        deprecated = pDeprecated;
        segmentCategory = pSegmentCategory;
    }

    /**
     * Getter method for the attribute technicalId
     * 
     * @return The technical id of the segment
     */
    public long getTechnicalId()
    {
        return technicalId;
    }

    /**
     * Setter method for the attribute technicalId
     * 
     * @param pTechnicalId The new technical id of the segment
     */
    public void setTechnicalId( long pTechnicalId )
    {
        technicalId = pTechnicalId;
    }

    /**
     * Getter method for the attribute segmentKeyName
     * 
     * @return The key name of the segment
     */
    public String getSegmentKeyName()
    {
        return segmentKeyName;
    }

    /**
     * Setter method for the attribute segmentKeyName
     * 
     * @param pSegmentKeyName The new key name of the segment
     */
    public void setSegmentName( String pSegmentKeyName )
    {
        segmentKeyName = pSegmentKeyName;
    }

    /**
     * Getter method for the attribute segmentId
     * 
     * @return The identifier of the segment
     */
    public Long getIdentifier()
    {
        return identifier;
    }

    /**
     * Setter method for the attribute segmentId
     * 
     * @param pSegmentId The identifier of the segment
     */
    public void setIdentifier( Long pSegmentId )
    {
        identifier = pSegmentId;
    }

    /**
     * Getter method for the attribute deprecated
     * 
     * @return true if the segment is deprecated
     */
    public boolean isDeprecated()
    {
        return deprecated;
    }

    /**
     * Setter method for the attribute deprecated
     * 
     * @param pDeprecated The new deprecation state of the segment
     */
    public void setDeprecated( boolean pDeprecated )
    {
        deprecated = pDeprecated;
    }

    /**
     * Getter method for the attribute segmentCategory
     * 
     * @return The category of the segment
     */
    public SegmentCategoryDTO getSegmentCategory()
    {
        return segmentCategory;
    }

    /**
     * Setter method for the attribute segmentCategory
     * 
     * @param pSegmentCategory The new category of the segment
     */
    public void setSegmentCategory( SegmentCategoryDTO pSegmentCategory )
    {
        segmentCategory = pSegmentCategory;
    }
    
    /**
     * This method returns the full segment key for internationalization
     * 
     * @return The internationalization key for the category
     */
    public String getFullKey()
    {
        return START_SEGMENT_REFERENCE_KEY+segmentCategory.getCategoryKeyName()+"."+segmentKeyName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ( ( segmentCategory == null ) ? 0 : segmentCategory.hashCode() );
        result = prime * result + ( ( segmentKeyName == null ) ? 0 : segmentKeyName.hashCode() );
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
        if ( !super.equals( obj ) )
        {
            return false;
        }
        if ( getClass() != obj.getClass() )
        {
            return false;
        }
        SegmentDTO other = (SegmentDTO) obj;
        if ( segmentCategory == null )
        {
            if ( other.segmentCategory != null )
            {
                return false;
            }
        }
        else if ( !segmentCategory.equals( other.segmentCategory ) )
        {
            return false;
        }
        if ( segmentKeyName == null )
        {
            if ( other.segmentKeyName != null )
            {
                return false;
            }
        }
        else if ( !segmentKeyName.equals( other.segmentKeyName ) )
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
        return segmentKeyName + " - Category : " + segmentCategory.toString();
    }

    /**
     * {@inheritDoc}
     */
    public int compareTo( SegmentDTO o )
    {
        return this.segmentKeyName.compareToIgnoreCase( o.segmentKeyName);
    }

}
