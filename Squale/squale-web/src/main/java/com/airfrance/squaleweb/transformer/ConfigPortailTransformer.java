package com.airfrance.squaleweb.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.airfrance.squalecommon.datatransfertobject.config.ProjectProfileDTO;
import com.airfrance.squalecommon.datatransfertobject.config.SourceManagementDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.config.ConfigPortailForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Transforme ConfigPortailForm <-> ProjectProfileDTO et SourceManagementDTO
 */
public class ConfigPortailTransformer extends AbstractListTransformer {

    /**
     * @param pObject l'objet SqualixConfigurationDTO à transformer en formulaire.
     * @throws WTransformerException si un pb apparaît.
     * @return le formulaire associé
     */
    public WActionForm objToForm(Object[] pObject) throws WTransformerException {
        ConfigPortailForm form = new ConfigPortailForm();
        objToForm(pObject, form);
        return form;
    }

    /**
     * @param pObject l'objet SqualixConfigurationDTO à transformer en formulaire.
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparaît.
     */
    public void objToForm(Object[] pObject, WActionForm pForm) throws WTransformerException {
        Collection listProfilesDTO = (Collection) pObject[0];
        Collection listManagersDTO = (Collection) pObject[1];
        ConfigPortailForm configForm = (ConfigPortailForm) pForm;
        ArrayList listProfilesForm = new ArrayList();
        ArrayList listManagersForm = new ArrayList();
        Iterator itProfiles = listProfilesDTO.iterator();
        ProjectProfileDTO profileDTO = null;
        while (itProfiles.hasNext()) {
            profileDTO = (ProjectProfileDTO) itProfiles.next();
            listProfilesForm.add(WTransformerFactory.objToForm(ProjectProfileTransformer.class, profileDTO));

        }
        configForm.setProfiles(listProfilesForm);
        Iterator itManagers = listManagersDTO.iterator();
        SourceManagementDTO managerDTO = null;
        while (itManagers.hasNext()) {
            managerDTO = (SourceManagementDTO) itManagers.next();
            listManagersForm.add(WTransformerFactory.objToForm(SourceManagementTransformer.class, managerDTO));
        }
        configForm.setSourceManagements(listManagersForm);
    }

    /**
     * Méthode générée
     * 
     * @param pForm le formulaire à transformer.
     * @param pObject l'objet à remplir 
     * @throws WTransformerException si un pb apparaît.
     */
    public void formToObj(WActionForm pForm, Object[] pObject) throws WTransformerException {
     }
}
