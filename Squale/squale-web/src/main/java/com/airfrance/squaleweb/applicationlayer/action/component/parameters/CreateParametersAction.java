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
package com.airfrance.squaleweb.applicationlayer.action.component.parameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.airfrance.squaleweb.applicationlayer.action.accessRights.ReaderAction;
import com.airfrance.squaleweb.applicationlayer.action.component.CreateProjectAction;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm;
import com.airfrance.squaleweb.applicationlayer.formbean.creation.CreateProjectForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Interface pour les actions associées à la configuration d'une tâche
 */
public class CreateParametersAction
    extends ReaderAction
{

    /**
     * Permet de remplir le bean de la tâche et de l'enregistrer en session si besoin.
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward fill( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                               HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionMessages errors = new ActionMessages();
        try
        {
            forward = pMapping.findForward( "config" );
            // Affectation au projet courant
            CreateProjectForm project = (CreateProjectForm) pRequest.getSession().getAttribute( "createProjectForm" );
            pForm.reset( pMapping, pRequest );
            // Indicate if it's a new project
            if ( null != pRequest.getSession().getAttribute( "modification" ) )
            {
                ( (AbstractParameterForm) pForm ).setNewConf( false );
            }
            WTransformerFactory.objToForm( ( (AbstractParameterForm) pForm ).getTransformer(), ( (WActionForm) pForm ),
                                           getTransformerParameters( project, pRequest ) );
            // On ajout le nom du formulaire pour pouvoir le récupérer
            project.getTaskForms().add( ( (AbstractParameterForm) pForm ).getNameInSession() );
        }
        catch ( Exception e )
        {
            // Traitement factorisé des exceptions
            handleException( e, errors, pRequest );
            // Routage vers la page d'erreur
            forward = pMapping.findForward( "total_failure" );
        }
        if ( !errors.isEmpty() )
        {
            saveMessages( pRequest, errors );
        }
        // On est passé par un menu donc on réinitialise le traceur
        resetTracker( pRequest );
        return null;
    }

    /**
     * Ajoute les paramètres au projet
     * 
     * @param pMapping le mapping
     * @param pForm le formulaire
     * @param pRequest la requête
     * @param pResponse la réponse
     * @return l'action à réaliser
     */
    public ActionForward addParameters( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                        HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionMessages errors = new ActionMessages();
        // Affectation au projet courant
        CreateProjectForm project = (CreateProjectForm) pRequest.getSession().getAttribute( "createProjectForm" );
        try
        {
            forward = pMapping.findForward( "configure" );

            // On modifie les paramètres du projet pour cette tâche
            WTransformerFactory.formToObj( ( (AbstractParameterForm) pForm ).getTransformer(), (WActionForm) pForm,
                                           getTransformerParameters( project, pRequest ) );

            // On sauvegarde le projet
            new CreateProjectAction().saveProject( pMapping, project, pRequest, pResponse );
        }
        catch ( Exception e )
        {
            // Traitement factorisé des exceptions
            handleException( e, errors, pRequest );
        }
        if ( !errors.isEmpty() )
        {
            saveMessages( pRequest, errors );
        }
        // On est passé par un menu donc on réinitialise le traceur
        resetTracker( pRequest );
        return forward;
    }

    /**
     * This method return the parameters of the task necessary for the transformer
     * 
     * @param pProject The project form
     * @param pRequest The servlet request
     * @return The task parameters necessary for the transformer
     * @throws Exception Exception occured
     */
    public Object[] getTransformerParameters( CreateProjectForm pProject, HttpServletRequest pRequest ) throws Exception    
    {
        String taskName=  pRequest.getParameter( "taskName" );
        return new Object[] { pProject.getParameters(),taskName };
    }

    /**
     * Permet de déconfigurer la tâche.
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward removeParameters( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                           HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionMessages errors = new ActionMessages();
        try
        {
            forward = pMapping.findForward( "configure" );
            // Affectation au projet courant
            CreateProjectForm project = (CreateProjectForm) pRequest.getSession().getAttribute( "createProjectForm" );
            // On supprime toutes les entrées liées à la tâche dans les paramètres du projet
            String[] constants = ( (AbstractParameterForm) pForm ).getParametersConstants();
            // Modification asked by user so it's not a new configuration
            ( (AbstractParameterForm) pForm ).setNewConf( false );
            for ( int i = 0; i < constants.length; i++ )
            {
                project.getParameters().getParameters().remove( constants[i] );
            }
            // On sauvegarde le projet
            new CreateProjectAction().saveProject( pMapping, project, pRequest, pResponse );
            // Change status for configuration
            ( (AbstractParameterForm) pForm ).setNewConf( false );
        }
        catch ( Exception e )
        {
            // Traitement factorisé des exceptions
            handleException( e, errors, pRequest );
            // Routage vers la page d'erreur
            forward = pMapping.findForward( "total_failure" );
        }
        if ( !errors.isEmpty() )
        {
            saveMessages( pRequest, errors );
        }
        // On est passé par un menu donc on réinitialise le traceur
        resetTracker( pRequest );
        return forward;
    }
}
