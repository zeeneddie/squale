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
package com.airfrance.squalix.util.file;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Chmod;
import org.apache.tools.ant.taskdefs.ExecuteOn;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.types.FileSet;
import org.codehaus.plexus.util.PathTool;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;

/**
 * Manipule les fichiers et répertoires d'analyse
 */
public final class FileUtility
{

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( FileUtility.class );

    /** L'extension d'un fichier java */
    public static final String COMPILED_FILE_EXTENSION = ".class";

    /**
     * Private constructor
     */
    private FileUtility()
    {
        
    }
    
    /**
     * Vérifie le contenu du dossier passé en paramètre et retourne la liste des fichiers filtrés
     * 
     * @param pFile le dossier à vérifier.
     * @param pFilter le filtre à respecter.
     * @param pFilesList la liste des fichier
     * @return la liste des fichiers validés.
     */
    public static Set createRecursiveListOfFiles( File pFile, FileFilter pFilter, Set pFilesList )
    {
        if ( null != pFile && pFile.isDirectory() && pFile.canRead() )
        {
            // On récupère tous les dossiers et les fichiers correspondant au pattern
            // des fichiers à analyser
            File[] list = pFile.listFiles( pFilter );
            String filename = null;
            // On parcours récursivement tous les dossiers pour stocker
            // tous les fichiers correspondant au pattern qu'ils contiennent
            for ( int i = 0; i < list.length; i++ )
            {
                if ( list[i].isFile() )
                {
                    // Pour lister les fichiers, on va utiliser le nom absolu
                    filename = list[i].getAbsolutePath();
                    pFilesList.add( filename );
                }
                else if ( list[i].isDirectory() )
                {
                    createRecursiveListOfFiles( list[i], pFilter, pFilesList );
                }
            }
        }
        return pFilesList;
    }

    /**
     * @param pViewPath le chemin vers la vue
     * @param pSrcs la liste des noms absolus des fichiers sources
     * @param pIncludes les patterns à inclure
     * @param pExcludes les patterns à exclure
     * @param pExcludedDirs les répertoires exclus de la compilation
     * @param pExtensions les extensions à conserver
     * @return la liste des fichiers qui ne sont pas exclus ou qui sont inclus parmi tous les fichiers des sources
     *         <code>pSrcs</code>. Les noms des fichiers sont relatifs au view path
     */
    public static List getIncludedFiles( String pViewPath, List pSrcs, ListParameterBO pIncludes,
                                         ListParameterBO pExcludes, ListParameterBO pExcludedDirs, String[] pExtensions )
    {
        // On récupère le pattern pour filtrer les extensions
        // On ne rajoute pas les patterns des extensions dans les patterns
        // inclus du scanner car il se peut que l'utilisateur veuille exclure
        // une extension autorisée.
        Pattern pattern = getPatternForExtensions( pExtensions );
        List includedFileNames = new ArrayList();
        // Pour chaque répertoire source, on va créer un DirectoryScanner
        // et ainsi construire les fichiers qui doivent être analysés
        DirectoryScanner scanner = new DirectoryScanner();
        // sensibilité à la casse
        scanner.setCaseSensitive( true );
        if ( null != pIncludes )
        {
            String[] includesTab = transformListInTab( pIncludes.getParameters() );
            scanner.setIncludes( includesTab );
        }
        List excludes = new ArrayList();
        if ( null != pExcludes )
        {
            String[] excludesTab = transformListInTab( pExcludes.getParameters() );
            scanner.setExcludes( excludesTab );
            excludes.addAll( Arrays.asList( excludesTab ) );
        }
        if ( null != pExcludedDirs )
        {
            // on va construire les patterns d'exclusions avec les noms des répertoires
            // On construit d'abord la regexp des sources car le chemin du répertoire
            // doit être relatif au répertoire source et non au projet pour le filset
            String sourcesRegexp = ( (String) pSrcs.get( 0 ) ).replaceFirst( pViewPath, "" ).replaceAll( "//", "/" );
            for ( int i = 1; i < pSrcs.size(); i++ )
            {
                sourcesRegexp += "|" + ( (String) pSrcs.get( i ) ).replaceFirst( pViewPath, "" ).replaceAll( "//", "/" );
            }
            for ( int i = 0; i < pExcludedDirs.getParameters().size(); i++ )
            {
                String dir = ( (StringParameterBO) pExcludedDirs.getParameters().get( i ) ).getValue();
                dir = dir.replaceFirst( sourcesRegexp, "" );
                // On ajoute le pattern d'exclusion
                String exDir = "**/" + dir + "/**";
                excludes.add( exDir.replaceAll( "//", "/" ) );
            }
            scanner.setExcludes( (String[]) excludes.toArray( new String[excludes.size()] ) );
        }
        for ( int i = 0; i < pSrcs.size(); i++ )
        {
            String src = (String) pSrcs.get( i );
            File srcFile = new File( src );
            // src doit être absolu : PRECONDITION
            try
            {
                if ( srcFile.isDirectory() )
                {
                    String baseName = srcFile.getCanonicalPath();
                    scanner.setBasedir( srcFile.getCanonicalFile() );
                    scanner.scan();
                    // On ajoute les noms absolus des fichiers inclus
                    addAbsoluteFileNames( baseName, scanner.getIncludedFiles(), includedFileNames, pattern );
                }
                else
                {
                    // On ajoute le nom absolu du fichier au fichiers inclus
                    includedFileNames.add( srcFile.getCanonicalPath().replaceAll( "\\\\", "/" ) );
                }
            }
            catch ( IOException ioe )
            {
                // On loggue juste l'erreur
                LOGGER.warn( ioe.getMessage() );
            }
        }
        return includedFileNames;
    }

