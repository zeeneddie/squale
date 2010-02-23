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
package org.squale.squaleweb.util.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.squale.squaleweb.util.InputFieldDataChecker;

/**
 * Test class for org.squale.squaleweb.util.InputFieldDataChecker.
 */
public class InputFieldDataCheckerTest
{

    /**
     * Test method for {@link org.squale.squaleweb.util.InputFieldDataChecker#check(java.lang.String)}.
     */
    @Test
    public void testCheckCorrectUserIDs()
    {
        assertTrue( InputFieldDataChecker.USER_ID.check( "fabrice" ) );
        assertTrue( InputFieldDataChecker.USER_ID.check( "fabrice2" ) );
        assertTrue( InputFieldDataChecker.USER_ID.check( "fabrice-2" ) );
        assertTrue( InputFieldDataChecker.USER_ID.check( "fabrice_2" ) );
        assertTrue( InputFieldDataChecker.USER_ID.check( "212596" ) );
        assertTrue( InputFieldDataChecker.USER_ID.check( "212596f" ) );
        assertTrue( InputFieldDataChecker.USER_ID.check( "212596-f" ) );
        assertTrue( InputFieldDataChecker.USER_ID.check( "212596_f" ) );
        assertTrue( InputFieldDataChecker.USER_ID.check( "2-12596_f" ) );
        assertTrue( InputFieldDataChecker.USER_ID.check( "fabrice.bellingard" ) );
    }

    /**
     * Test method for {@link org.squale.squaleweb.util.InputFieldDataChecker#check(java.lang.String)}.
     */
    @Test
    public void testCheckWrongUserIDs()
    {
        assertFalse( InputFieldDataChecker.USER_ID.check( "-fabrice" ) );
        assertFalse( InputFieldDataChecker.USER_ID.check( "_fabrice" ) );
        assertFalse( InputFieldDataChecker.USER_ID.check( ".fabrice" ) );
        assertFalse( InputFieldDataChecker.USER_ID.check( "-12345" ) );
        assertFalse( InputFieldDataChecker.USER_ID.check( "_12345" ) );
        assertFalse( InputFieldDataChecker.USER_ID.check( "fabrice bellingard" ) );

    }

    /**
     * Test method for {@link org.squale.squaleweb.util.InputFieldDataChecker#check(java.lang.String)}.
     */
    @Test
    public void testCheckCorrectTagNames()
    {
        assertTrue( InputFieldDataChecker.USER_ID.check( "tag" ) );
        assertTrue( InputFieldDataChecker.USER_ID.check( "tag2" ) );
        assertTrue( InputFieldDataChecker.USER_ID.check( "tag-2" ) );
        assertTrue( InputFieldDataChecker.USER_ID.check( "tag_2" ) );
        assertTrue( InputFieldDataChecker.USER_ID.check( "212596" ) );
        assertTrue( InputFieldDataChecker.USER_ID.check( "212596f" ) );
        assertTrue( InputFieldDataChecker.USER_ID.check( "212596-f" ) );
        assertTrue( InputFieldDataChecker.USER_ID.check( "212596_f" ) );
        assertTrue( InputFieldDataChecker.USER_ID.check( "2-12596_f" ) );
        assertTrue( InputFieldDataChecker.USER_ID.check( "tag.tag2" ) );
    }

    /**
     * Test method for {@link org.squale.squaleweb.util.InputFieldDataChecker#check(java.lang.String)}.
     */
    @Test
    public void testCheckWrongUserTagNames()
    {
        assertFalse( InputFieldDataChecker.USER_ID.check( "-tag" ) );
        assertFalse( InputFieldDataChecker.USER_ID.check( "_tag" ) );
        assertFalse( InputFieldDataChecker.USER_ID.check( ".tag" ) );
        assertFalse( InputFieldDataChecker.USER_ID.check( "-12345" ) );
        assertFalse( InputFieldDataChecker.USER_ID.check( "_12345" ) );

    }

}
