package com.airfrance.squalecommon.enterpriselayer.businessobject.result;

/**
 * @author m401540
 * @version 1.0
 * @hibernate.subclass discriminator-value="Number"
 */
public abstract class NumberMetricBO
    extends MetricBO
{

    /**
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.result.MetricBO#getValue()
     */
    public abstract Object getValue();

    /**
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.result.MetricBO#setValue(java.lang.Object)
     */
    public abstract void setValue( Object value );

}
