package com.airfrance.squalix.tools.jdepend;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * Messages pour la tâche ckjm
 */
public class JDependMessages extends BaseMessages {
    /** Instance */
    static private JDependMessages mInstance = new JDependMessages();

    /**
     * Constructeur par défaut. Privé pour éviter l'instanciation.
     */
    private JDependMessages() {
        super("com.airfrance.squalix.tools.jdepend.jdepend");
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
