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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;

/**
 * Test pour FileUtility
 */
public class FileUtilityTest
    extends SqualeTestCase
{

    /** chemin du répertoire contenant les fichiers compilés */
    public static final String CLASSES_DIR_PATH = "bin/org/squale/squalix/tools/computing";

    /** Le nombre de .class dans le répertoire CLASSES_DIR_PATH */
    public static final int NUMBER_OF_CLASSES = 1;

    /** Nom absolu d'un fichier compilé */
    public static final String ABSOLUTE_CLASS_PATH =
        "bin/org/squale/squalix/tools/computing/project/ComputeResultTaskTest.class";

    /**
     * Test pour createRecursiveListOfFiles
     */
    public void testCreateRecursiveListOfFiles()
    {
        HashSet files = new HashSet();
        File rootDir = new File( CLASSES_DIR_PATH );
        ExtensionFileFilter filter = new ExtensionFileFilter( ".class" );
        FileUtility.createRecursiveListOfFiles( rootDir, filter, files );
        assertEquals( NUMBER_OF_CLASSES, files.size() );
    }

    /**
     * Test pour getFileName
     * 
     * @throws IOException si erreur
     */
    public void testGetFileName()
        throws IOException
    {
        String fileName = FileUtility.getFileName( ABSOLUTE_CLASS_PATH );
        assertEquals( "ComputeResultTaskTest.java", fileName );
    }

    /**
     * Test pour getFileName
     * 
     * @throws IOException si erreur
     */
    public void findFilesWithPathSuffix()
        throws IOException
    {
        File directory = new File( "src" );
        String suffix = "org\\squale\\squalix\\tools\\ckjm\\CkjmtaskTest.java";
        File file = FileUtility.findFileWithPathSuffix( directory, suffix );
        assertEquals( "src\\org\\squale\\squalix\\tools\\ckjm\\CkjmtaskTest.java", file.getAbsolutePath() );
    }

    /**
     * Teste la copie d'un répertoire dans un autre
     * 
     * @throws IOException si erreur
     */
    public void testCopyInto()
        throws IOException
    {
        File src = new File( "lib" );
        File dest = new File( "data/copyInto" );
        FileUtility.copyIntoDir( src, dest );
    }

    /**
     * Teste la récupération des fichiers à analyser
     * 
     * @throws IOException si erreur
     */
    public void testGetIncludes()
        throws IOException
    {
        String viewPath = "data/samples/testWeb";
        ArrayList srcs = new ArrayList();
        File src1 = new File( viewPath, "JavaSource" );
        File src2 = new File( viewPath, "WebContent/jsp" );
        srcs.add( src1.getCanonicalPath().replaceAll( "\\\\", "/" ) );
        srcs.add( src2.getCanonicalPath().replaceAll( "\\\\", "/" ) );
        List files =
            FileUtility.getIncludedFiles( viewPath, srcs, null, buildExcludes(), buildExcludeDirs( viewPath ),
                                          new String[0] );
        final int count = 5;
        assertEquals( count, files.size() );
    }

    /**
     * Construit le paramètre EXCLUDED_PATTERNS
     * 
     * @return la liste des exclusions
     */
    private ListParameterBO buildExcludes()
    {
        ListParameterBO excludes = new ListParameterBO();
        StringParameterBO exclude = new StringParameterBO();
        exclude.setValue( "**/AbstractClass.java" );
        excludes.getParameters().add( exclude );
        return excludes;
    }

    /**
     * @param pViewPath le viewPath
     * @return la liste des répertoires exclus
     */
    private ListParameterBO buildExcludeDirs( String pViewPath )
    {
        ListParameterBO excludes = new ListParameterBO();
        StringParameterBO exclude = new StringParameterBO();
        exclude.setValue( "JavaSource/testExcluded" );
        excludes.getParameters().add( exclude );
        return excludes;
    }

}
