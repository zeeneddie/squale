package com.airfrance.squalix.util.parser;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * 
 */
public class ParserAllTests
{

    /**
     * @return la suite de tests
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite( "Test for parser utility" );
        // cpp
        suite.addTest( new TestSuite( CppParserTest.class ) );
        // java
        suite.addTest( new TestSuite( JavaParserTest.class ) );
        // j2ee
        suite.addTest( new TestSuite( J2EEParserTest.class ) );
        return suite;
    }

}
