package com.airfrance.welcom.taglib.aide;

import java.util.Hashtable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe contenant plusieurs Aide Ressource en fonction de la locale Pour changer le modèle de ce commentaire de type
 * généré, allez à : Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class Aide
{
    /** logger */
    private static Log log = LogFactory.getLog( Aide.class );

    /** l'instance pour le singleton */
    private static Aide instance = null;

    /** hashtable pour stocker l'aide ressource en fonction de la locale */
    private final Hashtable hAideLocale = new Hashtable();

    /** le nom du fichier */
    private String fileBundle = null;

    /** le prefixage de l'url */
    private String prefixURL = null;

    /**
     * Constructeur prive pour le singleton
     */
    private Aide()
    {
    }

    /**
     * Retourne l'instance de l'aide
     * 
     * @return l'instance
     */
    public static Aide getInstance()
    {
        if ( instance == null )
        {
            instance = new Aide();
        }

        return instance;
    }

    /**
     * Ajoute une Aideressource pour une locale donnée
     * 
     * @param locale la locale
     * @throws AideException l'exception susceptible d'etre levee
     */
    public void addAideRessource( final Locale locale )
        throws AideException
    {
        if ( fileBundle != null )
        {
            hAideLocale.put( locale, new AideRessource( fileBundle, locale ) );

            log.info( "Chargement de l'aide pour la locale : " + locale.getCountry() + "_" + locale.getLanguage() );
        }
        else
        {
            throw new AideException( "Nom du fichier Bundle d'Aide vide" );
        }
    }

    /**
     * Recupere un Aide Ressource en fonction de la Locale
     * 
     * @param locale la locale
     * @return un aide ressource
     */
    public AideRessource getAideRessource( final Locale locale )
    {
        return (AideRessource) hAideLocale.get( locale );
    }

    /**
     * Retourne la valeur d'une clef en fonction de sa Locale
     * 
     * @param key la cle
     * @param locale la locale
     * @return la valeur retournee
     * @throws AideException l'exception susceptible d'etre levee
     */
    public String getString( final String key, final Locale locale )
        throws AideException
    {
        try
        {
            final ResourceBundle ressourceBundle = java.util.ResourceBundle.getBundle( fileBundle, locale );
            String value = "";

            try
            {
                value = ressourceBundle.getString( key );
            }
            catch ( final MissingResourceException e )
            {
                // Trappe l'execption pour quelle ne remonte pas trop Haut
                // Elle est traité dans le finnaly
            }
            finally
            {
                if ( ( value == null ) || value.equals( "" ) || value.equals( "DEFAULT" ) )
                {
                    throw new AideException( "Pas de valeur associé a la clef " + key + " pour la locale " + locale
                        + "!!!" );
                }
            }

            // String value = getAideRessource(locale).getString(key);
            log.info( "Pour la Locale (" + locale.getCountry() + "_" + locale.getLanguage() + ") Key : " + key
                + " Value :" + value );

            return value;
        }
        catch ( final AideException ae )
        {
            // La clef n'est pas trouver essaye l'url par default
            return Aide.getInstance().getUrlDefault( key, locale );
        }
    }

    /**
     * retourne l'url d'une aide
     * 
     * @param key la cle
     * @param locale la locale
     * @return l'url d'aide
     * @throws AideException l'exception susceptible d'etre levee
     */
    public static String getUrlAide( final String key, final Locale locale )
        throws AideException
    {
        return Aide.getInstance().getPrefixURL() + locale.getLanguage() + "/"
            + Aide.getInstance().getString( key, locale );
    }

    /**
     * Initialise l'aide
     * 
     * @param fileBundle le nom du fichier
     * @param prefixURL le prefixage de l'url
     * @throws AideException l'exception susceptible d'etre levee
     */
    public static void openAide( final String fileBundle, final String prefixURL )
        throws AideException
    {
        // Stocke le nom du fichier
        Aide.getInstance().setFileBundle( fileBundle );

        // Initialise l'aide par la locale par default
        Aide.getInstance().addAideRessource( Locale.getDefault() );

        // Stocke le prefixage de l'url;
        Aide.getInstance().setPrefixURL( prefixURL );
    }

    /**
     * Accesseur
     * 
     * @param pFileBundle file bundle
     */
    public void setFileBundle( final String pFileBundle )
    {
        fileBundle = pFileBundle;
    }

    /**
     * Accesseur
     * 
     * @param key la cle
     * @param locale la locale
     * @return le default url
     * @throws AideException exception susceptible d'etre levee
     */
    public String getUrlDefault( final String key, final Locale locale )
        throws AideException
    {
        String value = "";

        try
        {
            // if (getAideRessource(locale)==null)
            // {
            // addAideRessource(locale);
            // }
            final ResourceBundle ressourceBundle = java.util.ResourceBundle.getBundle( fileBundle, locale );

            try
            {
                value = ressourceBundle.getString( AideRessource.KEY_DEFAULT );
            }
            catch ( final MissingResourceException e )
            {
                // Catch de l'exception pour pas quelle soir gere par le systeme
                // cf finnnaly
            }
            finally
            {
                if ( ( value == null ) || value.equals( "" ) )
                {
                    throw new AideException( "Pas de valeur associé a la clef " + key + " pour la locale " + locale
                        + "!!!" );
                }
            }

            // String value = getAideRessource(locale).getString(AideRessource.KEY_DEFAULT);
            log.warn( "NOTFOUND : use key :" + AideRessource.KEY_DEFAULT + ", pour la Locale (" + locale.getCountry()
                + "_" + locale.getLanguage() + ") Key : " + key + " Value :" + value );
        }
        catch ( final AideException ae )
        {
            log.warn( "NOTFOUND : default key :" + AideRessource.KEY_DEFAULT
                + ", impossible d'afficher une page d'aide" );

            throw new AideException( "Aide non disponible" );
        }

        return value;
    }

    /**
     * Accesseur
     * 
     * @param pPrefixURL le nouveau prefixUrl
     */
    public void setPrefixURL( final String pPrefixURL )
    {
        prefixURL = pPrefixURL;
    }

    /**
     * Accesseur
     * 
     * @return le prefixUrl
     */
    public String getPrefixURL()
    {
        return prefixURL;
    }
}