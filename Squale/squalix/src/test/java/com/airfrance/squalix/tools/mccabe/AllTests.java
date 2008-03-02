/*
 * Created on Dec 6, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.airfrance.squalix.tools.mccabe;

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
        TestSuite suite = new TestSuite( "Test for com.airfrance.squalix.tools.mccabe" );
        // $JUnit-BEGIN$
        suite.addTest( new TestSuite( McCabeConfigurationTest.class ) );
        suite.addTest( new TestSuite( McCabePCFFileTest.class ) );
        suite.addTest( new TestSuite( McCabePersistorTest.class ) );
        suite.addTest( new TestSuite( JavaMcCabeTaskTest.class ) );
        // $JUnit-END$
        return suite;
    }
}
