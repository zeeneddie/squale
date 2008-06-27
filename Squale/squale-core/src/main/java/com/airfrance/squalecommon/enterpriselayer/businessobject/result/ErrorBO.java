//Source file: D:\\cc_views\\squale_v0_0_act_M400843\\squale\\src\\squaleCommon\\src\\com\\airfrance\\squalecommon\\enterpriselayer\\businessobject\\result\\ErrorBO.java

package com.airfrance.squalecommon.enterpriselayer.businessobject.result;

import java.io.Serializable;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;

/**
 * @author m400842 (by rose)
 * @version 1.0
 * @hibernate.class table="Error" mutable="true"
 */
public class ErrorBO
    implements Serializable
{
    /** Maximum for a task */
    public static final int NB_MAX_ERRORS = 500;

    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId;

    /**
     * Message initial de l'outil d'analyse
     */
    private String mInitialMessage;

    /**
     * Message traduit en version Squale si possible : il s'agit en fait de sa clé d'internationalisation.
     */
    private String mMessage;

    /**
     * Niveau de criticité
     */
    private String mLevel;

    /**
     * Nom de la tâche source
     */
    private String mTaskName;

    /**
     * Audit durant lequel l'erreur a été générée
     */
    private AuditBO mAudit;

    /**
     * Projet sur lequel l'erreur a été générée
     */
    private ProjectBO mProject;

    /**
     * Niveau de criticité maximal
     */
    public static final String CRITICITY_FATAL = "error.criticity.fatal";

    /**
     * Niveau de criticité des avertissements
     */
    public static final String CRITICITY_WARNING = "error.criticity.warning";

    /**
     * Niveau de criticité avec un impact mineur
     */
    public static final String CRITICITY_LOW = "error.criticity.low";

    /**
     * Access method for the mInitialMessage property.
     * 
     * @return the current value of the mInitialMessage property
     * @hibernate.property name="initialMessage" column="InitialMessage" type="string" length="2048" not-null="false"
     *                     unique="false" update="true" insert="true"
     * @roseuid 42BACEE203CF
     */
    public String getInitialMessage()
    {
        return mInitialMessage;
    }

    /**
     * Sets the value of the mInitialMessage property.
     * 
     * @param pInitialMessage the new value of the mInitialMessage property
     * @roseuid 42BACEE203D0
     */
    public void setInitialMessage( String pInitialMessage )
    {
        mInitialMessage = pInitialMessage;
    }

    /**
     * Access method for the mMessage property.
     * 
     * @return the current value of the mMessage property
     * @hibernate.property name="message" column="Message" type="string" length="2048" not-null="false" unique="false"
     *                     update="true" insert="true"
     * @roseuid 42BACEE203DF
     */
    public String getMessage()
    {
        return mMessage;
    }

    /**
     * Sets the value of the mMessage property.
     * 
     * @param pMessage the new value of the mMessage property
     * @roseuid 42BACEE203E0
     */
    public void setMessage( String pMessage )
    {
        mMessage = pMessage;
    }

    /**
     * Access method for the mLevel property.
     * 
     * @return the current value of the mLevel property
     * @hibernate.property name="level" column="CriticityLevel" type="string" update="true" insert="true"
     * @roseuid 42BACEE30006
     */
    public String getLevel()
    {
        return mLevel;
    }

    /**
     * Sets the value of the mLevel property.
     * 
     * @param pLevel the new value of the mLevel property
     * @roseuid 42BACEE30007
     */
    public void setLevel( String pLevel )
    {
        mLevel = pLevel;
    }

    /**
     * Access method for the mTaskName property.
     * 
     * @return the current value of the mTaskName property
     * @hibernate.property name="taskName" column="TaskName" type="string" update="true" insert="true"
     * @roseuid 42BACEE30016
     */
    public String getTaskName()
    {
        return mTaskName;
    }

    /**
     * Sets the value of the mTaskName property.
     * 
     * @param pTaskName the new value of the mTaskName property
     * @roseuid 42BACEE30017
     */
    public void setTaskName( String pTaskName )
    {
        mTaskName = pTaskName;
    }

    /**
     * Access method for the mAudit property.
     * 
     * @return the current value of the mAudit property
     * @hibernate.many-to-one column="AuditId"
     *                        class="com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO"
     *                        cascade="none" not-null="true" outer-join="auto" update="true" insert="true"
     * @roseuid 42BACEE30025
     */
    public AuditBO getAudit()
    {
        return mAudit;
    }

    /**
     * Sets the value of the mAudit property.
     * 
     * @param pAudit the new value of the mAudit property
     * @roseuid 42BACEE30026
     */
    public void setAudit( AuditBO pAudit )
    {
        mAudit = pAudit;
    }

    /**
     * Access method for the mId property.
     * 
     * @return the current value of the mId property Note: unsaved-value An identifier property value that indicates
     *         that an instance is newly instantiated (unsaved), distinguishing it from transient instances that were
     *         saved or loaded in a previous session. If not specified you will get an exception like this: another
     *         object associated with the session has the same identifier
     * @hibernate.id generator-class="native" type="long" column="ErrorId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="error_sequence"
     * @roseuid 42BFF47A02DF
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Sets the value of the mId property.
     * 
     * @param pId the new value of the mId property
     * @roseuid 42BFF47A02FE
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * Access method for the mProject property.
     * 
     * @return the current value of the mProject property
     * @hibernate.many-to-one column="ProjectId"
     *                        class="com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO"
     *                        cascade="none" not-null="true" outer-join="auto" update="true" insert="true"
     * @roseuid 42BFF47A033C
     */
    public ProjectBO getProject()
    {
        return mProject;
    }

    /**
     * Sets the value of the mProject property.
     * 
     * @param pProject the new value of the mProject property
     * @roseuid 42BFF47A034C
     */
    public void setProject( ProjectBO pProject )
    {
        mProject = pProject;
    }

    /**
     * Constructeur par défaut
     * 
     * @roseuid 42CA43BA03D8
     */
    public ErrorBO()
    {
        mId = -1;
    }

    /**
     * Constructeur complet
     * 
     * @param pInitialMessage le message d'erreur initial (généré par l'outil).
     * @param pMessage le message d'erreur.
     * @param pLevel le niveau de l'erreur.
     * @param pTaskName le nom de la tache ayant généré l'erreur
     * @param pAudit l'audit concerné.
     * @param pProject le projet concerné.
     * @roseuid 42CA43BB000F
     */
    public ErrorBO( String pInitialMessage, String pMessage, String pLevel, String pTaskName, AuditBO pAudit,
                    ProjectBO pProject )
    {
        mId = -1;
        mInitialMessage = pInitialMessage;
        mMessage = pMessage;
        mLevel = pLevel;
        mTaskName = pTaskName;
        mAudit = pAudit;
        mProject = pProject;
    }
}