    /**
     * Crée le fichier de log en incrémentant l'extension. ex : pFileName = logger.log Si logger existe -> logger.log.1
     * et ainsi de suite jusqu'à trouver l'extension qui n'existe pas
     * 
     * @param pFileName le nom absolu du fichier de log
     * @return le fichier de log avec l'extension appropriée
     */
    public static File getLogFile( String pFileName )
    {
        // On limite à 10 les logs
        final int maxLog = 10;
        File logger = new File( pFileName );
        // on essaye de créer ses répertoires
        logger.getParentFile().mkdirs();
        if ( logger.exists() )
        {
            int i = 1;
            // On récupère le nom et on en recrée un avec l'extension appropriée
            logger = new File( pFileName + "." + i );
            while ( i < maxLog && logger.exists() )
            {
                i++;
                logger = new File( pFileName + "." + i );
            }
        }
        return logger;
    }

    /**
     * @param pExtensions les extensions
     * @return le pattern associé
     */
    private static Pattern getPatternForExtensions( String[] pExtensions )
    {
        Pattern pattern = null;
        String regexp = "";
        if ( null != pExtensions )
        {
            for ( int i = 0; i < pExtensions.length; i++ )
            {
                regexp += "|.*\\." + pExtensions[i].replaceFirst( "\\.", "" );
            }
            if ( regexp.length() > 0 )
            {
                // on compile en supprimant le premier "|" inutile
                pattern = Pattern.compile( regexp.substring( 1 ), Pattern.CASE_INSENSITIVE );
            }
        }
        return pattern;
    }

    /**
     * @param pBaseName le absolu du répertoire parent aux fichier de pToAdd
     * @param pToAdd les fichiers à convertir et à ajouter à pContainer
     * @param pContainer la liste à compléter
     * @param pPattern le pattern pour les extensions
     * @throws IOException si erreur de fichier
     */
    private static void addAbsoluteFileNames( String pBaseName, String[] pToAdd, List pContainer, Pattern pPattern )
        throws IOException
    {
        // On parcours tous les fichier à ajouter afin de rechercher leur chemin absolu
        for ( int i = 0; i < pToAdd.length; i++ )
        {
            File file = new File( pBaseName, pToAdd[i] );
            // Le fichier doit exister et correspondre au pattern donné en paramètre
            if ( file.exists()
                && ( null == pPattern || ( null != pPattern && pPattern.matcher( file.getName() ).matches() ) ) )
            {
                // On converti le chemin au format UNIX
                pContainer.add( file.getCanonicalPath().replaceAll( "\\\\", "/" ) );
            }
        }
    }

