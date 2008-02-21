/*
 * Created on Sep 15, 2004
 */
package com.airfrance.jraf.provider.persistence.hibernate.facade;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.commons.exception.JrafPersistenceException;
import com.airfrance.jraf.spi.persistence.ISession;

/**
 * <p>Title : FacadeHelper.java</p>
 * <p>Description : Classe d'assistance pour les Facade.</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
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
