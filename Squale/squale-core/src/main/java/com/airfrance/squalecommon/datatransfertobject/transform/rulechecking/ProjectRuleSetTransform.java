package com.airfrance.squalecommon.datatransfertobject.transform.rulechecking;

import com.airfrance.squalecommon.datatransfertobject.rulechecking.ProjectRuleSetDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.component.ComponentTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.ProjectRuleSetBO;

/**
 * Transformation DTO-BO de ProjectRuleSet
 */
public class ProjectRuleSetTransform
{

    /**
     * Constructeur prive
     */
    private ProjectRuleSetTransform()
    {

    }

    /**
     * BO -> DTO pour un RuleSet
     * 
     * @param pRuleSetBO BO
     * @return pRuleSetDTO DTO
     */
    public static ProjectRuleSetDTO bo2Dto( ProjectRuleSetBO pRuleSetBO )
    {
        ProjectRuleSetDTO dto = new ProjectRuleSetDTO();
        RuleSetTransform.bo2Dto( pRuleSetBO, dto );
        dto.setProject( ComponentTransform.bo2Dto( pRuleSetBO.getProject() ) );
        return dto;
    }

}
