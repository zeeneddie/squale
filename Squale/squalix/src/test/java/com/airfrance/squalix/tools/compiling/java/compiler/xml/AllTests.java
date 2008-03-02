package com.airfrance.squalix.tools.compiling.java.compiler.xml;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 *
 */
public class AllTests
{

    /**
     * Suite de tests
     * 
     * @return tests
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite( "Test for com.airfrance.squalix.tools.compiling.java.compiler.xml" );
        // $JUnit-BEGIN$
        suite.addTest( new TestSuite( JXMLCompilerTest.class ) );
        // $JUnit-END$
        return suite;
    }
}
