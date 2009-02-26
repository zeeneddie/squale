package com.airfrance.squalecommon.enterpriselayer.businessobject.result.cobertura;

/**
 * <p>
 * Instance of this class stores values regarding the method level metrics
 * </p>
 * 
 * @hibernate.subclass discriminator-value="CoberturaMethodMetrics"
 */
public class CoberturaMethodMetricsBO
    extends AbstractCoberturaMetricsBO
{
    /** The signature of the method as it is needed later to identify the methods */
    private String signature;

    /** The default constructor */
    public CoberturaMethodMetricsBO()
    {
        super();
        signature = "";
    }

    /**
     * Getter of the signature of the method
     * 
     * @return the signature of the method
     */
    public String getSignature()
    {
        return signature;
    }

    /**
     * Setter of the signature of the method
     * 
     * @param pSignature the value of the signature
     */
    public void setSignature( String pSignature )
    {
        this.signature = pSignature;
    }

}
