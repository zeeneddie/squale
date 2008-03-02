/*
 * Created on Dec 6, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.airfrance.squalix.util.csv;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author M840451 To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
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
        TestSuite suite = new TestSuite( "Test for com.airfrance.squalix.util.csv" );
        // $JUnit-BEGIN$
        suite.addTest( new TestSuite( CSVParserTest.class ) );
        // $JUnit-END$
        return suite;
    }
}
