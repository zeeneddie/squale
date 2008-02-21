/*
 * Créé le 3 févr. 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.addons.spell.exception;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WSpellCheckerNotFound extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 4750174715019154209L;

    /**
     * Contructeur si le SpellChecker n'est pas trouvé 
     */
    public WSpellCheckerNotFound() {
        super();
    }

    /**
     * Contructeur si le SpellChecker n'est pas trouvé
     * @param message Message
     */
    public WSpellCheckerNotFound(final String message) {
        super(message);
    }

    /**
     * Contructeur si le SpellChecker n'est pas trouvé
     * @param message Message
     * @param cause Cause
     */
    public WSpellCheckerNotFound(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Contructeur si le SpellChecker n'est pas trouvé
     * @param cause Cause
     */
    public WSpellCheckerNotFound(final Throwable cause) {
        super(cause);
    }

}
