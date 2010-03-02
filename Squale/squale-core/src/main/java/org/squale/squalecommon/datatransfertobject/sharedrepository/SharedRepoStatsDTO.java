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
package org.squale.squalecommon.datatransfertobject.sharedrepository;

import java.io.Serializable;

/**
 * This class represents some statistics for a specific data
 */
public class SharedRepoStatsDTO
    implements Serializable
{
    /**
     * UID
     */
    private static final long serialVersionUID = -1871625807691433648L;

    /**
     * Technical Id
     */
    private long statsId;

    /**
     * The segementation linked to the stats
     */
    private SegmentationDTO segmentation;

    /**
     * The type of the element
     */
    private String elementType;

    /**
     * The type of the data
     */
    private String dataType;

    /**
     * The generic name of the data
     */
    private String dataName;

    /**
     * The language
     */
    private String language;

    /**
     * The mean for the current data
     */
    private float mean;

    /**
     * The max value for the current data
     */
    private float max;

    /**
     * The min value for the current data
     */
    private float min;

    /**
     * The deviation for the current data
     */
    private float deviation;

    /**
     * The number of element computed for the current data
     */
    private int elements;

    /**
     * Default constructor
     */
    public SharedRepoStatsDTO()
    {
        statsId = -1;
    }

    /**
     * @param pElementType
     * @param pDataType
     * @param pDataName
     * @param pLanguage
     * @param pSegmentation
     */
    public SharedRepoStatsDTO( String pElementType, String pDataType, String pDataName, String pLanguage,
                               SegmentationDTO pSegmentation )
    {
        statsId = -1;
        setElementType( pElementType );
        setDataType( pDataType );
        setDataName( pDataName );
        setLanguage( pLanguage );
        setSegmentation( pSegmentation );
    }

    /**
     * Getter method for the attribute statsId
     * 
     * @return The technical id
     */
    public long getStatsId()
    {
        return statsId;
    }

    /**
     * Setter method for the attribute statsId
     * 
     * @param pStatsId The new technical id
     */
    public void setStatsId( long pStatsId )
    {
        statsId = pStatsId;
    }

    /**
     * Getter method for the attribute elementType
     * 
     * @return The element type
     */
    public String getElementType()
    {
        return elementType;
    }

    /**
     * Setter method for the attribute elementType
     * 
     * @param pElementType The new element type
     */
    public void setElementType( String pElementType )
    {
        elementType = pElementType;
    }

    /**
     * Getter method for the attribute dataType
     * 
     * @return The data type
     */
    public String getDataType()
    {
        return dataType;
    }

    /**
     * Setter method for the attribute dataType
     * 
     * @param pDataType The new data type
     */
    public void setDataType( String pDataType )
    {
        dataType = pDataType;
    }

    /**
     * Getter method for the attribute dataName
     * 
     * @return The data name
     */
    public String getDataName()
    {
        return dataName;
    }

    /**
     * Setter method for the attribute dataName
     * 
     * @param pDataName The new data name
     */
    public void setDataName( String pDataName )
    {
        dataName = pDataName;
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
        language = pLanguage;
    }

    /**
     * Getter method for the attribute mean
     * 
     * @return The mean value
     */
    public float getMean()
    {
        return mean;
    }

    /**
     * Setter method for the attribute mean
     * 
     * @param pMean The new mean value
     */
    public void setMean( float pMean )
    {
        mean = pMean;
    }

    /**
     * Getter method for the attribute max
     * 
     * @return The max value
     */
    public float getMax()
    {
        return max;
    }

    /**
     * Setter method for the attribute max
     * 
     * @param pMax The new max value
     */
    public void setMax( float pMax )
    {
        max = pMax;
    }

    /**
     * Getter method for the attribute min
     * 
     * @return The min value
     */
    public float getMin()
    {
        return min;
    }

    /**
     * Setter method for the attribute min
     * 
     * @param pMin The new min value
     */
    public void setMin( float pMin )
    {
        min = pMin;
    }

    /**
     * Getter method for the attribute deviation
     * 
     * @return The deviation for the current data
     */
    public float getDeviation()
    {
        return deviation;
    }

    /**
     * Setter method for the attribute deviation
     * 
     * @param pDeviation The new deviation
     */
    public void setDeviation( float pDeviation )
    {
        deviation = pDeviation;
    }

    /**
     * Getter method for the attribute elements
     * 
     * @return The number of elements computed
     */
    public int getElements()
    {
        return elements;
    }

    /**
     * Setter method for the attribute elements
     * 
     * @param pElements The new number of elements computed
     */
    public void setElements( int pElements )
    {
        elements = pElements;
    }

    /**
     * Getter method for the attribute segmentation
     * 
     * @return The segmentation linked to the current stat
     */
    public SegmentationDTO getSegmentation()
    {
        return segmentation;
    }

    /**
     * Setter method for the attribute segementation
     * 
     * @param pSegmentation The new segmentation linked to the current stat
     */
    public void setSegmentation( SegmentationDTO pSegmentation )
    {
        segmentation = pSegmentation;
    }
}
