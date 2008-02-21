package com.airfrance.squalix.tools.mccabe;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * @author m400842 (by rose)
 * @version 1.0
 */
public class McCabeMessages extends BaseMessages {
    /** Instance */
    static private McCabeMessages mInstance = new McCabeMessages();

    /**
     * Constructeur privé pour éviter l'instanciation
     * @roseuid 42D3C09200D8
     */
    private McCabeMessages() {
        super("com.airfrance.squalix.tools.mccabe.mccabe");
    }

    /**
     * Retourne la chaîne de caractère identifiée par la clé.
     * @param pKey nom de la clé.
     * @return la chaîne associée.
     * @roseuid 42D3C09200D9
     */
    public static String getString(String pKey) {
        return mInstance.getBundleString(pKey);
    }

    /**
     * Obtention d'une chaîne
     * @param pKey clef
     * @param pArgument argument
     * @return chaîne valuée
     */
    public static String getString(String pKey, Object pArgument) {
        return getString(pKey, new Object[] { pArgument });
    }

    /**
     * @param pKey clef
     * @param pObjects objets
     * @return chaîne valuée
     */
    public static String getString(String pKey, Object[] pObjects) {
        return mInstance.getBundleString(pKey, pObjects);
    }
}
