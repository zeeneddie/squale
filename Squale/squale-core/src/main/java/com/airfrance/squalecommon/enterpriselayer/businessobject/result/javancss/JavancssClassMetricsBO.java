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
    public JavancssClassMetricsBO()
    {
        super();
        getMetrics().put( CLASSES, new IntegerMetricBO() );
        getMetrics().put( METHODS, new IntegerMetricBO() );
        getMetrics().put( MAXVG, new IntegerMetricBO() );
        getMetrics().put( SUMVG, new IntegerMetricBO() );
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
