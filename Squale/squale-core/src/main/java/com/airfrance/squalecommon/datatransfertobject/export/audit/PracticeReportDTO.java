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
package com.airfrance.squalecommon.datatransfertobject.export.audit;

import java.util.List;

import com.airfrance.squalecommon.datatransfertobject.rule.PracticeRuleDTO;
import com.airfrance.squalecommon.datatransfertobject.result.QualityResultDTO;

/**
 * A practice result
 */
public class PracticeReportDTO
    extends QualityReportDTO
{
    /**
     * Constructor
     * 
     * @param resultDTO its children
     */
    public PracticeReportDTO( QualityResultDTO resultDTO )
    {
        super( resultDTO );
        isRulechecking = ( (PracticeRuleDTO) resultDTO.getRule() ).isRuleChecking();
    }

    /**
     * top for this practice
     * Can be a list of transgression (if isRulechecking is true)
     * or a list of PracticeReportDetailedDTO
     */
    private List worstResults;

    /**
     * Indicate if the rule of the practice is a rulechecking type
     */
    private boolean isRulechecking;

    /**
     * Getter for top
     * 
     * @return top
     */
    public List getWorstResults()
    {
        return worstResults;
    }

    /**
     * Modify top
     * 
     * @param pWorstResults new top
     */
    public void setWorstResults( List pWorstResults )
    {
        worstResults = pWorstResults;
    }

    /**
     * Getter for isRulechecking propertie
     * 
     * @return true if the rule of the practice is a rulechecking type
     */
    public boolean isRulechecking()
    {
        return isRulechecking;
    }

}
