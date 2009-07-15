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
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.tools.ant.taskdefs.optional.jsp.JspNameMangler;

import org.squale.squalix.tools.compiling.jsp.configuration.JspCompilingConfiguration;

/**
 * Utilitaire pour la manipulation des fichiers JSP
 */
public class JspFileUtility
{

    /** Extension des pages JSP */
    public static final String JSP_EXTENSION = ".jsp";

    /**
     * Java keywords
     */
    private static final String JAVA_KEYWORDS[] =
        { "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue",
            "default", "do", "double", "else", "enum", "extends", "final", "finally", "float", "for", "goto", "if",
            "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "package", "private",
            "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this",
            "throws", "transient", "try", "void", "volatile", "while" };

    /**
     * Dans le cas des compilation des jsps, un nom de package ou classe interdit en java mais autorisé pour les JSPs
     * est renommé.<br/> Par exemple <b>package
     * </p>
     * sera renommé en <b>package%</b> lors de la compilation des .jsp en .java.<br/> Ensuite les noms de contenant
     * des caractères spéciaux sont renommés.<br/> Par exemple notre <b>package%</b> sera renommé en <b>package_0025</b>
     * 
     * @param pJspPaths les répertoires contenant les jsps
     * @param pFullName le nom absolue d'une classe jsp compilée
     * @throws IOException si erreur
     * @return le nom absolu du fichier correpondant à la JSP
     */
    public static String getAbsoluteJspFileName( List pJspPaths, String pFullName )
        throws IOException
    {
        String fileName = null;
        boolean search = true;
        // On va examiner tout les noms de package ainsi que le nom
        // de la classe
        String[] decomposed = pFullName.split( "\\." );
        String covertedDirName = "";
        // Le premier package indique l'index du répertoire source dans la liste donnée en paramètre
        // ex : jsp -> index 0; jsp1 -> index1
        for ( int i = 0; i < decomposed.length - 1; i++ )
        {
            covertedDirName += decomposed[i] + "/";
        }
        covertedDirName += decomposed[decomposed.length - 1] + JSP_EXTENSION;
        String firstPackage = pFullName.substring( 0, pFullName.indexOf( "." ) );
        File directory = new File( (String) pJspPaths.get( getJspDirectoryId( pJspPaths, decomposed[1] ) ) );
        Set files = new HashSet();
        files =
            FileUtility.createRecursiveListOfFiles( directory, new ConvertFileNameFilter( directory, covertedDirName ),
                                                    files );
        if ( files.size() > 0 )
        {
            fileName = (String) files.iterator().next();
        }
        return fileName;
    }

    /**
     * @param pJspPaths la liste des noms des chemins vers les jsps
     * @param pJspDir le nom du répertoire des jsps à trouver
     * @return l'index du répertoire correspondant
     */
    public static int getJspDirectoryId( List pJspPaths, String pJspDir )
    {
        // On récupère l'index du répertoire pJspDir
        int index = 0;
        try
        {
            // On tente de récupérer l'index du package
            index = Integer.parseInt( pJspDir.replaceFirst( JspCompilingConfiguration.FIRST_PACKAGE, "" ) );
        }
        catch ( NumberFormatException nbfe )
        {
            // Si erreur c'est qu'il n'y a pas de chiffre à la fin du nom --> index 0
            index = 0;
        }
        return index;
    }

