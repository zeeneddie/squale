package com.airfrance.squaleweb.applicationlayer.action.results.project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.config.TaskDTO;
import com.airfrance.squalecommon.datatransfertobject.result.ErrorDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.ErrorBO;
import com.airfrance.squaleweb.applicationlayer.action.ActionUtils;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.DefaultAction;
import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ApplicationErrorForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ProjectForm;
import com.airfrance.squaleweb.applicationlayer.tracker.TrackerStructure;
import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.squaleweb.transformer.ApplicationTransformer;
import com.airfrance.squaleweb.transformer.ErrorListTransformer;
import com.airfrance.squaleweb.transformer.ProjectErrorTransformer;
import com.airfrance.squaleweb.transformer.ProjectTransformer;
import com.airfrance.squaleweb.transformer.SetOfErrorsListTransformer;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 */
public class ErrorsAction extends DefaultAction {

    /**
     * Récupération des erreurs
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward errors(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {
        ActionErrors actionErrors = new ActionErrors();
        ActionForward forward = null;
        ArrayList list = new ArrayList(0);
        try {
            WActionForm projectForm = initProject(pMapping, pRequest);
            ComponentDTO project = (ComponentDTO) WTransformerFactory.formToObj(ProjectTransformer.class, projectForm)[0];
            // On récupère l'id de l'audit courant
            String currentAuditId = (String) pRequest.getAttribute("currentAuditId");
            if (null == currentAuditId) {
                // On le récupère depuis le form
                currentAuditId = ((RootForm) pForm).getCurrentAuditId();
            }
            // Récupère la liste des taches dans la base par nom de tâche
            Collection tasks = getTasks(project.getID(), currentAuditId);
            Collection errors;
            if (tasks != null) {
                Iterator it = tasks.iterator();
                while (it.hasNext()) {
                    String taskName = ((String) it.next());
                    errors = getErrorsByTask(currentAuditId, project, taskName);
                    // Si il y a eu des erreurs pour la tache courante,
                    // alors on les stocke.
                    if (errors != null && errors.size() != 0) {
                        // Conversion du formulaire
                        String errorLevelMax = getMaxLevel(errors);
                        Object[] params = { taskName, errors, errorLevelMax };
                        // ajout dans la liste
                        list.add(WTransformerFactory.objToForm(ErrorListTransformer.class, params));
                    }
                }
            }
            // ajout de la liste dans le set
            Object[] params = { list };
            // le form pour faciliter l'affichage dans la jsp
            WActionForm set = WTransformerFactory.objToForm(SetOfErrorsListTransformer.class, params);
            // on met dans la session le set
            pRequest.getSession().setAttribute("setOfErrorsListForm", set);
            forward = pMapping.findForward("displayErrors");
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
        //Mise en place du traceur historique
        String displayName = WebMessages.getString(pRequest.getLocale(), "tracker.error.project");
        updateHistTracker(displayName, "project.do?action=summary", TrackerStructure.UNDEFINED, pRequest, false);
        return forward;
    }

    /**
     * Récupération des erreurs pour un audit en échec
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward applicationErrors(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {
        ActionErrors actionErrors = new ActionErrors();
        ActionForward forward = null;
        ApplicationErrorForm form = (ApplicationErrorForm) pForm;
        ArrayList list = new ArrayList(0);
        long auditId = -1;
        try {
            ComponentDTO appli = (ComponentDTO) WTransformerFactory.formToObj(ApplicationTransformer.class, ActionUtils.getCurrentApplication(pRequest))[0];
            List audits = initAudit(pRequest, appli);
            // ici audits ne peut pas etre null
            auditId = (((AuditDTO) audits.get(0)).getID());
            // On affecte l'id de l'audit au form
            form.setAuditId(auditId);
            form.setAuditDate(((AuditDTO) audits.get(0)).getFormattedDate());
            Collection projects = getAllProjects(audits, appli);
            Iterator it = projects.iterator();
            while (it.hasNext()) {
                ComponentDTO project = (ComponentDTO) it.next();
                Integer[] errors = getErrorsByProject(audits, project);
                List failedTasks = getFailedTasks(audits, project);
                Object[] params = new Object[] { project, errors, failedTasks };
                // ajout dans la liste
                list.add(WTransformerFactory.objToForm(ProjectErrorTransformer.class, params));
            }
            form.setList(list);
            // on met dans la session l'applicationErrorForm
            pRequest.getSession().setAttribute("applicationErrorForm", form);
            forward = pMapping.findForward("displayApplicationErrors");
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
        //Mise en place du traceur historique
        String displayName = WebMessages.getString(pRequest.getLocale(), "tracker.error.application");
        updateHistTracker(displayName, "audits.do?action=select&kind=failed&currentAuditId=" + auditId, TrackerStructure.UNDEFINED, pRequest, false);
        return forward;
    }

    /**
     * @param pMapping le mapping.
     * @param pRequest la requête HTTP.
     * @return le form
     * @throws JrafEnterpriseException en cas d'erreur sur l'interrogation en base
     * @throws WTransformerException en cas d'erreur lors des transformations
     */
    private ProjectForm initProject(ActionMapping pMapping, HttpServletRequest pRequest) throws JrafEnterpriseException, WTransformerException {
        ProjectForm project = null;
        Long projectId = null;
        // Obtention de l'id de projet
        String param = (String) pRequest.getAttribute("projectId");
        if (null == param) {
            param = (String) pRequest.getParameter("projectId");
        }
        if (param != null) {
            projectId = new Long(param);
        }
        // Récupération du projet actuellement en session
        ComponentDTO projectDto = (ComponentDTO) pRequest.getSession().getAttribute(BaseDispatchAction.PROJECT_DTO);
        project = (ProjectForm) WTransformerFactory.objToForm(ProjectTransformer.class, new Object[] { projectDto });
        // On vérifie si le projet choisi est différent de celui connu en session
        if ((project == null) || ((projectId != null) && (projectId.longValue() != project.getId()))) {
            project = setupProject(projectId, pRequest);
        }
        return project;
    }

