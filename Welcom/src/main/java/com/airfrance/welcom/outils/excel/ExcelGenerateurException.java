/*
 * Créé le 28 oct. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.excel;

/**
 *
 * Classe d'exception retournée par l' ExcelGenerateur
 * @author Rémy Bouquet
 *
 */
public class ExcelGenerateurException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 5604953364154760625L;

    /**
     * Lévée un exception si un probleme a la generation Excel
     * @param arg0 : Libelle du message
     */
    public ExcelGenerateurException(final String arg0) {
        super(arg0);
    }
    
    /**
     * Lévée un exception si un probleme a la generation Excel
     * @param message : Libelle du message
     * @param cause : Cause
     */
    public ExcelGenerateurException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Lévée un exception si un probleme a la generation Excel
     * @param cause : Cause
     */
    public ExcelGenerateurException(Throwable cause) {
        super(cause);
    }
}