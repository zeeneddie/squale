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
package org.squale.sharedrepository.export;

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
     * For the metric data only
     * Indicate to which language the metric is linked 
     */
    @XStreamAsAttribute
    private String language;

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
     * @param pLanguage The language
     */
    private DataEx( String pType, String pGenericName, String pValue, String pLanguage )
    {
        type = pType;
        genericName = pGenericName;
        value = pValue;
        language = pLanguage;
    }
    
    /**
     * This method create a DataEx object for a metric
     * 
     * @param pType The type of the data
     * @param pGenericName The name of the data
     * @param pValue The value of the data
     * @param pLanguage The language
     * @return The DataEx
     */
    public static DataEx createMetric(String pType, String pGenericName, String pValue, String pLanguage)
    {
        return new DataEx( pType, pGenericName, pValue, pLanguage );
    }
    
    /**
     * This method create a DataEx object for a Factor, Criterium, Practice 
     * 
     * @param pType The type of the data
     * @param pGenericName The name of the data
     * @param pValue The value of the data
     * @return The DataEx
     */
    public static DataEx createData(String pType, String pGenericName, String pValue)
    {
        return new DataEx( pType, pGenericName, pValue, null );
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

    /**
     * Getter method for the attribute language
     * 
     * @return The language
     */
    public String getLanguage()
    {
        return language;
    }

    /**
     * Setter method for the attribute language
     * 
     * @param pLanguage The new language
     */
    public void setLanguage( String pLanguage )
    {
        this.language = pLanguage;
    }

}
