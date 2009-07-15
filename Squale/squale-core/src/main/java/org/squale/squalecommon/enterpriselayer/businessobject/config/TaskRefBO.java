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
     * @hibernate.key column="TaskRefId"
     * @hibernate.one-to-many class="com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskParameterBO"
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
     *                        outer-join="auto" update="true" insert="true"
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
