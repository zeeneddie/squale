package com.airfrance.squaleweb.applicationlayer.formbean.creation;

import java.util.Date;

import com.airfrance.squaleweb.applicationlayer.formbean.component.FactorListForm;
import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Configuration d'une grille qualité
 */
public class GridConfigForm extends RootForm {
    /**
     * Id de la grille
     */
    private long mId;
    /**
     * Nom de la grille
     */
    private String mName = "";
    /** Date de mise à jour */
    private Date mUpdateDate;
    /**
     * @return l'id.
     */
    public long getId() {
        return mId;
    }
    /**
     * @return le nom.
     */
    public String getName() {
        return mName;
    }
    /**
     * @return date de mise à jour
     */
    public Date getUpdateDate() {
        return mUpdateDate;
    }
    /**
     * @param pId l'id.
     */
    public void setId(long pId) {
        mId = pId;
    }
    /**
     * @param pName le nom.
     */
    public void setName(String pName) {
        mName = pName;
    }
    /**
     * @param pString date de mise à jour
     */
    public void setUpdateDate(Date pString) {
        mUpdateDate = pString;
    }
    /** Facteurs associés */
    private FactorListForm mFactors;
    
    /**
     * @return facteurs
     */
    public FactorListForm getFactors() {
        return mFactors;
    }

    /**
     * @param pFactors facteurs
     */
    public void setFactors(FactorListForm pFactors) {
        mFactors = pFactors;
    }
}
