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
package org.squale.squalix.util.file;

import java.io.IOException;
import java.util.ArrayList;

import org.squale.squalecommon.SqualeTestCase;

/**
 * Test pour la manipulation des fichiers jsps Le test sur la compilation des jsps doit avoir été lancé avant.
 */
public class JspFileUtilityTest
    extends SqualeTestCase
{

    /**
     * Test pour la récupération d'un fichier source jsp lors d'un renommage par JspC
     * 
     * @throws IOException si erreur
     */
    public void testGetAbsoluteJspFileName()
        throws IOException
    {
        ArrayList paths = new ArrayList();
        paths.add( "data/samples/testWeb/WebContent/jsp" );
        paths.add( "../squaleWeb/WebContent/jsp" );
        String className = "jsp.pac_00023_000a7_0005ekage_000231.class_00025";
        String filename = JspFileUtility.getAbsoluteJspFileName( paths, className );
        assertTrue( filename.endsWith( "class.jsp" ) );
    }

}
