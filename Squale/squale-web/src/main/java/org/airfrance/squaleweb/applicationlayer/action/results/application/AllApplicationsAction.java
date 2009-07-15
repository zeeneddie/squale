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
package com.airfrance.squaleweb.applicationlayer.action.results.application;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.DefaultAction;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ApplicationListForm;
import com.airfrance.squaleweb.transformer.ApplicationListTransformer;
import com.airfrance.squaleweb.transformer.FactorsResultListTransformer;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Affichage de la liste des applications Cette action permet d'extraire la liste des applications disponibles pour
 * l'utilisateur courant et de les restituer sous une forme synthétique
 */
public class AllApplicationsAction
    extends DefaultAction
{

    /**
     * Affichage de la liste des applications non publiques
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward listNotPublic( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                        HttpServletResponse pResponse )
    {

        ActionForward forward;
        try
        {
            // Récupération de la liste des applications
            forward = execute( pMapping, pForm, getUserApplicationWithResultsList( pRequest ) );
        }
        catch ( Exception e )
        {
            ActionErrors errors = new ActionErrors();
            // Traitement factorisé des erreurs
            handleException( e, errors, pRequest );
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "failure" );
        }
        // On est passé par un menu donc on réinitialise le traceur
        resetTracker( pRequest );
        return ( forward );
    }

    /**
     * Affichage de la liste des applications publiques
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward listPublic( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                     HttpServletResponse pResponse )
    {

        ActionForward forward;
        try
        {
            // Récupération de la liste des applications
            forward = execute( pMapping, pForm, getUserPublicApplicationWithResultsList( pRequest ) );
            // On indique qu'il s'agit d'applications publiques
            pRequest.setAttribute( "isPublic", "true" );
        }
        catch ( Exception e )
        {
            ActionErrors errors = new ActionErrors();
            // Traitement factorisé des erreurs
            handleException( e, errors, pRequest );
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "failure" );
        }
        // On est passé par un menu donc on réinitialise le traceur
        resetTracker( pRequest );
        return ( forward );
    }

    /**
     * @param pMapping le mapping
     * @param pForm le formulaire
     * @param pAppli les applications
     * @return le forward
     * @throws WTransformerException si erreur
     * @throws JrafEnterpriseException si erreur
     */
    private ActionForward execute( ActionMapping pMapping, ActionForm pForm, List pAppli )
        throws WTransformerException, JrafEnterpriseException
    {
        ActionForward forward;
        ApplicationListForm applicationListForm = new ApplicationListForm();
        ArrayList applicationsList = new ArrayList();
        applicationsList.addAll( pAppli );
        applicationListForm.setList( applicationsList );
        List applications =
            (List) ( WTransformerFactory.formToObj( ApplicationListTransformer.class, applicationListForm )[0] );
        if ( applications.isEmpty() )
        {
            // S'il n'y a pas d'applications, on redirige vers une page dédiée
            forward = pMapping.findForward( "empty" );
        }
        else
        {
            List results = new LinkedList();
            // Récupération des résultats à partir de l'Application Component
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Results" );
            Object[] paramIn = { applications, null };
            // Récupération des résultats pour chacun des applications concernées
            List applicationResults = ( (List) ac.execute( "getApplicationResults", paramIn ) );
            // Transformation des résultats avant leur affichage par la couche
            // welcom
            WTransformerFactory.objToForm( FactorsResultListTransformer.class, (WActionForm) pForm, new Object[] {
                applications, applicationResults } );
            forward = pMapping.findForward( "factors" );
        }
        return forward;
    }

}
