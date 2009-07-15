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
package org.squale.squalix.tools.cpptest;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.squale.squalecommon.SqualeTestCase;

/**
 * Test du parser de résultat CppTest
 */
public class ResultParserTest
    extends SqualeTestCase
{

    /**
     * Test nominal
     */
    public void testNominal()
    {
        File resultFile = new File( "data/cpptest", "report_20060626-120211-607.xml" );
        assertTrue( "fichier existant", resultFile.exists() );
        try
        {
            FileInputStream stream = new FileInputStream( resultFile );
            ReportParser parser = new ReportParser();
            Map result = parser.parse( stream, "" );
            final int nbRules = 76;
            assertEquals( "Expected number of rules", nbRules, result.size() );
            final int detailSize = 2;
            int count = 0;
            Iterator it = result.values().iterator();
            while ( it.hasNext() )
            {
                count += ( (Collection) it.next() ).size();
            }
            assertEquals( detailSize, count );
            stream.close();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test de parsing d'un mauvais fichier
     */
    public void testBadFile()
    {
        File resultFile = new File( ".", "build.properties" );
        assertTrue( "fichier existant", resultFile.exists() );
        try
        {
            FileInputStream stream = new FileInputStream( resultFile );
            ReportParser parser = new ReportParser();
            parser.parse( stream, "" );
            stream.close();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            assertTrue( "expected exception", true );
        }
    }
}
