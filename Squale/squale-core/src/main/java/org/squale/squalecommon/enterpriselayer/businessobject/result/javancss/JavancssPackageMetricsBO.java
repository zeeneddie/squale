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
 * Implementation of measureBO for the package level result of javancss
 * @hibernate.subclass discriminator-value="JavancssPackageMetrics"
 */
public class JavancssPackageMetricsBO
    extends JavancssClassMetricsBO
{

    /**
     * number of javadocs lines
     */
    private static final String JAVADOCSLINES = "javadocsLines";

    /**
     * Number of single comments line 
     */
    private static final String SINGLECOMMENTSLINES = "singleCommentsLines";

    /**
     * Number of Multi comments line
     */
    private static final String MULTICOMMENTSLINES = "multiCommentsLines";

    /**
     * Default constructor
     */
    public JavancssPackageMetricsBO()
    {
        super();
        getMetrics().put( JAVADOCSLINES, new IntegerMetricBO() );
        getMetrics().put( SINGLECOMMENTSLINES, new IntegerMetricBO() );
        getMetrics().put( MULTICOMMENTSLINES, new IntegerMetricBO() );
    }

    /**
     * Get the number of javadocs lines for the component 
     * @return The number of javadocs lines
     */
    public Integer getJavadocsLines()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( JAVADOCSLINES ) ).getValue();
    }

    /**
     * Set the number of javadocs lines for the component
     * @param pJavadocsLines The number of javadocs lines
     */
    public void setJavadocsLines( int pJavadocsLines )
    {
        ( (IntegerMetricBO) getMetrics().get( JAVADOCSLINES ) ).setValue( pJavadocsLines );
    }

    /**
     * Get the number of single comments line for the component
     * @return The number of single comments line
     */
    public Integer getSingleCommentsLines()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( SINGLECOMMENTSLINES ) ).getValue();
    }

    /**
     * Set the number of single comments line for the component
     * @param pSingleJavadocsLines The number of single comments line
     */
    public void setSingleCommentsLines( int pSingleJavadocsLines )
    {
        ( (IntegerMetricBO) getMetrics().get( SINGLECOMMENTSLINES ) ).setValue( pSingleJavadocsLines );
    }

    /**
     * Get the number of multi comments lines for the component
     * @return The number of multi comments lines
     */
    public Integer getMultiCommentsLines()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( MULTICOMMENTSLINES ) ).getValue();
    }

    /**
     * set The number of multi comments lines for the component
     * @param pMultiJavadocsLines the number of multi comments lines
     */
    public void setMultiCommentsLines( int pMultiJavadocsLines )
    {
        ( (IntegerMetricBO) getMetrics().get( MULTICOMMENTSLINES ) ).setValue( pMultiJavadocsLines );
    }

}
