package com.airfrance.squalecommon.datatransfertobject;

import java.io.Serializable;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * @author M400843
 *
 */
public class DTOMessages extends BaseMessages implements Serializable {
    /** Instance */
    static private DTOMessages mInstance = new DTOMessages();

    /**
     * Constructeur par défaut. Privé pour éviter l'instanciation.
     */
    private DTOMessages() {
        super("com.airfrance.squalecommon.datatransfertobject.dto_messages");
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
