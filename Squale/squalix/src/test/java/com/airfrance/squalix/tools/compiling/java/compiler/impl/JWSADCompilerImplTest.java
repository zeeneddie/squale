package com.airfrance.squalix.tools.compiling.java.compiler.impl;

import java.util.LinkedList;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalix.tools.compiling.java.beans.JWSADProject;

/**
 * UNIT_KO : Non testable du fait du changement temporaire dans la compilation pour avoir la version 1.5
 */
public class JWSADCompilerImplTest
    extends SqualeTestCase
{

    /**
     * Test de compilation d'un projet simple
     */
    public void testExecute()
    {
        LinkedList list = new LinkedList();
        JWSADProject project = new JWSADProject();
        project.setPath( "." );
        project.setSrcPath( "data/samples/testCommon" );
        project.setDestPath( "data/samples/bin/" );
        project.setRequiredMemory( "1024m" );
        project.setJavaVersion( "1_4" );
        list.add( project );
        JWSADCompilerImpl compiler = new JWSADCompilerImpl( list );
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

}
