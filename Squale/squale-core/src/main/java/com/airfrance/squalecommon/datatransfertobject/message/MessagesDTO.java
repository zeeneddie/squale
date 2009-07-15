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
package com.airfrance.squalecommon.datatransfertobject.message;

import java.util.Map;
import java.util.TreeMap;

/**
 * DTO pour les messages Cette classe encapsule une collection de messages
 */
public class MessagesDTO
{
    /** Langue parf défaut */
    private static final String DEFAULT_LANG = "en";

    /** Messages */
    private TreeMap mMessages = new TreeMap();

    /**
     * Obtention d'un message Si le message n'est pas trouvé dans la langue demandée, la langue par défaut est utilisée
     * 
     * @param pLang langue
     * @param pKey clef
     * @return message ou null si non trouvé
     */
    public String getMessage( String pLang, String pKey )
    {
        String result = null;
        Map langMap = (Map) mMessages.get( pLang );
        // On prend la langue par défaut si besoin
        if ( langMap == null )
        {
            langMap = (Map) mMessages.get( DEFAULT_LANG );
        }
        if ( langMap != null )
        {
            result = (String) langMap.get( pKey );
        }
        return result;
    }

    /**
     * Ajout d'un message
     * 
     * @param pLang langue
     * @param pKey clef
     * @param pText texte
     */
    public void addMessage( String pLang, String pKey, String pText )
    {
        if ( pLang == null )
        {
            //CHECKSTYLE:OFF
            pLang = "";
            //CHECKSTYLE:ON
        }
        Map langMap = (Map) mMessages.get( pLang );
        // Création de la map pour la langue si besoin
        if ( langMap == null )
        {
            langMap = new TreeMap();
            mMessages.put( pLang, langMap );
        }
        langMap.put( pKey, pText );
    }
}
