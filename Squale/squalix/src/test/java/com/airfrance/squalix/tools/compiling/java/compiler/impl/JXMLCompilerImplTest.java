package com.airfrance.squalix.tools.compiling.java.compiler.impl;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalix.tools.compiling.java.beans.JXMLProject;

/**
 * UNIT_KO : Pas eu le temps de le traiter
 */
public class JXMLCompilerImplTest
    extends SqualeTestCase
{

    /**
     * Compilation avec un build.xml existant
     */
    public void testExecute()
    {
        JXMLProject project = new JXMLProject();
        project.setPath( "data/samples/testCommon/build.xml" );
        project.setTarget( "all" );
        JXMLCompilerImpl compiler = new JXMLCompilerImpl( project );
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
