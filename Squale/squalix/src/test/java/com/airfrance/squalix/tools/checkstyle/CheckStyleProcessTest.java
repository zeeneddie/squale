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
            File sources = new File("data/samples");
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
            File sources = new File( "data/samples/testBatch/");
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
