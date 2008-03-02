package com.airfrance.squalecommon.datatransfertobject.transform.rule;

import com.airfrance.squalecommon.datatransfertobject.rule.FactorRuleDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO;

/**
 * Transformation d'un facteur
 */
public class FactorRuleTransform
{
    /**
     * Conversion
     * 
     * @param pFactorRule objet à convertir
     * @return résultat de la conversion
     */
    public static FactorRuleDTO bo2Dto( FactorRuleBO pFactorRule )
    {
        FactorRuleDTO result = new FactorRuleDTO();
        bo2Dto( result, pFactorRule );
        return result;
    }

    /**
     * Conversion
     * 
     * @param pFactorRule objet à convertir
     * @param pDto objet converti
     */
    public static void bo2Dto( FactorRuleDTO pDto, FactorRuleBO pFactorRule )
    {
        QualityRuleTransform.bo2Dto( pDto, pFactorRule );
    }

}
