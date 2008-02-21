package com.airfrance.squaleweb.transformer;

import com.airfrance.squalecommon.datatransfertobject.component.ProfileDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ProfileForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformer ProfileDTO <-> ProfilForm
 * 
 * @author M400842
 *
 */
public class ProfileTransformer implements WITransformer{
   
    /**
     * @param pObject l'objet à transformer
     * @throws WTransformerException si un pb apparait.
     * @return le formulaire.
     */
    public WActionForm objToForm(Object[] pObject) throws WTransformerException {
        ProfileForm form = new ProfileForm();
        objToForm(pObject, form);
        return form;
    }

    /**
     * @param pObject l'objet à transformer
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparait.
     */
    public void objToForm(Object[] pObject, WActionForm pForm) throws WTransformerException {
        ProfileDTO dto = (ProfileDTO)pObject[0];
        ProfileForm form = (ProfileForm)pForm;
        form.setName(dto.getName());
        form.setRights(dto.getRights());
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj(WActionForm pForm) throws WTransformerException {
        Object[] obj = {new ProfileDTO()};
        formToObj(pForm, obj);
        return obj;
    }

    /**
     * @param pObject l'objet à remplir
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     */
    public void formToObj(WActionForm pForm, Object[] pObject) throws WTransformerException { 
        ProfileForm form = (ProfileForm)pForm;
        ProfileDTO dto = (ProfileDTO)pObject[0];
        dto.setName(form.getName());
        dto.setRights(form.getRights());
    }
}
