package com.airfrance.squalix.tools.compiling.java.parser.rsa;

import com.airfrance.squalix.tools.compiling.CompilingMessages;
import com.airfrance.squalix.tools.compiling.java.parser.wsad.JWSADParserConfiguration;

/**
 * Cette classe permet de récupérer la configuration relative au parser de fichiers de classpath pour les projets RSA7
 */
public class JRSAParserConfiguration
    extends JWSADParserConfiguration
{
    /**
     * Valeur "con" pour l'attribut kind.
     */
    private String mCon = "con";

    /**
     * Nom du projet EAR (peut être vide)
     */
    private String mEARProject = "";

    /**
     * Chemin vers le fichier Manifest.mf du projet
     */
    private String mManifestPath = "";

    /**
     * Chemin vers le fichier contenant les propriétés d'un projet J2EE (sert à récupérer le répertoire d'application
     * qui est par défaut WebContent)
     */
    private String mWebSettings = "";

    /**
     * Constructeur par défaut
     * 
     * @throws Exception exception
     */
    public JRSAParserConfiguration()
        throws Exception
    {
        super();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalix.tools.compiling.java.parser.wsad.JWSADParserConfiguration#createReflectionMap()
     */
    protected void createReflectionMap()
        throws Exception
    {
        super.createReflectionMap();
        /*
         * valeur "con" possible pour l'attribut "kind" pour la balise <classpathentry>
         */
        mMap.put( CompilingMessages.getString( "configuration.java.parsing.wsad.values.value.name.con" ),
                  this.getClass().getDeclaredMethod( "setCon", new Class[] { String.class } ) );
    }

    /**
     * @return la valeur "con" pour l'attribut kind.
     */
    public String getCon()
    {
        return mCon;
    }

    /**
     * @param pValue valeur "con" pour l'attribut kind.
     */
    public void setCon( String pValue )
    {
        mCon = pValue;
    }

    /**
     * @return le chemin vers le fichier de configuration web à parser
     */
    public String getWebSettings()
    {
        return mWebSettings;
    }

    /**
     * @param pWebSettings le chemin vers le fichier de configuration web à parser
     */
    public void setWebSettings( String pWebSettings )
    {
        mWebSettings = pWebSettings;
    }

    /**
     * @return le nom du projet EAR
     */
    public String getEARProject()
    {
        return mEARProject;
    }

    /**
     * @return le chemin vers le manifest
     */
    public String getManifestPath()
    {
        return mManifestPath;
    }

    /**
     * @param pEARProject le nom du projet EAR
     */
    public void setEARProject( String pEARProject )
    {
        mEARProject = pEARProject;
    }

    /**
     * @param pManifestPath le chemin vers le manifest
     */
    public void setManifestPath( String pManifestPath )
    {
        mManifestPath = pManifestPath;
    }

}
