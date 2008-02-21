package com.airfrance.squaleweb.transformer;

import com.airfrance.squalecommon.datatransfertobject.config.ServeurDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.config.ServeurForm;

import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformer pour un serveur d'exécution de Squalix
 */
public class ServeurTransformer implements WITransformer {

    /** 
     * {@inheritDoc}
     * @param form le formulaire
     * @return le tableau d'objets transformée
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[])
     */
    public Object[] formToObj(WActionForm form) throws WTransformerException {
        Object[] obj = {new ServeurDTO()};
        formToObj(form, obj);
        return obj;
    }

    /** 
     * {@inheritDoc}
     * @param arg0 le tableau des objets nécessaires à la transformation
     * @return le formulaire transformé
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[])
     */
    public WActionForm objToForm(Object[] arg0) throws WTransformerException {
        ServeurForm form = new ServeurForm();
        objToForm(arg0, form);
        return form;
    }

    /** 
     * {@inheritDoc}
     * @param arg0 {@inheritDoc} - le serveur sous forme DTO
     * @param arg1 le formulaire serveur
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[], com.airfrance.welcom.struts.bean.WActionForm)
     */
    public void objToForm(Object[] arg0, WActionForm arg1) throws WTransformerException {
        ServeurForm form = (ServeurForm) arg1;
        ServeurDTO dto = (ServeurDTO) arg0[0];
        form.setServeurId(dto.getServeurId());
        form.setName(dto.getName());
    }
    
    /** {@inheritDoc}
    * @param arg0 {@inheritDoc}
    * @param arg1 {@inheritDoc}
    * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm, java.lang.Object[])
    */
    public void formToObj(WActionForm arg0, Object[] arg1) throws WTransformerException {
        ServeurForm form = (ServeurForm) arg0;
        ServeurDTO dto = (ServeurDTO) arg1[0];
        dto.setServeurId(form.getServeurId());
        dto.setName(form.getName());

    }




}
