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
package org.squale.squalecommon.datatransfertobject.rulechecking;

import java.io.Serializable;

/**
 * DTO pour une transgression.
 */
public class RuleCheckingDTO
    implements Serializable
{

    /** Identificateur */
    private long mId;

    /**
     * Nom la Règle
     */
    private String mName;

    /**
     * La sévérité de la règle
     */
    private String mSeverity;

    /**
     * La version de grille de règle
     */
    private String mVersion;

    /**
     * Id de la mesure
     */
    private long mMeasureID = -1;

    /**
     * Constructeur par defaut
     */
    public RuleCheckingDTO()
    {

    }

    /**
     * @return Id
     */
    public long getId()
    {
        return mId;
    }

    /**
     * @return name
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @return severity
     */
    public String getSeverity()
    {
        return mSeverity;
    }

    /**
     * @return version
     */
    public String getVersion()
    {
        return mVersion;
    }

    /**
     * @return l'id de la mesure
     */
    public long getMeasureID()
    {
        return mMeasureID;
    }

    /**
     * @param pId id
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * @param pName name
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * @param pSeverity severity
     */
    public void setSeverity( String pSeverity )
    {
        mSeverity = pSeverity;
    }

    /**
     * @param pVersion version
     */
    public void setVersion( String pVersion )
    {
        mVersion = pVersion;
    }

    /**
     * @param pID id de la mesure
     */
    public void setMeasureID( long pID )
    {
        mMeasureID = pID;
    }

}
