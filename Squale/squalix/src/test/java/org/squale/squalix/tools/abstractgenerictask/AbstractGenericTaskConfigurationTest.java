package org.squale.squalix.tools.abstractgenerictask;

import java.util.ArrayList;
import java.util.List;

import junit.framework.JUnit4TestAdapter;
import junit.framework.TestCase;

import org.codehaus.plexus.util.cli.Commandline;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import com.airfrance.squalix.tools.abstractgenerictask.AbstractGenericTaskConfiguration;

/**
 * Test Class of the {@link AbstractGenericTaskConfiguration}
 */
public class AbstractGenericTaskConfigurationTest
    extends TestCase
{
    /* Switching check-style off to write tests */
    // CHECKSTYLE:OFF
    private String pViewPath;

    private String pParameters;

    private StringParameterBO pWorkingDir;

    private StringParameterBO pToolLocation;

    private AbstractGenericTaskConfiguration conf;

    private Commandline cmd;

    private List<StringParameterBO> myTestList;

    private ListParameterBO myTestListBO;


    /** Preparing instance of needed objects */
    @Before
    public void setUp()
    {
        pViewPath = "/myServer/mySqualix/myHomeDir/myApplication/myProject/";
        pParameters = "../goToTheDir/ -command -Dproperty=value";
        pWorkingDir = new StringParameterBO( "mySpecified/WorkingDir" );
        pToolLocation = new StringParameterBO( "myTool/location/myTool.tool" );
        conf = new AbstractGenericTaskConfiguration();
        cmd = new Commandline();
        myTestList = new ArrayList<StringParameterBO>();
        myTestListBO = new ListParameterBO();

    }

    /** Cleaning values of objects */
    @After
    public void tearDown()
    {
        pViewPath = "";
        pParameters = "";
        pWorkingDir = new StringParameterBO();
        pToolLocation = new StringParameterBO();
    }

    /** Testing the isPattern method which identifies '*' in user entries */
    @Test
    public void testIsPattern()
    {
        String pattern = "**/*.xml";
        assertEquals( true, conf.isPattern( pattern ) );
    }

    /** Testing the {@link AbstractGenericTaskConfiguration#getResultFilesLocation} method */
    @Test
    public void testResultCount()
    {
        myTestList.add( new StringParameterBO( "firstValue" ) );
        myTestList.add( new StringParameterBO( "secondValue" ) );
        myTestList.add( new StringParameterBO( "thirdValue" ) );
        myTestList.add( new StringParameterBO( "forthValue" ) );
        myTestList.add( new StringParameterBO( "fifthValue" ) );
        myTestListBO.setParameters( myTestList );
        String[] arrayAfterTreatment = conf.getResultFilesLocation( myTestListBO );
        int count = arrayAfterTreatment.length;
        assertEquals( 5, count );
        assertEquals( "firstValue", arrayAfterTreatment[0] );
        assertEquals( "forthValue", arrayAfterTreatment[3] );
    }

    /**
     * Testing the preparetoolExecution method which enriched an instance of {@link Commandline}.<br />
     */
    @Ignore( "Not Ready to Run" )
    @Test
    /*
     * Please note that as the @Ignore annotation is not interpreted by JUnit3 or the JUnit4TestAdapter an underscore is
     * added here to avoid execution
     */
    public void _testNoWorkingDir()
    {
        /* Testing the workingDirectory resolving */
        cmd = conf.prepareToolExecution( pToolLocation, pWorkingDir, pParameters, pViewPath );
        assertEquals( "myTool/location/myTool.tool", cmd.getExecutable() );
    }
    
    /**
     * This method launches the unit tests written in {@link AbstractGenericTaskConfigurationTest}
     * 
     * @return an adapter for JUnit3/4 tests
     */
    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter( AbstractGenericTaskConfigurationTest.class );
    }

}
