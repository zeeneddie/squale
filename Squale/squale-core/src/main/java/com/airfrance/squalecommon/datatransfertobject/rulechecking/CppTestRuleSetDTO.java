package com.airfrance.squalecommon.datatransfertobject.rulechecking;

/**
 * DTO pour CppTestRuleSet
 */
public class CppTestRuleSetDTO extends RuleSetDTO {

    /** Nom des règles CppTest */
    private String mCppTestName;
    /**
     * @return nom
     */
    public String getCppTestName() {
        return mCppTestName;
    }

    /**
     * @param pName nom
     */
    public void setCppTestName(String pName) {
        mCppTestName = pName;
    }
}
