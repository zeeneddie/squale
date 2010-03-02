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
package org.squale.sharedrepository.stat;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Data class used for the export
 */
@XStreamAlias( "data" )
public class DataStat
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
     * For the metric data only
     * Indicate to which language the metric is linked 
     */
    @XStreamAsAttribute
    private String language;
    
    /**
     * The list of statistics linked to the data
     */
    @XStreamImplicit
    private List<Statistic> statList;

    /**
     * Default constructor
     */
    public DataStat()
    {
        statList = new ArrayList<Statistic>();
    }

    /**
     * Full constructor
     * 
     * @param pType The type of the data
     * @param pGenericName The name of the data
     * @param pLanguage The language
     * @param stats The list of statistics linked to the data
     */
    private DataStat( String pType, String pGenericName, String pLanguage ,List<Statistic> stats )
    {
        type = pType;
        genericName = pGenericName;
        language = pLanguage;
        statList = stats;
    }
    
    /**
     * This method create a dataStat for a metric data
     * 
     * @param pType The type of the data
     * @param pGenericName The name of the data
     * @param pLanguage The language
     * @param stats The list of statistics linked to the data
     * @return A metric dataStat
     */
    public static DataStat createMetricDataStat(String pType, String pGenericName, String pLanguage ,List<Statistic> stats)
    {
        return new DataStat( pType, pGenericName, pLanguage, stats );
    }

    /**
     * This method create a dataStat for a metric data
     * 
     * @param pType The type of the data
     * @param pGenericName The name of the data
     * @param stats The list of statistics linked to the data
     * @return A metric dataStat
     */
    public static DataStat createDataStat(String pType, String pGenericName ,List<Statistic> stats)
    {
        return new DataStat( pType, pGenericName, null , stats );
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
     * Getter method for the attribute statList
     * 
     * @return The list of statistics
     */
    public List<Statistic> getStatList()
    {
        return statList;
    }

    /**
     * Setter method for the attribute statList
     * 
     * @param stats The new list of statistics
     */
    public void setStatList( List<Statistic> stats )
    {
        statList = stats;
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
