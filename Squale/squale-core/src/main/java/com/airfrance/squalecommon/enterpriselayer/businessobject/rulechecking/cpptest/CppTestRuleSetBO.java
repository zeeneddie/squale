package com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.cpptest;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.RuleSetBO;

/**
 * RuleSet de CppTest
 * @hibernate.subclass
 * discriminator-value="CppTest"
 */
public class CppTestRuleSetBO extends RuleSetBO {
    /** Nom des règles CppTest */
    private String mCppTestName;
    /**
     * @return nom
     * 
     * @hibernate.property 
     * name="cppTestName" 
     * column="CppTestName" 
     * not-null="false" 
     * type="string" 
     * unique="true"
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
