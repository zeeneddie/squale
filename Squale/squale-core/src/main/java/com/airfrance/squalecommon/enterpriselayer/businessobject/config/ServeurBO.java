package com.airfrance.squalecommon.enterpriselayer.businessobject.config;

/**
 * Serveur d'exécution de Squalix
 *
 * @hibernate.class 
 * table="Serveur"
 * lazy="true"
 */
public class ServeurBO {

    /**
     * Id du serveur
     */
    private long mServeurId = -1;
    
    /**
     * Nom du serveur
     */
    private String mName;
    
    /**
     * @return l'id du serveur
     * 
     * @hibernate.id generator-class="assigned"
     * name="id" 
     * column="ServeurId" 
     * type="long" 
     * length="19"
     * unsaved-value="-1"
     */
    public long getServeurId() {
        return mServeurId;
    }

    /**
     * @return le nom du serveur
     * 
     * @hibernate.property
     * name="name" 
     * column="Name" 
     * type="string" 
     * length="256"
     * not-null="true" 
     * unique="true"
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
