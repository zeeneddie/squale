package com.airfrance.squalecommon.enterpriselayer.businessobject.result.roi;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MeasureBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MetricBO;

/**
 * Représente un retour sur investissement
 * 
 * @hibernate.subclass discriminator-value="Roi"
 */
public class RoiMetricsBO
    extends MeasureBO
{

    /** Le nom de la tâche ROI */
    public static final String ROI_TASKNAME = "RoiTask";

    /**
     * Le nombre de corrections
     */
    private final static String NB_CORRECTIONS = "roi";

    /**
     * Constructeur par défaut.
     */
    public RoiMetricsBO()
    {
        super();
        getMetrics().put( NB_CORRECTIONS, new IntegerMetricBO() );
        mTaskName = ROI_TASKNAME;
    }

    /**
     * @return le nombre de corrections
     */
    public Integer getNbCorrections()
    {
        return (Integer) ( (MetricBO) getMetrics().get( NB_CORRECTIONS ) ).getValue();
    }

    /**
     * @param pNbCorrections le nombre de corrections
     */
    public void setNbCorrections( int pNbCorrections )
    {
        ( (IntegerMetricBO) getMetrics().get( NB_CORRECTIONS ) ).setValue( new Integer( pNbCorrections ) );
    }
}
