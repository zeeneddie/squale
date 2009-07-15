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
package org.squale.squalecommon.enterpriselayer.businessobject.result.javancss;

import org.squale.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;

/**
 * Implementation of measureBO for the project level result of the javancss tools
 * 
 * @hibernate.subclass discriminator-value="JavancssProjectMetrics"
 */
public class JavancssProjectMetricsBO
    extends JavancssPackageMetricsBO
{

    /**
     * Number of line in the project
     */
    private static final String LINES = "lines";

    /**
     * Number of line of code (code + comments)
     */
    private static final String LOC = "numberOfCodeLines";
    
    /**
     * Number of line of comments
     */
    private static final String COMMENTSLINES = "numberOfCommentsLines"; 
    
    /**
     * Number of class recalculate
     */
    private static final String CLASSNUMBER = "numberOfClasses";
    
    /**
     * Number of class recalculate
     */
    private static final String METHODNUMBER = "numberOfMethods";
    
    /**
     * Default constructor
     */
    public JavancssProjectMetricsBO()
    {
        super();
        getMetrics().put( LINES, new IntegerMetricBO() );
        getMetrics().put( LOC, new IntegerMetricBO() );
        getMetrics().put( CLASSNUMBER, new IntegerMetricBO() );
        getMetrics().put( METHODNUMBER, new IntegerMetricBO() );
        getMetrics().put( COMMENTSLINES, new IntegerMetricBO() );
    }

    /**
     * Get the number of line of code (code + comments)
     * 
     * @return return the number of line of code
     */
    public Integer getLoc()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( LOC ) ).getValue();
    }

    /**
     * Set the value for the number of line of code (code + comments)
     * 
     * @param pLoc the number of line of code
     */
    public void setLoc( int pLoc )
    {
        ( (IntegerMetricBO) getMetrics().get( LOC ) ).setValue( pLoc );
    }
    
    /**
     * Get the number of line of comments
     * 
     * @return return the number of line of comments
     */
    public Integer getCommentsLines()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( COMMENTSLINES ) ).getValue();
    }

    /**
     * Set the value for the number of line of comments
     * 
     * @param pCommentsLines the number of line of comments
     */
    public void setCommentsLines( int pCommentsLines )
    {
        ( (IntegerMetricBO) getMetrics().get( COMMENTSLINES ) ).setValue( pCommentsLines );
    }
    
    /**
     * Get the calculated number of class
     * 
     * @return return the calculated number of class
     */
    public Integer getClassNumber()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( CLASSNUMBER ) ).getValue();
    }

    /**
     * Set the calculated number of Class
     * 
     * @param pClass the calculated number of Class
     */
    public void setClassNumber( int pClass )
    {
        ( (IntegerMetricBO) getMetrics().get( CLASSNUMBER ) ).setValue( pClass );
    }
    
    /**
     * Get the number of line of code
     * 
     * @return return the number of line of code
     */
    public Integer getLines()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( LINES ) ).getValue();
    }

    /**
     * Set the value for the number of line of code
     * 
     * @param pLines the number of line of code
     */
    public void setLines( int pLines )
    {
        ( (IntegerMetricBO) getMetrics().get( LINES ) ).setValue( pLines );
    }
    
    /**
     * Get the calculated number methods
     * 
     * @return return the calculated number of methods
     */
    public Integer getMethodNumber()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( METHODNUMBER ) ).getValue();
    }

    /**
     * Set the calculated number of methods
     * 
     * @param pNumberOfMethods the calculate number of methods
     */
    public void setMethodNumber( int pNumberOfMethods )
    {
        ( (IntegerMetricBO) getMetrics().get( METHODNUMBER ) ).setValue( pNumberOfMethods );
    }
    
}
