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

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This class represents one statistics for the reference
 */
@XStreamAlias( "statistic" )
public class Statistic
{

    /**
     * The name of the statistics
     */
    @XStreamAsAttribute
    private String statName;

    /**
     * The value of the statistics
     */
    @XStreamAsAttribute
    private String statValue;

    /**
     * Default constructor
     */
    public Statistic()
    {
    }

    /**
     * Full constructor
     * 
     * @param pStatName the statistic name
     * @param pStatValue the statistic value
     */
    public Statistic( String pStatName, String pStatValue )
    {
        this.statName = pStatName;
        this.statValue = pStatValue;
    }

    /**
     * Getter method for the attribute statName
     * 
     * @return the name of the stat
     */
    public String getStatName()
    {
        return statName;
    }

    /**
     * Setter method for the attribute statName
     * 
     * @param pStatName The new name of the statistics
     */
    public void setStatName( String pStatName )
    {
        statName = pStatName;
    }

    /**
     * Getter method for the attribute statValue
     * 
     * @return The value of the statistics
     */
    public String getStatValue()
    {
        return statValue;
    }

    /**
     * Setter method for the attribute statValue
     * 
     * @param pStatValue The new value of the statistics
     */
    public void setStatValue( String pStatValue )
    {
        statValue = pStatValue;
    }

}
