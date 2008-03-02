package com.airfrance.squaleweb.applicationlayer.formbean.stats;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 */
public class FactorsStatsForm
    extends RootForm
{

    /** le nombre de facteurs acceptés */
    private int mNbFactorsAccepted;

    /** le nombre de facteurs acceptés avec réserves */
    private int mNbFactorsReserved;

    /** le nombre de facteurs refusés */
    private int mNbFactorsRefused;

    /** le total */
    private int mNbTotal;

    /**
     * @return le nombre de facteurs acceptés
     */
    public int getNbFactorsAccepted()
    {
        return mNbFactorsAccepted;
    }

    /**
     * @return le nombre de facteurs refusés
     */
    public int getNbFactorsRefused()
    {
        return mNbFactorsRefused;
    }

    /**
     * @return le nombre de facteurs acceptés avec réserves
     */
    public int getNbFactorsReserved()
    {
        return mNbFactorsReserved;
    }

    /**
     * @param pNbAccepted le nombre de facteurs acceptés
     */
    public void setNbFactorsAccepted( int pNbAccepted )
    {
        mNbFactorsAccepted = pNbAccepted;
    }

    /**
     * @param pNbRefused le nombre de facteurs refusés
     */
    public void setNbFactorsRefused( int pNbRefused )
    {
        mNbFactorsRefused = pNbRefused;
    }

    /**
     * @param pReserved le nombre de facteurs réservés
     */
    public void setNbFactorsReserved( int pReserved )
    {
        mNbFactorsReserved = pReserved;
    }

    /**
     * @return le total
     */
    public int getNbTotal()
    {
        return mNbTotal;
    }

    /**
     * @param pTotal le total
     */
    public void setNbTotal( int pTotal )
    {
        mNbTotal = pTotal;
    }

}
