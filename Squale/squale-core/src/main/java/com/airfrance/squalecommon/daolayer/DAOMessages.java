package com.airfrance.squalecommon.daolayer;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * @author M400843
 *
 */
public class DAOMessages extends BaseMessages {
    /** Instance */
    static private DAOMessages mInstance = new DAOMessages();

    /**
     * Constructeur par défaut. Privé pour éviter l'instanciation.
     */
    private DAOMessages() {
        super("com.airfrance.squalecommon.daolayer.dao_messages");
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
    public static String getString(String pKey, String[] pValues) {
        return mInstance.getBundleString(pKey, pValues);
    }
}
