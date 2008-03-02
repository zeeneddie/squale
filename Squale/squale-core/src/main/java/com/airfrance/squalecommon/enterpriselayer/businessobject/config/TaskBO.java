package com.airfrance.squalecommon.enterpriselayer.businessobject.config;

/**
 * Tâche squalix
 * 
 * @hibernate.class table="Task" lazy="true"
 */
public class TaskBO
{

    /**
     * Identifiant (au sens technique) de l'objet
     */
    private long mId;

    /** Le nom de la tâche */
    private String mName;

    /** La classe correspondante à la tâche */
    private String mClassName;

    /** Configuration possible de la tâche */
    private boolean mConfigurable;

    /** Configuration possible de la tâche */
    private boolean mStandard;

    /** indique si la tâche est obligatoire */
    private boolean mMandatory;

    /**
     * Constructeur par défaut
     */
    public TaskBO()
    {
        mId = -1;
        mName = "";
        mClassName = "";
        mConfigurable = true;
        mStandard = false;
        mMandatory = false;
    }

    /**
     * Méthode d'accès pour mId
     * 
     * @return la l'identifiant (au sens technique) de l'objet
     * @hibernate.id generator-class="native" type="long" column="TaskId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="task_sequence"
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Change la valeur de mId
     * 
     * @param pId le nouvel identifiant
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * Méthode d'accès à mName
     * 
     * @return le nom de la tâche
     * @hibernate.property name="name" column="Name" type="string" length="255" not-null="true" unique="true"
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Change la valeur de mName
     * 
     * @param pName le nouveau nom de la tâche
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * Méthode d'accès à mClassName
     * 
     * @return le nom de la classe associée à la tâche
     * @hibernate.property name="class" column="Class" type="string" length="2048" not-null="true" unique="false"
     */
    public String getClassName()
    {
        return mClassName;
    }

    /**
     * Change la valeur de mClassName
     * 
     * @param pClassName la nouvelle classe associée
     */
    public void setClassName( String pClassName )
    {
        mClassName = pClassName;
    }

    /**
     * Méthode d'accès à mConfigurable
     * 
     * @return true si la tâche est configurable
     * @hibernate.property name="configurable" column="Configurable" type="boolean" not-null="true" unique="false"
     */
    public boolean isConfigurable()
    {
        return mConfigurable;
    }

    /**
     * Change la valeur de mConfigurable
     * 
     * @param pConfigurable la nouvelle valeur de mConfigurable
     */
    public void setConfigurable( boolean pConfigurable )
    {
        mConfigurable = pConfigurable;
    }

    /**
     * @return true si la tâche fait partie de la configuration minimum d'un projet
     * @hibernate.property name="standard" column="Standard" type="boolean" not-null="true" unique="false"
     */
    public boolean isStandard()
    {
        return mStandard;
    }

    /**
     * @param pStandard true si la tâche fait partie de la configuration minimum d'un projet
     */
    public void setStandard( boolean pStandard )
    {
        mStandard = pStandard;
    }

    /**
     * @return true si la tâche est obligatoire
     * @hibernate.property name="mandatory" column="Mandatory" type="boolean" not-null="true" unique="false"
     */
    public boolean isMandatory()
    {
        return mMandatory;
    }

    /**
     * @param pMandatory true si la tâche est obligatoire
     */
    public void setMandatory( boolean pMandatory )
    {
        mMandatory = pMandatory;
    }

}
