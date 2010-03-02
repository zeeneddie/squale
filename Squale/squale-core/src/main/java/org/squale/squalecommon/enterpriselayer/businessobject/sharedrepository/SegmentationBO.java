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

import org.squale.squalecommon.enterpriselayer.businessobject.tag.TagBO;

/**
 * This class represent a segmentation ( a list of segment ) 
 * 
 * @hibernate.class table="segmentation"
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
    private Set<TagBO> tagList = new HashSet<TagBO>();

    /**
     * List of stats
     */
    private Set<SharedRepoStatsBO> statsList = new HashSet<SharedRepoStatsBO>();

    /**
     * Getter method for the attribute segmentationId
     * 
     * @return The technical id
     * @hibernate.id generator-class="native" type="long" column="segmentationId" unsaved-value="-1" length="19"
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
     * @hibernate.set table="Tag_Segementation" lazy="false" cascade="none" inverse="false" sort="unsorted"
     * @hibernate.key column="segmentationId"
     * @hibernate.many-to-many class="org.squale.squalecommon.enterpriselayer.businessobject.tag.TagBO"
     *                         column="TagId" outer-join="auto"
     */
    public Set<TagBO> getTagList()
    {
        return tagList;
    }

    /**
     * Setter method for the attribute tagList
     * 
     * @param pTagList The new tag list
     */
    public void setTagList( Set<TagBO> pTagList )
    {
        tagList = pTagList;
    }

    /**
     * Getter method for the attribute statsList
     * 
     * @return The stat list
     * @hibernate.set table="shared_repo_stats" lazy="true" cascade="all" inverse="true" sort="unsorted"
     * @hibernate.key column="segmentationId"
     * @hibernate.one-to-many class="org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.StatsBO"
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
