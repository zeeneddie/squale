package com.airfrance.squaleweb.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.airfrance.squalecommon.datatransfertobject.rule.QualityGridConfDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.component.FactorListForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.FactorRuleForm;
import com.airfrance.squaleweb.applicationlayer.formbean.creation.GridConfigForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Transformation d'une grille qualit�
 */
public class GridConfTransformer implements WITransformer {

    /**
     * @param pObject l'objet � transformer
     * @throws WTransformerException si un pb apparait.
     * @return le formulaire.
     */
    public WActionForm objToForm(Object[] pObject) throws WTransformerException {
        GridConfigForm form = new GridConfigForm();
        objToForm(pObject, form);
        return form;
    }

    /**
     * @param pObject l'objet � transformer
     * @param pForm le formulaire � remplir.
     * @throws WTransformerException si un pb apparait.
     */
    public void objToForm(Object[] pObject, WActionForm pForm) throws WTransformerException {
        QualityGridConfDTO gridDTO = (QualityGridConfDTO)pObject[0];
        GridConfigForm form = (GridConfigForm)pForm;
        form.setId(gridDTO.getId());
        form.setName(gridDTO.getName());
        form.setUpdateDate(gridDTO.getUpdateDate());
        // Positionnement des facteurs
        Iterator factorsIt = gridDTO.getFactors().iterator();
        ArrayList factors = new ArrayList();
        while (factorsIt.hasNext()) {
            // Conversion de chacun des facteurs
            factors.add(WTransformerFactory.objToForm(FactorTransformer.class,factorsIt.next()));
        }
        FactorListForm factorForms = new FactorListForm();
        factorForms.setList(factors);
        form.setFactors(factorForms);
    }

    /**
     * @param pForm le formulaire � lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj(WActionForm pForm) throws WTransformerException {
        Object[] obj = {new QualityGridConfDTO()};
        formToObj(pForm, obj);
        return obj;
    }

    /**
     * @param pObject l'objet � remplir
     * @param pForm le formulaire � lire.
     * @throws WTransformerException si un pb apparait.
     */
    public void formToObj(WActionForm pForm, Object[] pObject) throws WTransformerException {
        GridConfigForm gridForm = (GridConfigForm)pForm;
        QualityGridConfDTO dto = (QualityGridConfDTO)pObject[0];
        dto.setId(gridForm.getId());
        dto.setName(gridForm.getName());
        // Positionnement des facteurs
        Iterator factors = gridForm.getFactors().getList().iterator();
        Collection factorDTO = new ArrayList();
        while (factors.hasNext()) {
            // Conversion de chacun des facteurs
            factorDTO.add(WTransformerFactory.formToObj(FactorTransformer.class, (FactorRuleForm)factors.next())[0]);
        }
        dto.setFactors(factorDTO);
    }

}