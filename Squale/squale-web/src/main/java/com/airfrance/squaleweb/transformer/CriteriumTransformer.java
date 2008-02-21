package com.airfrance.squaleweb.transformer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

import com.airfrance.squalecommon.datatransfertobject.rule.CriteriumRuleDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.PracticeRuleDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.component.CriteriumRuleForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.PracticeRuleForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.QualityRuleForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Transformation d'un critère
 */
public class CriteriumTransformer implements WITransformer {

    /**
     * @param pObject l'objet à transformer
     * @throws WTransformerException si un pb apparait.
     * @return le formulaire.
     */
    public WActionForm objToForm(Object[] pObject) throws WTransformerException {
        CriteriumRuleForm form = new CriteriumRuleForm();
        objToForm(pObject, form);
        return form;
    }

    /**
     * @param pObject l'objet à transformer
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparait.
     */
    public void objToForm(Object[] pObject, WActionForm pForm) throws WTransformerException {
        CriteriumRuleDTO criteriumDTO = (CriteriumRuleDTO) pObject[0];
        CriteriumRuleForm form = (CriteriumRuleForm) pForm;
        form.setId(criteriumDTO.getId());
        form.setName(criteriumDTO.getName());
        String ponderation = "";
        form.setPonderation(ponderation);
        // Traitement de chaque pratique
        Iterator practicesIt = criteriumDTO.getPractices().keySet().iterator();
        while (practicesIt.hasNext()) {
            PracticeRuleDTO currentDTO = (PracticeRuleDTO) practicesIt.next();
            form.addPractice((PracticeRuleForm) WTransformerFactory.objToForm(PracticeTransformer.class, currentDTO));
            ponderation += QualityRuleForm.SEPARATOR + ((Float) criteriumDTO.getPractices().get(currentDTO)).toString();
        }
        if (ponderation.length() > 2) {
            // On supprime la première virgule inutile
            ponderation = ponderation.substring(2);
        }
        form.setPonderation(ponderation);
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj(WActionForm pForm) throws WTransformerException {
        CriteriumRuleDTO dto = new CriteriumRuleDTO();
        formToObj(pForm,new Object[] {dto} );
        return new Object[] {dto};
    }

    /**
     * @param pObject l'objet à remplir
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     */
    public void formToObj(WActionForm pForm, Object[] pObject) throws WTransformerException {
        CriteriumRuleForm form = (CriteriumRuleForm) pForm;
        CriteriumRuleDTO dto = (CriteriumRuleDTO) pObject[0];
        dto.setId(form.getId());
        dto.setName(form.getName());
        ArrayList practices = (ArrayList) form.getPractices().getList();
        SortedMap practicesMap = new TreeMap();
        String ponderation = form.getPonderation();
        // on découpe les pondérations qui sont sous la forme d'une String dans le form
        // les valeurs sont séparés par une ", " (QualityRuleForm.SEPARATOR)
        StringTokenizer st = new StringTokenizer(ponderation, QualityRuleForm.SEPARATOR);
        // Si le nombre de pondérations est différent du nombre de critères
        // on lance une exception
        if(st.countTokens() != practices.size()) {
            throw new WTransformerException();
        }
        int i = 0;
        while(st.hasMoreTokens()){
            PracticeRuleForm practiceForm = (PracticeRuleForm) practices.get(i++);
            PracticeRuleDTO practiceDTO = (PracticeRuleDTO)WTransformerFactory.formToObj(PracticeTransformer.class, practiceForm)[0];
            dto.addPractice(practiceDTO, new Float(Float.parseFloat((String) st.nextElement())));
        }
    }

}
