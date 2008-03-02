package com.airfrance.squalix.tools.macker;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Suite de tests pour Macker
 */
public class MackerAllTests
{

    /**
     * @return la suite de tests
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite( "Test for macker task" );
        // $JUnit-BEGIN$
        suite.addTest( new TestSuite( MackerTaskTest.class ) );
        suite.addTest( new TestSuite( J2eeMackerTaskTest.class ) );
        // $JUnit-END$
        return suite;
    }

}
