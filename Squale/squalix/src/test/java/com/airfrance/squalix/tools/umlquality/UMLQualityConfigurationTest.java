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
package com.airfrance.squalix.tools.umlquality;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalix.core.exception.ConfigurationException;

/**
 * Test de la configuration UMLQuality
 */
public class UMLQualityConfigurationTest
    extends SqualeTestCase
{

    /**
     * Test de chargement
     */
    public void testParse()
    {
        UMLQualityConfiguration cfg = new UMLQualityConfiguration();
        InputStream stream;
        try
        {
            stream = new FileInputStream( new File( "../squalix/config/umlquality-config.xml" ) );
            cfg.parse( stream );
            // Vérification des données lues dans le fichier
            assertEquals( "/app/SQUALE/squalix/umlquality/report", cfg.getReportDirectory() );
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
        UMLQualityConfiguration cfg = new UMLQualityConfiguration();
        InputStream stream;
        try
        {
            stream = new FileInputStream( new File( "build.properties" ) );
            cfg.parse( stream );
            // Vérification des données lues dans le fichier
            fail( "unexpected exception" );
        }
        catch ( Exception e )
        {
            assertTrue( "expected exception", true );
            assertTrue( "Exception lancée par la méthode parse", e instanceof ConfigurationException );
        }
    }

}
