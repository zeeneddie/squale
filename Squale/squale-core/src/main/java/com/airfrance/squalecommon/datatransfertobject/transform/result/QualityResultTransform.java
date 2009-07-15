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
package com.airfrance.squalecommon.datatransfertobject.transform.result;

import com.airfrance.squalecommon.datatransfertobject.result.QualityResultDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.component.AuditTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.component.ComponentTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.rule.QualityRuleTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.PracticeResultBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.QualityResultBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO;

/**
 * Transformer QualityresultBO <--> QualityresultDTO
 */
public final class QualityResultTransform

{

    /**
     * Private constructor
     */
    private QualityResultTransform()
    {

    }

    /**
     * Transform a QualityresultBO in a QualityresultDTO
     * 
     * @param pQualityResultBO The BO to transform
     * @return a QualityresultDTO
     */
    public static QualityResultDTO bo2Dto( QualityResultBO pQualityResultBO )
    {
        QualityResultDTO qualityResultDTO = new QualityResultDTO();
        qualityResultDTO.setId( pQualityResultBO.getId() );
        qualityResultDTO.setMeanMark( pQualityResultBO.getMeanMark() );
        if ( pQualityResultBO.getAudit() != null )
        {
            qualityResultDTO.setAudit( AuditTransform.bo2Dto( pQualityResultBO.getAudit() ) );
        }
        qualityResultDTO.setProject( ComponentTransform.bo2Dto( pQualityResultBO.getProject() ) );
        qualityResultDTO.setRule( QualityRuleTransform.bo2Dto( pQualityResultBO.getRule(), true ) );
        qualityResultDTO.setCreationDate( pQualityResultBO.getCreationDate() );
        return qualityResultDTO;
    }

    /**
     * Transform a QualityresultDTO in a QualityresultBO
     * 
     * @param resultDto The dto to transform
     * @return a QualityresultBO
     */
    public static QualityResultBO simplifyDto2Bo( QualityResultDTO resultDto )
    {
        PracticeResultBO bo = new PracticeResultBO();
        bo.setId( resultDto.getId() );
        bo.setMeanMark( resultDto.getMeanMark() );
        bo.setCreationDate( resultDto.getCreationDate() );

        ProjectBO project = new ProjectBO();
        project.setId( resultDto.getProject().getID() );
        bo.setProject( project );

        PracticeRuleBO rule = new PracticeRuleBO();
        rule.setId( resultDto.getRule().getId() );
        bo.setRule( rule );
        return bo;
    }

}
