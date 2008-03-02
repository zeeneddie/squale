package com.airfrance.squalix.tools.compiling.java;

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
        TestSuite suite = new TestSuite( "Test for com.airfrance.squalix.tools.compiling.java" );
        // $JUnit-BEGIN$
        suite.addTest( new TestSuite( JCompilingTaskTest.class ) );
        suite.addTest( com.airfrance.squalix.tools.compiling.java.compiler.AllTests.suite() );
        suite.addTest( com.airfrance.squalix.tools.compiling.java.configuration.AllTests.suite() );
        suite.addTest( com.airfrance.squalix.tools.compiling.java.parser.AllTests.suite() );
        // $JUnit-END$
        return suite;
    }
}
