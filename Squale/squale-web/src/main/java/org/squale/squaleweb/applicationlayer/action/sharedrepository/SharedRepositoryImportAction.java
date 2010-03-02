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
package org.squale.squaleweb.applicationlayer.action.sharedrepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.AccessDelegateHelper;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.squaleweb.applicationlayer.action.accessRights.AdminAction;
import org.squale.squaleweb.applicationlayer.formbean.sharedrepository.SharedRepositoryImportForm;
import org.squale.squaleweb.servlet.UserSqualeSessionContext;

/**
 * Action linked to the shared repository import reference page
 */
public class SharedRepositoryImportAction
    extends AdminAction
{

    /**
     * Action done on the access to the import reference page
     * 
     * @param mapping The struts mapping
     * @param form The html page form
     * @param request The servlet request
     * @param response The servlet response
     * @return The next action
     */
    public ActionForward detail( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response )
    {
        ActionForward forward = mapping.findForward( "total_failure" );
        SharedRepositoryImportForm currentForm = (SharedRepositoryImportForm) form;
        UserSqualeSessionContext userSessionContext =
            (UserSqualeSessionContext) request.getSession().getAttribute(
                                                                          UserSqualeSessionContext.USER_SQUALE_SESSION_CONTEXT );
        currentForm.setCurrentReferenceVersion( userSessionContext.getImportReferenceVersion() );
        forward = mapping.findForward( "enter_import" );

        return forward;
    }

    /**
     * Action call when the import of a new reference file is asked
     * 
     * @param mapping The struts mapping
     * @param form The html page form
     * @param request The servlet request
     * @param response The servlet response
     * @return The next action
     */
    public ActionForward impor( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response )
    {
        ActionForward forward = mapping.findForward( "total_failure" );
        ActionErrors actionErrors = new ActionErrors();

        try
        {
            SharedRepositoryImportForm currentForm = (SharedRepositoryImportForm) form;
            InputStream is = currentForm.getInputStream();
            if ( is.available() > 0 )
            {
                // We try to import the new reference file
                tryToImport( currentForm, is, actionErrors, request );
            }
            else
            {
                ActionMessage message = new ActionMessage( "shared_repository.reference.noFile" );
                actionErrors.add( ActionMessages.GLOBAL_MESSAGE, message );
            }
            forward = mapping.findForward( "import_asked" );
        }
        catch ( FileNotFoundException e )
        {
            handleException( e, actionErrors, request );
        }
        catch ( IOException e )
        {
            handleException( e, actionErrors, request );
        }
        catch ( JrafEnterpriseException e )
        {
            handleException( e, actionErrors, request );
        }
        // If there are error informations, we display them
        if ( !actionErrors.isEmpty() )
        {
            // Messages backup
            saveMessages( request, actionErrors );
        }
        return forward;
    }

    /**
     * This method try to import the inpustream is
     * 
     * @param currentForm The current form
     * @param is The inputstream to import
     * @param actionErrors The list of error message
     * @param request The http request
     * @throws JrafEnterpriseException exception occurs during the import
     */
    private void tryToImport( SharedRepositoryImportForm currentForm, InputStream is, ActionErrors actionErrors,
                              HttpServletRequest request )
        throws JrafEnterpriseException
    {
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "sharedRepositoryImport" );
        Integer newReferenceVersion =
            (Integer) ac.execute( "importReference", new Object[] { is, currentForm.getCurrentReferenceVersion() } );
        // if newReferenceVersion is not null, that means the reference file is newer than the one already
        // inserted and that the new reference file has been inserted
        if ( newReferenceVersion != null )
        {
            ActionMessage message = new ActionMessage( "shared_repository.reference.loaded" );
            actionErrors.add( ActionMessages.GLOBAL_MESSAGE, message );
            currentForm.setCurrentReferenceVersion( newReferenceVersion );
            UserSqualeSessionContext userSessionContext =
                (UserSqualeSessionContext) request.getSession().getAttribute(
                                                                              UserSqualeSessionContext.USER_SQUALE_SESSION_CONTEXT );
            userSessionContext.setImportReferenceVersion( newReferenceVersion );
            UserSqualeSessionContext.setContext( request.getSession(), userSessionContext );
        }
        // else newReferenceVersion is null and the reference file has not been inserted.
        else
        {
            ActionMessage message = new ActionMessage( "shared_repository.reference.older" );
            actionErrors.add( ActionMessages.GLOBAL_MESSAGE, message );
        }
    }
}
