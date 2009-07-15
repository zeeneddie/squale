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
package org.squale.squaleweb.applicationlayer.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.squale.welcom.struts.transformer.WTransformerFactory;

import org.squale.jraf.helper.AccessDelegateHelper;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.squaleweb.applicationlayer.action.accessRights.DefaultAction;
import org.squale.squaleweb.transformer.ServeurListTransformer;
import org.squale.squaleweb.applicationlayer.formbean.config.ServeurListForm;

import java.util.Collection;

/**
 */
public class UtilLinkAction
    extends DefaultAction
{

    /**
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward goodPractice( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                       HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionErrors actionErrors = new ActionErrors();
        try
        {
            forward = pMapping.findForward( "goodPractice" );
        }
        catch ( Exception e )
        {
            // Factorisation du traitement des exceptions
            handleException( e, actionErrors, pRequest );
        }
        if ( !actionErrors.isEmpty() )
        {
            // Sauvegarde des messages
            saveMessages( pRequest, actionErrors );
            // Routage vers la page d'erreur
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward error( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionErrors actionErrors = new ActionErrors();
        try
        {
            forward = pMapping.findForward( "error" );
        }
        catch ( Exception e )
        {
            // Factorisation du traitement des exceptions
            handleException( e, actionErrors, pRequest );
        }
        if ( !actionErrors.isEmpty() )
        {
            // Sauvegarde des messages
            saveMessages( pRequest, actionErrors );
            // Routage vers la page d'erreur
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward newApplication( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                         HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionErrors actionErrors = new ActionErrors();
        try
        {
            forward = pMapping.findForward( "newApplication" );
        }
        catch ( Exception e )
        {
            // Factorisation du traitement des exceptions
            handleException( e, actionErrors, pRequest );
        }
        if ( !actionErrors.isEmpty() )
        {
            // Sauvegarde des messages
            saveMessages( pRequest, actionErrors );
            // Routage vers la page d'erreur
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward configApplication( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                            HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionErrors actionErrors = new ActionErrors();
        try
        {
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Serveur" );
            Collection lListeServeurDTO = (Collection) ac.execute( "listeServeurs" );
            ServeurListForm lListeServeurForm = new ServeurListForm();
            WTransformerFactory.objToForm( ServeurListTransformer.class, lListeServeurForm, lListeServeurDTO );
            pRequest.setAttribute( "listeServeur", lListeServeurForm );

            forward = pMapping.findForward( "configApplication" );
        }
        catch ( Exception e )
        {
            // Factorisation du traitement des exceptions
            handleException( e, actionErrors, pRequest );
        }
        if ( !actionErrors.isEmpty() )
        {
            // Sauvegarde des messages
            saveMessages( pRequest, actionErrors );
            // Routage vers la page d'erreur
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward addRights( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                    HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionErrors actionErrors = new ActionErrors();
        try
        {
            forward = pMapping.findForward( "addRights" );
        }
        catch ( Exception e )
        {
            // Factorisation du traitement des exceptions
            handleException( e, actionErrors, pRequest );
        }
        if ( !actionErrors.isEmpty() )
        {
            // Sauvegarde des messages
            saveMessages( pRequest, actionErrors );
            // Routage vers la page d'erreur
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward adminApplication( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                           HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionErrors actionErrors = new ActionErrors();

        try
        {
            forward = pMapping.findForward( "adminApplication" );
        }
        catch ( Exception e )
        {
            // Factorisation du traitement des exceptions
            handleException( e, actionErrors, pRequest );
        }
        if ( !actionErrors.isEmpty() )
        {
            // Sauvegarde des messages
            saveMessages( pRequest, actionErrors );
            // Routage vers la page d'erreur
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward applicationSummary( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                             HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionErrors actionErrors = new ActionErrors();
        try
        {
            forward = pMapping.findForward( "applicationSummary" );
        }
        catch ( Exception e )
        {
            // Factorisation du traitement des exceptions
            handleException( e, actionErrors, pRequest );
        }
        if ( !actionErrors.isEmpty() )
        {
            // Sauvegarde des messages
            saveMessages( pRequest, actionErrors );
            // Routage vers la page d'erreur
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward message( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                  HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionErrors actionErrors = new ActionErrors();
        try
        {
            forward = pMapping.findForward( "message" );
        }
        catch ( Exception e )
        {
            // Factorisation du traitement des exceptions
            handleException( e, actionErrors, pRequest );
        }
        if ( !actionErrors.isEmpty() )
        {
            // Sauvegarde des messages
            saveMessages( pRequest, actionErrors );
            // Routage vers la page d'erreur
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward login( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionErrors actionErrors = new ActionErrors();
        try
        {
            forward = pMapping.findForward( "login" );
        }
        catch ( Exception e )
        {
            // Factorisation du traitement des exceptions
            handleException( e, actionErrors, pRequest );
        }
        if ( !actionErrors.isEmpty() )
        {
            // Sauvegarde des messages
            saveMessages( pRequest, actionErrors );
            // Routage vers la page d'erreur
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward homepage( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                   HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionErrors actionErrors = new ActionErrors();
        try
        {
            forward = pMapping.findForward( "homepage" );
        }
        catch ( Exception e )
        {
            // Factorisation du traitement des exceptions
            handleException( e, actionErrors, pRequest );
        }
        if ( !actionErrors.isEmpty() )
        {
            // Sauvegarde des messages
            saveMessages( pRequest, actionErrors );
            // Routage vers la page d'erreur
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

}
