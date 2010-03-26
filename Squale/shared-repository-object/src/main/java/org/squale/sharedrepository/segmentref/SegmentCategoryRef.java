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
 * This class represent a segment category
 */
@XStreamAlias( "segment-category-ref" )
public class SegmentCategoryRef
{

    /**
     * The key name of the category
     */
    @XStreamAsAttribute
    private String keyName;

    /**
     * The list of label
     */
    @XStreamImplicit
    private List<Label> labelList;

    /**
     * The type of category (application or module)
     */
    @XStreamAsAttribute
    private String type;

    /**
     * The categoryId
     */
    @XStreamAsAttribute
    private Long categoryId;

    /**
     * Indicate if the category is deprecated
     */
    @XStreamAsAttribute
    private boolean deprecated;

    /**
     * The list of segment
     */
    @XStreamImplicit
    private List<SegmentRef> segmentList;

    /**
     * Constructor
     * 
     * @param pKeyName The new category name
     * @param pCategoryId The category identifier
     * @param pType The category type (module or application)
     * @param pDeprecated (The deprecation state of the category)
     */

    public SegmentCategoryRef( String pKeyName, Long pCategoryId, String pType, boolean pDeprecated )
    {
        keyName = pKeyName;
        labelList = new ArrayList<Label>();
        categoryId = pCategoryId;
        type = pType;
        deprecated = pDeprecated;
        segmentList = new ArrayList<SegmentRef>();
    }

    /**
     * Getter method for the attribute keyName
     * 
     * @return The key name of the category
     */
    public String getKeyName()
    {
        return keyName;
    }

    /**
     * Setter for the attribute keyName
     * 
     * @param pKeyName The new key name of the category
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
     * Getter method for the attribute segmentList
     * 
     * @return The list of segment for the category
     */
    public List<SegmentRef> getSegmentList()
    {
        return segmentList;
    }

    /**
     * Add a segment to the list of segment for the category
     * 
     * @param segment The segment to add
     */
    public void addSegment( SegmentRef segment )
    {
        segmentList.add( segment );
    }

    /**
     * Getter method for the attribute catId
     * 
     * @return The category id
     */
    public Long getCatId()
    {
        return categoryId;
    }

    /**
     * Setter method for the attribute catId
     * 
     * @param pCategoryId The new category id
     */
    public void setCatId( Long pCategoryId )
    {
        categoryId = pCategoryId;
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
     * Getter method for the attribute type
     * 
     * @return The type of category ( application or module )
     */
    public String getType()
    {
        return type;
    }

    /**
     * Setter method for the attribute type
     * 
     * @param pType The new type ( application or module )
     */
    public void setType( String pType )
    {
        type = pType;
    }

}
