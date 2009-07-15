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

/**
 * DTO pour la fréquence max des audits
 */
public class AuditFrequencyDTO
{

    /** L'identifiant (au sens technique) de l'objet */
    private long mId;

    /** Durée depuis le dernier accès (en nombre de jours) */
    private int mDays;

    /** Fréquence max d'audit (en nombre de jours) */
    private int mFrequency;

    /**
     * Méthode d'accès à mId
     * 
     * @return l'identifiant de l'objet
     */
    public long getId()
    {
        return mId;
    }

    /**
     * @return la durée le depuis dernier accès
     */
    public int getDays()
    {
        return mDays;
    }

    /**
     * @return la fréquence max d'audit
     */
    public int getFrequency()
    {
        return mFrequency;
    }

    /**
     * Change la valeur de mId
     * 
     * @param pId la nouvelle valeur de l'identifiant de l'objet
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * @param pDays la durée le depuis dernier accès
     */
    public void setDays( int pDays )
    {
        mDays = pDays;
    }

    /**
     * @param pFrequency la fréquence max d'audit
     */
    public void setFrequency( int pFrequency )
    {
        mFrequency = pFrequency;
    }
}
