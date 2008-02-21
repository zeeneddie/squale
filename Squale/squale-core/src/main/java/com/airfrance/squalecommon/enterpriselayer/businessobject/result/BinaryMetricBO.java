package com.airfrance.squalecommon.enterpriselayer.businessobject.result;

/**
 * @author M401540
 *
 * @hibernate.subclass
 * discriminator-value="Bin"
 */
public class BinaryMetricBO extends MetricBO {
    /**
     * Valeur sous forme de chaine de bytes 
     */
    private byte[] mValue;

    /**
     * Access method for the mValue property.
     * 
     * @return   the current value of the mName property
     * 
     * @hibernate.property 
     * name="Value" 
     * column="Blob_val" 
     * type="com.airfrance.jraf.provider.persistence.hibernate.BinaryBlobType" 
     * not-null="false" 
     * unique="false"
     * 
     */
    public Object getValue() {
        return mValue;
    }

    /**
     * @param pValue image sous forme de byte[]
     */
    public void setValue(Object pValue) {
        mValue = (byte []) pValue;
    }

}
