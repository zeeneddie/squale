package com.airfrance.squalix.util.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;

/**
 * Test pour FileUtility
 */
public class FileUtilityTest
    extends SqualeTestCase
{

    /** chemin du répertoire contenant les fichiers compilés */
    public static final String CLASSES_DIR_PATH = "bin/com/airfrance/squalix/tools/computing";

    /** Le nombre de .class dans le répertoire CLASSES_DIR_PATH */
    public static final int NUMBER_OF_CLASSES = 1;

    /** Nom absolu d'un fichier compilé */
    public static final String ABSOLUTE_CLASS_PATH =
        "bin/com/airfrance/squalix/tools/computing/project/ComputeResultTaskTest.class";

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
        String suffix = "com\\airfrance\\squalix\\tools\\ckjm\\CkjmtaskTest.java";
        File file = FileUtility.findFileWithPathSuffix( directory, suffix );
        assertEquals( "src\\com\\airfrance\\squalix\\tools\\ckjm\\CkjmtaskTest.java", file.getAbsolutePath() );
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
