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
     * Max value of the cyclomatic complexity in the component
     */
    private static final  String MAXVG = "maxVg" ;
    
    /**
     * Sum of cyclomatic complexity on the component
     */
    private static final  String  SUMVG = "sumVg";
    

    /**
     * Default constructor
     */
    public JavancssMetricsBO()
    {
        super();
        getMetrics().put( NCSS, new IntegerMetricBO() );
        getMetrics().put( JAVADOCS, new IntegerMetricBO() );
        getMetrics().put( MAXVG, new IntegerMetricBO() );
        getMetrics().put( SUMVG, new IntegerMetricBO() );
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
    /**
     * Get the value of SumVg for the component
     * 
     * @return the value of SumVg
     */
    public Integer getSumVg()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( SUMVG ) ).getValue();
    }

    /**
     * Set the sumVg for the component
     * 
     * @param pSumVg The value of SumVg
     */
    public void setSumVg( int pSumVg )
    {
        ( (IntegerMetricBO) getMetrics().get( SUMVG ) ).setValue( pSumVg );
    }

    
    
    /**
     * Get the value of MaxVg for the component
     * 
     * @return the value of MaxVg
     */
    public Integer getMaxVg()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( MAXVG ) ).getValue();
    }

    /**
     * Set the sumVg for the component
     * 
     * @param pMaxVg The value of MaxVg
     */
    public void setMaxVg( int pMaxVg )
    {
        ( (IntegerMetricBO) getMetrics().get( MAXVG ) ).setValue( pMaxVg );
    }
    
    
}
