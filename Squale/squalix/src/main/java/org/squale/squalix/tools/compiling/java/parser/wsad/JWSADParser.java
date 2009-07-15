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
//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\org\\squale\\squalix\\tools\\compiling\\java\\parser\\wsad\\WSADParser.java

package org.squale.squalix.tools.compiling.java.parser.wsad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import org.squale.squalecommon.enterpriselayer.businessobject.result.ErrorBO;
import org.squale.squalix.core.exception.ConfigurationException;
import org.squale.squalix.tools.compiling.CompilingMessages;
import org.squale.squalix.tools.compiling.java.beans.JWSADProject;
import org.squale.squalix.tools.compiling.java.configuration.JCompilingConfiguration;
import org.squale.squalix.tools.compiling.java.parser.configuration.JParserConfiguration;
import org.squale.squalix.tools.compiling.java.parser.configuration.JParserUtility;
import org.squale.squalix.util.file.FileUtility;

/**
 * Parser de fichier <code>.classpath</code> pour WSAD 5.x.
 * 
 * @author m400832 (by rose)
 * @version 1.0
 */
public class JWSADParser
{

    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( JWSADParser.class );

    /** Container indiquant des dépendances de plugins */
    public static final String PLUGIN_CON = "org.eclipse.pde.core.requiredPlugins";

    /** Option d'export d'un plugin */
    public static final String PLUGIN_VISIBILITY = "visibility:=reexport";

    /** Le chemin minimum du manifest d'un projet */
    public static final String MANIFEST_PATHNAME = "META-INF/MANIFEST.MF";

    /** Indique les dépendances des plugins dans le manifest */
    public static final String REQUIRE_BUNDLE = "Require-Bundle";

    /** Indique les exports de packages dans le manifest */
    public static final String EXPORT_PACKAGE = "Export-Package";

    /**
     * Constante ".".
     */
    protected static final String DOT = ".";

    /** La liste des erreurs rencontrées durant le traitement du composant */
    private List mErrors = new ArrayList();

    /**
     * Instance de configuration.
     */
    protected JWSADParserConfiguration mConfiguration = null;

    /**
     * Liste de projets WSAD à parser.
     */
    protected List mProjectList = null;

    /**
     * Construteur par défaut.
     * 
     * @param pProjectList liste de projets WSAD à parser.
     * @throws Exception exception.
     */
    public JWSADParser( List pProjectList )
        throws Exception
    {
        mProjectList = pProjectList;
        mConfiguration = new JWSADParserConfiguration();
    }

    /**
     * This method runs the parsing process (if the project list isn't null or empty). <br />
     * If the <code>parse()</code> method returns <code>false</code>, then the <code>Project</code> boolean
     * <code>mIsCompiled</code> is set to <code>true</code> : this is to say that this project will not be compiled
     * by the Compile class. Actually, the goal is to avoid trying to compile a project in which no classpath file was
     * found.
     * 
     * @see JWSADProject
     * @see JWSADParser#parse()
     * @throws Exception exception lancée en cas d'erreur.
     */
    public void execute()
        throws Exception
    {
        boolean isNull = false;

        /* si la liste des projets n'est pas nulle */
        if ( null != mProjectList )
        {
            Iterator it = mProjectList.iterator();

            /* si l'itérateur possède des éléments */
            if ( null != it && it.hasNext() )
            {
                /* tant qu'il en possède */
                while ( it.hasNext() )
                {
                    /*
                     * on affecte le projet courant en variable de classe
                     */
                    JWSADProject project = (JWSADProject) it.next();
                    /* Vérification de la présence du .classpath indiquant un projet WSAD */
                    if ( needsParsing( project ) )
                    {
                        // On parse le manifest dans le cas de la compilation RCP
                        parseManifest( project );
                        parse( project );
                    }
                    else
                    {
                        /*
                         * alors on considère que le projet WSAD a déjà été compilé
                         */
                        LOGGER.debug( CompilingMessages.getString( "java.logs.task.dropped" ) + project.getName() );
                        project.setCompiled( true );
                    }
                }
                executeParsingEntries();
            }
            else
            {
                /* on affecte le booléen */
                isNull = true;
            }
            it = null;
        }
        else
        {
            /* on affecte le booléen */
            isNull = true;
        }

        /* si le booléen est nul */
        if ( isNull )
        {
            /* alors on lance une nouvelle exception */
            throw new Exception( CompilingMessages.getString( "java.exception.parsing.wsad_project_list_is_null" ) );
        }

        /* en mode debug, on affiche les résultats */
        printParsingResults();
    }

    /**
     * Traite le parsing des entrées récupérée lor du parsing du .classpath autre que "lib" pour chaque projet parsé
     * 
     * @throws Exception si une erreur survient dans un traitement
     */
    private void executeParsingEntries()
        throws Exception
    {
        // Pour chaque projet on traite les entrées du parsing autre que "lib"
        Iterator it = mProjectList.iterator();
        Iterator valueIt;
        while ( it.hasNext() )
        {
            /*
             * on affecte le projet courant en variable de classe
             */
            JWSADProject project = (JWSADProject) it.next();
            // On a traité les librairies, on traite les autres entrées du classpath
            for ( Iterator keysIt = project.getClasspathEntries().keySet().iterator(); keysIt.hasNext(); )
            {
                String key = (String) keysIt.next();
                // On va itérer sur la liste des valeurs identifiées par la clé
                valueIt = ( (Collection) project.getClasspathEntries().get( key ) ).iterator();
                if ( mConfiguration.getSrc().equals( key ) )
                {
                    while ( valueIt.hasNext() )
                    {
                        processSrc( project, (String) valueIt.next(), false, false );
                    }
                }
                else if ( mConfiguration.getCon().equals( key ) )
                {
                    while ( valueIt.hasNext() )
                    {
                        processCon( project, (String) valueIt.next() );
                    }
                }
                else
                {
                    while ( valueIt.hasNext() )
                    {
                        processSrc( project, (String) valueIt.next(), true, false );
                    }
                }
            }
        }
    }

