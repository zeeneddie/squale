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

import java.util.Date;

/**
 * DTO d'un RuleSet
 */
public class RuleSetDTO
{
    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId = -1;

    /** Nom */
    private String mName;

    /**
     * Date de la creation de la regle de calcul d'un résultat qualité
     */
    private Date mDateOfUpdate;

    /**
     * Access method for the mId property.
     * 
     * @return the current value of the mId property
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Sets the value of the mId property.
     * 
     * @param pId the new value of the mId property
     */

    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * @return nom
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @param pName nom
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * Récupère la date de mise à jour
     * 
     * @return la date de mise à jour
     */
    public Date getDateOfUpdate()
    {
        return mDateOfUpdate;
    }

    /**
     * Affecte la date de mise à jour
     * 
     * @param pDate la date de mise à jour
     */
    public void setDateOfUpdate( Date pDate )
    {
        mDateOfUpdate = pDate;
    }

}
