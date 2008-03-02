package com.airfrance.squalix.tools.compiling.java.parser.configuration;

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
        TestSuite suite = new TestSuite( "Test for com.airfrance.squalix.tools.compiling.java.parser.configuration" );
        // $JUnit-BEGIN$
        suite.addTest( new TestSuite( JParserUtilityTest.class ) );
        suite.addTest( new TestSuite( JParserConfigurationTest.class ) );
        // $JUnit-END$
        return suite;
    }
}
