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
