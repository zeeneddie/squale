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
package com.airfrance.squalecommon.enterpriselayer.businessobject.message;

import java.util.Date;

/**
 * News
 * 
 * @hibernate.class table="News" mutable="true"
 */
public class NewsBO
{

    /**
     * Identifiant (au sens technique) de l'objet
     */
    private long mId;

    /** la clé de la news */
    private String key;

    /** la date de début de validité de la news */
    private Date beginningDate;

    /** la date de fin */
    private Date endDate;

    /**
     * @return la clé
     * @hibernate.property name="key" column="NewsKey" type="string" not-null="true" length="4000" update="true"
     *                     insert="true"
     */
    public String getKey()
    {
        return key;
    }

    /**
     * @param newKey la nouvelle clé
     */
    public void setKey( String newKey )
    {
        key = newKey;
    }

    /**
     * @return la date de début de validité
     * @hibernate.property name="beginningDate" column="Beginning_Date" type="timestamp" not-null="true" unique="false"
     *                     update="true" insert="true"
     */
    public Date getBeginningDate()
    {
        return beginningDate;
    }

    /**
     * @return la date de fin de validité
     * @hibernate.property name="endDate" column="End_Date" type="timestamp" not-null="true" unique="false"
     *                     update="true" insert="true"
     */
    public Date getEndDate()
    {
        return endDate;
    }

    /**
     * @param newBeginningDate la nouvelle date de début de validité
     */
    public void setBeginningDate( Date newBeginningDate )
    {
        beginningDate = newBeginningDate;
    }

    /**
     * @param newEndDate la nouvelle date de fin
     */
    public void setEndDate( Date newEndDate )
    {
        endDate = newEndDate;
    }

    /**
     * @return l'id
     * @hibernate.id generator-class="native" type="long" column="Id" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="news_sequence"
     */
    public long getId()
    {
        return mId;
    }

    /**
     * @param newId le nouvel id
     */
    public void setId( long newId )
    {
        mId = newId;
    }

}
