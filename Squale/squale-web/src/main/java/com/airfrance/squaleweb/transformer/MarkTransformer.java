package com.airfrance.squaleweb.transformer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.result.ResultsDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.FactorRuleDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.PracticeRuleDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ComponentForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.MarkForm;
import com.airfrance.squaleweb.util.SqualeWebActionUtils;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformation des notes
 */
public class MarkTransformer implements WITransformer {

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[])
     * {@inheritDoc}
     */
    public WActionForm objToForm(Object[] object) throws WTransformerException {
        MarkForm result = new MarkForm();
        objToForm(object, result);
        return result;
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[], com.airfrance.welcom.struts.bean.WActionForm)
     * {@inheritDoc}
     */
    public void objToForm(Object[] object, WActionForm pForm) throws WTransformerException {
        // Récupération des paramètres
        MarkForm markForm = (MarkForm) pForm;
        int index = 0;
        ResultsDTO result = (ResultsDTO) object[index++];
        FactorRuleDTO factor = (FactorRuleDTO) object[index++];
        PracticeRuleDTO practice = (PracticeRuleDTO) object[index++];
        // Si il reste un seul élément, c'est une note
        if (object.length == index + 1) {
            markForm.setMarkValue(((Integer) object[index++]).intValue());
            markForm.setMinMark(-1);
            markForm.setMaxMark(-1);
        }
        // si il en reste deux , c'est un interval
        if (object.length == index + 2) {
            markForm.setMinMark(((Double) object[index++]).doubleValue());
            markForm.setMaxMark(((Double) object[index++]).doubleValue());
            markForm.setMarkValue(-1);
        }

        // Liste des tre
        List tre = (List) result.getResultMap().get(null);
        result.getResultMap().remove(null);
        // Liste des composants DTO
        LinkedList componentsDTO = new LinkedList();
        componentsDTO.addAll(result.getResultMap().keySet());
        // Liste des composants
        ArrayList components = new ArrayList();
        Iterator it = componentsDTO.listIterator();
        ComponentForm cform = null;
        ComponentDTO dto = null;
        // Parcours des composants renvoyés
        while (it.hasNext()) {
            dto = (ComponentDTO) it.next();
            // Création de la form associée et ajout
            cform = new ComponentForm();
            cform.setId(dto.getID());
            cform.setName(dto.getName());
            cform.setFullName(dto.getFullName());
            cform.getMetrics().addAll(SqualeWebActionUtils.getAsStringsList((List) result.getResultMap().get(dto)));
            components.add(cform);
        }
        // Mise en formulaire des valeurs
        markForm.setPracticeName(practice.getName());
        markForm.setPracticeId(practice.getId() + "");
        markForm.setTreNames(tre);
        markForm.setComponents(components);
        markForm.setFactorName(factor.getName());
        markForm.setFactorId(factor.getId() + "");

    }

    /** 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm)
     * {@inheritDoc}
     */
    public Object[] formToObj(WActionForm form) throws WTransformerException {
        throw new WTransformerException("not yet implemented");
    }

    /** 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm, java.lang.Object[])
     * {@inheritDoc}
     */
    public void formToObj(WActionForm form, Object[] object) throws WTransformerException {
        throw new WTransformerException("not yet implemented");
    }

}