    /**
     * Mise en place de la session pour le projet
     * Version édulcorée par rapport à celle de ProjectResultsAction car
     * on gère que le minimum pour les erreurs
     * @param pProjectId id du projet
     * @param pRequest requête
     * @return form du projet ou null si erreur
     * @throws JrafEnterpriseException si erreur
     * @throws WTransformerException si erreur
     */
    private ProjectForm setupProject(Long pProjectId, HttpServletRequest pRequest) throws JrafEnterpriseException, WTransformerException {
        ComponentDTO project = (ComponentDTO) pRequest.getSession().getAttribute(BaseDispatchAction.PROJECT_DTO);
        return (ProjectForm) WTransformerFactory.objToForm(ProjectTransformer.class, new Object[] { project });
    }

    /**
     * PRECONDITION: pErrors n'est pas null
     * @param pErrors la liste des erreurs
     * @return le niveau le plus critique de l'ensemble des erreurs
     */
    private String getMaxLevel(Collection pErrors) {
        // initialisation au minimum
        String max = ErrorBO.CRITICITY_LOW;
        Iterator it = pErrors.iterator();
        while (it.hasNext() && !max.equals(ErrorBO.CRITICITY_FATAL)) {
            ErrorDTO nextDTO = (ErrorDTO) it.next();
            if (nextDTO.getLevel().equals(ErrorBO.CRITICITY_WARNING)) {
                max = ErrorBO.CRITICITY_WARNING;
            } else {
                if (nextDTO.getLevel().equals(ErrorBO.CRITICITY_FATAL)) {
                    max = ErrorBO.CRITICITY_FATAL;
                }
            }
        }
        return max;
    }

