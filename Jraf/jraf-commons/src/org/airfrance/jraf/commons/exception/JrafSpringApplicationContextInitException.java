/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * Créé le 17 févr. 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.jraf.commons.exception;

/**
 * @author 6391988
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;
 * Préférences&gt;Java&gt;Génération de code&gt;
 * Code et commentaires
 */
public class JrafSpringApplicationContextInitException extends JrafException {

	/**
	 * Contructeur JrafSpringApplicationContextInitException.
	 */
	public JrafSpringApplicationContextInitException() {
		super();
	}
	
	/**
	 * Ajoute un messge d'exception JRAF en cas d'échec
	 * de l'initialisation de applicationContext.
	 * @param message
	 */
	public JrafSpringApplicationContextInitException(String message) {
		super(message);
	}
	
	/**
	 * Ajoute une clause Throwable.
	 * @param root
	 */
	public JrafSpringApplicationContextInitException(Throwable root) {
		super(root);
	}
	
	/**
	 * Ajoute une message et une clause Throwable.
	 * @param message
	 * @param root
	 */
	public JrafSpringApplicationContextInitException(String message, Throwable root) {
		super(message,root);
	}
}
