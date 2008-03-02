package com.airfrance.squalecommon.datatransfertobject.transform.rulechecking;

import com.airfrance.squalecommon.datatransfertobject.rulechecking.RuleSetDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.RuleSetBO;

/**
 * Transformation DTO-BO de RuleSet
 */
class RuleSetTransform
{

    /**
     * Constructeur prive
     */
    private RuleSetTransform()
    {
    }

    /**
     * DTO -> BO pour un RuleSet
     * 
     * @param pRuleSetBO BO
     * @param pRuleSetDTO DTO
     */
    public static void dto2Bo( RuleSetDTO pRuleSetDTO, RuleSetBO pRuleSetBO )
    {
        pRuleSetBO.setId( pRuleSetDTO.getId() );
        pRuleSetBO.setName( pRuleSetDTO.getName() );
        // On ne positionne pas la date
    }

    /**
     * BO -> DTO pour un RuleSet
     * 
     * @param pRuleSetBO BO
     * @param pRuleSetDTO DTO
     */
    public static void bo2Dto( RuleSetBO pRuleSetBO, RuleSetDTO pRuleSetDTO )
    {
        pRuleSetDTO.setId( pRuleSetBO.getId() );
        pRuleSetDTO.setName( pRuleSetBO.getName() );
        pRuleSetDTO.setDateOfUpdate( pRuleSetBO.getDateOfUpdate() );
    }
}
