package com.airfrance.squaleweb.applicationlayer.action.grid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.CopyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.QualityGridDTO;
import com.airfrance.squalecommon.util.SqualeCommonConstants;
import com.airfrance.squalecommon.util.SqualeCommonUtils;
import com.airfrance.squalecommon.util.mail.MailerHelper;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.AdminAction;
import com.airfrance.squaleweb.applicationlayer.formbean.UploadFileForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.GridForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.GridListForm;
import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.squaleweb.transformer.GridListTransformer;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Importation des grilles qualité
 */
public class GridCreationAction extends AdminAction {

    /** Nom du formulaire permettant la confirmation */
    public static final String GRID_CONFIRM_FORM = "gridConfirmForm";
    /** Attribut du fichier de grille */
    static final private String GRID_FILE = "gridFile";

    /**
     * Importation d'un fichier de grille
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward importGrid(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {

        ActionErrors errors = new ActionErrors();
        ActionForward forward;
        try {
            UploadFileForm form = (UploadFileForm) pForm;
            IApplicationComponent ac = AccessDelegateHelper.getInstance("QualityGrid");
            StringBuffer importErrors = new StringBuffer();
            Collection grids = (Collection) ac.execute("importGrid", new Object[] { form.getInputStream(), importErrors });
            if (importErrors.length() > 0) {
                // Affichage des messages d'erreur
                // obtenus pendant l'importation
                ActionMessage error = new ActionMessage("grid_import.errors", importErrors.toString());
                errors.add(ActionMessages.GLOBAL_MESSAGE, error);
                forward = pMapping.findForward("fail");
            } else {
                // Sauvegarde du fichier d'upload dans la session
                File gridFile = File.createTempFile("squaleGrid", ".xml");
                InputStream is = form.getInputStream();
                FileOutputStream os = new FileOutputStream(gridFile);
                // Copie du fichier par la couche commons-io
                CopyUtils.copy(is, os);
                is.close();
                os.close();
                // Sauvegarde de l'emplacement de la copie du fichier uploadé
                pRequest.getSession().setAttribute(GRID_FILE, gridFile.getCanonicalPath());
                // Placement des information sur le formulaire de confirmation
                WActionForm gridListForm = WTransformerFactory.objToForm(GridListTransformer.class, grids);
                // en session car WIZARD
                pRequest.getSession().setAttribute(GRID_CONFIRM_FORM, gridListForm);
                // Indications des grilles existantes qui seront remplacées
                Iterator gridsIt = grids.iterator();
                while (gridsIt.hasNext()) {
                    QualityGridDTO grid = (QualityGridDTO) gridsIt.next();
                    // Si un id est associée à la grille, elle existe déjà dans la
                    // base -> dans ce cas on place un message indiquant le remplacement
                    // de la grille
                    if (grid.getId() != -1) {
                        ActionMessage error = new ActionMessage("grid_import.replace", grid.getName());
                        errors.add(ActionMessages.GLOBAL_MESSAGE, error);
                    } else {
                        ActionMessage error = new ActionMessage("grid_import.new", grid.getName());
                        errors.add(ActionMessages.GLOBAL_MESSAGE, error);
                    }
                }
                // Redirection vers la page de confirmation
                forward = pMapping.findForward("confirm");
            }
        } catch (Exception e) {
            // Traitement des exceptions
            handleException(e, errors, pRequest);
            forward = pMapping.findForward("total_failure");
        }
        if (!errors.isEmpty()) {
            saveMessages(pRequest, errors);
        }
        //On est passé par un menu donc on réinitialise le traceur
        resetTracker(pRequest);
        return forward;
    }

    /**
     * Confirmation du chargement de la grille
     * @param pMapping mapping
     * @param pForm form
     * @param pRequest requête
     * @param pResponse réponse
     * @return forward correspondant
     */
    public ActionForward confirmGrid(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {
        ActionForward forward;
        ActionErrors errors = new ActionErrors();
        // Récupération dy fichier contenant la grille à importer
        String gridName = (String) pRequest.getSession().getAttribute(GRID_FILE);
        File gridFile = null;
        if (gridName != null) {
            gridFile = new File(gridName);
        }
        pRequest.getSession().removeAttribute(GRID_FILE);
        if ((gridFile != null) && gridFile.exists()) {
            try {
                // On réalise l'importation du fichier qui a été copié au préalable
                IApplicationComponent ac = AccessDelegateHelper.getInstance("QualityGrid");
                StringBuffer importErrors = new StringBuffer();
                FileInputStream is = new FileInputStream(gridFile);
                // collection de QualityGridDTO
                Collection grids = (Collection) ac.execute("createGrid", new Object[] { is, importErrors });
                if (importErrors.length() > 0) {
                    // Affichage des messages d'erreur
                    ActionMessage error = new ActionMessage("grid_import.errors", importErrors.toString());
                    errors.add(ActionMessages.GLOBAL_MESSAGE, error);
                    forward = pMapping.findForward("fail");
                } else {
                    forward = pMapping.findForward("success_mail");
                    GridListForm gridListForm = (GridListForm) WTransformerFactory.objToForm(GridListTransformer.class, grids);
                    // en session car WIZARD
                    pRequest.getSession().setAttribute(GRID_CONFIRM_FORM, gridListForm);
                }
            } catch (Exception e) {
                // Traitement des exceptions
                handleException(e, errors, pRequest);
                forward = pMapping.findForward("total_failure");
            } finally {
                // Effacement du fichier temporaire
                gridFile.delete();
            }
        } else {
            // Ce cas peut se présenter si on est en mode cluster par exemple
            // et que le basculement s'opère à ce moment là
            ActionMessage error = new ActionMessage("grid_import.nofile");
            errors.add(ActionMessages.GLOBAL_MESSAGE, error);
            forward = pMapping.findForward("fail");
        }
        // Enregistrement des messages
        if (!errors.isEmpty()) {
            saveMessages(pRequest, errors);
        }
        //On est passé par un menu donc on réinitialise le traceur
        resetTracker(pRequest);
        return forward;
    }

    /**
     * Envoi d'un mail aux gestionnaires qui sont concernés par le changement de grille
     * @param pMapping mapping
     * @param pForm form
     * @param pRequest requête
     * @param pResponse réponse
     * @return forward correspondant
     */
     public ActionForward notify(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {
        ActionForward forward;
        ActionErrors errors = new ActionErrors();
        try {
            GridListForm list =  (GridListForm) pRequest.getSession().getAttribute(GRID_CONFIRM_FORM);
            Collection grids = list.getGrids();
            IApplicationComponent ac = AccessDelegateHelper.getInstance("Component");
            // Pour toutes les grilles qualités mises à jour, on envoie un mail à tous 
            // les managers de toutes les applications qui utilisent ces grilles 
            // pour leur signaler le changement
            Iterator gridsIt = grids.iterator();
            Collection projectsColl = new ArrayList(0);
            while (gridsIt.hasNext()) {
                GridForm gridForm =  (GridForm) gridsIt.next();
                String gridName = gridForm.getName();
                String adminText = gridForm.getAdminText();
                Object[] paramIn = new Object[] { gridName };
                projectsColl = ((Collection) ac.execute("findWhereQualityGrid", paramIn));
                // récupère la liste des applications sans doublons
                Collection appliColl = new ArrayList(0);
                Iterator projIt = projectsColl.iterator();
                while (projIt.hasNext()) {
                    ComponentDTO project = (ComponentDTO) projIt.next();
                    Long appliId = new Long(project.getIDParent());
                    if (!appliColl.contains(appliId)) {
                        // on enregistre pour dire qu'on a déjà envoyé le mail
                        appliColl.add(appliId);
                        // Envoi du mail
                        String[] params = { project.getName()};
                        String sender = WebMessages.getString(getLocale(pRequest),"mail.sender.squale");
                        String header = WebMessages.getString(getLocale(pRequest),"mail.header");
                        String object = sender + WebMessages.getString(pRequest,"mail.grid.change.object");
                        String content = header + (String) WebMessages.getString(pRequest.getLocale(), "mail.grid.change.content",params);
                        SqualeCommonUtils.notifyByEmail(MailerHelper.getMailerProvider(), SqualeCommonConstants.ONLY_MANAGERS, appliId, object, content, false);
                    }
                }
            }
            forward = pMapping.findForward("success");
        } catch (Exception e) {
            // Traitement des exceptions
            handleException(e, errors, pRequest);
            forward = pMapping.findForward("total_failure");
        } 
        return forward;
    }

    /**
     * Annulation du chargement de la grille
     * @param pMapping mapping
     * @param pForm form
     * @param pRequest requête
     * @param pResponse réponse
     * @return forward correspondant
     */
    public ActionForward cancelGrid(ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest, HttpServletResponse pResponse) {
        // Effacement du fichier temporaire de grille
        File gridFile = new File((String) pRequest.getSession().getAttribute(GRID_FILE));
        pRequest.getSession().removeAttribute(GRID_FILE);
        gridFile.delete();
        //On est passé par un menu donc on réinitialise le traceur
        resetTracker(pRequest);
        // Retour sur la liste des grilles
        return pMapping.findForward("success");
    }
}
