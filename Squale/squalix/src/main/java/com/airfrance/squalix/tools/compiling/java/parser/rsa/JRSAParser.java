package com.airfrance.squalix.tools.compiling.java.parser.rsa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalix.core.exception.ConfigurationException;
import com.airfrance.squalix.tools.compiling.CompilingMessages;
import com.airfrance.squalix.tools.compiling.java.beans.JRSAProject;
import com.airfrance.squalix.tools.compiling.java.beans.JWSADProject;
import com.airfrance.squalix.tools.compiling.java.parser.wsad.JWSADParser;

/**
 * Parser de fichier <code>.classpath</code> pour RSA7
 */
public class JRSAParser
    extends JWSADParser
{

    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( JRSAParser.class );

    /** Mot clé du manifest pour trouver les librairies */
    private static final String CLASS_PATH = "Class-Path:";

    /** Container faisant référence au manifest.mf du projet */
    private static final String MANIFEST_CON = "org.eclipse.jst.j2ee.internal.module.container";

    /** Container faisant référence au librairies du contenues dans le répertoire WEB-INF/lib */
    private static final String WEB_LIB_CON = "org.eclipse.jst.j2ee.internal.web.container";

    /** Chemin vers le fichier contenant le nom du répertoire Web du projet */
    private static final String WEB_SETTINGS_PATH = "/.settings/org.eclipse.wst.common.component";

    /** Nom du répertoire contenant les librairies d'application Web */
    private static final String WEB_LIB = "/WEB-INF/lib";

    /**
     * @param pProjectList liste de projets RSA à parser.
     * @throws Exception excpetion
     */
    public JRSAParser( List pProjectList )
        throws Exception
    {
        super( pProjectList );
        mConfiguration = new JRSAParserConfiguration();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalix.tools.compiling.java.parser.wsad.JWSADParser#mapKeyValues(com.airfrance.squalix.tools.compiling.java.beans.JWSADProject,
     *      java.lang.String, java.lang.String, boolean)
     */
    protected void mapKeyValues( JWSADProject pProject, String pKeyName, String pKeyValue, boolean pExported )
        throws Exception
    {
        // Sous RSA7, il faut géré le cas des connecteurs
        if ( ( (JRSAParserConfiguration) mConfiguration ).getCon().equals( pKeyName ) )
        {
            processCon( pProject, pKeyValue );
        }
        super.mapKeyValues( pProject, pKeyName, pKeyValue, pExported );
    }

    /**
     * Il y a deux connecteurs à traiter : 1. celui faisant référence au META-INF/Manifest.mf du projet (-->
     * org.eclipse.jst.j2ee.internal.module.container) 2. Celui faisant référence auw librairies sous WEB-INF/lib dans
     * le cas de projets J2EE (--> org.eclipse.jst.j2ee.internal.web.container)
     * 
     * @param pProject le projet RSA
     * @param pKeyValue la valuer de la clé
     * @throws Exception si erreur
     */
    private void processCon( JWSADProject pProject, String pKeyValue )
        throws Exception
    {
        /* CAS 1 : META-INF/Manifest.mf */
        if ( MANIFEST_CON.equals( pKeyValue ) )
        {
            processManifestCon( pProject );
        }
        else if ( WEB_LIB_CON.equals( pKeyValue ) )
        {
            /* CAS 2 : WEB-INF/lib */
            processWebInfLibCon( pProject );
        }
    }

    /**
     * @param pProject le projet
     * @throws FileNotFoundException si erreur de fichier
     * @throws ConfigurationException si erreur de configuration
     */
    private void processWebInfLibCon( JWSADProject pProject )
        throws FileNotFoundException, ConfigurationException
    {
        // Dans ce cas on va parser le fichier org.eclipse.wst.common.component du répertoire .settings
        // du projet pour nous informer sur le nom du répertoire contenant la partie Web
        // (par défaut WebContent)
        File webSettings = new File( pProject.getPath() + WEB_SETTINGS_PATH );
        if ( !webSettings.exists() )
        {
            LOGGER.warn( webSettings.getAbsolutePath() + " n'existe pas!!" );
        }
        else
        {
            JRSAWebSettingsParser parser = new JRSAWebSettingsParser();
            parser.parse( new FileInputStream( webSettings ) );
            if ( null != parser.getWebContentFolder() )
            {
                // Une fois le nom du répertoire, on ajoute les libraries au classpath
                File webLibDir = new File( pProject.getPath() + parser.getWebContentFolder() + WEB_LIB );
                if ( webLibDir.exists() && webLibDir.isDirectory() )
                {
                    File[] webLibs = webLibDir.listFiles();
                    for ( int i = 0; i < webLibs.length; i++ )
                    {
                        if ( webLibs[i].isFile() )
                        {
                            addToClasspath( pProject, webLibs[i].getAbsolutePath() );
                        }
                    }
                }
            }
        }
    }

    /**
     * @param pProject le projet
     * @throws Exception si erreur
     */
    private void processManifestCon( JWSADProject pProject )
        throws Exception
    {
        // Dans le manifest, le projet EAR n'est pas renseigné, il n'y a que les
        // noms des libraries, il faut donc que l'utilisateur ait rentrer le nom
        // de son projet EAR
        File manifest = getManifest( pProject );
        if ( null == manifest )
        {
            String message = CompilingMessages.getString( "java.warning.manifest_not_found" );
            LOGGER.error( message );
            // Renvoi d'une exception de configuration
            throw new ConfigurationException( message );
        }
        // On vérifie que le chemin donné par l'utilisateur existe, sinon on lance un warning
        // On récupère le projet EAR associé
        // on place le nom du projet entre "/" pour utiliser la méthode lors d'une dépendance WSAD
        StringBuffer earProject = new StringBuffer( "/" );
        earProject.append( ( (JRSAProject) pProject ).getEARProjectName() );
        earProject.append( "/" );
        if ( earProject.length() == 2 )
        {
            LOGGER.warn( "EAR non configuré" );
        }
        else
        {
            // On parse le manifest selon les spécifications
            // (http://java.sun.com/j2se/1.3/docs/guide/jar/jar.html)
            parseManifest( pProject, manifest, earProject );
        }
    }

    /**
     * Parse le manifest
     * 
     * @param pProject le projet
     * @param pManifest le manifest à parser
     * @param earProject le nom du projet EAR
     * @throws Exception si erreur
     */
    public void parseManifest( JWSADProject pProject, File pManifest, StringBuffer earProject )
        throws Exception
    {
        LOGGER.info( "On parse le manifest de " + pProject.getName() + " pour trouver les librairies EAR" );
        // Chaque ligne peut-être CR LF | LF | CR(not followed by LF) code ASCII 13 et 10";
        // On récupère toutes les valeurs (commence par un espace et se termine par un espace)
        BufferedReader reader = new BufferedReader( new FileReader( pManifest ) );
        
        StringBuffer value = new StringBuffer();
        String line = reader.readLine() ;
        while ( null != line  && !line.startsWith( CLASS_PATH ) )
        {
            // on parse jusqu'à trouver la partie qui nous intéresse
            line = reader.readLine();
        }
        if ( null != line )
        {
            // On récupère chaque lib
            line = line.replaceFirst( CLASS_PATH, "" );
            String[] libs = line.split( " +" ); // On sépare les libs qui sont séparées par un ou plsr espaces
            for ( int i = 0; i < libs.length; i++ )
            {
                if ( i == libs.length - 1 && !line.endsWith( " " ) )
                {
                    value = earProject;
                    value.append( line.trim() );
                }
                else if ( libs[i].trim().length() > 0 )
                {
                    // On ajoute la valeur au classpath
                    processLib( pProject, earProject + libs[i].trim(), false );
                    value.setLength( 0 );
                }
            }
            // On parcours jusqu'à ce qu'in ligne ne commence pas par un espace
            line = reader.readLine();
            while ( null != line  && line.matches( " +.*" ) )
            {
                // Si il y a plus d'un espace en début de ligne
                // ou un espace sans rien derrière
                if ( line.matches( "  +| " ) && value.length() > 0 )
                {
                    // nouvelle ligne
                    value.insert( 0, earProject );
                    processLib( pProject, value.toString(), false );
                    value.setLength( 0 );
                }
                libs = line.split( " +" ); // On sépare les libs qui sont séparées par un ou plsr espaces
                for ( int i = 0; i < libs.length; i++ )
                {
                    if ( i == libs.length - 1 && !line.endsWith( " " ) )
                    {
                        value.append( libs[i].trim() );
                    }
                    else if ( libs[i].trim().length() > 0 )
                    {
                        if ( value.length() > 0 )
                        {
                            value.append( libs[i].trim() );
                        }
                        // On ajoute la valeur au classpath
                        processLib( pProject, earProject + libs[i].trim(), false );
                        value.setLength( 0 );
                    }
                }
                line = reader.readLine();
            }
        }
    }
}
