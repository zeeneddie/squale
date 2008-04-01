//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\com\\airfrance\\squalix\\tools\\compiling\\java\\JavaCompilingConfiguration.java

package com.airfrance.squalix.tools.compiling.java.configuration;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.airfrance.squalix.configurationmanager.ConfigUtility;
import com.airfrance.squalix.tools.compiling.CompilingMessages;

/**
 * Classe de configuration pour la compilation des projets JAVA.<br />
 * La version 2.0 introduit le recours à la réflexion.
 * 
 * @author m400832
 * @version 2.0
 */
public class JCompilingConfiguration
{

    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( JCompilingConfiguration.class );

    /**
     * Séparateur UNIX.
     */
    public static final String UNIX_SEPARATOR = "/";

    /**
     * Séparateur Windows.
     */
    public static final String WINDOWS_SEPARATOR = "\\";

    /**
     * Version par défaut du JDK à utiliser.
     */
    private String mJDKDefaultVersion = "";

    /**
     * Répertoire cible pour stocker les classes compilées.
     */
    private String mDestDir = "";

    /**
     * Séparateur du classpath.
     */
    private String mClasspathSeparator = "";

    /**
     * Mémoire requise pour la compilation.
     */
    private String mRequiredMemory = "";

    /**
     * Répertoire cible pour copier ou extraire le bundle eclipse.
     */
    private String mEclipseBundleDir = "";

    /**
     * Répertoire cible pour extraire les libraries exportées dans le cas de la compilation RCP.
     */
    private File mExportedLibsDir = new File( "" );

    /**
     * Lib to add to the javac bootclasspath option depend on dialect. 1.3 -> pathtoJreLib1_3 1.4 -> pathToJreLib1_4 1.5 ->
     * pathToJreLib1_5
     */
    private HashMap mBootclasspaths = new HashMap();

    /**
     * HashMap utilisée opur la réflexion.
     */
    private HashMap mMap;

    /**
     * Constructeur.
     * 
     * @throws Exception exception lors de la configuration.
     * @see #createReflectionMap()
     * @see #getConfigurationFromXML(String)
     */
    public JCompilingConfiguration()
        throws Exception
    {
        createReflectionMap();
        getConfigurationFromXML( CompilingMessages.getString( "configuration.file" ) );
    }

    /**
     * Cette méthode lance le parsing du fichier de configuration XML.
     * 
     * @param pFile chemin du fichier de configuration à parser.
     * @throws Exception exception en cas d'erreur de parsing.
     */
    private void getConfigurationFromXML( final String pFile )
        throws Exception
    {
        LOGGER.trace( CompilingMessages.getString( "logs.task.entering_method" ) );
        boolean isNull = false;

        /* on récupère le noeud racine */
        Node root = ConfigUtility.getRootNode( pFile, CompilingMessages.getString( "configuration.root" ) );

        /* s'il n'est pas nul */
        if ( null != root )
        {
            /* on récupère le noeud relatif la compilation JAVA */
            Node myNode = ConfigUtility.getNodeByTagName( root, CompilingMessages.getString( "configuration.java" ) );

            /* s'il n'est pas nul */
            if ( null != myNode )
            {
                /*
                 * on récupère le noeud relatif à la configuration générale pour la compilation JAVA
                 */
                myNode =
                    ConfigUtility.getNodeByTagName( myNode, CompilingMessages.getString( "configuration.java.general" ) );

                /* récupération des séparateurs */
                getSeparatorsFromXML( myNode );

                /* récupération des classpath des APIs Java */
                getBootclasspathsFromXML( myNode );

                /*
                 * création d'une liste des autres noeuds contenant les infos de config générale
                 */
                ArrayList nodes = new ArrayList();
                /* version par défaut du JDK */
                nodes.add( CompilingMessages.getString( "configuration.java.general.default_jdk_version" ) );
                /* répertoire de stockage des classes compilées */
                nodes.add( CompilingMessages.getString( "configuration.java.general.dest_dir" ) );
                /* mémoire requise pour la compilation */
                nodes.add( CompilingMessages.getString( "configuration.java.general.required_memory" ) );
                /* répertoire contenant le bundle eclipse de l'utilisateur */
                nodes.add( CompilingMessages.getString( "configuration.java.general.eclipse_bundle_path" ) );
                /* répertoire contenant les librairies exportées dans le cas de la compilation RCP */
                nodes.add( CompilingMessages.getString( "configuration.java.general.exported_libs_path" ) );

                /* récupération des autres noeuds de la config générale */
                getGeneralFromXML( myNode, nodes );
            }
            else
            {
                /* noeud rencontré nul / n'existe pas */
                isNull = true;
            }
            myNode = null;

        }
        else
        {
            /* noeud rencontré nul / n'existe pas */
            isNull = true;
        }

        /* si le noeud rencontré est nul / n'existe pas */
        if ( isNull )
        {
            /* on lance l'exception en rapport */
            throw new Exception( CompilingMessages.getString( "exception.xml.node_not_found" ) );
        }

        root = null;
    }

