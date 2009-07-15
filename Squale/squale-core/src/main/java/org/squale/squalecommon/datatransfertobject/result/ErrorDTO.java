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
//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squaleCommon\\src\\org\\squale\\squalecommon\\datatransfertobject\\ErrorDTO.java

package org.squale.squalecommon.datatransfertobject.result;

import java.io.Serializable;

/**
 */
public class ErrorDTO
    implements Serializable
{

    /**
     * Clé du message d'erreur
     */
    private String mMessageKey;

    /**
     * Clé du niveau de criticité de l'erreur
     */
    private String mLevel;

    /**
     * Clé représentant le nom de la page
     */
    private String mTaskName;

    /**
     * Identifiant de l'audit
     */
    private long mAuditId;

    /**
     * Identifiant du projet
     */
    private long mProjectId;

    /**
     * Constructeur par defaut
     * 
     * @roseuid 42CB92090181
     */
    public ErrorDTO()
    {
        mAuditId = -1;
        mProjectId = -1;
        mTaskName = null;
    }

    /**
     * Access method for the mMessageKey property.
     * 
     * @return the current value of the mMessageKey property
     * @roseuid 42CB920901A9
     */
    public String getMessageKey()
    {
        return mMessageKey;
    }

    /**
     * Sets the value of the mMessageKey property.
     * 
     * @param pMessageKey the new value of the mMessageKey property
     * @roseuid 42CB920901EF
     */
    public void setMessageKey( String pMessageKey )
    {
        mMessageKey = pMessageKey;
    }

    /**
     * Access method for the mLevel property.
     * 
     * @return the current value of the mLevel property
     * @roseuid 42CB92090249
     */
    public String getLevel()
    {
        return mLevel;
    }

    /**
     * Sets the value of the mLevel property.
     * 
     * @param pLevel the new value of the mLevel property
     * @roseuid 42CB9209027B
     */
    public void setLevel( String pLevel )
    {
        mLevel = pLevel;
    }

    /**
     * Access method for the mTaskName property.
     * 
     * @return the current value of the mTaskName property
     * @roseuid 42CB92090307
     */
    public String getTaskName()
    {
        return mTaskName;
    }

    /**
     * Sets the value of the mTaskName property.
     * 
     * @param pTaskName the new value of the mTaskName property
     * @roseuid 42CB92090361
     */
    public void setTaskName( String pTaskName )
    {
        mTaskName = pTaskName;
    }

    /**
     * Access method for the mAuditId property.
     * 
     * @return the current value of the mAuditId property
     * @roseuid 42CB920903C5
     */
    public long getAuditId()
    {
        return mAuditId;
    }

    /**
     * Sets the value of the mAuditId property.
     * 
     * @param pAuditId the new value of the mAuditId property
     * @roseuid 42CB920A001A
     */
    public void setAuditId( long pAuditId )
    {
        mAuditId = pAuditId;
    }

    /**
     * Access method for the mProjectId property.
     * 
     * @return the current value of the mProjectId property
     * @roseuid 42CB920903C5
     */
    public long getProjectId()
    {
        return mProjectId;
    }

    /**
     * Sets the value of the mProjectId property.
     * 
     * @param pProjectId the new value of the mProjectId property
     * @roseuid 42CB920A001A
     */
    public void setProjectId( long pProjectId )
    {
        mProjectId = pProjectId;
    }
}
