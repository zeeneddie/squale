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
package org.squale.sharedrepository.export;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Segment class used for the export
 */
@XStreamAlias( "segment" )
public class SegmentEx
{

    /**
     * The identifier of the segment
     */
    @XStreamAsAttribute
    private Long segmentId;

    /**
     * Default constructor
     */
    public SegmentEx()
    {

    }

    /**
     * Full constructor
     * 
     * @param pSegmentId The identifier of the segment
     */
    public SegmentEx( Long pSegmentId )
    {
        segmentId = pSegmentId;
    }

    /**
     * Getter method for the attribute segmentId
     * 
     * @return The segment id
     */
    public Long getSegmentId()
    {
        return segmentId;
    }

    /**
     * Getter method for the attribute segmentId
     * 
     * @param pSegmentId The new segment identifier
     */
    public void setSegmentId( Long pSegmentId )
    {
        segmentId = pSegmentId;
    }

}
