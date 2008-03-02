package com.airfrance.squalix.tools.cpptest;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.airfrance.squalecommon.SqualeTestCase;

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
