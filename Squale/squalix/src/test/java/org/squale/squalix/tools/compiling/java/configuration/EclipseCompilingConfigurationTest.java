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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalix.core.exception.ConfigurationException;

/**
 * Test la récupération de la configuration de la compilation avec Eclipse
 */
public class EclipseCompilingConfigurationTest
    extends SqualeTestCase
{
    /**
     * Test d'importation
     */
    public void testImport()
    {
        EclipseCompilingConfiguration cfg = new EclipseCompilingConfiguration();
        InputStream stream;
        try
        {
            stream = new FileInputStream( new File( "src/config/eclipsecompiling-config.xml" ) );
            cfg.parse( stream );
            // Vérification des données lues dans le fichier
            assertEquals( "D:", cfg.getWorkspace() );
            assertEquals( "java", cfg.getCommand() );
            final int nbMandatoryOptions = 18;
            assertEquals( nbMandatoryOptions, cfg.getOptions().size() );
            assertTrue( cfg.getEclipseHome().getAbsolutePath().endsWith( "plugins" ) );
            assertEquals( "C:/eclipse_3_2_1/plugins",
                          cfg.getSqualeEclipsePlugins().getAbsolutePath().replaceAll( "\\\\", "/" ) );
            assertEquals( "script1.sh", cfg.getRightScript() );
            assertEquals( "script2.sh", cfg.getCopyScript() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Chargement d'un fichier erroné
     */
    public void testParseBadFile()
    {
        EclipseCompilingConfiguration cfg = new EclipseCompilingConfiguration();
        InputStream stream;
        try
        {
            stream = new FileInputStream( new File( "../squalix/config/cpptest-config.xml" ) );
            cfg.parse( stream );
            // Vérification des données lues dans le fichier
            fail( "expected exception" );
        }
        catch ( Exception e )
        {
            assertTrue( "expected exception", true );
            assertTrue( "Exception lancée par la méthode parse", e instanceof ConfigurationException );
        }
    }
}