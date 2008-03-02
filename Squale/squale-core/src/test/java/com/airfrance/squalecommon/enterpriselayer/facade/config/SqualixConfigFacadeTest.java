package com.airfrance.squalecommon.enterpriselayer.facade.config;

import java.io.InputStream;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.datatransfertobject.config.SqualixConfigurationDTO;

/**
 * Test pour SqualixConfigFacade
 */
public class SqualixConfigFacadeTest
    extends SqualeTestCase
{

    /**
     * Test la récupération de la configuration en base
     */
    public void testCreateConfig()
    {
        StringBuffer errors = new StringBuffer();
        InputStream stream = getClass().getClassLoader().getResourceAsStream( "data/config/squalix-config_simple.xml" );
        SqualixConfigurationDTO conf;
        try
        {
            ConfigurationImport.createConfig( stream, errors );
            conf = SqualixConfigFacade.getConfig();
            assertEquals( 1, conf.getProfiles().size() );
            assertTrue( errors.length() == 0 );
        }
        catch ( JrafEnterpriseException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }
}
