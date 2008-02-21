package com.airfrance.squalecommon.datatransfertobject.transform.rule;

import com.airfrance.squalecommon.datatransfertobject.rule.CriteriumRuleDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.CriteriumRuleBO;

/**
 * Transformation d'un critère
 */
public class CriteriumRuleTransform {
    /**
     * Conversion
     * @param pCriteriumRule objet à convertir
     * @return résultat de la conversion
     */
    public static CriteriumRuleDTO bo2Dto(CriteriumRuleBO pCriteriumRule) {
        CriteriumRuleDTO result = new CriteriumRuleDTO();
        QualityRuleTransform.bo2Dto(result, pCriteriumRule);
        return result;
    }

}
