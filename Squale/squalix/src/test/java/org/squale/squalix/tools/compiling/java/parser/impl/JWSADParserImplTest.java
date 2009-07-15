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
package com.airfrance.squalix.tools.compiling.java.parser.impl;

import java.util.LinkedList;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalix.tools.compiling.java.beans.JWSADProject;

/**
 *
 */
public class JWSADParserImplTest
    extends SqualeTestCase
{

    /**
     * Test de l'implémentation d'un parser de projet WSAD
     */
    public void testExecute()
    {
        // Le fait de creer un projet vide permet son traitement avec junit
        // car il cherchera à travailler sur le répertoire courant
        JWSADProject project = new JWSADProject();
        project.setPath( "./" );
        assertEquals( "Le classpath ne devrait être vide", project.getClasspath(), "" );
        assertEquals( "Le chemin des sources devrait être nul", project.getSrcPath(), "" );
        LinkedList list = new LinkedList();
        list.add( project );
        try
        {
            JWSADParserImpl parser = new JWSADParserImpl( list );
            parser.execute();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "Unexpected exception" );
        }
        assertTrue( "Le classpath n'a pas été parsé", project.getClasspath().length() > 0 );
        assertTrue( "Le fichier .classpath a mal été parsé", project.getSrcPath().length() > 0 );
        assertFalse( "Le projet n'est pas compilé", project.isCompiled() );
        assertTrue( "Le projet est un projet WSAD", project.isWSAD() );
    }

}
