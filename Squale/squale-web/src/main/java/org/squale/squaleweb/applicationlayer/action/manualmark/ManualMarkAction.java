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
package org.squale.squaleweb.applicationlayer.action.manualmark;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.AccessDelegateHelper;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;
import org.squale.squalecommon.datatransfertobject.result.QualityResultDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;
import org.squale.squalecommon.util.TimeUtil;

import org.squale.squaleweb.applicationlayer.action.accessRights.DefaultAction;

import org.squale.squaleweb.applicationlayer.formbean.LogonBean;
import org.squale.squaleweb.applicationlayer.formbean.manualmark.ManualMarkElementForm;
import org.squale.squaleweb.applicationlayer.formbean.manualmark.ManualMarkForm;
import org.squale.squaleweb.resources.WebMessages;
import org.squale.squaleweb.transformer.manualmark.ManualMarkElementTransform;
import org.squale.squaleweb.transformer.manualmark.ManualMarkTransform;
import org.squale.squaleweb.util.TimelimitationUtil;
import org.squale.welcom.struts.transformer.WTransformerException;
import org.squale.welcom.struts.transformer.WTransformerFactory;
import org.squale.welcom.struts.util.WConstants;

/**
 * Action linked to the manual mark management jsp
 */
public class ManualMarkAction
    extends DefaultAction
{

    /**
     * This method recover the list of manual mark of a project and forward to the jsp which manage the manual marks
     * 
     * @param mapping The mapping.
     * @param form The jsp form.
     * @param request The HTTP request.
     * @param response The servlet response.
     * @return The action results.
     */
    public ActionForward list( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response )
    {
        ActionErrors errors = new ActionErrors();
        ActionForward forward = null;
        mapping.findForward( "total_failure" );
        ManualMarkForm currentForm = (ManualMarkForm) form;
        String componentId = currentForm.getProjectId();
        currentForm.setProjectIdSafe( componentId );
        String appliId = currentForm.getApplicationId();
        try
        {
            // Recovery of the manual mark of the project
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Results" );
            ArrayList<QualityResultDTO> elementToDisplay =
                (ArrayList<QualityResultDTO>) ac.execute( "listOfManualMark",
                                                          new Object[] { new Long( Long.parseLong( componentId ) ) } );
            // Transform the bo into form
            WTransformerFactory.objToForm( ManualMarkTransform.class, currentForm, new Object[] { elementToDisplay,
                request.getLocale() } );
            // Sort the collection of manual practice by their name
            Collections.sort( currentForm.getManualPracticeList() );

            // Initialize some ManualmarForm attribute
            currentForm.setTemporValue( "" );
            Calendar currentcalendar = TimeUtil.calDateOnly();
            currentForm.setTemporDate( currentcalendar.getTime() );
            currentForm.setEditLine( "-1" );
            currentForm.setOutOfDate( WebMessages.getString( request.getLocale(), "timelimitation.outOfDate" ) );

            // search if the user as the right to add/modify a manual practice
            LogonBean sessionUser = (LogonBean) request.getSession().getAttribute( WConstants.USER_KEY );
            String profil = ( sessionUser.getProfile( currentForm.getApplicationId() ) ).toString();
            String canModify = "false";
            if ( profil.equals( ProfileBO.ADMIN_PROFILE_NAME ) )
            {
                canModify = "true";
            }
            currentForm.setCanModify( canModify );

            forward = mapping.findForward( "success" );
        }
        catch ( JrafEnterpriseException e )
        {
            handleException( e, errors, request );
            saveMessages( request, errors );
        }
        catch ( WTransformerException e )
        {
            handleException( e, errors, request );
            saveMessages( request, errors );
        }
        // Reset of the tracker because we have used a menu
        resetTracker( request );
        return forward;
    }

    /**
     * This method record the quality result for manual mark which have been modified
     * 
     * @param mapping The mapping
     * @param form The JSP form
     * @param request The http request
     * @param response The servlet response
     * @return The action results
     */
    public ActionForward saveResult( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                     HttpServletResponse response )
    {
        ActionErrors errors = new ActionErrors();
        ActionForward forward = null;
        forward = mapping.findForward( "total_failure" );
        ManualMarkForm currentForm = (ManualMarkForm) form;
        try
        {
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Results" );
            // Recovery of the number of the line edited
            int editLine = Integer.parseInt( currentForm.getEditLine() );
            ManualMarkElementForm eltForm = currentForm.getManualPracticeList().get( editLine );
            boolean fieldValid = isValid( eltForm, request );
            // If the modified fields are valid
            if ( fieldValid )
            {
                // transform bo to DTO
                QualityResultDTO dto =
                    (QualityResultDTO) WTransformerFactory.formToObj( ManualMarkElementTransform.class, eltForm )[0];
                ComponentDTO compo = new ComponentDTO();
                compo.setID( Long.parseLong( currentForm.getProjectIdSafe() ) );
                dto.setProject( compo );
                // Save the new manual practice
                ac.execute( "saveManualResult", new Object[] { dto } );
                // Now no one line are edited
                currentForm.setEditLine( "-1.0" );
                // Compute of the new time left for the new mark
                eltForm.setTimeLeft( TimelimitationUtil.timeleft( eltForm.getTimelimitation(),
                                                                  eltForm.getCreationDate(), request.getLocale() ) );
            }
            // if the validation is KO
            else
            {
                /*
                 * We set in the eltForm the initial values in order to the user can decide to finally not modify the
                 * manual practice. If we don't do this, when the user click on the cancel picto he show the bad values
                 * whereas they are not record. He has to reload the page for have the good value. And we put the bad
                 * value in the tempor fields of the form in order to keep them when we go back to the page.
                 * When we go back to the page a script copy the tempor field in the edited field. 
                 */
                Date tprDate = currentForm.getTemporDate();
                currentForm.setTemporDate( eltForm.getCreationDate() );
                eltForm.setCreationDate( tprDate );

                String tprValue = currentForm.getTemporValue();
                currentForm.setTemporValue( eltForm.getValue() );
                eltForm.setValue( tprValue );
            }
            forward = mapping.findForward( "success" );
        }
        catch ( WTransformerException e )
        {
            handleException( e, errors, request );
            saveMessages( request, errors );
        }
        catch ( JrafEnterpriseException e )
        {
            handleException( e, errors, request );
            saveMessages( request, errors );
        }
        return forward;
    }

    /**
     * Validation of the field modify in the form
     * 
     * @param form the current form
     * @param request The http request
     * @return true if the validation is OK
     */
    private boolean isValid( ManualMarkElementForm form, HttpServletRequest request )
    {
        boolean valid = true;
        ActionErrors errors = new ActionErrors();
        boolean mark = validMark( form, errors );
        boolean date = validDate( form, errors );
        if ( !mark || !date )
        {
            valid = false;
            request.setAttribute( Globals.ERROR_KEY, errors );
        }
        return valid;
    }

    /**
     * Validation of the mark field of the form
     * 
     * @param form The current form
     * @param errors The list of errors launch during the validation
     * @return true if the validation is OK
     */
    private boolean validMark( ManualMarkElementForm form, ActionErrors errors )
    {
        boolean valid = true;
        // The field mark shouldn't be empty
        if ( form.getValue() == null )
        {
            ActionMessage message = new ActionMessage( "manualMark.action.nullMark" );
            errors.add( "msg", message );

            valid = false;
        }
        else
        {
            try
            {
                // The field mark should be a float
                float value = Float.parseFloat( form.getValue() );
                // The field mark should be between 0.0 and 3.0
                if ( value < 0.0 || value > 3.0 )
                {
                    ActionMessage message = new ActionMessage( "manualMark.action.invalidMark" );
                    errors.add( "msg", message );
                    valid = false;
                }
            }
            catch ( NumberFormatException e )
            {
                ActionMessage message = new ActionMessage( "manualMark.action.notNumber" );
                errors.add( "msg", message );
                valid = false;
            }
        }
        return valid;
    }

    /**
     * Validation of the date field
     * 
     * @param form The current form
     * @param errors The list of error launch by tghe validation
     * @return True if the validation is OK
     */
    private boolean validDate( ManualMarkElementForm form, ActionErrors errors )
    {
        boolean valid = true;
        // The field date shouldn't be empty
        if ( form.getCreationDate() == null )
        {
            ActionMessage message = new ActionMessage( "manualMark.action.nullDate" );
            errors.add( "msg", message );
            valid = false;
        }
        else
        {
            Date creaDate = form.getCreationDate();
            Calendar currentCal = TimeUtil.calDateOnly();
            // The date should be prior to today date
            if ( creaDate.after( currentCal.getTime() ) )
            {
                ActionMessage message = new ActionMessage( "manualMark.action.invalidDate" );
                errors.add( "msg", message );
                valid = false;
            }
        }
        return valid;
    }
}
