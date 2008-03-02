package com.airfrance.squalecommon.datatransfertobject.transform.component;

import com.airfrance.squalecommon.datatransfertobject.component.AuditGridDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.rule.QualityGridTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditGridBO;

/**
 * Conversion de BO en DTO
 */
public class AuditGridTransform
{
    /**
     * Conversion de BO en DTO
     * 
     * @param pAuditGridBO audit et grille
     * @return dto correspondant
     */
    public static AuditGridDTO bo2dto( AuditGridBO pAuditGridBO )
    {
        AuditGridDTO dto = new AuditGridDTO();
        dto.setGrid( QualityGridTransform.bo2Dto( pAuditGridBO.getGrid() ) );
        dto.setProject( ComponentTransform.bo2Dto( pAuditGridBO.getProject() ) );
        dto.setAudit( AuditTransform.bo2Dto( pAuditGridBO.getAudit(), -1 ) );
        return dto;
    }
}
