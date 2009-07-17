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
package org.squale.squalecommon.util.messages;

import java.util.MissingResourceException;

import org.squale.squalecommon.SqualeTestCase;

/**
 * @author M400841 Cette classe test ne permet pas de tester dans de mauvaises conditions les methodes ! ! ! ! ! ! ! ! ! !
 */
public class CommonMessagesTest
    extends SqualeTestCase
{

    /**
     * Permet de voir le bon fonctionnement d'une methode
     */
    private boolean test = true;

    /**
     * Test une mauvaise clé dans le fichier
     */
    public void testGetBadKey()
    {
        String result = CommonMessages.getString( "bambi.manu" );
        assertNull( result );
    }

    /**
     * Test une bonne clé dans le fichier
     */
    public void testGetGoodKey()
    {

        test = true;

        try
        {
            assertTrue( CommonMessages.getString( "exception.messages" ) instanceof String );
        }
        catch ( MissingResourceException e )
        {
            test = false;
        }
        assertTrue( test );

    }

    /**
     * Test une clé ne pouvant pas convertir en entier
     */
    public void testGetBadInteger()
    {
        Integer result = new Integer( CommonMessages.getInt( "tr.measure.mccabe.classresult" ) );
        // -1 est la valeur en cas d'échec
        assertEquals( result, new Integer( -1 ) );
    }

    /**
     * Test une clé pouvant etre converti en entier
     */
    public void testGetGoodInteger()
    {

        test = true;

        try
        {
            CommonMessages.getInt( "audit.nombre" );
        }
        catch ( NumberFormatException e )
        {
            test = false;
        }
        assertTrue( test );
    }

}
