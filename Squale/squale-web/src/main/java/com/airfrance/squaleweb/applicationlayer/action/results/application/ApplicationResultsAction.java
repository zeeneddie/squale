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
package com.airfrance.squaleweb.applicationlayer.action.results.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.servlet.ServletUtilities;

import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squaleweb.applicationlayer.action.ActionUtils;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.ReaderAction;
import com.airfrance.squaleweb.applicationlayer.formbean.LogonBean;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ApplicationForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ProjectForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ProjectListForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.SplitAuditsListForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ResultListForm;
import com.airfrance.squaleweb.transformer.ApplicationTransformer;
import com.airfrance.squaleweb.transformer.FactorsResultListTransformer;
import com.airfrance.squaleweb.util.SqualeWebConstants;
import com.airfrance.squaleweb.util.graph.GraphMaker;
import com.airfrance.squaleweb.util.graph.KiviatMaker;
import com.airfrance.squaleweb.util.graph.PieChartMaker;
import com.airfrance.welcom.outils.pdf.PDFDataJasperReports;
import com.airfrance.welcom.outils.pdf.PDFEngine;
import com.airfrance.welcom.outils.pdf.PDFFactory;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;
import com.airfrance.welcom.struts.util.WConstants;
import com.airfrance.welcom.taglib.table.TableUtil;

/**
 * Affichage des résultats de niveau application Les résultats disponibles au niveau de l'application sont affichés par
 * cette action, il s'agit des graphes de kiviat et de piechart ainsi que les facteurs
 */
