package com.airfrance.squalix.tools.checkstyle;

import java.io.File;
import java.io.FileInputStream;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalix.tools.ruleschecking.CheckstyleReportParser;
import com.airfrance.squalix.tools.ruleschecking.ReportHandler;

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
         * @see com.airfrance.squalix.tools.ruleschecking.ReportHandler#processError(java.lang.String, java.lang.String,
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