    /**
     * Transforme une liste de StringParameterBO en tableau de String
     * 
     * @param pList la liste de StringParameterBO
     * @return la liste transformée en tableau de String
     */
    private static String[] transformListInTab( List pList )
    {
        final int size = pList.size();
        String[] patterns = new String[size];
        for ( int i = 0; i < size; i++ )
        {
            String pattern = ( (StringParameterBO) pList.get( i ) ).getValue();
            patterns[i] = pattern;
        }
        return patterns;
    }

    /**
     * Récupère le nom du fichier source dans lequel est enregistrée la classe correspondant au fichier compilé passé en
     * paramètre. PRECONDITION: Le fichier passé en paramètre doit être un fichier java compilé avec l'option -g:source.
     * Le nom du fichier source se trouve après le mot-clé "sourceFile". Deux cas possibles:<br/>
     * <ul>
     * <li>Le nom du fichier sur la même ligne : SourceFile"""sourceFileName.java</li>
     * <li>Le nom du fichier sur la ligne suivante : SourceFile""<br/>KiviatBO.java</li>
     * </ul>
     * 
     * @param pClassFileName le nom absolu du fichier compilé
     * @return le nom du fichier source correspondant
     * @throws IOException si erreur
     */
    public static String getFileName( String pClassFileName )
        throws IOException
    {
        ClassParser classParser = new ClassParser( pClassFileName );
        JavaClass javaClass = classParser.parse();
        String fileName = javaClass.getSourceFileName();
        return fileName;
    }

    /**
     * Supprime récursivement un fichier ou un dossier.
     * 
     * @param pFile la racine de l'arbre à effacer.
     * @return le résultat des suppressions.
     */
    public static boolean deleteRecursively( final File pFile )
    {
        boolean result = true;
        if ( pFile.isDirectory() )
        {
            File files[] = pFile.listFiles();
            for ( int i = 0; i < files.length; i++ )
            {
                result &= deleteRecursively( files[i] );
            }
        }
        result &= pFile.delete();
        return result;
    }

    /**
     * On ne supprime pas le répertoire racine, juste l'arborsecence
     * 
     * @param pFile le répertoire dont on veut supprimer l'arborescence
     * @return le résultat des suppressions.
     */
    public static boolean deleteRecursivelyWithoutDeleteRootDirectory( File pFile )
    {
        boolean result = true;
        if ( pFile.isDirectory() )
        {
            File files[] = pFile.listFiles();
            for ( int i = 0; i < files.length; i++ )
            {
                result &= deleteRecursively( files[i] );
            }
        }
        return result;

    }

    /**
     * @param pDirectory répertoire de recherche
     * @param pSuffix suffixe recherché
     * @return collection de java.io.File ayant l'extension
     */
    public static Collection findFilesWithSuffix( File pDirectory, String pSuffix )
    {
        Collection result = new ArrayList();
        File[] files = pDirectory.listFiles();
        if ( files != null )
        {
            for ( int i = 0; i < files.length; i++ )
            {
                File file = files[i];
                if ( file.isDirectory() )
                {
                    result.addAll( findFilesWithSuffix( file, pSuffix ) );
                }
                else if ( file.getName().endsWith( pSuffix ) )
                {
                    result.add( file );
                }
            }
        }
        return result;
    }

