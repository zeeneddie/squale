package com.airfrance.squaleweb.applicationlayer.action.component.parameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.airfrance.squaleweb.applicationlayer.action.accessRights.ReaderAction;
import com.airfrance.squaleweb.applicationlayer.action.component.CreateProjectAction;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm;
import com.airfrance.squaleweb.applicationlayer.formbean.creation.CreateProjectForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Interface pour les actions associ�es � la configuration d'une t�che
 */
public class CreateParametersAction extends ReaderAction {

    /**
    * Permet de remplir le bean de la t�che et de l'enregistrer en session si besoin.
    * 
    * @param pMapping le mapping.
    * @param pForm le formulaire � lire.
    * @param pRequest la requ�te HTTP.
    * @param pResponse la r�ponse de la servlet.
    * @return l'action � r�aliser.
    */
    public ActionForward fill(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {
        ActionForward forward = null;
        ActionMessages errors = new ActionMessages();
        try {
            forward = pMapping.findForward("config");
            // Affectation au projet courant
            CreateProjectForm project = (CreateProjectForm) pRequest.getSession().getAttribute("createProjectForm");
            pForm.reset(pMapping, pRequest);
            WTransformerFactory.objToForm(((AbstractParameterForm) pForm).getTransformer(), ((WActionForm) pForm), getTransformerParameters(project, pRequest));
            // On ajout le nom du formulaire pour pouvoir le r�cup�rer
            project.getTaskForms().add(((AbstractParameterForm) pForm).getNameInSession());
        } catch (Exception e) {
            // Traitement factoris� des exceptions
            handleException(e, errors, pRequest);
            // Routage vers la page d'erreur
            forward = pMapping.findForward("total_failure");
        }
        if (!errors.isEmpty()) {
            saveMessages(pRequest, errors);
        }
        //On est pass� par un menu donc on r�initialise le traceur
        resetTracker(pRequest);
        return null;
    }

    /**
     * Ajoute les param�tres au projet
     * @param pMapping le mapping
     * @param pForm le formulaire
     * @param pRequest la requ�te
     * @param pResponse la r�ponse
     * @return l'action � r�aliser
     */
    public ActionForward addParameters(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {
        ActionForward forward = null;
        ActionMessages errors = new ActionMessages();
        // Affectation au projet courant
        CreateProjectForm project = (CreateProjectForm) pRequest.getSession().getAttribute("createProjectForm");
        try {
            forward = pMapping.findForward("configure");

            // On modifie les param�tres du projet pour cette t�che
            WTransformerFactory.formToObj(((AbstractParameterForm) pForm).getTransformer(), (WActionForm) pForm, getTransformerParameters(project, pRequest));

            // On sauvegarde le projet
            new CreateProjectAction().saveProject(pMapping, project, pRequest, pResponse);
        } catch (Exception e) {
            // Traitement factoris� des exceptions
            handleException(e, errors, pRequest);
        }
        if (!errors.isEmpty()) {
            saveMessages(pRequest, errors);
        }
        //On est pass� par un menu donc on r�initialise le traceur
        resetTracker(pRequest);
        return forward;
    }

    /**
     * @param pProject le formulaire repr�sentant le projet
     * @param pRequest la requ�te
     * @return les param�tres n�cessaires au transformer
     * @throws Exception si erreur
     */
    public Object[] getTransformerParameters(CreateProjectForm pProject, HttpServletRequest pRequest) throws Exception {
        return new Object[] { pProject.getParameters()};
    }

    /**
     * Permet de d�configurer la t�che.
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire � lire.
     * @param pRequest la requ�te HTTP.
     * @param pResponse la r�ponse de la servlet.
     * @return l'action � r�aliser.
     */
    public ActionForward removeParameters(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {
        ActionForward forward = null;
        ActionMessages errors = new ActionMessages();
        try {
            forward = pMapping.findForward("configure");
            // Affectation au projet courant
            CreateProjectForm project = (CreateProjectForm) pRequest.getSession().getAttribute("createProjectForm");
            // On supprime toutes les entr�es li�es � la t�che dans les param�tres du projet
            String[] constants = ((AbstractParameterForm) pForm).getParametersConstants();
            for(int i=0; i<constants.length; i++) {
                project.getParameters().getParameters().remove(constants[i]);
            }
            // On sauvegarde le projet
            new CreateProjectAction().saveProject(pMapping, project, pRequest, pResponse);
        } catch (Exception e) {
            // Traitement factoris� des exceptions
            handleException(e, errors, pRequest);
            // Routage vers la page d'erreur
            forward = pMapping.findForward("total_failure");
        }
        if (!errors.isEmpty()) {
            saveMessages(pRequest, errors);
        }
        //On est pass� par un menu donc on r�initialise le traceur
        resetTracker(pRequest);
        return forward;
    }
}