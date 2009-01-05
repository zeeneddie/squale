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
package com.airfrance.squalix.tools.checkstyle;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Tests de checkstyle
 */
public class AllTests
{
    /**
     * Suite de tests
     * 
     * @return suite de tests
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite( "Test for com.airfrance.squalix.tools.checkstyle" );
        // $JUnit-BEGIN$
        suite.addTest( new TestSuite( CheckStyleConfigurationTest.class ) );
        suite.addTest( new TestSuite( CheckStyleProcessTest.class ) );
        suite.addTest( new TestSuite( CheckStyleReportParserTest.class ) );
        suite.addTest( new TestSuite( RulesCheckingTaskTest.class ) );
        suite.addTest( new TestSuite( CheckStylePersistorTest.class ) );
        // $JUnit-END$
        return suite;
    }
}
