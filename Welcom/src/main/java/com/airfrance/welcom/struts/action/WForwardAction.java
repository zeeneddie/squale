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
package com.airfrance.welcom.struts.action;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;

import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.struts.bean.WIChanged;
import com.airfrance.welcom.struts.util.ServletUtils;
import com.airfrance.welcom.taglib.field.util.LayoutUtils;
import com.airfrance.welcom.taglib.table.InternalTableUtil;

/**
 * WForwardAction
 */
public class WForwardAction
    extends WAction
{
    /**
     * The request attribute under which the path information is stored for processing during a
     * RequestDispatcher.include() call.
     */
    public static final String INCLUDE_PATH_INFO = "javax.servlet.include.path_info";

    /**
     * The request attribute under which the servlet path information is stored for processing during a
     * RequestDispatcher.include() call.
     */
    public static final String INCLUDE_SERVLET_PATH = "javax.servlet.include.servlet_path";

    /**
     * @see com.airfrance.welcom.struts.action.WAction#wExecute(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward wExecute( final ActionMapping mapping, final ActionForm form,
                                   final HttpServletRequest request, final HttpServletResponse response )
        throws IOException, ServletException, JspException
    {
        final String oldAction = request.getParameter( "oldAction" );

        // Permer de rester dansle contexte.
        final String forward = request.getParameter( "wforward" );
        if ( forward != null )
        {
            return mapping.findForward( forward );
        }

        if ( !GenericValidator.isBlankOrNull( oldAction ) )
        {
            // Effectue le populate
            final ModuleConfig moduleConfig = mapping.getModuleConfig();
            final String path = ServletUtils.processPath( servlet, request, response, oldAction, moduleConfig );
            final ActionMapping mappingGenerique = ServletUtils.processMapping( request, response, moduleConfig, path );

            if ( mappingGenerique != null )
            {
                // Process any ActionForm bean related to this request
                final ActionForm formGenerique =
                    ServletUtils.processActionForm( request, response, moduleConfig, mappingGenerique, servlet );

                // remis a zero
                // Déactive si optimization.checkbox.javascript a true
                if ( Util.isFalse( WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_AUTORESET_CHECKBOX ) ) )
                {
                    InternalTableUtil.razCheckBoxListe( request, formGenerique );
                }

                // Appele le reset du formulaire
                formGenerique.reset( mappingGenerique, request );

                // Effectue la populate
                ServletUtils.processPopulate( request, response, formGenerique, mappingGenerique, servlet );

                // on gere le cas ColOrder :
                final String action = request.getParameter( "action" );
                if ( ( action != null ) && ( action.equals( "order" ) ) )
                {
                    final String property = request.getParameter( "property" );
                    final String sens = request.getParameter( "sens" );
                    final List list =
                        (List) LayoutUtils.getProperty( formGenerique, request.getParameter( "collection" ) );
                    final int index1 = Integer.parseInt( request.getParameter( "position" ) );
                    int index2;
                    final Object obj1 = list.get( index1 );
                    Object obj2;
                    if ( sens.equals( "up" ) )
                    {
                        index2 = index1 - 1;
                        obj2 = list.get( index2 );
                    }
                    else
                    {
                        index2 = index1 + 1;
                        obj2 = list.get( index2 );
                    }
                    // on echange la property d'ordre
                    final Object oldProperty1 = LayoutUtils.getProperty( obj1, property );
                    final Object oldProperty2 = LayoutUtils.getProperty( obj2, property );
                    LayoutUtils.setProperty( obj1, property, oldProperty2 );
                    LayoutUtils.setProperty( obj2, property, oldProperty1 );

                    // on modifie l'ordre dans la liste
                    Collections.swap( list, index1, index2 );

                    // on position a true l'attribut changed :
                    ( (WIChanged) obj1 ).setChanged( true );
                    ( (WIChanged) obj2 ).setChanged( true );
                }

            }
        }

        final String referer = request.getParameter( "requestURI" );

        if ( !GenericValidator.isBlankOrNull( referer ) && ( referer.length() > 1 ) && ( referer.charAt( 0 ) == '/' ) )
        {
            final String contextPath = request.getContextPath();

            if ( referer.indexOf( contextPath, 0 ) > -1 )
            {
                return new ActionForward( referer.substring( contextPath.length(), referer.length() ) );
            }
            else
            {
                return new ActionForward( referer );
            }
        }
        else
        {
            return new ActionForward( referer );
        }
    }
}