package com.airfrance.squaleweb.applicationlayer.formbean.rulechecking;

/**
 * Formulaire d'un ruleset PMD
 */
public class PmdRuleSetForm extends AbstractRuleSetForm {
    /** Langage (java ou jsp) */
    private String mLanguage;
    
    /**
     * 
     * @param pLanguage langage
     */
    public void setLanguage(String pLanguage) {
        mLanguage = pLanguage;
    }
    
    /**
     * 
     * @return langage
     */
    public String getLanguage() {
        return mLanguage;
    }
}
