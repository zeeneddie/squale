package com.airfrance.squalecommon.enterpriselayer.businessobject.result.cobertura;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.FloatMetricBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MeasureBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.StringMetricBO;

/**
 * <p>
 * This abstract class is used as a model which has to be sub-classed by any classes written in order to recover metrics
 * from the Cobertura xml result file.
 * </p>
 * <p>
 * Please note that all setters are final as they are not intended to be overridden.
 * </p>
 * 
 * @hibernate.subclass discriminator-value="CoberturaMetrics"
 */
public class AbstractCoberturaMetricsBO
    extends MeasureBO
{
    /** The "line-rate key" whatever the level of the metric (i.e Project, Package, Class, Method) */
    private static final String LINE_RATE = "lineRate";

    /** The "branch-rate key" whatever the level of the metric (i.e Project, Package, Class, Method) */
    private static final String BRANCH_RATE = "branchRate";

    /**
     * The "item name key" whatever the level of the metric (i.e Project, Package, Class, Method) This property is not
     * intended to be pushed in DB
     */
    private static final String ITEM_NAME = "name";

    /**
     * The "item name key" whatever the level of the metric (i.e Project, Package, Class, Method). This property is not
     * persisted in DB
     */
    private String name;

    /**
     * The default constructor
     */
    public AbstractCoberturaMetricsBO()
    {
        super();
        /* Putting key and mapped value into the map called mMetrics in MeasureBO */
        getMetrics().put( LINE_RATE, new FloatMetricBO() );
        getMetrics().put( BRANCH_RATE, new FloatMetricBO() );
        // getMetrics().put( ITEM_NAME, new StringMetricBO() );
    }

    /*----------------------------------------------- Getters -----------------------------------------------*/
    /**
     * <p>
     * Getter of the lineRate value.
     * </p>
     * 
     * @return the value of the lineRate metric
     */
    public Float getLineRate()
    {
        return (Float) ( (FloatMetricBO) getMetrics().get( LINE_RATE ) ).getValue();
    }

    /**
     * Getter of the branchRate value
     * 
     * @return the value of the branchRate metric
     */
    public Float getBranchRate()
    {
        return (Float) ( (FloatMetricBO) getMetrics().get( BRANCH_RATE ) ).getValue();
    }

    /**
     * Getter of the name value
     * 
     * @return the name of the audited object
     */
    public String getName()
    {
        // return (String) ( (StringMetricBO) getMetrics().get( ITEM_NAME ) ).getValue();
        return name;
    }

    /*----------------------------------------------- Setters -----------------------------------------------*/
    /**
     * Setter of the lineRate metric
     * <p>
     * The name of the line-rate setter has been simplified to "setLine" so as to avoid any error during the mapping in
     * the xml configuration file (please see coberturaParserXmlRules.xml in squalix/src/assembly/resources/config for
     * more info)
     * </p>
     * 
     * @param pLineRate rate of the line coverage
     */
    public final void setLine( float pLineRate )
    {
        ( (FloatMetricBO) getMetrics().get( LINE_RATE ) ).setValue( pLineRate );
    }

    /**
     * Setter of the branchRate metric
     * <p>
     * The name of the line-rate setter has been simplified to "setBranch" so as to avoid any error during the mapping
     * in the xml configuration file (please see coberturaParserXmlRules.xml in squalix/src/assembly/resources/config
     * for more info)
     * </p>
     * 
     * @param pBranchRate rate of the branch coverage
     */
    public final void setBranch( float pBranchRate )
    {
        ( (FloatMetricBO) getMetrics().get( BRANCH_RATE ) ).setValue( pBranchRate );
    }

    /**
     * Setter of the name
     * 
     * @param pName name of the object that is audited
     */
    public final void setName( String pName )
    {
        this.name = pName;
    }
}
