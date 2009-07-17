/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.squale.squalecommon.enterpriselayer.facade.config;

import java.io.InputStream;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.datatransfertobject.config.SqualixConfigurationDTO;

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
        }
        catch ( JrafEnterpriseException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }
}
