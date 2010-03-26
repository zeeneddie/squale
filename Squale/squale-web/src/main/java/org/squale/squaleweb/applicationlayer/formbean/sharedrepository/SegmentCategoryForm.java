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
package org.squale.squaleweb.applicationlayer.formbean.sharedrepository;

import java.util.ArrayList;
import java.util.List;

import org.squale.welcom.struts.bean.WActionForm;

/**
 * Form for the segment category
 */
public class SegmentCategoryForm
    extends WActionForm
{

    /**
     * UID
     */
    private static final long serialVersionUID = 5981914519608072243L;

    /**
     * The name of the category
     */
    private String name;

    /**
     * The deprecated state of the category
     */
    private Boolean deprecated = false;

    /**
     * The list of segment for the category
     */
    private List<SegmentForm> segmentList = new ArrayList<SegmentForm>();

    /**
     * Constructor
     */
    public SegmentCategoryForm()
    {

    }

    /**
     * Getter method for the attribute name
     * 
     * @return The name of the category
     */
    public String getName()
    {
        return name;
    }

    /**
     * Setter method for the attribute name
     * 
     * @param pName The new name of the category
     */
    public void setName( String pName )
    {
        name = pName;
    }

    /**
     * Getter method for the attribute deprecated
     * 
     * @return The deprecation state of the category
     */
    public Boolean getDeprecated()
    {
        return deprecated;
    }

    /**
     * Setter method for the attribute deprecated
     * 
     * @param pDeprecated The new deprecation state of the category
     */
    public void setDeprecated( Boolean pDeprecated )
    {
        deprecated = pDeprecated;
    }

    /**
     * Getter method for the attribute segmentList
     * 
     * @return the list of segment of the category
     */
    public List<SegmentForm> getSegmentList()
    {
        return segmentList;
    }

    /**
     * Setter method for the attribute segmentList
     * 
     * @param pSegmentList the new list of segment of the category
     */
    public void setSegmentList( List<SegmentForm> pSegmentList )
    {
        segmentList = pSegmentList;
    }

    /**
     * Add a segment to the list of segment
     * 
     * @param segment The segment to add
     */
    public void addSegment( SegmentForm segment )
    {
        segmentList.add( segment );
    }

}
