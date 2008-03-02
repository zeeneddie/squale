package com.airfrance.squalix.tools.compiling.java.compiler.wsad;

import java.util.LinkedList;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalix.tools.compiling.java.beans.JWSADProject;

/**
 * UNIT_KO : : Non testable du fait du changement temporaire dans la compilation pour avoir la version 1.5
 */
public class JWSADCompilerTest
    extends SqualeTestCase
{

    /**
     * Test de compilation d'un projet simple
     */
    public void testRunCompilation()
    {
        LinkedList list = new LinkedList();
        JWSADProject project = new JWSADProject();
        project.setPath( "data/samples/testCommon" );
        project.setSrcPath( "data/samples/testCommon" );
        project.setDestPath( "bin" );
        project.setRequiredMemory( "1024m" );
        project.setJavaVersion( "1_4" );
        list.add( project );
        JWSADCompiler compiler = new JWSADCompiler( list );
        try
        {
            compiler.runCompilation();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "Unexpected exception" );
        }
    }

}
