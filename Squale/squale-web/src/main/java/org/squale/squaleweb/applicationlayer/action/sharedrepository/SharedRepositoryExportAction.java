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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.AccessDelegateHelper;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.squalecommon.datatransfertobject.job.JobDTO;
import org.squale.squalecommon.datatransfertobject.sharedrepository.ApplicationExportDTO;
import org.squale.squaleweb.applicationlayer.action.accessRights.AdminAction;
import org.squale.squaleweb.applicationlayer.formbean.LogonBean;
import org.squale.squaleweb.applicationlayer.formbean.component.ApplicationForm;
import org.squale.squaleweb.applicationlayer.formbean.sharedrepository.SharedRepositoryExportForm;
import org.squale.squaleweb.transformer.sharedrepository.ExportTransformer;
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
     * The buffer size
     */
    private final int bufferSize = 4096;

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

        try
        {
            SharedRepositoryExportForm pageForm = (SharedRepositoryExportForm) form;
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "sharedRepositoryExport" );
            boolean exportInProgress = ( (Boolean) ac.execute( "isInProgress", new Object[] {} ) ).booleanValue();
            if ( exportInProgress )
            {
                pageForm.setInProgressJob( true );
            }
            else
            {
                LogonBean logonBean = (LogonBean) request.getSession().getAttribute( WConstants.USER_KEY );
                List<ApplicationForm> allApplication = logonBean.getApplicationsList();

                ArrayList<ApplicationExportDTO> allApplicationWithResult =
                    selectApplicationWithResult( allApplication, ac );

                Object[] param = new Object[] {};
                List<JobDTO> jobs = (List<JobDTO>) ac.execute( "getLastJobs", param );
                param = new Object[] { allApplicationWithResult, jobs };
                // pageForm.reset( mapping, request );
                // SharedRepositoryExportForm newForm = new SharedRepositoryExportForm();
                pageForm.init();
                WTransformerFactory.objToForm( ExportTransformer.class, pageForm, param );

                if ( pageForm.getSuccessfulJob() != null )
                {
                    File exportFile = retrieveExportFile();
                    if ( exportFile != null )
                    {
                        pageForm.setExportFile( true );
                    }
                }
            }
            // pageForm = newForm;
            forward = mapping.findForward( "enter_export" );
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
     * This method select the applications which have results among the application given in argument and then create a
     * list of ApplicationExportDTO
     * 
     * @param allApplication The list of application
     * @param ac The sharedRepositoryExport application component
     * @return The list of application selected
     * @throws JrafEnterpriseException exception occurs during the creation of the applicationExportDTO
     */
    private ArrayList<ApplicationExportDTO> selectApplicationWithResult( List<ApplicationForm> allApplication,
                                                                         IApplicationComponent ac )
        throws JrafEnterpriseException
    {
        ArrayList<ApplicationExportDTO> allApplicationWithResult = new ArrayList<ApplicationExportDTO>();
        for ( ApplicationForm applicationForm : allApplication )
        {
            if ( applicationForm.getHasResults() )
            {
                Object[] param = new Object[] { applicationForm.getApplicationId() };
                ApplicationExportDTO appDto = (ApplicationExportDTO) ac.execute( "getExportApplication", param );

                if ( appDto == null )
                {
                    appDto = new ApplicationExportDTO();
                    appDto.setApplicationId( Long.parseLong( applicationForm.getApplicationId() ) );
                    appDto.setApplicationName( applicationForm.getApplicationName() );
                }
                allApplicationWithResult.add( appDto );
            }
        }
        return allApplicationWithResult;
    }

    /**
     * Action done when the user click on the button export
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
        ArrayList<ApplicationExportDTO> applicationToExport;
        SharedRepositoryExportForm currentForm = (SharedRepositoryExportForm) form;
        try
        {
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "sharedRepositoryExport" );
            boolean exportInProgress = ( (Boolean) ac.execute( "isInProgress", new Object[] {} ) ).booleanValue();
            if ( exportInProgress )
            {
                currentForm.setInProgressJob( true );
            }
            else
            {
                Object[] transform = WTransformerFactory.formToObj( ExportTransformer.class, currentForm );
                applicationToExport = (ArrayList<ApplicationExportDTO>) transform[0];

                Object[] param = { applicationToExport };
                ac.execute( "applicationToExport", param );
                // If there is no application selected and one export scheduled, then we delete this scheduled export
                if ( !currentForm.getOneToExport() && currentForm.getScheduledJob() )
                {
                    param = new Object[] {};
                    ac.execute( "cancelJob", param );
                }
                // If there is no audit scheduled then we scheduled an export
                else if ( !currentForm.getScheduledJob() )
                {
                    param = new Object[] {};
                    ac.execute( "scheduledJob", param );
                }
            }
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
     * This method create the download of the export file
     * 
     * @param mapping The mapping
     * @param form The form
     * @param request The http request
     * @param response The http response
     * @return The next action to do
     */
    public ActionForward downloadExportFile( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                             HttpServletResponse response )
    {
        ActionForward forward = null;
        ActionErrors actionErrors = new ActionErrors();
        SharedRepositoryExportForm pageForm = (SharedRepositoryExportForm) form;
        IApplicationComponent ac;
        try
        {
            ac = AccessDelegateHelper.getInstance( "sharedRepositoryExport" );
            boolean exportInProgress = ( (Boolean) ac.execute( "isInProgress", new Object[] {} ) ).booleanValue();
            if ( exportInProgress )
            {
                pageForm.setInProgressJob( true );
                forward = mapping.findForward( "enter_export" );
            }
            else
            {
                if ( pageForm.getSuccessfulJob() != null )
                {
                    File exportFile = retrieveExportFile();
                    // We should have only one export file
                    if ( exportFile != null )
                    {
                        String filename = exportFile.getName();
                        response.setContentType( "multipart/zip" );
                        response.setHeader( "Content-Disposition", "attachment; filename=\"" + filename + "\";" );
                        response.setContentLength( (int) exportFile.length() );

                        try
                        {
                            writeExport( response, exportFile );
                        }
                        catch ( JrafEnterpriseException e )
                        {
                            handleException( e, actionErrors, request );
                        }
                    }
                }
            }
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
     * This method write the export file into the servlet response
     * 
     * @param response The servlet response
     * @param exportFile The export file
     * @throws JrafEnterpriseException Exception occurs during the writing
     */
    private void writeExport( HttpServletResponse response, File exportFile )
        throws JrafEnterpriseException
    {
        OutputStream os = null;
        InputStream is = null;
        try
        {
            try
            {
                os = response.getOutputStream();
                FileInputStream stream = new FileInputStream( exportFile );
                BufferedInputStream bis = new BufferedInputStream( stream );
                is = new BufferedInputStream( bis );
                int count;
                byte[] buf = new byte[bufferSize];
                while ( ( count = is.read( buf ) ) > -1 )
                {
                    os.write( buf, 0, count );
                }
            }
            finally
            {
                is.close();
                os.close();
            }
        }
        catch ( IOException e )
        {
            throw new JrafEnterpriseException( e );
        }
    }

    /**
     * this method retrieve the export file
     * 
     * @return The export file
     */
    private File retrieveExportFile()
    {
        File exportFile = null;
        String squaleHome = System.getenv( "SQUALE_HOME" );
        File exportDir = new File( squaleHome + "/Squalix/export" );
        File[] listFile = exportDir.listFiles();
        // We should have only one export file
        if ( listFile != null && listFile.length == 1 )
        {
            exportFile = listFile[0];
        }
        return exportFile;
    }

}
