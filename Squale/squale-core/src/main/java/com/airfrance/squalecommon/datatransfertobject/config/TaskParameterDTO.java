package com.airfrance.squalecommon.datatransfertobject.config;

/**
 * Paramètre de tâche
 */
public class TaskParameterDTO {
    /**
     * Nom du paramètre
     */
    private String mName;
    /**
     * Valeur du paramètre
     */
    private String mValue;

    /**
     * Méthode d'accès à mName
     * 
     * @return le nom
     */
    public String getName() {
        return mName;
    }
    
    /**
     * 
     * @param pName nom
     */
    public void setName(String pName) {
        mName = pName;
    }
    
    
    /**
     * Méthode d'accès à mValue
     * 
     * @return la valeur
     */
    public String getValue() {
        return mValue;
    }
    
    /**
     * 
     * @param pValue nom
     */
    public void setValue(String pValue) {
        mValue = pValue;
    }

}
