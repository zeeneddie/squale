/*
 * Créé le 8 mars 06
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
public class JrafIllegalStateException extends JrafRuntimeException {

	/**
	 * Constructeur par defaut. 
	 */
	public JrafIllegalStateException() {
		super();
	}
	
	/**
	 * Exception.
	 * @param msg
	 */
	public JrafIllegalStateException(String msg) {
		super(msg);
	}
	
	/**
	 * Exception.
	 * @param msg
	 * @param th
	 */
	public JrafIllegalStateException(String msg, Throwable th) {
		super(msg, th);
	}
	
	/**
	 * Renvoie une exception .
	 * @param th
	 */
	public JrafIllegalStateException(Throwable th) {
		super(th);
	}
}