    /**
     * Parse le manifest
     * 
     * @param pProject le projet
     * @throws Exception si erreur
     */
    private void parseManifest( JWSADProject pProject )
        throws Exception
    {
        // On récupère le manifest
        File manifest = getManifest( pProject );
        if ( null != manifest )
        {
            // On récupère les packages exportés
            parseManifestForExportedPackages( pProject, manifest );
        }
    }

    /**
     * La partie concernée se présente de cette manière : Export-Package: p1, p2, p4
     * 
     * @param pProject le projet
     * @param pManifest le manifest
     * @throws Exception si erreur
     */
    private void parseManifestForExportedPackages( JWSADProject pProject, File pManifest )
        throws Exception
    {
        parseManifest( pProject, pManifest, EXPORT_PACKAGE, this.getClass().getMethod(
                                                                                       "addExportedPackage",
                                                                                       new Class[] {
                                                                                           JWSADProject.class,
                                                                                           String.class } ) );
    }

    /**
     * Ajoute les packages exportés par réflexion
     * 
     * @param pProject le projet
     * @param pPackage la package à ajouter
     */
    public void addExportedPackage( JWSADProject pProject, String pPackage )
    {
        pProject.addExportedPackage( pPackage.trim() );
    }

    /**
     * Indique la présence du fichier .classpath
     * 
     * @param pProject projet WSAD
     * @return false si aucun fichier .classpath n'est trouvé
     */
    private boolean needsParsing( JWSADProject pProject )
    {
        String path = pProject.getPath();
        File classPathFile = new File( path + mConfiguration.getFilename() );

        return classPathFile.exists();
    }

    /**
     * Lance le parsing.
     * 
     * @param pProject projet WSAD
     * @return <code>true</code> en cas de succès, <code>false</code> sinon.
     * @throws Exception lorque le fichier est mal formaté
     */
    private boolean parse( JWSADProject pProject )
        throws Exception
    {
        /* on part du principe que le parsing fonctionnera */
        boolean isParsed = true;
        /*
         * on récupère le noeud "<classpath>" du fichier ".classpath" du projet WSAD
         */
        Node myNode =
            JParserUtility.getRootNode( pProject.getPath() + mConfiguration.getFilename(),
                                        mConfiguration.getClasspathAnchor() );

        /*
         * si le noeud n'est pas nul, et qu'il s'agit d'un noeud de type ELEMENT_NODE
         */
        if ( null != myNode && Node.ELEMENT_NODE == myNode.getNodeType() )
        {
            /* on récupère le noeud "<classpathentry>" */
            myNode = JParserUtility.getNodeByTagName( myNode, mConfiguration.getClasspathentry() );

            /* on initialise les variables pour la bouche à suivre */
            NamedNodeMap attrMap = null;
            Node exported = null;
            String attrValue = null, attrName = null, exportedAttr = "false";

            /* si le noeud n'est pas nul */
            while ( null != myNode )
            {
                /* si le noeud est de type ELEMENT_NODE */
                if ( Node.ELEMENT_NODE == myNode.getNodeType() )
                {
                    /* on récupère les attributs du noeud */
                    attrMap = myNode.getAttributes();

                    /* on récupère la valeur de l'attribut KIND */
                    attrName = ( attrMap.getNamedItem( mConfiguration.getKind() ) ).getNodeValue().trim();

                    /* on récupère la valeur de l'attribut PATH */
                    attrValue = ( attrMap.getNamedItem( mConfiguration.getPath() ) ).getNodeValue().trim();

                    /* on récupère la valeur de l'attribut EXPORTED */
                    // Par défaut exported = false;
                    exportedAttr = "false";
                    exported = attrMap.getNamedItem( mConfiguration.getExported() );
                    if ( null != exported )
                    {
                        exportedAttr = exported.getNodeValue().trim();
                    }

                    /*
                     * on mappe les couples clé / valeur car il faut traiter les lib en premier
                     */
                    mapKeyValues( pProject, attrName, attrValue, Boolean.valueOf( exportedAttr ).booleanValue() );
                }
                /* on itère sur les noeuds */
                myNode = myNode.getNextSibling();
            }
            /* on fait le ménage */
            attrMap = null;
            attrName = null;
            attrValue = null;

            /* si le noeud est nul ou du mauvais type */
        }
        else
        {
            /* alors on lance une exception */
            throw new Exception( CompilingMessages.getString( "exception.xml.node_not_found" ) );
        }

        myNode = null;

        /*
         * on crée un buffer pour définir le chemin du dossier contenant les ressources nécessaires à la compilation
         */
        StringBuffer path = new StringBuffer( CompilingMessages.getString( "dir.root.java" ) );
        path.append( JCompilingConfiguration.UNIX_SEPARATOR );
        path.append( pProject.getJavaVersion() );
        path.append( JCompilingConfiguration.UNIX_SEPARATOR );

        /*
         * on crée le descripteur de fichier et on appelle la méthode addCompilingRessourcesToClasspath(File)
         */
        File f = new File( path.toString().replace( '.', '_' ) );
        addCompilingRessourcesToClasspath( pProject, f );
        f = null;

        return isParsed;
    }

