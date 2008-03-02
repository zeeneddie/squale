package com.airfrance.squalix.tools.checkstyle;

import java.io.File;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalix.core.exception.ConfigurationException;
import com.airfrance.squalix.tools.ruleschecking.CheckStyleProcess;

/**
 * Test du processus ChecksStyle
 */
public class CheckStyleProcessTest
    extends SqualeTestCase
{

    /**
     * Test de lancement nominal
     */
    public void testNominal()
    {
        File jarDirectory = new File( "../squalix/lib/checkstyle" );
        File reportDirectory = new File( "." );
        try
        {
            long time = System.currentTimeMillis();
            CheckStyleProcess proc = new CheckStyleProcess( jarDirectory, reportDirectory, ParametersConstants.JAVA1_4 );
            File xmlFile = new File( "data/checkstyle/checkstyle_parsing.xml" );
            String[] sources = { "data/samples/testBatch/", "data/samples/testCommon/" };
            File resultFile = proc.analyseSources( xmlFile, sources, "checkstyle-report.xml" );
            assertNotNull( "Fichier existant", resultFile );
            assertTrue( "Fichier existant", resultFile.exists() );
            assertTrue( "Fichier récent", resultFile.lastModified() > time );
            assertFalse( "Pas d'erreur", proc.hasErrorOccurred() );
        }
        catch ( ConfigurationException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test de lancement avec répertoire inexistant
     */
    public void testNotExistingReportDir()
    {
        // Les répertoires n'existent pas mais checkstyle doit les créer
        File jarDirectory = new File( "../squalix/lib/checkstyle_bad" );
        File reportDirectory = new File( "/toto" );
        try
        {
            CheckStyleProcess proc = new CheckStyleProcess( jarDirectory, reportDirectory, ParametersConstants.JAVA1_4 );
            // mais il y a erreur car pas de jars
            File xmlFile = new File( "data/checkstyle/checkstyle_parsing.xml" );
            String[] sources = { "data/samples/testBatch/", "data/samples/testCommon/" };
            File resultFile = proc.analyseSources( xmlFile, sources, "checkstyle-report.xml" );
            assertTrue( "Erreur de traitement car jars inexistants", proc.hasErrorOccurred() );
        }
        catch ( ConfigurationException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }
}
