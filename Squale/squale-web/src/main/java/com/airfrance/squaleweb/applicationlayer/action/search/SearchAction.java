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
package com.airfrance.squaleweb.applicationlayer.action.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.DefaultAction;
import com.airfrance.squaleweb.transformer.SearchProjectTransformer;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Recherche un projet.
 */
public class SearchAction
    extends DefaultAction
{

    /**
     * Recherche un projet
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward searchProject( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                        HttpServletResponse pResponse )
    {

        ActionMessages errors = new ActionMessages();
        ActionForward forward = null;

        try
        {
            String firstCallParam = pRequest.getParameter( "firstCall" );
            // On ne fait le traitement si on a pas cliqué sur le bouton "rechercher".
            // Le paramétre "firstCall" l'indique
            if ( null == firstCallParam )
            {
                // On récupère les données du formulaire de recherche d'un projet
                Object[] data = WTransformerFactory.formToObj( SearchProjectTransformer.class, (WActionForm) pForm );
                String appli = (String) data[0];
                String project = (String) data[1];
                // Récupération de la liste des applications
                List applications = getUserApplicationListAsDTO( pRequest );
                // Obtention de la couche métier
                IApplicationComponent ac = AccessDelegateHelper.getInstance( "Component" );
                Object[] paramIn = { applications, appli, project };
                // Appel de la couche métier pour obtenir les projets correspondants aux critères
                Map projectsDto = (Map) ac.execute( "getProjectsWithLastAudit", paramIn );
                // Transformation en formulaire
                WTransformerFactory.objToForm( SearchProjectTransformer.class, (WActionForm) pForm, new Object[] {
                    projectsDto, applications } );
            }
            else
            {
                // On reset le formulaire
                ActionForm searchForm =
                    WTransformerFactory.objToForm( SearchProjectTransformer.class, new Object[] { new HashMap(),
                        new ArrayList() } );
                pRequest.getSession().setAttribute( "searchProjectForm", searchForm );
            }
            forward = pMapping.findForward( "list" );
        }
        catch ( Exception e )
        {
            // Traitement factorisé des exceptions et transfert vers la page d'erreur
            handleException( e, errors, pRequest );
            // saveMessages(pRequest, messages);
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }
}
