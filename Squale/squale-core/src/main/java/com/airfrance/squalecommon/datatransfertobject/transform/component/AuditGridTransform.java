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
