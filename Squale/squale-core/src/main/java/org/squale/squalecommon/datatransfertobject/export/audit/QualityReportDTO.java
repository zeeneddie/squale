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
package org.squale.squalecommon.datatransfertobject.export.audit;

import java.util.ArrayList;
import java.util.List;

import org.squale.squalecommon.datatransfertobject.result.QualityResultDTO;

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