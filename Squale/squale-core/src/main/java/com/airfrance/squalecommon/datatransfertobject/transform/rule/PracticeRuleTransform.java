package com.airfrance.squalecommon.datatransfertobject.transform.rule;

import com.airfrance.squalecommon.datatransfertobject.rule.PracticeRuleDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO;

/**
 * Transformation d'une pratique
 */
public class PracticeRuleTransform
{
    /**
     * Conversion
     * 
     * @param pPracticeRule objet à convertir
     * @return résultat de la conversion
     */
    public static PracticeRuleDTO bo2Dto( PracticeRuleBO pPracticeRule )
    {
        PracticeRuleDTO result = new PracticeRuleDTO();
        QualityRuleTransform.bo2Dto( result, pPracticeRule );
        return result;
    }

}
