package com.airfrance.squalecommon.datatransfertobject.config;

/**
 * Serveur d'exécution de Squalix
 */
public class ServeurDTO {

    /**
     * Id du serveur
     */
    private long mServeurId;
    
    /**
     * Nom du serveur
     */
    private String mName;
    
    /**
     * @return l'id du serveur
     */
    public long getServeurId() {
        return mServeurId;
    }

    /**
     * @return le nom du serveur
     */
    public String getName() {
        return mName;
    }

    /**
     * @param pId l'id du serveur 
     */
    public void setServeurId(long pId) {
        mServeurId = pId;
    }

    /**
     * @param pName le nom du serveur
     */
    public void setName(String pName) {
        mName = pName;
    }

}
