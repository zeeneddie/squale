package com.airfrance.squalix.tools.checkstyle;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Tests de checkstyle
 */
public class AllTests
{
    /**
     * Suite de tests
     * 
     * @return suite de tests
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite( "Test for com.airfrance.squalix.tools.checkstyle" );
        // $JUnit-BEGIN$
        suite.addTest( new TestSuite( CheckStyleConfigurationTest.class ) );
        suite.addTest( new TestSuite( CheckStyleProcessTest.class ) );
        suite.addTest( new TestSuite( CheckStyleReportParserTest.class ) );
        suite.addTest( new TestSuite( RulesCheckingTaskTest.class ) );
        suite.addTest( new TestSuite( CheckStylePersistorTest.class ) );
        // $JUnit-END$
        return suite;
    }
}
