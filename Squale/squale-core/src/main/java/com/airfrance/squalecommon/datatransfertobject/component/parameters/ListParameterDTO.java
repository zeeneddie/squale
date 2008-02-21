package com.airfrance.squalecommon.datatransfertobject.component.parameters;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class ListParameterDTO {

    /**
     * La liste de paramètres
     */
    private List mParameters = new ArrayList();

    /**
     * Identifiant de l'objet
     */
    private long mId = -1;
    
    /**
     * Access method for the mId property.
     * 
     * @return   the current value of the mId property
     */
    public long getId() {
        return mId;
    }

    /**
     * Sets the value of the mId property.
     * 
     * @param pId the new value of the mId property
     */
    public void setId(long pId) {
        mId = pId;
    }

    /**
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

}
