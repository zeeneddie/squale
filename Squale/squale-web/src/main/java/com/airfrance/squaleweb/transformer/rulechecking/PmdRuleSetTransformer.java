package com.airfrance.squaleweb.transformer.rulechecking;

import com.airfrance.squalecommon.datatransfertobject.rulechecking.PmdRuleSetDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.rulechecking.PmdRuleSetForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformation d'un ruleset Pmd
 */
public class PmdRuleSetTransformer extends AbstractRuleSetTransformer {

    /**
     * @param pObject l'objet à transformer
     * @throws WTransformerException si un pb apparait.
     * @return le formulaire.
     */
    public WActionForm objToForm(Object[] pObject) throws WTransformerException {
        PmdRuleSetForm form = new PmdRuleSetForm();
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
        PmdRuleSetForm form = (PmdRuleSetForm) pForm;
        PmdRuleSetDTO dto = (PmdRuleSetDTO)pObject[0];
        form.setLanguage(dto.getLanguage());
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj(WActionForm pForm) throws WTransformerException {
        Object[] obj = {new PmdRuleSetDTO()};
        formToObj(pForm, obj);
        return obj;
    }
}
