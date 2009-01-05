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
/*
 * Créé le 13 sept. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squaleweb.applicationlayer.action.results.project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.servlet.ServletUtilities;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.result.ResultsDTO;
import com.airfrance.squaleweb.applicationlayer.action.ActionUtils;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.ReaderAction;
import com.airfrance.squaleweb.applicationlayer.action.export.xls.ExcelDataComponentsResultsList;
import com.airfrance.squaleweb.applicationlayer.action.export.xls.ExcelDataTopList;
import com.airfrance.squaleweb.applicationlayer.formbean.LogonBean;
import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ComponentForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ComponentResultListForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.TopListForm;
import com.airfrance.squaleweb.applicationlayer.tracker.TrackerStructure;
import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.squaleweb.transformer.ComponentResultListTransformer;
import com.airfrance.squaleweb.util.SqualeWebConstants;
import com.airfrance.squaleweb.util.graph.BubbleMaker;
import com.airfrance.squaleweb.util.graph.GraphMaker;
import com.airfrance.welcom.outils.excel.ExcelFactory;
import com.airfrance.welcom.outils.excel.ExcelGenerateurException;
import com.airfrance.welcom.outils.pdf.PDFDataJasperReports;
import com.airfrance.welcom.outils.pdf.PDFEngine;
import com.airfrance.welcom.outils.pdf.PDFFactory;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;
import com.airfrance.welcom.struts.util.WConstants;

/**
 * @author M400841 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class TopAction
    extends ReaderAction
{
    /** Keyword for tres keys attribute */
    public static final String TRE_KEYS_KEYWORD = "treKeys";

    /** Keyword for tre values attribute */
    public static final String TRE_VALUES_KEYWORD = "treValues";

    /**
     * Selectionne un nouveau projet courant à visualiser.
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward display( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                  HttpServletResponse pResponse )
    {

        ActionForward forward = null;
        ActionErrors errors = new ActionErrors();

        try
        {
            TopListForm topListForm = (TopListForm) pForm;
            // recupere le "type" de top
            topListForm.setComponentType( pRequest.getParameter( "componenttype" ) );
            topListForm.setTre( pRequest.getParameter( "tre" ) );

            // et calcule les resultats
            getResults( pRequest, topListForm );
        }
        catch ( Exception e )
        {
            handleException( e, errors, pRequest );
        }

        if ( !errors.isEmpty() )
        {
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "total_failure" );
        }
        else
        {
            forward = pMapping.findForward( "success" );
        }
        // Mise en place du traceur historique
        String name = WebMessages.getString( pRequest.getLocale(), pRequest.getParameter( "tre" ) );
        String way = pRequest.getParameter( "componenttype" );

        // On récupère la clé pour le traceur en fonction du type du composant
        if ( null != way )
        { // code défensif
            int index = way.lastIndexOf( "." );
            if ( index > 0 )
            {
                String tracker = way.substring( index, way.length() );
                name = WebMessages.getString( pRequest.getLocale(), "tracker.top" + tracker ) + name;
            }
        }
        updateHistTracker( name, "top.do?" + pRequest.getQueryString(), TrackerStructure.TOP_VIEW, pRequest, true );
        // Indique que l'on vient d'une vue top et pas d'une vue composant
        changeWay( pRequest, "false" );
        // Pour pouvoir ajouter le composant à la fin du traceur
        needToReset( pRequest, "true" );
        return forward;
    }

    /**
     * Redirige vers une jsp affichant le scatterplott
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward displayBubble( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                        HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionErrors errors = new ActionErrors();
        try
        {
            JFreeChart chartBubble = getBubbleChart( pRequest );
            ChartRenderingInfo infoBubble = new ChartRenderingInfo( new StandardEntityCollection() );
            // Sauvegarde de l'image du BubbleChart au format png dans le form
            String fileNameBubble =
                ServletUtilities.saveChartAsPNG( chartBubble, BubbleMaker.DEFAULT_WIDTH, BubbleMaker.DEFAULT_HEIGHT,
                                                 infoBubble, pRequest.getSession() );
            GraphMaker projectBubbleGraphMaker = new GraphMaker( pRequest, fileNameBubble, infoBubble );
            pRequest.setAttribute( SqualeWebConstants.BUBBLE_GRAPH_MAKER, projectBubbleGraphMaker );
        }
        catch ( Exception e )
        {
            handleException( e, errors, pRequest );
        }
        if ( !errors.isEmpty() )
        {
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "total_failure" );
        }
        else
        {
            forward = pMapping.findForward( "displayBubble" );
        }
        return forward;
    }

    /**
     * @param pRequest la requête
     * @return le graphe du scatterplot
     * @throws JrafEnterpriseException si erreur
     */
    public JFreeChart getBubbleChart( HttpServletRequest pRequest )
        throws JrafEnterpriseException
    {
        ComponentDTO projectDTO = (ComponentDTO) pRequest.getSession().getAttribute( BaseDispatchAction.PROJECT_DTO );
        AuditDTO currentAudit = (AuditDTO) pRequest.getSession().getAttribute( BaseDispatchAction.CURRENT_AUDIT_DTO );
        AuditDTO previousAudit = (AuditDTO) pRequest.getSession().getAttribute( BaseDispatchAction.PREVIOUS_AUDIT_DTO );
        // Code défensif pour éviter un null pointer,
        // le projectId est le currentAuditId ne peuvent pas etre nul (erreur),
        // le previousAuditId peut etre null et est gérée ultérieurement
        Long projectId = new Long( projectDTO.getID() );
        if ( projectId == null )
        {
            projectId = new Long( -1 );
        }
        Long currentAuditId = new Long( currentAudit.getID() );
        if ( currentAuditId == null )
        {
            currentAuditId = new Long( -1 );
        }
        Long previousAuditId = new Long( -1 );
        if ( null != previousAudit )
        {
            previousAuditId = new Long( previousAudit.getID() );
        }
        // Gestion en cache du scatterplott pour éviter de le recalculer si c'est le meme que celui en session
        BubbleMaker bubbleMaker = (BubbleMaker) pRequest.getSession().getAttribute( SqualeWebConstants.BUBBLE_GRAPH );
        // le chart est également stocké en session, en parallèle avec le bubble Maker
        JFreeChart chartBubble = (JFreeChart) pRequest.getSession().getAttribute( SqualeWebConstants.BUBBLE_CHART );
        // Si celui qui est en session n'est pas le meme, on le recalcule
        if ( bubbleMaker == null || !projectId.toString().equals( bubbleMaker.getProjectId() )
            || !currentAuditId.toString().equals( bubbleMaker.getCurrentAuditId() )
            || !previousAuditId.toString().equals( bubbleMaker.getPreviousAuditId() ) )
        {
            Object[] paramsBubble = { projectId, currentAuditId };
            // Préparation de l'appel à la couche métier
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Graph" );
            Object[] inputParams = (Object[]) ac.execute( "getProjectBubbleGraph", paramsBubble );
            if ( null != inputParams )
            {
                // index pour éviter les magic numbers
                int indexInParam = 0;
                // Construction du graphe en passant la requête, la position de l'axe des abscisses et la position
                // de l'axe des ordonnées
                bubbleMaker =
                    new BubbleMaker( pRequest.getLocale(), (Long) inputParams[indexInParam++],
                                     (Long) inputParams[indexInParam++] );
                bubbleMaker.addSerie( (String) inputParams[indexInParam++], (double[]) inputParams[indexInParam++],
                                      (double[]) inputParams[indexInParam++], (double[]) inputParams[indexInParam++] );
                chartBubble =
                    bubbleMaker.getChart( projectId.toString(), currentAuditId.toString(), previousAuditId.toString(),
                                          (double[]) inputParams[indexInParam++],
                                          (double[]) inputParams[indexInParam++],
                                          (double[]) inputParams[indexInParam++], (String[]) inputParams[indexInParam] );
            }
            else
            {
                // On enregistre en session le bubble par défaut
                bubbleMaker = new BubbleMaker( pRequest.getLocale(), new Long( 0 ), new Long( 0 ) );
                chartBubble =
                    bubbleMaker.getChart( projectId.toString(), currentAuditId.toString(), previousAuditId.toString(),
                                          new double[0], new double[0], new double[0], new String[0] );
            }
            // On met en session
            pRequest.getSession().setAttribute( SqualeWebConstants.BUBBLE_GRAPH, bubbleMaker );
            pRequest.getSession().setAttribute( SqualeWebConstants.BUBBLE_CHART, chartBubble );
        }
        return chartBubble;
    }

    /**
     * Redirige vers une jsp affichant le scatterplott
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward displayComponents( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                            HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionErrors errors = new ActionErrors();
        try
        {
            // if request is not build correctly
            forward = pMapping.findForward( "total_failure" );
            // retrieve tres and values in request
            String tres = pRequest.getParameter( TRE_KEYS_KEYWORD );
            String values = pRequest.getParameter( TRE_VALUES_KEYWORD );
            if ( null != tres && tres.length() > 0 && null != values && values.length() > 0 )
            {
                String[] treKeys = tres.split( "," );
                String[] treValues = values.split( "," );
                IApplicationComponent ac = AccessDelegateHelper.getInstance( "Results" );
                Object[] paramIn =
                    { new Long( ( (RootForm) pForm ).getProjectId() ), new Long(( (RootForm) pForm ).getCurrentAuditId()),
                    treKeys, treValues,
                        new Integer( WebMessages.getString( pRequest.getLocale(), "component.max" ) ) };
                // Number of components is limited
                List result = (List) ac.execute( "getComponentsWhereTres", paramIn );
                WTransformerFactory.objToForm( ComponentResultListTransformer.class, (WActionForm)pForm, new Object[]{result, treKeys, treValues} );
                forward = pMapping.findForward( "success" );
            }
        }
        catch ( NumberFormatException nfe )
        {
            // error in request
            handleException( nfe, errors, pRequest );
        }
        catch ( JrafEnterpriseException jee )
        {
            handleException( jee, errors, pRequest );
        }
        catch ( WTransformerException wte )
        {
            handleException( wte, errors, pRequest );
        }
        if ( !errors.isEmpty() )
        {
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * Permet de récupérer les résultats pour les tops
     * 
     * @param pRequest requete HTTP
     * @param pTopListForm TopListForm formulaire a lire
     * @throws Exception exception
     */
    private void getResults( final HttpServletRequest pRequest, final TopListForm pTopListForm )
        throws Exception
    {

        IApplicationComponent ac = AccessDelegateHelper.getInstance( "Results" );

        // recupere l'audit courant
        List auditDTOs = ActionUtils.getCurrentAuditsAsDTO( pRequest );
        AuditDTO audit = null;
        if ( auditDTOs != null )
        {
            audit = (AuditDTO) ( auditDTOs.get( 0 ) );
        }

        // et le projet courant
        ComponentDTO project = (ComponentDTO) pRequest.getSession().getAttribute( BaseDispatchAction.PROJECT_DTO );

        // appelle getTopResults de squaleCommon
        Object[] paramIn =
            { project, pTopListForm.getComponentType(), audit, pTopListForm.getTre(),
                new Integer( WebMessages.getString( pRequest.getLocale(), "component.max" ) ) };
        // Limitation du nombre de résultats
        ResultsDTO result = (ResultsDTO) ac.execute( "getTopResults", paramIn );

        ArrayList components = new ArrayList();

        // et met les resultats en formes pour la jsp (Bean)
        if ( result != null )
        {
            List componentDtoList = (List) result.getResultMap().get( null );
            result.getResultMap().remove( null );

            AuditDTO nextKey = (AuditDTO) result.getResultMap().keySet().iterator().next();
            List valuesList = (List) result.getResultMap().get( nextKey );

            // Conversion des ComponentDTO en Form
            ComponentDTO dto = null;
            ComponentForm form = null;
            // Parcours de chacun des composants
            for ( int i = 0; i < componentDtoList.size(); i++ )
            {
                dto = (ComponentDTO) componentDtoList.get( i );
                form = new ComponentForm();
                form.setId( dto.getID() );
                form.setName( dto.getName() );
                form.setFullName( dto.getFullName() );
                if ( valuesList.get( i ) == null )
                {
                    // Placement d'une chaîne par défaut
                    form.getMetrics().add( 0, WebMessages.getString( pRequest.getLocale(), "result.cant_display" ) );
                }
                else
                {
                    form.getMetrics().add( 0, valuesList.get( i ) + "" );
                }
                components.add( form );
            }

        }
        pTopListForm.setComponentListForm( components );

    }

    /**
     * Export components list to XLS format
     * 
     * @param mapping mapping
     * @param form bean
     * @param request http request
     * @param response response
     * @return null (change http response header)
     * @throws ServletException if error
     */
    public ActionForward exportComponentsToExcel( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                  HttpServletResponse response )
    throws ServletException
{
    // user name
    LogonBean logon = (LogonBean) request.getSession().getAttribute( WConstants.USER_KEY );
    try
    {
        // we have to get full name of components
        ExcelDataComponentsResultsList data =
            new ExcelDataComponentsResultsList( request.getLocale(), getResources( request ), (ComponentResultListForm) form,
                                  logon.getMatricule() );
        ExcelFactory.generateExcelToHTTPResponse( data, response, "SQUALE_components.xls" );
    } catch(ExcelGenerateurException ege) {
        throw new ServletException( ege );
    }
    return null;
}

    /**
     * Exporte la liste des tops au format XLS
     * 
     * @param mapping le actionMapping
     * @param form le form
     * @param request la request
     * @param response la response
     * @return l'actionForward
     * @throws ServletException exception pouvant etre levee
     */
    public ActionForward exportToExcel( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response )
        throws ServletException
    {
        // Le nom de l'utilisateur
        LogonBean logon = (LogonBean) request.getSession().getAttribute( WConstants.USER_KEY );
        try
        {
            // Il faut récupérer les noms complets des composants
            ExcelDataTopList data =
                new ExcelDataTopList( request.getLocale(), getResources( request ), (TopListForm) form,
                                      logon.getMatricule() );
            ExcelFactory.generateExcelToHTTPResponse( data, response, "TopResults.xls" );
        }
        catch ( Exception e )
        {
            throw new ServletException( e );
        }
        return null;
    }

    /**
     * Exporte les tops au format PDF
     * 
     * @param mapping le actionMapping
     * @param form le form
     * @param request la request
     * @param response la response
     * @return l'actionForward
     * @throws ServletException exception pouvant etre levee
     */
    public ActionForward exportToPDF( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                      HttpServletResponse response )
        throws ServletException
    {
        TopListForm theForm = (TopListForm) form;
        Collection data = theForm.getComponentListForm();
        try
        {
            HashMap parameters = new HashMap();
            PDFDataJasperReports pdfData =
                new PDFDataJasperReports( request.getLocale(), getResources( request ), data,
                                          "/com/airfrance/squaleweb/resources/jasperreport/tops.jasper", false,
                                          parameters );

            // Le nom de la métrique
            parameters.put( "tre", WebMessages.getString( request, theForm.getTre() ) );
            // Le type de composant
            parameters.put( "type", WebMessages.getString( request, theForm.getComponentType() ) );
            // Le nom de l'utilisateur
            LogonBean logon = (LogonBean) request.getSession().getAttribute( WConstants.USER_KEY );
            parameters.put( "userName", logon.getMatricule() );
            // Le nom de l'application
            parameters.put( "applicationName", theForm.getApplicationName() );
            // Le nom du projet
            parameters.put( "projectName", theForm.getProjectName() );
            // La date de l'audit
            parameters.put( "auditDate", theForm.getAuditName() );
            // La date de l'audit précédent
            parameters.put( "previousAuditDate", theForm.getPreviousAuditName() );
            PDFFactory.generatePDFToHTTPResponse( pdfData, response, "", PDFEngine.JASPERREPORTS );
        }
        catch ( Exception e )
        {
            throw new ServletException( e );
        }
        return null;
    }

}
