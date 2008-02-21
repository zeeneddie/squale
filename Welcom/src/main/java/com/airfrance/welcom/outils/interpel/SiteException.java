/*
 * Créé le 7 oct. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.interpel;

/**
 *
 * Erreur sur le site
 *
 * @author M327837
 * @deprecated 
 *
 */
public class SiteException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4481655564326801375L;

	/**
     * Constructeur d'un probleme sur le site 
     * @param m Message de l'exception
     */
    public SiteException(final String m) {
        super(m);
    }
}