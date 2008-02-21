package com.airfrance.squalecommon.datatransfertobject.rulechecking;

/**
 * DTO pour le ruleset PMD
 */
public class PmdRuleSetDTO extends RuleSetDTO {

    /** Langage */
    private String mLanguage;
    
    /**
     * Access method for the mLanguage property.
     * @return language
     **/
    public String getLanguage() {
        return mLanguage;
    }
    
    /**
     * @param pLanguage langage
     */
    public void setLanguage(String pLanguage) {
        mLanguage = pLanguage;
    }
}
