package com.airfrance.squalecommon.enterpriselayer.facade.config;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * Messages pour la configuration du portail.
 *
 */
public class ConfigMessages extends BaseMessages {
    /** Instance */
    static private ConfigMessages mInstance = new ConfigMessages();

    /**
     * Constructeur par défaut. Privé pour éviter l'instanciation.
     */
    private ConfigMessages() {
        super("com.airfrance.squalecommon.enterpriselayer.facade.config.config_messages");
    }

    /**
     * Retourne la chaîne de caractère identifiée par la clé.
     * @param pKey nom de la clé.
     * @return la chaîne associée.
     */
    public static String getString(String pKey) {
        return mInstance.getBundleString(pKey);
    }

    /**
     * 
     * Retourne la chaîne de caractère identifiée par la clé.
     * @param pKey nom de la clé.
     * @param pValues les valeurs à insérer dans la chaine
     * @return la chaîne associée.
     */
    public static String getString(String pKey, Object[] pValues) {
        return mInstance.getBundleString(pKey, pValues);
    }

}