    /**
     * @param pDirectory répertoire de recherche
     * @param pSuffix suffixe recherché
     * @return le fichier dont le chemin absolu fini par pSuffix
     */
    public static File findFileWithPathSuffix( File pDirectory, String pSuffix )
    {
        // Pour avoir une comparaison indépendante entre les différents système d'exploitation
        // on remplace tous les "\" par des "/" unix
        String newSuffix = pSuffix.replace( '\\', '/' );
        File result = null;
        // On récupère tous les fichiers et dossiers du répertoire pDirectory
        File[] files = pDirectory.listFiles();
        if ( files != null )
        {
            // On parcours récursivement les répertoires
            // jusqu'à trouver le fichier dont le chemin absolu fini par pSuffix
            for ( int i = 0; i < files.length && result == null; i++ )
            {
                File file = files[i];
                if ( file.isDirectory() )
                {
                    result = findFileWithPathSuffix( file, newSuffix );
                }
                else if ( file.getAbsolutePath().replace( '\\', '/' ).endsWith( newSuffix ) )
                {
                    return file;
                }
            }
        }
        // Si aucun fichier ne correspond au critère, on renvoie null.
        return result;
    }

    /**
     * Récupère le nom absolu du fichier java compilé correspondant à la classe passé en paramètre parmi tous les noms
     * de fichier présents dans le set.
     * 
     * @param pFilesName la liste des noms des fichiers
     * @param pAbsoluteClassName le nom absolu de la classe
     * @return le nom absolu du fichier compilé de la classe <code>pAbsoluteClassName</code>
     */
    public static String getClassFileName( HashSet pFilesName, String pAbsoluteClassName )
    {
        // Le nom de la classe sans son package:
        String classPath = pAbsoluteClassName.replace( '.', File.separatorChar );
        Iterator it = pFilesName.iterator();
        String str = "";
        String result = null;
        boolean found = false;
        // Le nom du fichier compilé associé à la classe a pour nom: nomDeLaClasse.class
        while ( it.hasNext() && !found )
        {
            str = (String) it.next();
            found = str.endsWith( classPath + COMPILED_FILE_EXTENSION );
        }
        if ( found )
        {
            result = str;
        }
        return result;
    }

    /**
     * @param pPackageName le nom entièrement qualifié du package de la classe contenue dans <code>pFileName</code>
     * @param pFileName le nom du fichier source
     * @return le chemin relatif du fichier source
     */
    public static String buildEndOfFileName( String pPackageName, String pFileName )
    {
        // on construit le chemin avec le package de la classe et son nom de fichier source
        return pPackageName.replace( '.', File.separatorChar ) + File.separator + pFileName;
    }

    /**
     * Transforme une liste de path sous fome de String en un tableau de fichiers associés
     * 
     * @param pList la liste des sources à transformer
     * @param pViewPath le chemin de la vue
     * @return le tableau de fichiers
     */
    public static File[] transformListInFileTab( List pList, String pViewPath )
    {
        File[] result = new File[pList.size()];
        for ( int i = 0; i < result.length; i++ )
        {
            // on ajoute le viewPath et on supprime
            // les doublons de séparateur éventuellement présent
            String fileName = pViewPath + File.separator + ( (StringParameterBO) pList.get( i ) ).getValue();
            result[i] = new File( fileName.replaceAll( File.separator + File.separator, File.separator ) );
        }
        return result;

    }

    /**
     * @param pSources la liste des chemins vers les sources d'un projet
     * @param pEndOfFileName la fin du chemin du fichier source à chercher
     * @throws IOException si erreur
     * @return le nom absolu du fichier dont la fin se termine par pEndOfFileName
     */
    public static String getAbsoluteFileName( List pSources, String pEndOfFileName )
        throws IOException
    {
        String fileName = null;
        for ( int i = 0; i < pSources.size() && null == fileName; i++ )
        {
            File directory = new File( (String) pSources.get( i ) );
            if ( directory.isDirectory() )
            {
                // On recherche le fichier dans ce répertoires
                File file = findFileWithPathSuffix( directory, pEndOfFileName );
                if ( null != file )
                {
                    fileName = file.getCanonicalPath().replaceAll( "\\\\", "/" );
                }
            }
        }
        return fileName;
    }

