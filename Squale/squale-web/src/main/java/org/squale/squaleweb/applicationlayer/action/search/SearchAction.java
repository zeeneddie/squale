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
package org.squale.squaleweb.applicationlayer.action.search;

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
import org.squale.jraf.helper.AccessDelegateHelper;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.squaleweb.applicationlayer.action.accessRights.DefaultAction;
import org.squale.squaleweb.transformer.SearchProjectTransformer;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WTransformerFactory;

/**
 * Search a project
 */
public class SearchAction
    extends DefaultAction
{

    /**
     * Searches for a project in the database
     * 
     * @param pMapping the mapping.
     * @param pForm the form to be used and read.
     * @param pRequest the HTTP request.
     * @param pResponse the servlet response.
     * @return the action to return.
     */
    public ActionForward searchProject( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                        HttpServletResponse pResponse )
    {

        ActionMessages errors = new ActionMessages();
        ActionForward forward = null;

        try
        {
            String firstCallParam = pRequest.getParameter( "firstCall" );
            // The research is done only if the research button has been clicked
            // That is indicated by the "firstCall" parameter
            if ( null == firstCallParam )
            {
                // Retrieval of the research form data
                Object[] data = WTransformerFactory.formToObj( SearchProjectTransformer.class, (WActionForm) pForm );
                String appli = (String) data[0];
                String project = (String) data[1];
                String[] tagNames = ( (String) data[2] ).split( " " );
                // Retrieval of the list of applications
                List applications = getUserApplicationListAsDTO( pRequest );
                // retrieval of the business layer
                IApplicationComponent ac = AccessDelegateHelper.getInstance( "Component" );
                Object[] paramIn = { applications, appli, project, tagNames };
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
                pRequest.setAttribute( "firstCall", true );
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
