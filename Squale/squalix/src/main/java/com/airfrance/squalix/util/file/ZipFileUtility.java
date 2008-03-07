package com.airfrance.squalix.util.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.BUnzip2;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.GUnzip;
import org.apache.tools.ant.taskdefs.Untar;

/**
 * Utilitaire pour les fichiers compressés Le type d'archive est connu en exécutant la commande 'file' d'unix. Dans le
 * cas où cette commande échouerait (si l'exécution se fait sous windows par exemple), on reconnait le type grâce à
 * l'extension du fichier.
 */
public class ZipFileUtility
{
    // Résultat renvoyé par la commande 'file' d'unix
    /** Résultat pour les archives tar posix */
    public static final String POSIX_TAR_RESULT = "POSIX tar archive";

    /** Résultat pour les archives tar */
    public static final String TAR_RESULT = "USTAR tar archive";

    /** Résultat pour les fichiers zippés avec GZip */
    public static final String GZIP_RESULT = "gzip compressed data";

    /** Résultat pour les fichiers zippés avec BZip2 */
    public static final String BZIP2_RESULT = "bzip2 compressed data";

    /** Résultat pour une archive zip */
    public static final String ZIP_RESULT = "ZIP archive";

    /** Résultat pour une archive jar */
    public static final String JAR_RESULT = "java program";

    /** Résultats d'archives */
    public static final String[] ARCHIVE_RESULTS =
        { POSIX_TAR_RESULT, TAR_RESULT, GZIP_RESULT, BZIP2_RESULT, ZIP_RESULT, JAR_RESULT };

    // Dans le cas où la commande file échoue, on utilisera les extensions du fichier
    /** Extension d'une archive tar */
    public static final String TAR_EXTENSION = ".tar";

    /** Extension d'une archive jar */
    public static final String JAR_EXTENSION = ".jar";

    /** Extension d'une archive GZip */
    public static final String GZIP_EXTENSION = ".gz";

    /** Extension d'une archive BZip2 */
    public static final String BZIP2_EXTENSION = ".bz2";

    /** Extension d'une archive zip */
    public static final String ZIP_EXTENSION = ".zip";

    /** Extension d'une archive ear */
    public static final String EAR_EXTENSION = ".ear";

    /** Extension d'une archive war */
    public static final String WAR_EXTENSION = ".war";

    /** Extension d'une archive targz */
    public static final String TGZ_EXTENSION = ".tgz"; // car courante

    /** Extensions d'archives reconnues */
    public static final String[] ARCHIVE_EXTENSIONS =
        { TAR_EXTENSION, JAR_EXTENSION, GZIP_EXTENSION, BZIP2_EXTENSION, ZIP_EXTENSION, EAR_EXTENSION, WAR_EXTENSION,
            TGZ_EXTENSION };

    /**
     * Extrait les fichiers et répertoires contenus dans pArchive dans le répertoire de destination pDest PRECONDITION :
     * pArchive et pDest existent
     * 
     * @param pArchive l'archive à décompresser
     * @param pDest le répertoire de destination
     * @throws Exception si erreur
     */
    public static void extractArchiveFile( File pArchive, File pDest )
        throws Exception
    {
        // On va appeler différentes méthodes en fonction du type de l'archive
        // On récupère le type de pSrc avec la commande unix "file"
        // On traite à part le cas des .tgz car commande courante mais pas même traitement
        String archiveType = getExtension( pArchive.getName() );
        if ( !archiveType.equalsIgnoreCase( TGZ_EXTENSION ) )
        {
            archiveType = isArchiveFile( pArchive );
        }
        if ( !( "" ).equals( archiveType ) )
        {
            // On extrait l'archive
            extractArchiveFile( pArchive, pDest, archiveType );
        }
    }

