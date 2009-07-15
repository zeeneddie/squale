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
package org.squale.squalecommon.datatransfertobject.transform.rulechecking;

import org.squale.squalecommon.datatransfertobject.rulechecking.PmdRuleSetDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.pmd.PmdRuleSetBO;

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
