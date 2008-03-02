package com.airfrance.squalix;

import com.airfrance.squalix.core.PartitionRotationTest;
import com.airfrance.squalix.tools.ckjm.CkjmTaskTest;
import com.airfrance.squalix.tools.clearcase.ClearCaseConfigurationTest;
import com.airfrance.squalix.tools.compiling.java.JavaMockCompilingTaskTest;
import com.airfrance.squalix.tools.compiling.java.configuration.JCompilingConfigurationTest;
import com.airfrance.squalix.tools.compiling.java.parser.rsa.JRSAWebSettingsParserTest;
import com.airfrance.squalix.tools.computing.project.ComputeResultTaskTest;
import com.airfrance.squalix.tools.cpptest.CppTestAllTests;
import com.airfrance.squalix.tools.jdepend.JDependTaskTest;
import com.airfrance.squalix.tools.jspvolumetry.JSPVolumetryConfigurationTest;
import com.airfrance.squalix.tools.macker.MackerAllTests;
import com.airfrance.squalix.tools.pmd.PmdTaskTest;
import com.airfrance.squalix.tools.sourcecodeanalyser.SourceCodeAnalyserTaskTest;
import com.airfrance.squalix.util.file.FileUtilAllTests;
import com.airfrance.squalix.util.parser.ParserAllTests;
import com.airfrance.squalix.util.process.ProcessManagerTest;
import com.airfrance.squalix.util.repository.ComponentRepositoryTest;
import com.airfrance.squalix.util.stoptime.StopTimeHelperTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Tests de Squalix
 */
public class AllSqualixTests
{

    /**
     * @return la suite de tests
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite( "Test for SQUALIX project" );
        // $JUnit-BEGIN$
        /* script de partition */
        suite.addTest( new TestSuite( PartitionRotationTest.class ) );
        /* checkstyle */
        suite.addTest( com.airfrance.squalix.tools.checkstyle.AllTests.suite() );
        /* ckjm */
        suite.addTest( new TestSuite( CkjmTaskTest.class ) );
        /* clearcase (il manque un test) */
        suite.addTest( new TestSuite( ClearCaseConfigurationTest.class ) );
        /* Compilation java --> à compléter lorsque DINB aura installé le 1.5 */
        suite.addTest( new TestSuite( JCompilingConfigurationTest.class ) );
        suite.addTest( com.airfrance.squalix.tools.compiling.java.parser.configuration.AllTests.suite() );
        suite.addTest( com.airfrance.squalix.tools.compiling.java.parser.impl.AllTests.suite() );
        // RSA
        suite.addTest( new TestSuite( JRSAWebSettingsParserTest.class ) );
        // Mock
        suite.addTest( new TestSuite( JavaMockCompilingTaskTest.class ) );
        /* Compilation JSP */
        // suite.addTest(new TestSuite(JWSADJspTomcatCompilerTest.class));
        /* Calcul des résultats */
        suite.addTest( new TestSuite( ComputeResultTaskTest.class ) );
        /* cppTest */
        suite.addTest( CppTestAllTests.suite() );
        /* JDepend */
        suite.addTest( new TestSuite( JDependTaskTest.class ) );
        /* volumétrie des JSPs */
        suite.addTest( new TestSuite( JSPVolumetryConfigurationTest.class ) );
        /* macker */
        suite.addTest( MackerAllTests.suite() );
        /* mccabe */
        suite.addTest( com.airfrance.squalix.tools.mccabe.AllTests.suite() );
        /* pmd */
        suite.addTest( new TestSuite( PmdTaskTest.class ) );
        /* récupérateur de source en local */
        suite.addTest( new TestSuite( SourceCodeAnalyserTaskTest.class ) );
        /* UMLQuality */
        suite.addTest( com.airfrance.squalix.tools.umlquality.AllTests.suite() );
        /* Utilitaires */
        // CSV
        suite.addTest( com.airfrance.squalix.util.csv.AllTests.suite() );
        // manipulation des fichiers
        suite.addTest( FileUtilAllTests.suite() );
        // parser
        suite.addTest( ParserAllTests.suite() );
        // process
        suite.addTest( new TestSuite( ProcessManagerTest.class ) );
        // persistance des composants
        suite.addTest( new TestSuite( ComponentRepositoryTest.class ) );
        // arrêt du batch
        suite.addTest( new TestSuite( StopTimeHelperTest.class ) );
        // $JUnit-END$
        return suite;
    }
}
