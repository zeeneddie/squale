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
 * Créé le 13 mai 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.struts.action;

import javax.servlet.http.HttpServletRequest;

import org.squale.welcom.outils.Util;
import org.squale.welcom.outils.WelcomConfigurator;
import org.squale.welcom.struts.util.WConstants;


/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WActionUtil
{

    /**
     * Verifie sur le Bean WConstant.USER_KEY n'est pas present dans la session
     * 
     * @param request : request
     * @throws TimeOutException : Mise en time out
     */
    public static void checkSessionTimeout( final HttpServletRequest request )
        throws TimeOutException
    {
        if ( Util.isTrue( WelcomConfigurator.getMessage( WelcomConfigurator.CHECKTIMEOUT_ENABLED ) )
            && ( request.getSession().getAttribute( WConstants.USER_KEY ) == null ) )
        {

            throw new TimeOutException( "welcom.internal.error.session.timeout" );

        }

    }
}