package com.airfrance.squalix.tools.compiling.java.configuration;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalecommon.util.xml.XmlImport;
import com.airfrance.squalix.configurationmanager.ConfigUtility;
import com.airfrance.squalix.core.exception.ConfigurationException;
import com.airfrance.squalix.tools.compiling.CompilingMessages;

/**
 * Classe de configuration pour la compilation des projets JAVA avec eclipse Le fichier de configuration se nomme
 * "eclipsecompiling-config.xml"
 */
public class EclipseCompilingConfiguration
    extends XmlImport
{
    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( EclipseCompilingConfiguration.class );

    /** Le workspace */
    private String mWorkspace;

    /** La commande */
    private String mCommand;

    /** Les options de la commande */
    private List mOptions;

    /** Le répertoire d'installation d'eclipse */
    private File mEclipseHome;

    /** Le répertoire contenant les plugins nécessaires au bon déroulement du plugin de compilation */
    private File mSqualeEclipsePlugins;

    /** Le chemin vers le script de modification des droits */
    private String mRightScript;

    /** Le chemin vers le script de copie de répertoire */
    private String mCopyScript;

    /**
     * Constructeur par défaut
     */
    public EclipseCompilingConfiguration()
    {
        super( LOGGER );
        mWorkspace = "";
        mCommand = "";
        mOptions = new ArrayList();
        mEclipseHome = null;
        mSqualeEclipsePlugins = null;
        mRightScript = "";
        mCopyScript = "";
    }

    /**
     * Lecture du fichier de configuration
     * 
     * @param pStream flux
     * @throws ConfigurationException si erreur
     */
    public void parse( InputStream pStream )
        throws ConfigurationException
    {
        StringBuffer errors = new StringBuffer();
        Digester digester =
            preSetupDigester( "-//EclipseCompiling Configuration DTD //EN", "/config/eclipsecompiling-config.dtd",
                              errors );
        // Traitement du workspace
        digester.addCallMethod( "configuration/workspace", "setWorkspace", 1, new Class[] { String.class } );
        digester.addCallParam( "configuration/workspace", 0 );
        // Traitement de l'exécutable
        digester.addCallMethod( "configuration/command/executable", "setCommand", 1, new Class[] { String.class } );
        digester.addCallParam( "configuration/command/executable", 0 );
        // Traitement des options
        digester.addCallMethod( "configuration/command/options/option", "addOption", 1, new Class[] { String.class } );
        digester.addCallParam( "configuration/command/options/option", 0 );
        // Traitement du répertoire d'installation d'eclipse
        digester.addCallMethod( "configuration/eclipseHome", "setEclipseHome", 1, new Class[] { String.class } );
        digester.addCallParam( "configuration/eclipseHome", 0 );
        // Traitement du répertoire d'exécution d'eclipse
        digester.addCallMethod( "configuration/squaleEclipseHome", "setSqualeEclipsePlugins", 1,
                                new Class[] { String.class } );
        digester.addCallParam( "configuration/squaleEclipseHome", 0 );
        // Traitement du script de copie
        digester.addCallMethod( "configuration/copyScript", "setCopyScript", 1, new Class[] { String.class } );
        digester.addCallParam( "configuration/copyScript", 0 );
        // Traitement du script de changement des droits
        digester.addCallMethod( "configuration/rightScript", "setRightScript", 1, new Class[] { String.class } );
        digester.addCallParam( "configuration/rightScript", 0 );
        digester.push( this );
        // Appel du parser
        parse( digester, pStream, errors );
        if ( errors.length() > 0 )
        {
            throw new ConfigurationException( CompilingMessages.getString( "exception.eclipse.configuration",
                                                                           new Object[] { errors.toString() } ) );
        }
    }

    /**
     * @return la commande
     */
    public String getCommand()
    {
        return mCommand;
    }

    /**
     * @return les options
     */
    public List getOptions()
    {
        return mOptions;
    }

    /**
     * @param option l'option facultative à ajouter
     */
    public void addOption( String option )
    {
        // On sépare les différent paramètres de l'option
        String[] options = option.split( " " );
        for ( int i = 0; i < options.length; i++ )
        {
            mOptions.add( options[i] );
        }
    }

    /**
     * @param pCommand la commande à lancer
     */
    public void setCommand( String pCommand )
    {
        mCommand = ConfigUtility.filterStringWithSystemProps( pCommand );
    }

    /**
     * @return le workspace
     */
    public String getWorkspace()
    {
        return mWorkspace;
    }

    /**
     * @param pWorkspace le workspace
     */
    public void setWorkspace( String pWorkspace )
    {
        mWorkspace = ConfigUtility.filterStringWithSystemProps( pWorkspace );
    }

    /**
     * @return le répertoire d'installation d'eclipse
     */
    public File getEclipseHome()
    {
        return mEclipseHome;
    }

    /**
     * @return le répertoire contenant les plugins nécessaires au bon déroulement du plugin de compilation
     */
    public File getSqualeEclipsePlugins()
    {
        return mSqualeEclipsePlugins;
    }

    /**
     * @param pHome le répertoire d'installation d'eclipse
     */
    public void setEclipseHome( String pHome )
    {
        mEclipseHome = new File( ConfigUtility.filterStringWithSystemProps( pHome ) );
    }

    /**
     * @param pSqualeHome le répertoire contenant les plugins nécessaires au bon déroulement du plugin de compilation
     */
    public void setSqualeEclipsePlugins( String pSqualeHome )
    {
        mSqualeEclipsePlugins = new File( ConfigUtility.filterStringWithSystemProps( pSqualeHome ) );
    }

    /**
     * @return le chemin vers le script de copie de répertoire
     */
    public String getCopyScript()
    {
        return mCopyScript;
    }

    /**
     * @return le chemin vers le script de changement des droits d'une arborescence de répertoire
     */
    public String getRightScript()
    {
        return mRightScript;
    }

    /**
     * @param pCopyScript le chemin vers le script de copie de répertoire
     */
    public void setCopyScript( String pCopyScript )
    {
        mCopyScript = ConfigUtility.filterStringWithSystemProps( pCopyScript );
    }

    /**
     * @param pRightScript le chemin vers le script de changement des droits
     */
    public void setRightScript( String pRightScript )
    {
        mRightScript = ConfigUtility.filterStringWithSystemProps( pRightScript );
    }

}
