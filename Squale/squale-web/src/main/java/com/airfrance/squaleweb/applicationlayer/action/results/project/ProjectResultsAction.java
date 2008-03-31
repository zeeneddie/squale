package com.airfrance.squaleweb.applicationlayer.action.results.project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

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

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.result.ResultsDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.CriteriumRuleDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.FactorRuleDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.PracticeRuleDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.PracticeResultBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.AbstractFormulaBO;
import com.airfrance.squaleweb.applicationlayer.action.ActionUtils;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.ReaderAction;
import com.airfrance.squaleweb.applicationlayer.action.export.xls.ExcelDataMarkList;
import com.airfrance.squaleweb.applicationlayer.action.export.xml.IDELinkXMLData;
import com.airfrance.squaleweb.applicationlayer.action.export.xml.XMLData;
import com.airfrance.squaleweb.applicationlayer.action.export.xml.XMLFactory;
import com.airfrance.squaleweb.applicationlayer.formbean.LogonBean;
import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ApplicationForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ProjectForm;
import com.airfrance.squaleweb.applicationlayer.formbean.information.PracticeInformationForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ComponentForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.CriteriumForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.MarkForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ProjectFactorForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ProjectSummaryForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ResultForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ResultListForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ResultRulesCheckingForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.RuleCheckingItemsListForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.RuleCheckingPDFForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.RulesCheckingForm;
import com.airfrance.squaleweb.applicationlayer.tracker.TrackerStructure;
import com.airfrance.squaleweb.comparator.AuditComparator;
import com.airfrance.squaleweb.comparator.AuditGridComparator;
import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.squaleweb.transformer.ComponentTransformer;
import com.airfrance.squaleweb.transformer.MarkTransformer;
import com.airfrance.squaleweb.transformer.PracticeResultTransformer;
import com.airfrance.squaleweb.transformer.ProjectSummaryTransformer;
import com.airfrance.squaleweb.transformer.ProjectTransformer;
import com.airfrance.squaleweb.transformer.RuleCheckingItemsListTransformer;
import com.airfrance.squaleweb.transformer.RulesCheckingResultTransformer;
import com.airfrance.squaleweb.util.SqualeWebActionUtils;
import com.airfrance.squaleweb.util.SqualeWebConstants;
import com.airfrance.squaleweb.util.graph.BubbleMaker;
import com.airfrance.squaleweb.util.graph.GraphMaker;
import com.airfrance.squaleweb.util.graph.KiviatMaker;
import com.airfrance.squaleweb.util.graph.RepartitionBarMaker;
import com.airfrance.squaleweb.util.graph.RepartitionMaker;
import com.airfrance.welcom.outils.excel.ExcelFactory;
import com.airfrance.welcom.outils.pdf.PDFDataJasperReports;
import com.airfrance.welcom.outils.pdf.PDFEngine;
import com.airfrance.welcom.outils.pdf.PDFFactory;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;
import com.airfrance.welcom.struts.util.WConstants;

/**
 * @version 1.0
 * @author
 */
