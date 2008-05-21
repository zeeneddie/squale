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
