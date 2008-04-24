package com.airfrance.squalecommon.enterpriselayer.businessobject.result.javancss;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;

/**
 * Implementation of measureBO for the project level result of the javancss tools
 * 
 * @hibernate.subclass discriminator-value="JavancssProjectMetrics"
 */
public class JavancssProjectMetricsBO
    extends JavancssPackageMetricsBO
{

    /**
     * Number of line
     */
    private static final String LINES = "lines";

    /**
     * Default constructor
     */
    public JavancssProjectMetricsBO()
    {
        super();
        getMetrics().put( LINES, new IntegerMetricBO() );
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
     * @param pLoc the number of line of code
     */
    public void setLine( int pLines )
    {
        ( (IntegerMetricBO) getMetrics().get( LINES ) ).setValue( pLines );
    }
}
