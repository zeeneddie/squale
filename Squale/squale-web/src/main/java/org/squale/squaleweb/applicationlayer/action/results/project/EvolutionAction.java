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
package org.squale.squaleweb.applicationlayer.action.results.project;

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
import org.apache.struts.action.ActionMessages;

import org.squale.jraf.helper.AccessDelegateHelper;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.squalecommon.datatransfertobject.component.AuditDTO;
import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;
import org.squale.squaleweb.applicationlayer.action.ActionUtils;
import org.squale.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction;
import org.squale.squaleweb.applicationlayer.action.accessRights.ReaderAction;
import org.squale.squaleweb.applicationlayer.formbean.LogonBean;
import org.squale.squaleweb.applicationlayer.formbean.results.EvolutionForm;
import org.squale.squaleweb.transformer.EvolutionTransformer;
import org.squale.welcom.outils.pdf.PDFDataJasperReports;
import org.squale.welcom.outils.pdf.PDFEngine;
import org.squale.welcom.outils.pdf.PDFFactory;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WTransformerFactory;
import org.squale.welcom.struts.util.WConstants;

/**
 * Action pour la ncomparaison détaillée de deux audits
 */
public class EvolutionAction
    extends ReaderAction
{

    /** le nombre max de résultats à récupérer */
    public static final int MAX_RESULTS = 300;

    /**
     * Nom de l'attibut en session pour indiquer à l'utilisateur que les résultats sont limités
     */
    public static final String DISPLAY_RESULTS_MSG = "displayLimitMsg";

    /**
     * Filtre les pratiques et les composants
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward list( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                               HttpServletResponse pResponse )
    {

        ActionForward forward = null;
        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        try
        {
            forward = pMapping.findForward( "success" );
            // On récupère les deux audits
            List auditDTOList = ActionUtils.getCurrentAuditsAsDTO( pRequest );
            // On récupère le projet
            ComponentDTO project = (ComponentDTO) pRequest.getSession().getAttribute( BaseDispatchAction.PROJECT_DTO );
            // On vérifie qu'on en a bien deux et qu'ils ne sont pas nuls et que le projet est défini
            // sinon il s'agit d'une erreur
            if ( auditDTOList != null && auditDTOList.size() == 2 && auditDTOList.get( 0 ) != null
                && auditDTOList.get( 1 ) != null && project != null )
            {
                // On transforme le formulaire en un tableau de string
                Object[] filter = new Object[EvolutionForm.NB_FILTER];
                WTransformerFactory.formToObj( EvolutionTransformer.class, (WActionForm) pForm, filter );
                // On récupère l'applicationComponent
                IApplicationComponent ac = AccessDelegateHelper.getInstance( "Results" );
                AuditDTO firstAudit = (AuditDTO) auditDTOList.get( 0 );
                AuditDTO secondAudit = (AuditDTO) auditDTOList.get( 1 );
                Object[] paramsTab = { firstAudit, secondAudit, project, filter, new Integer( MAX_RESULTS ) };
                // On appelle la méthode avec le filtre associé
                Collection results = (Collection) ac.execute( "getChangedComponentResults", paramsTab );
                // Si le nombre de résultats correspond au maximum d'élément affichables (cf. MAX_RESULTS)
                // on affiche un message dans la page
                pRequest.getSession().removeAttribute( DISPLAY_RESULTS_MSG ); // gestion de la session
                if ( results.size() >= MAX_RESULTS )
                {
                    pRequest.getSession().setAttribute( DISPLAY_RESULTS_MSG, Boolean.TRUE );
                }
                // On transforme les listes en formulaire qui dépend des filtres utilisés et du tri
                Object[] args = new Object[] { results, pRequest.getLocale() };
                WTransformerFactory.objToForm( EvolutionTransformer.class, (WActionForm) pForm, args );
            }
        }
        catch ( Exception e )
        {
            handleException( e, errors, pRequest );
        }
        return forward;
    }

    /**
     * Export PDF pour la comparaison des audits
     * 
     * @param pMapping le actionMapping
     * @param pForm le form
     * @param pRequest la request
     * @param pResponse la response
     * @return l'actionForward
     * @throws ServletException exception pouvant etre levee
     */
    public ActionForward exportPDF( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                    HttpServletResponse pResponse )
        throws ServletException
    {
        try
        {
            // Les données seront une liste à 1 élément contenant le formulaire courant
            Collection data = new ArrayList();
            data.add( pForm );

            // Les paramètres
            HashMap parameters = new HashMap();
            // Le nom de l'utilisateur
            LogonBean logon = (LogonBean) pRequest.getSession().getAttribute( WConstants.USER_KEY );
            parameters.put( "userName", logon.getMatricule() );

            PDFDataJasperReports pdfData =
                new PDFDataJasperReports( pRequest.getLocale(), getResources( pRequest ), data,
                                          "/org/squale/squaleweb/resources/jasperreport/Comparison.jasper", false,
                                          parameters );
            PDFFactory.generatePDFToHTTPResponse( pdfData, pResponse, "", PDFEngine.JASPERREPORTS );
        }
        catch ( Exception e )
        {
            throw new ServletException( e );
        }
        return null;
    }
}