    /**
     * Retire le préfixe du chemin du fichier.
     * 
     * @param pFilename le nom absolu du fichier à modifier.
     * @param pPrefix le préfixe à retirer.
     * @throws IOException si erreur
     * @return le chemin modifié.
     */
    public static String getRelativeFileName( String pFilename, String pPrefix )
        throws IOException
    {
        String result = pFilename;
        if ( null != result )
        {
            // On remplace les éventuels "\" par des "/" pour avoir un ensemble
            // cohérent dans la base et pouvoir faire des comparaisons sans risque
            result = pFilename.replace( '\\', '/' );
            String newPrefix = pPrefix;
            if ( null != newPrefix )
            {
                // On récupère le chemin absolu de la racine du projet
                // en unifiant les séparateurs pour la compraison
                newPrefix = pPrefix.replace( '\\', '/' );
            }
            if ( result.startsWith( newPrefix ) )
            {
                // Si le chemin débute par le chemin de la racine, on l'ampute
                result = result.substring( newPrefix.length() );
                if ( result.startsWith( "/" ) )
                {
                    // On l'enlève
                    result = result.substring( "/".length() );
                }
            }
        }
        return result;
    }

    /**
     * Copie tous les fichiers de pSrc dans pDest et désarchive les fichiers compressés
     * 
     * @param pSrc le fichier (compressé ou non) ou répertoire source
     * @param pDest le répertoire de destination qui existe déjà
     * @throws Exception si erreur
     */
    public static void copyAndExtractInto( File pSrc, File pDest )
        throws Exception
    {
        File currentSrc = new File( pDest + "/" + pSrc.getName() );
        if ( pSrc.isDirectory() )
        {
            copyIntoDir( pSrc, currentSrc );
        }
        else if ( pSrc.isFile() )
        {
            // On vérifie qu'il ne s'agit pas d'une archive
            String archiveType = ZipFileUtility.isArchiveFile( pSrc );
            if ( !( "" ).equals( archiveType ) )
            {
                ZipFileUtility.extractArchiveFile( pSrc, pDest, archiveType );
            }
            else
            {
                copyIntoDir( pSrc, pDest );
            }
        }
    }

    /**
     * Copy pSrc into pDest 
	 * <li>If pSrc is a <b>directory</b>, the directory is copied into pDest (to copy the content of the directory, 
	 * use use copyDirContentIntoDir instead) 
	 * <li>If pSrc is a <b>file</b>, the file is copied info pDest.
     * 
     * @param pSrc source file (compressed or not) or source directory
     * @param pDest target directory
     * @throws IOException si erreur de flux
     */
    public static void copyIntoDir( File pSrc, File pDest )
        /* ex copyInto */
        throws IOException
    {
        // On crée les répertoires
        pDest.mkdirs();
        // Copy is done using ant task
        Copy copyFile = new Copy();
        copyFile.setProject( new Project() );
        copyFile.init();
        if ( pSrc.isFile() )
        {
            copyFile.setFile( pSrc );
        }
        else
        {
            // This is a directory, fileset is requested by ant task
            FileSet files = new FileSet();
            files.setDir( pSrc.getAbsoluteFile().getParentFile() ); // il faut appeler .getAbsoluteFile car sinon ça
            														// peut rendre null
            files.setIncludes( pSrc.getAbsoluteFile().getName() + "/**" ); // idem
            copyFile.addFileset( files );
        }
        copyFile.setTodir( pDest );
        copyFile.execute();
    }

