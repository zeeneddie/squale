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
