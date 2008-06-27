package com.airfrance.squalecommon.enterpriselayer.businessobject.result;

/**
 * @hibernate.subclass discriminator-value="Float"
 */
public class FloatMetricBO
    extends NumberMetricBO
{

    /**
     * Valeur continue du mérique
     */
    protected Float mValue;

    /**
     * Access method for the mValue property.
     * 
     * @return the current value of the mName property
     * @hibernate.property name="Value" column="Number_val" type="float" not-null="false" unique="false" update="true"
     *                     insert="true"
     */
    public Object getValue()
    {
        return mValue;
    }

    /**
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.result.MetricBO#setValue(java.lang.Object)
     */
    public void setValue( Object pValue )
    {
        mValue = (Float) pValue;
    }

    /**
     * Sets the value of the mValue property as a float.
     * 
     * @param pValue the new value of the mValue property
     */
    public void setValue( float pValue )
    {
        mValue = new Float( pValue );
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.result.MetricBO#isPrintable()
     */
    public boolean isPrintable()
    {
        return true;
    }

}
