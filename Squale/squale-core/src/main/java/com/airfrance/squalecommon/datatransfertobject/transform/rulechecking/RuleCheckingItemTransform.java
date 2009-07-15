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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.airfrance.squalecommon.datatransfertobject.rulechecking.RuleCheckingItemDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.component.ComponentTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking.RuleCheckingTransgressionItemBO;

/**
 * Transforme un item de transgression en dto <-> bo.
 */
public class RuleCheckingItemTransform
{

    /**
     * Constructeur privé
     */
    private RuleCheckingItemTransform()
    {
    }

    /**
     * BO -> DTO pour un item
     * 
     * @param pItemBO BO
     * @return DTO
     */
    public static RuleCheckingItemDTO bo2Dto( RuleCheckingTransgressionItemBO pItemBO )
    {
        RuleCheckingItemDTO dto = new RuleCheckingItemDTO();
        if ( null != pItemBO.getComponent() )
        {
            dto.setComponent( ComponentTransform.bo2Dto( pItemBO.getComponent() ) );
        }
        if ( null != pItemBO.getComponentInvolved() )
        {
            dto.setComponentInvolved( ComponentTransform.bo2Dto( pItemBO.getComponentInvolved() ) );
        }
        dto.setMessage( pItemBO.getMessage() );
        dto.setRuleCode( pItemBO.getRule().getCode() );
        dto.setRuleSeverity( pItemBO.getRule().getSeverity() );
        dto.setComponentFile( pItemBO.getComponentFile() );
        dto.setLine( pItemBO.getLine() );
        return dto;
    }

    /**
     * Transforme une liste de BOs en DTOs
     * 
     * @param pItemsBO la liste des BOs à transformer
     * @return la liste des DTOs
     */
    public static Collection bo2Dto( Collection pItemsBO )
    {
        Collection itemsDTO = new ArrayList();
        for ( Iterator it = pItemsBO.iterator(); it.hasNext(); )
        {
            RuleCheckingTransgressionItemBO itemBO = (RuleCheckingTransgressionItemBO) it.next();
            itemsDTO.add( bo2Dto( itemBO ) );
        }
        return itemsDTO;
    }

}
