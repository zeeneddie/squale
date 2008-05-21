package com.airfrance.squalecommon.datatransfertobject.export.audit;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.squalecommon.datatransfertobject.result.QualityResultDTO;

/**
 * A quality result
 */
public class QualityReportDTO
    extends QualityResultDTO
{
    /**
     * List of children
     */
    protected List qualityResults = new ArrayList();

    /**
     * its score for the previous audit
     */
    protected float previousScore = -1;

    // TODO : Waiting for average referential specification.
    // private float mAverageScore;
    // private float mAverageScoreVol;

    /**
     * Constructor
     * 
     * @param resultDTO its children
     */
    public QualityReportDTO( QualityResultDTO resultDTO )
    {
        mAudit = resultDTO.getAudit();
        mMeanMark = resultDTO.getMeanMark();
        mProject = resultDTO.getProject();
        mRule = resultDTO.getRule();
        previousScore = -1;
    }

    /**
     * Getter for previous score
     * 
     * @return previous score
     */
    public float getPreviousScore()
    {
        return previousScore;
    }

    /**
     * Modify previous score
     * 
     * @param pPreviousScore new score
     */
    public void setPreviousScore( float pPreviousScore )
    {
        previousScore = pPreviousScore;
    }

    /**
     * Get its children results
     * 
     * @return children results
     */
    public List getQualityResults()
    {
        return qualityResults;
    }

    /**
     * Modify its children
     * 
     * @param pQualityResults new children
     */
    public void setQualityResults( List pQualityResults )
    {
        this.qualityResults = pQualityResults;
    }

    /**
     * Add a child result
     * 
     * @param qualityReport result to add
     */
    public void addResult( QualityReportDTO qualityReport )
    {
        qualityResults.add( qualityReport );

    }
}