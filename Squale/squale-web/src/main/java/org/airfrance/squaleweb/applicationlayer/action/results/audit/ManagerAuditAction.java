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
package com.airfrance.squaleweb.applicationlayer.action.results.audit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.ManagerAction;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ApplicationErrorForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.AuditForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.SplitAuditsListForm;
import com.airfrance.squaleweb.transformer.AuditTransformer;
import com.airfrance.squaleweb.util.SqualeWebActionUtils;
import com.airfrance.squaleweb.util.SqualeWebConstants;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 */
public class ManagerAuditAction
    extends ManagerAction
{

    /**
     * Permet la purge d'audits.
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward purge( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                HttpServletResponse pResponse )
    {
        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        ActionForward forward = pMapping.findForward( "purgeError" );
        try
        {
            // Récupération des audits sélectionnés par checkbox
            SplitAuditsListForm form = (SplitAuditsListForm) pForm;
            ArrayList auditsSelected = new ArrayList();
            auditsSelected.addAll( new AuditUtils().getSelection( form.getAudits() ) );
            // Si la purge s'est bien passée, on foraward vers une pages récapitulative des purges
            if ( purge( form.getApplicationName(), auditsSelected, form.getAudits(), pRequest, messages ) )
            {
                forward = pMapping.findForward( "purge" );
                saveMessages( pRequest, messages );
                // On met en attribut de requête l'action appelante pour le bouton retour
                // Il y a au moins un audit, on le récupère pour avoir les informations
                AuditForm audit = (AuditForm) form.getAudits().get( 0 );
                pRequest.setAttribute( SqualeWebConstants.RETURN_ACTION_KEY, "audits.do?action=list&kind="
                    + SqualeWebActionUtils.getAuditKind( audit.getStatus() ) + "&applicationId="
                    + audit.getApplicationId() );
            }
        }
        catch ( Exception e )
        {
            handleException( e, errors, pRequest );
        }
        if ( !errors.isEmpty() )
        {
            // Enregistrement du message et routage vers la page d'erreur
            saveErrors( pRequest, errors );
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * Permet la purge d'audits en cours
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward purgeRunningAudits( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                             HttpServletResponse pResponse )
    {
        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        ActionForward forward = pMapping.findForward( "purgeInvalid" );
        try
        {
            // Récupération des audits sélectionnés par checkbox
            SplitAuditsListForm form = (SplitAuditsListForm) pForm;
            ArrayList auditsSelected = new ArrayList();
            auditsSelected.addAll( new AuditUtils().getSelection( form.getShutDownAudits() ) );
            if ( purge( form.getApplicationName(), auditsSelected, form.getShutDownAudits(), pRequest, messages ) )
            {
                forward = pMapping.findForward( "purge" );
                // On met en attribut de requête l'action appelante pour le bouton retour
                pRequest.setAttribute( SqualeWebConstants.RETURN_ACTION_KEY,
                                       "adminNotAttemptedAudit.do?action=displayNotAttemptedAndRunning" );
            }
        }
        catch ( Exception e )
        {
            handleException( e, errors, pRequest );
        }
        if ( !errors.isEmpty() )
        {
            // Enregistrement du message et routage vers la page d'erreur
            saveErrors( pRequest, errors );
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * Méthode permettant de factoriser le code de vérification de la sélection et le code de suppression des différents
     * audits
     * 
     * @param pApplicationName le nom de l'application
     * @param pSelectedAudit la liste des audits à purger
     * @param pCurrentAuditsList la liste correspondante au niveau du form
     * @param pRequest la requete http
     * @param messages les messages
     * @return true si la sélection pour la purge est correcte : * Il faut au moins un audit * on doit garder au moins
     *         un audit terminé
     * @throws JrafEnterpriseException en cas d'échec de récupération des données
     * @throws WTransformerException en cas de problème de transformation
     */
    private boolean purge( String pApplicationName, List pSelectedAudit, List pCurrentAuditsList,
                           HttpServletRequest pRequest, ActionMessages messages )
        throws JrafEnterpriseException, WTransformerException
    {
        ActionMessage message = null;
        boolean selectionOk = true;
        // On doit sélectionné au moins un audit pour que la sélection
        if ( pSelectedAudit.size() < 1 )
        {
            message = new ActionMessage( "error.invalid_audits_purge_selection" );
            messages.add( ActionMessages.GLOBAL_MESSAGE, message );
            selectionOk = false;
        }
        else
        {
            // On doit garder au moins un audit terminé
            if ( ( (AuditForm) pSelectedAudit.get( 0 ) ).getStatus() == AuditBO.TERMINATED )
            {
                if ( pCurrentAuditsList.size() == pSelectedAudit.size() )
                {
                    message = new ActionMessage( "error.invalid_terminated_audits_purge_selection" );
                    messages.add( ActionMessages.GLOBAL_MESSAGE, message );
                    selectionOk = false;
                }
            }
        }
        if ( message == null )
        {
            // La sélection est forcément valide ici
            // Parcours de la sélection et purge de chaque audit
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Purge" );
            Object[] paramIn = { null };
            Iterator iterator = pSelectedAudit.iterator();
            // On purge les audits un par un
            // et on crée le message de confirmation
            while ( iterator.hasNext() )
            {
                AuditForm auditForm = (AuditForm) iterator.next();
                AuditDTO auditDTO = (AuditDTO) WTransformerFactory.formToObj( AuditTransformer.class, auditForm )[0];
                paramIn[0] = auditDTO;
                ac.execute( "purgeAudit", paramIn );
                // Construction d'un message de confirmation pour chaque
                // audit purgé
                // Le message affichera la date de réalisation de l'audit
                SimpleDateFormat format = new SimpleDateFormat( "EEE d MMM yyyy", pRequest.getLocale() );
                String date = format.format( auditDTO.getRealDate() );
                // Si il s'agit d'un audit de jalon, on rajoute le label entre parenthèses
                if ( auditDTO.getType().equals( AuditBO.MILESTONE ) && auditDTO.getName() != null )
                {
                    date += " (" + auditDTO.getName() + ")";
                }
                String appliName =
                    ( null == auditForm.getApplicationName() ) ? pApplicationName : auditForm.getApplicationName();
                message = new ActionMessage( "info.purge_audit", new Object[] { appliName, date } );
                messages.add( ActionMessages.GLOBAL_MESSAGE, message );
            }
        }
        // sauvegarde les messages
        saveMessages( pRequest, messages );
        return selectionOk;
    }

    /**
     * Permet la purge d'un audit en echec.
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward purgeFailedAudit( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                           HttpServletResponse pResponse )
    {

        ActionErrors errors = new ActionErrors();
        ActionForward forward = new ActionForward();

        try
        {
            // Récupération de l'audit concerné
            ApplicationErrorForm form = (ApplicationErrorForm) pForm;
            ActionErrors messages = new ActionErrors();
            // Purge de l'audit
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Purge" );
            Object[] paramIn = { null };
            AuditDTO auditDTO = new AuditDTO();
            auditDTO.setID( form.getAuditId() );
            paramIn[0] = auditDTO;
            ac.execute( "purgeAudit", paramIn );
            // Construction d'un message de confirmation pour
            // l'audit purgé
            String appliName = (String) pRequest.getParameter( "applicationName" );
            String date = (String) pRequest.getParameter( "auditDate" );
            ActionMessage message = new ActionMessage( "info.purge_failed_audit", appliName, date );
            messages.add( ActionMessages.GLOBAL_MESSAGE, message );
            saveMessages( pRequest, messages );
            forward = pMapping.findForward( "purge" );
            // On met en attribut de requête l'action appelante pour le bouton retour
            String oldAuditId = pRequest.getParameter( "oldAuditId" );
            if ( null != oldAuditId && !oldAuditId.equals( "none" ) )
            {
                // On vient de la liste des audits en echec d'une application
                String oldPAuditId = pRequest.getParameter( "oldPreviousAuditId" );
                pRequest.setAttribute( SqualeWebConstants.RETURN_ACTION_KEY,
                                       "audits.do?action=list&kind=failed&applicationId="
                                           + form.getApplicationId() + "&currentAuditId=" + oldAuditId
                                           + "&previousAuditId=" + oldPAuditId );
            }
            else
            {
                // On retourne sur la page d'accueil
                pRequest.setAttribute( SqualeWebConstants.RETURN_ACTION_KEY, "index.do" );
            }
        }
        catch ( Exception e )
        {
            handleException( e, errors, pRequest );
        }
        if ( !errors.isEmpty() )
        {
            // Enregistrement du message et routage vers la page d'erreur
            saveErrors( pRequest, errors );
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * Permet de reprogrammer un audit.
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward restart( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                  HttpServletResponse pResponse )
    {

        ActionErrors errors = new ActionErrors();
        ActionForward forward = new ActionForward();

        try
        {
            // Récupération de l'audit concerné
            ApplicationErrorForm form = (ApplicationErrorForm) pForm;
            ActionErrors messages = new ActionErrors();
            // Purge de l'audit
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "ApplicationAdmin" );
            Object[] paramIn = { null };
            AuditDTO auditDTO = new AuditDTO();
            auditDTO.setID( form.getAuditId() );
            // On récupère l'id de l'application sur laquelle l'audit à été réalisé
            String appliId = (String) pRequest.getParameter( "applicationId" );
            // On affecte l'id de l'application au dto
            auditDTO.setApplicationId( Integer.parseInt( appliId ) );
            paramIn[0] = auditDTO;
            AuditDTO newAudit = (AuditDTO) ac.execute( "restartAudit", paramIn );
            // Construction d'un message de confirmation pour
            // l'audit purgé
            ActionMessage message = null;
            if ( null != newAudit )
            {
                message = new ActionMessage( "info.restart_failed_audit" );
            }
            else
            {
                message = new ActionMessage( "error.restart_failed_audit" );
            }
            messages.add( ActionMessages.GLOBAL_MESSAGE, message );
            saveMessages( pRequest, messages );
            forward = pMapping.findForward( "restart" );
        }
        catch ( Exception e )
        {
            handleException( e, errors, pRequest );
        }
        if ( !errors.isEmpty() )
        {
            // Enregistrement du message et routage vers la page d'erreur
            saveErrors( pRequest, errors );
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }
}