public class ProjectResultsAction
    extends ReaderAction
{

    /**
     * Le nom désignant l'attribut en session pour indiquer si on veut tous les facteurs ou non pour le kiviat.
     */
    private static final String ALL_FACTORS = "allFactors";

    /**
     * Selectionne un nouveau projet courant à visualiser.
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward select( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                 HttpServletResponse pResponse )
    {

        ActionForward forward = null;
        ActionErrors errors = new ActionErrors();

        try
        {
            // Add an user access for this application
            addUserAccess( pRequest, ActionUtils.getCurrentApplication( pRequest ).getId() );
            // On supprime l'attribut en session
            pRequest.getSession().removeAttribute( ALL_FACTORS );
            forward = init( pMapping, pRequest, pForm );
            if ( null == forward )
            {
                forward = pMapping.findForward( "summaryAction" );
            }
        }
        catch ( Exception e )
        {
            // Traitement factorisé des exceptions
            handleException( e, errors, pRequest );
        }
        if ( !errors.isEmpty() )
        {
            // Sauvegarde des messages
            saveMessages( pRequest, errors );
            // Routage vers la page d'erreur
            forward = pMapping.findForward( "total_failure" );
        }
        // On est passé par un menu donc on réinitialise le traceur
        resetTracker( pRequest );
        return forward;
    }

    /*
     * ======================================================================================================= Page de
     * résumé =======================================================================================================
     */

    /**
     * Présente les résultats résumés du projet.
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

        ActionForward forward = null;
        ActionErrors errors = new ActionErrors();
        Long currentAuditId = null, previousAuditId = null;
        try
        {
            forward = init( pMapping, pRequest, pForm );
            if ( null == forward )
            {
                if ( getSummary( pRequest, pForm ) )
                {
                    // Récupération des données permettant la génération du Kiviat du projet
                    // On récupère le projet
                    ComponentDTO project =
                        (ComponentDTO) pRequest.getSession().getAttribute( BaseDispatchAction.PROJECT_DTO );
                    AuditDTO audit =
                        (AuditDTO) pRequest.getSession().getAttribute( BaseDispatchAction.CURRENT_AUDIT_DTO );
                    AuditDTO previousAudit =
                        (AuditDTO) pRequest.getSession().getAttribute( BaseDispatchAction.CURRENT_AUDIT_DTO );
                    if ( project != null && audit != null )
                    {
                        // Récupération du paramètre : tous les facteurs ou seuls les facteurs ayant une note ?
                        ProjectSummaryForm projectSummaryForm = (ProjectSummaryForm) pForm;
                        boolean pAllFactors = projectSummaryForm.isAllFactors();
                        // On met cette valeur en session
                        pRequest.getSession().setAttribute( ALL_FACTORS, new Boolean( pAllFactors ) );
                        // Préparation de l'appel à la couche métier
                        IApplicationComponent ac = AccessDelegateHelper.getInstance( "Graph" );
                        Long projectId = new Long( project.getID() );
                        currentAuditId = new Long( audit.getID() );

                        Object[] paramsKiviat = { projectId, currentAuditId, String.valueOf( pAllFactors ) };

                        // Recherche des données utiles à la génération du Kiviat
                        // Recherche des données Kiviat
                        KiviatMaker maker = new KiviatMaker();
                        Object[] kiviatObject = (Object[]) ac.execute( "getProjectKiviatGraph", paramsKiviat );
                        Map projectsValues = (Map) kiviatObject[0];
                        Boolean displayCheckBoxFactors = (Boolean) kiviatObject[1];
                        projectSummaryForm.setDisplayCheckBoxFactors( displayCheckBoxFactors );

                        Set keysSet = projectsValues.keySet();
                        Iterator it = keysSet.iterator();
                        while ( it.hasNext() )
                        {
                            String key = (String) it.next();
                            maker.addValues( key, (SortedMap) projectsValues.get( key ), pRequest );
                        }
                        JFreeChart chartKiviat = maker.getChart();
                        ChartRenderingInfo infoKiviat = new ChartRenderingInfo( new StandardEntityCollection() );
                        // Sauvegarde de l'image du Kiviat au format png dans un espace temporaire
                        String fileNameKiviat =
                            ServletUtilities.saveChartAsPNG( chartKiviat, KiviatMaker.DEFAULT_WIDTH,
                                                             KiviatMaker.DEFAULT_HEIGHT, infoKiviat,
                                                             pRequest.getSession() );
                        GraphMaker projectKiviatChart = new GraphMaker( pRequest, fileNameKiviat, infoKiviat );

                        // Pour l'export pdf en attendant de mettre les graphe dans les formulaires
                        pRequest.getSession().removeAttribute( "kiviatChart" );
                        pRequest.getSession().setAttribute(
                                                            "kiviatChart",
                                                            chartKiviat.createBufferedImage( KiviatMaker.DEFAULT_WIDTH,
                                                                                             KiviatMaker.DEFAULT_HEIGHT ) );

                        ( (ProjectSummaryForm) pForm ).setKiviat( projectKiviatChart );
                    }
                    forward = pMapping.findForward( "summary" );
                }
                else
                { // Aucun audit, redirige vers la page courante
                    forward = pMapping.findForward( "noAudits" );
                }
            }
        }
        catch ( Exception e )
        {
            // Factorisation du traitement des exceptions
            handleException( e, errors, pRequest );
        }
        if ( !errors.isEmpty() )
        {
            // Enregistrement des messages
            saveMessages( pRequest, errors );
            // Routage vers la page d'erreur
            forward = pMapping.findForward( "total_failure" );
        }
        // Mise en place du traceur historique
        updateHistTracker( WebMessages.getString( pRequest, "tracker.synthesis" ), "project.do?action=summary",
                           TrackerStructure.UNDEFINED, pRequest, true );
        // Indique que l'on vient d'une vue synthèse et pas d'une vue composant
        changeWay( pRequest, "false" );
        return forward;
    }

    /**
     * Crée le résumé des données du projet.
     * 
     * @param pRequest la requête de l'action.
     * @param pForm le formulaire de la requête.
     * @return true
     * @throws Exception si un pb apparaît.
     */
    private boolean getSummary( final HttpServletRequest pRequest, final ActionForm pForm )
        throws Exception
    {
        ComponentDTO project =
            (ComponentDTO) WTransformerFactory.formToObj( ProjectTransformer.class,
                                                          ActionUtils.getCurrentProject( pRequest ) )[0];
        List auditsList = initAudit( pRequest, project );
        boolean result = auditsList.size() > 0;
        if ( result )
        {

            // **************************************************************************************************
            // Récupération des facteurs
            // **************************************************************************************************
            Collection factors = getFactorsList( auditsList, project ); // auditsDTO

            // **************************************************************************************************
            // Récupération de la volumetrie
            // **************************************************************************************************
            Long currentAuditId = new Long( ( (AuditDTO) auditsList.get( 0 ) ).getID() );
            Object[] paramIn = { currentAuditId, project };
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Results" );
            ResultsDTO resultDTO = ( (ResultsDTO) ac.execute( "getProjectVolumetry", paramIn ) );
            Map volumetries = resultDTO.getResultMap();
            Object[] paramIn2 = { currentAuditId, new Long( project.getID() ) };
            Boolean haveErrors = ( (Boolean) ac.execute( "getHaveErrors", paramIn2 ) );
            ac = AccessDelegateHelper.getInstance( "Component" );
            Boolean canBeExportedToIDE =
                ( (Boolean) ac.execute( "canBeExportedToIDE", new Object[] { new Long( project.getID() ) } ) );

            // Conversion du formulaire
            Object[] params = { project, factors, volumetries, haveErrors, canBeExportedToIDE };
            WTransformerFactory.objToForm( ProjectSummaryTransformer.class, (ProjectSummaryForm) pForm, params );

            // On récupère l'attribut indiquant si l'on veut tous les facteurs
            Boolean allFactors = (Boolean) pRequest.getSession().getAttribute( ALL_FACTORS );
            if ( null != allFactors )
            {
                // On modifie le form
                ( (ProjectSummaryForm) pForm ).setAllFactors( allFactors.booleanValue() );
                // On supprime l'attribut en session
                pRequest.getSession().removeAttribute( ALL_FACTORS );
            }
        }
        return result;
    }

    /**
     * Retourne les notes des facteurs du projet sur la liste d'audit donnée.
     * 
     * @param pAuditsList la liste d'audits.
     * @param pProject le projet.
     * @return le formulaire contenant la liste des facteurs.
     * @throws Exception si un problème apparaît.
     */
    private Collection getFactorsList( final List pAuditsList, final ComponentDTO pProject )
        throws Exception
    {
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "Results" );
        Object[] paramIn = { pAuditsList, pProject };
        // Les résultats sont retournés dans l'ordre imposé par la grille qualité
        // on maintient cet ordre pour l'affichage
        ResultsDTO resultDTO = ( (ResultsDTO) ac.execute( "getProjectResults", paramIn ) );
        // On récupère la liste des collections de résultats
        AuditGridComparator auditComp = new AuditGridComparator();
        Map resultMap = new TreeMap( auditComp );
        resultDTO.getResultMap().remove( null );
        resultMap.putAll( resultDTO.getResultMap() );
        List results = new LinkedList();
        results.addAll( resultMap.entrySet() );
        Collections.reverse( results );
        return results;
    }

    /*
     * ======================================================================================================= Page de
     * détails d'un facteur
     * =======================================================================================================
     */

    /**
     * Récupère les détails du facteur
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward factor( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                 HttpServletResponse pResponse )
    {

        ActionForward forward = null;
        ActionErrors errors = new ActionErrors();

        // Le nom du facteur pour le traceur
        String name = "";
        long factorRuleId = -1;

        try
        {
            // On vérifie les données renvoyées par le formulaire
            forward = init( pMapping, pRequest, pForm );
            if ( null == forward )
            {
                // Récupération des paramètres de la requête
                String param = (String) pRequest.getParameter( "which" );
                factorRuleId = Long.parseLong( param );
                // Obtention des audits courants
                List auditsDTO = ActionUtils.getCurrentAuditsAsDTO( pRequest );
                ComponentDTO project =
                    (ComponentDTO) WTransformerFactory.formToObj( ProjectTransformer.class,
                                                                  ActionUtils.getCurrentProject( pRequest ) )[0];

                FactorRuleDTO factor = getFactorRule( factorRuleId );
                // Récupère le nom pour le traceur
                name = factor.getName();

                // Récupération de la note et de l'évolution du facteur
                ( (ProjectSummaryForm) pForm ).setResults( getFactorResult( factor, auditsDTO, project, pRequest ) );
                // Récupération des critères
                ResultListForm criteria = getCriteriaList( factor, auditsDTO, project );
                pRequest.setAttribute( SqualeWebConstants.CHILDREN_KEY, criteria );
                forward = pMapping.findForward( "factor" );
            }
        }
        catch ( Exception e )
        {
            // Traitement factorisé des exceptions d'erreur
            handleException( e, errors, pRequest );
        }
        if ( !errors.isEmpty() )
        {
            // Sauvegarde des messages et transfert vers la page
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "total_failure" );
        }
        // Mise en place du traceur historique
        updateHistTracker( WebMessages.getString( pRequest, name ), "project.do?action=factor&which=" + factorRuleId,
                           TrackerStructure.FACTOR_VIEW, pRequest, false );
        // Indique que l'on vient d'une vue synthèse et pas d'une vue composant
        changeWay( pRequest, "false" );
        // On ne doit pas effacer completement le traceur
        needToReset( pRequest, "false" );
        return forward;
    }

    /**
     * @param pFactorRuleId l'id de la règle
     * @return le facteur correspondant
     * @throws JrafEnterpriseException si erreur
     */
    private FactorRuleDTO getFactorRule( long pFactorRuleId )
        throws JrafEnterpriseException
    {
        // Obtention du facteur
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "QualityGrid" );
        FactorRuleDTO factor = new FactorRuleDTO();
        factor.setId( pFactorRuleId );
        Object[] paramIn = { factor, new Boolean( true ) };
        // Appel de la couche métier
        factor = (FactorRuleDTO) ac.execute( "getQualityRule", paramIn );
        return factor;
    }

    /**
     * Retourne la valeur du facteur
     * 
     * @param pFactor le nom de facteur (clé du tre)
     * @param pAudits le liste des audits
     * @param pProject le projet analysé
     * @param pRequest la requête active.
     * @return un form de résultat contenant la note du facteur
     * @throws Exception si un pb apparaît.
     */
    private ResultForm getFactorResult( final FactorRuleDTO pFactor, final List pAudits, final ComponentDTO pProject,
                                        final HttpServletRequest pRequest )
        throws Exception
    {
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "Results" );
        Object[] paramIn = { pAudits, pProject, new Long( pFactor.getId() ) };
        ResultsDTO resultDTO = ( (ResultsDTO) ac.execute( "getByAudit", paramIn ) );

        // Récupération et tri des audits
        List audits = new ArrayList();
        audits.addAll( (List) resultDTO.getResultMap().get( null ) );
        resultDTO.getResultMap().remove( null );
        AuditComparator auditComp = new AuditComparator();
        audits.remove( null );
        Collections.sort( audits, auditComp );
        Collections.reverse( audits );
        ResultForm factorResult = new ResultForm();
        factorResult.setName( pFactor.getName() );
        factorResult.setId( "" + pFactor.getId() );
        List res = (List) resultDTO.getResultMap().get( pProject );
        Float value = null;
        if ( res.size() > 0 )
        {
            value = (Float) res.get( 0 );
        }
        if ( null != value )
        {
            SqualeWebActionUtils.setValue( factorResult, "setCurrentMark", "" + value.floatValue() );
        }
        if ( audits.size() > 1 )
        {
            Float value2 = (Float) ( ( (List) resultDTO.getResultMap().get( pProject ) ).get( 1 ) );
            factorResult.setPredecessorMark( "" + value2 );
        }
        return factorResult;
    }

    /**
     * Retourne la liste des critères liés au facteur paramètre.
     * 
     * @param pFactorRule le facteur parent
     * @param pAudits la liste des audits
     * @param pProject le projet analysé
     * @return la liste des résultats
     * @throws Exception si un pb apparait
     */
    private ResultListForm getCriteriaList( final FactorRuleDTO pFactorRule, final List pAudits,
                                            final ComponentDTO pProject )
        throws Exception
    {
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "Results" );
        Object[] paramIn = { pProject, new Long( pFactorRule.getId() ), pAudits };
        // Appel de la couche métier
        ResultsDTO resultDTO = ( (ResultsDTO) ac.execute( "getTreChildrenResults", paramIn ) );
        ArrayList names = new ArrayList();
        names.addAll( (Collection) resultDTO.getResultMap().get( null ) );
        resultDTO.getResultMap().remove( null );
        ResultListForm results = new ResultListForm();
        // Récupération et tri des audits
        List audits = new LinkedList();
        audits.addAll( resultDTO.getResultMap().keySet() );
        if ( audits.size() > 0 )
        {
            AuditComparator auditComp = new AuditComparator();
            // Tri des audits selon leur date
            Collections.sort( audits, auditComp );
            // Inversion des audits pour obtenir la date la plus récente d'abord
            Collections.reverse( audits );
            CriteriumForm criteriumForm = null;
            ArrayList criteria = new ArrayList();
            // Parcours de chacun des critères pour le placer dans le formbean
            List res = (List) resultDTO.getResultMap().get( audits.get( 0 ) );
            for ( int i = 0; i < names.size(); i++ )
            {
                criteriumForm = new CriteriumForm();
                criteriumForm.setName( ( (CriteriumRuleDTO) names.get( i ) ).getName() );
                criteriumForm.setId( ( (CriteriumRuleDTO) names.get( i ) ).getId() + "" );
                // Mise du facteur correspondant au critère
                criteriumForm.setTreParent( pFactorRule.getId() + "" );
                // Obtention de la note affectée au critère
                Float value = (Float) ( res.get( i ) );
                if ( null != value && value.floatValue() != -1 )
                {
                    SqualeWebActionUtils.setValue( criteriumForm, "setCurrentMark", "" + value.floatValue() );
                }
                // Calcul de la tendance s'il existe un audit précédent
                if ( audits.size() > 1 )
                {
                    Float value2 = (Float) ( ( (List) resultDTO.getResultMap().get( audits.get( 1 ) ) ).get( i ) );
                    criteriumForm.setPredecessorMark( "" + value2 );
                }
                getCriteriumResult( (CriteriumRuleDTO) names.get( i ), criteriumForm, pAudits, pProject );
                criteria.add( criteriumForm );
            }
            results.setList( criteria );
        }
        return results;
    }

    /**
     * Retourne le détail du critère recherché, avec ses pratiques.
     * 
     * @param pCriteriumRuleDTO critère
     * @param pCriteriumForm le critère à remplir, il doit posséder un nom.
     * @param pAudits la liste des audits.
     * @param pProject le projet analysé.
     * @throws Exception si un pb apparaît.
     */
    private void getCriteriumResult( final CriteriumRuleDTO pCriteriumRuleDTO, final CriteriumForm pCriteriumForm,
                                     final List pAudits, final ComponentDTO pProject )
        throws Exception
    {
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "Results" );
        Object[] paramIn = { pProject, new Long( pCriteriumRuleDTO.getId() ), pAudits };
        ResultsDTO resultDTO = ( (ResultsDTO) ac.execute( "getTreChildrenResults", paramIn ) );
        ArrayList names = new ArrayList();
        names.addAll( (Collection) resultDTO.getResultMap().get( null ) );
        resultDTO.getResultMap().remove( null );
        ArrayList practices = new ArrayList();
        // Récupération et tri des audits
        List audits = new LinkedList();
        audits.addAll( resultDTO.getResultMap().keySet() );
        AuditComparator auditComp = new AuditComparator();
        Collections.sort( audits, auditComp );
        // Inversion de la collection pour obtenir la dernière date en premier
        Collections.reverse( audits );
        ResultForm resultForm = null;
        // Parcours des pratiques
        for ( int i = 0; i < names.size(); i++ )
        {
            resultForm = new ResultForm();
            resultForm.setName( ( (PracticeRuleDTO) names.get( i ) ).getName() );
            resultForm.setId( ( (PracticeRuleDTO) names.get( i ) ).getId() + "" );
            resultForm.setTreParent( pCriteriumForm.getTreParent() );
            // Note associée à la pratique
            Float value = (Float) ( ( (List) resultDTO.getResultMap().get( audits.get( 0 ) ) ).get( i ) );
            if ( null != value && value.floatValue() != -1 )
            {
                // Conversion de la valeur en texte
                SqualeWebActionUtils.setValue( resultForm, "setCurrentMark", "" + value.floatValue() );
            }
            // Calcul de la tendance s'il existe un audit antérieur
            if ( audits.size() > 1 )
            {
                Float value2 = (Float) ( ( (List) resultDTO.getResultMap().get( audits.get( 1 ) ) ).get( i ) );
                resultForm.setPredecessorMark( "" + value2 );
            }
            practices.add( resultForm );
        }
        pCriteriumForm.setPractices( practices );
    }

    /*
     * ======================================================================================================= Page de
     * détails d'une pratique
     * =======================================================================================================
     */

    /**
     * Récupère les détails de la pratique
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward practice( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                   HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionErrors errors = new ActionErrors();
        try
        {
            // On récupère l'indicateur pour le traceur car il va être mis à false
            // dans la méthode commune "getForm" or il se peut que l'on vienne
            // de la page composant.
            String tracker_bool = (String) pRequest.getSession().getAttribute( SqualeWebConstants.TRACKER_BOOL );
            WActionForm form = getForm( pMapping, pRequest, pForm );
            changeWay( pRequest, tracker_bool );
            if ( form instanceof ResultRulesCheckingForm )
            {
                forward = pMapping.findForward( "practiceruleschecking" );
            }
            else
            {
                forward = pMapping.findForward( "practice" );
                ResultForm resForm = (ResultForm) form;
                // rempli les champs à passer de requete en requete
                resForm.copyValues( (RootForm) pForm );
                // le graph
                double[] tab;
                // Si la formule est nulle (ne devrait pas arriver si on arrive ici), pas de graphe
                if ( resForm.getFormulaType() != null )
                {
                    // Dans les 2 cas on affiche le graph en barre
                    RepartitionBarMaker barMaker =
                        new RepartitionBarMaker( pRequest, resForm.getProjectId(), resForm.getCurrentAuditId(),
                                                 resForm.getPreviousAuditId(), resForm.getId(), resForm.getParentId() );
                    barMaker.setValues( resForm.getIntRepartition() );
                    // Sauvegarde de l'image de l'histogramme au format png dans un espace temporaire
                    ChartRenderingInfo infoRepartition = new ChartRenderingInfo( new StandardEntityCollection() );
                    String repartitionFileName =
                        ServletUtilities.saveChartAsPNG( barMaker.getChart(), RepartitionBarMaker.DEFAULT_WIDTH,
                                                         RepartitionBarMaker.DEFAULT_HEIGHT, infoRepartition,
                                                         pRequest.getSession() );
                    GraphMaker repartitionChart = new GraphMaker( pRequest, repartitionFileName, infoRepartition );
                    ( (ProjectSummaryForm) pForm ).setBarGraph( repartitionChart );
                    // Dans ce cas là, on affiche en plus l'histogramme
                    if ( resForm.getFormulaType().equals( AbstractFormulaBO.TYPE_SIMPLE ) )
                    {
                        RepartitionMaker maker =
                            new RepartitionMaker( pRequest, resForm.getProjectId(), resForm.getCurrentAuditId(),
                                                  resForm.getPreviousAuditId(), resForm.getId(), resForm.getParentId() );
                        maker.build( resForm.getFloatRepartition() );
                        // Sauvegarde de l'image de l'histogramme au format png dans un espace temporaire
                        infoRepartition = new ChartRenderingInfo( new StandardEntityCollection() );
                        repartitionFileName =
                            ServletUtilities.saveChartAsPNG( maker.getChart(), RepartitionMaker.DEFAULT_WIDTH,
                                                             RepartitionMaker.DEFAULT_HEIGHT, infoRepartition,
                                                             pRequest.getSession() );
                        repartitionChart = new GraphMaker( pRequest, repartitionFileName, infoRepartition );
                        ( (ProjectSummaryForm) pForm ).setHistoBarGraph( repartitionChart );
                    }
                    else
                    { // on remet l'histogramme à null pour le réinitialiser et ne pas l'afficher
                        ( (ProjectSummaryForm) pForm ).setHistoBarGraph( null );
                    }
                }
            }
            ( (ProjectSummaryForm) pForm ).setResults( form );
        }
        catch ( Exception e )
        {
            // Traitement factorisé des exceptions
            handleException( e, errors, pRequest );
        }
        if ( !errors.isEmpty() )
        {
            // Sauvegarde des messages et renvoi vers la page d'erreur
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * @param pMapping le mapping
     * @param pRequest la requête
     * @param pForm le form
     * @return le formulaire
     * @throws JrafEnterpriseException si erreur
     * @throws NumberFormatException si erreur
     * @throws WTransformerException si erreur
     */
    private WActionForm getForm( ActionMapping pMapping, HttpServletRequest pRequest, ActionForm pForm )
        throws JrafEnterpriseException, NumberFormatException, WTransformerException
    {
        // Pour le tracer aussi
        String param = "";
        Long practiceId = new Long( -1 );
        String practiceName = "";
        String paramParent = null;
        WActionForm result = null;
        // On vérifie les données renvoyées par le formulaire
        if ( pForm == null || null == ( init( pMapping, pRequest, pForm ) ) )
        {
            param = (String) pRequest.getParameter( "which" );
            practiceId = new Long( Long.parseLong( param ) );
            // Obtention de la pratique
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "QualityGrid" );
            PracticeRuleDTO practice = new PracticeRuleDTO();
            practice.setId( practiceId.longValue() );
            Object[] paramIn = { practice, new Boolean( true ) };
            // Appel de la couche métier
            // On récupère la règle avec les métriques utilisées dans la formule
            // pour pouvoir afficher la légende
            Object[] ruleAndTre = (Object[]) ac.execute( "getQualityRuleAndUsedTres", paramIn );
            practice = (PracticeRuleDTO) ruleAndTre[0];
            practiceName = practice.getName();
            paramParent = (String) pRequest.getParameter( "whichParent" );
            FactorRuleDTO factor = null;
            if ( paramParent != null )
            {
                // Création du facteur
                factor = new FactorRuleDTO();
                factor.setId( Long.parseLong( paramParent ) );
                factor = (FactorRuleDTO) ac.execute( "getQualityRule", new Object[] { factor, new Boolean( false ) } );
            }
            List auditsDTO = ActionUtils.getCurrentAuditsAsDTO( pRequest );
            ComponentDTO project =
                (ComponentDTO) WTransformerFactory.formToObj( ProjectTransformer.class,
                                                              ActionUtils.getCurrentProject( pRequest ) )[0];
            // Récupération des données dans le cas d'un rulechecking
            String methodName;
            Class transformerClass;
            // Traitement différent selon le type de la pratique
            if ( practice.isRuleChecking() )
            {
                methodName = "getRuleCheckingResults";
                transformerClass = RulesCheckingResultTransformer.class;
            }
            else
            {
                methodName = "getRepartitionResults";
                transformerClass = PracticeResultTransformer.class;
            }

            // On récupère la description de la règle qualitée
            PracticeInformationForm infoForm = new PracticeInformationForm();
            Object[] triggerAndFormula = new InformationAction().formatFormula( pRequest, practice );
            String trigger = (String) triggerAndFormula[0];
            String elementKey = practice.getHelpKey();
            if ( elementKey != null && elementKey.length() != 0 )
            {
                // récupération des clés pour l'affichage
                String description = WebMessages.getString( pRequest, elementKey + ".description" );
                String correction = WebMessages.getString( pRequest, elementKey + ".correction" );
                infoForm.setTrigger( trigger );
                // Suivant le type de la formule, on a récupéré une formule simple
                // ou une formule avec des conditions
                Object formula = triggerAndFormula[1];
                if ( formula instanceof String )
                {
                    infoForm.setFormula( (String) formula );
                }
                if ( formula instanceof String[] )
                {
                    infoForm.setFormulaCondition( (String[]) formula );
                }
                // Affectation
                infoForm.setDescription( description );
                infoForm.setCorrection( correction );
                infoForm.setName( WebMessages.getString( pRequest, elementKey ) );
                infoForm.setUsedTres( (Collection) ruleAndTre[1] );

            }

            // Récupération de la note et de l'évolution de la pratique
            ac = AccessDelegateHelper.getInstance( "Results" );
            auditsDTO.remove( null );
            paramIn = new Object[] { auditsDTO, project, practice };
            ResultsDTO resultDTO = ( (ResultsDTO) ac.execute( methodName, paramIn ) );
            resultDTO.getResultMap().remove( null );
            // Récupération et tri des audits
            List audits = new ArrayList();
            audits.addAll( resultDTO.getIntRepartitionPracticeMap().keySet() );
            AuditComparator auditComp = new AuditComparator();
            Collections.sort( audits, auditComp );
            // Inversion de la collection pour avoir le dernier audit en tête de liste
            Collections.reverse( audits );
            // Transformation en formulaire
            result =
                WTransformerFactory.objToForm( transformerClass, new Object[] { resultDTO, factor, practice, project,
                    audits, infoForm, pRequest.getLocale() } );
            manageTrackerFromPractice( pRequest, paramParent, practiceName, practiceId );
        }
        return result;
    }

    /**
     * @param pRequest la requete
     * @param pParamParent le parent (peut etre null)
     * @param pPracticeName le nom de la pratique
     * @param pPracticeId l'id de la pratique
     */
    private void manageTrackerFromPractice( HttpServletRequest pRequest, String pParamParent, String pPracticeName,
                                            Long pPracticeId )
    {
        // Mise en place du traceur historique
        String displayName = WebMessages.getString( pRequest, pPracticeName );
        String url = "project.do?action=practice&which=" + pPracticeId.longValue();
        if ( null != pParamParent )
        {
            url += "&whichParent=" + pParamParent;
        }
        updateHistTracker( displayName, url, TrackerStructure.FACTOR_VIEW, pRequest, false );
        // Indique que l'on vient d'une vue synthèse et pas d'une vue composant
        changeWay( pRequest, "false" );
        // On ne doit pas effacer completement le traceur
        needToReset( pRequest, "false" );

    }

    /*
     * ======================================================================================================= Page de
     * détails d'une transgression
     * =======================================================================================================
     */

    /**
     * récupère les détails d'une transgression.
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward ruleCheckingDetails( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                              HttpServletResponse pResponse )
    {
        // Initialisation des variables :
        ActionForward forward = null;
        ActionErrors errors = new ActionErrors();
        String rule = "";
        long measureId = -1;
        long ruleId = -1;
        try
        {
            ProjectSummaryForm projectSummaryForm = (ProjectSummaryForm) pForm;
            // Obtention de la pratique
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Results" );
            String ruleName = (String) pRequest.getParameter( "ruleName" );
            String measure = (String) pRequest.getParameter( "measureId" );
            rule = (String) pRequest.getParameter( "ruleId" );
            measureId = Long.parseLong( measure );
            ruleId = Long.parseLong( rule );
            Object[] paramIn = { new Long( measureId ), new Long( ruleId ) };
            Collection details = ( (Collection) ac.execute( "getRuleCheckingItemResults", paramIn ) );
            RuleCheckingItemsListForm itemsForm =
                (RuleCheckingItemsListForm) WTransformerFactory.objToForm( RuleCheckingItemsListTransformer.class,
                                                                           new Object[] { ruleName, details } );
            pRequest.getSession().setAttribute( SqualeWebConstants.ITEMS_KEY, itemsForm );
            pRequest.getSession().setAttribute( "practiceName", pRequest.getParameter( "practiceName" ) );
            forward = pMapping.findForward( "practicerulescheckingitem" );
        }
        catch ( Exception e )
        {
            // Traitement factorisé des exceptions
            handleException( e, errors, pRequest );
        }
        if ( !errors.isEmpty() )
        {
            // Enregistrement des messages et routage vers une page d'erreur
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "total_failure" );
        }
        // Mise en place du traceur historique
        String trackerKey = "project.result.practice.rulesChecking.rule.item.intro.tracker";
        String linkName = WebMessages.getString( pRequest, trackerKey );
        String url =
            "project.do?action=ruleCheckingDetails&measureId=" + measureId + "&ruleId=" + ruleId + "&ruleName=" + rule;
        updateHistTracker( linkName, url, TrackerStructure.FACTOR_VIEW, pRequest, false );
        // Indique que l'on vient d'une vue synthèse et pas d'une vue composant
        changeWay( pRequest, "false" );
        // On ne doit pas effacer completement le traceur
        needToReset( pRequest, "false" );
        return forward;
    }

    /*
     * ======================================================================================================= Page
     * d'affichage des composants ayant une note précise sur une pratique
     * =======================================================================================================
     */

    /**
     * Récupère les détails de la pratique
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward mark( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                               HttpServletResponse pResponse )
    {

        ActionForward forward = null;
        ActionErrors errors = new ActionErrors();
        // sert aussi pour le traceur
        String value = "", minValue = "", maxValue = "", factorParent = "", param = "", note = "", markParamForTracker =
            "";
        try
        {
            forward = init( pMapping, pRequest, pForm );
            if ( null == forward )
            {
                param = (String) pRequest.getParameter( "tre" );
                factorParent = (String) pRequest.getParameter( "factorParent" );
                IApplicationComponent acGrid = AccessDelegateHelper.getInstance( "QualityGrid" );
                // Obtention de la pratique
                long practiceId = Long.parseLong( (String) pRequest.getParameter( "tre" ) );
                PracticeRuleDTO practice = new PracticeRuleDTO();
                practice.setId( practiceId );
                practice =
                    (PracticeRuleDTO) acGrid.execute( "getQualityRule", new Object[] { practice, new Boolean( false ) } );
                FactorRuleDTO factor = new FactorRuleDTO();
                if ( factorParent != null )
                {
                    // Obtention du facteur
                    long factorId = Long.parseLong( factorParent );
                    factor.setId( factorId );
                    factor =
                        (FactorRuleDTO) acGrid.execute( "getQualityRule", new Object[] { factor, new Boolean( false ) } );
                }
                else
                {
                    factor.setId( -1 );
                    factor.setName( "" );
                }
                // Préparation de l'appel des couches métier
                IApplicationComponent ac = AccessDelegateHelper.getInstance( "Results" );

                AuditDTO audit = (AuditDTO) ( ActionUtils.getCurrentAuditsAsDTO( pRequest ).get( 0 ) );
                ComponentDTO project =
                    (ComponentDTO) pRequest.getSession().getAttribute( BaseDispatchAction.PROJECT_DTO );

                ResultsDTO result = null;
                value = (String) pRequest.getParameter( "currentMark" );
                // Suivant le type de paramètre passé, on peut savoir si on vient du graph ou si
                // on vient du tableau de répartition
                if ( value != null )
                { // Clic sur le tableau de répartition
                    // Note entière à ajouter à l'url du traceur
                    markParamForTracker = "&currentMark=" + value;
                    Integer intValue = new Integer( value );
                    // Un nombre maximal de résultats est retrouvé
                    Object[] paramIn =
                        { audit, project, new Long( practiceId ), intValue,
                            new Integer( WebMessages.getString( Locale.getDefault(), "component.max" ) ) };
                    // Appel de la couche métier
                    result = (ResultsDTO) ac.execute( "getValueResults", paramIn );
                    // Transformation vers le formulaire
                    WTransformerFactory.objToForm( MarkTransformer.class, (WActionForm) pForm, new Object[] { result,
                        factor, practice, intValue } );
                    note = WebMessages.getString( pRequest, "project.results.mark.status_" + value );
                }
                else
                { // clic sur le graph
                    minValue = (String) pRequest.getParameter( "minMark" );
                    maxValue = (String) pRequest.getParameter( "maxMark" );
                    // Intervalle à ajouter à l'url du traceur
                    markParamForTracker = "&minMark=" + minValue + "&maxMark=" + maxValue;
                    // Conversion en double
                    Double minMark = new Double( minValue );
                    Double maxMark = new Double( maxValue );
                    // Un nombre maximal de résultats est retrouvé
                    Object[] paramIn =
                        { audit, project, new Long( practiceId ), minMark, maxMark,
                            new Integer( WebMessages.getString( Locale.getDefault(), "component.max" ) ) };
                    // Appel de la couche métier
                    result = (ResultsDTO) ac.execute( "getValueResultsForInterval", paramIn );
                    // Transformation vers le formulaire
                    WTransformerFactory.objToForm( MarkTransformer.class, (WActionForm) pForm, new Object[] { result,
                        factor, practice, minMark, maxMark } );
                    note = WebMessages.getString( pRequest, "project.results.mark" );
                    // pour le traceur
                    note += "[" + minMark.doubleValue() + "," + maxMark.doubleValue();
                    // Suivant la note, on l'inclus ou pas la dans l'intervalle
                    // on ne l'inclus que si c'est 3
                    if ( maxMark.intValue() != PracticeResultBO.EXCELLENT )
                    {
                        note += "[";
                    }
                    else
                    {
                        note += "]";
                    }
                }
                forward = pMapping.findForward( "mark" );
            }
        }
        catch ( Exception e )
        {
            // Traitement factorisé des exceptions
            handleException( e, errors, pRequest );
        }
        if ( !errors.isEmpty() )
        {
            // Enregistrement des messages et routage vers une page d'erreur
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "total_failure" );
        }
        // Mise en place du traceur historique
        String url = "mark.do?action=mark&tre=" + param + markParamForTracker;
        if ( factorParent != null )
        {
            url += "&factorparent=" + factorParent;
        }

        updateHistTracker( note, url, TrackerStructure.FACTOR_VIEW, pRequest, false );
        // Indique que l'on vient d'une vue synthèse et pas d'une vue composant
        changeWay( pRequest, "false" );
        // ICI on doit dire qu'il faudra effacer completement le traceur
        // après le passage sur le prochain composant
        needToReset( pRequest, "true" );
        // *****************************************************************
        return forward;
    }

    /**
     * Initialise l'action. Si une opération est à effectuer parce que les données de session ne permettent pas de
     * poursuivre l'action, alors un forward non null est renvoyé.
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire
     * @param pRequest la requête HTTP.
     * @return l'action à réaliser.
     * @throws JrafEnterpriseException exception.
     * @throws WTransformerException exception de transformation.
     */
    private ActionForward init( ActionMapping pMapping, HttpServletRequest pRequest, ActionForm pForm )
        throws JrafEnterpriseException, WTransformerException
    {
        ActionForward forward = null;
        ProjectForm project = null;
        Long projectId = null;
        // Obtention de l'id de projet
        String param = (String) pRequest.getAttribute( "projectId" );
        if ( null == param )
        {
            param = (String) pRequest.getParameter( "projectId" );
        }
        if ( param != null )
        {
            projectId = new Long( param );
        }
        // Récupération du projet depuis la session
        ComponentDTO projectDto = (ComponentDTO) pRequest.getSession().getAttribute( BaseDispatchAction.PROJECT_DTO );
        project = (ProjectForm) WTransformerFactory.objToForm( ProjectTransformer.class, new Object[] { projectDto } );
        // On vérifie si le projet choisi est différent de celui connu en session
        if ( ( project == null ) || ( ( projectId != null ) && ( projectId.longValue() != project.getId() ) ) )
        {
            project = setupProject( projectId, pRequest, pForm );
        }
        if ( null == project )
        {
            // l'id n'est pas valide
            ActionError error = new ActionError( "error.invalid_project" );
            ActionErrors errors = new ActionErrors();
            errors.add( "application", error );
            saveErrors( pRequest, errors );
            forward = pMapping.findForward( "failure" );
        }
        return forward;
    }

    /**
     * Mise en place de la session pour le projet Les données concernant le projet sont placées en session, à savoir le
     * formulaire du projet ainsi que le contenu du menu "Top"
     * 
     * @param pProjectId id du projet
     * @param pRequest requête
     * @param pForm le form associé à l'action
     * @return form du projet ou null si erreur
     * @throws JrafEnterpriseException si erreur
     * @throws WTransformerException si erreur
     */
    ProjectForm setupProject( Long pProjectId, HttpServletRequest pRequest, ActionForm pForm )
        throws JrafEnterpriseException, WTransformerException
    {
        ProjectForm project = null;
        // Récupération de l'id de projet
        ComponentDTO dto = new ComponentDTO();
        dto.setID( pProjectId.longValue() );
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "Component" );
        Object[] paramIn = { dto };
        // Obtention du projet par la couche métier
        dto = (ComponentDTO) ac.execute( "get", paramIn );
        if ( null != dto )
        {
            Object obj[] = { dto };
            project = (ProjectForm) WTransformerFactory.objToForm( ProjectTransformer.class, obj );
            ApplicationForm currentApplication = ActionUtils.getCurrentApplication( pRequest );
            // Mise à jour de l'application courante si besoin
            if ( null == currentApplication || currentApplication.getId() != dto.getIDParent() )
            {
                ApplicationForm application =
                    ActionUtils.getComponent( dto.getIDParent(), getUserApplicationList( pRequest ) );
            }
            Object paramToTransform[] = { dto };
            // Création du form pour afficher le composant
            ComponentForm projectCompForm =
                (ComponentForm) WTransformerFactory.objToForm( ComponentTransformer.class, paramToTransform );
            // on met à jour les valeurs concernant l'application et le projet
            // du componentForm qui va etre passé dans la requete
            projectCompForm.copyValues( (ProjectSummaryForm) pForm );
            pRequest.setAttribute( "componentForm", projectCompForm );
        }
        return project;
    }

    /**
     * Permet de récupérer le dernier audit dans la session ou le charger par les AC
     * 
     * @param pRequest requete HTTP
     * @param pComponent ComponentDTO dont on souhaite les audits
     * @return Liste des derniers AuditDTOs
     * @throws JrafEnterpriseException si erreur
     * @throws WTransformerException si erreur
     */
    private List initAudit( HttpServletRequest pRequest, ComponentDTO pComponent )
        throws WTransformerException, JrafEnterpriseException
    {

        List auditsList = new LinkedList();

        // récupère les audits dans la session
        // renvoie null
        List auditsDTO = ActionUtils.getCurrentAuditsAsDTO( pRequest );

        if ( null != auditsDTO )
        {
            auditsList.addAll( auditsDTO.subList( 0, Math.min( auditsDTO.size(), 2 ) ) );
        }
        else
        {
            Object[] paramIn2 = { pComponent, null, null, new Integer( AuditBO.TERMINATED ) };
            IApplicationComponent ac2 = AccessDelegateHelper.getInstance( "Component" );
            auditsList = (List) ac2.execute( "getLastAllAudits", paramIn2 );
        }
        return auditsList;
    }

    /**
     * Exporte le plan d'action détaillé pour le projet
     * 
     * @param mapping le actionMapping
     * @param form le form
     * @param request la request
     * @param response la response
     * @return l'actionForward
     * @throws ServletException exception pouvant etre levee
     */
    public ActionForward exportPDFDetailedActionPlan( ActionMapping mapping, ActionForm form,
                                                      HttpServletRequest request, HttpServletResponse response )
        throws ServletException
    {
        /*
         * 1. On récupère les plus mauvaises pratiques triées par l'effort à fournir pour progresser 2. Pour chaque
         * pratique, on remonte les pires composants (ou transgressions) dans la limite de 100
         */
        Collection badPractices;
        // On récupère l'id du projet et de l'audit
        RootForm rootForm = (RootForm) form;
        String projectId = rootForm.getProjectId();
        String auditId = rootForm.getCurrentAuditId();

        try
        {
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Results" );
            badPractices =
                (Collection) ac.execute( "getWorstPractices", new Object[] { auditId, projectId, new Boolean( true ) } );

            HashMap parameters = new HashMap();
            // Le nom de l'utilisateur
            LogonBean logon = (LogonBean) request.getSession().getAttribute( WConstants.USER_KEY );
            parameters.put( "userName", logon.getMatricule() );
            // La date de l'audit courant
            parameters.put( "auditDate", rootForm.getAuditName() );
            PDFDataJasperReports data =
                new PDFDataJasperReports( request.getLocale(), getResources( request ), badPractices,
                                          "/com/airfrance/squaleweb/resources/jasperreport/ActionPlan.jasper", false,
                                          parameters );
            PDFFactory.generatePDFToHTTPResponse( data, response, "", PDFEngine.JASPERREPORTS );
        }
        catch ( Exception e )
        {
            throw new ServletException( e );
        }
        return null;
    }

    /**
     * Export IDE au format XML. L'export comportement les plus mauvaises pratiques avec les 100 plus mauvais composants
     * (même principe que pour le plan d'action)
     * 
     * @param mapping le mapping
     * @param form le formulaire
     * @param request la requête
     * @param response la réponse
     * @return null car aucune action doit âtre faite
     * @throws ServletException si erreur
     */
    public ActionForward exportIDE( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response )
        throws ServletException
    {
        ActionForward forward = null;
        Collection badPractices;
        // On récupère l'id du projet et de l'audit
        RootForm rootForm = (RootForm) form;
        String projectId = rootForm.getProjectId();
        String auditId = rootForm.getCurrentAuditId();

        try
        {
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Results" );
            badPractices =
                (Collection) ac.execute( "getWorstPractices", new Object[] { auditId, projectId, new Boolean( true ) } );
            ac = AccessDelegateHelper.getInstance( "Component" );
            String workspace =
                (String) ac.execute( "getProjectWorkspace", new Object[] { new Long( Long.parseLong( projectId ) ) } );
            XMLData xmlData =
                new IDELinkXMLData( request, rootForm.getAuditDate(), rootForm.getApplicationName(), workspace,
                                    badPractices );
            // On récupère le label de l'audit si il s'agit d'un audit de jalon
            if ( rootForm.getAuditName().matches( ".+\\(.+\\)" ) )
            { // le label se trouve avant la première parenthèse
                ( (IDELinkXMLData) xmlData ).setLabel( rootForm.getAuditName().replaceFirst( "\\(.+\\)", "" ).trim() );
            }
            String fileName = "actionPlan_" + rootForm.getProjectName() + ".sqa";
            XMLFactory.generateXMLtoHTTPResponse( xmlData, response, fileName );
        }
        catch ( Exception ioe )
        {
            throw new ServletException( ioe );
        }
        return forward;
    }

    /**
     * Exporte le plan d'action synthétique pour le projet
     * 
     * @param mapping le actionMapping
     * @param form le form
     * @param request la request
     * @param response la response
     * @return l'actionForward
     * @throws ServletException exception pouvant etre levee
     */
    public ActionForward exportPDFActionPlan( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                              HttpServletResponse response )
        throws ServletException
    {
        /*
         * 1. On récupère les plus mauvaises pratiques triées par l'effort à fournir pour progresser 2. Pour chaque
         * pratique, on remonte le nombre des pires composants (ou transgressions)
         */
        Collection badPractices;
        // On récupère l'id du projet et de l'audit
        ProjectSummaryForm theForm = (ProjectSummaryForm) form;
        String projectId = theForm.getProjectId();
        String auditId = theForm.getCurrentAuditId();

        try
        {
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Results" );
            badPractices =
                (Collection) ac.execute( "getWorstPractices", new Object[] { auditId, projectId, new Boolean( false ) } );

            HashMap parameters = new HashMap();
            // La date de l'audit courant
            parameters.put( "auditDate", theForm.getAuditName() );
            // La date de l'audit précédent
            parameters.put( "previousAuditDate", theForm.getPreviousAuditName() );
            // la volumétrie
            parameters.put( "volumetry", theForm.getVolumetry().getList() );
            // Les facteurs
            parameters.put( "factors", theForm.getFactors().getFactors() );
            // Le nom de l'utilisateur
            LogonBean logon = (LogonBean) request.getSession().getAttribute( WConstants.USER_KEY );
            parameters.put( "userName", logon.getMatricule() );
            PDFDataJasperReports data =
                new PDFDataJasperReports( request.getLocale(), getResources( request ), badPractices,
                                          "/com/airfrance/squaleweb/resources/jasperreport/ActionPlan.jasper", false,
                                          parameters );
            PDFFactory.generatePDFToHTTPResponse( data, response, "", PDFEngine.JASPERREPORTS );
        }
        catch ( Exception e )
        {
            throw new ServletException( e );
        }
        return null;
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
        Collection data = new ArrayList( 1 );
        ProjectSummaryForm theForm = (ProjectSummaryForm) form;
        data.add( theForm );
        try
        {
            HashMap parameters = new HashMap();
            // L'image du kiviat
            parameters.put( "kiviat", (java.awt.Image) request.getSession().getAttribute( "kiviatChart" ) );
            // L'image du scatterplot
            JFreeChart bubbleChart = new TopAction().getBubbleChart( request );
            parameters.put( "scatterplot", bubbleChart.createBufferedImage( BubbleMaker.DEFAULT_WIDTH,
                                                                            BubbleMaker.DEFAULT_HEIGHT ) );
            // Le nom de l'utilisateur
            LogonBean logon = (LogonBean) request.getSession().getAttribute( WConstants.USER_KEY );
            parameters.put( "userName", logon.getMatricule() );
            // Pour chaque facteur, on récupère la liste des critères et pratiques associés
            Collection factors = theForm.getFactors().getFactors();
            // Obtention des audits courants
            List auditsDTO = ActionUtils.getCurrentAuditsAsDTO( request );
            ComponentDTO project = new ComponentDTO();
            project.setID( Long.parseLong( theForm.getProjectId() ) );
            HashMap factorsResults = new HashMap();
            Map practicesResults = new HashMap();
            for ( Iterator it = factors.iterator(); it.hasNext(); )
            {
                ProjectFactorForm factor = (ProjectFactorForm) it.next();
                FactorRuleDTO factorRule = getFactorRule( factor.getId() );
                ResultListForm criteria = getCriteriaList( factorRule, auditsDTO, project );
                factorsResults.put( new Long( factor.getId() ), criteria.getList() );
            }
            // Les critères associés aux facteurs
            parameters.put( "factorsResults", factorsResults );
            // Les pratiques associées aux critères
            parameters.put( "practicesResults", practicesResults.values() );
            PDFDataJasperReports pdfData =
                new PDFDataJasperReports( request.getLocale(), getResources( request ), data,
                                          "/com/airfrance/squaleweb/resources/jasperreport/ProjectSummary.jasper",
                                          false, parameters );
            PDFFactory.generatePDFToHTTPResponse( pdfData, response, "", PDFEngine.JASPERREPORTS );
        }
        catch ( Exception e )
        {
            throw new ServletException( e );
        }
        return null;
    }

    /**
     * Exporte la liste des composants par note de pratiques au format XLS
     * 
     * @param mapping le actionMapping
     * @param form le form
     * @param request la request
     * @param response la response
     * @return l'actionForward
     * @throws ServletException exception pouvant etre levee
     */
    public ActionForward exportMarkToExcel( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                            HttpServletResponse response )
        throws ServletException
    {
        // Le nom de l'utilisateur
        LogonBean logon = (LogonBean) request.getSession().getAttribute( WConstants.USER_KEY );
        try
        {
            ExcelDataMarkList data =
                new ExcelDataMarkList( request.getLocale(), getResources( request ), (MarkForm) form,
                                       logon.getMatricule() );
            ExcelFactory.generateExcelToHTTPResponse( data, response, "Marks.xls" );
        }
        catch ( Exception e )
        {
            throw new ServletException( e );
        }
        return null;
    }

    /**
     * Exporte la liste des composants par note de pratiques au format PDF
     * 
     * @param mapping le actionMapping
     * @param form le form
     * @param request la request
     * @param response la response
     * @return l'actionForward
     * @throws ServletException exception pouvant etre levee
     */
    public ActionForward exportMarkToPDF( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response )
        throws ServletException
    {
        MarkForm theForm = (MarkForm) form;
        Collection data = theForm.getComponents();
        try
        {
            HashMap parameters = new HashMap();
            PDFDataJasperReports pdfData =
                new PDFDataJasperReports( request.getLocale(), getResources( request ), data,
                                          "/com/airfrance/squaleweb/resources/jasperreport/metricsResults.jasper",
                                          false, parameters );

            // Le nom des métriques
            parameters.put( "treNames", theForm.getTreNames() );
            // Le nom de la pratique
            String practiceName = WebMessages.getString( request, theForm.getPracticeName() );
            parameters.put( "practiceName", practiceName );
            // Le min de l'intervalle
            String minMarkMessage = "";
            if ( theForm.getMinMark() != theForm.getMaxMark() )
            {
                minMarkMessage = "" + theForm.getMinMark();
            }
            else
            {
                minMarkMessage =
                    WebMessages.getString( request, "project.results.mark.status_" + theForm.getMarkValue() );
            }
            parameters.put( "minMarkMessage", minMarkMessage );
            // Le nom de l'utilisateur
            LogonBean logon = (LogonBean) request.getSession().getAttribute( WConstants.USER_KEY );
            parameters.put( "userName", logon.getMatricule() );
            // Le nom de l'application
            parameters.put( "applicationName", theForm.getApplicationName() );
            // Le nom du projet
            parameters.put( "projectName", theForm.getProjectName() );
            // La date de l'audit (ou le nom dans le cas d'un jalon)
            parameters.put( "auditDate", theForm.getAuditName() );
            // La date de l'audit précédent (ou le nom dans le cas d'un jalon)
            parameters.put( "previousAuditDate", theForm.getPreviousAuditName() );
            PDFFactory.generatePDFToHTTPResponse( pdfData, response, "", PDFEngine.JASPERREPORTS );
        }
        catch ( Exception e )
        {
            throw new ServletException( e );
        }
        return null;
    }

    /**
     * Exporte la liste des transgressions (100 par type au maximum) au format PDF
     * 
     * @param mapping le actionMapping
     * @param form le form
     * @param request la request
     * @param response la response
     * @return l'actionForward
     * @throws ServletException exception pouvant etre levee
     */
    public ActionForward exportTransgressionsToPDF( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                    HttpServletResponse response )
        throws ServletException
    {
        ProjectSummaryForm theForm = (ProjectSummaryForm) form;
        try
        {
            // On récupère les RuleCheckingForm
            Collection rules = ( (ResultRulesCheckingForm) theForm.getResults() ).getList();
            // Pour chaque règle, si il y a eu des transgressions, on les remonte et on les stocke
            // pour les données PDF
            Collection data = fillTransgressions( rules );

            // Les paramètres
            HashMap parameters = new HashMap();
            // Le nom de la pratique
            parameters.put( "practiceName",
                            WebMessages.getString( request,
                                                   ( (ResultRulesCheckingForm) theForm.getResults() ).getName() ) );
            // Note de la pratique
            parameters.put( "currentMark", ( (ResultRulesCheckingForm) theForm.getResults() ).getCurrentMark() );
            // Le nom de l'utilisateur
            LogonBean logon = (LogonBean) request.getSession().getAttribute( WConstants.USER_KEY );
            parameters.put( "userName", logon.getMatricule() );
            // Le nom de l'application
            parameters.put( "applicationName", theForm.getApplicationName() );
            // Le nom du projet
            parameters.put( "projectName", theForm.getProjectName() );
            // La date de l'audit
            parameters.put( "auditDate", theForm.getAuditName() );

            PDFDataJasperReports pdfData =
                new PDFDataJasperReports( request.getLocale(), getResources( request ), data,
                                          "/com/airfrance/squaleweb/resources/jasperreport/transgressions.jasper",
                                          false, parameters );
            PDFFactory.generatePDFToHTTPResponse( pdfData, response, "", PDFEngine.JASPERREPORTS );
        }
        catch ( Exception e )
        {
            throw new ServletException( e );
        }
        return null;
    }

    /**
     * Construit l'ensemble des règles transgressés avec leurs détails pour l'export PDF
     * 
     * @param pRulesCheckingForm l'ensemble des règles
     * @return les règles transgressées et leurs détails
     * @throws JrafEnterpriseException si erreur JRAF
     * @throws WTransformerException si erreur de transformation
     */
    private Collection fillTransgressions( Collection pRulesCheckingForm )
        throws JrafEnterpriseException, WTransformerException
    {
        // Initialisation
        Collection data = new ArrayList();
        Collection details = null;
        RuleCheckingItemsListForm itemsForm = null;
        RuleCheckingPDFForm formForPDF = null;
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "Results" );
        Object[] paramIn = new Object[2];
        for ( Iterator it = pRulesCheckingForm.iterator(); it.hasNext(); )
        {
            RulesCheckingForm current = (RulesCheckingForm) it.next();
            if ( current.getTransgressionsNumber() > 0 )
            {
                paramIn[0] = new Long( current.getMeasureID() );
                paramIn[1] = new Long( current.getId() );
                details = ( (Collection) ac.execute( "getRuleCheckingItemResults", paramIn ) );
                itemsForm =
                    (RuleCheckingItemsListForm) WTransformerFactory.objToForm( RuleCheckingItemsListTransformer.class,
                                                                               new Object[] { current.getNameRule(),
                                                                                   details } );
                formForPDF =
                    new RuleCheckingPDFForm( current.getNameRule(), current.getSeverity(),
                                             current.getTransgressionsNumber(), itemsForm );
                data.add( formForPDF );
            }
        }
        return data;
    }
}
