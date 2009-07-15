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
package org.squale.squalecommon.datatransfertobject.message;

import java.util.Collection;
import java.util.Date;

/**
 * 
 *
 */
public class NewsDTO
{

    /** la clé de la news */
    private String key;

    /** la date de début de validité de la news */
    private Date beginningDate;

    /** la date de fin de validité de la news */
    private Date endDate;

    /** la collection des messages associés */
    private MessageDTO message;

    /** La liste des langues disponibles pour écrire un message */
    private Collection langSet;

    /**
     * Identifiant (au sens technique) de l'objet
     */
    private long mId;

    /**
     * @return l'id
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

    /**
     * @return la date de début de validité
     */
    public Date getBeginningDate()
    {
        return beginningDate;
    }

    /**
     * @return la date de fin de validité
     */
    public Date getEndDate()
    {
        return endDate;
    }

    /**
     * @return la clé
     */
    public String getKey()
    {
        return key;
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
     * @param newKey la nouvelle clé
     */
    public void setKey( String newKey )
    {
        key = newKey;
    }

    /**
     * @return la collection des messages
     */
    public MessageDTO getMessage()
    {
        return message;
    }

    /**
     * @param newMessage la nouvelle collection de messages
     */
    public void setMessage( MessageDTO newMessage )
    {
        message = newMessage;
    }

    /**
     * @return la liste des lang
     */
    public Collection getLangSet()
    {
        return langSet;
    }

    /**
     * @param newLangSet la nouvelle liste des langues disponibles
     */
    public void setLangSet( Collection newLangSet )
    {
        langSet = newLangSet;
    }
}
