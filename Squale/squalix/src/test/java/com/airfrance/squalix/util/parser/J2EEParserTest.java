package com.airfrance.squalix.util.parser;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.JspBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalix.tools.compiling.jsp.configuration.JspCompilingConfiguration;

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
