package com.airfrance.squalix.tools.compiling.java.parser;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Tests du package com.airfrance.squalix.tools.compiling.java.parser
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
        TestSuite suite = new TestSuite( "Test for com.airfrance.squalix.tools.compiling.java.parser" );
        // $JUnit-BEGIN$
        suite.addTest( com.airfrance.squalix.tools.compiling.java.parser.configuration.AllTests.suite() );
        suite.addTest( com.airfrance.squalix.tools.compiling.java.parser.impl.AllTests.suite() );
        suite.addTest( com.airfrance.squalix.tools.compiling.java.parser.wsad.AllTests.suite() );
        // $JUnit-END$
        return suite;
    }
}
