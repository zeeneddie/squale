package com.airfrance.squaleweb.transformer;

import com.airfrance.squalecommon.datatransfertobject.rulechecking.RuleCheckingItemDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.results.RuleCheckingItemForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transforme un item de transgression.
 */
public class RuleCheckingItemTransformer implements WITransformer {

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[])
     * {@inheritDoc}
     */
    public WActionForm objToForm(Object[] pObject) throws WTransformerException {
        RuleCheckingItemForm form = new RuleCheckingItemForm();
        objToForm(pObject, form);
        return form;
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[], com.airfrance.welcom.struts.bean.WActionForm)
     * {@inheritDoc}
     */
    public void objToForm(Object[] pObject, WActionForm pForm) throws WTransformerException {
        RuleCheckingItemDTO dto = (RuleCheckingItemDTO) pObject[0];
        RuleCheckingItemForm form = (RuleCheckingItemForm) pForm;
        form.setMessage(dto.getMessage());
        if (null != dto.getComponent()) {
            form.setComponentId(dto.getComponent().getID());
            form.setComponentName(dto.getComponent().getName());
        }
        if (null != dto.getComponentInvolved()) {
            form.setComponentInvolvedId(dto.getComponentInvolved().getID());
            form.setComponentInvolvedName(dto.getComponentInvolved().getName());
        }
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm)
     * {@inheritDoc}
     */
    public Object[] formToObj(WActionForm pForm) throws WTransformerException {
        throw new WTransformerException("not yet implemented");
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm, java.lang.Object[])
     * {@inheritDoc}
     */
    public void formToObj(WActionForm arg0, Object[] arg1) throws WTransformerException {
        throw new WTransformerException("not yet implemented");
    }

}
