//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squaleCommon\\src\\com\\airfrance\\squalecommon\\enterpriselayer\\businessobject\\result\\mccabe\\McCabeProjectMetricsBO.java

package com.airfrance.squalecommon.enterpriselayer.businessobject.result.mccabe;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;

/**
 * @author m400842 (by rose)
 * @version 1.0
 * 
 * @hibernate.subclass
 * discriminator-value="ProjectMetrics"
 */
public final class McCabeQAProjectMetricsBO extends McCabeQAMetricsBO {
    
    /**
     * Nombre de classes dans le sous-projet
     */
    private final static String NUMBEROFCLASSES="numberOfClasses";

    /**
     * Nombre de méthodes dans le sous-projet.
     */
    private final static String NUMBEROFMETHODS="numberOfMethods";

    /**
     * Constructeur
     * @roseuid 42B9751A0293
     */
    public McCabeQAProjectMetricsBO() {
        super();
        getMetrics().put(NUMBEROFCLASSES, new IntegerMetricBO());
        getMetrics().put(NUMBEROFMETHODS, new IntegerMetricBO());
    }

    /**
     * Access method for the mNumberOfClasses property.
     * 
     * @return   the current value of the mNumberOfClasses property
     * 
     * @roseuid 42C416B702BC
     */
    public Integer getNumberOfClasses() {
        return (Integer) ((IntegerMetricBO) getMetrics().get(NUMBEROFCLASSES)).getValue();
    }

    /**
     * Sets the value of the mNumberOfClasses property.
     * 
     * @param pNumberOfClasses the new value of the mNumberOfClasses property
     * @roseuid 42C416B702DB
     */
    public void setNumberOfClasses(Integer pNumberOfClasses) {
        ((IntegerMetricBO) getMetrics().get(NUMBEROFCLASSES)).setValue(pNumberOfClasses);
    }

    /**
     * Access method for the mNumberOfMethods property.
     * 
     * @return   the current value of the mNumberOfMethods property
     * 
     * @roseuid 42C416B70339
     */
    public Integer getNumberOfMethods() {
        return (Integer) ((IntegerMetricBO) getMetrics().get(NUMBEROFMETHODS)).getValue();
    }

    /**
     * Sets the value of the mNumberOfMethods property.
     * 
     * @param pNumberOfMethods the new value of the mNumberOfMethods property
     * @roseuid 42C416B70367
     */
    public void setNumberOfMethods(Integer pNumberOfMethods) {
        ((IntegerMetricBO) getMetrics().get(NUMBEROFMETHODS)).setValue(pNumberOfMethods);
    }

}
