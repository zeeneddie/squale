package com.airfrance.squalix.util.process;

import java.io.File;

import com.airfrance.squalecommon.SqualeTestCase;

/**
 * Test de lancement de processus
 */
public class ProcessManagerTest
    extends SqualeTestCase
    implements ProcessErrorHandler, ProcessOutputHandler
{

    /** Données sur le flux stdout */
    private StringBuffer mOutput;

    /** Données sur le flux stderr */
    private StringBuffer mError;

    /**
     * Test de lancement de commande On lance la commande java -version
     */
    public void testStartProcess()
    {
        final String SRC_DIR = "data/Project4McCabeCppTest";

        try
        {
            String[] command = { "java", "-version" };
            ProcessManager mgr = new ProcessManager( command, null, new File( SRC_DIR ) );
            mgr.setOutputHandler( this );
            int result = mgr.startProcess( this );
            assertEquals( "commande réussie", 0, result );
            // La commande java -version écrit sur stderr
            assertEquals( "résultat sur stderr", 0, mOutput.length() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test de lancement de commande On lance la commande java -version
     */
    public void testStartbadProcess()
    {
        final String SRC_DIR = "data/Project4McCabeCppTest";

        try
        {
            String[] command = { "unknown_command", "-version" };
            ProcessManager mgr = new ProcessManager( command, null, new File( SRC_DIR ) );
            mgr.setOutputHandler( this );
            int result = mgr.startProcess( this );
            fail( "expected exception" );
        }
        catch ( Exception e )
        {
            assertTrue( "expected exception", true );
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.airfrance.squalix.util.process.ProcessErrorHandler#processError(java.lang.String)
     */
    public void processError( String pErrorMessage )
    {
        mError.append( pErrorMessage );
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.airfrance.squalix.util.process.ProcessOutputHandler#processOutput(java.lang.String)
     */
    public void processOutput( String pOutputLine )
    {
        mOutput.append( pOutputLine );
    }

    /**
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp()
        throws Exception
    {
        super.setUp();
        mOutput = new StringBuffer();
        mError = new StringBuffer();
    }

}
