package com.airfrance.squaleweb.transformer.component.parameters;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import com.airfrance.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.rulechecking.CppTestRuleSetDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.CppTestForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Conversion des informations du formulaire de configuration CppTest
 */
public class CppTestProjectConfTransformer implements WITransformer {

    /**
     * @param pObject l'objet à transformer
     * @throws WTransformerException si un pb apparait.
     * @return le formulaire.
     */
    public WActionForm objToForm(Object[] pObject) throws WTransformerException {
        CppTestForm form = new CppTestForm();
        objToForm(pObject, form);
        return form;
    }

    /**
     * @param pObject l'objet à transformer
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparait.
     */
    public void objToForm(Object[] pObject, WActionForm pForm) throws WTransformerException {
        // Récupération des paramètres
        MapParameterDTO projetParams = (MapParameterDTO) pObject[0];
        // Paramètres CppTest
        MapParameterDTO params = (MapParameterDTO) projetParams.getParameters().get(ParametersConstants.CPPTEST);
        Collection rulesets = (Collection) pObject[1];

        // On remplit le form
        CppTestForm cppTestForm = (CppTestForm) pForm;
        // Récupération des rulesets
        HashSet set = new HashSet();
        for (Iterator it = rulesets.iterator(); it.hasNext();) {
            CppTestRuleSetDTO dto = (CppTestRuleSetDTO) it.next();
            set.add(dto.getName());
        }
        cppTestForm.setRuleSets(set);
        if (params != null) {
            // Récupération du RuleSet
            StringParameterDTO ruleSetName = (StringParameterDTO) params.getParameters().get(ParametersConstants.CPPTEST_RULESET_NAME);
            // Ce cas est peu probable - il s'agirait d'un oubli en base de données
            if (ruleSetName != null) {
                cppTestForm.setSelectedRuleSet(ruleSetName.getValue());
            }
            // Récupération du script CppTest
            StringParameterDTO script = (StringParameterDTO) params.getParameters().get(ParametersConstants.CPPTEST_SCRIPT);
            // Ce cas est peu probable - il s'agirait d'un oubli en base de données
            if (script != null) {
                cppTestForm.setScript(script.getValue());
            }
        }
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj(WActionForm pForm) throws WTransformerException {
        Object[] obj = { new MapParameterDTO()};
        formToObj(pForm, obj);
        return obj;
    }

    /**
     * @param pObject l'objet à remplir
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     */
    public void formToObj(WActionForm pForm, Object[] pObject) throws WTransformerException {
        CppTestForm cppTestForm = (CppTestForm) pForm;
        MapParameterDTO params = (MapParameterDTO) pObject[0];
        // Paramètres CppTest
        MapParameterDTO cppTestParams = new MapParameterDTO();
        // Traitement du ruleSet
        StringParameterDTO selectedRuleSet = new StringParameterDTO();
        selectedRuleSet.setValue(cppTestForm.getSelectedRuleSet());
        cppTestParams.getParameters().put(ParametersConstants.CPPTEST_RULESET_NAME, selectedRuleSet);
        // Traitement du script CppTest
        StringParameterDTO script = new StringParameterDTO();
        script.setValue(cppTestForm.getScript());
        cppTestParams.getParameters().put(ParametersConstants.CPPTEST_SCRIPT, script);
        params.getParameters().put(ParametersConstants.CPPTEST, cppTestParams);
    }

}
