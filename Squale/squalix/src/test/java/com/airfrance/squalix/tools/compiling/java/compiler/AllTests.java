package com.airfrance.squalix.tools.compiling.java.compiler;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Tests du package com.airfrance.squalix.tools.compiling.java.compiler
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
        TestSuite suite = new TestSuite( "Test for com.airfrance.squalix.tools.compiling.java.compiler" );
        // $JUnit-BEGIN$
        suite.addTest( com.airfrance.squalix.tools.compiling.java.compiler.impl.AllTests.suite() );
        suite.addTest( com.airfrance.squalix.tools.compiling.java.compiler.wsad.AllTests.suite() );
        suite.addTest( com.airfrance.squalix.tools.compiling.java.compiler.xml.AllTests.suite() );
        // $JUnit-END$
        return suite;
    }
}