    /**
     * Cette méthode récupère les valeurs de fichier de configuration relatives à la configuration générale de la tâche,
     * et les attribue par réflexion.
     * 
     * @param pNode noeud racine.
     * @param pChildNodeNames noeud fils à parcourir.
     * @throws Exception exception lors du traitement.
     */
    private void getGeneralFromXML( final Node pNode, final ArrayList pChildNodeNames )
        throws Exception
    {
        LOGGER.trace( CompilingMessages.getString( "logs.task.entering_method" ) );

        /*
         * instanciation des variables pour la boucle qui va suivre.
         */
        Node myNode;
        String pChildNodeName;

        /* itérateur sur les noms de noeuds */
        Iterator it = pChildNodeNames.iterator();

        /* si l'itérateur n'est pas nul */
        if ( null != it )
        {
            /* tant qu'il a des éléments */
            while ( it.hasNext() )
            {
                /* nom du noeud fils courant */
                pChildNodeName = (String) it.next();

                /* noeud enfant */
                myNode = ConfigUtility.getNodeByTagName( pNode, pChildNodeName );

                /* si le noeud contient une valeur */
                if ( null != myNode && Node.ELEMENT_NODE == myNode.getNodeType() )
                {
                    mapKeyValue( pChildNodeName, myNode.getFirstChild().getNodeValue().trim() );

                    /* sinon */
                }
                else
                {
                    /* on lance l'exception en rapport */
                    throw new Exception( CompilingMessages.getString( "exception.xml.node_not_found" ) );
                }
            }
        }
        myNode = null;
    }

    /**
     * Récupére les classpaths vers les APIs Java
     * 
     * @param pNode le noeud
     * @throws Exception si erreur
     */
    private void getBootclasspathsFromXML( final Node pNode )
        throws Exception
    {

        /* noeud racine contenant les classpaths */
        Node myNode =
            ConfigUtility.getNodeByTagName( pNode,
                                            CompilingMessages.getString( "configuration.java.general.bootclasspaths" ) );

        boolean throwException = false;

        // not null and element type
        if ( null != myNode && Node.ELEMENT_NODE == myNode.getNodeType() )
        {
            // We get the first child node
            myNode =
                ConfigUtility.getNodeByTagName(
                                                myNode,
                                                CompilingMessages.getString( "configuration.java.general.bootclasspaths.bootclasspath" ) );

            // If node exists
            if ( null != myNode )
            {
                NamedNodeMap attrMap = null;
                String javaVersion = null;

                /* tant qu'il y a des noeuds */
                while ( null != myNode )
                {
                    if ( Node.ELEMENT_NODE == myNode.getNodeType() )
                    {
                        /* on récupère les attributs du noeud */
                        attrMap = myNode.getAttributes();

                        /* attribut "version" */
                        javaVersion =
                            ( attrMap.getNamedItem( CompilingMessages.getString( "configuration.java.general.bootclasspaths.bootclasspath.version" ) ) ).getNodeValue().trim();

                        // We will get all children nodes
                        getBootClasspathLibsFromXML( myNode, javaVersion );
                    }
                    /* on itère */
                    myNode = myNode.getNextSibling();
                }

                attrMap = null;
                javaVersion = null;

                /* erreur rencontrée --> exception à lancer */
            }
            else
            {
                throwException = true;
            }
            /* erreur rencontrée --> exception à lancer */
        }
        else
        {
            throwException = true;
        }

        myNode = null;

        /* erreur rencontrée */
        if ( throwException )
        {
            /* exception lancée */
            throw new Exception( CompilingMessages.getString( "exception.xml.node_not_found" ) );
        }
    }

