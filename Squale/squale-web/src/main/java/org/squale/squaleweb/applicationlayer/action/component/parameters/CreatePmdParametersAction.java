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
package org.squale.squaleweb.applicationlayer.action.component.parameters;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import org.squale.jraf.helper.AccessDelegateHelper;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.squaleweb.applicationlayer.action.component.CreateProjectAction;
import org.squale.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm;
import org.squale.squaleweb.applicationlayer.formbean.creation.CreateProjectForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WTransformerFactory;

/**
 * Action de paramétrage des paramètres de la tâche Java/Jsp PMD Une même action permet le paramétrage de la tâche PMD
 * dans un contexte java seul ou avec le contexte jsp
 */
public class CreatePmdParametersAction
    extends CreateParametersAction
{

    /**
     * @see org.squale.squaleweb.applicationlayer.action.component.parameters.CreateParametersAction#getTransformerParameters(org.squale.squaleweb.applicationlayer.formbean.creation.CreateProjectForm,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    public Object[] getTransformerParameters( CreateProjectForm pProject, HttpServletRequest pRequest )
        throws Exception
    {
        // Obtention des configurations disponibles
        IApplicationComponent acPmdAdmin = AccessDelegateHelper.getInstance( "PmdAdmin" );
        // on récupère la liste des configurations disponibles
        Collection configurationsDTO = (Collection) acPmdAdmin.execute( "getAllConfigurations" );
        Object[] params = { pProject.getParameters(), pProject.getConfigurableTask( "PmdTask" ), configurationsDTO };

        return params;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.squale.squaleweb.applicationlayer.action.component.parameters.CreateParametersAction#fill(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward fill( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                               HttpServletResponse pResponse )
    {
        super.fill( pMapping, pForm, pRequest, pResponse );
        // Only if it's a new project
        if ( ( (AbstractParameterForm) pForm ).isNewConf() )
        {
            // Save project in order to have a default value for checkstyle
            ActionMessages errors = new ActionMessages();
            CreateProjectForm project = (CreateProjectForm) pRequest.getSession().getAttribute( "createProjectForm" );
            try
            {
                WTransformerFactory.formToObj( ( (AbstractParameterForm) pForm ).getTransformer(), (WActionForm) pForm,
                                               getTransformerParameters( project, pRequest ) );
            }
            catch ( Exception e )
            {
                // Save exceptions
                handleException( e, errors, pRequest );
            }
            if ( !errors.isEmpty() )
            {
                saveMessages( pRequest, errors );
            }
            // Call business layer
            new CreateProjectAction().saveProject( pMapping, project, pRequest, pResponse );
        }
        return null;
    }

}
