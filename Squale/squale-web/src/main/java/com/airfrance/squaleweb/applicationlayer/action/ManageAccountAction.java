package com.airfrance.squaleweb.applicationlayer.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squalecommon.datatransfertobject.component.UserDTO;
import com.airfrance.squalecommon.util.mail.MailException;
import com.airfrance.squalecommon.util.mail.MailerHelper;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.DefaultAction;
import com.airfrance.squaleweb.applicationlayer.formbean.LogonBean;
import com.airfrance.squaleweb.applicationlayer.formbean.component.UserForm;
import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.squaleweb.transformer.LogonBeanTransformer;
import com.airfrance.squaleweb.transformer.UserTransformer;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;
import com.airfrance.welcom.struts.util.WConstants;

/**
 * Administration du compte de l'utilisateur
 * Cette action permet � un utilisateur d'obtenir la liste des informations rattach�es
 * et leur modification
 */
public class ManageAccountAction extends DefaultAction {

    /** Cl� pour r�cup�rer l'ancien email */
    public static final String OLD_EMAIL_KEY = "oldEmail";

    /**
     * Informations sur un utilisateur
     * @param pMapping le mapping.
     * @param pForm le formulaire � lire.
     * @param pRequest la requ�te HTTP.
     * @param pResponse la r�ponse de la servlet.
     * @return l'action � r�aliser.
     */
    public ActionForward detail(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {

        ActionMessages errors = new ActionMessages();
        ActionForward forward = null;

        try {
            // On r�cup�re les informations du user connect�
            LogonBean logonBeanSecurity = (LogonBean) pRequest.getSession().getAttribute(WConstants.USER_KEY);
            // On r�cup�re les informations de l'utilisateur
            UserDTO user = new UserDTO();
            user.setMatricule(logonBeanSecurity.getMatricule());

            IApplicationComponent ac = AccessDelegateHelper.getInstance("Login");
            Object[] paramIn = { user, Boolean.valueOf(logonBeanSecurity.isAdmin())};

            user = (UserDTO) ac.execute("verifyUser", paramIn);
            // On met � jour le formulaire 
            WTransformerFactory.objToForm(UserTransformer.class, (WActionForm) pForm, user);
            forward = pMapping.findForward("detail");
        } catch (Exception e) {
            handleException(e, errors, pRequest);
            saveMessages(pRequest, errors);
            forward = pMapping.findForward("total_failure");
        }
        //On est pass� par un menu donc on r�initialise le traceur
        resetTracker(pRequest);
        return (forward);
    }

    /**
      * Mise � jour des informations de l'utilisateur
      * @param pMapping le mapping.
      * @param pForm le formulaire � lire.
      * @param pRequest la requ�te HTTP.
      * @param pResponse la r�ponse de la servlet.
      * @return l'action � r�aliser.
      */
    public ActionForward update(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {

        ActionMessages errors = new ActionMessages();
        ActionForward forward = null;

        try {
            // R�cup�ration des informations
            UserDTO user = new UserDTO();
            WTransformerFactory.formToObj(UserTransformer.class, (WActionForm) pForm, user);
            // Les informations sur le matricule et l'id sont en session
            // pour des raisons de s�curit�
            LogonBean logonBeanSecurity = (LogonBean) pRequest.getSession().getAttribute(WConstants.USER_KEY);
            user.setMatricule(logonBeanSecurity.getMatricule());
            user.setID(logonBeanSecurity.getId());
            // Mise � jour des informations dans la base de donn�es
            IApplicationComponent ac = AccessDelegateHelper.getInstance("Login");
            Object[] paramIn = { user, Boolean.valueOf(logonBeanSecurity.isAdmin())};
            user = (UserDTO) ac.execute("createOrUpdateUser", paramIn);
            // Mise � jour du formulaire
            WTransformerFactory.objToForm(UserTransformer.class, (WActionForm) pForm, user);
            // Mise � jour du LogonBean en cons�quence
            boolean isAdmin = logonBeanSecurity.isAdmin();
            logonBeanSecurity = new LogonBean();
            WTransformerFactory.formToObj(LogonBeanTransformer.class, (WActionForm) pForm, new Object[] { logonBeanSecurity, Boolean.valueOf(isAdmin)});
            pRequest.getSession().setAttribute(WConstants.USER_KEY, logonBeanSecurity);
            addConfirmationMessage(pForm, pRequest, errors);
            // Renvoi vers le d�tail
            forward = pMapping.findForward("detail");
        } catch (Exception e) {
            handleException(e, errors, pRequest);
            saveMessages(pRequest, errors);
            forward = pMapping.findForward("total_failure");
        }
        //On est pass� par un menu donc on r�initialise le traceur
        resetTracker(pRequest);
        return (forward);
    }

    /**
     * Permet d'ajouter un message de confirmation ou d'erreur
     * @param pForm le formulaire
     * @param pRequest la requ�te
     * @param pErrors les erreurs
     */
    private void addConfirmationMessage(ActionForm pForm, HttpServletRequest pRequest, ActionMessages pErrors) {
        // On r�cup�re l'ancien email
        String oldEmail = pRequest.getParameter(OLD_EMAIL_KEY);
        String newMail = ((UserForm) pForm).getEmail();
        // Si l'email a chang� et qu'il n'est pas vide
        if (newMail.length() > 0 && !newMail.equals(oldEmail)) {
            // On va essay� d'envoyer un email de confirmation.
            // Si le message n'aboutit pas, on indiquera � l'utilisateur de v�rifier son email
            // L'email est quand m�me modifi� car l'erreur d'envoi peut provenir de l'outil d'envoi d'email...
            try {
                String object = WebMessages.getString(pRequest, "mail.update.confirm.object");
                String content = WebMessages.getString(pRequest, "mail.update.confirm.content");
                // si l'envoie de mail produit une erreur, c'est probablement que l'adresse
                // qui a �t� fourni est invalide donc on affiche le message d'avertissement
                MailerHelper.getMailerProvider().sendMail(null, new String[]{newMail},object,content);
            } catch (MailException e) {
                ActionMessage error = new ActionMessage("user_management.error.email");
                pErrors.add(ActionMessages.GLOBAL_MESSAGE, error);
                saveMessages(pRequest, pErrors);
            }
        }
    }
}