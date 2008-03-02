package com.airfrance.squalix.tools.rsm;

import com.airfrance.squalix.util.parser.JavaParser;

/**
 */
public class JavaRSMTask
    extends RSMTask
{

    /**
     * Constructeur
     */
    public JavaRSMTask()
    {
        mName = "JavaRSMTask";
        mLanguageParser = new JavaParser( mProject );
    }
}
