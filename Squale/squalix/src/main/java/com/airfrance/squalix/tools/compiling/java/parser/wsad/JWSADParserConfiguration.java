/*
 * Créé le 26 juil. 05, par M400832.
 */
package com.airfrance.squalix.tools.compiling.java.parser.wsad;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.airfrance.squalix.configurationmanager.ConfigUtility;
import com.airfrance.squalix.tools.compiling.CompilingMessages;
import com.airfrance.squalix.tools.compiling.java.parser.configuration.JParserConfiguration;

/**
 * Cette classe permet de récupérer la configuration relative au parser 
 * de fichiers de classpath pour les projets WSAD 5.x.
 * @author m400832
 * @version 2.1
 */
public class JWSADParserConfiguration extends JParserConfiguration {

    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog(JWSADParserConfiguration.class);

    /**
     * Balise classpath.
     */
    private String mClasspathAnchor = "";

    /**
     * Balise classpathentry.
     */
    private String mClasspathentry = "";

    /**
     * Attribut kind.
     */
    private String mKind = "";

    /**
     * Attribut path.
     */
    private String mPath = "";

    /**
     * Attribut exported.
     */
    private String mExported = "";

    /**
     * Valeur "src" pour l'attribut kind.
     */
    private String mSrc = "";

    /**
     * Valeur "lib" pour l'attribut kind.
     */
    private String mLib = "";

    /**
     * Valeur "var" pour l'attribut kind.
     */
    private String mVar = "";

    /**
     * Valeur "con" pour l'attribut kind.
     */
    private String mCon = "";

    /**
     * HashMap utilisée pour la réflexion.
     */
    protected HashMap mMap = null;

    /** indique le type des projets (RSA, WSAD,...)*/
    protected String projectType;

    /**
     * Constructeur.
     * @throws Exception exception en cas d'erreur lors de la 
     * configuration.
     */
    public JWSADParserConfiguration() throws Exception {
        super();
        createReflectionMap();
        getConfigurationFromXML(CompilingMessages.getString("configuration.file"));
    }

    /**
     * Cette méthode lance le parsing du fichier de configuration XML.
     * @param pFile chemin du fichier de configuration à parser.
     * @throws Exception exception en cas d'erreur de parsing.
     */
    private void getConfigurationFromXML(final String pFile) throws Exception {
        LOGGER.trace(CompilingMessages.getString("logs.task.entering_method"));
        boolean isNull = false;

        /* on récupère le noeud racine */
        Node root = ConfigUtility.getRootNode(pFile, CompilingMessages.getString("configuration.root"));

        /* si le noeud n'est pas nul */
        if (null != root) {
            /* on récupère le noeud concernant la compilation JAVA */
            Node myNode = ConfigUtility.getNodeByTagName(root, CompilingMessages.getString("configuration.java"));
            /* si le noeud n'est pas nul */
            if (null != myNode) {
                /* on récupère le noeud concernant le parsing JAVA */
                myNode = ConfigUtility.getNodeByTagName(myNode, CompilingMessages.getString("configuration.java.parsing"));
                /* si le noeud n'est pas nul */
                if (null != myNode) {
                    /* on récupère le noeud concernant le parsing pour des 
                     * projets JAVA développés sous WSAD 4.x et 5.x */
                    myNode = ConfigUtility.getNodeByTagName(myNode, CompilingMessages.getString("configuration.java.parsing.wsad"));
                    /* on appelle différentes méthodes pour récupérer 
                     * les données */
                    getFilenameFromXML(myNode);
                    /* on récupère les valeurs de configuration des 
                     * noms des clés. */
                    getTagsFromXML(
                        myNode,
                        CompilingMessages.getString("configuration.java.parsing.wsad.keys"),
                        CompilingMessages.getString("configuration.java.parsing.wsad.keys.key"),
                        CompilingMessages.getString("configuration.java.parsing.wsad.keys.key.name"),
                        CompilingMessages.getString("configuration.java.parsing.wsad.keys.key.value"));
                    /* on récupère les valeurs de configuration des 
                     * valeurs des clés. */
                    getTagsFromXML(
                        myNode,
                        CompilingMessages.getString("configuration.java.parsing.wsad.values"),
                        CompilingMessages.getString("configuration.java.parsing.wsad.values.value"),
                        CompilingMessages.getString("configuration.java.parsing.wsad.values.value.name"),
                        CompilingMessages.getString("configuration.java.parsing.wsad.values.value.value"));
                } else {
                    /* noeud non trouvé */
                    isNull = true;
                }
            } else {
                /* noeud non trouvé */
                isNull = true;
            }
            /* ménage */
            myNode = null;

        } else {
            /* noeud non trouvé */
            isNull = true;
        }

        /* si un noeud n'a pas été trouvé */
        if (isNull) {
            /* on lance une nouvelle exception */
            throw new Exception(CompilingMessages.getString("exception.xml.node_not_found"));
        }
        /* ménage */
        root = null;
    }

