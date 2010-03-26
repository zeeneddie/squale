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

import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;

/**
 * This class represents a BO for a segment
 * 
 * @hibernate.class table="Segment" lazy="true"
 */
public class SegmentBO
    implements Serializable
{

    /**
     * UID
     */
    private static final long serialVersionUID = -5527771838837480652L;

    /**
     * Technical id
     */
    private long technicalId = -1;

    /**
     * The name of the segment
     */
    private String segmentName;

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
    private SegmentCategoryBO segmentCategory;

    /**
     * The list of module linked to the segment
     */
    private Set<ProjectBO> moduleList;

    /**
     * Constructor
     */
    public SegmentBO()
    {
        moduleList = new HashSet<ProjectBO>();
    }

    /**
     * Getter method for the attribute technicalId
     * 
     * @return The technical id of the segment
     * @hibernate.id generator-class="native" type="long" column="SegmentId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="segment_sequence"
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
     * Getter method for the attribute segmentName
     * 
     * @return The name of the segment
     * @hibernate.property update="true" insert="true" column="Name" type="string" not-null="true"
     */
    public String getSegmentName()
    {
        return segmentName;
    }

    /**
     * Setter method for the attribute segmentName
     * 
     * @param pSegmentName The new name of the segment
     */
    public void setSegmentName( String pSegmentName )
    {
        segmentName = pSegmentName;
    }

    /**
     * Getter method for the attribute identifier
     * 
     * @return The identifier of the segment
     * @hibernate.property update="true" insert="true" column="Identifier" type="long" not-null="true"
     */
    public Long getIdentifier()
    {
        return identifier;
    }

    /**
     * Setter method for the attribute identifier
     * 
     * @param pIdentifier The identifier of the segment
     */
    public void setIdentifier( Long pIdentifier )
    {
        identifier = pIdentifier;
    }

    /**
     * Getter method for the attribute deprecated
     * 
     * @return true if the segment is deprecated
     * @hibernate.property update="true" insert="true" column="Deprecated" type="boolean" not-null="true"
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
     * @hibernate.many-to-one column="CategoryId"
     *                        class="org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.segment.SegmentCategoryBO"
     *                        update="true" insert="true" not-null="true" outer-join="auto"
     */
    public SegmentCategoryBO getSegmentCategory()
    {
        return segmentCategory;
    }

    /**
     * Setter method for the attribute segmentCategory
     * 
     * @param pSegmentCategory The new category of the segment
     */
    public void setSegmentCategory( SegmentCategoryBO pSegmentCategory )
    {
        segmentCategory = pSegmentCategory;
    }

    /**
     * Getter method for the attribute moduleList
     * 
     * @return The list of module linked to the segment
     * @hibernate.set table="Segment_Module" lazy="true" cascade="none" inverse="false" sort="unsorted"
     * @hibernate.key column="SegmentId" not-null="true"
     * @hibernate.many-to-many class="org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO"
     *                         column="ComponentId" outer-join="auto"
     */
    public Set<ProjectBO> getModuleList()
    {
        return moduleList;
    }

    /**
     * Setter method for the attribute moduleList
     * 
     * @param pModuleList The list of module linked to this segment
     */
    public void setModuleList( Set<ProjectBO> pModuleList )
    {
        moduleList = pModuleList;
    }

    /**
     * Add a module to the list of module linked to the segment
     * 
     * @param module The module to add
     */
    public void addModule( ProjectBO module )
    {
        moduleList.add( module );
    }

    

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( deprecated ? 1231 : 1237 );
        result = prime * result + ( ( identifier == null ) ? 0 : identifier.hashCode() );
        result = prime * result + ( ( moduleList == null ) ? 0 : moduleList.hashCode() );
        result = prime * result + ( ( segmentCategory == null ) ? 0 : segmentCategory.hashCode() );
        result = prime * result + ( ( segmentName == null ) ? 0 : segmentName.hashCode() );
        return result;
    }

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
        SegmentBO other = (SegmentBO) obj;
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
        if ( moduleList == null )
        {
            if ( other.moduleList != null )
            {
                return false;
            }
        }
        else if ( !moduleList.equals( other.moduleList ) )
        {
            return false;
        }
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
        if ( segmentName == null )
        {
            if ( other.segmentName != null )
            {
                return false;
            }
        }
        else if ( !segmentName.equals( other.segmentName ) )
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
        return segmentName + " - Category : " + segmentCategory.toString();
    }

}
