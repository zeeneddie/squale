package com.airfrance.squalix.tools.checkstyle;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalix.core.exception.ConfigurationException;
import com.airfrance.squalix.tools.ruleschecking.CheckstyleConfiguration;

/**
 * Test de configuration
 */
public class CheckStyleConfigurationTest
    extends SqualeTestCase
{
    /**
     * Test d'importation
     */
    public void testImport()
    {
        CheckstyleConfiguration cfg = new CheckstyleConfiguration();
        InputStream stream;
        try
        {
            stream = new FileInputStream( new File( "../squalix/config/checkstyle-config.xml" ) );
            cfg.parse( stream );
            // Vérification des données lues dans le fichier
            assertEquals( "/app/SQUALE/dev/data/checkstyle/report", cfg.getReportDirectory() );
            assertEquals( "/app/SQUALE/dev/bin/java/lib/checkstyle", cfg.getJarDirectory() );
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
        CheckstyleConfiguration cfg = new CheckstyleConfiguration();
        InputStream stream;
        try
        {
            stream = new FileInputStream( new File( "../squalix/config/cpptest-config.xml" ) );
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
