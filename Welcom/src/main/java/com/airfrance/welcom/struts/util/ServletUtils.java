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
// Source File Name:   ServletUtils.java
package com.airfrance.welcom.struts.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.RequestUtils;

import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.taglib.field.util.TagUtils;
import com.airfrance.welcom.taglib.onglet.JSOnglet;
import com.airfrance.welcom.taglib.table.InternalTableUtil;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ServletUtils
{

    /** Constants */
    public static final String INCLUDE_PATH_INFO = "javax.servlet.include.path_info";

    /** Constants */
    public static final String INCLUDE_SERVLET_PATH = "javax.servlet.include.servlet_path";

    /** Constants */
    public static final String LAST_REFERER_KEY = "lastreferer";

    /** page d'accueil */
    private static final String lastReferer = "/index.jsp";

    /** Ulr a ingorer */
    private static final String changeURL[] = { "/changeLocale.do", "/changeSkin.do", "/logon.do" };

    /**
     * Contructeur
     */
    public ServletUtils()
    {
    }

    /**
     * Recupere le last refer
     * 
     * @param request : request
     * @return : le last referer
     */
    static String getLastReferer( final HttpServletRequest request )
    {
        final HttpSession session = request.getSession();
        String value = (String) session.getAttribute( "lastreferer" );

        if ( value == null )
        {
            value = lastReferer;
        }

        return value;
    }

    /**
     * Recupere le refrer en ignorant les urls suppllemenentaires
     * 
     * @param request : request
     * @return referer
     */
    public static String getReferer( final HttpServletRequest request )
    {
        String referer = request.getHeader( "Referer" );

        if ( referer != null )
        {
            final int lenprotocole = referer.indexOf( "://" );

            final int pos = referer.substring( lenprotocole + 3, referer.length() ).indexOf( '/' );
            referer = referer.substring( lenprotocole + 3 + pos, referer.length() );

            if ( !referer.equals( changeURL[0] ) && !referer.equals( changeURL[1] ) && !referer.equals( changeURL[2] ) )
            {
                setLastReferer( request, referer );

                return referer;
            }

            if ( !lastReferer.equals( referer ) )
            {
                return getLastReferer( request );
            }
            else
            {
                return "/main.jsp";
            }
        }
        else
        {
            return lastReferer;
        }
    }

    /**
     * Stocke le last referer
     * 
     * @param request : request
     * @param pLastReferer : adresse du derniere referer
     */
    static void setLastReferer( final HttpServletRequest request, final String pLastReferer )
    {
        final HttpSession session = request.getSession();
        session.setAttribute( "lastreferer", pLastReferer );
    }

    /**
     * Permet le persistance quand le Requestprocessor n'est pas activé pour la gestion des dates
     * 
     * @param form : formulaire struts
     * @param request : request
     */
    public static void welcomPersistanceUtil( final ActionForm form, final HttpServletRequest request )
    {
        // Recupere tous les parameters
        Hashtable onglets = (Hashtable) request.getSession().getAttribute( JSOnglet.SELECTED_TABS );

        if ( onglets == null )
        {
            onglets = new Hashtable();
        }

        // Recupere tous les parameters
        final Enumeration enumeration = request.getParameterNames();

        while ( enumeration.hasMoreElements() )
        {
            final String element = (String) enumeration.nextElement();

            if ( element.indexOf( "WHour" ) > 0 )
            {
                final String field = element.substring( 0, element.indexOf( "WHour" ) );

                try
                {
                    BeanUtils.setProperty( form, field, TagUtils.getDateHeureFromDateTag( request, field ) );
                }
                catch ( final IllegalAccessException e )
                {
                    ;
                }
                catch ( final InvocationTargetException e )
                {
                    ;
                }
            }

            if ( element.indexOf( "selectedtab" ) > 0 )
            {
                final String onglet = request.getParameter( element );

                if ( !GenericValidator.isBlankOrNull( onglet ) )
                {
                    onglets.put( element, onglet );
                }
            }
        }

        request.getSession().setAttribute( JSOnglet.SELECTED_TABS, onglets );

        // Persistance des cases a cochés
        if ( Util.isFalse( WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_AUTORESET_CHECKBOX ) ) )
        {
            InternalTableUtil.razCheckBoxListe( request, form );
        }
    }

    /**
     * Select the mapping used to process the selection path for this request. If no mapping can be identified, create
     * an error response and return <code>null</code>.
     * 
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param moduleConfig : Config de struts
     * @param path The portion of the request URI for selecting a mapping
     * @return l'action mapping
     * @exception IOException if an input/output error occurs
     */
    public static ActionMapping processMapping( final HttpServletRequest request, final HttpServletResponse response,
                                                final ModuleConfig moduleConfig, final String path )
        throws IOException
    {
        // Is there a directly defined mapping for this path?
        ActionMapping mapping = (ActionMapping) moduleConfig.findActionConfig( path );

        if ( mapping != null )
        {
            request.setAttribute( Globals.MAPPING_KEY, mapping );

            return ( mapping );
        }

        // Locate the mapping for unknown paths (if any)
        final ActionConfig configs[] = moduleConfig.findActionConfigs();

        for ( int i = 0; i < configs.length; i++ )
        {
            if ( configs[i].getUnknown() )
            {
                mapping = (ActionMapping) configs[i];
                request.setAttribute( Globals.MAPPING_KEY, mapping );

                return ( mapping );
            }
        }

        // No mapping can be found to process this request
        response.sendError( HttpServletResponse.SC_BAD_REQUEST, "processInvalid :" + path );

        return ( null );
    }

    /**
     * Populate the properties of the specified ActionForm instance from the request parameters included with this
     * request. In addition, request attribute <code>Globals.CANCEL_KEY</code> will be set if the request was
     * submitted with a button created by <code>CancelTag</code>.
     * 
     * @param servlet servlet
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param form The ActionForm instance we are populating
     * @param mapping The ActionMapping we are using
     * @exception ServletException if thrown by RequestUtils.populate()
     */
    public static void processPopulate( final HttpServletRequest request, final HttpServletResponse response,
                                        final ActionForm form, final ActionMapping mapping, final ActionServlet servlet )
        throws ServletException
    {
        if ( form == null )
        {
            return;
        }

        form.setServlet( servlet );
        form.reset( mapping, request );

        if ( mapping.getMultipartClass() != null )
        {
            request.setAttribute( Globals.MULTIPART_KEY, mapping.getMultipartClass() );
        }

        RequestUtils.populate( form, mapping.getPrefix(), mapping.getSuffix(), request );

        // Set the cancellation request attribute if appropriate
        if ( ( request.getParameter( Constants.CANCEL_PROPERTY ) != null )
            || ( request.getParameter( Constants.CANCEL_PROPERTY_X ) != null ) )
        {
            request.setAttribute( Globals.CANCEL_KEY, Boolean.TRUE );
        }
    }

    /**
     * Retrieve and return the <code>ActionForm</code> bean associated with this mapping, creating and stashing one if
     * necessary. If there is no form bean associated with this mapping, return <code>null</code>.
     * 
     * @param servlet servlet
     * @param moduleConfig config
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param mapping The mapping we are using
     * @return ActionFormulaire
     */
    public static ActionForm processActionForm( final HttpServletRequest request, final HttpServletResponse response,
                                                final ModuleConfig moduleConfig, final ActionMapping mapping,
                                                final ActionServlet servlet )
    {
        // Create (if necessary a form bean to use
        final ActionForm instance = RequestUtils.createActionForm( request, mapping, moduleConfig, servlet );

        if ( instance == null )
        {
            return ( null );
        }

        if ( "request".equals( mapping.getScope() ) )
        {
            request.setAttribute( mapping.getAttribute(), instance );
        }
        else
        {
            final HttpSession session = request.getSession();
            session.setAttribute( mapping.getAttribute(), instance );
        }

        return ( instance );
    }

    /**
     * Identify and return the path component (from the request URI) that we will use to select an ActionMapping to
     * dispatch with. If no such path can be identified, create an error response and return <code>null</code>.
     * 
     * @param moduleConfig config
     * @param path path a tester
     * @param servlet Servelet
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @return le chemin adequat
     * @exception IOException if an input/output error occurs
     */
    public static String processPath( final ActionServlet servlet, final HttpServletRequest request,
                                      final HttpServletResponse response, String path, final ModuleConfig moduleConfig )
        throws IOException
    {
        final String prefix = request.getContextPath();

        // String prefix = moduleConfig.getPrefix();
        path = path.substring( prefix.length() );

        final int slash = path.lastIndexOf( "/" );
        final int period = path.lastIndexOf( "." );

        if ( ( period >= 0 ) && ( period > slash ) )
        {
            path = path.substring( 0, period );
        }

        return ( path );
    }
}