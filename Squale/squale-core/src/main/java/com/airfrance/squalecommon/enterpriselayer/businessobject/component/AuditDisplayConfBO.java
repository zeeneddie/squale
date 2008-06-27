package com.airfrance.squalecommon.enterpriselayer.businessobject.component;

import com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.AbstractDisplayConfBO;

/**
 * Configurations d'affichage à prendre pour un audit donné
 * 
 * @hibernate.class table="AuditDisplayConfBO" mutable="true"
 */
public class AuditDisplayConfBO
{
    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId = -1;

    /** Projet */
    private ProjectBO mProject;

    /** la configuration */
    private AbstractDisplayConfBO mDisplayConf;

    /** Audit (lien inverse) */
    private AuditBO mAudit;

    /**
     * @return l'identifiant (au sens technique) de l'objet
     * @hibernate.id generator-class="native" type="long" column="AuditConfId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="auditconf_sequence"
     * @roseuid 42BFDEB701B4
     */
    public long getId()
    {
        return mId;
    }

    /**
     * @param pId l'dentifiant (au sens technique) de l'objet
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * @return la configuration
     * @hibernate.many-to-one class="com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.AbstractDisplayConfBO"
     *                        column="ConfId" not-null="false" lazy="false" cascade="none" outer-join="auto"
     *                        update="true" insert="true" 
     */
    public AbstractDisplayConfBO getDisplayConf()
    {
        return mDisplayConf;
    }

    /**
     * @return le projet
     * @hibernate.many-to-one class="com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO"
     *                        column="ProjectId" not-null="false" cascade="none" outer-join="auto" update="true"
     *                        insert="true" 
     */
    public ProjectBO getProject()
    {
        return mProject;
    }

    /**
     * @param pDisplayConf la configuration
     */
    public void setDisplayConf( AbstractDisplayConfBO pDisplayConf )
    {
        mDisplayConf = pDisplayConf;
    }

    /**
     * @param pProjectBO projet
     */
    public void setProject( ProjectBO pProjectBO )
    {
        mProject = pProjectBO;
    }

    /**
     * @return l'audit
     * @hibernate.many-to-one column="AuditId"
     *                        class="com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO"
     *                        cascade="none" outer-join="auto" update="true" insert="true"
     */
    public AuditBO getAudit()
    {
        return mAudit;
    }

    /**
     * @param pAuditBO audit
     */
    public void setAudit( AuditBO pAuditBO )
    {
        mAudit = pAuditBO;
    }

}
