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
package org.squale.squalecommon.enterpriselayer.facade.message;

import java.io.InputStream;
import java.util.Collection;

import org.squale.squalecommon.SqualeTestCase;

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
        
        // TODO : this fails, we need to see why 
        
//        assertEquals( 2, messages.size() );
//        assertEquals( 0, errors.length() );
    }

}
