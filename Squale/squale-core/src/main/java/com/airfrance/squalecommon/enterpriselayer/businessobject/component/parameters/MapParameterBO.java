package com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters;

import java.util.HashMap;
import java.util.Map;

/**
 * @hibernate.subclass
 * discriminator-value="Map"
 */
public class MapParameterBO extends ProjectParameterBO {
    
    /**
     * La map de paramètres
     */
    private Map mParameters = new HashMap();
    
    /**
     * @hibernate.map 
     * name="parametersMap" 
     * table="ProjectParameters" 
     * lazy="false" 
     * cascade="all"
     * @hibernate.collection-index 
     * column="Key" 
     * type="string" 
     * @hibernate.collection-key 
     * column="MapId"
     * @hibernate.collection-one-to-many 
     * class="com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ProjectParameterBO" 
     * @return la map de paramètres
     */
    public Map getParameters() {
        return mParameters;
    }

    /**
     * @param pMap la nouvelle map de parametres
     */
    public void setParameters(Map pMap) {
        mParameters = pMap;
    }

    /** 
     * {@inheritDoc}
     * @param obj
     * @return {@inheritDoc}
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        boolean result = false;
        if(obj instanceof MapParameterBO) {
            result = getParameters().equals(((MapParameterBO)obj).getParameters());
        }
        return result;
    }
    /**
     *  
     * {@inheritDoc}
     * @return {@inheritDoc}
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return getParameters() == null ? super.hashCode() : getParameters().hashCode();
    }
}
