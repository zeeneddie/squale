package com.airfrance.squalix.tools.rsm;

import com.airfrance.squalix.util.parser.CppParser;


/**
 */
public class CppRSMTask extends RSMTask {

    /**
     * Constructeur
     */
    public CppRSMTask() {
        mName = "CppRSMTask";
        mLanguageParser = new CppParser(mProject);
    }
    
    
}
