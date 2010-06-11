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
package org.squale.squalerest.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Data class used for the export
 */
@XStreamAlias( "factor" )
public class FactorRest
{

    /**
     * The name of the data
     */
    @XStreamAsAttribute
    @XStreamAlias( "name" )
    private String genericName;

    /**
     * The value of the data
     */
    private String value;

    /**
     * Default constructor
     */
    public FactorRest()
    {

    }

    /**
     * Full constructor
     * 
     * @param pGenericName The name of the data
     * @param pValue The value of the data
     */
    public FactorRest( String pGenericName, String pValue )
    {
        genericName = pGenericName;
        value = pValue;
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
