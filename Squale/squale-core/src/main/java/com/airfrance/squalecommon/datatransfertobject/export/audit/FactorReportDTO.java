package com.airfrance.squalecommon.datatransfertobject.export.audit;

import com.airfrance.squalecommon.datatransfertobject.result.QualityResultDTO;

/**
 * Factor result
 */
public class FactorReportDTO
    extends QualityReportDTO
{

    /**
     * Constructor
     * 
     * @param resultDTO criteria
     */
    public FactorReportDTO( QualityResultDTO resultDTO )
    {
        super( resultDTO );
    }
}
