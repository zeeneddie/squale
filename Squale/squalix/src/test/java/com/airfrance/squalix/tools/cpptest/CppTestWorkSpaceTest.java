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
package com.airfrance.squalix.tools.cpptest;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import com.airfrance.squalecommon.SqualeTestCase;

/**
 * Test unitaire de CppTestWorkSpace
 */
public class CppTestWorkSpaceTest
    extends SqualeTestCase
{

    /** Racine du répertoire */
    private File mRoot;

    /**
     * Test du constructeur
     */
    public void testConstructor()
    {
        File root = mRoot;
        try
        {
            assertFalse( "répertoire racine n'existe pas", root.exists() );
            CppTestWorkSpace workspace = new CppTestWorkSpace( root );
            assertTrue( "Répertoire de rapport créé", workspace.getReportDirectory().exists() );
            assertTrue( "répertoire du projet créé", workspace.getProjectFile().getParentFile().exists() );
            assertFalse( "fichier projet non créé", workspace.getProjectFile().exists() );
            workspace.cleanup();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test du cleanup
     */
    public void testCleanup()
    {
        try
        {
            assertFalse( "workspace non créé", mRoot.exists() );
            CppTestWorkSpace workspace = new CppTestWorkSpace( mRoot );
            assertTrue( "workspace créé", mRoot.exists() );
            // On fait le cleanup
            workspace.cleanup();
            assertFalse( "workspace détruit", mRoot.exists() );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test de la recherche des fichiers de rapport
     */
    public void testGetReportFiles()
    {
        CppTestWorkSpace workspace = null;
        try
        {
            workspace = new CppTestWorkSpace( mRoot );
            Collection coll = workspace.getReportFiles();
            assertEquals( "pas de rapport présent", 0, coll.size() );
            // Création articifielle d'un rapport
            File.createTempFile( "report", CppTestWorkSpace.REPORT_EXTENSION, workspace.getReportDirectory() );
            coll = workspace.getReportFiles();
            assertEquals( "un rapport présent", 1, coll.size() );
            // Création articifielle d'un deuxième rapport
            File.createTempFile( "report", CppTestWorkSpace.REPORT_EXTENSION, workspace.getReportDirectory() );
            coll = workspace.getReportFiles();
            assertEquals( "deux rapports présents", 2, coll.size() );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
        finally
        {
            if ( workspace != null )
            {
                workspace.cleanup();
            }
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp()
        throws Exception
    {
        super.setUp();
        mRoot = new File( "squalixTest" + System.currentTimeMillis() );
    }

}
