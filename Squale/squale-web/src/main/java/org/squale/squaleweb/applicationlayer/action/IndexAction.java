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
package org.squale.squaleweb.applicationlayer.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.servlet.ServletUtilities;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.AccessDelegateHelper;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.squalecommon.datatransfertobject.component.AuditDTO;
import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;
import org.squale.squalecommon.datatransfertobject.component.UserDTO;
import org.squale.squalecommon.datatransfertobject.config.web.HomepageComponentDTO;
import org.squale.squalecommon.datatransfertobject.result.ResultsDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squaleweb.applicationlayer.action.accessRights.DefaultAction;
import org.squale.squaleweb.applicationlayer.action.message.AdminNewsAction;
import org.squale.squaleweb.applicationlayer.formbean.HomepageForm;
import org.squale.squaleweb.applicationlayer.formbean.LogonBean;
import org.squale.squaleweb.applicationlayer.formbean.component.ApplicationForm;
import org.squale.squaleweb.applicationlayer.formbean.component.ApplicationListForm;
import org.squale.squaleweb.applicationlayer.formbean.component.SplitAuditsListForm;
import org.squale.squaleweb.comparator.ComponentComparator;
import org.squale.squaleweb.homepage.Stat;
import org.squale.squaleweb.resources.WebMessages;
import org.squale.squaleweb.transformer.ApplicationListTransformer;
import org.squale.squaleweb.transformer.FactorsResultListTransformer;
import org.squale.squaleweb.transformer.SplitAuditsListTransformer;
import org.squale.squaleweb.util.graph.GraphMaker;
import org.squale.squaleweb.util.graph.KiviatMaker;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WTransformerException;
import org.squale.welcom.struts.transformer.WTransformerFactory;
import org.squale.welcom.struts.util.WConstants;

/**
 * Action class for the homepage.jsp
 */