    /**
     * Cette méthode n'a été créée que dans le but de faire chuter la complexité cyclomatique de la méthode
     * <code>parse()</code>.
     * 
     * @param pProject projet WSAD
     * @param pKeyName nom de la clé.
     * @param pKeyValue valeur de la clé.
     * @param pExported si pKeyName est exportée
     * @throws Exception exception : lancée uniquement si elle provient des méthodes <code>processLib(String)</code>,
     *             <code>processVar(String)</code> ou <code>processSrc(String)</code>.
     */
    protected void mapKeyValues( JWSADProject pProject, String pKeyName, String pKeyValue, boolean pExported )
        throws Exception
    {

        /* si pKeyName="lib" */
        if ( mConfiguration.getLib().equals( pKeyName ) )
        {
            processLib( pProject, pKeyValue, pExported );
            /* si pKeyName="src" ou pKeyName="con" */
        }
        else if ( mConfiguration.getSrc().equals( pKeyName ) )
        {
            if ( pExported )
            {
                pProject.addClasspathEntrie( pKeyName + "Exported", pKeyValue );
            }
            else
            {
                pProject.addClasspathEntrie( pKeyName, pKeyValue );
            }
        }
        else if ( mConfiguration.getCon().equals( pKeyName ) )
        {
            pProject.addClasspathEntrie( pKeyName, pKeyValue );
        }
        else
        {
            /* les autres cas ne sont pas traités */
            LOGGER.debug( CompilingMessages.getString( "java.exception.parsing.tag_non_used" ) + " : " + pKeyName );
        }
    }

    /**
     * Dans le cas où des plugins sont requis (ex: Projets RCP)
     * 
     * @param pProject le projet
     * @param pKeyValue la valeur
     * @throws Exception si erreur
     */
    private void processCon( JWSADProject pProject, String pKeyValue )
        throws Exception
    {
        if ( PLUGIN_CON.equals( pKeyValue ) )
        {
            // Il y a des dépendances vers des plugins.
            // Il faut donc parser le manifest pour trouver toutes les dépendances
            // On ajoute les plugins eclipse directement au classpath
            addCompilingRessourcesToClasspath( pProject, pProject.getBundleDir() );
            File manifest = getManifest( pProject );
            if ( null == manifest )
            {
                // On affiche un warning sans lancer d'exception car le projet peut compiler sans ce fichier
                String message = CompilingMessages.getString( "java.warning.manifest_not_found", pProject.getName() );
                LOGGER.warn( message );
            }
            else
            {
                // On parse le manifest
                parseManifestForPluginDependencies( pProject, manifest );
            }
        }
    }

    /**
     * Parse le manifest pour trouver les plugins nécessaires à la compilation La partie concernée se présente de cette
     * manière : Require-Bundle: plugin1, plugin2;option1=v1;visibility:=reexport, plugin3, plugin4
     * 
     * @param pProject le projet
     * @param pManifest le manifest
     * @throws Exception si erreur
     */
    private void parseManifestForPluginDependencies( JWSADProject pProject, File pManifest )
        throws Exception
    {
        LOGGER.debug( "On parse le manifest de " + pProject.getName() + " pour trouver les dépendances de plugins" );
        parseManifest( pProject, pManifest, REQUIRE_BUNDLE, this.getClass().getMethod(
                                                                                       "processPlugin",
                                                                                       new Class[] {
                                                                                           JWSADProject.class,
                                                                                           String.class } ) );
    }

    /**
     * Sert à parser des block du manifest qui ont la même DTD pBlock: string1, string2, string3 (par exemple les
     * parties Bundle-Required et Export-Package)
     * 
     * @param pProject le projet
     * @param pManifest le manifest
     * @param pBlock le block à trouver
     * @param pMethod la méthode à appeler pour chaque ligne trouvée
     * @throws ConfigurationException si erreur
     */
    private void parseManifest( JWSADProject pProject, File pManifest, String pBlock, Method pMethod )
        throws ConfigurationException
    {
        try
        {
            // On récupère toutes les valeurs (commence par un espace et se termine par un espace)
            BufferedReader reader = new BufferedReader( new FileReader( pManifest ) );
            String line = reader.readLine();
            String plugin = "";

            while ( null != line && !line.startsWith( pBlock ) )
            {
                // on parse jusqu'à trouver la partie qui nous intéresse
                line = reader.readLine();
            }
            if ( null != line )
            {
                boolean stop = !line.endsWith( "," );
                // On récupère chaque plugin du Require-Bundle
                line = line.replaceFirst( pBlock + ": ", "" );
                String[] plugins = line.split( "," );
                for ( int i = 0; i < plugins.length; i++ )
                {
                    pMethod.invoke( this, new Object[] { pProject, plugins[i].trim() } );
                }
                line = reader.readLine();
                while ( null != line && !stop )
                {
                    if ( line.endsWith( "," ) )
                    {
                        plugins = line.split( "," );
                        for ( int i = 0; i < plugins.length; i++ )
                        {
                            pMethod.invoke( this, new Object[] { pProject, plugins[i].trim() } );
                        }
                    }
                    else
                    {
                        stop = true;
                        pMethod.invoke( this, new Object[] { pProject, line.trim() } );
                    }
                    line = reader.readLine();
                }
            }
        }
        catch ( IOException ioe )
        {
            throw new ConfigurationException( ioe.getMessage() );
        }
        catch ( InvocationTargetException ite )
        {
            throw new ConfigurationException( ite.getTargetException().getMessage() );
        }
        catch ( IllegalAccessException iae )
        {
            throw new ConfigurationException( iae.getMessage() );
        }

    }

    /**
     * Traite la dépendance d'un plugin
     * 
     * @param pProject le projet
     * @param pPlugin le nom du plugin
     * @throws Exception si erreur
     */
    public void processPlugin( JWSADProject pProject, String pPlugin )
        throws Exception
    {
        String[] options = pPlugin.split( ";" );

        // On récupère le nom du plugin
        String plugin = options[0].trim();

        // On regarde si l'option "visibility" est en reexport
        // car dans ce cas il faudra ajouter ce plugin à la liste des plugins exportés
        boolean reexport = false;
        for ( int i = 1; i < options.length && !reexport; i++ )
        {
            if ( options[i].matches( ".*" + PLUGIN_VISIBILITY + ".*" ) )
            {
                reexport = true;
            }
        }

        // On recherche une dépendance
        boolean found = findDependency( pProject, plugin, reexport, true );
        // Si la dépendance n'a pas été trouvée, c'est peut-être un plugin eclipse
        if ( !found && !isEclipsePlugin( pProject.getBundleDir(), plugin ) )
        {
            // si le plugin est introuvable, on lance une exception de configuration
            throw new ConfigurationException( "Required plugin (" + plugin
                + ") not found in project's compilation rules!" );
        }
    }

