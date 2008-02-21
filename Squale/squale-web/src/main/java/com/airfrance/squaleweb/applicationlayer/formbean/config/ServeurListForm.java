package com.airfrance.squaleweb.applicationlayer.formbean.config;

import com.airfrance.welcom.struts.bean.WActionForm;

import java.util.ArrayList;

/**
 * Transformer de liste de serveurs
 */
public class ServeurListForm extends WActionForm {

    /**
     * Liste des serveurs
     */
    private ArrayList mServeurs = new ArrayList();

    /**
     * @return la liste des serveurs
     */
    public ArrayList getServeurs() {
        return mServeurs;
    }

    /**
     * @param pServeurs la liste des serveurs
     */
    public void setServeurs(ArrayList pServeurs) {
        mServeurs = pServeurs;
    }

}
