package com.airfrance.squaleweb.applicationlayer.formbean.results;

import com.airfrance.squaleweb.applicationlayer.formbean.ActionIdForm;

/**
 * Résultat d'un facteur pour un projet
 */
public class ProjectFactorForm extends ActionIdForm {
    
    /** Nom du facteur */
    private String mName = "";
    
    /** Note actuelle du facteur */
    private String mCurrentMark = "";
    
    /** Note précédante du facteur */
    private String mPredeccesorMark = "";

    /**
     * @return nom
     */
    public String getName() {
        return mName;
    }

    /**
     * @return la note courante
     */
    public String getCurrentMark() {
        return mCurrentMark;
    }

    /**
     * @return la note courante
     */
    public String getPredecessorMark() {
        return mPredeccesorMark;
    }

    /**
     * @param pString nom
     */
    public void setName(String pString) {
        mName = pString;
    }

    /**
     * @param pString la nouvelle note courante
     */
    public void setCurrentMark(String pString) {
        if (pString != null) {
            mCurrentMark = pString;
        }
    }

    /**
     * @param pString la nouvelle note de l'audit précédant
     */
    public void setPredecessorMark(String pString) {
        if (pString != null) {
            mPredeccesorMark = pString;
        }
    }
}
