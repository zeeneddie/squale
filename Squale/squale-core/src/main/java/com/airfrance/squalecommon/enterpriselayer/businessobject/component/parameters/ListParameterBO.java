package com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters;

import java.util.ArrayList;
import java.util.List;

/**
 * @hibernate.subclass
 * discriminator-value="List"
 */
public class ListParameterBO extends ProjectParameterBO {
    
    /**
     * La liste de paramètres
     */
    private List mParameters = new ArrayList();
    
    /**
     * @hibernate.list 
     * table="ProjectParameter" 
     * cascade="all"
     * 
     * @hibernate.collection-key 
     * column="ListId"
     * 
     * @hibernate.collection-index 
     * column="Rank" 
     * type="long" 
     * length="19"
     * 
     * @hibernate.collection-one-to-many 
     * class="com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ProjectParameterBO"
     *
     * @return la liste de paramètres
     */
    public List getParameters() {
        return mParameters;
    }

    /**
     * @param pList la nouvelle liste de paramètres
     */
    public void setParameters(List pList) {
        mParameters = pList;
    }

    /** 
     * {@inheritDoc}
     * @param obj
     * @return {@inheritDoc}
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        boolean result = false;
        if(obj instanceof ListParameterBO) {
            result = getParameters().equals(((ListParameterBO)obj).getParameters());
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
