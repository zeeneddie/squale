package org.squale.squalix.tools.coberturatask;

import java.io.File;

import org.apache.commons.digester.Digester;
import org.junit.After;
import org.junit.Before;

import com.airfrance.squalix.tools.cobertura.CoberturaParser;

import junit.framework.TestCase;

/**
 * Test Class of the {@link CoberturaParserTest}
 */
public class CoberturaParserTest
    extends TestCase
{
    /* Switching check-style off to write tests */
    // CHECKSTYLE:OFF
    private Digester digester;
    
    private File configurationFile;
    
    private CoberturaParser parser;

    /** Preparing instance of needed objects */
    @Before
    public void setUp()
    {
        configurationFile = new File("myTestConfiguration.xml");
        parser = new CoberturaParser();
    }

    /** Cleaning values of objects */
    @After
    public void tearDown()
    {
    }

    /** Testing the xml configuration method */
    public void testXmlConfiguration()
    {
        //See with FB for private method testing
    }
}