    /**
     * Add bootclasspath lib to the bootclasspath map with javaVersion key
     * 
     * @param pNode root node containing all lib tag definitions
     * @param javaVersion java dialect
     * @throws Exception if error
     */
    private void getBootClasspathLibsFromXML( Node pNode, String javaVersion )
        throws Exception
    {

        boolean throwException = false;
        Node node = pNode;

        // not null and element type
        if ( null != node && Node.ELEMENT_NODE == node.getNodeType() )
        {
            // We get the first child node
            node =
                ConfigUtility.getNodeByTagName(
                                                node,
                                                CompilingMessages.getString( "configuration.java.general.bootclasspaths.bootclasspath.lib" ) );

            // If node exists
            if ( null != node )
            {

                NamedNodeMap attrMap = null;
                String attrPath = null;

                /* While there are nodes */
                while ( null != node )
                {
                    if ( Node.ELEMENT_NODE == node.getNodeType() )
                    {
                        /* on récupère les attributs du noeud */
                        attrMap = node.getAttributes();

                        /* "path" attribute */
                        attrPath =
                            ( attrMap.getNamedItem( CompilingMessages.getString( "configuration.java.general.bootclasspaths.bootclasspath.lib.path" ) ) ).getNodeValue().trim();

                        // We invoke add method
                        ( (Method) ( mMap.get( CompilingMessages.getString( "configuration.java.general.bootclasspaths.bootclasspath" ) ) ) ).invoke(
                                                                                                                                                      this,
                                                                                                                                                      new String[] {
                                                                                                                                                          javaVersion,
                                                                                                                                                          attrPath } );
                    }
                    /* on itère */
                    node = node.getNextSibling();
                }

                attrMap = null;
                attrPath = null;

                /* have error --> launch exception */
            }
            else
            {
                throwException = true;
            }
            /* have erreor --> launch exception */
        }
        else
        {
            throwException = true;
        }

        node = null;

        /* have error */
        if ( throwException )
        {
            /* launched exception */
            throw new Exception( CompilingMessages.getString( "exception.xml.node_not_found" ) );
        }
    }

