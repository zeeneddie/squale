package com.airfrance.squaleweb.applicationlayer.action.config;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.AdminAction;
import com.airfrance.squaleweb.transformer.ConfigPortailTransformer;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Configuration du portail
 */
public class ConfigPortailAction extends AdminAction {

    /**
     * Affichage de la liste des types de récupération des sources et de la liste
     * des profiles.
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward list(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {
        ActionForward forward = null;
        ActionErrors errors = new ActionErrors();
        try {
            IApplicationComponent ac = AccessDelegateHelper.getInstance("SqualixConfig");
            ArrayList managersDTO = (ArrayList) ac.execute("getSourceManagements", null);
            ArrayList profilesDTO = (ArrayList) ac.execute("getProfiles", null);
            WTransformerFactory.objToForm(ConfigPortailTransformer.class, (WActionForm)pForm, profilesDTO, managersDTO);
        } catch (Exception e) {
            handleException(e, errors, pRequest);
        }
        if (!errors.isEmpty()) {
            saveMessages(pRequest, errors);
        }
        forward = pMapping.findForward("list");
        return forward;
    }
}