    /**
     * Extrait les fichiers et répertoires contenus dans pArchive dans le répertoire de destination pDest PRECONDITION :
     * pArchive et pDest existent
     * 
     * @param pArchive l'archive à décompresser
     * @param pDest le répertoire de destination
     * @param pArchiveType le résulat de la commande 'file' d'unix
     * @throws Exception si erreur
     */
    public static void extractArchiveFile( File pArchive, File pDest, String pArchiveType )
        throws Exception
    {
        // En fonction du type d'archive, on utilise la tâche ant appropriée
        if ( isZipFile( pArchiveType ) )
        {
            // .zip
            unZip( pArchive, pDest );
        }
        else if ( isTarFile( pArchiveType ) )
        {
            // .tar
            extractTarFile( pArchive, pDest );
        }
        else if ( isGZipFile( pArchiveType ) )
        {
            // .gz
            unGZip( pArchive, pDest );
        }
        else if ( isBZip2File( pArchiveType ) )
        {
            // .bz2
            unBZip2( pArchive, pDest );
        }
        else
        {
            // cas où l'archive n'est pas géré par SQUALE
            String message = FileMessages.getString( "exception.not_archive_file", pArchive.getAbsolutePath() );
            throw new IOException( message );
        }
        // On récupère le répertoire de d'extraction
        File destFile = getDestFile( pDest, pArchive.getName() );
        // On supprime l'archive que l'on vient d'extraire car on en a plus besoin
        // si pDest est un parent
        File newArchive = pArchive;
        if ( newArchive.getAbsolutePath().startsWith( pDest.getAbsolutePath() ) )
        {
            newArchive.delete();
        }
        newArchive = destFile;
        // car par exemple d'un .tar.gz
        extractArchiveFile( newArchive, pDest );
    }

    /**
     * @param pArchiveType le résultat de la commande file ou l'extension de l'archive
     * @return si il s'agit d'une archive à dézipper avec la méthode <code>extractZipFile</code>
     */
    private static boolean isZipFile( String pArchiveType )
    {
        // Dans le cas de la commande file
        boolean result = pArchiveType.lastIndexOf( ZIP_RESULT ) != -1 || pArchiveType.lastIndexOf( JAR_RESULT ) != -1;
        // Dans le cas des extensions
        result =
            result || pArchiveType.equalsIgnoreCase( ZIP_EXTENSION ) || pArchiveType.equalsIgnoreCase( EAR_EXTENSION )
                || pArchiveType.equalsIgnoreCase( WAR_EXTENSION ) || pArchiveType.equalsIgnoreCase( JAR_EXTENSION );
        return result;
    }

    /**
     * @param pArchiveType le résultat de la commande file ou l'extension de l'archive
     * @return si il s'agit d'une archive compressée avec BZip2
     */
    private static boolean isBZip2File( String pArchiveType )
    {
        // Dans le cas de la commande file
        boolean result = pArchiveType.lastIndexOf( BZIP2_RESULT ) != -1;
        // Dans le cas des extensions
        result = result || pArchiveType.equalsIgnoreCase( BZIP2_EXTENSION );
        return result;
    }

    /**
     * @param pArchiveType le résultat de la commande file ou l'extension de l'archive
     * @return si il s'agit d'une archive compressée avec GZip
     */
    private static boolean isGZipFile( String pArchiveType )
    {
        // Dans le cas de la commande file
        boolean result = pArchiveType.lastIndexOf( GZIP_RESULT ) != -1;
        // Dans le cas des extensions
        result = result || pArchiveType.equalsIgnoreCase( GZIP_EXTENSION );
        return result;
    }

    /**
     * @param pArchiveType le résultat de la commande file ou l'extension de l'archive
     * @return si il s'agit d'une archive tar
     */
    private static boolean isTarFile( String pArchiveType )
    {
        // Dans le cas de la commande file
        boolean result =
            pArchiveType.lastIndexOf( TAR_RESULT ) != -1 || pArchiveType.lastIndexOf( POSIX_TAR_RESULT ) != -1;
        // Dans le cas des extensions on considère aussi que le .tgz est un tar
        // car on va utiliser untar avec l'option de décompression
        result =
            result || pArchiveType.equalsIgnoreCase( TAR_EXTENSION ) || pArchiveType.equalsIgnoreCase( TGZ_EXTENSION );
        ;
        return result;
    }

    /**
     * @param pZip le fichier à dézipper
     * @param pDest le répertoire de destination
     */
    private static void unZip( File pZip, File pDest )
    {
        Expand expander = new Expand();
        expander.setProject( new Project() );
        expander.init();
        expander.setSrc( pZip );
        expander.setDest( pDest );
        expander.execute();
    }

    /**
     * @param pZip le fichier à dézipper
     * @param pDest le répertoire de destination
     */
    private static void unBZip2( File pZip, File pDest )
    {
        BUnzip2 unzip = new BUnzip2();
        unzip.setProject( new Project() );
        unzip.init();
        unzip.setDest( pDest );
        unzip.setSrc( pZip );
        unzip.execute();
    }

    /**
     * @param pZip le fichier à dézipper
     * @param pDest le répertoire de destination
     */
    private static void unGZip( File pZip, File pDest )
    {
        GUnzip unzip = new GUnzip();
        unzip.setProject( new Project() );
        unzip.init();
        unzip.setDest( pDest );
        unzip.setSrc( pZip );
        unzip.execute();
    }

