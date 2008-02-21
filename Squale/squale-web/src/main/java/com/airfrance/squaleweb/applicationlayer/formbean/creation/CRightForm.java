/*
 * Créé le 11 août 05, par M400832.
 */
package com.airfrance.squaleweb.applicationlayer.formbean.creation;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * @author M400832
 * @version 1.0
 */
public class CRightForm extends RootForm {
    
    /**
     * Matricule de l'utilisateur.
     */
    private String mMatricule = null;
    
    /**
     * Profil de l'utilisateur.
     */
    private String mProfile = null;
    
    /**
     * @return le matricule de l'utilisateur.
     */
    public String getMatricule() {
        return mMatricule;
    }

    /**
     * @return le profil de l'utilisateur.
     */
    public String getProfile() {
        return mProfile;
    }

    /**
     * @param pMatricule le matricule de l'utilisateur.
     */
    public void setMatricule(String pMatricule) {
        mMatricule = pMatricule;
    }

    /**
     * @param pProfile le profil de l'utilisateur.
     */
    public void setProfile(String pProfile) {
        mProfile = pProfile;
    }

}