    /**
     * @param pBundle le répertoire du bundle eclipse
     * @param pPlugin le nom du plugin
     * @return true si il appartient au bundle
     */
    private boolean isEclipsePlugin( File pBundle, String pPlugin )
    {
        boolean result = false;
        File[] files = pBundle.listFiles();
        // Si le fichier existe, il s'agit bien d'un plugin eclipse
        for ( int i = 0; null != files && i < files.length && !result; i++ )
        {
            if ( files[i].getName().matches( pPlugin + ".*" ) )
            {
                return true;
            }
            else if ( files[i].isDirectory() )
            {
                result = isEclipsePlugin( files[i], pPlugin );
            }
        }
        return result;
    }

    /**
     * @param pProject le projet
     * @return le fichier manifest si il existe, null sinon
     */
    protected File getManifest( JWSADProject pProject )
    {
        File manifest = null;
        // Le manifest a pu être donné par l'utilisateur
        if ( null != pProject.getManifestPath() && pProject.getManifestPath().length() > 0 )
        {
            manifest = FileUtility.getAbsoluteFile( pProject.getPath(), new File( pProject.getManifestPath() ) );
        }
        if ( null == manifest || !manifest.exists() )
        {
            // Si il n'existe pas ou qu'il n'a pas été défini dans la configuration, on le recherche
            manifest = FileUtility.findFileWithPathSuffix( new File( pProject.getPath() ), MANIFEST_PATHNAME );
            if ( null != manifest )
            {
                LOGGER.info( "Chemin du Manifest : " + manifest.getAbsolutePath() );
            }
        }
        return manifest;
    }

    /**
     * If the <code>lib</code> value is encountered in a <code>kind</code> tag, this method is called by the
     * <code>parse()</code> method. <br />
     * <br />
     * Two cases exist : <br/>
     * <ul>
     * <li>the path value begins with a "/",</li>
     * <li>or not !</li>
     * </ul>
     * <br />
     * In the first case, two possibilities : <br />
     * <ul>
     * <li> the string between the first and second "/" is the name of a project contained in the workspace. For
     * example, one has three projects inside his workspace : <b>TonusIntranetWeb</b>, <b>TonusIntranetCommons</b> and
     * <b>TonusIntranetEAR</b>.<br />
     * In the <b>TonusIntranetWeb</b> classpath file, one founds the following line :
     * <code>&lt;classpathentry kind="lib" 
     * path="/TonusIntranetEAR/my_jars/my.jar"/&gt;</code>.<br />
     * The path to <code>my.jar</code> is thus :
     * <code>workspace_path/TonusIntranetWeb/../TonusIntranetEAR/my_jars/my.jar</code>, which is equal to
     * <code>workspace_path/TonusIntranetEAR/my_jars/my.jar</code>. </li>
     * <li> the string between the first and second "/" is not a project name contained in the workspace. It's an
     * absolute UNIX filepath. It is therefore directly added to the classpath. </li>
     * </ul>
     * <br />
     * In the second case, the following path is added to the classpath : <code>
     * workspace_path/current_project_name/pPath</code>.
     * 
     * @param pProject project WSAD
     * @param pPath value of the <code>path</code> tag linked with.
     * @param pExported value of the <code>exported</code> tag linked with
     * @throws Exception exception.
     * @since 1.0
     */
    protected void processLib( JWSADProject pProject, final String pPath, final boolean pExported )
        throws Exception
    {
        StringBuffer path = new StringBuffer();

        /* si pPath commence par un "/" */
        if ( 0 == pPath.indexOf( JCompilingConfiguration.UNIX_SEPARATOR ) )
        {
            JWSADProject project = findProjectDependency( pPath, path );
            /*
             * aucune dépendance n'a été trouvée ou il n'y a pas de projets WSAD, il s'agit d'un chemin UNIX absolu
             */
            if ( null == project )
            {
                path.append( pPath );
                path.append( mConfiguration.getClasspathSeparator() );
            }

            /*
             * pPath ne commence pas par un "/" il s'agit d'une ressource du projet WSAD à ajouter au classpath
             */
        }
        else
        {
            path.append( pProject.getPath() );
            path.append( pPath );
            // On vérifie l'existence du fichier sur le filesystem
            File file = new File( path.toString() );
            if ( !file.exists() )
            {
                String warning =
                    CompilingMessages.getString( "logs.task.classpath.filenotfound" )
                        + path.toString().replaceFirst( pProject.getPath(), "" );
                LOGGER.warn( warning );
                // On ajoute un warning à la liste des erreurs
                ErrorBO warningDB = new ErrorBO();
                warningDB.setLevel( ErrorBO.CRITICITY_WARNING );
                warningDB.setInitialMessage( warning );
                addError( warningDB );
            }
            // On ajoute le séparateur de classpath
            path.append( mConfiguration.getClasspathSeparator() );
        }

        /* on ajoute le chemin construit au classpath */
        addToClasspath( pProject, path.toString() );
        if ( pExported )
        {
            // On l'ajoute à la liste des librairies exportées du projet
            addToExportedLib( pProject, path.toString() );
        }
        path = null;
    }

    /**
     * Ajoute <code>pPath</code> aux librairies exportées de <code>pProject</code>
     * 
     * @param pProject le projet courant
     * @param pPath le chemin à ajouter
     */
    private void addToExportedLib( JWSADProject pProject, String pPath )
    {
        pProject.setExportedLib( getClasspathFormatAfterAdd( pProject.getExportedLib(), pPath ) );
    }

