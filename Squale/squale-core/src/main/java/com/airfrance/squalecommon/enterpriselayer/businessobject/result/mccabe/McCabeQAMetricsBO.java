//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squaleCommon\\src\\com\\airfrance\\squalecommon\\enterpriselayer\\businessobject\\result\\mccabe\\McCabeMetricsBO.java

package com.airfrance.squalecommon.enterpriselayer.businessobject.result.mccabe;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MeasureBO;

/**
 * @author m400842 (by rose)
 * @version 1.0
 * 
 * @hibernate.subclass
 * discriminator-value="McCabeQAMetrics"
 */
public abstract class McCabeQAMetricsBO extends MeasureBO {
    
    /**
     * Cet attribut n'est pas persistant, il représente le nom du composant auquel 
     * sont liées les données.<br>
     * L'adaptateur utilise ce nom pour déterminer l'instance de AbstractComponentBO 
     * correspondante et créer la relation.
     */
    protected String mComponentName;
    
    /**
     * Access method for the mComponentName property.
     * 
     * @return   the current value of the mComponentName property
     * @roseuid 42C416B60319
     */
    public String getComponentName() {
        return mComponentName;
    }
    
    /**
     * Sets the value of the mComponentName property.
     * 
     * @param pComponentName the new value of the mComponentName property
     * @roseuid 42C416B60329
     */
    public void setComponentName(String pComponentName) {
        mComponentName = pComponentName;
    }
}
