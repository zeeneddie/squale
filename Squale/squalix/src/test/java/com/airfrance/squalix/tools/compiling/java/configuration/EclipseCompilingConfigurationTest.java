package com.airfrance.squalix.tools.compiling.java.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalix.core.exception.ConfigurationException;

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