    /**
     * @param pLibPath le chemin de la dépendance
     * @param pPath le chemin a remplir
     * @return le projet dépendant si une dépendance de projet a été trouvée, null sinon
     */
    protected JWSADProject findProjectDependency( String pLibPath, StringBuffer pPath )
    {
        // Initialisation du retour de la méthode
        JWSADProject result = null;
        /* on crée un itérateur de la liste des projets */
        Iterator it = mProjectList.iterator();

        /*
         * booléen utilisé pour signifier la découverte d'une dépendance, i.e. d'un projet WSAD
         */
        boolean found = false;

        /* si l'itérateur n'est pas nul */
        if ( null != it )
        {
            /*
             * on récupère la chaîne comprise entre les 2 premiers séparateurs UNIX
             */
            String nomProjet = pLibPath.substring( 1, pLibPath.indexOf( JCompilingConfiguration.UNIX_SEPARATOR, 1 ) );
            JWSADProject pTemp = null;

            /*
             * tant que l'itérateur a des éléments ou qu'aucune dépendance n'est trouvée
             */
            while ( null == result && it.hasNext() )
            {
                /* on stocke le projet WSAD courant de la liste */
                pTemp = (JWSADProject) it.next();

                /*
                 * si nomProject est égale au nom du projet, alors il y a dépendance
                 */
                if ( ( pTemp.getName() ).equals( nomProjet ) )
                {
                    /* on construit le path en conséquence */
                    pPath.append( pTemp.getPath() );
                    pPath.append( pLibPath.substring(
                                                      pLibPath.indexOf( JCompilingConfiguration.UNIX_SEPARATOR, 1 ) + 1,
                                                      pLibPath.length() ) );
                    pPath.append( mConfiguration.getClasspathSeparator() );

                    /*
                     * on a trouvé la dépendance, inutile d'itérer plus longtemps.
                     */
                    result = pTemp;
                }
            }
        }
        return result;
    }

    /**
     * Cette méthode parcourt le répertoire (et ses sous-répertoires) contenant les ressources de compilation et les
     * ajoute au classpath, i.e. ajoute tous les jars / zips / classes communes à toutes les compilations (typiquement
     * <code>j2ee.jar</code>). <br />
     * 
     * @param pProject projet WSAD
     * @param pFile répertoire à parcourir.
     * @throws Exception exception lors du parcours du répertoire.
     */
    private void addCompilingRessourcesToClasspath( JWSADProject pProject, final File pFile )
        throws Exception
    {
        /* Liste des extensions autorisées */
        ArrayList allowedExtensions = new ArrayList();
        allowedExtensions.add( JParserConfiguration.EXT_JAR );
        allowedExtensions.add( JParserConfiguration.EXT_CLASS );
        allowedExtensions.add( JParserConfiguration.EXT_ZIP );

        /* Buffer qui contiendra le classpath */
        StringBuffer path = null;

        /* Parcours du répertoire. */
        if ( pFile.isDirectory() )
        {
            String s;
            path = new StringBuffer();
            File[] fileList = pFile.listFiles();
            String fileExt;

            for ( int i = 0; i < fileList.length; i++ )
            {
                /* s'il s'agit d'un fichier */
                if ( fileList[i].isFile() )
                {
                    /* on récupère l'extension */
                    fileExt = fileList[i].getName();
                    fileExt = fileExt.substring( fileExt.lastIndexOf( JWSADParser.DOT ) + 1, fileExt.length() );

                    /* si l'extension est autorisée */
                    if ( allowedExtensions.contains( fileExt ) )
                    {
                        /* on ajoute le fichier au buffer */
                        path.append( fileList[i].getAbsolutePath().replaceAll(
                                                                               JCompilingConfiguration.WINDOWS_SEPARATOR
                                                                                   + JCompilingConfiguration.WINDOWS_SEPARATOR,
                                                                               JCompilingConfiguration.UNIX_SEPARATOR ) );
                        path.append( mConfiguration.getClasspathSeparator() );
                    }
                    /* s'il s'agit d'un répertoire */
                }
                else
                {
                    /* on appelle récursivement la méthode */
                    addCompilingRessourcesToClasspath( pProject, fileList[i] );
                }
            }
            /* ménage */
            fileList = null;
            fileExt = null;
            /* on ajoute le buffer au classpath */
            addToClasspath( pProject, path.toString() );

        }

        /* ménage */
        path = null;
        allowedExtensions = null;
    }

