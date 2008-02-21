package com.airfrance.squaleweb.resources;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * @author m400832
 * @version 1.0
 */
public class WebMessages extends BaseMessages{

    /**
     * @param pBundleName le nom du bundle
     */
    private WebMessages(String pBundleName) {
        super(pBundleName);
    }

    /**
     * Logger.
     */
    private static final Log LOGGER =
        LogFactory.getLog(WebMessages.class);

    /**
     * Chemin du fichier de propriétés.
     */
    private static final String BUNDLE_NAME =
        "com.airfrance.squaleweb.resources.ApplicationResources";

    /**
     * Instance de ResourceBudle utilisée par defaut
     */
    private static final ResourceBundle RESOURCE_BUNDLE =
        ResourceBundle.getBundle(BUNDLE_NAME, new Locale(""));


    /**
     * Retourne la chaîne de caractère identifiée par la clé.
     * @param pKey nom de la clé.
     * @return la chaîne associée.
     */
    public static String getString(String pKey) {
        String value = null;
        try {
            value = RESOURCE_BUNDLE.getString(pKey);
        } catch (MissingResourceException e) {
            LOGGER.warn(e,e);
            value = pKey;
        }
        return value;
    }

    /**
     * Retourne la chaîne de caractère identifiée par la clé.
     * @param pLocale locale
     * @param pKey nom de la clé.
     * @return la chaîne associée.
     */
    public static String getString(Locale pLocale, String pKey) {
        String value = null;
        try {
            // On modifie la locale par défaut pour récupérer
            // notre fichier properties par défaut (ApplicationResources.properties) dans le cas
            // où il n'y a pas de ressource pour la locale passée en paramètre
            Locale.setDefault(new Locale(""));
            ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, pLocale);
            value = bundle.getString(pKey);
            if (value==null) {
                value = DataBaseMessages.getMessage(pLocale, pKey);
            }
        } catch (MissingResourceException e) {
            value = DataBaseMessages.getMessage(pLocale, pKey);
        }
        return value;
    }
    
    /**
     * Retourne la chaîne de caractère identifiée par la clé.
     * @param pRequest requête
     * @param pKey nom de la clé.
     * @return la chaîne associée.
     */
    public static String getString(HttpServletRequest pRequest, String pKey) {
        String value = null;
        Locale locale = (Locale) pRequest.getSession().getAttribute(Globals.LOCALE_KEY);
        if (locale==null) {
            locale = Locale.getDefault();
        }
        return getString(locale, pKey);
    }

    /**
     * @param pKey la clé
     * @return le résultat après conversion dans le int associé
     */
    public static int getInt(String pKey) {
        return Integer.decode(getString(pKey)).intValue();
    }

    /**
     * @param pKey la clé
     * @return le résultat après conversion dans le booléen associé
     */
    public static boolean getBool(String pKey) {
        return Boolean.valueOf(getString(pKey)).booleanValue();
    }

    /**
     * @param pKey la clé
     * @return le résultat après conversion dans le double associé
     */
    public static double getDouble(String pKey) {
        return Double.parseDouble(getString(pKey));
    }

    /**
     * @param pKey la clé
     * @param pValues les paramètres du message
     * @return le texte
     */
    public static Object getString(String pKey, String[] pValues) {
        MessageFormat format = new MessageFormat(getString(pKey));
        return format.format(pValues);
    }

    /**
     * @param pLocale la locale
     * @param pKey la clé
     * @param pValues les paramètres du message
     * @return le texte
     */
    public static Object getString(Locale pLocale, String pKey, String[] pValues) {
        MessageFormat format = new MessageFormat(getString(pLocale, pKey));
        return format.format(pValues);
    }
}
