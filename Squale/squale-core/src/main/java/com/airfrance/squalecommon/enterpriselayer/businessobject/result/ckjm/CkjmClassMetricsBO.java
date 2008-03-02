package com.airfrance.squalecommon.enterpriselayer.businessobject.result.ckjm;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MeasureBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MetricBO;

/**
 * @hibernate.subclass discriminator-value="CkjmClassMetrics"
 */
public class CkjmClassMetricsBO
    extends MeasureBO
{

    /**
     * Couplage efferent
     */
    private final static String CBO = "cbo";

    /**
     * Couplage afferent
     */
    private final static String CA = "ca";

    /**
     * Constructeur par défaut.
     */
    public CkjmClassMetricsBO()
    {
        super();
        getMetrics().put( CBO, new IntegerMetricBO() );
        getMetrics().put( CA, new IntegerMetricBO() );
    }

    /**
     * Méthode d'accès à la métrique couplage efferent
     * 
     * @return la valuer du cbo
     */
    public Integer getCbo()
    {
        return (Integer) ( (MetricBO) getMetrics().get( CBO ) ).getValue();
    }

    /**
     * Change la valuer de la métrique couplage efferent
     * 
     * @param pCbo la nouvelle valeur du cbo
     */
    public void setCbo( int pCbo )
    {
        ( (IntegerMetricBO) getMetrics().get( CBO ) ).setValue( new Integer( pCbo ) );
    }

    /**
     * Méthode d'accès à la métrique couplage afferent
     * 
     * @return la valuer du ca
     */
    public Integer getCa()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( CA ) ).getValue();
    }

    /**
     * Change la valuer de la métrique couplage afferent
     * 
     * @param pCa la nouvelle valeur du ca
     */
    public void setCa( int pCa )
    {
        ( (IntegerMetricBO) getMetrics().get( CA ) ).setValue( new Integer( pCa ) );
    }
}
