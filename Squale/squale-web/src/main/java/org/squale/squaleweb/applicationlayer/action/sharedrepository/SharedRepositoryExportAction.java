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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.AccessDelegateHelper;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.squalecommon.datatransfertobject.config.AdminParamsDTO;
import org.squale.squaleweb.applicationlayer.action.accessRights.AdminAction;
import org.squale.squaleweb.applicationlayer.formbean.LogonBean;
import org.squale.squaleweb.applicationlayer.formbean.component.ApplicationForm;
import org.squale.squaleweb.applicationlayer.formbean.sharedrepository.SharedRepositoryExportForm;
import org.squale.squaleweb.transformer.sharedrepository.ExportTransformer;
import org.squale.squaleweb.util.ExportThread;
import org.squale.welcom.struts.transformer.WTransformerException;
import org.squale.welcom.struts.transformer.WTransformerFactory;
import org.squale.welcom.struts.util.WConstants;

/**
 * Action linked to the page to do the export for the shared repository
 */
public class SharedRepositoryExportAction
    extends AdminAction
{

    /**
     * Action done on the access to the export page
     * 
     * @param mapping The mapping
     * @param form The form
     * @param request The http request
     * @param response The http response
     * @return The next action to do
     */
    public ActionForward detail( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response )
    {

        ActionForward forward = mapping.findForward( "total_failure" );
        ActionErrors actionErrors = new ActionErrors();

        ServletContext ctx = getServlet().getServletContext();
        Object attribute = ctx.getAttribute( "export_in_progress" );
        if ( attribute != null && attribute.toString().equals( "true" ) )
        {
            forward = mapping.findForward( "export_in_progress" );
        }
        else
        {
            try
            {
                SharedRepositoryExportForm pageForm = (SharedRepositoryExportForm) form;

                LogonBean logonBean = (LogonBean) request.getSession().getAttribute( WConstants.USER_KEY );
                List<ApplicationForm> allAppliWithResult = logonBean.getApplicationsList();

                Object[] param = { allAppliWithResult };
                WTransformerFactory.objToForm( ExportTransformer.class, pageForm, param );
                forward = mapping.findForward( "enter_export" );
            }
            catch ( WTransformerException e )
            {
                handleException( e, actionErrors, request );
            }

            // If there are error informations, we display them
            if ( !actionErrors.isEmpty() )
            {
                // Messages backup
                saveMessages( request, actionErrors );
                // Redirect to the error page
                forward = mapping.findForward( "total_failure" );
            }
        }

        return forward;
    }

    /**
     * Action done when the user clic on the button export
     * 
     * @param mapping The mapping
     * @param form The form
     * @param request The http request
     * @param response The http response
     * @return The next action to do
     */
    public ActionForward export( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response )
    {
        ActionForward forward = mapping.findForward( "total_failure" );
        ActionErrors actionErrors = new ActionErrors();

        try
        {
            launchExport( form, false, request.getLocale() );
            forward = mapping.findForward( "export_ask" );
        }
        catch ( WTransformerException e )
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
            // Redirect to the error page
            forward = mapping.findForward( "total_failure" );
        }

        return forward;
    }

    /**
     * Action done when the user clic on the button export
     * 
     * @param mapping The mapping
     * @param form The form
     * @param request The http request
     * @param response The http response
     * @return The next action to do
     */
    public ActionForward exportAll( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response )
    {
        ActionForward forward = mapping.findForward( "total_failure" );
        ActionErrors actionErrors = new ActionErrors();

        try
        {
            launchExport( form, true, request.getLocale() );
            forward = mapping.findForward( "export_ask" );
        }
        catch ( WTransformerException e )
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
            // Redirect to the error page
            forward = mapping.findForward( "total_failure" );
        }

        return forward;
    }

    /**
     * This method launch the export. The boolean argument indicate if we export all applications (true) or only those
     * selected (false)
     * 
     * @param form The form
     * @param all This boolean indicate if we export all the application
     * @param local The current local
     * @throws WTransformerException exception occurs during the transformation form to object
     * @throws JrafEnterpriseException Exception occurs during the recover of the export configuration parameters
     */
    private void launchExport( ActionForm form, boolean all, Locale local )
        throws WTransformerException, JrafEnterpriseException
    {
        SharedRepositoryExportForm pageForm = (SharedRepositoryExportForm) form;

        // List of selected applications
        ArrayList<Long> listSelectedApplicationsId = new ArrayList<Long>();
        // List of all applications
        ArrayList<Long> listApplicationsId = new ArrayList<Long>();

        Object[] param = { listSelectedApplicationsId, listApplicationsId };
        WTransformerFactory.formToObj( ExportTransformer.class, pageForm, param );

        // Recover mapping
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "SqualixConfig" );
        Object[] param2 = new Object[] {};
        List<AdminParamsDTO> mappingList = (List<AdminParamsDTO>) ac.execute( "getExportAdminParams", param2 );
        ExportThread export = null;
        if ( all )
        {
            export = new ExportThread( listApplicationsId, mappingList, getServlet(), local );
        }
        else
        {
            export = new ExportThread( listSelectedApplicationsId, mappingList, getServlet(), local );
        }
        Thread exportThread = new Thread( export );
        exportThread.start();
        ServletContext ctx = getServlet().getServletContext();
        ctx.setAttribute( "export_in_progress", "true" );
    }

}
