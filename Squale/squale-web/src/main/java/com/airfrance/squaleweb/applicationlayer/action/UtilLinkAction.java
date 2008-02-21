package com.airfrance.squaleweb.applicationlayer.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.airfrance.welcom.struts.transformer.WTransformerFactory;

import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.DefaultAction;
import com.airfrance.squaleweb.transformer.ServeurListTransformer;
import com.airfrance.squaleweb.applicationlayer.formbean.config.ServeurListForm;

import java.util.Collection;

/**
 */
public class UtilLinkAction extends DefaultAction {

    /**
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward goodPractice(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {
        ActionForward forward = null;
        ActionErrors actionErrors = new ActionErrors();
        try {
            forward = pMapping.findForward("goodPractice");
        } catch (Exception e) {
            // Factorisation du traitement des exceptions
            handleException(e, actionErrors, pRequest);
        }
        if (!actionErrors.isEmpty()) {
            // Sauvegarde des messages
            saveMessages(pRequest, actionErrors);
            // Routage vers la page d'erreur
            forward = pMapping.findForward("total_failure");
        }
        return forward;
    }

    /**
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward error(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {
        ActionForward forward = null;
        ActionErrors actionErrors = new ActionErrors();
        try {
            forward = pMapping.findForward("error");
        } catch (Exception e) {
            // Factorisation du traitement des exceptions
            handleException(e, actionErrors, pRequest);
        }
        if (!actionErrors.isEmpty()) {
            // Sauvegarde des messages
            saveMessages(pRequest, actionErrors);
            // Routage vers la page d'erreur
            forward = pMapping.findForward("total_failure");
        }
        return forward;
    }

    /**
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward newApplication(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {
        ActionForward forward = null;
        ActionErrors actionErrors = new ActionErrors();
        try {
            forward = pMapping.findForward("newApplication");
        } catch (Exception e) {
            // Factorisation du traitement des exceptions
            handleException(e, actionErrors, pRequest);
        }
        if (!actionErrors.isEmpty()) {
            // Sauvegarde des messages
            saveMessages(pRequest, actionErrors);
            // Routage vers la page d'erreur
            forward = pMapping.findForward("total_failure");
        }
        return forward;
    }

    /**
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward configApplication(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {
        ActionForward forward = null;
        ActionErrors actionErrors = new ActionErrors();
        try {
            IApplicationComponent ac = AccessDelegateHelper.getInstance("Serveur");
            Collection lListeServeurDTO = (Collection) ac.execute("listeServeurs");
            ServeurListForm lListeServeurForm = new ServeurListForm();
            WTransformerFactory.objToForm(ServeurListTransformer.class,lListeServeurForm,lListeServeurDTO);
            pRequest.setAttribute("listeServeur",lListeServeurForm);
            
            forward = pMapping.findForward("configApplication");
        } catch (Exception e) {
            // Factorisation du traitement des exceptions
            handleException(e, actionErrors, pRequest);
        }
        if (!actionErrors.isEmpty()) {
            // Sauvegarde des messages
            saveMessages(pRequest, actionErrors);
            // Routage vers la page d'erreur
            forward = pMapping.findForward("total_failure");
        }
        return forward;
    }

    /**
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward addRights(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {
        ActionForward forward = null;
        ActionErrors actionErrors = new ActionErrors();
        try {
            forward = pMapping.findForward("addRights");
        } catch (Exception e) {
            // Factorisation du traitement des exceptions
            handleException(e, actionErrors, pRequest);
        }
        if (!actionErrors.isEmpty()) {
            // Sauvegarde des messages
            saveMessages(pRequest, actionErrors);
            // Routage vers la page d'erreur
            forward = pMapping.findForward("total_failure");
        }
        return forward;
    }

    /**
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward adminApplication(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {
        ActionForward forward = null;
        ActionErrors actionErrors = new ActionErrors();
        
        try {
            forward = pMapping.findForward("adminApplication");
        } catch (Exception e) {
            // Factorisation du traitement des exceptions
            handleException(e, actionErrors, pRequest);
        }
        if (!actionErrors.isEmpty()) {
            // Sauvegarde des messages
            saveMessages(pRequest, actionErrors);
            // Routage vers la page d'erreur
            forward = pMapping.findForward("total_failure");
        }
        return forward;
    }

    /**
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward applicationSummary(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {
        ActionForward forward = null;
        ActionErrors actionErrors = new ActionErrors();
        try {
            forward = pMapping.findForward("applicationSummary");
        } catch (Exception e) {
            // Factorisation du traitement des exceptions
            handleException(e, actionErrors, pRequest);
        }
        if (!actionErrors.isEmpty()) {
            // Sauvegarde des messages
            saveMessages(pRequest, actionErrors);
            // Routage vers la page d'erreur
            forward = pMapping.findForward("total_failure");
        }
        return forward;
    }

    /**
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward message(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {
        ActionForward forward = null;
        ActionErrors actionErrors = new ActionErrors();
        try {
            forward = pMapping.findForward("message");
        } catch (Exception e) {
            // Factorisation du traitement des exceptions
            handleException(e, actionErrors, pRequest);
        }
        if (!actionErrors.isEmpty()) {
            // Sauvegarde des messages
            saveMessages(pRequest, actionErrors);
            // Routage vers la page d'erreur
            forward = pMapping.findForward("total_failure");
        }
        return forward;
    }

    /**
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward login(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {
        ActionForward forward = null;
        ActionErrors actionErrors = new ActionErrors();
        try {
            forward = pMapping.findForward("login");
        } catch (Exception e) {
            // Factorisation du traitement des exceptions
            handleException(e, actionErrors, pRequest);
        }
        if (!actionErrors.isEmpty()) {
            // Sauvegarde des messages
            saveMessages(pRequest, actionErrors);
            // Routage vers la page d'erreur
            forward = pMapping.findForward("total_failure");
        }
        return forward;
    }

    /**
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward homepage(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {
        ActionForward forward = null;
        ActionErrors actionErrors = new ActionErrors();
        try {
            forward = pMapping.findForward("homepage");
        } catch (Exception e) {
            // Factorisation du traitement des exceptions
            handleException(e, actionErrors, pRequest);
        }
        if (!actionErrors.isEmpty()) {
            // Sauvegarde des messages
            saveMessages(pRequest, actionErrors);
            // Routage vers la page d'erreur
            forward = pMapping.findForward("total_failure");
        }
        return forward;
    }

}
