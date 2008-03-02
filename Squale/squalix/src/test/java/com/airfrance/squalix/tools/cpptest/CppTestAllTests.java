package com.airfrance.squalix.tools.cpptest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Suite de tests pour la tâche CppTest
 */
public class CppTestAllTests
{

    /**
     * @return la suite de tests
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite( "Test for cppTest task" );
        // $JUnit-BEGIN$
        /* script de partition */
        suite.addTest( new TestSuite( CppTestConfigurationTest.class ) );
        suite.addTest( new TestSuite( CppTestWorkSpaceTest.class ) );
        suite.addTest( new TestSuite( ResultParserTest.class ) );
        suite.addTest( new TestSuite( CppTestPersistorTest.class ) );
        suite.addTest( new TestSuite( CppTestTaskTest.class ) );
        // $JUnit-END$
        return suite;
    }
}
