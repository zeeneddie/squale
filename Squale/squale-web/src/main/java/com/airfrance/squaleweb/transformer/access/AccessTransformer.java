package com.airfrance.squaleweb.transformer.access;

import com.airfrance.squalecommon.datatransfertobject.access.UserAccessDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.access.AccessForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformer pour les accès utilisateur
 */
public class AccessTransformer  implements WITransformer {

    /** 
     * {@inheritDoc}
     * @param pObject {@inheritDoc}
     * @return {@inheritDoc}
     * @throws WTransformerException {@inheritDoc}
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[])
     */
    public WActionForm objToForm(Object[] pObject) throws WTransformerException {
        AccessForm form = new AccessForm();
        objToForm(pObject, form);
        return form;
    }

    /** 
     * {@inheritDoc}
     * @param pObject {@inheritDoc}
     * @param pForm {@inheritDoc}
     * @throws WTransformerException {@inheritDoc}
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[], com.airfrance.welcom.struts.bean.WActionForm)
     */
    public void objToForm(Object[] pObject, WActionForm pForm) throws WTransformerException {
        UserAccessDTO dto = (UserAccessDTO) pObject[0];
        AccessForm form = (AccessForm) pForm;
        form.setDate(dto.getDate());
        form.setMatricule(dto.getMatricule());
    }

    /** 
     * {@inheritDoc}
     * @param pForm {@inheritDoc}
     * @return {@inheritDoc}
     * @throws WTransformerException {@inheritDoc}
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm)
     */
    public Object[] formToObj(WActionForm pForm) throws WTransformerException {
        throw new WTransformerException("not implemented");
    }

    /** 
     * {@inheritDoc}
     * @param pForm {@inheritDoc}
     * @param pObject {@inheritDoc}
     * @throws WTransformerException {@inheritDoc}
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm, java.lang.Object[])
     */
    public void formToObj(WActionForm pForm, Object[] pObject) throws WTransformerException {
        throw new WTransformerException("not implemented");
    }

}
