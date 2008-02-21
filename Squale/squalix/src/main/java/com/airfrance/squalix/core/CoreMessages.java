package com.airfrance.squalix.core;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * 
 */
public class CoreMessages extends BaseMessages {
    /** Instance */
    static private CoreMessages mInstance = new CoreMessages();

    /**
     * Constructeur privé pour éviter l'instanciation
     * 
     */
    private CoreMessages() {
        super("com.airfrance.squalix.core.core");
    }

    /**
     * Retourne la chaîne de caractère identifiée par la clé.
     * @param pKey nom de la clé.
     * @return la chaîne associée.
     * 
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

    /**
     * @param pKey clef
     * @param pArgument argument
     * @return chaîne associée
     */
    public static String getString(String pKey, Object pArgument) {
        return getString(pKey, new Object[] { pArgument });
    }
}