    /**
     * Copy pSrcDir content into pDest 
	 * The content of pSrcDir is copied into the pDest directory
     * 
     * @param pSrcDir source directory
     * @param pDest target directory
     * @throws IOException si erreur de flux
     */
    public static void copyDirContentIntoDir( File pSrcDir, File pDest )
        throws IOException
    {
        // On crée les répertoires
        pDest.mkdirs();
        // On récupère tous les dossiers et les fichiers correspondant au pattern
        // des fichiers à analyser
        File[] list = pSrcDir.listFiles();
        // On parcours récursivement tous les dossiers pour stocker
        // tous les fichiers correspondant au pattern qu'ils contiennent
        for ( int i = 0; i < list.length; i++ )
        {
            File file = list[i];
            copyIntoDir( file, pDest );
        }
    }

    /**
     * Copie le répertoire dans le répertoire source ou extrait l'archive dans le répertoire source.
     * 
     * @param pSrc le répertoire (compressé ou non) à copier
     * @param pDest le répertoire de destination
     * @throws Exception si erreur au niveau du traitement (fichier source n'existe pas...)
     */
    public static void copyOrExtractInto( File pSrc, File pDest )
        throws Exception
    {
        // On crée ce répertoire par sécurité
        pDest.mkdirs();
        if ( pSrc.isDirectory() )
        {
            // On copie tous le répertoire dans celui défini dans le fichier de configuration
            // sans désarchiver les archives si il y en a
            copyIntoDir( pSrc, pDest );
        }
        else
        {
            // Il s'agit d'une archive, il faut l'extraire dans le répertoire racine
            // Si ce n'est pas une archive, on lance une exception de configuration
            String archiveType = ZipFileUtility.isArchiveFile( pSrc );
            if ( ( "" ).equals( archiveType ) )
            {
                // On lance une exception
                String message = FileMessages.getString( "file.exception.file_not_correct" ) + pSrc.getAbsoluteFile();
                throw new IOException( message );
            }
            ZipFileUtility.extractArchiveFile( pSrc, pDest );
        }

        if ( pDest.exists() && pDest.isDirectory() )
        {
            // Chmod sur les scripts executable pour la target UNIX
            Chmod chmod = new Chmod();
            chmod.setProject( new Project() );
            chmod.setDir( pDest );
            chmod.setIncludes( FileMessages.getString( "file.pattern.executablefile" ) );
            chmod.setPerm( "ugo+rx" );
            chmod.execute();
        }
    }

    /**
     * Copie le fichier pSrc dans le fichier pDest
     * 
     * @param pSrc le fichier source
     * @param pDest le fichier de destination
     * @throws IOException si erreur de flux
     */
    public static void copyFile( File pSrc, File pDest )
        throws IOException
    {
        // Copy is done using ant task
        Copy copyFile = new Copy();
        copyFile.setProject( new Project() );
        copyFile.init();
        copyFile.setFile( pSrc );
        copyFile.setTofile( pDest );
        copyFile.execute();
    }

    /**
     * Permet de récupérer le fichier en absolu
     * 
     * @param pParent le chemin parent
     * @param pChild le fichier en relatif ou absolu
     * @return le fichier absolu
     */
    public static File getAbsoluteFile( String pParent, File pChild )
    {
        File result;
        // Si le fichier a un nom absolue et existe, on prend celui-ci
        if ( pChild.isAbsolute() && pChild.exists() )
        {
            result = pChild;
        }
        else
        {
            // Le fichier est supposé être relatif au projet
            result = new File( pParent, pChild.getPath() );
        }
        return result;
    }

    /**
     * This method search in a list of path the elements which endings with the nameToFind. It's useful for search a
     * complete path for a file for which there is only the end of the path.
     * 
     * @param cutPath List of path file
     * @param nameToFind The file name to find
     * @return return The element in the list which endings by the nameToFind
     */
    public static List findFileName( List cutPath, String nameToFind )
    {
        List result = new ArrayList();
        for ( int i = 0; i < cutPath.size(); i++ )
        {
            String compare = (String) cutPath.get( i );
            if ( compare.endsWith( nameToFind ) )
            {
                result.add( compare );
            }
        }
        return result;
    }

