package com.airfrance.squalecommon.enterpriselayer.businessobject.result.umlquality;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;



/**
 * 
 * @hibernate.subclass
 * discriminator-value="UMLQualityInterfaceMetrics"
 */
public class UMLQualityInterfaceMetricsBO extends UMLQualityMetricsBO {

    /** Number of Ancestors: le nombre d'ancêtres de l'Interface */
    private final static String  NUMANC="numAnc";
    /** Number of Clients: le nombre de classes qui implémentent l'Interface.*/
    private final static String  NUMCLIENTS="numClients";
    /**  Number of Operations: le nombre d'opérations dans l'Interface. */
    private final static String  NUMOPS="numOps";

    /**
     * Access method for the NumAnc property.
     * 
     * @return   the current value of the NumAnc property
     * 
     */
    public Integer  getNumAnc() {
        return (Integer) ((IntegerMetricBO) getMetrics().get(NUMANC)).getValue();
    }
    /**
     * Sets the value of the NumAnc property.
     * 
     * @param pNumAnc the new value of the NumAnc property
     * 
     */
    public void setNumAnc(Integer  pNumAnc) {
        ((IntegerMetricBO) getMetrics().get(NUMANC)).setValue(pNumAnc);
    }
    /**
     * Access method for the NumClients property.
     * 
     * @return   the current value of the NumClients property
     *  
     */
    public Integer  getNumClients() {
        return (Integer) ((IntegerMetricBO) getMetrics().get(NUMCLIENTS)).getValue();
    }
    /**
     * Sets the value of the NumClients property.
     * 
     * @param pNumClients the new value of the NumClients property
     * 
     */
    public void setNumClients(Integer  pNumClients) {
        ((IntegerMetricBO) getMetrics().get(NUMCLIENTS)).setValue(pNumClients);
    }
    
    /**
     * Access method for the NumOps property.
     * 
     * @return   the current value of the NumOps property
     *  
     * 
     */
    public Integer  getNumOps() {
        return (Integer) ((IntegerMetricBO) getMetrics().get(NUMOPS )).getValue();
    }
    /**
     * Sets the value of the NumOps property.
     * 
     * @param pNumOps the new value of the NumOps property
     * 
     */
    public void setNumOps(Integer pNumOps) {
        ((IntegerMetricBO) getMetrics().get(NUMOPS)).setValue(pNumOps);
    }

    /**
     * Constructeur par défaut.
     * 
     */
    public UMLQualityInterfaceMetricsBO() {
        super();
        getMetrics().put(NUMANC, new IntegerMetricBO());
        getMetrics().put(NUMCLIENTS, new IntegerMetricBO());
        getMetrics().put(NUMOPS, new IntegerMetricBO());
        
    }
}
