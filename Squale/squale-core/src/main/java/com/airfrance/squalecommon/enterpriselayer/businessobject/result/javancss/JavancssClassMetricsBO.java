package com.airfrance.squalecommon.enterpriselayer.businessobject.result.javancss;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;

/**
 * Implementation of measureBO of the class level result for the javancss tool
 * 
 * @hibernate.subclass discriminator-value="JavancssClassMetrics"
 */
public class JavancssClassMetricsBO
    extends JavancssMetricsBO
{

    /**
     * Number of classes
     */
    private static final String CLASSES = "classes";

    /**
     * Number of methods
     */
    private static final String METHODS = "methods";

    /**
     * Default constructor
     */
    public JavancssClassMetricsBO()
    {
        super();
        getMetrics().put( CLASSES, new IntegerMetricBO() );
        getMetrics().put( METHODS, new IntegerMetricBO() );
    }

    /**
     * Get the number of classes for the component
     * 
     * @return The number of classes
     */
    public Integer getClasses()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( CLASSES ) ).getValue();
    }

    /**
     * Set the number of classes for the component
     * 
     * @param pClasses the number of classes
     */
    public void setClasses( int pClasses )
    {
        ( (IntegerMetricBO) getMetrics().get( CLASSES ) ).setValue( pClasses );
    }

    /**
     * Get the number of methods for the component
     * 
     * @return The number of methods
     */
    public Integer getMethods()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( METHODS ) ).getValue();
    }

    /**
     * Set the number of methods for the component
     * 
     * @param pMethods The number of methods
     */
    public void setMethods( int pMethods )
    {
        ( (IntegerMetricBO) getMetrics().get( METHODS ) ).setValue( pMethods );
    }

}
