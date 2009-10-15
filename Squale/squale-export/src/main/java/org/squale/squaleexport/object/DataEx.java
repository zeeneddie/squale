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
 * 
 *
 */
@XStreamAlias( "data" )
public class DataEx
{

    /**
     * 
     */
    @XStreamAsAttribute
    private String type;

    /**
     * 
     */
    @XStreamAsAttribute
    private String genericName;

    /**
     * 
     */
    @XStreamAsAttribute
    private String value;

    /**
     * 
     */
    public DataEx()
    {

    }

    /**
     * @param pType
     * @param pGenericName
     * @param pValue
     */
    public DataEx( String pType, String pGenericName, String pValue )
    {
        type = pType;
        genericName = pGenericName;
        value = pValue;
    }

    /**
     * @return
     */
    public String getType()
    {
        return type;
    }

    /**
     * @param pType
     */
    public void setType( String pType )
    {
        type = pType;
    }

    /**
     * @return
     */
    public String getGenericName()
    {
        return genericName;
    }

    /**
     * @param pGenericName
     */
    public void setGenericName( String pGenericName )
    {
        genericName = pGenericName;
    }

    /**
     * @return
     */
    public String getValue()
    {
        return value;
    }

    /**
     * @param pValue
     */
    public void setValue( String pValue )
    {
        value = pValue;
    }

}
