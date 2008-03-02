package com.airfrance.squalecommon.datatransfertobject.stats;

/**
 */
public class CommonStatsDTO
{

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
    public int getLoc()
    {
        return mLoc;
    }

    /**
     * @return le nombre de projets
     */
    public int getNbProjects()
    {
        return mNbProjects;
    }

    /**
     * @param pLoc le nombre de lignes de code
     */
    public void setLoc( int pLoc )
    {
        mLoc = pLoc;
    }

    /**
     * @param pNbProjects le nombre de projets
     */
    public void setNbProjects( int pNbProjects )
    {
        mNbProjects = pNbProjects;
    }

}
