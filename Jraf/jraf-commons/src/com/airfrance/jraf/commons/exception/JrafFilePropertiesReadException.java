/*
 * Créé le 21 févr. 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.jraf.commons.exception;

/**
 * @author 6391988
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class JrafFilePropertiesReadException extends JrafDaoException {
	
	public JrafFilePropertiesReadException(String message) {
		super(message);
	}
		
	public JrafFilePropertiesReadException(Throwable root) {
			super(root);
		}
		
	public JrafFilePropertiesReadException(String msg, Throwable root) {
			super(msg, root);
	}
}
