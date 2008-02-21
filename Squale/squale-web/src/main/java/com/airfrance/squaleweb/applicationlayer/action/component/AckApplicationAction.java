package com.airfrance.squaleweb.applicationlayer.action.component;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squalecommon.datatransfertobject.component.ApplicationConfDTO;
import com.airfrance.squalecommon.util.SqualeCommonConstants;
import com.airfrance.squalecommon.util.SqualeCommonUtils;
import com.airfrance.squalecommon.util.mail.MailerHelper;
import com.airfrance.squaleweb.applicationlayer.action.ActionUtils;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.AdminAction;
import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.squaleweb.transformer.ApplicationConfListTransformer;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Confirmation des nouvelles applications
 */
public class AckApplicationAction extends AdminAction {

    /**
     * Liste des applications à confirmer
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward list(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {

        ActionMessages errors = new ActionMessages();
        ActionForward forward = null;

        try {
            // On construit la liste des applications à confirmer
            IApplicationComponent ac2 = AccessDelegateHelper.getInstance("Validation");
            Collection applications = (Collection) ac2.execute("getApplicationsCreated", null);
            // On met à jour le formulaire 
            WTransformerFactory.objToForm(ApplicationConfListTransformer.class, (WActionForm) pForm, applications);
            pRequest.getSession().setAttribute("applicationListForm", pForm);
            forward = pMapping.findForward("list");
        } catch (Exception e) {
            handleException(e, errors, pRequest);
            saveMessages(pRequest, errors);
            forward = pMapping.findForward("total_failure");
        }
        //On est passé par un menu donc on réinitialise le traceur
        resetTracker(pRequest);
        return (forward);
    }

    /**
      * Confirmation des applications
      * Les applications sélectionnées sont confirmées
      * @param pMapping le mapping.
      * @param pForm le formulaire à lire.
      * @param pRequest la requête HTTP.
      * @param pResponse la réponse de la servlet.
      * @return l'action à réaliser.
      */
    public ActionForward acknowledge(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {

        ActionMessages messages = new ActionMessages();
        ActionErrors errors = new ActionErrors();
        ActionForward forward = null;

        try {
            forward = pMapping.findForward("acknowledge");
            // On construit la liste des applications à confirmer
            ArrayList applicationsToConfirm = new ArrayList();
            WTransformerFactory.formToObj(ApplicationConfListTransformer.class, (WActionForm) pForm, new Object[] { applicationsToConfirm, Boolean.TRUE });
            // La liste des applications devant être confirmées est vérifiée
            if (applicationsToConfirm.isEmpty()) {
                // Affichage d'une erreur sur le message
                ActionError error = new ActionError("error.invalidSelection");
                errors.add("", error);
                forward = pMapping.findForward("list");
            } else {
                // On confirme la création des applications
                // au niveau de la base de données
                Object[] args = { applicationsToConfirm };
                IApplicationComponent ac = AccessDelegateHelper.getInstance("Validation");
                List notValidated = (List) ac.execute("validateApplicationsCreation", args);
                // On construit la liste des messages de confirmation
                // à postériori
                Iterator it = applicationsToConfirm.iterator();
                while (it.hasNext()) {
                    ApplicationConfDTO applicationConf = (ApplicationConfDTO) it.next();
                    if (!notValidated.contains(applicationConf.getName())) {
                        ActionMessage message = new ActionMessage("info.ack_application", applicationConf.getName());
                        messages.add(ActionMessages.GLOBAL_MESSAGE, message);
                        // Notification par email sur les administrateurs
                        notifyByEmail(pRequest, applicationConf);
                    }
                }
                if (notValidated.size() > 0) {
                    // On sauvegarde un message d'erreur pour les applications qui n'ont pas
                    // pu être validée à cause de la non unicité de leur nom
                    String error = "<ul>";
                    for (Iterator errorIt = notValidated.iterator(); errorIt.hasNext();) {
                        String name = (String) errorIt.next();
                        error += "<li>" + name + "</li>";
                    }
                    error += "</ul>";
                    ActionError messageError = new ActionError("error.ack_not_validated_application", error);
                    errors.add("", messageError);
                    // Mise à jour des informations de l'utilisateur
                    ActionUtils.refreshUser(pRequest);
                }
            }
            saveMessages(pRequest, messages);
            saveErrors(pRequest, errors);
        } catch (Exception e) {
            // Traitement de l'exception factorisé
            // Au niveau de la classe de base
            handleException(e, errors, pRequest);
            saveErrors(pRequest, errors);
            forward = pMapping.findForward("total_failure");
        }
        //On est passé par un menu donc on réinitialise le traceur
        resetTracker(pRequest);
        return (forward);
    }

    /**
     * Notification par email de la confirmation des applications
     * @param pRequest requête
     * @param pApplication application confirmée
     */
    private void notifyByEmail(HttpServletRequest pRequest, ApplicationConfDTO pApplication) {
        // Construction du corps du mail
        Object[] params = { pApplication.getName()};
        String sender = WebMessages.getString(getLocale(pRequest),"mail.sender.squale");
        String header = WebMessages.getString(getLocale(pRequest),"mail.header") ;
        String subject = sender + MessageFormat.format(WebMessages.getString(getLocale(pRequest), "applications_ack.mail.title"), params);
        String content = header + MessageFormat.format(WebMessages.getString(getLocale(pRequest), "applications_ack.mail.body"), params);
        // Envoi du mail aux admins du portail et aux managers de l'application
        SqualeCommonUtils.notifyByEmail(MailerHelper.getMailerProvider(), SqualeCommonConstants.MANAGERS_AND_ADMINS, new Long(pApplication.getId()), subject, content, false);
    }
}
