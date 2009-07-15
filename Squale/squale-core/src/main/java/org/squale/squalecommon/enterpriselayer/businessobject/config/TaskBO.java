/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.squale.squalecommon.enterpriselayer.businessobject.config;


/**
 * A {@link TaskBO} type object represents a task in Squalix.
 * 
 * @hibernate.class table="Task" lazy="true"
 */
public class TaskBO
{

    /** Identifier of the instance */
    private long mId;

    /** The name of the task */
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
     *                     update="true" insert="true"
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
     *                     update="true" insert="true"
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
     *                     update="true" insert="true"
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
     * @hibernate.property name="standard" column="Standard" type="boolean" not-null="false" unique="false"
     *                     update="true" insert="true"
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
     * @hibernate.property name="mandatory" column="Mandatory" type="boolean" not-null="false" unique="false"
     *                     update="true" insert="true"
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
