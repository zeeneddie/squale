package com.airfrance.squaleweb.transformer.rulechecking;

import com.airfrance.squaleweb.applicationlayer.formbean.rulechecking.CheckstyleRuleSetListForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformation d'une liste de jeux de règles checkstyle
 */
public class CheckStyleRuleSetListTransformer extends AbstractRuleSetListTransformer {

    /**
     * Constructeur
     */
    public CheckStyleRuleSetListTransformer() {
        super(CheckstyleRuleSetTransformer.class);
    }

    /**
     * @param pObject le tableau de CheckstyleDTO à transformer en formulaires.
     * @throws WTransformerException si un pb apparaît.
     * @return le formulaire associé
     */
    public WActionForm objToForm(Object[] pObject) throws WTransformerException {
        CheckstyleRuleSetListForm form = new CheckstyleRuleSetListForm();
        objToForm(pObject, form);
        return form;
    }

}