public class ApplicationResultsAction
    extends ReaderAction
{

    /**
     * Synthèse d'une application Si l'application comporte un seul projet, la synthèse du projet est alors affichée.
     * Dans le cas contraire, trois onglets sont présentés : un qui donne la répartition, l'autre donnant les facteurs
     * par projet et le dernier donnant le kiviat.
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward summary( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                  HttpServletResponse pResponse )
    {

        // Cette action est appelée dans plusieurs contextes possibles
        // Depuis une action struts avec l'id de l'application ou depuis
        // une page de l'application sans id de l'application (on a alors affaire à l'application courante)
        ActionForward forward;
        try
        {
            // Add an user access for this application
            addUserAccess( pRequest, ActionUtils.getCurrentApplication( pRequest ).getId() );
            forward = checkApplication( pMapping, pRequest );
            // Si forward est renseigné, l'application contient un seul projet
            // On redirige donc vers le projet adéquat
            if ( null == forward )
            {
                // Récupération des informations concernant les facteurs
                ApplicationForm application = ActionUtils.getCurrentApplication( pRequest );
                // On remet à jour les form en session permettant d'accéder aux différents types d'audits
                SplitAuditsListForm auditsForm =
                    (SplitAuditsListForm) pRequest.getSession().getAttribute( "splitAuditsListForm" );
                if ( auditsForm != null )
                {
                    auditsForm.copyValues( application );
                    auditsForm.resetAudits( ActionUtils.getCurrentAuditsAsDTO( pRequest ) );
                    pRequest.getSession().setAttribute( "splitAuditsListForm", auditsForm );
                }
                List auditsDTO = ActionUtils.getCurrentAuditsAsDTO( pRequest );
                if ( auditsDTO != null )
                {
                    ( (ResultListForm) pForm ).resetAudits( auditsDTO );
                }
                // On efface ce form contenant les erreurs de l'application, car si on passe par ici
                // Ce form ne peut que contenir les informations pour une autre application
                pRequest.getSession().removeAttribute( "applicationErrorForm" );

                // Préparation de l'appel à la couche métier
                IApplicationComponent ac = AccessDelegateHelper.getInstance( "Results" );
                List applications =
                    Arrays.asList( WTransformerFactory.formToObj( ApplicationTransformer.class, application ) );
                Object[] paramIn = { applications, auditsDTO };
                // Les résultats sont retournés dans l'ordre imposé par la grille
                // on maintient cet ordre pour l'affichage
                List results = (List) ac.execute( "getApplicationResults", paramIn );
                pRequest.getSession().removeAttribute( SqualeWebConstants.RESULTS_KEY );
                if ( null != results && results.size() > 0 )
                {
                    // Transformation des résultats avant leur affichage par la couche
                    // welcom
                    WTransformerFactory.objToForm( FactorsResultListTransformer.class, (ResultListForm) pForm,
                                                   new Object[] { applications, results } );
                }
                else
                {
                    // si aucun resultat => liste vide
                    WTransformerFactory.objToForm( FactorsResultListTransformer.class, (ResultListForm) pForm,
                                                   new Object[] { applications, new ArrayList() } );
                }
                // Récupération des données permettant la génération du Kiviat et du PieChart de l'application
                if ( null != auditsDTO && null != auditsDTO.get( 0 ) )
                {
                    // Préparation de l'appel à la couche métier
                    ac = AccessDelegateHelper.getInstance( "Graph" );
                    Long pCurrentAuditId = new Long( ( (AuditDTO) auditsDTO.get( 0 ) ).getID() );
                    Object[] paramAuditId = { pCurrentAuditId };

                    // Recherche des données Kiviat
                    KiviatMaker maker = new KiviatMaker();
                    Map projectsValues = (Map) ac.execute( "getApplicationKiviatGraph", paramAuditId );
                    Set keysSet = projectsValues.keySet();
                    Iterator it = keysSet.iterator();
                    while ( it.hasNext() )
                    {
                        String key = (String) it.next();
                        maker.addValues( key, (SortedMap) projectsValues.get( key ), pRequest );
                    }
                    JFreeChart chartKiviat = maker.getChart();
                    ChartRenderingInfo infoKiviat = new ChartRenderingInfo( new StandardEntityCollection() );
                    // Initialisation du kiviat
                    String fileNameKiviat =
                        ServletUtilities.saveChartAsPNG( chartKiviat, KiviatMaker.DEFAULT_WIDTH,
                                                         KiviatMaker.DEFAULT_HEIGHT, infoKiviat, pRequest.getSession() );
                    GraphMaker applicationKiviatChart = new GraphMaker( pRequest, fileNameKiviat, infoKiviat );

                    // Pour l'export en PDF
                    pRequest.getSession().removeAttribute( "kiviatChart" );
                    pRequest.getSession().setAttribute(
                                                        "kiviatChart",
                                                        chartKiviat.createBufferedImage( KiviatMaker.DEFAULT_WIDTH,
                                                                                         KiviatMaker.DEFAULT_HEIGHT ) );

                    // Recherche des données PieChart
                    JFreeChart pieChart;
                    Object[] maps = (Object[]) ac.execute( "getApplicationPieChartGraph", paramAuditId );
                    String pPreviousAuditId = null;
                    // on peut ne pas avoir d'audit précédent
                    if ( auditsDTO.size() > 1 )
                    {
                        pPreviousAuditId = "" + ( (AuditDTO) auditsDTO.get( 1 ) ).getID();
                    }
                    PieChartMaker pieMaker = new PieChartMaker( null, pCurrentAuditId.toString(), pPreviousAuditId );
                    pieMaker.setValues( (Map) maps[0] );
                    pieChart = pieMaker.getChart( (Map) maps[1], pRequest );

                    ChartRenderingInfo infoPieChart = new ChartRenderingInfo( new StandardEntityCollection() );
                    // Initialisation du pieChart
                    String fileName =
                        ServletUtilities.saveChartAsPNG( pieChart, PieChartMaker.DEFAULT_WIDTH,
                                                         PieChartMaker.DEFAULT_HEIGHT, infoPieChart,
                                                         pRequest.getSession() );
                    GraphMaker applicationPieChart = new GraphMaker( pRequest, fileName, infoPieChart );

                    // Met à jour les 2 champs du form avec les 2 graphs calculés
                    ( (ResultListForm) pForm ).setKiviat( applicationKiviatChart );
                    ( (ResultListForm) pForm ).setPieChart( applicationPieChart );

                    // Met à jour le form en session
                    pRequest.getSession().setAttribute( "resultListForm", pForm );

                    // Pour l'export en PDF
                    pRequest.getSession().removeAttribute( "pieChart" );
                    pRequest.getSession().setAttribute(
                                                        "pieChart",
                                                        pieChart.createBufferedImage( PieChartMaker.DEFAULT_WIDTH,
                                                                                      PieChartMaker.DEFAULT_HEIGHT ) );
                }
                // Affichage des informations sur la page jsp
                forward = pMapping.findForward( "summary" );
            }
        }
        catch ( Exception e )
        {
            ActionErrors errors = new ActionErrors();
            // Traitement factorisé des erreurs
            handleException( e, errors, pRequest );
            // Sauvegarde des erreurs
            saveMessages( pRequest, errors );
            // Routage vers la page d'erreur
            forward = pMapping.findForward( "total_failure" );
        }
        // On est passé par un menu donc on réinitialise le traceur
        resetTracker( pRequest );
        // Indique que l'on vient d'une vue synthèse et pas d'une vue composant
        changeWay( pRequest, "false" );
        return forward;
    }

    /**
     * Vérification de l'application Si l'application est invalide ou s'il ne comporte qu'un seul projet, un forward est
     * renvoyé, sinon les projets sont extraits pour la mise à jour du menu de gauche.
     * 
     * @param pMapping le mapping.
     * @param pRequest la requête HTTP.
     * @return l'action à réaliser.
     * @throws Exception exception.
     */
    private ActionForward checkApplication( ActionMapping pMapping, HttpServletRequest pRequest )
        throws Exception
    {
        ActionForward forward = null;
        ApplicationForm application = ActionUtils.getCurrentApplication( pRequest );
        if ( null == application )
        {
            // l'id n'est pas valide
            ActionError error = new ActionError( "error.invalid_application" );
            ActionErrors errors = new ActionErrors();
            errors.add( "application", error );
            saveErrors( pRequest, errors );
            forward = pMapping.findForward( "failure" );
        }
        else
        {
            // On récupère la liste des projets de l'application
            List projects =
                ( (ProjectListForm) pRequest.getSession().getAttribute( BaseDispatchAction.PROJECTS_KEY ) ).getList();
            if ( null != projects && projects.size() == 1 )
            {
                // On récupère l'id du projet et on renvoie vers
                // l'affichage du projet
                Long projectId = new Long( ( (ProjectForm) projects.get( 0 ) ).getId() );
                pRequest.setAttribute( "projectId", projectId.toString() );
                // On ajoute aussi l'id du projet dans les paramétres pour le traceur
                // TODO FAB : regarder cette ligne décommentée (lien avec le traceur...)
                // pRequest.getParameterMap().put("projectId", new String[]{projectId.toString()});
                forward = pMapping.findForward( "project" );
            }
        }
        return forward;
    }

    /**
     * Action exportPDF pour StyleReport ou iText
     * 
     * @param mapping le actionMapping
     * @param form le form
     * @param request la request
     * @param response la response
     * @return l'actionForward
     * @throws ServletException exception pouvant etre levee
     */
    public ActionForward exportPDF( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response )
        throws ServletException
    {
        Collection results = TableUtil.getSortedTable( request.getSession(), "resultListForm", "list" );
        try
        {
            HashMap parameters = new HashMap();
            PDFDataJasperReports data =
                new PDFDataJasperReports( request.getLocale(), getResources( request ), results,
                                          "/com/airfrance/squaleweb/resources/jasperreport/ApplicationResults.jasper",
                                          false, parameters );
            // Pour récupérer les paramètres root
            parameters.put( "applicationName", ( (ResultListForm) form ).getApplicationName() );
            // La date ou le nom des audits
            parameters.put( "auditDate", ( (ResultListForm) form ).getAuditName() );
            parameters.put( "previousAuditDate", ( (ResultListForm) form ).getPreviousAuditName() );
            // L'image du pieChart
            parameters.put( "pieChart", (java.awt.Image) request.getSession().getAttribute( "pieChart" ) );
            // Récupération des données sur la volumétrie des projets de l'application
            // Préparation de l'appel à la couche métier
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Graph" );
            Long pCurrentAuditId = new Long( Long.parseLong( ( (ResultListForm) form ).getCurrentAuditId() ) );
            Object[] paramAuditId = { pCurrentAuditId };
            // Recherche des données PieChart
            JFreeChart pieChart;
            Object[] maps = (Object[]) ac.execute( "getApplicationPieChartGraph", paramAuditId );
            // On récupère la volumétrie par grille
            final int indexVol = 2;
            PieChartMaker pieMaker = new PieChartMaker( null, null, null );
            pieMaker.setValues( (Map) maps[indexVol] );
            pieChart = pieMaker.getChart( new HashMap(), request );
            // L'image du pieChart pour la volumetrie par grille
            parameters.put( "gridPieChart", pieChart.createBufferedImage( PieChartMaker.DEFAULT_WIDTH,
                                                                          PieChartMaker.DEFAULT_HEIGHT ) );
            // L'image du kiviat
            parameters.put( "kiviatChart", (java.awt.Image) request.getSession().getAttribute( "kiviatChart" ) ); // Le
            // nom
            // de
            // l'utilisateur
            LogonBean logon = (LogonBean) request.getSession().getAttribute( WConstants.USER_KEY );
            parameters.put( "userName", logon.getMatricule() );
            PDFFactory.generatePDFToHTTPResponse( data, response, "", PDFEngine.JASPERREPORTS );
        }
        catch ( Exception e )
        {
            throw new ServletException( e );
        }
        return null;
    }

}
