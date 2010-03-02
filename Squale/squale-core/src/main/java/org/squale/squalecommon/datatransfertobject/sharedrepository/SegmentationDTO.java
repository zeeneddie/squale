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
package org.squale.squalecommon.datatransfertobject.sharedrepository;

import java.util.HashSet;
import java.util.Set;

import org.squale.squalecommon.datatransfertobject.tag.TagDTO;

/**
 * This class represent a segmentation ( a list of segment )
 */
public class SegmentationDTO
{

    /**
     * ThecnicalId
     */
    private long segmentationId;

    /**
     * List of tags
     */
    private Set<TagDTO> tagList = new HashSet<TagDTO>();

    /**
     * List of stats
     */
    private Set<SharedRepoStatsDTO> statsList = new HashSet<SharedRepoStatsDTO>();

    /**
     * Getter method for the attribute segmentationId
     * 
     * @return The technical id
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
     */
    public Set<TagDTO> getTagList()
    {
        return tagList;
    }

    /**
     * Setter method for the attribute tagList
     * 
     * @param pTagList The new tag list
     */
    public void setTagList( Set<TagDTO> pTagList )
    {
        tagList = pTagList;
    }

    /**
     * Getter method for the attribute statsList
     * 
     * @return The stat list
     */
    public Set<SharedRepoStatsDTO> getStatsList()
    {
        return statsList;
    }

    /**
     * Setter method for the attribute statsList
     * 
     * @param pStatsList The new stat list
     */
    public void setStatsList( Set<SharedRepoStatsDTO> pStatsList )
    {
        statsList = pStatsList;
    }

}