    /**
     * Converti un nom de classe ou package avec l'algorithme utilisé par JspC. Code copié de JspNameMangler.java de ant
     * 
     * @param pName le nom à convertir
     * @return le nom converti
     */
    public static String convertWithJspNameMangler( String pName )
    {
        // since we don't mangle extensions like the servlet does,
        // we need to check for keywords as class names
        String newName = pName;
        for ( int i = 0; i < JspNameMangler.keywords.length && !newName.endsWith( "%" ); ++i )
        {
            if ( newName.equals( JspNameMangler.keywords[i] ) )
            {
                newName += "%";
            }
        }

        // Fix for invalid characters. If you think of more add to the list.
        StringBuffer modifiedClassName = new StringBuffer( newName.length() );
        // first char is more restrictive than the rest
        char firstChar = newName.charAt( 0 );
        if ( Character.isJavaIdentifierStart( firstChar ) )
        {
            modifiedClassName.append( firstChar );
        }
        else
        {
            modifiedClassName.append( mangleChar( firstChar ) );
        }
        // this is the rest
        for ( int i = 1; i < newName.length(); i++ )
        {
            char subChar = newName.charAt( i );
            if ( Character.isJavaIdentifierPart( subChar ) )
            {
                modifiedClassName.append( subChar );
            }
            else
            {
                modifiedClassName.append( mangleChar( subChar ) );
            }
        }
        return modifiedClassName.toString();
    }

    /**
     * Algorithme de conversion de JspC. Code copié de JspNameMangler.java de ant.
     * 
     * @param ch char to mangle
     * @return mangled string; 5 digit hex value
     */
    private static final String mangleChar( char ch )
    {
        // La conversion du caractère spécial sera de 6 caractères
        final int nbChar = 6;
        char newChar = ch;
        if ( newChar == File.separatorChar )
        {
            newChar = '/';
        }
        // Il converti en hexadécimal précédé de "_"
        // et d'autant de zéro que nécessaire pour compléter les 6
        // caractères
        String s = Integer.toHexString( newChar );
        int nzeros = ( nbChar - 1 ) - s.length();
        char[] result = new char[nbChar];
        result[0] = '_';
        for ( int i = 1; i <= nzeros; ++i )
        {
            result[i] = '0';
        }
        int resultIndex = 0;
        for ( int i = nzeros + 1; i < nbChar; ++i )
        {
            result[i] = s.charAt( resultIndex++ );
        }
        return new String( result );
    }