    /**
     * liste les projets de cette application
     * @param pAuditsList la liste des audits
     * @param pApplication l'application dont on veut les projets
     * @return la collection des projets
     * @throws JrafEnterpriseException en cas d'échec
     */
    private Collection getAllProjects(List pAuditsList, ComponentDTO pApplication) throws JrafEnterpriseException {
        IApplicationComponent ac = AccessDelegateHelper.getInstance("Component");
        Object[] paramIn = { pApplication, null, (AuditDTO) pAuditsList.get(0), null };
        Collection projectCollection = ((Collection) ac.execute("getChildren", paramIn));
        return projectCollection;
    }

    /**
     * @param pAuditId l'id de l'audit
     * @param pProject le projet
     * @param pTaskName le nom de la tache
     * @throws JrafEnterpriseException en cas d'échec
     * @return les erreurs que la tache a provoqué
     */
    private Collection getErrorsByTask(String pAuditId, ComponentDTO pProject, String pTaskName) throws JrafEnterpriseException {
        IApplicationComponent ac = AccessDelegateHelper.getInstance("Error");
        ErrorDTO error = new ErrorDTO();
        error.setProjectId(pProject.getID());
        if (null != pAuditId) {
            error.setAuditId(Long.parseLong(pAuditId));
        }
        // on doit le mettre sous forme de listes
        List task = new ArrayList(0);
        task.add(pTaskName);
        Object[] paramIn3 = { task, error, null, null };
        Collection errorsCollection = ((Collection) ac.execute("getErrorsByTask", paramIn3));
        return errorsCollection;
    }

    /**
     * @param pAuditsList les audits
     * @param pProject le projet
     * @throws JrafEnterpriseException en cas d'échec
     * @return les erreurs que la tache a provoqué par niveau
     */
    private Integer[] getErrorsByProject(List pAuditsList, ComponentDTO pProject) throws JrafEnterpriseException {
        // on appelle l'IApplicationComponent avec l'argument d'appel
        // et son tableau de parametre
        IApplicationComponent ac = AccessDelegateHelper.getInstance("Results");
        Object[] paramIn = { new Long(((AuditDTO) pAuditsList.get(0)).getID()), new Long(pProject.getID())};
        return ((Integer[]) ac.execute("getErrorsRepartition", paramIn));
    }

    /**
     * @param pAuditsList les audits
     * @param pProject le projet
     * @throws JrafEnterpriseException en cas d'échec
     * @return les noms des tâches en échec
     */
    private List getFailedTasks(List pAuditsList, ComponentDTO pProject) throws JrafEnterpriseException {
        // on appelle l'IApplicationComponent avec l'argument d'appel
        // et son tableau de parametre
        IApplicationComponent ac = AccessDelegateHelper.getInstance("Results");
        Object[] paramIn = { new Long(((AuditDTO) pAuditsList.get(0)).getID()), new Long(pProject.getID())};
        return (List) ac.execute("getFailedTasks", paramIn);
    }

    /**
     * Permet de récupérer le dernier audit dans la session ou le charger par les AC
     * @param pRequest requete HTTP
     * @param pComponent ComponentDTO dont on souhaite les audits
     * @return Liste des derniers AuditDTOs, liste ne contenant qu'un seul élément à fortiori
     * @throws Exception exception
     */
    private List initAudit(HttpServletRequest pRequest, ComponentDTO pComponent) throws Exception {
        // récupère les audits dans la session
        return ActionUtils.getCurrentAuditsAsDTO(pRequest);
    }

    /**
     * liste toutes les taches du projet pour cet audit trié par nom de tâche
     * @param pProjectId l'id du projet
     * @param pAuditId l'id de l'audit
     * @return la liste des taches
     * @throws Exception en cas de problème lors de la récupération des taches
     */
    private Collection getTasks(long pProjectId, String pAuditId) throws Exception {
        IApplicationComponent ac = AccessDelegateHelper.getInstance("Error");
        Object[] param = { new Long(pProjectId), pAuditId};
        Collection tasks = ((Collection) ac.execute("getAllTasks", param));
        return tasks;
    }

}
