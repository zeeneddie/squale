/*
 * Créé le 11 août 05, par M400832.
 */
package com.airfrance.squaleweb.applicationlayer.formbean.creation;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * @author M400832
 * @version 1.0
 */
public class CApplicationForm
    extends RootForm
{

    /**
     * Délai de purge par défaut
     */
    private static final int DEFAULT_PURGE_DELAY = 180;

    /**
     * Fréquence d'audit par défaut
     */
    private static final int DEFAULT_AUDIT_FREQUENCY = 15;

    /**
     * Nom de l'application = nom de l'application AF.
     */
    private String mName = null;

    /**
     * Booléan permettant de savoir si l'on ne doit faire que des audits de type Milestone = jalon.
     */
    private boolean mMilestoneOnly = false;

    /**
     * Entité Air France : Valbonne / Vilgénis / Toulouse.
     */
    private String mAFLocation = null;

    /**
     * Bean de droits.
     */
    private CRightListForm mRLF = null;

    /**
     * Délai entre deux purges de l'application
     */
    private int mPurgeDelay = DEFAULT_PURGE_DELAY;

    /**
     * Délai entre deux purges de l'application
     */
    private int mAuditFrequency = DEFAULT_AUDIT_FREQUENCY;

    /**
     * Get name
     * 
     * @return le nom de l'application
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Set name
     * 
     * @param pName le nom de l'application
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * @return le bean contenant la liste de droits.
     */
    public CRightListForm getRightListForm()
    {
        return mRLF;
    }

    /**
     * @return le délai de purge
     */
    public int getPurgeDelay()
    {
        return mPurgeDelay;
    }

    /**
     * @param pRLF le bean de droits.
     */
    public void setRightListForm( CRightListForm pRLF )
    {
        mRLF = pRLF;
    }

    /**
     * @param pPurgeDelay le délai de purge (en jour)
     */
    public void setPurgeDelay( int pPurgeDelay )
    {
        mPurgeDelay = pPurgeDelay;
    }

    /**
     * @return la fréquence d'audit en jours
     */
    public int getAuditFrequency()
    {
        return mAuditFrequency;
    }

    /**
     * @param pAuditFrequency la fréquence d'audit en jours
     */
    public void setAuditFrequency( int pAuditFrequency )
    {
        mAuditFrequency = pAuditFrequency;
    }

    /**
     * @return la valeur de mMilestoneOnly.
     */
    public boolean isMilestoneOnly()
    {
        return mMilestoneOnly;
    }

    /**
     * @param pMilestoneOnly la nouvelle valeur de mMilestoneOnly.
     */
    public void setMilestoneOnly( boolean pMilestoneOnly )
    {
        mMilestoneOnly = pMilestoneOnly;
    }

    /**
     * @return le site de l'application
     */
    public String getAFLocation()
    {
        return mAFLocation;
    }

    /**
     * @param pAFLocation le site de l'application
     */
    public void setAFLocation( String pAFLocation )
    {
        mAFLocation = pAFLocation;
    }
}