    /**
     * Filtre sur les répertoires des JSPs pour ne trouver que le fichier correspondant au nom converti
     */
    static class ConvertFileNameFilter
        implements FileFilter
    {

        /** Chemin racine à ne pas convertir */
        private File mRoot;

        /** chemin de la jsp éventuellement converti */
        private String mPath;

        /**
         * Constructeur
         * 
         * @param pRoot le chemin racine de la jsp
         * @param pPath le nom du fichier éventuellement converti par JspC
         */
        public ConvertFileNameFilter( File pRoot, String pPath )
        {
            mRoot = pRoot;
            mPath = pPath;
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.io.FileFilter#accept(java.io.File)
         */
        public boolean accept( File pathname )
        {
            boolean result = false;
            if ( pathname.isDirectory() )
            {
                result = true;
            }
            else if ( pathname.isFile() && pathname.getName().endsWith( JSP_EXTENSION ) )
            {
                // si il s'agit d'une jsp, on vérifie qu'elle ne correspond pas
                // à la jsp convertie
                String convertedPath = convertPath( pathname );
                if ( convertedPath.endsWith( mPath ) )
                {
                    result = true;
                }
            }
            return result;
        }

        /**
         * @param pPathname le nom du fichier à convertir
         * @return le nom converti
         */
        private String convertPath( File pPathname )
        {
            String result = "";
            try
            {
                // On remplace les séparateur de fichier pour avoir une cohérence dans les comparaisons
                String pathname = pPathname.getCanonicalPath().replaceAll( "\\\\", "/" );
                String rootPathname = mRoot.getCanonicalPath().replaceAll( "\\\\", "/" );
                // On récupère le chemin relatif par rapport au répertoire racine de la jsp
                String relativePath = pathname.replaceFirst( rootPathname, "" );
                // On supprime le "/" au début et l'extension ".jsp" car il ne faut pas les convertir
                relativePath = relativePath.substring( 1, relativePath.length() - JSP_EXTENSION.length() );
                // On parcours l'ensemble du chemin pour convertir tous les répertoires
                String[] paths = relativePath.split( "/" );
                for ( int i = 0; i < paths.length - 1; i++ )
                {
                    result += convertWithJspNameMangler( paths[i] ) + "/";
                }
                // On convertit le nom de la jsp et on lui rajoute son extension supprimée plus haut
                result += convertWithJspNameMangler( paths[paths.length - 1] ) + JSP_EXTENSION;
            }
            catch ( IOException e )
            {
                // Si une erreur survient, le fichier n'est pas correcte
                result = "";
            }
            return result;
        }

    }

    /**
     * @param basePackageName root package for this jsp
     * @param jspUri jsp relative path
     * @return full classname for this jsp
     * @see tomcat source code
     */
    public static String getJspFullClassName( String basePackageName, String jspUri )
    {
        int iSep = jspUri.lastIndexOf( '/' ) + 1;
        String className = makeJavaIdentifier( jspUri.substring( iSep ) );
        String packageName = getServletPackageName( basePackageName, jspUri );
        return packageName + "." + className;
    }

    /**
     * Package name for the generated class is make up of the base package name, which is user settable, and the derived
     * package name. The derived package name directly mirrors the file heirachy of the JSP page.
     * 
     * @see tomcat source code
     */
    public static String getServletPackageName( String basePackageName, String jspUri )
    {
        String result = basePackageName;
        String dPackageName = getDerivedPackageName( jspUri );
        if ( dPackageName.length() > 0 )
        {
            result += "." + dPackageName;
        }
        return result;
    }

    /**
     * @param jspUri relative jsp path
     * @return package's name converted by Tomcat
     * @see Tomcat source code
     */
    private static String getDerivedPackageName( String jspUri )
    {
        String derivedPackageName = null;
        int iSep = jspUri.lastIndexOf( '/' );
        derivedPackageName = ( iSep > 0 ) ? makeJavaPackage( jspUri.substring( 0, iSep ) ) : "";
        return derivedPackageName;
    }

    /**
     * Converts the given path to a Java package or fully-qualified class name
     * 
     * @param path Path to convert
     * @return Java package corresponding to the given path
     */
    public static final String makeJavaPackage( String path )
    {
        String classNameComponents[] = path.split( "/" );
        StringBuffer legalClassNames = new StringBuffer();
        for ( int i = 0; i < classNameComponents.length; i++ )
        {
            legalClassNames.append( makeJavaIdentifier( classNameComponents[i] ) );
            if ( i < classNameComponents.length - 1 )
            {
                legalClassNames.append( '.' );
            }
        }
        return legalClassNames.toString();
    }

    /**
     * Converts the given identifier to a legal Java identifier
     * 
     * @param identifier Identifier to convert
     * @return Legal Java identifier corresponding to the given identifier
     */
    public static final String makeJavaIdentifier( String identifier )
    {
        StringBuffer modifiedIdentifier = new StringBuffer( identifier.length() );
        if ( !Character.isJavaIdentifierStart( identifier.charAt( 0 ) ) )
        {
            modifiedIdentifier.append( '_' );
        }
        for ( int i = 0; i < identifier.length(); i++ )
        {
            char ch = identifier.charAt( i );
            if ( Character.isJavaIdentifierPart( ch ) && ch != '_' )
            {
                modifiedIdentifier.append( ch );
            }
            else if ( ch == '.' )
            {
                modifiedIdentifier.append( '_' );
            }
            else
            {
                modifiedIdentifier.append( mangleChar( ch ) );
            }
        }
        if ( isJavaKeyword( modifiedIdentifier.toString() ) )
        {
            modifiedIdentifier.append( '_' );
        }
        return modifiedIdentifier.toString();
    }

    /**
     * Test whether the argument is a Java keyword
     * 
     * @param key the key to test
     * @return true if <code>key</code> is a java keyword
     */
    public static boolean isJavaKeyword( String key )
    {
        int i = 0;
        int j = JAVA_KEYWORDS.length;
        while ( i < j )
        {
            int k = ( i + j ) / 2;
            int result = JAVA_KEYWORDS[k].compareTo( key );
            if ( result == 0 )
            {
                return true;
            }
            if ( result < 0 )
            {
                i = k + 1;
            }
            else
            {
                j = k;
            }
        }
        return false;
    }
}
