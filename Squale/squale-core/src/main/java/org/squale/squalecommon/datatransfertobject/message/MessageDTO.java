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

/**
 * 
 */
public class MessageDTO
{

    /** le titre du message, dans le cas d'une news */
    private String title;

    /** le texte du message */
    private String text;

    /** la langue du message */
    private String lang;

    /** la clé de message */
    private String key;

    /**
     * @return la clé
     */
    public String getKey()
    {
        return key;
    }

    /**
     * @return la langue
     */
    public String getLang()
    {
        return lang;
    }

    /**
     * @return le texte
     */
    public String getText()
    {
        return text;
    }

    /**
     * @return le titre
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param newKey la nouvelle valeur de la clé
     */
    public void setKey( String newKey )
    {
        key = newKey;
    }

    /**
     * @param newLang la nouvelle valeur de la langue
     */
    public void setLang( String newLang )
    {
        lang = newLang;
    }

    /**
     * @param newText la nouvelle valeur du texte
     */
    public void setText( String newText )
    {
        text = newText;
    }

    /**
     * @param newTitle la nouvelle valeur du titre
     */
    public void setTitle( String newTitle )
    {
        title = newTitle;
    }

}