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

import org.squale.welcom.struts.bean.WActionFormSelectable;

/**
 * Struts for the segment
 */
public class SegmentForm
    extends WActionFormSelectable
{

    /**
     * UID
     */
    private static final long serialVersionUID = 6396938270598817367L;

    /**
     * Segment name
     */
    private String name;

    /**
     * Segment technical Id
     */
    private String identifier;

    /**
     * Deprecation state
     */
    private Boolean deprecated = false;

    /**
     * Constructor
     */
    public SegmentForm()
    {

    }

    /**
     * Getter method for the attribute name
     * 
     * @return The name of the segment
     */
    public String getName()
    {
        return name;
    }

    /**
     * Setter method for the attribute name
     * 
     * @param pName The new name of the segment
     */
    public void setName( String pName )
    {
        name = pName;
    }

    /**
     * Getter method for the attribute identifier
     * 
     * @return the identifier of the segment
     */
    public String getIdentifier()
    {
        return identifier;
    }

    /**
     * Setter method for the attribute identifier
     * 
     * @param pIdentifier The new identifier of the segment
     */
    public void setIdentifier( String pIdentifier )
    {
        identifier = pIdentifier;
    }

    /**
     * Getter method for the attribute deprecated
     * 
     * @return The deprecation state of the segment
     */
    public Boolean getDeprecated()
    {
        return deprecated;
    }

    /**
     * Setter method for the attribute deprecated
     * 
     * @param pDeprecated the new deprecation of the segment
     */
    public void setDeprecated( Boolean pDeprecated )
    {
        deprecated = pDeprecated;
    }

}