    /**
     * Cette méthode récupère le nom du fichier de classpath à parser.
     * @param pNode Noeud XML à parser.
     * @throws Exception exception en cas d'erreur de parsing.
     */
    private void getFilenameFromXML(Node pNode) throws Exception {
        /* on récupère le noeud contenant le nom du fichier contenant le 
         * classpath du projet WSAD */
        Node myNode = ConfigUtility.getNodeByTagName(pNode, CompilingMessages.getString("configuration.java.parsing.wsad.filename"));

        /* si le noeud n'est pas nul, et est de type ELEMENT */
        if (null != myNode && Node.ELEMENT_NODE == myNode.getNodeType()) {
            setFilename(myNode.getFirstChild().getNodeValue().trim());

            /* sinon, il y a un problème concernant ce noeud */
        } else {
            /* on lance une nouvelle exception */
            throw new Exception(CompilingMessages.getString("exception.xml.node_not_found"));
        }
        myNode = null;
    }

    /**
    * Cette méthode récupère le nom des clés nécessaires pour parser 
    * le fichier de classpath.
    * @param pNode noeud XML à parser.
    * @param pRootAnchor noeud racine.
    * @param pChildAnchor noeud fils.
    * @param pChildName attribut "name" du noeud fils.
    * @param pChildValue attribut "value" du noeud fils.
    * @throws Exception exception en cas d'erreur lors du parsing.
    * @see #mapKeyValues(String, String)
    */
    private void getTagsFromXML(final Node pNode, final String pRootAnchor, final String pChildAnchor, final String pChildName, final String pChildValue) throws Exception {
        LOGGER.trace(CompilingMessages.getString("logs.task.entering_method"));

        /* on récupère le noeud racine contenant les clés */
        Node myNode = ConfigUtility.getNodeByTagName(pNode, pRootAnchor);

        boolean throwException = false;

        /* si le noeud est bien trouvé */
        if (null != myNode && Node.ELEMENT_NODE == myNode.getNodeType()) {

            /* on récupère le premier noeud fils */
            myNode = ConfigUtility.getNodeByTagName(myNode, pChildAnchor);

            if (null != myNode) {
                /* on initialise des variables pour la boucle 
                 * qui va suivre */
                NamedNodeMap attrMap = null;
                String attrValue = null, attrName = null;

                /* tant qu'il y a des noeuds */
                while (null != myNode) {
                    /* s'il est de type ELEMENT */
                    if (Node.ELEMENT_NODE == myNode.getNodeType()) {
                        /* récupération des attributs du noeud */
                        attrMap = myNode.getAttributes();

                        /* attribut "clé" */
                        attrName = (attrMap.getNamedItem(pChildName)).getNodeValue().trim();

                        /* attribut "valeur" */
                        attrValue = (attrMap.getNamedItem(pChildValue)).getNodeValue().trim();

                        /* on mappe les clés et les valeurs */
                        mapKeyValues(attrName, attrValue);
                    }
                    /* on itère sur les noeuds */
                    myNode = myNode.getNextSibling();
                }
                /* ménage */
                attrMap = null;
                attrName = null;
                attrValue = null;
            } else {
                /* noeud vide */
                throwException = true;
            }
        } else {
            /* noeud vide */
            throwException = true;
        }

        myNode = null;

        /* une erreur s'est produite : le noeud était vide */
        if (throwException) {
            /* on lance l'exception en rapport */
            throw new Exception(CompilingMessages.getString("exception.xml.node_not_found"));
        }
    }

    /**
     * Cette méthode permet d'attribuer à des attributs de classes 
     * des valeurs issues du fichier de configuration xml <code>compiling-
     * config.xml</code>. On utilise la réflexion pour faire chuter 
     * les complexités cyclomatique, cyclomatique essentielle, 
     * et d'intégration.
     * @param pKeyName nom de la clé.
     * @param pKeyValue valeur de la clé.
     * @throws Exception exception si le nom de la clé ne fait pas parti 
     * des clés préalablement définies dans le fichier de configuration 
     * <code>com.airfrance.squalix.tools.compiling.compiling.properties</code>.
     * @see #createReflectionMap()
     */
    private void mapKeyValues(String pKeyName, String pKeyValue) throws Exception {
        LOGGER.trace(CompilingMessages.getString("logs.task.entering_method"));

        /* on invoque le setter correspondant à la clé pKeyName, 
         * en lui passant la valeur pKeyValue */
        Object[] obj = { pKeyValue };
        ((Method) (mMap.get(pKeyName))).invoke(this, obj);
    }

