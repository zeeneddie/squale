package com.airfrance.squalix.tools.compiling.java.compiler.xml;

import java.io.FileNotFoundException;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalix.tools.compiling.java.beans.JXMLProject;

/**
 * Test de compilation avec un fichier ant existant UNIT_KO : Pas eu le temps de le traiter
 */
public class JXMLCompilerTest
    extends SqualeTestCase
{

    /**
     * Compilaion avec un build.xml existant
     */
    public void testExecute()
    {
        JXMLProject project = new JXMLProject();
        project.setPath( "data/samples/testCommon/build.xml" );
        project.setTarget( "all" );
        JXMLCompiler compiler = new JXMLCompiler( project );
        try
        {
            compiler.execute();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "Unexpected exception" );
        }
    }

    /**
     * Compilaion avec un build.xml inexistant
     */
    public void testExecuteNotFound()
    {
        JXMLProject project = new JXMLProject();
        project.setPath( "data/samples/testCommon/unknown.xml" );
        project.setTarget( "all" );
        JXMLCompiler compiler = new JXMLCompiler( project );
        try
        {
            compiler.execute();
            fail( "Exception was expected" );
        }
        catch ( Exception e )
        {
            assertTrue( "Expected exception", e instanceof FileNotFoundException );
        }
    }

    /**
     * Compilaion avec un build.xml existant et une target inexistante
     */
    public void testExecuteNoTarget()
    {
        JXMLProject project = new JXMLProject();
        project.setPath( "data/samples/testCommon/build.xml" );
        project.setTarget( "unknown" );
        JXMLCompiler compiler = new JXMLCompiler( project );
        try
        {
            compiler.execute();
            fail( "Exception was expected" );
        }
        catch ( Exception e )
        {
            assertTrue( "Expected exception", true );
        }
    }
}