    /**
     * If the <code>src</code> value is encountered in a <code>kind</code> tag, this method is called by the
     * <code>parse()</code> method. <br />
     * <br />
     * Two cases exist : <br/>
     * <ul>
     * <li>the path value begins with a "/",</li>
     * <li>or not !</li>
     * </ul>
     * <br />
     * In the first case, two sub-cases :
     * <ul>
     * <li>it's a linked project,</li>
     * <li>it's an unix path.</li>
     * </ul>
     * If it's a known project, then the dependency is added, by calling the
     * <code>processSrcDir(JWSADProject, String)</code> method. <br />
     * If an unix file path is encoutered, then it is directly added to the classpath, such as in the case in which the
     * file path does not begin by a "/".
     * 
     * @param pProject project WSAD
     * @param pPath value of the <code>path</code> tag linked with.
     * @param pExported value of the <code>exported</code> tag linked with.
     * @param pIsPlugin si pProject est un plugin
     * @throws Exception exception.
     * @since 1.0
     * @see JWSADProject#setSrcPath(String)
     * @see #processSrcDir(JWSADProject, String)
     */
    private void processSrc( JWSADProject pProject, final String pPath, final boolean pExported, final boolean pIsPlugin )
        throws Exception
    {
        boolean appendPath = true;
        boolean doPath = true;
        StringBuffer path = new StringBuffer();

        /* si pPath commence par un "/" */
        if ( pPath.startsWith( JCompilingConfiguration.UNIX_SEPARATOR ) )
        {
            /* on récupère le morceau de chaîne après le 1er "/" */
            String nomProjet = pPath.substring( 1, pPath.length() );
            boolean found = findDependency( pProject, nomProjet, pExported, pIsPlugin );

            /*
             * alors il s'agit d'un chemin UNIX absolu : il s'agit d'un répertoire contenant des fichiers sources du
             * projet courant
             */
            doPath = !found;

            /*
             * sinon il s'agit d'un répertoire contenant des fichiers sources du projet courant
             */
        }
        /*
         * on affecte le répertoire comme étant le répertoire contenant les sources
         */
        if ( doPath )
        {
            // Il faut pour tous les répertoires exclus supprimer le pPath
            // du pattern si il y est car le pattern doit être relatif au répertoire
            // source.
            String replacePath = ( pProject.getPath() + "/" + pPath ).replaceAll( "//", "/" );
            for ( int i = 0; null != pProject.getExcludedDirs() && i < pProject.getExcludedDirs().size(); i++ )
            {
                // On récupère le répertoire exclu et on lui rajoute un "/" en bout de nom pour spécifier la fin
                // du nom
                String currentEx = ( (String) pProject.getExcludedDirs().get( i ) + "/" ).replaceAll( "//", "/" );
                currentEx = currentEx.replaceFirst( replacePath + "/+", "" );
                // Si il s'agit d'un répertoire fils du répertoire source, on l'ajoute dans les exclusions
                // on vérifie donc si il ne s'ahit pas du répertoire source lui-même (currentEx.length() > 0)
                // et que ce répertoire exclu n'est pas un parent (!replacePath.startsWith(currentEx))
                if ( currentEx.length() > 0 && !replacePath.startsWith( currentEx ) )
                {
                    // On remplace l'exclusion
                    pProject.getExcludedDirs().remove( i );
                    pProject.getExcludedDirs().add( i, currentEx );
                }
                else
                {
                    // On n'ajoutera pas le répertoire source puisqu'il est exclu
                    appendPath = false;
                }
            }
            if ( appendPath )
            {
                if ( pProject.getSrcPath().length() > 0 )
                {
                    path.append( pProject.getSrcPath() );
                }
                path.append( pProject.getPath() );
                path.append( pPath );
                path.append( JCompilingConfiguration.UNIX_SEPARATOR );
                path.append( mConfiguration.getClasspathSeparator() );
                pProject.setSrcPath( path.toString() );
            }
        }

        path = null;
    }

    /**
     * @param pProject le projet
     * @param pProjectName le nom du projet à chercher
     * @param pExported true si le projet de nom <code>pProjectName</code> est exporté
     * @return true si le projet a été trouvé parmis les dépendances
     * @param pIsPlugin si pProject est un plugin
     * @throws Exception si erreur
     */
    private boolean findDependency( JWSADProject pProject, String pProjectName, boolean pExported, boolean pIsPlugin )
        throws Exception
    {
        Iterator it = mProjectList.iterator();
        boolean found = false;
        if ( null != it )
        {
            /*
             * tant qu'il y a des éléments et qu'aucune dépendance n'a été trouvée
             */
            while ( false == found && it.hasNext() )
            {
                /*
                 * on appelle la méthode processSrcDir(JWSADProject, String) qui ajoute le cas échéant des dépendances
                 * au projet courant (pProject). Si une dépendance est ajoutée, alors found est mis à true.
                 */
                found = processSrcDir( pProject, (JWSADProject) it.next(), pProjectName, pExported, pIsPlugin );
            }
        }
        return found;
    }

    /**
     * Cette méthode a été implémentée afin de faire chuter la complexité cyclomatique de la méthode
     * <code>processSrc(String)</code>.
     * 
     * @param pMainProject JWSADProject.
     * @param pProject JWSADProject.
     * @param pNomProject Nom du projet : issu du tag <code>path</code> du fichier XML de classpath.
     * @param pExported true si le <code>pMainProject</code> exporte <code>pProject</code>
     * @param pIsPlugin si pProject est un plugin
     * @return <code>true</code> s'il existe une dépendance entre projets, <code>false</code> sinon.
     * @throws Exception exception en cas d'erreur.
     * @see #processSrc(String)
     * @see #processSrcDir(JWSADProject, String)
     * @see #addProjectDependency(JWSADProject)
     */
    private boolean processSrcDir( JWSADProject pMainProject, JWSADProject pProject, String pNomProject,
                                   boolean pExported, boolean pIsPlugin )
        throws Exception
    {
        boolean found = false;
        /* si la chaîne est égale au nom du projet WSAD */
        if ( pNomProject.equals( pProject.getName() ) )
        {
            /* alors il y a dépendance : on l'ajoute. */
            addProjectDependency( pMainProject, pProject, pExported, pIsPlugin );
            if ( !pIsPlugin )
            {
                /*
                 * on ajoute le répertoire contenant les classes compilées de pProject au classpath de pMainProject
                 */
                addToClasspath( pMainProject, pProject.getDestPath() );
                /* Ainsi que ses libraries exportées si il s'agit d'un projet */
                LOGGER.debug( "Traitement des librairies exportées de " + pProject.getName() + " pour "
                    + pMainProject.getName() );
                addToClasspath( pMainProject, pProject.getExportedLib() );
            }
            else
            {
                /*
                 * On dézippe les librairies exportées de pProject dans le répertoire défini dans la configuration et
                 * ajoute les .class au classpath nécessaires lors de la compilation
                 */
                if ( pProject.getExportedLib().length() > 0 )
                {
                    addExportedLibs( pMainProject, pProject );
                }
                // Il faut aussi ajouter aussi les .classes compilées du plugin au classpath
                // en fonction des packages exportés mais dans ce cas il faut attendre que le projet
                // soit compilé donc on exécute la méthode lors de la compilation des projets
                // @see JWSADCompiler.java
            }
            found = true;
        }
        return found;
    }