    /**
     * Cette méthode crée une map contenant des clés associées à des 
     * méthodes de type setter. <br />
     * En procédant ainsi, on pourra facilement affecter une valeur à 
     * une variable par réflexion.
     * @throws Exception exception de réflexion.
     * @see #mapKeyValues(String, String)
     */
    protected void createReflectionMap() throws Exception {
        LOGGER.trace(CompilingMessages.getString("logs.task.entering_method"));

        /* tableau contenant la classe du paramètre à passer à
         * chaque setter. ici, java.lang.String. */
        Class[] param = { String.class };

        mMap = new HashMap();

        /* balise <classpath> */
        mMap.put(CompilingMessages.getString("configuration.java.parsing.wsad.keys.key.name.classpath"), this.getClass().getMethod("setClasspathAnchor", param));

        /* balise <classpathentry> */
        mMap.put(CompilingMessages.getString("configuration.java.parsing.wsad.keys.key.name.classpathentry"), this.getClass().getMethod("setClasspathentry", param));

        /* attribut "kind" pour la balise <classpathentry> */
        mMap.put(CompilingMessages.getString("configuration.java.parsing.wsad.keys.key.name.kind"), this.getClass().getMethod("setKind", param));

        /* attribut "path" pour la balise <classpathentry> */
        mMap.put(CompilingMessages.getString("configuration.java.parsing.wsad.keys.key.name.path"), this.getClass().getMethod("setPath", param));

        /* attribut "exported" pour la balise <classpathentry> */
        mMap.put(CompilingMessages.getString("configuration.java.parsing.wsad.keys.key.name.exported"), this.getClass().getMethod("setExported", param));

        /* valeur "src" possible pour l'attribut "kind" 
         * pour la balise <classpathentry> */
        mMap.put(CompilingMessages.getString("configuration.java.parsing.wsad.values.value.name.src"), this.getClass().getMethod("setSrc", param));

        /* valeur "lib" possible pour l'attribut "kind" 
         * pour la balise <classpathentry> */
        mMap.put(CompilingMessages.getString("configuration.java.parsing.wsad.values.value.name.lib"), this.getClass().getMethod("setLib", param));

        /* valeur "var" possible pour l'attribut "kind" 
         * pour la balise <classpathentry> */
        mMap.put(CompilingMessages.getString("configuration.java.parsing.wsad.values.value.name.var"), this.getClass().getMethod("setVar", param));

        /* valeur "con" possible pour l'attribut "kind" 
         * pour la balise <classpathentry> */
        mMap.put(CompilingMessages.getString("configuration.java.parsing.wsad.values.value.name.con"), this.getClass().getMethod("setCon", param));
    }

    /**
     * Getter.
     * @return la valeur de la balise classpath.
     */
    public String getClasspathAnchor() {
        return mClasspathAnchor;
    }

    /**
     * Getter.
     * @return la valeur de la balise classpathentry.
     */
    public String getClasspathentry() {
        return mClasspathentry;
    }

    /**
     * Getter.
     * @return la valeur de l'attribut kind.
     */
    public String getKind() {
        return mKind;
    }

    /**
     * Getter.
     * @return la valeur de l'attribut path.
     */
    public String getPath() {
        return mPath;
    }

    /**
     * Getter.
     * @return Valeur "lib" pour l'attribut kind.
     */
    public String getLib() {
        return mLib;
    }

    /**
     * Getter.
     * @return Valeur "src" pour l'attribut kind.
     */
    public String getSrc() {
        return mSrc;
    }

    /**
     * Getter.
     * @return Valeur "var" pour l'attribut kind.
     */
    public String getVar() {
        return mVar;
    }

    /**
     * @return la valeur "con" pour l'attribut kind.
     */
    public String getCon() {
        return mCon;
    }

    /**
     * Setter. 
     * @param pLib Valeur "lib" pour l'attribut kind.
     */
    public void setLib(String pLib) {
        mLib = pLib;
    }

    /**
     * @param pValue valeur "con" pour l'attribut kind.
     */
    public void setCon(String pValue) {
        mCon = pValue;
    }

    /**
     * Setter.
     * @param pVar Valeur "var" pour l'attribut kind.
     */
    public void setVar(String pVar) {
        mVar = pVar;
    }

    /**
     * Setter.
     * @param pSrc Valeur "src" pour l'attribut kind.
     */
    public void setSrc(String pSrc) {
        mSrc = pSrc;
    }

    /**
     * Setter.
     * @param pClasspathAnchor la valeur de la balise classpath.
     */
    public void setClasspathAnchor(String pClasspathAnchor) {
        mClasspathAnchor = pClasspathAnchor;
    }

    /**
     * Setter.
     * @param pClasspathentry la valeur de la balise classpathentry.
     */
    public void setClasspathentry(String pClasspathentry) {
        mClasspathentry = pClasspathentry;
    }

    /**
     * Setter.
     * @param pKind la valeur de l'attribut kind.
     */
    public void setKind(String pKind) {
        mKind = pKind;
    }

    /**
     * Setter.
     * @param pPath la valeur de l'attribut path.
     */
    public void setPath(String pPath) {
        mPath = pPath;
    }
    /**
     * @return l'attribut exported
     */
    public String getExported() {
        return mExported;
    }

    /**
     * @param pExported la valeur de l'attribut exported
     */
    public void setExported(String pExported) {
        mExported = pExported;
    }

}
