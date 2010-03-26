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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.AccessDelegateHelper;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.sharedrepository.segmentref.CategoryRefType;
import org.squale.squalecommon.datatransfertobject.component.ApplicationLightDTO;
import org.squale.squalecommon.datatransfertobject.sharedrepository.segment.SegmentCategoryDTO;
import org.squale.squalecommon.datatransfertobject.sharedrepository.segment.SegmentDTO;
import org.squale.squaleweb.applicationlayer.action.accessRights.AdminAction;
import org.squale.squaleweb.applicationlayer.formbean.sharedrepository.ApplicationLightForm;
import org.squale.squaleweb.applicationlayer.formbean.sharedrepository.SegmentCategoryForm;
import org.squale.squaleweb.applicationlayer.formbean.sharedrepository.SegmentForm;
import org.squale.squaleweb.applicationlayer.formbean.sharedrepository.SharedRepositorySegmentationForm;
import org.squale.squaleweb.transformer.sharedrepository.ApplicationLightTransformer;
import org.squale.squaleweb.transformer.sharedrepository.SegmentCategoryTransformer;
import org.squale.squaleweb.transformer.sharedrepository.SegmentTransformer;
import org.squale.welcom.struts.transformer.WTransformerException;
import org.squale.welcom.struts.transformer.WTransformerFactory;

/**
 * Struts action linked to the segmentation activity
 */