    /**
     * Ajoute les .class des packages exportés pour toutes les libraries exportées
     * 
     * @param pMainPlugin le plugin courant
     * @param pPlugin le plugin dépendant
     * @throws Exception si erreur
     */
    private void addExportedLibs( JWSADProject pMainPlugin, JWSADProject pPlugin )
        throws Exception
    {
        LOGGER.debug( "Traitement des librairies exportées de " + pPlugin.getName() + " pour " + pMainPlugin.getName() );
        // Pour chaque librairie exportée, on dézippe
        String[] libs = pPlugin.getExportedLib().split( mConfiguration.getClasspathSeparator() );
        // On parcours si il y a des librairies exportées.
        for ( int i = 0; i < libs.length && !( libs.length == 1 && libs[0].length() == 0 ); i++ )
        {
            FileUtility.copyOrExtractInto( new File( libs[i] ), mConfiguration.getExportedLibsDir() );
        }
        // On ajoute les packages exportés des librairies extraites au classpath
        addExportedPackagesToClasspath( pMainPlugin, pPlugin, mConfiguration.getExportedLibsDir() );
    }

    /**
     * Permet d'ajouter le répertoire contenant les .class des packages exportés par <code>pPlugin</code> au classpath
     * de <code>pMainPlugin</code>
     * 
     * @param pMainPlugin le plugin dont il faut modifier le classpath
     * @param pPlugin le plugin dont dépend <code>pMainPlugin</code>
     * @param pRoot le répertoire de recherche des packages exportés
     * @throws IOException si erreur lors de la copie
     */
    public void addExportedPackagesToClasspath( JWSADProject pMainPlugin, JWSADProject pPlugin, File pRoot )
        throws IOException
    {
        File packageDir = null;
        // On crée le répertoire racine qui contiendra les .class (chemin_pRoot/nomProjet)
        File copyRoot = new File( pRoot, pMainPlugin.getName() );
        if ( !copyRoot.exists() )
        {
            // On crée le répertoire si il n'existe pas pour pouvoir faire la copie
            copyRoot.mkdir();
        }
        File copyDest = null;
        for ( int i = 0; i < pPlugin.getExportedPackages().size(); i++ )
        {
            // On crée le répertoire à partir du nom du package
            // Ex : org.squale.jraf --> chemin_pRoot/org/squale/jraf
            packageDir =
                new File( pRoot, ( (String) pPlugin.getExportedPackages().get( i ) ).replaceAll( "\\.", File.separator ) );
            // On crée le répertoire de destination des .class
            // Ex : chemin_pRoot/nomProjet/org/squale/jraf
            copyDest =
                new File( copyRoot, ( (String) pPlugin.getExportedPackages().get( i ) ).replaceAll( "\\.",
                                                                                                    File.separator ) );
            // On copie les .class qui sont à la racine du répertoire dans le répertoire spécifique
            // au projet
            // Cette copie est faite car il faut ajouter au -classpath de javac un répertoire contenant le chemin
            // des packages car on ne peut pas rajouter le chemin absolu du .classpath sinon les imports
            // ne seront pas résolus.
            copyClassesTo( packageDir, copyDest );
        }
        // On ajoute le répertoire si il n'a pas encore été rajouté
        if ( !pMainPlugin.getClasspath().matches( ".*" + copyRoot.getAbsolutePath() + ";.*" ) )
        {
            addToClasspath( pMainPlugin, copyRoot.getAbsolutePath() );
        }
    }

    /**
     * Copie tous les .class à la racine du répertoire <code>pDir</code> dans <code>pDest</code>
     * 
     * @param pDir le répertoire dans lequel il faut chercher les .class
     * @param pDest le répertoire de destination des .class copiés
     * @throws IOException si erreur lors de la copie
     */
    private void copyClassesTo( File pDir, File pDest )
        throws IOException
    {
        // On liste les fichiers à la racine de pDir
        File[] classes = pDir.listFiles();
        if ( null != classes )
        {
            File currentFile = null;
            // On créer le chemin des répertoires pour préparer la copie
            pDest.mkdirs();
            // On copie tous les fichiers ayant l'extension .class
            for ( int i = 0; i < classes.length; i++ )
            {
                currentFile = classes[i];
                if ( currentFile.isFile()
                    && currentFile.getName().endsWith( JWSADParser.DOT + JParserConfiguration.EXT_CLASS ) )
                {
                    FileUtility.copyIntoDir( currentFile, pDest );
                }
            }
        }
    }

    /**
     * This method adds a string (i.e. a directory that should be added to the project classpath) to the
     * <code>mClasspath</code> JWSADProject attribute.
     * 
     * @param pProject JWSADProject.
     * @param pCp String to be added to the <code>mClasspath</code> WSAD attribute.
     * @since 1.0
     * @see JWSADProject#setClasspath(String)
     */
    public void addToClasspath( JWSADProject pProject, String pCp )
    {
        pProject.setClasspath( getClasspathFormatAfterAdd( pProject.getClasspath(), pCp ) );
    }

    /**
     * @param pClasspathFormat une chaîne dans le format "classpath" (i.e. séparée par ";")
     * @param pPathToAdd le chemin à ajouter
     * @return pClasspathFormat à laquelle on a ajouté pPath
     */
    private String getClasspathFormatAfterAdd( String pClasspathFormat, String pPathToAdd )
    {
        StringBuffer s = new StringBuffer( pClasspathFormat );
        s.append( pPathToAdd );
        /* si la chaîne ne se termine pas un ";" */
        if ( s.lastIndexOf( mConfiguration.getClasspathSeparator() ) != ( s.length() - 1 ) )
        {
            s.append( mConfiguration.getClasspathSeparator() );
        }
        return s.toString();
    }

