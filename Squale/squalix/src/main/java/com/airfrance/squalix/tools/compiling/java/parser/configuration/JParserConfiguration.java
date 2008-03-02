/*
 * Créé le 27 juil. 05, par M400832.
 */
package com.airfrance.squalix.tools.compiling.java.parser.configuration;

import com.airfrance.squalix.tools.compiling.java.configuration.JCompilingConfiguration;

/**
 * Configuration générique des parseurs.
 * 
 * @author m400832
 * @version 1.0
 */
public class JParserConfiguration
    extends JCompilingConfiguration
{

    /**
     * Extension des fichiers <code>class</code>.
     */
    public static final String EXT_CLASS = "class";

    /**
     * Extension des fichiers <code>zip</code>.
     */
    public static final String EXT_ZIP = "zip";

    /**
     * Extension des fichiers <code>jar</code>.
     */
    public static final String EXT_JAR = "jar";

    /**
     * Nom du fichier de classpath à parser.
     */
    private String mFilename = "";

    /**
     * Constructeur par défaut.
     * 
     * @throws Exception exception.
     */
    public JParserConfiguration()
        throws Exception
    {
        super();
    }

    /**
     * Getter
     * 
     * @return le nom du fichier de classpath à parser.
     */
    public String getFilename()
    {
        return mFilename;
    }

    /**
     * Setter.
     * 
     * @param pFilename le nom du fichier de classpath à parser.
     */
    public void setFilename( String pFilename )
    {
        mFilename = pFilename;
    }
}
