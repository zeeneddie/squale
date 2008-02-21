package com.airfrance.welcom.taglib.aide;

import java.util.Locale;

/**
 * Classe contenant le ressourceBundle, la locale, et la localisation du fichier de ressource
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class AideRessource {    
    /** Constante */
    public final static String KEY_DEFAULT = "DEFAULT";
    /** Constante */
    private final static String KEY_NOTFOUND = "DEFAULT";
    /** le ressource bundle*/
    private java.util.ResourceBundle resourceBundle = null;
    /** la locale */
    private Locale locale = Locale.getDefault();
    /** le fichier de ressource*/
    private String fileBundle = null;

    /**
     * Constructeur
     * @param pFileBundle le fichier de ressource a utiliser
     * @param pLocale la locale
     * @throws AideException exception susceptible d'etre levee a l'ouverture du fichier
     */
    public AideRessource(final String pFileBundle, final Locale pLocale) throws AideException {
        setLocale(pLocale);
        setFileBundle(pFileBundle);
        openRessourceBundle();
    }

    /**
     * accesseur
     * @param pFileBundle le nouveau fileBundle
     */
    public void setFileBundle(final String pFileBundle) {
        fileBundle = pFileBundle;
    }

    /**
     * accesseur
     * @param pLocale la nouvelle locale
     */
    public void setLocale(final Locale pLocale) {
        locale = pLocale;
    }

    /**
     * Retourne la valeur stocke dans le ressource Bundle
     * @param key la cle
     * @param parDefaut la valeur par defaut si le ressource bundle est nul, ou si la valeur associee a la cle n'existe pas
     * @return la valeur stockee dans le ressource bundle
     */
    public String getValue(final String key, final String parDefaut) {
        if ((resourceBundle == null) || (resourceBundle.getString(key) == null)) {
            return parDefaut;
        } else {
            return resourceBundle.getString(key);
        }
    }

    /**
     * Retourne la valeur Sockee dans la ressource Bundle, si pas trouvee retourne la clef DEFAUT
     * @param key la cle
     * @return la valeur stockee dans le ressource bundle
     */
    public String getString(final String key) {
        return getValue(key, KEY_NOTFOUND);
    }

    
    /**
     * Ouvre le ressourceBundle
     * @throws AideException exception susceptible d'etre levee 
     */
    public void openRessourceBundle() throws AideException {
        resourceBundle = java.util.ResourceBundle.getBundle(fileBundle, locale);
    }
}