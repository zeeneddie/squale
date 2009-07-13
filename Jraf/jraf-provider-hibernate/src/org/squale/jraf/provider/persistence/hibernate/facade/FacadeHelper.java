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
 * Created on Sep 15, 2004
 */
package org.squale.jraf.provider.persistence.hibernate.facade;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.commons.exception.JrafPersistenceException;
import org.squale.jraf.spi.persistence.ISession;

/**
 * <p>Title : FacadeHelper.java</p>
 * <p>Description : Classe d'assistance pour les Facade.</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * 
 */
public class FacadeHelper {

	/** log */
	private static final Log log = LogFactory.getLog(FacadeHelper.class);

	/**
	 * Ferme la session de persistance
	 * @param session session de persistance
	 * @param source declencheur de la fermeture de session
	 * @throws JrafEnterpriseException
	 */
	public static void closeSession(ISession session, String source)
		throws JrafEnterpriseException {

		try {
			if (session != null) {
				// on ferme la session 
				session.closeSession();
			}
		} catch (JrafPersistenceException e) {
			log.error(
				"Probleme lors de la fermeture de la session dans le composant '"
					+ source
					+ "'. Erreur :  "
					+ e.getMessage(),
				e);
			throw new JrafEnterpriseException(e);
		}
	}
	
	/**
	 * Convertie les JrafDAOException en JrafEnterpriseException
	 * @param e exception en entree
	 * @param nameMethod nom de la methode
	 * @throws JrafEnterpriseException
	 */
	public static void convertException(Exception e, String nameMethod)
		throws JrafEnterpriseException {

		// trace	
		log.error(
			"Probleme dans l'execution du composant '"
				+ nameMethod
				+ "'. Erreur : "
				+ e.getMessage(),
			e);

		throw new JrafEnterpriseException(e);
	}

}
