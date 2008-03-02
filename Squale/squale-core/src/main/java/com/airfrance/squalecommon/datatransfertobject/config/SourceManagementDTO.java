package com.airfrance.squalecommon.datatransfertobject.config;

/**
 * Récupérateur de sources
 */
public class SourceManagementDTO
    extends TasksUserDTO
{

    /** Audit de jalon possible */
    private boolean mMilestoneAudit;

    /** Audit de suivi possible */
    private boolean mNormalAudit;

    /**
     * Méthode d'accès à mMilestoneAudit
     * 
     * @return true si l'audit de jalon est possible
     */
    public boolean isMilestoneAudit()
    {
        return mMilestoneAudit;
    }

    /**
     * Méthode d'accès à mNormalAudit
     * 
     * @return true si l'audit de suivi est possible
     */
    public boolean isNormalAudit()
    {
        return mNormalAudit;
    }

    /**
     * Change la valeur de mMilestoneAudit
     * 
     * @param pMilestoneAudit la nouvelle valeur de mMilestoneAudit
     */
    public void setMilestoneAudit( boolean pMilestoneAudit )
    {
        mMilestoneAudit = pMilestoneAudit;
    }

    /**
     * Change la valeur de mNormalAudit
     * 
     * @param pNormalAudit la nouvelle valeur de mNormalAudit
     */
    public void setNormalAudit( boolean pNormalAudit )
    {
        mNormalAudit = pNormalAudit;
    }

}
