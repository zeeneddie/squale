package com.airfrance.squaleweb.transformer;

import com.airfrance.squalecommon.datatransfertobject.rule.AbstractFormulaDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.PracticeRuleDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.component.FormulaForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.PracticeRuleForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Transformation d'une pratique
 */
public class PracticeTransformer implements WITransformer {

    /**
     * @param pObject l'objet à transformer
     * @throws WTransformerException si un pb apparait.
     * @return le formulaire.
     */
    public WActionForm objToForm(Object[] pObject) throws WTransformerException {
        PracticeRuleForm form = new PracticeRuleForm();
        objToForm(pObject, form);
        return form;
    }

    /**
     * @param pObject l'objet à transformer
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparait.
     */
    public void objToForm(Object[] pObject, WActionForm pForm) throws WTransformerException {
        PracticeRuleDTO practiceDTO = (PracticeRuleDTO) pObject[0];
        PracticeRuleForm form = (PracticeRuleForm) pForm;
        form.setId(practiceDTO.getId());
        form.setName(practiceDTO.getName());
        form.setEffort(practiceDTO.getEffort());
        if (practiceDTO.getFormula() != null) {
            form.setFormula((FormulaForm) WTransformerFactory.objToForm(FormulaTransformer.class, practiceDTO.getFormula()));
        }
        if (practiceDTO.getWeightingFunction() != null) {
            form.setWeightingFunction(practiceDTO.getWeightingFunction());
        }
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj(WActionForm pForm) throws WTransformerException {
        PracticeRuleDTO dto = new PracticeRuleDTO();
        formToObj(pForm, new Object[] { dto });
        return new Object[] { dto };
    }

    /**
     * @param pObject l'objet à remplir
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     */
    public void formToObj(WActionForm pForm, Object[] pObject) throws WTransformerException { 
        PracticeRuleForm form = (PracticeRuleForm) pForm;
        PracticeRuleDTO dto = (PracticeRuleDTO) pObject[0];
        dto.setId(form.getId());
        dto.setName(form.getName());
        dto.setEffort(form.getEffort());
        if (form.getFormula() != null) {
            dto.setFormula((AbstractFormulaDTO) WTransformerFactory.formToObj(FormulaTransformer.class, form.getFormula())[0]);
        }
        if (form.getWeightingFunction() != null) {
            dto.setWeightingFunction(form.getWeightingFunction());
        }
    }

}
