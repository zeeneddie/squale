package com.airfrance.squalecommon.datatransfertobject.transform.result;

import com.airfrance.squalecommon.datatransfertobject.result.QualityResultDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.component.AuditTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.component.ComponentTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.rule.QualityRuleTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.QualityResultBO;

/**
 * bo <--> dto pour les résultats qualités
 */
public class QualityResultTransform
{

    /**
     * @param pQualityResultBO le bo
     * @return le dto
     */
    public static QualityResultDTO bo2Dto( QualityResultBO pQualityResultBO )
    {
        QualityResultDTO qualityResultDTO = new QualityResultDTO();
        qualityResultDTO.setMeanMark( pQualityResultBO.getMeanMark() );
        qualityResultDTO.setAudit( AuditTransform.bo2Dto( pQualityResultBO.getAudit() ) );
        qualityResultDTO.setProject( ComponentTransform.bo2Dto( pQualityResultBO.getProject() ) );
        qualityResultDTO.setRule( QualityRuleTransform.bo2Dto( pQualityResultBO.getRule(), true ) );
        return qualityResultDTO;
    }

}
