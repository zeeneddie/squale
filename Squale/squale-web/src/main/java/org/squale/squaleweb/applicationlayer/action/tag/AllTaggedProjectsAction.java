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
package org.squale.squaleweb.applicationlayer.action.tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.AccessDelegateHelper;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.squalecommon.daolayer.tag.TagDAOImpl;
import org.squale.squalecommon.datatransfertobject.component.AuditDTO;
import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;
import org.squale.squalecommon.datatransfertobject.result.ResultsDTO;
import org.squale.squalecommon.datatransfertobject.tag.TagDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squaleweb.applicationlayer.action.ActionUtils;
import org.squale.squaleweb.applicationlayer.action.accessRights.DefaultAction;
import org.squale.squaleweb.applicationlayer.formbean.component.ApplicationListForm;
import org.squale.squaleweb.applicationlayer.formbean.component.ProjectListForm;
import org.squale.squaleweb.applicationlayer.formbean.component.SplitAuditsListForm;
import org.squale.squaleweb.applicationlayer.formbean.results.ProjectSummaryForm;
import org.squale.squaleweb.comparator.AuditGridComparator;
import org.squale.squaleweb.transformer.ApplicationListTransformer;
import org.squale.squaleweb.transformer.FactorsResultListTransformer;
import org.squale.squaleweb.transformer.ProjectSummaryTransformer;
import org.squale.squaleweb.transformer.stats.FactorsStatsTransformer;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WTransformerException;
import org.squale.welcom.struts.transformer.WTransformerFactory;

/**
 * Affichage de la liste des applications Cette action permet d'extraire la liste des applications disponibles pour
 * l'utilisateur courant et de les restituer sous une forme synthétique
 */
public class AllTaggedProjectsAction
    extends DefaultAction
{

    /**
     * Le nom désignant l'attribut en session pour indiquer si on veut tous les facteurs ou non pour le kiviat.
     */
    private static final String ALL_FACTORS = "allFactors";

    /**
     * Affichage de la liste des applications non publiques
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward listSameTagProjects( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                              HttpServletResponse pResponse )
    {

        ActionForward forward;
        try
        {
            // Récupération du tag en session voulu
            String tagId = (String) pRequest.getParameter( "tag" );

            Object[] paramIn = new Object[] { Long.parseLong( tagId )};
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "TagAdmin" );
            // Récupération des tags pour chacun des applications concernées
            TagDTO tag = ( (TagDTO) ac.execute( "getTag", paramIn ) );
            pRequest.setAttribute( "tagName", tag.getName() );
            paramIn[0] = new TagDTO[] { tag };
            IApplicationComponent ac2 = AccessDelegateHelper.getInstance( "Component" );
            List projects = ( (List) ac2.execute( "getTaggedProjects", paramIn ) );

            forward = execute( pMapping, pForm, projects, pRequest );
        }
        catch ( Exception e )
        {
            ActionErrors errors = new ActionErrors();
            // Traitement factorisé des erreurs
            handleException( e, errors, pRequest );
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "failure" );
        }
        // On est passé par un menu donc on réinitialise le traceur
        // resetTracker( pRequest );
        return ( forward );
    }

    /**
     * @param pMapping le mapping
     * @param pForm le formulaire
     * @param pProjects les applications
     * @return le forward
     * @throws WTransformerException si erreur
     * @throws JrafEnterpriseException si erreur
     */
    private ActionForward execute( ActionMapping pMapping, ActionForm pForm, List pProjects, HttpServletRequest pRequest )
        throws WTransformerException, JrafEnterpriseException, Exception
    {
        ActionForward forward;
        List projectList = new ArrayList();
        if ( pProjects.isEmpty() )
        {
            // S'il n'y a pas d'applications, on redirige vers une page dédiée
            forward = pMapping.findForward( "empty" );
        }
        else
        {
            List results = new LinkedList();
            // Récupération des résultats à partir de l'Application Component
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Results" );
            List projectResults = new ArrayList();
            for ( Object object : pProjects )
            {
                // Object[] paramIn = { null, project };
                // // Récupération des résultats pour chacun des applications concernées
                // projectResults.add( (ResultsDTO) ac.execute( "getProjectResults", paramIn ) );
                // }

                ComponentDTO project = (ComponentDTO) object;

                ProjectSummaryForm projectSummaryForm = new ProjectSummaryForm();

                projectSummaryForm.setProjectName( project.getName() );
                projectSummaryForm.setProjectId( "" + project.getID() );

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
                    projectSummaryForm.setCurrentAuditId( "" + currentAuditId );
                    Long previousAuditId;
                    if ( auditsList.size() > 1 )
                    {
                        previousAuditId = new Long( ( (AuditDTO) auditsList.get( 1 ) ).getID() );
                    }
                    else
                    {
                        previousAuditId = null;
                    }

                    projectSummaryForm.setPreviousAuditId( "" + ( previousAuditId == null ? "" : previousAuditId ) );
                    Object[] paramIn = { currentAuditId, project };
                    IApplicationComponent ac2 = AccessDelegateHelper.getInstance( "Results" );
                    ResultsDTO resultDTO = ( (ResultsDTO) ac2.execute( "getProjectVolumetry", paramIn ) );
                    Map volumetries = resultDTO.getResultMap();
                    Object[] paramIn2 = { currentAuditId, new Long( project.getID() ) };
                    Boolean haveErrors = ( (Boolean) ac2.execute( "getHaveErrors", paramIn2 ) );
                    ac2 = AccessDelegateHelper.getInstance( "Component" );
                    Boolean canBeExportedToIDE =
                        ( (Boolean) ac2.execute( "canBeExportedToIDE", new Object[] { new Long( project.getID() ) } ) );

                    // Conversion du formulaire
                    Object[] params = { project, factors, volumetries, haveErrors, canBeExportedToIDE };
                    WTransformerFactory.objToForm( ProjectSummaryTransformer.class, projectSummaryForm, params );
                }
                projectList.add( projectSummaryForm );
            }
            ( (ProjectListForm) pForm ).setList( projectList );
            forward = pMapping.findForward( "list" );
        }
        return forward;
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

}
