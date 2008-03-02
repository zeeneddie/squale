package com.airfrance.squalix.tools.cpd;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Suite de tests pour la tâche CPD
 */
public class CpdAllTests
{

    /**
     * @return la suite de tests pour CPD
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite( "Test for CPD task" );
        // $JUnit-BEGIN$
        suite.addTest( new TestSuite( JavaCpdProcessingTest.class ) );
        suite.addTest( new TestSuite( CpdTaskTest.class ) );
        // $JUnit-END$
        return suite;
    }

}
