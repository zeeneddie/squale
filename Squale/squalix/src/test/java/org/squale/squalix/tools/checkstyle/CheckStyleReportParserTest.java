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
package org.squale.squalix.tools.checkstyle;

import java.io.File;
import java.io.FileInputStream;

import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalix.tools.ruleschecking.CheckstyleReportParser;
import org.squale.squalix.tools.ruleschecking.ReportHandler;

/**
 * Test du parser de rapport checkstyle
 */
public class CheckStyleReportParserTest
    extends SqualeTestCase
{
    /**
     * Test de parsing checkstyle
     */
    public void testParsing()
    {
        CheckstyleReportParser parser = new CheckstyleReportParser( "" );
        try
        {
            ErrorCounter counter = new ErrorCounter();
            parser.parse( new FileInputStream( new File( "data/checkstyle/output.xml" ) ), counter );
            final int expectedCount = 23;
            assertEquals( expectedCount, counter.getCount() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    /**
     * Comptage des erreurs
     */
    class ErrorCounter
        implements ReportHandler
    {
        /** Nombre d'erreurs */
        private int mCount;

        /**
         * (non-Javadoc)
         * 
         * @see org.squale.squalix.tools.ruleschecking.ReportHandler#processError(java.lang.String, java.lang.String,
         *      java.lang.String, java.lang.String, java.lang.String, java.lang.String)
         */
        public void processError( String pFileName, String pLine, String pColumn, String pSeverity, String pMessage,
                                  String pSource )
        {
            assertNotNull( pFileName );
            assertNotNull( pLine );
            assertNotNull( pColumn );
            assertNotNull( pSeverity );
            assertNotNull( pMessage );
            assertNotNull( pSource );
            mCount++;
        }

        /**
         * @return nombre d'erreurs
         */
        public int getCount()
        {
            return mCount;
        }

    }
}
