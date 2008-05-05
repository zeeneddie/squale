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
