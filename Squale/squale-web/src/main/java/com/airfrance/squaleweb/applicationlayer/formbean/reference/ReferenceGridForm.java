package com.airfrance.squaleweb.applicationlayer.formbean.reference;

import java.util.Date;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Données du référentiel par grille
 */
public class ReferenceGridForm extends RootForm {

    /** Date de mise à jour */
    private Date mUpdateDate;

    /** Date sous la forme de String */
    private String mFormattedDate = "";

    /** nom de la grille */
    private String mName = "";

    /**
     * @return date de mise à jour formatée
     */
    public String getFormattedDate() {
        return mFormattedDate;
    }

    /**
     * @param pString date de mise à jour formatée
     */
    public void setFormattedDate(String pString) {
        mFormattedDate = pString;
    }

    /**
     * Liste des références (referencesForm)
     */
    private ReferenceListForm mReferenceListForm = new ReferenceListForm();
    /**
     * @return la liste des références
     */
    public ReferenceListForm getReferenceListForm() {
        return mReferenceListForm;
    }

    /**
     * @param pReferenceListForm la liste des références
     */
    public void setReferenceListForm(ReferenceListForm pReferenceListForm) {
        mReferenceListForm = pReferenceListForm;
    }

    /**
     * @return date de mise à jour
     */
    public Date getUpdateDate() {
        return mUpdateDate;
    }

    /**
     * @param pString date de mise à jour
     */
    public void setUpdateDate(Date pString) {
        mUpdateDate = pString;
    }

    /**
     * @return le nom de la grille
     */
    public String getName() {
        return mName;
    }

    /**
     * @param pName le nom de la grille
     */
    public void setName(String pName) {
        mName = pName;
    }

}
