package com.airfrance.squalecommon.enterpriselayer.businessobject.config;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Référence de tâche *
 * 
 * @hibernate.class table="TaskRef" lazy="true"
 */
public class TaskRefBO
{
    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId = -1;

    /**
     * Tâche
     */
    protected TaskBO mTask;

    /**
     * Paramètres de la tâche
     */
    private Collection mParameters = new ArrayList();

    /**
     * Méthode d'accès pour mId
     * 
     * @return la l'identifiant (au sens technique) de l'objet
     * @hibernate.id generator-class="native" type="long" column="TaskRefId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="taskRef_sequence"
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
     * Récupère la collection des paramètres
     * 
     * @return les paramètres
     * @hibernate.bag table="TaskParameter" lazy="true" cascade="all"
     * @hibernate.collection-key column="TaskRefId"
     * @hibernate.collection-one-to-many class="com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskParameterBO"
     */
    public Collection getParameters()
    {
        return mParameters;
    }

    /**
     * @param pParameters paramètres
     */
    public void setParameters( Collection pParameters )
    {
        mParameters = pParameters;
    }

    /**
     * Ajout d'un Parameter
     * 
     * @param pName nom
     * @param pValue valeur
     */
    public void addParameter( String pName, String pValue )
    {
        TaskParameterBO arg = new TaskParameterBO();
        arg.setName( pName );
        arg.setValue( pValue );
        getParameters().add( arg );
    }

    /**
     * @return tâche
     * @hibernate.many-to-one column="TaskId" cascade="all"
     *                        class="com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskBO"
     */
    public TaskBO getTask()
    {
        return mTask;
    }

    /**
     * @param pTask tâche
     */
    public void setTask( TaskBO pTask )
    {
        mTask = pTask;
    }
}
