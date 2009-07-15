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
package org.squale.squalix.tools.compiling.java.configuration;

import org.squale.squalecommon.SqualeTestCase;

/**
 *
 */
public class JCompilingConfigurationTest
    extends SqualeTestCase
{

    /**
     * Test du parsing des paramètres de configuration de compilation
     */
    public void testJCompilingConfiguration()
    {
        try
        {
            JCompilingConfiguration conf = new JCompilingConfiguration();
            // Les paramètres sont stockés en dur dans le fichier
            // config/compiling-config.xml
            assertEquals( conf.getClasspathSeparator(), ";" );
            assertEquals( conf.getDestDir(), "bin/" );
            assertEquals( conf.getJDKDefaultVersion(), "1_4" );
            assertEquals( conf.getRequiredMemory(), "1024m" );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "Unexpected exception" );
        }
    }
}
