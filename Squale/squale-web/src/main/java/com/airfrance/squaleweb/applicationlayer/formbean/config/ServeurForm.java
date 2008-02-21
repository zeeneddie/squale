package com.airfrance.squaleweb.applicationlayer.formbean.config;

import com.airfrance.welcom.struts.bean.WActionForm;

/**
 * Formulaire d'un serveur
 */
public class ServeurForm extends WActionForm {

    /**
     * Id du serveur
     */
    private long mServeurId = -1;
    
    /**
     * Nom du serveur
     */
    private String mName = "";
    
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
