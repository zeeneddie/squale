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
package org.squale.squalix.tools.compiling.java.compiler.impl;

import java.util.LinkedList;

import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalix.tools.compiling.java.beans.JWSADProject;

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
