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

import java.io.File;
import java.io.FileInputStream;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking.CheckstyleTransgressionBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleRuleSetBO;
import com.airfrance.squalecommon.enterpriselayer.facade.checkstyle.xml.CheckstyleImport;
import com.airfrance.squalix.tools.ruleschecking.CheckStylePersistor;
import com.airfrance.squalix.tools.ruleschecking.CheckstyleReportParser;

/**
 * Test de la persistance checkstyle
 */
public class CheckStylePersistorTest
    extends SqualeTestCase
{
    /**
     * Test de persistance
     */
    public void testPersist()
    {
        try
        {
            CheckstyleImport imp = new CheckstyleImport();
            FileInputStream stream = new FileInputStream( "data/checkstyle/checkstyle_parsing.xml" );
            StringBuffer errors = new StringBuffer();
            CheckstyleRuleSetBO ruleSet = imp.importCheckstyle( stream, errors );
            CheckStylePersistor persistor = new CheckStylePersistor( ruleSet );
            CheckstyleReportParser parser = new CheckstyleReportParser( "" );
            parser.parse( new FileInputStream( new File( "data/checkstyle/output.xml" ) ), persistor );
            CheckstyleTransgressionBO trans = persistor.computeTransgression();
            assertNotNull( trans );
            assertEquals( ruleSet.getRules().size(), trans.getMetrics().size() );
            final int expectedCount = 23;
            assertEquals( expectedCount, trans.getDetails().size() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }
}
