package com.airfrance.squalecommon.datatransfertobject.transform.rulechecking;

import com.airfrance.squalecommon.datatransfertobject.rulechecking.CppTestRuleSetDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.cpptest.CppTestRuleSetBO;

/**
 * Transformation DTO-BO de RuleSet
 */
public class CppTestRuleSetTransform
{

    /**
     * Constructeur prive
     */
    private CppTestRuleSetTransform()
    {

    }

    /**
     * DTO -> BO pour un CppTestRuleSet
     * 
     * @return BO
     * @param pRuleSetDTO DTO
     */
    public static CppTestRuleSetBO dto2Bo( CppTestRuleSetDTO pRuleSetDTO )
    {
        CppTestRuleSetBO bo = new CppTestRuleSetBO();
        RuleSetTransform.dto2Bo( pRuleSetDTO, bo );
        bo.setCppTestName( pRuleSetDTO.getCppTestName() );
        return bo;
    }

    /**
     * BO -> DTO pour un RuleSet
     * 
     * @param pRuleSetBO BO
     * @return pRuleSetDTO DTO
     */
    public static CppTestRuleSetDTO bo2Dto( CppTestRuleSetBO pRuleSetBO )
    {
        CppTestRuleSetDTO dto = new CppTestRuleSetDTO();
        RuleSetTransform.bo2Dto( pRuleSetBO, dto );
        dto.setCppTestName( pRuleSetBO.getCppTestName() );
        return dto;
    }

}
