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
/*
 * Créé le 6 nov. 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.bean;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.airfrance.welcom.outils.WelcomConfigurator;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WResultAction
{

    /** Clef de stockage des message de la popup */
    private static String RESULT_ACTION_KEY = "com.airfrance.welcom.struts.bean.WResultAction";

    /**
     * Sauve le message dans la popup
     * 
     * @param request request
     * @param message message
     */
    public static void saveMessage( HttpServletRequest request, String message )
    {

        String useSession = WelcomConfigurator.getMessage( WelcomConfigurator.MESSAGE_POPUP_STORE_IN_SESSION );

        if ( "true".equalsIgnoreCase( useSession ) )
        {
            request.getSession().setAttribute( RESULT_ACTION_KEY, message );
        }
        else
        {
            request.setAttribute( RESULT_ACTION_KEY, message );
        }
    }

    /**
     * Lecture du message de la request
     * 
     * @param request request
     * @return message
     */
    public static String readMessage( ServletRequest request )
    {
        String useSession = WelcomConfigurator.getMessage( WelcomConfigurator.MESSAGE_POPUP_STORE_IN_SESSION );
        if ( "true".equalsIgnoreCase( useSession ) )
        {
            return (String) ( (HttpServletRequest) request ).getSession().getAttribute( RESULT_ACTION_KEY );
        }
        else
        {
            return (String) request.getAttribute( RESULT_ACTION_KEY );
        }
    }

    /**
     * reset message
     * 
     * @param request request
     */
    public static void resetMessage( ServletRequest request )
    {
        request.removeAttribute( RESULT_ACTION_KEY );
        ( (HttpServletRequest) request ).getSession().removeAttribute( RESULT_ACTION_KEY );
    }

}