    /**
     * This method adds a dependency for the current project. <br />
     * <br />
     * For example : <br />
     * <br />
     * <code>project1.addProjectDependency(project2)</code><br />
     * <br />
     * means that <code>project1</code> depends on <code>project2</code> to be successfully compiled. <br />
     * <br />
     * 
     * @param pProject JWSADProject.
     * @param pProj JWSADProject on which depends the current project.
     * @param pExported if <code>pProj</code> is exported by <code>pProject</code>
     * @param pIsAPlugin true si pProj est un plugin
     * @throws Exception si erreur
     * @see JWSADProject#addProjectDependency(JWSADProject)
     */
    private void addProjectDependency( JWSADProject pProject, JWSADProject pProj, boolean pExported, boolean pIsAPlugin )
        throws Exception
    {
        if ( pExported )
        {
            pProject.addExportedProject( pProj );
        }
        else
        {
            pProject.addProjectDependency( pProj );
        }
        // Si on il s'agit d'un plugin dépendant, il faut ajouter des dépendances récursivement pour tous
        // les plugins ayant un visibility=reexport
        if ( pIsAPlugin )
        {
            addPluginDependencies( pProject, pProj );
        }
        else
        {
            // Il faut ajouter au classpath toutes les librairies exportées de tous les projets exportés
            // par le projet dont project dépend et ce récursivement.
            // Ex : A dépend de B qui exporte C qui exporte D qui exporte la librairie L
            // Alors L doit être dans le classpath de A,B,C et D!
            addExportedLibToClasspath( pProject, pProj );
        }
    }

    /**
     * Ajoute toutes les dépendances des plugins exportés
     * 
     * @param pMainPlugin le plugin courant
     * @param pDependencyPlugin le plugin dépendant
     * @throws Exception si erreur dans la recherche du plugin
     */
    private void addPluginDependencies( JWSADProject pMainPlugin, JWSADProject pDependencyPlugin )
        throws Exception
    {
        // On ajoute au projets dépendants de pMainProject les plugins exportées par
        // le plugin dépendant pDependencyPlugin
        for ( int i = 0; i < pDependencyPlugin.getExportedProjects().size(); i++ )
        {
            JWSADProject curExportedPlugin = (JWSADProject) pDependencyPlugin.getExportedProjects().get( i );
            // On ajoute la dépendance
            findDependency( pMainPlugin, curExportedPlugin.getName(), false, true );
        }
    }

    /**
     * Ajoute toutes les librairies exportées par les projets dépendants eux-même exportés
     * 
     * @param pMainProject le projet courant
     * @param pDependencyProject le projet dépendant
     */
    private void addExportedLibToClasspath( JWSADProject pMainProject, JWSADProject pDependencyProject )
    {
        // On ajoute au classpath de pMainProject les librairies exportées par
        // les projets exportés par pDependencyProject
        for ( int i = 0; i < pDependencyProject.getExportedProjects().size(); i++ )
        {
            JWSADProject curExportedProject = (JWSADProject) pDependencyProject.getExportedProjects().get( i );
            // On ajoute ses librairies exportées au classpath
            addToClasspath( pMainProject, curExportedProject.getExportedLib() );
            // On rapelle récursivement la méthode pour les projets exportés de curExportedProject
            addExportedLibToClasspath( pMainProject, curExportedProject );
        }
    }

    /**
     * Méthode permettant d'afficher les résultats du parsing.
     */
    private void printParsingResults()
    {
        LOGGER.debug( "WSAD PARSING RESULTS" );
        Iterator it = mProjectList.iterator();
        /* si l'itérateur possède des éléments */
        if ( null != it && it.hasNext() )
        {
            JWSADProject affP = null;
            Iterator tmpIt = null;
            /* tant qu'il y a des éléments */
            while ( it.hasNext() )
            {
                affP = (JWSADProject) it.next();
                /* on ajoute les variables "simples" */
                LOGGER.debug( "Project: " + affP.getName() );
                LOGGER.debug( "SourcePath: " + affP.getSrcPath() );
                LOGGER.debug( "DestinationPath: " + affP.getDestPath() );
                LOGGER.debug( "NeedsToBeCompiled " + !( affP.isCompiled() ) );
                String dependencies = "";
                if ( affP.getDependsOnProjects() != null )
                {
                    tmpIt = affP.getDependsOnProjects().iterator();
                    /* tant qu'il y a des dépendances */
                    while ( tmpIt.hasNext() )
                    {
                        dependencies += ( (JWSADProject) ( tmpIt.next() ) ).getName();
                    }
                }
                LOGGER.debug( "Dependencies " + dependencies );
                LOGGER.debug( "Classpath: " + affP.getClasspath() );
            }
        }
    }

    /**
     * Getter.
     * 
     * @return la liste des projets WSAD.
     */
    public List getProjectList()
    {
        return mProjectList;
    }

    /**
     * @return la liste des erreurs
     */
    public List getErrors()
    {
        return mErrors;
    }

    /**
     * Ajoute une erreur de traitement
     * 
     * @param pError l'erreur
     */
    public void addError( ErrorBO pError )
    {
        mErrors.add( pError );
    }

    /**
     * Filtre sur les fichiers .class d'un répertoire
     */
    class OnlyClassesFilter
        implements FileFilter
    {

        /** Extension d'une classe java compilée */
        public static final String CLASS_EXTENSION = ".class";

        /**
         * {@inheritDoc}
         * 
         * @see java.io.FileFilter#accept(java.io.File)
         */
        public boolean accept( File pathname )
        {
            boolean accept = false;
            // On ne récupère que les fichiers .class au premier niveau du répertoire
            if ( pathname.isFile() && pathname.getAbsolutePath().endsWith( CLASS_EXTENSION ) )
            {
                accept = true;
            }
            return accept;
        }
    }
}
