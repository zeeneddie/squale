package com.airfrance.squalecommon.enterpriselayer.businessobject.result.umlquality;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MeasureBO;

/**
 * 
 * @hibernate.subclass
 * discriminator-value="UMLQualityMetrics"
 */

public class UMLQualityMetricsBO extends MeasureBO implements Comparable{

    /**
     * Le nom complet(FQN)du composant
     *
     * Cet attribut n'est pas persistant, il représente le nom du composant auquel 
     * sont liées les métriques.<br/>
     * L'adaptateur utilise ce nom pour déterminer l'instance de AbstractComponentBO 
     * correspondante et créer la relation.
     */
     
    private String mComponentName;
    
    /**
     * Constructeur par défaut.
     * 
     */
    public UMLQualityMetricsBO(){
        super();
    }
    
    /**
     * Access method for the mName property.
     * 
     * @return   the current value of the component Name 
     * 
     */
    public String getComponentName() {
        return mComponentName;
    }
    
    /**
     * Sets the value of the mName property.
     * 
     * @param pComponentName the new value of the component Name 
     * 
     */
    public void setName(String pComponentName) {
        mComponentName=pComponentName;
    }
    
    /**(non-Javadoc)
     * @see java.lang.Comparable.compareTo
     */
    public int compareTo(Object o) {
        
      int result = 0;
       if (o instanceof UMLQualityMetricsBO) {
           UMLQualityMetricsBO metric = (UMLQualityMetricsBO) o;
           if ((metric.getComponentName() != null) && (getComponentName() != null)) {
               result = mComponentName.compareTo(metric.getComponentName());
           }
       }
       return result;
    }
}
