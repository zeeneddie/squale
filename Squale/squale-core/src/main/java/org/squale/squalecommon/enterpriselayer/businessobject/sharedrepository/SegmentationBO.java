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
package org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository;

import java.util.HashSet;
import java.util.Set;

import org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.segment.SegmentBO;

/**
 * This class represent a segmentation ( a list of segment )
 * 
 * @hibernate.class table="Segmentation"
 */
public class SegmentationBO
{

    /**
     * ThecnicalId
     */
    private long segmentationId;

    /**
     * List of tags
     */
    private Set<SegmentBO> segmentList = new HashSet<SegmentBO>();

    /**
     * List of stats
     */
    private Set<SharedRepoStatsBO> statsList = new HashSet<SharedRepoStatsBO>();

    /**
     * Getter method for the attribute segmentationId
     * 
     * @return The technical id
     * @hibernate.id generator-class="native" type="long" column="SegmentationId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="segmentation_sequence"
     */
    public long getSegmentationId()
    {
        return segmentationId;
    }

    /**
     * Setter method for the attribute segmentationId
     * 
     * @param pSegmentationId The new technical id
     */
    public void setSegmentationId( long pSegmentationId )
    {
        segmentationId = pSegmentationId;
    }

    /**
     * Getter method for the attribute tagList
     * 
     * @return The tag list
     * @hibernate.set table="Segment_Segmentation" lazy="true" cascade="none" inverse="false" sort="unsorted"
     * @hibernate.key column="SegmentationId"
     * @hibernate.many-to-many 
     *                         class="org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.segment.SegmentBO"
     *                         column="SegmentId" outer-join="auto"
     */
    public Set<SegmentBO> getSegmentList()
    {
        return segmentList;
    }

    /**
     * Setter method for the attribute segmentList
     * 
     * @param pSegmentList the new list of segment linked to this segmentation
     */
    public void setSegmentList( Set<SegmentBO> pSegmentList )
    {
        segmentList = pSegmentList;
    }

    /**
     * Setter method for the attribute tagList
     * 
     * @param pSegmentList The new tag list
     */
    /*public void setSegementList( Set<SegmentBO> pSegmentList )
    {
        segmentList = pSegmentList;
    }*/
    
    /**
     * This method adds a segment to the list of segment
     * 
     * @param segment The segment to add
     */
    public void addSegment( SegmentBO segment )
    {
        segmentList.add( segment );
    }

    /**
     * Getter method for the attribute statsList
     * 
     * @return The stat list
     * @hibernate.set table="SharedRepoStats" lazy="true" cascade="all" inverse="true" sort="unsorted"
     * @hibernate.key column="SegmentationId"
     * @hibernate.one-to-many 
     *                        class="org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.SharedRepoStatsBO"
     */
    public Set<SharedRepoStatsBO> getStatsList()
    {
        return statsList;
    }

    /**
     * Setter method for the attribute statsList
     * 
     * @param pStatsList The new stat list
     */
    public void setStatsList( Set<SharedRepoStatsBO> pStatsList )
    {
        statsList = pStatsList;
    }

}
