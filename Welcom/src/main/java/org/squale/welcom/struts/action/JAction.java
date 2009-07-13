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
 * Créé le 1 août 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.squale.welcom.outils.Util;
import org.squale.welcom.outils.jdbc.WJdbc;
import org.squale.welcom.outils.jdbc.WMJdbc;
import org.squale.welcom.struts.bean.WILogonBean;
import org.squale.welcom.struts.util.WConstants;


/**
 * @author M327837 Classe permattant la gestion du jdbc
 */
public abstract class JAction
    extends MAction
{
    /** logger */
    private static Log log = LogFactory.getLog( JAction.class );

    /** Est-ce que l'on verifie le timout */
    protected boolean disabledTimeOut = false;

    /**
     * ù Recupere le WILogonBean
     * 
     * @param request : le request
     * @return WILogonBean
     */
    public WILogonBean getWILogonBean( final HttpServletRequest request )
    {
        if ( request.getSession().getAttribute( WConstants.USER_KEY ) instanceof WILogonBean )
        {
            return (WILogonBean) request.getSession().getAttribute( WConstants.USER_KEY );
        }
        else
        {
            return null;
        }
    }

    /**
     * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward execute( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
                                  final HttpServletResponse response )
        throws Exception
    {

        WJdbc jdbc = null;
        ActionForward af = null;
        String userName = "";
        try
        {
            if ( !isDisabledTimeOut() )
            {
                WActionUtil.checkSessionTimeout( request );
            }
            final WILogonBean logonBean = getWILogonBean( request );

            if ( logonBean != null )
            {
                userName = logonBean.getUserName();
            }

            // Appele le traitement
            af = wExecute( mapping, form, request, response );

            // Teste si l'on utilise l'avec jdbc embarqué
            if ( af == null )
            {
                final String useMode =
                    ( (String) servlet.getServletContext().getAttribute( WConstants.WELCOM_USE_MODE ) );

                // Initialise la connection jdbc
                if ( Util.isEquals( useMode, WConstants.MODE_JDBC ) )
                {
                    jdbc = new WJdbc( userName );
                }
                else
                {
                    // appel de la fonction pour connaitre quel JDBC a utiliser
                    String jdbcName = dispatchJdbcName( mapping.getParameter() );
                    if ( jdbcName == null )
                    {
                        jdbcName = dispatchJdbcName( request, mapping.getParameter() );
                    }
                    jdbc = new WMJdbc( userName, jdbcName );
                }

                if ( !jdbc.isClosed() )
                {
                    af = wExecute( mapping, form, request, response, jdbc );
                }

            }
        }
        catch ( final Exception e )
        {
            if ( !( e instanceof TimeOutException ) )
            {
                log.error( e, e );
            }

            String theMessage = null;
            try
            {
                theMessage = getResources( request ).getMessage( getLocale( request ), e.getMessage() );
            }
            catch ( final Exception e2 )
            {
                throw e;
            }
            if ( !GenericValidator.isBlankOrNull( theMessage ) )
            {
                throw new Exception( theMessage );
            }
            else
            {
                throw e;
            }

        }
        finally
        {
            // Fermeture de tout ce qui a été ouvert en connection JDBC
            if ( jdbc != null )
            {
                jdbc.close();
            }

            jdbc = null;
        }

        return af;

    }

    /**
     * Fonction permettant de definir le quel jdbc on desire utiliser en retournant le nom definit dans le struts-config
     * 
     * @param functionName : Nom de la fonction qui va etre appelé
     * @return : le nom du dataSource a Utiliser
     */
    public String dispatchJdbcName( final String functionName )
    {
        return null;
    }

    /**
     * Fonction permettant de definir le quel jdbc on desire utiliser en retournant le nom definit dans le struts-config
     * 
     * @param request : Request pour identifié quelle methode a appeler
     * @param functionName : Nom de la fonction qui va etre appelé
     * @return : le nom du dataSource a Utiliser
     */
    public String dispatchJdbcName( final HttpServletRequest request, final String functionName )
    {
        return null;
    }

    /**
     * Fonction sans JDBC embarqué
     * 
     * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward wExecute( final ActionMapping mapping, final ActionForm form,
                                   final HttpServletRequest request, final HttpServletResponse response )
        throws Exception
    {
        return null;
    }

    /**
     * Fonction avec JDBC embarqué
     * 
     * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */

    public ActionForward wExecute( final ActionMapping mapping, final ActionForm form,
                                   final HttpServletRequest request, final HttpServletResponse response,
                                   final WJdbc jdbc )
        throws Exception
    {
        return null;
    }

    /**
     * @return si le timeout est activé
     */
    public boolean isDisabledTimeOut()
    {
        return disabledTimeOut;
    }

    /**
     * @param b si le timeout est activé
     */
    public void setDisabledTimeOut( final boolean b )
    {
        disabledTimeOut = b;
    }

}
