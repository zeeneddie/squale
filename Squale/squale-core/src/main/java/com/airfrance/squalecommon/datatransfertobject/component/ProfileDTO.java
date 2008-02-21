/*
 * Créé le 27 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalecommon.datatransfertobject.component;

import java.io.Serializable;
import java.util.Map;

/**
 * @author M400841
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ProfileDTO implements Serializable {

    /**
     * Clé correspondant au nom du profile
     */
    private String mName;
    
    /**
     * Clé des type de droits / Clés des droits 
     */
    private Map mRights;
    
    /**
     * Access method for the mName property.
     * 
     * @return   the current value of the mName property
     * @roseuid 42CB916000A6
     */
    public String getName() {
        return mName;
    }
    
    /**
     * Sets the value of the mName property.
     * 
     * @param pName the new value of the mName property
     * @roseuid 42CB91600100
     */
    public void setName(String pName) {
        mName = pName;
    }
    
    /**
     * Access method for the mRights property.
     * 
     * @return   the current value of the mRights property
     * @roseuid 42CB916000A6
     */
    public Map getRights() {
        return mRights;
    }
    
    /**
     * Sets the value of the mRights property.
     * 
     * @param pRights the new value of the mRights property
     * @roseuid 42CB91600100
     */
    public void setRights(Map pRights) {
        mRights = pRights;
    }

}
