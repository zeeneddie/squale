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
 * Créé le 15 avr. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.action;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.struts.lazyLoading.WLazyLoadingPersistance;
import com.airfrance.welcom.struts.lazyLoading.WLazyLoadingType;

/**
 * @author M327836 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WLazyLoadingAction
    extends Action
{

    /**
     * @see org.apache.struts.action.Action;
     */
    public ActionForward execute( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
                                  final HttpServletResponse response )
        throws Exception
    {

        try
        {

            final String charset = WelcomConfigurator.getMessage( WelcomConfigurator.ENCODING_CHARSET );
            response.setContentType( "text/html;charset=" + charset );
            response.setHeader( "Pragma", "no-cache" );
            response.setHeader( "Expires", "0" );
            response.setHeader( "Cache-Control", "no-store" );

            try
            {
                WActionUtil.checkSessionTimeout( request );
            }
            catch ( final TimeOutException e )
            {
                final String action = request.getParameter( "action" );

                if ( Util.isEquals( action, "timeout" ) )
                {
                    final String theMessage = getResources( request ).getMessage( getLocale( request ), e.getMessage() );
                    throw new Exception( theMessage );

                }
                else
                {
                    final PrintWriter out = response.getWriter();
                    out.print( "timeout" );
                    out.close();

                    return null;
                }
            }

            final String key = request.getParameter( "key" );
            final String type = request.getParameter( "type" );

            WLazyLoadingType lazyType = WLazyLoadingType.ONGLET;

            if ( type.equalsIgnoreCase( "dropdownpanel" ) )
            {
                lazyType = WLazyLoadingType.DROPDOWNPANEL;
            }
            else if ( type.equalsIgnoreCase( "zone" ) )
            {
                lazyType = WLazyLoadingType.ZONE;
            }
            else if ( type.equalsIgnoreCase( "combo" ) )
            {
                lazyType = WLazyLoadingType.COMBO;
                response.setHeader( "Content-Type", "text/xml; charset=" + charset );
            }

            final OutputStream out = response.getOutputStream();
            final PrintWriter writer = new PrintWriter( new OutputStreamWriter( out, charset ) );

            try
            {
                final String s = WLazyLoadingPersistance.find( request ).get( lazyType, key );
                writer.print( s );
            }
            catch ( final Exception e )
            {
                throw new ServletException( e );
            }

            writer.close();

            return null;

        }
        catch ( final Exception e )
        {
            throw e;
        }
    }
}