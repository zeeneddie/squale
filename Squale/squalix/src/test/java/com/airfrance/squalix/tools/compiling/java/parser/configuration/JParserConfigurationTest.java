package com.airfrance.squalix.tools.compiling.java.parser.configuration;

import junit.framework.TestCase;

/**
 * Test du parser de configuration
 */
public class JParserConfigurationTest
    extends TestCase
{

    /**
     * Constructor for JParserConfigurationTest.
     * 
     * @param arg0 le nom
     */
    public JParserConfigurationTest( String arg0 )
    {
        super( arg0 );
    }

    /**
     * Test du parser de configuration
     */
    public void testJParserConfiguration()
    {
        try
        {
            JParserConfiguration conf = new JParserConfiguration();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "Unexpected exception" );
        }
    }

}
