package com.airfrance.squalix.tools.cpptest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalix.core.exception.ConfigurationException;

/**
 * Test de la configuration CppTest
 */
public class CppTestConfigurationTest
    extends SqualeTestCase
{

    /**
     * Test de chargement
     */
    public void testParse()
    {
        CppTestConfiguration cfg = new CppTestConfiguration();
        InputStream stream;
        try
        {
            stream = new FileInputStream( new File( "../squalix/config/cpptest-config.xml" ) );
            cfg.parse( stream );
            // Vérification des données lues dans le fichier
            assertEquals( "/app/SQUALE/dev/data/cpptest/report", cfg.getReportDirectory() );
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
        CppTestConfiguration cfg = new CppTestConfiguration();
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
