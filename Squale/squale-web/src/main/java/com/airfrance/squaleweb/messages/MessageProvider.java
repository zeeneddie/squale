package com.airfrance.squaleweb.messages;

/**
 * Fournisseur de message
 * Cette interface est utilisée pour la fourniture de message
 * venant de la base de données
 */
public interface MessageProvider {
    /**
     * Obtention d'un message
     * Si le message n'est pas trouvé dans la langue demandée,
     * la langue par défaut est utilisée
     * @param pLang langue
     * @param pKey clef
     * @return message ou null si non trouvé
     */
    public String getMessage(String pLang, String pKey);

}
