package com.airfrance.squalecommon.datatransfertobject.transform.rulechecking;

import com.airfrance.squalecommon.datatransfertobject.rulechecking.PmdRuleSetDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.pmd.PmdRuleSetBO;

/**
 * Transformation BO-DTO pour Pmd
 */
public class PmdTransform
{

    /**
     * Constructeur prive
     */
    private PmdTransform()
    {
    }

    /**
     * DTO -> BO pour un CppTestRuleSet
     * 
     * @return BO
     * @param pRuleSetDTO DTO
     */
    public static PmdRuleSetBO dto2Bo( PmdRuleSetDTO pRuleSetDTO )
    {
        PmdRuleSetBO bo = new PmdRuleSetBO();
        RuleSetTransform.dto2Bo( pRuleSetDTO, bo );
        bo.setLanguage( pRuleSetDTO.getLanguage() );
        return bo;
    }

    /**
     * BO -> DTO pour un RuleSet
     * 
     * @param pRuleSetBO BO
     * @return pRuleSetDTO DTO
     */
    public static PmdRuleSetDTO bo2Dto( PmdRuleSetBO pRuleSetBO )
    {
        PmdRuleSetDTO dto = new PmdRuleSetDTO();
        RuleSetTransform.bo2Dto( pRuleSetBO, dto );
        dto.setLanguage( pRuleSetBO.getLanguage() );
        return dto;
    }
}
