package com.airfrance.squalix.tools.cpptest;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * Messages utilisés par la tâche CppTest
 */
public class CppTestMessages extends BaseMessages {
    /** Instance */
    static private CppTestMessages mInstance = new CppTestMessages();
    /**
     * Constructeur privé pour éviter l'instanciation
     * 
     */
    private CppTestMessages() {
        super("com.airfrance.squalix.tools.cpptest.cpptest");
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
