package com.airfrance.squalecommon.datatransfertobject.rule;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * DTO d'un facteur qualité
 */
public class FactorRuleDTO extends QualityRuleDTO {
    
    /** Criteres */
    private SortedMap mCriteria;
    
    /**
     * Ajout d'un critère
     * @param pCriterium critère
     * @param pWeight la pondération associée
     */
    public void addCriterium(CriteriumRuleDTO pCriterium, Float pWeight) {
        if (mCriteria==null) {
            mCriteria = new TreeMap();
        }
        mCriteria.put(pCriterium, pWeight);
    }
    
    /**
     * @return critères
     */
    public SortedMap getCriteria() {
        return mCriteria;
    }
}
