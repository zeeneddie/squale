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
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This class represent a segment reference
 */
@XStreamAlias( "segments-reference" )
public class SegmentsReference
{

    /**
     * The list of category
     */
    @XStreamImplicit
    private List<SegmentCategoryRef> categoryList;

    /**
     * Constructor
     */
    public SegmentsReference()
    {
        categoryList = new ArrayList<SegmentCategoryRef>();
    }

    /**
     * Getter method for the attribute categoryList
     * 
     * @return The list of category
     */
    public List<SegmentCategoryRef> getCategoryList()
    {
        return categoryList;
    }

    /**
     * Add a category to the list of category
     * 
     * @param pCategory The category to add
     */
    public void addCategory( SegmentCategoryRef pCategory )
    {
        categoryList.add( pCategory );
    }

}