    /**
     * This method cut path. The string viewPathName is suppress from the beginning of the path. So the list of path
     * file pass in argument have to begin with the argument viewPathName. For each element in the list the method cut
     * the beginning of the path and create a new list.
     * 
     * @param includedFile List of path file to cut
     * @param viewPathName The string to suppress
     * @return the list of path file cut
     */
    public static List cutPath( List includedFile, String viewPathName )
    {
        List cutIncludedFile = new ArrayList();
        String ini;
        for ( int i = 0; i < includedFile.size(); i++ )
        {
            ini = (String) includedFile.get( i );
            String cut = PathTool.getRelativeFilePath( viewPathName, ini );
            cutIncludedFile.add( cut );
        }
        return cutIncludedFile;
    }

    /**
     * Add write rights to files,subdirectories or both according to the pMode from the pBaseDir 
     * (pBaseDir rights are not modified)
     * 
     * @param pBaseDir Base Directory in which the write rights will be added
     * @param pMode -> file,dir,both
     * @throws Exception Exception occured
     */
    public static void setWriteRights( File pBaseDir, String pMode )
        throws Exception
    {
        Chmod mchmod = new Chmod();
        mchmod.setProject( new Project() );
        mchmod.setDir( pBaseDir );
        ExecuteOn.FileDirBoth mMode = new ExecuteOn.FileDirBoth();
        mMode.setValue( pMode );
        mchmod.setType( mMode );
        mchmod.setIncludes( "**/*" );
        mchmod.setPerm( "u+rw" );
        mchmod.execute();
    }

    /**
     * Supprime tous les fichiers nommé pFileName dans le répertoire pBaseDir et ses sous-répertoires
     * 
     * @param pBaseDir le répertoire de recherche
     * @param pFileName le nom de fichier à supprimer
     * @return true si tous les occurences du fichier ont été supprimés, false sinon
     * @throws Exception exception occured
     */
    public static Boolean deleteFilesinPath( File pBaseDir, String pFileName )
        throws Exception
    {
        Collection listFilesToRemove = findFiles( pBaseDir, pFileName );
        Integer objectif = listFilesToRemove.size();
        Integer resultat = 0;
        Iterator it = listFilesToRemove.iterator();
        while ( it.hasNext() )
        {
            File current = (File) it.next();
            Boolean result = current.delete();
            if ( result.booleanValue() )
            {
                resultat++;
            }
        }
        Boolean out = false;
        if ( objectif.equals( resultat ) )
        {
            out = true;
        }
        return out;
    }

    /**
     * recherche tous les fichiers nommés pFileName dans le répertoire pBasedir et ses sous-répertoires
     * 
     * @param pDirectory le répertoire de recherche
     * @param pFileName le nomde fichier à rechercher
     * @return la liste des fichiers recherchés
     */
    public static Collection findFiles( File pDirectory, String pFileName )
    {
        Collection result = new ArrayList();
        File[] files = pDirectory.listFiles();
        if ( files != null )
        {
            for ( int i = 0; i < files.length; i++ )
            {
                File file = files[i];
                if ( file.isDirectory() )
                {
                    result.addAll( findFiles( file, pFileName ) );
                }
                else if ( file.getName().equalsIgnoreCase( pFileName ) )
                {
                    result.add( file );
                }
            }
        }
        return result;
    }

    /**
     * Will concatenate 2 paths and give the new canonicalPath
     * 
     * @param lookupPath a path
     * @param path the path to concatenate
     * @return The concatenated paths
     * @throws IOException If an I/O error occurs, which is possible because the construction of the canonical pathname
     *             may require filesystem queries
     */
    public static String catPath( final String lookupPath, final String path )
        throws IOException
    {
        File beginPath = new File( lookupPath );
        File endPath = new File( path );
        File finalPath = new File( beginPath.getPath().concat( File.separator.concat( endPath.getPath() ) ) );
        return finalPath.getCanonicalPath();
    }

}
