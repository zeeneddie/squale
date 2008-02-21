package com.airfrance.squaleweb.transformer.rulechecking;

import com.airfrance.squalecommon.datatransfertobject.rulechecking.CppTestRuleSetDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.rulechecking.CppTestRuleSetForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformations du formulaire de configuration CppTest
 */
public class CppTestRuleSetTransformer extends AbstractRuleSetTransformer {

    /**
     * @param pObject l'objet à transformer
     * @throws WTransformerException si un pb apparait.
     * @return le formulaire.
     */
    public WActionForm objToForm(Object[] pObject) throws WTransformerException {
        CppTestRuleSetForm form = new CppTestRuleSetForm();
        objToForm(pObject, form);
        return form;
    }

    /** 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[], com.airfrance.welcom.struts.bean.WActionForm)
     * {@inheritDoc}
     */
    public void objToForm(Object[] pObject, WActionForm pForm) throws WTransformerException {
        super.objToForm(pObject, pForm);
        // Prise en compte des informations spécifiques
        CppTestRuleSetForm form = (CppTestRuleSetForm) pForm;
        CppTestRuleSetDTO dto = (CppTestRuleSetDTO)pObject[0];
        form.setCppTestName(dto.getCppTestName());
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj(WActionForm pForm) throws WTransformerException {
        Object[] obj = {new CppTestRuleSetDTO()};
        formToObj(pForm, obj);
        return obj;
    }
    
}
