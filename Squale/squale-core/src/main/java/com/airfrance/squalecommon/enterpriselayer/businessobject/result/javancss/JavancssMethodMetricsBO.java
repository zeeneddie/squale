package com.airfrance.squalecommon.enterpriselayer.businessobject.result.javancss;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;

/**
 * Implementation of measureBO of the method level result for javancss
 * 
 * @hibernate.subclass discriminator-value="JavancssMethodMetrics"
 */
public class JavancssMethodMetricsBO
    extends JavancssMetricsBO

{

    /**
     * The Cyclomatic Complexity Number
     */
    private static final String CCN = "ccn";

    /**
     * Default constructor
     */
    public JavancssMethodMetricsBO()
    {
        super();
        getMetrics().put( CCN, new IntegerMetricBO() );
    }

    /**
     * Get the Cyclomatic Complexity Number for the component
     * 
     * @return The Cyclomatic Complexity Number
     */
    public Integer getCcn()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( CCN ) ).getValue();
    }

    /**
     * Set Cyclomatic Complexity Number for the component
     * 
     * @param pCcn The Cyclomatic Complexity Number
     */
    public void setCcn( int pCcn )
    {
        ( (IntegerMetricBO) getMetrics().get( CCN ) ).setValue( pCcn );
    }

}
