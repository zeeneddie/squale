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
package org.squale.squaleweb.applicationlayer.formbean.messages;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import org.squale.squaleweb.applicationlayer.formbean.ActionIdFormSelectable;

/**
 * 
 */
public class NewsForm
    extends ActionIdFormSelectable
{

    /** la clé de la news */
    private String key = "";

    /** la date de début de validité de la news */
    private Date beginningDate = new Date();

    /** la date de fin de validité de la news */
    private Date endDate = new Date();

    /** le form associé */
    private MessageForm message = new MessageForm();

    /**
     * Identifiant (au sens technique) de l'objet
     */
    private long mId;

    /** La liste des langues disponibles pour écrire un message */
    private Collection langSet;

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
     * @param newTitle le nouveau titre
     */
    public void setKey( String newTitle )
    {
        key = newTitle;
    }

    /**
     * @return le form
     */
    public MessageForm getMessage()
    {
        return message;
    }

    /**
     * @param newForm la nouvelle collection
     */
    public void setMessage( MessageForm newForm )
    {
        message = newForm;
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

    /**
     * @see org.squale.welcom.struts.bean.WActionForm#wValidate(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    public void wValidate( ActionMapping mapping, HttpServletRequest request )
    {
        // Vérification des champs obligatoire
        super.wValidate( mapping, request );
        // La clé
        setKey( getKey().trim() );
        if ( getKey().length() == 0 )
        {
            addError( "key", new ActionError( "error.field.required" ) );
        }
        // Le titre du message
        getMessage().setKey( "news." + getKey() );
        getMessage().setTitle( getMessage().getTitle().trim() );
        if ( getMessage().getTitle().length() == 0 )
        {
            addError( "message.title", new ActionError( "error.field.required" ) );
        }
        // Le message
        getMessage().setText( getMessage().getText().trim() );
        if ( getMessage().getText().length() == 0 )
        {
            addError( "message.text", new ActionError( "error.field.required" ) );
        }
        getMessage().setLanguage( getMessage().getLanguage().trim() );
        if ( getMessage().getLanguage().length() == 0 )
        {
            addError( "message.lang", new ActionError( "error.field.required" ) );
        }
        // Vérification de la saisie
        // La date de début
        if ( null == getBeginningDate() )
        {
            addError( "beginningDate", new ActionError( "error.date.uncorrectFormat" ) );
        }
        // La date de fin
        if ( null == getEndDate() )
        {
            addError( "endDate", new ActionError( "error.date.uncorrectFormat" ) );
        }
        // Pour gérer la cohérence des dates, on vérifie que la date de fin est bien postérieure à la date de début
        if ( getBeginningDate() != null && getEndDate() != null
            && getBeginningDate().getTime() > getEndDate().getTime() )
        {
            addError( "endDate", new ActionError( "error.date.impossible" ) );
        }
    }
}
