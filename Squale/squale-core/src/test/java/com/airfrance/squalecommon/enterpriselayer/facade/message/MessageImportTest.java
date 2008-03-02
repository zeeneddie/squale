package com.airfrance.squalecommon.enterpriselayer.facade.message;

import java.io.InputStream;
import java.util.Collection;

import com.airfrance.squalecommon.SqualeTestCase;

/**
 * Test de la facade d'importation de messages
 */
public class MessageImportTest
    extends SqualeTestCase
{

    /**
     * Test d'importation des messages
     */
    public void testImportMessages()
    {
        StringBuffer errors = new StringBuffer();
        InputStream stream = getClass().getClassLoader().getResourceAsStream( "data/message/messages.xml" );
        MessageImport imp = new MessageImport();
        Collection messages = imp.importMessages( stream, errors );
        assertEquals( 2, messages.size() );
        assertEquals( 0, errors.length() );
    }

}
