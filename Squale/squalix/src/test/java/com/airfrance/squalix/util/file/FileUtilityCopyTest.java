package com.airfrance.squalix.util.file;

import java.io.File;

import junit.framework.TestCase;

/**
 * Test copy utility of class FileUtilityCopy
 */
public class FileUtilityCopyTest
    extends TestCase
{

	/**
	 * test copyIntoDir
	 * @throws Exception no exception shall be raised by this test (this is a failure case).
	 */
    public void testcopyIntoDir()
        throws Exception
    {
        // Temp dir
        File tempDir = new File( System.getProperty( "java.io.tmpdir" ), "TestCopy" );

        // create source folder with file in it
        File srcDir = new File( tempDir, "SourceDir" );
        if ( srcDir.exists() )
        {
            FileUtility.deleteRecursively( srcDir );
        }
        srcDir.mkdirs();
        File aFile = new File( srcDir, "test.txt" );
        aFile.createNewFile();

        // create destination folder
        File destDir = new File( tempDir, "DestDir" );
        if ( destDir.exists() )
        {
            FileUtility.deleteRecursively( destDir );
        }

        FileUtility.copyIntoDir( srcDir, destDir );
        // this method is supposed to copy the folder srcDir into destDir
        assertTrue( (new File(destDir, "SourceDir/test.txt")).exists() );
        
        FileUtility.deleteRecursively(tempDir);
    }

	/**
	 * test copyContentIntoDir
	 * @throws Exception no exception shall be raised by this test (this is a failure case).
	 */
    public void testcopyContentIntoDir()
        throws Exception
    {
        // Temp dir
        File tempDir = new File( System.getProperty( "java.io.tmpdir" ), "TestCopy" );

        // create source folder with file in it
        File srcDir = new File( tempDir, "SourceDir" );
        if ( srcDir.exists() )
        {
            FileUtility.deleteRecursively( srcDir );
        }
        srcDir.mkdirs();
        File aFile = new File( srcDir, "test.txt" );
        aFile.createNewFile();

        // create destination folder
        File destDir = new File( tempDir, "DestDir" );
        if ( destDir.exists() )
        {
            FileUtility.deleteRecursively( destDir );
        }
        destDir.mkdirs();
        
        FileUtility.copyDirContentIntoDir( srcDir, destDir );
        // this method is supposed to copy the content of the folder srcDir into destDir
        assertTrue( (new File(destDir, "test.txt")).exists() );
        
        FileUtility.deleteRecursively(tempDir);
    }
}
