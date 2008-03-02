package com.airfrance.squalix.util.file;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * 
 */
public class FileUtilAllTests
{

    /**
     * @return la suite de tests
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite( "Test for package util file" );
        // $JUnit-BEGIN$
        suite.addTest( new TestSuite( ExtensionIncludedFileFilterTest.class ) );
        suite.addTest( new TestSuite( FileUtilityTest.class ) );
        suite.addTest( new TestSuite( JspFileUtilityTest.class ) );
        return suite;
    }

}
