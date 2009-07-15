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
package com.airfrance.squalecommon.datatransfertobject.access;

import java.util.Date;

/**
 * 
 */
public class UserAccessDTO
{

    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId;

    /** Le matricule de l'utilisateur concerné */
    private String mMatricule;

    /** Date à laquelle l'utilisateur a accédé à l'application */
    private Date mDate;

    /**
     * @return l'id de l'objet
     */
    public long getId()
    {
        return mId;
    }

    /**
     * @param pId l'id de l'objet
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * @return la date d'accès
     */
    public Date getDate()
    {
        return mDate;
    }

    /**
     * @return le matricule de l'utilisateur
     */
    public String getMatricule()
    {
        return mMatricule;
    }

    /**
     * @param pDate la date d'accès
     */
    public void setDate( Date pDate )
    {
        mDate = pDate;
    }

    /**
     * @param pMatricule le matricule utilisateur
     */
    public void setMatricule( String pMatricule )
    {
        mMatricule = pMatricule;
    }

}
