package com.airfrance.squalix.tools.macker;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * Messages pour la tâche Macker
 */
public class MackerMessages extends BaseMessages {
    /** Instance */
    static private MackerMessages mInstance = new MackerMessages();

    /**
     * Constructeur par défaut. Privé pour éviter l'instanciation.
     */
    private MackerMessages() {
        super("com.airfrance.squalix.tools.macker.macker");
    }

    /**
     * Retourne la chaîne de caractère identifiée par la clé.
     * @param pKey nom de la clé.
     * @return la chaîne associée.
     */
    public static String getString(String pKey) {
        return mInstance.getBundleString(pKey);
    }
}
