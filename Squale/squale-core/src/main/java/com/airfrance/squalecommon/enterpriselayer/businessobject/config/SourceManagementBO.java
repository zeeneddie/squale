package com.airfrance.squalecommon.enterpriselayer.businessobject.config;

/**
 * Récupérateur de sources
 * 
 * @hibernate.subclass
 * lazy="false"
 * discriminator-value="SourceManagement"
 * 
 */
public class SourceManagementBO extends AbstractTasksUserBO {
    
    /** Audit de jalon possible */
    private boolean mMilestoneAudit;
    
    /** Audit de suivi possible */
    private boolean mNormalAudit;
    
    /**
     * Constructeur par défaut
     */
    public SourceManagementBO() {
        super();
        mMilestoneAudit = true;
        mNormalAudit = true;
    }
    
    /**
     * Méthode d'accès à mMilestoneAudit
     * 
     * @return true si l'audit de jalon est possible
     * 
     * @hibernate.property 
     * name="milestoneAudit" 
     * column="MilestoneAudit" 
     * type="boolean" 
     * 
     */
    public boolean isMilestoneAudit() {
        return mMilestoneAudit;
    }

    /**
     * Méthode d'accès à mNormalAudit
     * 
     * @return true si l'audit de suivi est possible
     * 
     * @hibernate.property 
     * name="normalAudit" 
     * column="NormalAudit" 
     * type="boolean" 
     * 
     */
    public boolean isNormalAudit() {
        return mNormalAudit;
    }

    /**
     * Change la valeur de mMilestoneAudit
     * 
     * @param pMilestoneAudit la nouvelle valeur de mMilestoneAudit
     */
    public void setMilestoneAudit(boolean pMilestoneAudit) {
        mMilestoneAudit = pMilestoneAudit;
    }

    /**
      * Change la valeur de mNormalAudit
      * 
      * @param pNormalAudit la nouvelle valeur de mNormalAudit
      */
    public void setNormalAudit(boolean pNormalAudit) {
        mNormalAudit = pNormalAudit;
    }

}
