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
 * Data class used for the export
 */
@XStreamAlias( "data" )
public class DataEx
{

    /**
     * The type of the data
     */
    @XStreamAsAttribute
    private String type;

    /**
     * The name of the data
     */
    @XStreamAsAttribute
    private String genericName;

    /**
     * The value of the data
     */
    @XStreamAsAttribute
    private String value;

    /**
     * Default constructor
     */
    public DataEx()
    {

    }

    /**
     * Full constructor
     * 
     * @param pType The type of the data
     * @param pGenericName The name of the data
     * @param pValue The value of the data
     */
    public DataEx( String pType, String pGenericName, String pValue )
    {
        type = pType;
        genericName = pGenericName;
        value = pValue;
    }

    /**
     * Getter method for the attribute type
     * 
     * @return The type of the component
     */
    public String getType()
    {
        return type;
    }

    /**
     * Setter method for the attribute type
     * 
     * @param pType The new type of the data
     */
    public void setType( String pType )
    {
        type = pType;
    }

    /**
     * Getter method for the attribute genericName
     * 
     * @return The generic name of the data
     */
    public String getGenericName()
    {
        return genericName;
    }

    /**
     * Setter method for the attribute genericName
     * 
     * @param pGenericName The new generic name of the data
     */
    public void setGenericName( String pGenericName )
    {
        genericName = pGenericName;
    }

    /**
     * Getter method for the attribute value
     * 
     * @return The value of the data
     */
    public String getValue()
    {
        return value;
    }

    /**
     * Setter method for the attribute value
     * 
     * @param pValue The new value of the data
     */
    public void setValue( String pValue )
    {
        value = pValue;
    }

}
