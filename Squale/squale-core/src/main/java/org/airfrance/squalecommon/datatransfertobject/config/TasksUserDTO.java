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
package com.airfrance.squalecommon.datatransfertobject.config;

import java.util.List;

/**
 * Utilisateur de tâches Squalix
 */
public class TasksUserDTO
{

    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId;

    /** Le nom associé */
    protected String mName;

    /** La liste des tâches d'analyses */
    protected List mAnalysisTasks;

    /** La liste des tâches finales */
    protected List mTerminationTasks;

    /**
     * Méthode d'accès pour mId
     * 
     * @return la l'identifiant (au sens technique) de l'objet
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
     * Méthode d'accès à mAnalysisTasks
     * 
     * @return la liste des tâches d'analyses
     */
    public List getAnalysisTasks()
    {
        return mAnalysisTasks;
    }

    /**
     * Méthode d'accès à mName
     * 
     * @return le nom du profile
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Méthode d'accès à mTerminationTasks
     * 
     * @return la liste des tâches finales
     */
    public List getTerminationTasks()
    {
        return mTerminationTasks;
    }

    /**
     * Change la valeur de mAnalysisTasks
     * 
     * @param pAnalysisTasks la nouvelle liste des tâches d'analyses
     */
    public void setAnalysisTasks( List pAnalysisTasks )
    {
        mAnalysisTasks = pAnalysisTasks;
    }

    /**
     * Change la valeur de mName
     * 
     * @param pName le nouveau nom du profile
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * Change la valeur de mTerminationTasks
     * 
     * @param pTerminationTasks la nouvelle liste de tâches finales
     */
    public void setTerminationTasks( List pTerminationTasks )
    {
        mTerminationTasks = pTerminationTasks;
    }

}