    /**
     * Cette méthode récupère le valeur des clés nécessaires pour parser le fichier de classpath.
     * 
     * @param pNode noeud XML à parser.
     * @throws Exception exception en cas d'erreur lors du parsing.
     * @see #mapKeyValue(String, String)
     */
    private void getSeparatorsFromXML( final Node pNode )
        throws Exception
    {
        LOGGER.trace( CompilingMessages.getString( "logs.task.entering_method" ) );

        /* noeud racine contenant les séparateurs */
        Node myNode =
            ConfigUtility.getNodeByTagName( pNode,
                                            CompilingMessages.getString( "configuration.java.general.separators" ) );

        boolean throwException = false;

        /* noeud non nul et de type ELEMENT */
        if ( null != myNode && Node.ELEMENT_NODE == myNode.getNodeType() )
        {
            /* on récupère le 1er noeud fils */
            myNode =
                ConfigUtility.getNodeByTagName(
                                                myNode,
                                                CompilingMessages.getString( "configuration.java.general.separators.separator" ) );

            /* si ce noeud existe */
            if ( null != myNode )
            {
                NamedNodeMap attrMap = null;
                String attrValue = null, attrName = null;

                /* tant qu'il y a des noeuds */
                while ( null != myNode )
                {
                    if ( Node.ELEMENT_NODE == myNode.getNodeType() )
                    {
                        /* on récupère les attributs du noeud */
                        attrMap = myNode.getAttributes();

                        /* attribut "clé" */
                        attrName =
                            ( attrMap.getNamedItem( CompilingMessages.getString( "configuration.java.general.separators.separator.name" ) ) ).getNodeValue().trim();

                        /* attribut "valeur" */
                        attrValue =
                            ( attrMap.getNamedItem( CompilingMessages.getString( "configuration.java.general.separators.separator.value" ) ) ).getNodeValue().trim();

                        /* on mappe les clés / valeurs -> réflexion. */
                        mapKeyValue( attrName, attrValue );
                    }
                    /* on itère */
                    myNode = myNode.getNextSibling();
                }

                attrMap = null;
                attrName = null;
                attrValue = null;

                /* erreur rencontrée --> exception à lancer */
            }
            else
            {
                throwException = true;
            }
            /* erreur rencontrée --> exception à lancer */
        }
        else
        {
            throwException = true;
        }

        myNode = null;

        /* erreur rencontrée */
        if ( throwException )
        {
            /* exception lancée */
            throw new Exception( CompilingMessages.getString( "exception.xml.node_not_found" ) );
        }
    }

    /**
     * Cette méthode assure la réflexion.
     * 
     * @param pKey clé dans la hashMap.
     * @param pValue valeur.
     * @throws Exception exception de réflexion.
     */
    private void mapKeyValue( final String pKey, final String pValue )
        throws Exception
    {

        /*
         * on invoque le setter correspondant à la clé attrName, en lui passant la valeur attrValue
         */
        Object[] obj = { pValue };
        ( (Method) ( mMap.get( pKey ) ) ).invoke( this, obj );
    }

    /**
     * Cette méthode crée une map contenant des clés associées à des méthodes de type setter. <br />
     * En procédant ainsi, on pourra facilement affecter une valeur à une variable par réflexion.
     * 
     * @throws Exception exception de réflexion.
     * @see #mapKeyValue(String, String)
     */
    private void createReflectionMap()
        throws Exception
    {
        LOGGER.trace( CompilingMessages.getString( "logs.task.entering_method" ) );

        /*
         * tableau contenant la classe du paramètre à passer à chaque setter. ici, java.lang.String.
         */
        Class[] param = { String.class };

        mMap = new HashMap();

        /* Version par défaut du JDK à utiliser. */
        mMap.put( CompilingMessages.getString( "configuration.java.general.default_jdk_version" ),
                  this.getClass().getMethod( "setJDKDefaultVersion", param ) );

        /* Répertoire cible pour stocker les classes compilées. */
        mMap.put( CompilingMessages.getString( "configuration.java.general.dest_dir" ),
                  this.getClass().getMethod( "setDestDir", param ) );

        /* Mémoire requise pour la compilation. */
        mMap.put( CompilingMessages.getString( "configuration.java.general.required_memory" ),
                  this.getClass().getMethod( "setRequiredMemory", param ) );

        /* Séparateur du classpath. */
        mMap.put( CompilingMessages.getString( "configuration.java.general.separators.separator.name.classpath" ),
                  this.getClass().getMethod( "setClasspathSeparator", param ) );

        /* Répertoire cible du bundle eclipse. */
        mMap.put( CompilingMessages.getString( "configuration.java.general.eclipse_bundle_path" ),
                  this.getClass().getMethod( "setEclipseBundleDir", param ) );

        /* Répertoire cible des librairies exportées qu'il faut dézippées pour la compilation RCP. */
        mMap.put( CompilingMessages.getString( "configuration.java.general.exported_libs_path" ),
                  this.getClass().getMethod( "setExportedLibsDir", param ) );

        /* Les chemins vers les classpath des APIs java */
        mMap.put( CompilingMessages.getString( "configuration.java.general.bootclasspaths.bootclasspath" ),
                  this.getClass().getMethod( "addBootclasspath", new Class[] { String.class, String.class } ) );
    }

