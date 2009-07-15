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
package org.squale.squalix.util.process;

import java.io.File;

import org.squale.squalecommon.SqualeTestCase;

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
     * @see org.squale.squalix.util.process.ProcessErrorHandler#processError(java.lang.String)
     */
    public void processError( String pErrorMessage )
    {
        mError.append( pErrorMessage );
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.squale.squalix.util.process.ProcessOutputHandler#processOutput(java.lang.String)
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
