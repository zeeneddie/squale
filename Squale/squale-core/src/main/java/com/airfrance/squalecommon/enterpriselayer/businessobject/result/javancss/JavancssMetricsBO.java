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
package com.airfrance.squalecommon.enterpriselayer.businessobject.result.javancss;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MeasureBO;

/**
 * Basic metric implementation of measure BO for javancss
 * 
 * @hibernate.subclass discriminator-value="JavancssMetrics"
 */
public class JavancssMetricsBO
    extends MeasureBO
{
    
    /**
     * Name of the component. Non persistent information
     */
    private String mComponentName;

    /**
     * Number of non comment source statement
     */
    private static final  String NCSS = "ncss";

    /**
     * Number of javadocs bloc
     */
    private static final  String JAVADOCS = "javadocs";
    
   

    /**
     * Default constructor
     */
    public JavancssMetricsBO()
    {
        super();
        getMetrics().put( NCSS, new IntegerMetricBO() );
        getMetrics().put( JAVADOCS, new IntegerMetricBO() );
    }

    /**
     * Get the value of ncss for the component
     * 
     * @return ncss
     */
    public Integer getNcss()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NCSS ) ).getValue();
    }

    /**
     * Set the value of ncss for the component
     * 
     * @param pNcss ncss
     */
    public void setNcss( int pNcss )
    {
        ( (IntegerMetricBO) getMetrics().get( NCSS ) ).setValue( pNcss );
    }

    /**
     * Get the number of javadocs bloc of the component
     * 
     * @return number of javadocs bloc lines
     */
    public Integer getJavadocs()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( JAVADOCS ) ).getValue();
    }

    /**
     * Put the number of javadocs bloc for the component
     * 
     * @param pJavadocs number of javadocs bloc
     */
    public void setJavadocs( int pJavadocs )
    {
        ( (IntegerMetricBO) getMetrics().get( JAVADOCS ) ).setValue( pJavadocs );
    }

    /**
     * Getter for the mComponentName property
     * @return The value of the mComponentProperty
     */
    public String getComponentName()
    {
        return mComponentName;
    }

    /**
     * Setter for the mComponentProperty
     * @param componentName The new value for the mComponentProperty
     */
    public void setComponentName( String componentName )
    {
        mComponentName = componentName;
    }

}
