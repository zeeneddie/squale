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
 * Créé le 28 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.struts.ajax;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Rémy Bouquet Wrapper de construction de réponse xml pour un message de confirmation dynamique.
 */
public class WHttpConfirmationMessageResponse
    extends AjaxXmlResponseWrapper
{

    /**
     * constructeur
     * 
     * @param presponse la response HTTP
     */
    public WHttpConfirmationMessageResponse( final HttpServletResponse presponse )
    {
        super( presponse, "confirm" );
    }

    /**
     * Ajoute un message à la réponse XML
     * 
     * @param message message à ajouter
     */
    public void sendMessage( final String message )
    {
        addItem( "message", message );
    }

}
