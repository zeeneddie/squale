package com.airfrance.squalix.util.file;

import java.io.IOException;
import java.util.ArrayList;

import com.airfrance.squalecommon.SqualeTestCase;

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
