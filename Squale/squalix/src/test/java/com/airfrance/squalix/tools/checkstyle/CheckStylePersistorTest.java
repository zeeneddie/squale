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
