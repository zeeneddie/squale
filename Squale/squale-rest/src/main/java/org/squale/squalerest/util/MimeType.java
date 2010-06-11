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
package org.squale.squalerest.util;

/**
 * Enumeration for mime type
 * 
 * @author bfranchet
 */
public enum MimeType
{

    /**
     * mime type for xml
     */
    xml( "application/xml" ),

    /**
     * mime type for json
     */
    json( "application/json" );

    /**
     * The real value
     */
    private String realValue;

    /**
     * Constructor
     * 
     * @param value The real value
     */
    MimeType( String value )
    {
        realValue = value;
    }

    /**
     * Getter method for the attribute realValue
     * 
     * @return Return the real value of the element
     */
    public String getRealValue()
    {
        return realValue;
    }

}
