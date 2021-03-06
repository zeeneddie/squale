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
package org.squale.squalix.tools.compiling.java.parser;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Tests du package org.squale.squalix.tools.compiling.java.parser
 */
public class AllTests
{

    /**
     * Suite de tests
     * 
     * @return tests
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite( "Test for org.squale.squalix.tools.compiling.java.parser" );
        // $JUnit-BEGIN$
        suite.addTest( org.squale.squalix.tools.compiling.java.parser.configuration.AllTests.suite() );
        suite.addTest( org.squale.squalix.tools.compiling.java.parser.impl.AllTests.suite() );
        suite.addTest( org.squale.squalix.tools.compiling.java.parser.wsad.AllTests.suite() );
        // $JUnit-END$
        return suite;
    }
}
