/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
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
