package com.airfrance.squaleweb.applicationlayer.action.component.parameters;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.airfrance.squaleweb.applicationlayer.action.component.CreateProjectAction;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.EclipseUserLibForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.EclipseVarForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.JCompilingForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.JavaCompilationForm;
import com.airfrance.squaleweb.applicationlayer.formbean.creation.CreateProjectForm;
import com.airfrance.squaleweb.transformer.component.parameters.JCompilingConfTransformer;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Configuration des paramètres de la tâche JCompiling
 */
public class CreateJCompilingParametersAction extends CreateParametersAction {

    /**
     * Ajoute des critères de compilation au projet Java
     * 
      * @param pMapping le mapping.
      * @param pForm le formulaire à lire.
      * @param pRequest la requête HTTP.
      * @param pResponse la réponse de la servlet.
      * @return l'action à réaliser.
      */
    public ActionForward addProjectJavaCompiling(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {
        ActionMessages errors = new ActionMessages();
        ActionForward forward = null;
        try {
            forward = pMapping.findForward("success");
            // Affectation au projet courant
            CreateProjectForm project = (CreateProjectForm) pRequest.getSession().getAttribute("createProjectForm");
            // Récupération du bean pour les paramètres java:
            JCompilingForm jCompilingForm = (JCompilingForm) pRequest.getSession().getAttribute("jCompilingForm");
            // On ajoute la règle au formulaire
            jCompilingForm.getCompilationRules().add(pForm);
            // Transformation des paramètres donnés dans le formulaire pour le projet
            WTransformerFactory.formToObj(JCompilingConfTransformer.class, jCompilingForm, project.getParameters());
            // On sauvegarde le projet
            new CreateProjectAction().saveProject(pMapping, project, pRequest, pResponse);
            // On supprime le type de compilation des paramètres de la requête
            pRequest.getParameterMap().remove("kindOfTask");

        } catch (Exception e) {
            handleException(e, errors, pRequest);
            saveMessages(pRequest, errors);
            // erreurs non prévues --> traitement spécifique
            forward = pMapping.findForward("total_failure");
        }
        if (!errors.isEmpty()) {
            // Affichage des erreurs sur la page initiale
            saveMessages(pRequest, errors);
            forward = pMapping.findForward("failure");
        }
        //On est passé par un menu donc on réinitialise le traceur
        resetTracker(pRequest);
        return forward;
    }

    /**
     * Retire une règle de compilation de la liste
     * 
      * @param pMapping le mapping.
      * @param pForm le formulaire à lire.
      * @param pRequest la requête HTTP.
      * @param pResponse la réponse de la servlet.
      * @return l'action à réaliser.
      */
    public ActionForward removeCompilingRules(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {

        ActionMessages errors = new ActionMessages();
        ActionForward forward = null;

        try {
            // Affectation au projet courant
            CreateProjectForm project = (CreateProjectForm) pRequest.getSession().getAttribute("createProjectForm");
            // Récupération du bean courant
            JCompilingForm jCompilingForm = (JCompilingForm) pForm;
            Iterator iter = jCompilingForm.getCompilationRules().iterator();
            // Suppression des règles de compilation sélectionnées
            // directement depuis l'itérateur
            boolean noSelection = true; // indique qu'aucune règle n'a été sélectionnée
            while (iter.hasNext()) {
                if (((JavaCompilationForm) iter.next()).isSelected()) {
                    iter.remove();
                    noSelection = false;
                }
            }
            // Si il n'y a pas eu de sélection, on affiche un message
            if (noSelection) {
                ActionMessage message = new ActionMessage("error.java.compiling.rule_selection");
                errors.add("ruleSelection", message);
                saveMessages(pRequest, errors);
            }
            // Transformation des paramètres donnés dans le formulaire pour le projet
            WTransformerFactory.formToObj(JCompilingConfTransformer.class, jCompilingForm, project.getParameters());
            // On sauvegarde le projet si il y reste au moins une règle
            if(jCompilingForm.getCompilationRules().size() > 0) {
                new CreateProjectAction().saveProject(pMapping, project, pRequest, pResponse);
            }
            forward = pMapping.findForward("success");
        } catch (Exception e) {
            // Traitement factorisé des exceptions
            handleException(e, errors, pRequest);
            saveMessages(pRequest, errors);
            // Renvoi vers la page d'erreur
            forward = pMapping.findForward("total_failure");
        }
        //On est passé par un menu donc on réinitialise le traceur
        resetTracker(pRequest);
        return forward;
    }

    /**
     * Ajoute une variable eclipse
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward addEclipseVar(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {

        ActionMessages errors = new ActionMessages();
        ActionForward forward = null;
        try {
            forward = pMapping.findForward("success");
            // Affectation au projet courant
            CreateProjectForm project = (CreateProjectForm) pRequest.getSession().getAttribute("createProjectForm");
            // Récupération du bean pour les paramètres java:
            JCompilingForm jCompilingForm = (JCompilingForm) pRequest.getSession().getAttribute("jCompilingForm");
            // On ajoute la règle au formulaire
            jCompilingForm.getEclipseVars().add(pForm);
            // Transformation des paramètres donnés dans le formulaire pour le projet
            WTransformerFactory.formToObj(JCompilingConfTransformer.class, jCompilingForm, project.getParameters());
            // On sauvegarde le projet
            new CreateProjectAction().saveProject(pMapping, project, pRequest, pResponse);
            // On supprime le type de compilation des paramètres de la requête
            pRequest.getParameterMap().remove("kindOfTask");

        } catch (Exception e) {
            handleException(e, errors, pRequest);
            saveMessages(pRequest, errors);
            // erreurs non prévues --> traitement spécifique
            forward = pMapping.findForward("total_failure");
        }
        if (!errors.isEmpty()) {
            // Affichage des erreurs sur la page initiale
            saveMessages(pRequest, errors);
            forward = pMapping.findForward("failure");
        }
        //On est passé par un menu donc on réinitialise le traceur
        resetTracker(pRequest);
        return forward;
    }

    /**
     * Retire des variables eclipse
     * 
      * @param pMapping le mapping.
      * @param pForm le formulaire à lire.
      * @param pRequest la requête HTTP.
      * @param pResponse la réponse de la servlet.
      * @return l'action à réaliser.
      */
    public ActionForward removeEclipseVars(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {

        ActionMessages errors = new ActionMessages();
        ActionForward forward = null;

        try {
            // Affectation au projet courant
            CreateProjectForm project = (CreateProjectForm) pRequest.getSession().getAttribute("createProjectForm");
            // Récupération du bean courant
            JCompilingForm jCompilingForm = (JCompilingForm) pForm;
            Iterator iter = jCompilingForm.getEclipseVars().iterator();
            // Suppression des variables sélectionnées
            // directement depuis l'itérateur
            boolean noSelection = true; // indique qu'aucune variable n'a été sélectionnée
            while (iter.hasNext()) {
                if (((EclipseVarForm) iter.next()).isSelected()) {
                    iter.remove();
                    noSelection = false;
                }
            }
            // Si il n'y a pas eu de sélection, on affiche un message
            if (noSelection) {
                ActionMessage message = new ActionMessage("error.java.compiling.var_selection");
                errors.add("ruleSelection", message);
                saveMessages(pRequest, errors);
            }
            // Transformation des paramètres donnés dans le formulaire pour le projet
            WTransformerFactory.formToObj(JCompilingConfTransformer.class, jCompilingForm, project.getParameters());
            // On sauvegarde le projet
            new CreateProjectAction().saveProject(pMapping, project, pRequest, pResponse);
            forward = pMapping.findForward("success");
        } catch (Exception e) {
            // Traitement factorisé des exceptions
            handleException(e, errors, pRequest);
            saveMessages(pRequest, errors);
            // Renvoi vers la page d'erreur
            forward = pMapping.findForward("total_failure");
        }
        //On est passé par un menu donc on réinitialise le traceur
        resetTracker(pRequest);
        return forward;
    }

    /**
     * Ajoute une libraire eclipse
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward addEclipseLib(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {

        ActionMessages errors = new ActionMessages();
        ActionForward forward = null;
        try {
            forward = pMapping.findForward("success");
            // Affectation au projet courant
            CreateProjectForm project = (CreateProjectForm) pRequest.getSession().getAttribute("createProjectForm");
            // Récupération du bean pour les paramètres java:
            JCompilingForm jCompilingForm = (JCompilingForm) pRequest.getSession().getAttribute("jCompilingForm");
            // On ajoute la règle au formulaire
            jCompilingForm.getEclipseLibs().add(pForm);
            // Transformation des paramètres donnés dans le formulaire pour le projet
            WTransformerFactory.formToObj(JCompilingConfTransformer.class, jCompilingForm, project.getParameters());
            // On sauvegarde le projet
            new CreateProjectAction().saveProject(pMapping, project, pRequest, pResponse);
            // On supprime le type de compilation des paramètres de la requête
            pRequest.getParameterMap().remove("kindOfTask");

        } catch (Exception e) {
            handleException(e, errors, pRequest);
            saveMessages(pRequest, errors);
            // erreurs non prévues --> traitement spécifique
            forward = pMapping.findForward("total_failure");
        }
        if (!errors.isEmpty()) {
            // Affichage des erreurs sur la page initiale
            saveMessages(pRequest, errors);
            forward = pMapping.findForward("failure");
        }
        //On est passé par un menu donc on réinitialise le traceur
        resetTracker(pRequest);
        return forward;
    }

    /**
     * Retire des librairies eclipse
     * 
      * @param pMapping le mapping.
      * @param pForm le formulaire à lire.
      * @param pRequest la requête HTTP.
      * @param pResponse la réponse de la servlet.
      * @return l'action à réaliser.
      */
    public ActionForward removeEclipseLibs(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {

        ActionMessages errors = new ActionMessages();
        ActionForward forward = null;

        try {
            // Affectation au projet courant
            CreateProjectForm project = (CreateProjectForm) pRequest.getSession().getAttribute("createProjectForm");
            // Récupération du bean courant
            JCompilingForm jCompilingForm = (JCompilingForm) pForm;
            Iterator iter = jCompilingForm.getEclipseLibs().iterator();
            // Suppression des variables sélectionnées
            // directement depuis l'itérateur
            boolean noSelection = true; // indique qu'aucune variable n'a été sélectionnée
            while (iter.hasNext()) {
                if (((EclipseUserLibForm) iter.next()).isSelected()) {
                    iter.remove();
                    noSelection = false;
                }
            }
            // Si il n'y a pas eu de sélection, on affiche un message
            if (noSelection) {
                ActionMessage message = new ActionMessage("error.java.compiling.var_selection");
                errors.add("ruleSelection", message);
                saveMessages(pRequest, errors);
            }
            // Transformation des paramètres donnés dans le formulaire pour le projet
            WTransformerFactory.formToObj(JCompilingConfTransformer.class, jCompilingForm, project.getParameters());
            // On sauvegarde le projet
            new CreateProjectAction().saveProject(pMapping, project, pRequest, pResponse);
            forward = pMapping.findForward("success");
        } catch (Exception e) {
            // Traitement factorisé des exceptions
            handleException(e, errors, pRequest);
            saveMessages(pRequest, errors);
            // Renvoi vers la page d'erreur
            forward = pMapping.findForward("total_failure");
        }
        //On est passé par un menu donc on réinitialise le traceur
        resetTracker(pRequest);
        return forward;
    }
}
