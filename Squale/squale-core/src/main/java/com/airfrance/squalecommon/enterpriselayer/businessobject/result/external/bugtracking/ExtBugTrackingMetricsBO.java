package com.airfrance.squalecommon.enterpriselayer.businessobject.result.external.bugtracking;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MeasureBO;

/**
 * Cette classe va servir pour faire le lien entre la récupération des infos dans les taches externes et
 * l'enregistrement dans la base de donnée
 * 
 * @hibernate.subclass discriminator-value="ExtBugTrackingMetrics"
 */
public class ExtBugTrackingMetricsBO
    extends MeasureBO
{

    /** nombre total de defects */
    private final static String NBDEFECTS = "numberOfDefects";

    /** nombre de defects ouvert non assigné */
    private final static String DEFECTSOPEN = "defetcsOpen";

    /** nombre de defects assigné en court de traitement */
    private final static String DEFECTSASSIGNED = "defectsAssigned";

    /** nombre de defects traité mais non validé */
    private final static String DEFECTSTREATED = "defectsTreated";

    /** nombre de defects clot */
    private final static String DEFECTSCLOSE = "defectsClose";

    /** nombre de defects qui sont des évolutions */
    private final static String DEFECTSEVOLUTION = "defectsEvolution";

    /** nombre de defects qui sont des anomalis */
    private final static String DEFECTSANOMALY = "defectsAnomaly";

    /** nombre de defects de niveau haut */
    private final static String DEFECTSHIGH = "defectsHigh";

    /** nombre de defects de niveau moyen */
    private final static String DEFECTSMEDIUM = "defectsMedium";

    /** nombre de defects de niveau bas */
    private final static String DEFECTSLOW = "defectsLow";

    /**
     * Constructeur par défaut
     */
    public ExtBugTrackingMetricsBO()
    {
        super();
        getMetrics().put( NBDEFECTS, new IntegerMetricBO() );
        getMetrics().put( DEFECTSOPEN, new IntegerMetricBO() );
        getMetrics().put( DEFECTSASSIGNED, new IntegerMetricBO() );
        getMetrics().put( DEFECTSTREATED, new IntegerMetricBO() );
        getMetrics().put( DEFECTSCLOSE, new IntegerMetricBO() );
        getMetrics().put( DEFECTSANOMALY, new IntegerMetricBO() );
        getMetrics().put( DEFECTSEVOLUTION, new IntegerMetricBO() );
        getMetrics().put( DEFECTSHIGH, new IntegerMetricBO() );
        getMetrics().put( DEFECTSMEDIUM, new IntegerMetricBO() );
        getMetrics().put( DEFECTSLOW, new IntegerMetricBO() );
    }

    /**
     * @return renvoi le nombre total de defects
     */
    public Integer getNumberOfDefects()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NBDEFECTS ) ).getValue();
    }

    /**
     * @return renvoi le nombre de defects ouvert et non assigné
     */
    public Integer getDefectsOpen()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( DEFECTSOPEN ) ).getValue();
    }

    /**
     * @return renvoi le nombre de defects assigné et non traité
     */
    public Integer getDefectsAssigned()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( DEFECTSASSIGNED ) ).getValue();
    }

    /**
     * @return renvoi le nombre defects traité mais non clot
     */
    public Integer getDefectsTreated()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( DEFECTSTREATED ) ).getValue();
    }

    /**
     * @return renvoi le nombre de defects clot
     */
    public Integer getDefectsClose()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( DEFECTSCLOSE ) ).getValue();
    }

    /**
     * @return renvoi le nombre de defects qui sont des anomaly
     */
    public Integer getDefectsAnomaly()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( DEFECTSANOMALY ) ).getValue();
    }

    /**
     * @return renvoi le nombre de defects qui sont en evolution
     */
    public Integer getDefectsEvolution()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( DEFECTSEVOLUTION ) ).getValue();
    }

    /**
     * @return renvoi le nombre de defects de niveau élévé
     */
    public Integer getDefectsHigh()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( DEFECTSHIGH ) ).getValue();
    }

    /**
     * @return renvoi le nombre de defects de niveau moyen
     */
    public Integer getDefectsMedium()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( DEFECTSMEDIUM ) ).getValue();
    }

    /**
     * @return renvoi le noimbre de defects de niveau bas
     */
    public Integer getDefectsLow()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( DEFECTSLOW ) ).getValue();
    }

    /**
     * @param pNbDefects le nouveau nombre total de defects
     */
    public void setNumberOfDefects( int pNbDefects )
    {
        ( (IntegerMetricBO) getMetrics().get( NBDEFECTS ) ).setValue( new Integer( pNbDefects ) );
    }

    /**
     * @param pDefetcsopen le nouveau nombre de defects ouvert mais non assigné
     */
    public void setDefectsOpen( int pDefetcsopen )
    {
        ( (IntegerMetricBO) getMetrics().get( DEFECTSOPEN ) ).setValue( new Integer( pDefetcsopen ) );
    }

    /**
     * @param pDefectsassigned le nouveau nombre de defects assigné mais non traité
     */
    public void setDefectsAssigned( int pDefectsassigned )
    {
        ( (IntegerMetricBO) getMetrics().get( DEFECTSASSIGNED ) ).setValue( new Integer( pDefectsassigned ) );
    }

    /**
     * @param pDefectsTreated le nouveau nombre de defects traité mais non clot
     */
    public void setDefectsTreated( int pDefectsTreated )
    {
        ( (IntegerMetricBO) getMetrics().get( DEFECTSTREATED ) ).setValue( new Integer( pDefectsTreated ) );
    }

    /**
     * @param pDefectsClose le nouveau nombre de defects clot
     */
    public void setDefectsClose( int pDefectsClose )
    {
        ( (IntegerMetricBO) getMetrics().get( DEFECTSCLOSE ) ).setValue( new Integer( pDefectsClose ) );
    }

    /**
     * @param pDefectsAnomaly le nouveau nomnbre de defects en anomaly
     */
    public void setDefectsAnomaly( int pDefectsAnomaly )
    {
        ( (IntegerMetricBO) getMetrics().get( DEFECTSANOMALY ) ).setValue( new Integer( pDefectsAnomaly ) );
    }

    /**
     * @param pDefectsEvolution le nouveau nombre de defects en evolution
     */
    public void setDefectsEvolution( int pDefectsEvolution )
    {
        ( (IntegerMetricBO) getMetrics().get( DEFECTSEVOLUTION ) ).setValue( new Integer( pDefectsEvolution ) );
    }

    /**
     * @param pDefectsHigh le nouveau nombre de defects ayant un niveau élévé
     */
    public void setDefectsHigh( int pDefectsHigh )
    {
        ( (IntegerMetricBO) getMetrics().get( DEFECTSHIGH ) ).setValue( new Integer( pDefectsHigh ) );
    }

    /**
     * @param pDefectsMedium le nouveau nombre de defects ayant un niveau moyen
     */
    public void setDefectsMedium( int pDefectsMedium )
    {
        ( (IntegerMetricBO) getMetrics().get( DEFECTSMEDIUM ) ).setValue( new Integer( pDefectsMedium ) );
    }

    /**
     * @param pDefectsLow le nouveau nombre de defects ayant un niveau bas
     */
    public void setDefectsLow( int pDefectsLow )
    {
        ( (IntegerMetricBO) getMetrics().get( DEFECTSLOW ) ).setValue( new Integer( pDefectsLow ) );
    }
}
