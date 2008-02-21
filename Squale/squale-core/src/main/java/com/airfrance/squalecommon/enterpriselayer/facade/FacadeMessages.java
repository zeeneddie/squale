package com.airfrance.squalecommon.enterpriselayer.facade;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * Messages spécifiques à la couche facade
 *
 */
public class FacadeMessages extends BaseMessages {
    /** Instance */
    static private FacadeMessages mInstance = new FacadeMessages();

    /**
     * Constructeur par défaut. Privé pour éviter l'instanciation.
     */
    private FacadeMessages() {
        super("com.airfrance.squalecommon.enterpriselayer.facade.facade_messages");
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
