package com.airfrance.squalix.tools.compiling.java.parser.rsa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalix.core.exception.ConfigurationException;

/**
 * Test pour le parsing du fichier .settings/org.eclipse.wst.common.component afin de récupérer le nom du répertoire web
 * content
 */
public class JRSAWebSettingsParserTest
    extends SqualeTestCase
{

    /**
     * Teste Le parsing du fichier.
     * 
     * @throws FileNotFoundException si erreur de fichier
     * @throws ConfigurationException si erreur de configuration
     */
    public void testParse()
        throws FileNotFoundException, ConfigurationException
    {
        JRSAWebSettingsParser parser = new JRSAWebSettingsParser();
        InputStream input =
            new FileInputStream( new File( "./data/QuickTestRSA/testWeb/.settings/org.eclipse.wst.common.component" ) );
        parser.parse( input );
        assertEquals( "/WebContent", parser.getWebContentFolder() );
    }

}
