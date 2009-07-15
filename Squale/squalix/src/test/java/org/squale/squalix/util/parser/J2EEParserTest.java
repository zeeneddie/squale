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
package org.squale.squalix.util.parser;

import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.enterpriselayer.businessobject.component.JspBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalix.tools.compiling.jsp.configuration.JspCompilingConfiguration;

/**
 * Test pour le parser J2EE
 */
public class J2EEParserTest
    extends SqualeTestCase
{

    /**
     * Test la création d'un composant JSP
     */
    public void testGetJsp()
    {
        String fileName = "D:/chemin/projetTest/jsp/pac#§^kage#1/MaPageJSP.jsp";
        String directoryName = "D:/chemin/projetTest/jsp/";
        String jspName = "MaPageJSP";
        ProjectBO project = new ProjectBO( "projetTest" );
        J2EEParser parser = new J2EEParser( project );
        JspBO jsp = parser.getJsp( jspName, fileName, directoryName, 0 );
        assertEquals( jspName, jsp.getName() );
        assertEquals( J2EEParser.ROOT_PACKAGE, jsp.getParent().getParent().getParent().getName() );
        assertEquals( JspCompilingConfiguration.FIRST_PACKAGE, jsp.getParent().getParent().getName() );
        assertEquals( "pac#§^kage#1", jsp.getParent().getName() );
        assertEquals( project.getName(), jsp.getParent().getParent().getParent().getParent().getName() );
    }

}
