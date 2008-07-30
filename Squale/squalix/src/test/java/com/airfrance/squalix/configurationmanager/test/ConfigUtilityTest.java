/**
 * 
 */
package com.airfrance.squalix.configurationmanager.test;

import junit.framework.TestCase;

import com.airfrance.squalix.configurationmanager.ConfigUtility;

/**
 * Test method for com.airfrance.squalix.configurationmanager.ConfigUtility
 * 
 * @see ConfigUtility
 * @author Fabrice
 */
public class ConfigUtilityTest
    extends TestCase
{

    /**
     * Test method for
     * {@link com.airfrance.squalix.configurationmanager.ConfigUtility#filterStringWithSystemProps(java.lang.String)}.
     */
    public void testFilterStringWithSystemProps()
    {
        String stringToFilter = "The java VM spec vendor is ${java.vm.specification.vendor}, of course.";

        assertEquals( "The java VM spec vendor is Sun Microsystems Inc., of course.",
                      ConfigUtility.filterStringWithSystemProps( stringToFilter ) );
    }

    /**
     * Test method for
     * {@link com.airfrance.squalix.configurationmanager.ConfigUtility#filterStringWithSystemProps(java.lang.String)}.
     */
    public void testFilterStringWithMultipleSystemProps()
    {
        // do the test with back slashes, as this can bring troubles compared to simple slashes
        System.setProperty( "my.prop1", "C:\\foo\\bar" );
        System.setProperty( "my.prop2", "foo2\\bar2" );

        String stringToFilter = "${my.prop1}\\monRepertoire\\${my.prop2}\\toto.xml";

        assertEquals( "C:\\foo\\bar\\monRepertoire\\foo2\\bar2\\toto.xml",
                      ConfigUtility.filterStringWithSystemProps( stringToFilter ) );
    }

    /**
     * Test method for
     * {@link com.airfrance.squalix.configurationmanager.ConfigUtility#filterStringWithSystemProps(java.lang.String)}.
     */
    public void testFilterStringWithNoSystemProps()
    {
        String stringToFilter = "This is no system property : ${my.property}!";

        assertEquals( "This is no system property : ${my.property}!",
                      ConfigUtility.filterStringWithSystemProps( stringToFilter ) );
    }

    /**
     * Test method for
     * {@link com.airfrance.squalix.configurationmanager.ConfigUtility#filterStringWithSystemProps(java.lang.String)}.
     */
    public void testFilterStringWithNoVariable()
    {
        String stringToFilter = "This is simple string!";

        assertEquals( "This is simple string!", ConfigUtility.filterStringWithSystemProps( stringToFilter ) );
    }

}