public class IndexAction
    extends DefaultAction
{
	/** Default Number of days for displaying in initUserSession for portlet */
    public static final int NUMBER_OF_DAYS_FOR_NEWS = 15;
    
    /** Use default page */
    private boolean defaultConfig;

    /** Display introduction ? */
    private boolean isDisplayIntroduction;

    /** Display the news ? */
    private boolean isDisplayNews;

    /** Display the audits */
    private boolean isDisplayAudits;

    /** Display the results ? */
    private boolean isDisplayResults;

    /** Display the statistics ? */
    private boolean isDisplayStatistics;

    /** Display the audit done ? */
    private boolean isDisplayAuditDone;

    /** Display the audit scheduled ? */
    private boolean isDisplayAuditScheduled;

    /** Display the audit successful */
    private boolean isDisplayAuditSuccessful;

    /** Display the partial audit ? */
    private boolean isDisplayAuditPartial;

    /** Display the failed audit ? */
    private boolean isDisplayAuditFailed;

    /** Display audit done and audit scheduled separately ? */
    private boolean isDisplaySeparately;

    /** Display result by grid ? */
    private boolean isDisplayResultByGrid;

    /** Display Kiviat ? */
    private boolean isDisplayResultKiviat;

    /** The number of days for search audit */
    private int nbJours;

    /** The widtg of one kiviat */
    private int kiviatWidth;
    
    /** Display Kiviat With All Factors ? */
    private boolean isDisplayResultKiviatAllFactors;

    /**
     * Action to execute before reach the homepage.jsp
     * 
     * @param mapping The mapping
     * @param form The form
     * @param request The http request
     * @param response The servlet response
     * @return The forward value for the redirection
     */
    public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                  HttpServletResponse response )
    {

        ActionMessages errors = new ActionMessages();
        ActionForward forward = null;

        try
        {
            HttpSession session = request.getSession();

            // Recovery of the user id
            LogonBean userLogonBean = (LogonBean) session.getAttribute( WConstants.USER_KEY );
            UserDTO user = new UserDTO();
            user.setID( userLogonBean.getId() );

            // Recovery of the homepage configuration (list of HomepageComponentDTO)
            Object[] paramIn = { user };
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Homepage" );
            List<HomepageComponentDTO> list = (List<HomepageComponentDTO>) ac.execute( "getHomepageConfig", paramIn );

            // If the user has no profile, we use the default profile
            defaultConfig = false;
            if ( list.size() == 0 )
            {
                list = new ArrayList<HomepageComponentDTO>();
                defaultHomepage( list );

            }

            // Creation of a map based on the list
            String[] elementToDisplay = new String[HomepageComponentDTO.ELEMENT];
            HashMap<String, HomepageComponentDTO> compoList = new HashMap<String, HomepageComponentDTO>();
            for ( HomepageComponentDTO compo : list )
            {
                compoList.put( compo.getComponentName(), compo );
            }

            // Set the value for the private attribute
            setDisplayValue( compoList );

            // Selection of the jsp to display and does the related action and set informations needed in the form
            jspAndAction( mapping, form, request, response, compoList, elementToDisplay );

            // Set in the form the list of element to display and flag on the use of the default configuration of the
            // homepage
            HomepageForm currentform = (HomepageForm) form;
            currentform.setElementToDisplay( elementToDisplay );
            currentform.setDefault( defaultConfig );

            forward = mapping.findForward( "success" );
        }
        catch ( JrafEnterpriseException e )
        {

            handleException( e, errors, request );
            saveMessages( request, errors );
            // Forward to the error jsp
            forward = mapping.findForward( "failure" );
        }
        return forward;
    }

    /**
     * This method set the value to the element to display
     * 
     * @param compoList The list of componentDTO
     */
    private void setDisplayValue( HashMap<String, HomepageComponentDTO> compoList )
    {

        isDisplayIntroduction =
            compoList.get( HomepageComponentDTO.INTRO ) != null ? Boolean.parseBoolean( compoList.get(
                                                                                                       HomepageComponentDTO.INTRO ).getComponentValue() )
                            : false;

        isDisplayNews =
            compoList.get( HomepageComponentDTO.NEWS ) != null ? Boolean.parseBoolean( compoList.get(
                                                                                                      HomepageComponentDTO.NEWS ).getComponentValue() )
                            : false;

        isDisplayAudits =
            compoList.get( HomepageComponentDTO.AUDIT ) != null ? Boolean.parseBoolean( compoList.get(
                                                                                                       HomepageComponentDTO.AUDIT ).getComponentValue() )
                            : false;
        if ( isDisplayAudits )
        {
            setAuditDisplayValue( compoList );
        }

        isDisplayResults =
            compoList.get( HomepageComponentDTO.RESULT ) != null ? Boolean.parseBoolean( compoList.get(
                                                                                                        HomepageComponentDTO.RESULT ).getComponentValue() )
                            : false;
        if ( isDisplayResults )
        {
            setResultDisplayValue( compoList );
        }

        isDisplayStatistics =
            compoList.get( HomepageComponentDTO.STAT ) != null ? Boolean.parseBoolean( compoList.get(
                                                                                                      HomepageComponentDTO.STAT ).getComponentValue() )
                            : false;

    }

    /**
     * This method set the value of the audit element to display
     * 
     * @param compoList The list of componentDTO
     */
    private void setAuditDisplayValue( HashMap<String, HomepageComponentDTO> compoList )
    {
        isDisplaySeparately =
            compoList.get( HomepageComponentDTO.AUDIT_SHOW_SEPARETELY ) != null ? Boolean.parseBoolean( compoList.get(
                                                                                                                       HomepageComponentDTO.AUDIT_SHOW_SEPARETELY ).getComponentValue() )
                            : false;

        isDisplayAuditDone =
            compoList.get( HomepageComponentDTO.AUDIT_DONE ) != null ? Boolean.parseBoolean( compoList.get(
                                                                                                            HomepageComponentDTO.AUDIT_DONE ).getComponentValue() )
                            : false;

        isDisplayAuditScheduled =
            compoList.get( HomepageComponentDTO.AUDIT_SCHEDULED ) != null ? Boolean.parseBoolean( compoList.get(
                                                                                                                 HomepageComponentDTO.AUDIT_SCHEDULED ).getComponentValue() )
                            : false;

        isDisplayAuditSuccessful =
            compoList.get( HomepageComponentDTO.AUDIT_SUCCESSFUL ) != null ? Boolean.parseBoolean( compoList.get(
                                                                                                                  HomepageComponentDTO.AUDIT_SUCCESSFUL ).getComponentValue() )
                            : false;

        isDisplayAuditPartial =
            compoList.get( HomepageComponentDTO.AUDIT_PARTIAL ) != null ? Boolean.parseBoolean( compoList.get(
                                                                                                               HomepageComponentDTO.AUDIT_PARTIAL ).getComponentValue() )
                            : false;

        isDisplayAuditFailed =
            compoList.get( HomepageComponentDTO.AUDIT_FAILED ) != null ? Boolean.parseBoolean( compoList.get(
                                                                                                              HomepageComponentDTO.AUDIT_FAILED ).getComponentValue() )
                            : false;

        nbJours =
            compoList.get( HomepageComponentDTO.AUDIT_NB_JOURS ) != null ? Integer.parseInt( compoList.get(
                                                                                                            HomepageComponentDTO.AUDIT_NB_JOURS ).getComponentValue() )
                            : Integer.parseInt( HomepageComponentDTO.DEFAULT_AUDIT_NB_JOURS );

    }

    /**
     * This method set the value of the result element to display
     * 
     * @param compoList The list of componentDTO
     */
    private void setResultDisplayValue( HashMap<String, HomepageComponentDTO> compoList )
    {
        isDisplayResultByGrid =
            compoList.get( HomepageComponentDTO.RESULT_BY_GRID ) != null ? Boolean.parseBoolean( compoList.get(
                                                                                                                HomepageComponentDTO.RESULT_BY_GRID ).getComponentValue() )
                            : false;

        isDisplayResultKiviat =
            compoList.get( HomepageComponentDTO.RESULT_KIVIAT ) != null ? Boolean.parseBoolean( compoList.get(
                                                                                                               HomepageComponentDTO.RESULT_KIVIAT ).getComponentValue() )
                            : false;

        kiviatWidth =
            compoList.get( HomepageComponentDTO.KIVIAT_WIDTH ) != null ? Integer.parseInt( compoList.get(
                                                                                                          HomepageComponentDTO.KIVIAT_WIDTH ).getComponentValue() )
                            : Integer.parseInt( HomepageComponentDTO.DEFAULT_KIVIAT_WIDTH );
            
        isDisplayResultKiviatAllFactors =
            compoList.get( HomepageComponentDTO.KIVIAT_ALL_FACTORS ) != null ? Boolean.parseBoolean( compoList.get(
                                                                                                               HomepageComponentDTO.KIVIAT_ALL_FACTORS ).getComponentValue() )
                            : false;

    }

    /**
     * This method put in an array the jsp to display and launch the related action
     * 
     * @param mapping The mapping
     * @param form The form
     * @param request The http request
     * @param response The servlet response
     * @param compoList The list of HomepageComponent
     * @param elementToDisplay The list of jsp to display
     * @throws JrafEnterpriseException Exception happened during one of the linked action
     */
    private void jspAndAction( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response, HashMap<String, HomepageComponentDTO> compoList,
                               String[] elementToDisplay )
        throws JrafEnterpriseException
    {
        /*
         * For each element which are displayable, if its related boolean is set to true then that mean it should be
         * display so we add its jsp name to the list of element to display
         */

        // For the introduction part
        HomepageComponentDTO temporCompo;
        if ( isDisplayIntroduction )
        {
            temporCompo = compoList.get( HomepageComponentDTO.INTRO );
            elementToDisplay[temporCompo.getComponentPosition() - 1] =
                "/jsp/homepage/" + temporCompo.getComponentName() + ".jsp";
        }

        // For the news part
        if ( isDisplayNews )
        {
            temporCompo = compoList.get( HomepageComponentDTO.NEWS );
            elementToDisplay[temporCompo.getComponentPosition() - 1] =
                "/jsp/homepage/" + temporCompo.getComponentName() + ".jsp";
            news( mapping, form, request, response );
        }

        // For the audit part
        if ( isDisplayAudits )
        {
            temporCompo = compoList.get( HomepageComponentDTO.AUDIT );
            elementToDisplay[temporCompo.getComponentPosition() - 1] =
                "/jsp/homepage/" + temporCompo.getComponentName() + ".jsp";
            audit( request, compoList, form );
        }

        // For the results parts
        if ( isDisplayResults )
        {
            temporCompo = compoList.get( HomepageComponentDTO.RESULT );
            elementToDisplay[temporCompo.getComponentPosition() - 1] =
                "/jsp/homepage/" + temporCompo.getComponentName() + ".jsp";
            result( request, form, compoList );
        }

        // For the statistics part
        if ( isDisplayStatistics )
        {
            temporCompo = compoList.get( HomepageComponentDTO.STAT );
            elementToDisplay[temporCompo.getComponentPosition() - 1] =
                "/jsp/homepage/" + temporCompo.getComponentName() + ".jsp";
            stat( form, request );
        }

    }

    /**
     * This method do the action needed for display the news part in the homepage.jsp
     * 
     * @param mapping The mapping
     * @param form The form
     * @param request The http request
     * @param response The servlet response
     */
    private void news( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response )
    {

        // Flag for the valid news
        request.setAttribute( "which", "current" );
        // Flag for the language
        request.setAttribute( "lang", request.getLocale().getLanguage() );

        // Recover the news list and set them in session
        AdminNewsAction newsAction = new AdminNewsAction();
        newsAction.listNews( mapping, form, request, response );
    }

    /**
     * Action to do if the audit part is present in the homepage
     * 
     * @param request The http request
     * @param form The form
     * @param compoList The list of HomepageComponent of the user
     * @throws JrafEnterpriseException Exception happen during the action
     */
    private void audit( HttpServletRequest request, HashMap<String, HomepageComponentDTO> compoList, ActionForm form )
        throws JrafEnterpriseException
    {

        try
        {
            // List of the applications for which the current user is define as manager or reader
            ArrayList<ApplicationForm> applications =
                (ArrayList<ApplicationForm>) getUserNotPublicApplicationList( request );

            // If the list of application is not empty
            if ( !applications.isEmpty() )
            {
                // Transform the list of applicationForm contain inside an applicationListForm into a list of
                // componentDTO
                ApplicationListForm appliForm = new ApplicationListForm();
                appliForm.setList( applications );
                ArrayList<ComponentDTO> userApplicationsDTO =
                    (ArrayList<ComponentDTO>) WTransformerFactory.formToObj( ApplicationListTransformer.class,
                                                                             appliForm )[0];

                // The limit date for the search the audits
                Calendar cal = Calendar.getInstance();
                cal.add( Calendar.DATE, -nbJours );

                // Definition of the list of excluded status. That means it's the list of the status of audits to not
                // display
                ArrayList<Integer> statusId = new ArrayList<Integer>();
                statusId.add( AuditBO.DELETED );

                // Modify the list statusId according to what should be show for the audit done
                statudIdForAuditDone( statusId );

                // if auditDone and auditScheduled should be show separately, then a second audit collection is needed
                Collection separateAudits = new ArrayList();

                // ApplicationComponent for "component"
                IApplicationComponent ac = AccessDelegateHelper.getInstance( "Component" );

                // If auditDone and auditScheduled should be show separately
                if ( isDisplayAuditDone && isDisplayAuditScheduled && isDisplaySeparately )
                {
                    separateAudits = auditShowSeparetely( statusId, userApplicationsDTO, cal, ac );
                }
                // If auditDone and auditScheduled shouldn't be show separately
                else
                {
                    // if the scheduled audits shouldn't be displayed
                    if ( !isDisplayAuditScheduled )
                    {
                        statusId.add( AuditBO.NOT_ATTEMPTED );
                    }
                }

                // Creation of the first audit collection
                Integer[] status = new Integer[statusId.size()];
                statusId.toArray( status );
                Object[] paramIn = { userApplicationsDTO, cal.getTime(), status };
                Collection audits = (Collection) ac.execute( "getAllAuditsAfterDate", paramIn );

                // Transform object to form
                SplitAuditsListForm temporaryForm = new SplitAuditsListForm();
                WTransformerFactory.objToForm( SplitAuditsListTransformer.class, (WActionForm) temporaryForm,
                                               separateAudits, audits );
                HomepageForm currentForm = (HomepageForm) form;
                currentForm.setAudits( temporaryForm.getAudits() );
                currentForm.setScheduledAudits( temporaryForm.getPublicAudits() );
            }
        }
        catch ( WTransformerException e )
        {
            throw new JrafEnterpriseException( "WTransformerException", e );
        }

    }

    /**
     * Action to do if the audit part is present in the holmepage
     * 
     * @param request The http request
     * @param form The page form
     * @param compoList The list of HomepageComponentDTO
     * @throws JrafEnterpriseException Exception happened during the action
     */
    private void result( HttpServletRequest request, ActionForm form, HashMap<String, HomepageComponentDTO> compoList )
        throws JrafEnterpriseException
    {
        try
        {

            // List of the applications which have results and for which the current user is define as manager or reader
            ApplicationListForm applicationListForm = new ApplicationListForm();
            List applicationsList = new ArrayList();
            applicationsList.addAll( getUserApplicationWithResultsList( request ) );
            applicationListForm.setList( applicationsList );

            // Transform a list of ApplicationForm to a list of componentDTO
            List<ComponentDTO> applications =
                (List<ComponentDTO>) ( WTransformerFactory.formToObj( ApplicationListTransformer.class,
                                                                      applicationListForm )[0] );
            Collections.sort( applications, new ComponentComparator() );

            HomepageForm currentForm = (HomepageForm) form;
            currentForm.setList( new ArrayList() );
            currentForm.setGraphMakerMap( new HashMap<Long, GraphMaker>() );

            // If the result by grid should be displayed
            if ( isDisplayResultByGrid )
            {
                // Recovering the results for each applications
                IApplicationComponent ac = AccessDelegateHelper.getInstance( "Results" );
                Object[] paramIn = { applications, null };
                List applicationResults = ( (List) ac.execute( "getApplicationResults", paramIn ) );

                // Transform the list of applications and results and set them into the form
                WTransformerFactory.objToForm( FactorsResultListTransformer.class, (WActionForm) form, new Object[] {
                    applications, applicationResults } );
            }

            // If the kiviat should be displayed
            if ( isDisplayResultKiviat )
            {
                resultKiviat( applications, request, form );
            }
        }
        catch ( WTransformerException e )
        {
            throw new JrafEnterpriseException( "WTransformerException", e );
        }

    }

    /**
     * Action to do if statistics part is present in the homepage
     * 
     * @param form The page form
     * @param request The http request
     * @throws JrafEnterpriseException Exception happened during the action
     */
    private void stat( ActionForm form, HttpServletRequest request )
        throws JrafEnterpriseException
    {
        try
        {
            HomepageForm currentForm = (HomepageForm) form;
            ApplicationListForm applicationListForm = new ApplicationListForm();

            // recovery of the application which have results and for which the user is reader or manager
            ArrayList<ApplicationForm> applicationsList =
                (ArrayList<ApplicationForm>) getUserApplicationWithResultsList( request );

            // Creation of a list of componentDTO
            applicationListForm.setList( applicationsList );
            List<ComponentDTO> applicationsDTO =
                (List<ComponentDTO>) ( WTransformerFactory.formToObj( ApplicationListTransformer.class,
                                                                      applicationListForm )[0] );
            // sort of the list
            Collections.sort( applicationsDTO, new ComponentComparator() );

            // for each application, filling of volumetry list
            List<Stat> volumetry = new ArrayList<Stat>();
            Iterator<ComponentDTO> itApplicationsDTO = applicationsDTO.iterator();
            while ( itApplicationsDTO.hasNext() )
            {
                ComponentDTO application = itApplicationsDTO.next();
                fillVolumetry( application, volumetry );
                if ( itApplicationsDTO.hasNext() )
                {
                    Stat vide = new Stat();
                    volumetry.add( vide );
                }
            }

            currentForm.setVolumetrie( volumetry );
        }
        catch ( WTransformerException e )
        {
            throw new JrafEnterpriseException( "WTransformerException", e );
        }

    }

    /**
     * This method fill the volumetry list by using the result of each application
     * 
     * @param application The applicatioon on which we want recovery statistics
     * @param volumetry The list of lines of Stat
     * @throws JrafEnterpriseException Exception occur during the action
     */
    private void fillVolumetry( ComponentDTO application, List<Stat> volumetry )
        throws JrafEnterpriseException
    {
        // initialization
        Integer nbLigne = new Integer( 1 );
        Integer indexDepart = new Integer( 0 );
        Integer status = new Integer( AuditBO.TERMINATED );

        IApplicationComponent ac1 = AccessDelegateHelper.getInstance( "Component" );

        // Recovery of the audit last sucessfull audit of the application
        Object[] paramIn = { application, nbLigne, indexDepart, status };
        List<AuditDTO> auditsDTO = (List<AuditDTO>) ac1.execute( "getLastAllAudits", paramIn );
        AuditDTO auditDTO = auditsDTO.get( 0 );

        // Recovery of the project linked to the audit found
        Object[] paramIn2 = { application, null, auditDTO, null };
        List<ComponentDTO> projectDTOList = (List<ComponentDTO>) ac1.execute( "getChildren", paramIn2 );

        // Sort of the project
        Collections.sort( projectDTOList, new ComponentComparator() );

        int[] applicationStat = { 0, 0, 0, 0 };
        // For each project creation of the line Stat, and addition of this stat line to the volumetry list
        for ( ComponentDTO project : projectDTOList )
        {
            Stat projectStat = statLine( auditDTO, application, project, applicationStat );
            volumetry.add( projectStat );
        }

        // if the application has more than one project then we add a line of the stat on the application
        if ( projectDTOList.size() > 1 )
        {
            Stat statline =
                new Stat( application.getName(), "", String.valueOf( applicationStat[2] ),
                          String.valueOf( applicationStat[3] ), String.valueOf( applicationStat[0] ),
                          String.valueOf( applicationStat[1] ) );
            volumetry.add( statline );
        }

    }

    /**
     * This method create a line of Stat for a project
     * 
     * @param auditDTO The audits which contains the information of the project
     * @param application The application which contains the project
     * @param project The project for which we recover the statistics
     * @param applicationStat Table for the statistics on an application
     * @return a full line of stat
     * @throws JrafEnterpriseException exception occur during the action
     */
    private Stat statLine( AuditDTO auditDTO, ComponentDTO application, ComponentDTO project, int[] applicationStat )
        throws JrafEnterpriseException
    {
        // recovery of the resultDTO link to the project and the audit
        Long currentAuditId = new Long( auditDTO.getID() );
        Object[] paramIn3 = { currentAuditId, project };
        IApplicationComponent ac2 = AccessDelegateHelper.getInstance( "Results" );
        ResultsDTO resultDTO = ( (ResultsDTO) ac2.execute( "getProjectVolumetry", paramIn3 ) );

        // Recovery of the statistics
        Map volumetries = resultDTO.getResultMap();
        List<Integer> measureValues = (List<Integer>) volumetries.get( project );
        Integer nbMethods = measureValues.get( 2 );
        Integer nbClasses = measureValues.get( 1 );
        Integer nbCommentsLines = measureValues.get( 0 );
        Integer nbCodesLines = measureValues.get( 3 );

        // Creation of the staistics line
        Stat projectStat =
            new Stat( application.getName(), project.getName(), nbCodesLines.toString(), nbCommentsLines.toString(),
                      nbMethods.toString(), nbClasses.toString() );

        // Set the information for the application statistics
        applicationStat[0] = applicationStat[0] + nbMethods;
        applicationStat[1] = applicationStat[1] + nbClasses;
        applicationStat[2] = applicationStat[2] + nbCodesLines;
        applicationStat[3] = applicationStat[3] + nbCommentsLines;

        return projectStat;
    }

    /**
     * Action to do for display kiviat
     * 
     * @param applicationList The list of component
     * @param request The http request
     * @param form The page form
     * @throws JrafEnterpriseException Exception happened during the action
     */
    private void resultKiviat( List<ComponentDTO> applicationList, HttpServletRequest request, ActionForm form )
        throws JrafEnterpriseException
    {
        try
        {
            IApplicationComponent ac1 = AccessDelegateHelper.getInstance( "Component" );
            IApplicationComponent ac2 = AccessDelegateHelper.getInstance( "Graph" );

            HomepageForm currentForm = (HomepageForm) form;
            HashMap<Long, GraphMaker> graphMakerMap = new HashMap<Long, GraphMaker>();
            for ( ComponentDTO application : applicationList )
            {
                // Recovering of the last audit successful for the application
                Integer nbLigne = new Integer( 1 );
                Integer indexDepart = new Integer( 0 );
                Integer status = new Integer( AuditBO.TERMINATED );
                Object[] paramIn = { application, nbLigne, indexDepart, status };
                List<AuditDTO> auditsDTO = (List<AuditDTO>) ac1.execute( "getLastAllAudits", paramIn );

                // Instanciation of the kiviat graph
                String appli = WebMessages.getString( "homepage.result.kiviatApplication" );
                KiviatMaker maker = new KiviatMaker( appli + application.getName() );

                // Recovering the list of values needed for built the graph
                Long pCurrentAuditId = new Long( auditsDTO.get( 0 ).getID() );
                
                Boolean allFactors = new Boolean (isDisplayResultKiviatAllFactors);
                Object[] paramAuditId = { pCurrentAuditId , String.valueOf( allFactors )};
                Map projectsValues = (Map) ac2.execute( "getApplicationKiviatGraph", paramAuditId );
                Set keysSet = projectsValues.keySet();
                Iterator it = keysSet.iterator();

                // For each series of values we add them
                while ( it.hasNext() )
                {
                    String key = (String) it.next();
                    maker.addValues( key, (SortedMap) projectsValues.get( key ), request );
                }

                // Create the JfreChart object
                JFreeChart chartKiviat = maker.getChart( false, false );

                // Calculation of the height of the graph based on the width of the graph choose by the user
                int kiviatHeight = Math.round( kiviatWidth * 2.0f / 3 );

                // Create the picture
                ChartRenderingInfo infoKiviat = new ChartRenderingInfo( new StandardEntityCollection() );
                String fileNameKiviat =
                    ServletUtilities.saveChartAsPNG( chartKiviat, kiviatWidth, kiviatHeight, infoKiviat,
                                                     request.getSession() );

                // For a clickable picture
                GraphMaker applicationKiviatChart = new GraphMaker( request, fileNameKiviat, infoKiviat );

                graphMakerMap.put( pCurrentAuditId, applicationKiviatChart );
            }
            currentForm.setGraphMakerMap( graphMakerMap );
        }
        catch ( IOException e )
        {
            throw new JrafEnterpriseException( "IOException", e );
        }
    }

    /**
     * This method put in the satusId list, the status id which should be exclude according to the value of the
     * HomepageComponent link to the audit done
     * 
     * @param statusId The list of statusId
     */
    private void statudIdForAuditDone( ArrayList<Integer> statusId )
    {
        // If the audit done shouldn't be displayed
        if ( !isDisplayAuditDone )
        {
            statusId.add( AuditBO.TERMINATED );
            statusId.add( AuditBO.FAILED );
            statusId.add( AuditBO.PARTIAL );
            statusId.add( AuditBO.RUNNING );
        }
        // If the audit done should be displayed
        else
        {
            // If the successful audits shouldn't be displayed
            if ( !isDisplayAuditSuccessful )
            {
                statusId.add( AuditBO.TERMINATED );
            }
            // If the partial audits shouldn't be displayed
            if ( !isDisplayAuditPartial )
            {
                statusId.add( AuditBO.PARTIAL );
            }
            // If the failed audits shouldn't be displayed
            if ( !isDisplayAuditFailed )
            {
                statusId.add( AuditBO.FAILED );
            }
        }
    }

    /**
     * This method return the second collection of audits when audit done and audit scheduled should be display
     * separately.
     * 
     * @param statusId The list of statusId for the first collection
     * @param userApplicationsDTO The of the application accessible to the user
     * @param cal The calendar
     * @param ac The application component
     * @return a collection of audit
     * @throws JrafEnterpriseException Exception happened during the creation of the separetAudit collection
     */
    private Collection auditShowSeparetely( ArrayList<Integer> statusId, ArrayList userApplicationsDTO, Calendar cal,
                                            IApplicationComponent ac )
        throws JrafEnterpriseException
    {
        Collection separateAudits = new ArrayList();

        // Creation of the second status list
        ArrayList<Integer> statusScheduledId = new ArrayList<Integer>();
        statusScheduledId.add( AuditBO.DELETED );
        statusScheduledId.add( AuditBO.TERMINATED );
        statusScheduledId.add( AuditBO.FAILED );
        statusScheduledId.add( AuditBO.PARTIAL );
        statusScheduledId.add( AuditBO.RUNNING );

        // The scheduled audit shouldn't be selected in the first collection
        statusId.add( AuditBO.NOT_ATTEMPTED );

        Integer[] status = new Integer[statusScheduledId.size()];
        statusScheduledId.toArray( status );
        Object[] paramIn = { userApplicationsDTO, cal.getTime(), status };

        // Creation of the second audit collection
        separateAudits = (Collection) ac.execute( "getAllAuditsAfterDate", paramIn );

        return separateAudits;

    }

    /**
     * This method create a default set of HomepageComponent
     * 
     * @param list The list of HomepageComponent to fill
     */
    private void defaultHomepage( List<HomepageComponentDTO> list )
    {
        /*
         * If the user has no homepage configured in his profile, then we used the default profile. This default profile
         * display the introduction block and the news block.
         */

        HomepageComponentDTO component;

        component = new HomepageComponentDTO( HomepageComponentDTO.INTRO, "true" );
        component.setComponentPosition( 1 );
        list.add( component );

        component = new HomepageComponentDTO( HomepageComponentDTO.NEWS, "true" );
        component.setComponentPosition( 2 );
        list.add( component );

        /*
         * We set the attribute defaultConfig to true, then in the homepage.jsp a paragraph which explain how have its
         * own homepage will be displayed
         */
        defaultConfig = true;
    }
    
    /**
     * Enregistre l'utilisateur en session
     * 
     * @param pForm le formulaire
     * @param pRequest la requête
     * @return true si l'utilisateur a pu être mis en session
     */
    public boolean initUserSession( ActionForm pForm, HttpServletRequest pRequest )
    {
        boolean sessionOk;

        ActionMessages errors = new ActionMessages();

        try
        {
            SplitAuditsListForm auditList = (SplitAuditsListForm) pForm;
            // On récupère les applications non publiques appartenant à l'utilisateur
            Collection applications = getUserNotPublicApplicationList( pRequest );
            // On récupère les publiques
            Collection publics = getUserPublicApplicationList( pRequest );
            // Recherche des audits des 15 derniers jours
            // Calcul de la date d'ancienneté d'audit
            Calendar cal = Calendar.getInstance();
            cal.add( Calendar.DATE, -NUMBER_OF_DAYS_FOR_NEWS );
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Component" );
            boolean displayAllAudits = auditList.isAllAudits();
            ApplicationListForm appliForm = new ApplicationListForm();
            appliForm.setList( (ArrayList) applications );
            Object[] paramIn =
                { (ArrayList) WTransformerFactory.formToObj( ApplicationListTransformer.class, appliForm )[0],
                    cal.getTime(), new Boolean( true ) };
            if ( displayAllAudits )
            {
                paramIn[1] = null;
            }
            Collection audits = (Collection) ac.execute( "getAllAuditsAfterDate", paramIn );
            appliForm.setList( (ArrayList) publics );
            paramIn[0] = (ArrayList) WTransformerFactory.formToObj( ApplicationListTransformer.class, appliForm )[0];
            Collection publicAudits = (Collection) ac.execute( "getAllAuditsAfterDate", paramIn );
            // On transforme les listes en formulaire
            WTransformerFactory.objToForm( SplitAuditsListTransformer.class, (WActionForm) pForm, publicAudits, audits );
            sessionOk = true;
        }
        catch ( Exception e )
        {
            // Traitement factorisé des exceptions et transfert vers la page d'erreur
            handleException( e, errors, pRequest );
            saveMessages( pRequest, errors );
            sessionOk = false;
        }
        return sessionOk;
    }
}
