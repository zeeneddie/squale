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
package org.squale.squaleexport.object;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Segment class used for the export
 */
@XStreamAlias( "segment" )
public class SegmentEx
{

    /**
     * The name of the segment
     */
    @XStreamAsAttribute
    private String name;

    /**
     * Default constructor
     */
    public SegmentEx()
    {

    }

    /**
     * Full constructor
     *  
     * @param pName The name of the segment
     */
    public SegmentEx( String pName )
    {
        name = pName;
    }

    /**
     * Getter method for the attribute name
     * 
     * @return The attribute name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Getter method for the attribute name
     * 
     * @param pName The attribute name
     */
    public void setName( String pName )
    {
        name = pName;
    }

}