public class SharedRepositorySegmentationAction
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
        SharedRepositorySegmentationForm currentForm = (SharedRepositorySegmentationForm) form;
        currentForm.initElementsSelected();
        IApplicationComponent ac;
        try
        {
            ac = AccessDelegateHelper.getInstance( "sharedRepoSegmentation" );

            // List of application available
            List<ApplicationLightDTO> listAppDtoLight =
                (List<ApplicationLightDTO>) ac.execute( "retrieveAppAndModule", new Object[] {} );
            List<ApplicationLightForm> appFormList = new ArrayList<ApplicationLightForm>();
            for ( ApplicationLightDTO applicationLightDTO : listAppDtoLight )
            {
                ApplicationLightForm appForm =
                    (ApplicationLightForm) WTransformerFactory.objToForm( ApplicationLightTransformer.class,
                                                                          applicationLightDTO );
                appFormList.add( appForm );
            }
            currentForm.setAppList( appFormList );
            currentForm.setCategoryList( new ArrayList<SegmentCategoryForm>() );
            forward = mapping.findForward( "segmentation" );
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
     * Action done on the access to the export page
     * 
     * @param mapping The struts mapping
     * @param form The form
     * @param request The http request
     * @param response The http response
     * @return The next action to do
     */
    public ActionForward retrieveSegment( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response )
    {

        ActionForward forward = mapping.findForward( "total_failure" );
        ActionErrors actionErrors = new ActionErrors();
        SharedRepositorySegmentationForm currentForm = (SharedRepositorySegmentationForm) form;
        IApplicationComponent segmentationAc;
        try
        {
            String appId = request.getParameter( "appId" );
            String modId = request.getParameter( "modId" );
            String parentId = request.getParameter( "parentId" );

            CategoryRefType type = CategoryRefType.application;
            String componentId = appId;
            if ( modId != null )
            {
                componentId = modId;
                type = CategoryRefType.module;
            }

            // We retrieve the list of all the segment
            segmentationAc = AccessDelegateHelper.getInstance( "sharedRepoSegmentation" );
            List<SegmentCategoryDTO> allCategory =
                (List<SegmentCategoryDTO>) segmentationAc.execute( "allCategory", new Object[] { type } );

            // We retrieve the list of segment linked to the chosen application / module
            List<Long> linkedSegment =
                (List<Long>) segmentationAc.execute( "retrieveLinkedSegments", new Object[] { componentId } );

            // We fill the form
            List<SegmentCategoryForm> categoryFormList = new ArrayList<SegmentCategoryForm>();
            SegmentCategoryForm categoryForm = null;
            for ( SegmentCategoryDTO segmentCategoryDTO : allCategory )
            {
                categoryForm = createCategoryForm( segmentCategoryDTO, linkedSegment );
                categoryFormList.add( categoryForm );
            }
            currentForm.setCategoryList( categoryFormList );
            currentForm.setAppSelected( appId );
            currentForm.setModuleSelected( modId );
            currentForm.setParentModule( parentId );
            forward = mapping.findForward( "segmentation" );

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
     * This method creates a categoryForm
     * 
     * @param segmentCategoryDTO The category to transform into categoryForm
     * @param linkedSegment The list of segment linked to the selected application
     * @return A categoryForm
     * @throws WTransformerException exception occurs during the creation
     */
    private SegmentCategoryForm createCategoryForm( SegmentCategoryDTO segmentCategoryDTO, List<Long> linkedSegment )
        throws WTransformerException
    {
        SegmentCategoryForm categoryForm = new SegmentCategoryForm();
        WTransformerFactory.objToForm( SegmentCategoryTransformer.class, categoryForm,
                                       new Object[] { segmentCategoryDTO } );
        List<SegmentDTO> segmentList = segmentCategoryDTO.getSegmentList();
        for ( SegmentDTO segmentDTO : segmentList )
        {
            boolean isChecked = false;
            if ( linkedSegment.contains( segmentDTO.getTechnicalId() ) )
            {
                isChecked = true;
            }
            SegmentForm segmentForm = new SegmentForm();
            Object[] obj = { segmentDTO, isChecked };
            WTransformerFactory.objToForm( SegmentTransformer.class, segmentForm, obj );
            categoryForm.addSegment( segmentForm );
        }
        return categoryForm;
    }

    /**
     * This action records the changes done. According to the user choices, the links segment <=> module are add or
     * remove
     * 
     * @param mapping The struts mapping
     * @param form The form
     * @param request The http request
     * @param response The http response
     * @return The next action to do
     */
    public ActionForward record( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response )
    {

        ActionForward forward = mapping.findForward( "total_failure" );
        ActionErrors actionErrors = new ActionErrors();
        try
        {
            SharedRepositorySegmentationForm currentForm = (SharedRepositorySegmentationForm) form;
            doSelectionUpdate( currentForm );
            forward = mapping.findForward( "detail" );
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
     * This method executes the update
     * 
     * @param currentForm The current struts form
     * @throws JrafEnterpriseException Exception occurs during the update
     */
    private void doSelectionUpdate( SharedRepositorySegmentationForm currentForm )
        throws JrafEnterpriseException
    {
        // The current component selected
        String componentSelected = currentForm.getElementSelected();

        // The list of segment previously linked to the component selected
        IApplicationComponent segmentationAc = AccessDelegateHelper.getInstance( "sharedRepoSegmentation" );
        List<Long> linkedSegment =
            (List<Long>) segmentationAc.execute( "retrieveLinkedSegments", new Object[] { componentSelected } );
        ArrayList<Long> segmentToAdd = new ArrayList<Long>();
        // For each segment of each category, the method tests if the segment is selected.
        // If yes then the method tests if this segment was already selected.
        // If yes then the method removes the segment from the linkedSegment list else the segment is added to the
        // segementToAdd list
        for ( SegmentCategoryForm category : currentForm.getCategoryList() )
        {
            boolean deprecatedCategory = category.getDeprecated();
            for ( SegmentForm segment : category.getSegmentList() )
            {
                boolean checkedElement = segment.isSelected();
                boolean deprecatedSegment = segment.getDeprecated();
                Long segId = Long.parseLong( segment.getIdentifier() );
                if ( deprecatedCategory || deprecatedSegment )
                {
                    if ( !linkedSegment.contains( segId ) )
                    {
                        linkedSegment.add( segId );
                    }
                }
                else if ( checkedElement )
                {
                    if ( linkedSegment.contains( segId ) )
                    {
                        linkedSegment.remove( segId );
                    }
                    else
                    {
                        segmentToAdd.add( segId );
                    }
                }
            }
        }

        /*
         * At this stage linkedSegment contains the list of segments which were linked to the component before and are
         * not linked now. And the segmentToAdd contains the list of segments which are now linked to the component
         * whereas they was not before.
         */
        // Execution of the update
        segmentationAc.execute( "updateSegments", new Object[] { componentSelected, segmentToAdd, linkedSegment } );

    }
}
