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
package com.airfrance.squaleweb.applicationlayer.action.homepagemanagement;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squalecommon.datatransfertobject.component.UserDTO;
import com.airfrance.squalecommon.datatransfertobject.config.web.HomepageComponentDTO;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.DefaultAction;
import com.airfrance.squaleweb.applicationlayer.formbean.LogonBean;
import com.airfrance.squaleweb.applicationlayer.formbean.homepagemanagement.HomepageManagementForm;
import com.airfrance.squaleweb.transformer.HomepageManagementTransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;
import com.airfrance.welcom.struts.util.WConstants;

/**
 * Action for the homepage management
 */
public class HomepageManagementAction
    extends DefaultAction
{

    /**
     * Action done at the loading of the homepageManagement jsp
     * 
     * @param pMapping The mapping
     * @param pForm The form
     * @param pRequest The http request
     * @param pResponse The servlet response
     * @return The action to do
     */
    public ActionForward state( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionErrors actionErrors = new ActionErrors();
        try
        {
            HomepageManagementForm form = (HomepageManagementForm) pForm;

            // Recovery of the current user
            LogonBean userLogonBean = (LogonBean) pRequest.getSession().getAttribute( WConstants.USER_KEY );
            UserDTO user = new UserDTO();
            user.setID( userLogonBean.getId() );

            // Recovery of the list of HomepageComponentDTO linked to the current user
            Object[] paramIn = { user };
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Homepage" );
            List<HomepageComponentDTO> homepageComponentDTOList =
                (List<HomepageComponentDTO>) ac.execute( "getHomepageConfig", paramIn );

            // Fill the form with the list of HomepageComponentDTO
            Object[] param = { homepageComponentDTOList };
            WTransformerFactory.objToForm( HomepageManagementTransformer.class, form, param );
            forward = pMapping.findForward( "success" );
        }
        catch ( ServletException e )
        {
            handleException( e, actionErrors, pRequest );
        }
        catch ( JrafEnterpriseException e )
        {
            handleException( e, actionErrors, pRequest );
        }
        if ( !actionErrors.isEmpty() )
        {
            // Messages backup
            saveMessages( pRequest, actionErrors );
            // Redirect to the error page
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * This action record the change done in the homepageManagement jsp in the database
     * 
     * @param mapping The mapping
     * @param form The form
     * @param request The http request
     * @param response The servlet response
     * @return The action to do
     */
    public ActionForward record( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response )
    {
        ActionForward forward = null;
        ActionErrors actionErrors = new ActionErrors();
        try
        {
            HomepageManagementForm pageForm = (HomepageManagementForm) form;
            HttpSession session = request.getSession();

            // Test of validity of the information in the form
            if ( isValid( pageForm, request ) )
            {
                // Creation of the list of HomepageComponentDTO based on the informations of the form
                ArrayList<HomepageComponentDTO> res = new ArrayList<HomepageComponentDTO>();
                Object[] param = { res };
                WTransformerFactory.formToObj( HomepageManagementTransformer.class, pageForm, param );

                // Recovery of the current user
                LogonBean userLogonBean = (LogonBean) session.getAttribute( WConstants.USER_KEY );
                UserDTO user = new UserDTO();
                user.setID( userLogonBean.getId() );

                // Record the new configuration
                Object[] paramIn = { user, res };
                IApplicationComponent ac = AccessDelegateHelper.getInstance( "Homepage" );
                ac.execute( "saveHomepageConfig", paramIn );

                // Add a message to the homepage_management.jsp if the record has done well
                ActionMessage message = new ActionMessage( "homepage_management.action.saveDone" );
                ActionMessages messages = new ActionMessages();
                messages.add( "msg", message );
                request.setAttribute( Globals.MESSAGE_KEY, messages );
            }
            forward = mapping.findForward( "success" );
        }
        catch ( WTransformerException e )
        {
            handleException( e, actionErrors, request );
        }
        catch ( JrafEnterpriseException e )
        {
            handleException( e, actionErrors, request );
        }

        // If there are information, we display them
        if ( !actionErrors.isEmpty() )
        {
            // Messages backup
            saveMessages( request, actionErrors );
            // Redirect to the error page
            forward = mapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * This method verify if the form is valid or not
     * 
     * @param form The form
     * @param request The hhtp request
     * @return true if the form is valid
     */
    private boolean isValid( HomepageManagementForm form, HttpServletRequest request )
    {
        boolean isValid = true;
        ActionErrors errors = new ActionErrors();

        // Does the the informations for the audit section are valid ?
        isValid = auditValid( form, errors );

        // Does the the informations for the result section are valid ?
        isValid = resultValid( form, errors, isValid );

        if ( !isValid )
        {
            request.setAttribute( Globals.ERROR_KEY, errors );
        }
        return isValid;
    }

    /**
     * This method check if the information in the audit block are valid
     * 
     * @param form The page form
     * @param errors The list of error messages
     * @return true if the information in the audit block are valid
     */
    private boolean auditValid( HomepageManagementForm form, ActionErrors errors )
    {
        boolean isValid = true;

        // When audit done is checked at least one element among audit successful, audit partial and audit failed should
        // be checked
        if ( form.isAuditDoneCheck() && !form.isAuditSuccessfullCheck() && !form.isAuditPartialCheck()
            && !form.isAuditFailedCheck() )
        {
            ActionMessage message = new ActionMessage( "homepage_management.action.auditDoneOneCheck" );
            errors.add( "msg", message );
            isValid = false;
        }
        try
        {
            // nb jours should be an int
            int value = Integer.parseInt( form.getAuditNbJours() );
            if ( value < 0 )
            {
                ActionMessage message = new ActionMessage( "homepage_management.action.negativeNbJours" );
                errors.add( "msg", message );
                isValid = false;
            }
        }
        catch ( NumberFormatException e )
        {
            // if it's not an int and audit done is checked then we sent an error message to the jsp.
            // And if audit done is not checked then we put the default value.
            if ( form.isAuditDoneCheck() )
            {
                ActionMessage message = new ActionMessage( "homepage_management.action.incorrectNbJours" );
                errors.add( "msg", message );
                isValid = false;
            }
            else
            {
                form.setAuditNbJours( HomepageComponentDTO.DEFAULT_AUDIT_NB_JOURS );
            }
        }
        return isValid;
    }

    /**
     * This method check if the information in the result block are valid
     * 
     * @param form The page form
     * @param errors The list of error messages
     * @param valid Actual state
     * @return true if the information in the result block are valid
     */
    private boolean resultValid( HomepageManagementForm form, ActionErrors errors, boolean valid )
    {
        boolean isValid = valid;

        // If result is checked then at least one element among result byGrid and kiviat should be checked
        if ( form.isResultCheck() && !form.isResultByGridCheck() && !form.isResultKiviatCheck() )
        {
            ActionMessage message = new ActionMessage( "homepage_management.action.resultOneCheck" );
            errors.add( "msg", message );
            isValid = false;
        }
        try
        {
            // kiviat width should be an int
            int value = Integer.parseInt( form.getKiviatWidth() );
            if ( value < 0 )
            {
                ActionMessage message = new ActionMessage( "homepage_management.action.negativeKiviatWidth" );
                errors.add( "msg", message );
                isValid = false;
            }
        }
        catch ( NumberFormatException e )
        {
            // if it's not an int and kiviat is checked then we send a message to the jsp
            if ( form.isResultKiviatCheck() )
            {
                ActionMessage message = new ActionMessage( "homepage_management.action.incorrectKiviatWidth" );
                errors.add( "msg", message );
                isValid = false;
            }
            // else if it's not an int and kiviat is not checked then we put the default value
            else
            {
                form.setAuditNbJours( HomepageComponentDTO.DEFAULT_KIVIAT_WIDTH );
            }
        }

        return isValid;
    }
}