    /**
     * @param pTar le tar à décompresser
     * @param pDest le répertoire de destination
     */
    private static void extractTarFile( File pTar, File pDest )
    {
        Untar untar = new Untar();
        untar.setProject( new Project() );
        untar.init();
        untar.setDest( pDest );
        untar.setSrc( pTar );
        // cas spécial d'un .tgz
        if ( getExtension( pTar.getName() ).equalsIgnoreCase( TGZ_EXTENSION ) )
        {
            Untar.UntarCompressionMethod mode = new Untar.UntarCompressionMethod();
            mode.setValue( "gzip" );
            untar.setCompression( mode );
        }
        untar.execute();

    }

    /**
     * @param pParentFile le fichier parent
     * @param pFileName le nom du fichier
     * @return le fichier extrait
     */
    private static File getDestFile( File pParentFile, String pFileName )
    {
        String destFileName = getFileNameWithoutExtension( pFileName );
        File pDestFile = new File( pParentFile, destFileName );
        return pDestFile;
    }

    /**
     * @param pFileName le nom du fichier
     * @return le nom du fichier sans son extension si extesion il y a
     */
    private static String getFileNameWithoutExtension( String pFileName )
    {
        String filename = pFileName;
        int lastDot = pFileName.lastIndexOf( "." );
        if ( lastDot != -1 )
        {
            filename = pFileName.substring( 0, lastDot );
        }
        return filename;
    }

    /**
     * @param pFile le fichier à vérifier
     * @return l'exécution de la commande "type" d'unix si pFile est une archive une chaîne vide sinon.
     * @throws IOException si erreur de flux
     * @throws InterruptedException si erreur lors du lancement de la commande file d'unix
     */
    public static String isArchiveFile( File pFile )
        throws IOException, InterruptedException
    {
        String cmdResult = "";
        Runtime runtime = Runtime.getRuntime();
        // On utilise la commande unix "file" pour connaître le type d'un fichier
        String cmd = "file " + pFile.getAbsolutePath();
        try
        {
            Process process = runtime.exec( cmd );
            process.waitFor();
            // On récupère le résultat
            InputStream cmdInput = process.getInputStream();
            int reader;
            while ( ( reader = cmdInput.read() ) != -1 )
            {
                cmdResult += (char) reader;
            }
            cmdInput.close();
            boolean isArchive = false;
            // C'est une archive si le type est présent dans les types répertoriés
            // dans le tableau de constantes ARCHIVE_RESULTS
            for ( int i = 0; i < ARCHIVE_RESULTS.length && !isArchive; i++ )
            {
                if ( cmdResult.lastIndexOf( ARCHIVE_RESULTS[i] ) != -1 )
                {
                    isArchive = true;
                }
            }
            // Si il ne s'agit pas d'une archive, on retourne una chaîne vide
            if ( !isArchive )
            {
                cmdResult = "";
            }
        }
        catch ( IOException ioe )
        {
            cmdResult = "";
        }
        // si la commande n'est pas exécutée avec succès ou qu'on n'a pas trouvé l'extension
        // on recherche l'extension par le nom du fichier
        // Le résulat sera alors l'extension du fichier
        // ou vide si le fichier n'a pas d'extension ou si l'extension n'est pas reconnu
        if ( cmdResult.length() == 0 )
        {
            cmdResult = getExtension( pFile.getName() );
            if ( !isKnownExtension( cmdResult ) )
            {
                cmdResult = "";

            }
        }
        return cmdResult;
    }

    /**
     * @param pExtension l'extension à vérifier
     * @return true si l'extension est reconnue comme étant l'extension d'une archive
     */
    private static boolean isKnownExtension( String pExtension )
    {
        boolean isKnown = false;
        for ( int i = 0; i < ARCHIVE_EXTENSIONS.length && !isKnown; i++ )
        {
            if ( pExtension.equalsIgnoreCase( ARCHIVE_EXTENSIONS[i] ) )
            {
                isKnown = true;
            }
        }
        return isKnown;
    }

    /**
     * @param pFilename le nom du fichier
     * @return l'extension si elle existe
     */
    private static String getExtension( String pFilename )
    {
        String extension = "";
        int lastDot = pFilename.lastIndexOf( "." );
        if ( lastDot > 0 )
        {
            extension = pFilename.substring( lastDot );
        }
        return extension;
    }
}