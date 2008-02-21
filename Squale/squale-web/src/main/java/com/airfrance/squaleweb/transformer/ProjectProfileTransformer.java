package com.airfrance.squaleweb.transformer;

import com.airfrance.squalecommon.datatransfertobject.config.ProjectProfileDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.config.ProjectProfileForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transforme ProjectProfileForm <-> ProjectProfileDTO
 */
public class ProjectProfileTransformer implements WITransformer {

    /**
     * @param pObject l'objet à transformer
     * @throws WTransformerException si un pb apparait.
     * @return le formulaire.
     */
    public WActionForm objToForm(Object[] pObject) throws WTransformerException {
        ProjectProfileForm form = new ProjectProfileForm();
        objToForm(pObject, form);
        return form;
    }

    /**
     * @param pObject l'objet à transformer
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparait.
     */
    public void objToForm(Object[] pObject, WActionForm pForm) throws WTransformerException {
        ProjectProfileDTO profileDTO = (ProjectProfileDTO)pObject[0];
        ProjectProfileForm form = (ProjectProfileForm)pForm;
        form.setId(profileDTO.getId());
        form.setName(profileDTO.getName());
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj(WActionForm pForm) throws WTransformerException {
        Object[] obj = {new ProjectProfileDTO()};
        formToObj(pForm, obj);
        return obj;
    }

    /**
    * @param pObject l'objet à remplir
    * @param pForm le formulaire à lire.
    * @throws WTransformerException si un pb apparait.
    */
    public void formToObj(WActionForm pForm, Object[] pObject) throws WTransformerException {
        ProjectProfileForm profileForm = (ProjectProfileForm)pForm;
        ProjectProfileDTO dto = (ProjectProfileDTO)pObject[0];
        dto.setId(Long.parseLong(profileForm.getId()));
        dto.setName(profileForm.getName());
    }

}
