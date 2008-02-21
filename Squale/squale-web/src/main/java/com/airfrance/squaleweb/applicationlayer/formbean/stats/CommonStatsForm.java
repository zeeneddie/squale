package com.airfrance.squaleweb.applicationlayer.formbean.stats;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 */
public class CommonStatsForm extends RootForm {

    /**
     * Le nombre de projets
     */
    protected int mNbProjects;
    
    /**
     * Le nombre de lignes de code
     */
    protected int mLoc;
    
    /**
     * @return le nombre de lignes de codes
     */
    public int getLoc() {
        return mLoc;
    }

    /**
     * @return le nombre de projets
     */
    public int getNbProjects() {
        return mNbProjects;
    }

    /**
     * @param pLoc le nombre de lignes de code
     */
    public void setLoc(int pLoc) {
        mLoc = pLoc;
    }

    /**
     * @param pNbProjects le nombre de projets
     */
    public void setNbProjects(int pNbProjects) {
        mNbProjects = pNbProjects;
    }

}