    /**
     * Getter.
     * 
     * @return Version par défaut du JDK à utiliser.
     */
    public String getJDKDefaultVersion()
    {
        return mJDKDefaultVersion;
    }

    /**
     * Setter.
     * 
     * @param pJDKDefaultVersion Version par défaut du JDK à utiliser.
     */
    public void setJDKDefaultVersion( String pJDKDefaultVersion )
    {
        mJDKDefaultVersion = pJDKDefaultVersion;
    }

    /**
     * Getter.
     * 
     * @return le séparateur du classpath.
     */
    public String getClasspathSeparator()
    {
        return mClasspathSeparator;
    }

    /**
     * Setter.
     * 
     * @param pClasspathSeparator le séparateur du classpath.
     */
    public void setClasspathSeparator( String pClasspathSeparator )
    {
        mClasspathSeparator = pClasspathSeparator;
    }

    /**
     * Getter.
     * 
     * @return le répertoire cible de stockage des classes compilées.
     */
    public String getDestDir()
    {
        return mDestDir;
    }

    /**
     * Setter.
     * 
     * @param pDestDir le répertoire cible de stockage des classes compilées.
     */
    public void setDestDir( String pDestDir )
    {
        mDestDir = pDestDir;
    }

    /**
     * Getter.
     * 
     * @return la mémoire requise pour la compilation
     */
    public String getRequiredMemory()
    {
        return mRequiredMemory;
    }

    /**
     * Setter.
     * 
     * @param pRequiredMemory la mémoire requise pour la compilation
     */
    public void setRequiredMemory( String pRequiredMemory )
    {
        mRequiredMemory = pRequiredMemory;
    }

    /**
     * @return le répertoire cible pour copier ou extraire le bundle eclipse.
     */
    public String getEclipseBundleDir()
    {
        return mEclipseBundleDir;
    }

    /**
     * @param pEclipseBundleDir le répertoire cible pour copier ou extraire le bundle eclipse.
     */
    public void setEclipseBundleDir( String pEclipseBundleDir )
    {
        mEclipseBundleDir = pEclipseBundleDir;
    }

    /**
     * @return le répertoire des libraries exportées
     */
    public File getExportedLibsDir()
    {
        return mExportedLibsDir;
    }

    /**
     * @param pExportedLibsDir le répertoire des libraries exportées
     */
    public void setExportedLibsDir( String pExportedLibsDir )
    {
        mExportedLibsDir = new File( pExportedLibsDir );
    }

    /**
     * @param pDialect la version java sous la forme 1.4, 1.3,...
     * @return les chemins vers les libs jre de l'api java de la version java pDialect
     */
    public List getBootclasspath( String pDialect )
    {
        return (List) mBootclasspaths.get( pDialect );
    }

    /**
     * Ajoute une entrée pour l'option -booclasspath de javac
     * 
     * @param pDialect le dialect java qui sert de clé
     * @param pPath le chemin vers une librairie de l'API sun de version pDialect
     */
    public void addBootclasspath( String pDialect, String pPath )
    {
        ArrayList libs = (ArrayList) mBootclasspaths.get( pDialect );
        if ( libs == null )
        {
            libs = new ArrayList();
        }
        libs.add( pPath );
        mBootclasspaths.put( pDialect, libs );
    }

}
