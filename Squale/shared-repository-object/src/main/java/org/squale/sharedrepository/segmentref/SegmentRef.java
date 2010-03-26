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
package org.squale.sharedrepository.segmentref;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Segment class used for the export
 */
@XStreamAlias( "segment-ref" )
public class SegmentRef
{

    /**
     * The key name of the segment
     */
    @XStreamAsAttribute
    private String keyName;

    /**
     * The list of label
     */
    @XStreamImplicit
    private List<Label> labelList;

    /**
     * The segment id
     */
    @XStreamAsAttribute
    private Long segmentId;

    /**
     * Indicate if the segment is deprecated
     */
    @XStreamAsAttribute
    private boolean deprecated;

    /**
     * Constructor
     * 
     * @param pKeyName The name of the segment
     * @param pSegmentId The segment identifier
     * @param pDeprecated The deprecation state of the segment
     */
    public SegmentRef( String pKeyName, Long pSegmentId, boolean pDeprecated )
    {
        keyName = pKeyName;
        labelList = new ArrayList<Label>();
        segmentId = pSegmentId;
        deprecated = pDeprecated;
    }

    /**
     * Getter method for the attribute keyName
     * 
     * @return The key key name of the segment
     */
    public String getKeyName()
    {
        return keyName;
    }

    /**
     * Setter for the attribute keyName
     * 
     * @param pKeyName The new key name of the segment
     */
    public void setKeyName( String pKeyName )
    {
        keyName = pKeyName;
    }

    /**
     * Getter method for the attribute labelList
     * 
     * @return the label list
     */
    public List<Label> getLabelList()
    {
        return labelList;
    }

    /**
     * Setter method for the attribute labelList
     * 
     * @param pLabelList The new list of label
     */
    public void setLabelList( List<Label> pLabelList )
    {
        this.labelList = pLabelList;
    }

    /**
     * This method adds a label to the list of labels
     * 
     * @param pLabel The label to add
     */
    public void addLabelt( Label pLabel )
    {
        labelList.add( pLabel );
    }

    /**
     * Getter for the attribute segmentId
     * 
     * @return The segment id
     */
    public Long getSegmentId()
    {
        return segmentId;
    }

    /**
     * Setter method for the attribute segmentId
     * 
     * @param pSegmentId The segment id
     */
    public void setSegmentId( Long pSegmentId )
    {
        segmentId = pSegmentId;
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
     * @param pDeprecated The new state of the segment
     */
    public void setDeprecated( boolean pDeprecated )
    {
        deprecated = pDeprecated;
    }

}
