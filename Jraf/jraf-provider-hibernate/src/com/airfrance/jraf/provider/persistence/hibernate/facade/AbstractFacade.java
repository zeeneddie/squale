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
package com.airfrance.jraf.provider.persistence.hibernate.facade;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.commons.exception.JrafPersistenceException;
import com.airfrance.jraf.helper.LoggingHelper;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.spi.enterpriselayer.IFacade;
import com.airfrance.jraf.spi.logging.ILogger;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafProviderPersistence
 * <p>Title : AbstractFacade.java</p>
 * <p>Description : Fourni les fonctions de bases pour l'ouverture, le logging et la fermeture
 * des business components.</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 * @deprecated depuis JRAF v2.0
 * Cette classe est depréciee. Elle n'est pas remplacee.
 * Pour les methodes logFacade : utiliser directement le provider de logging 
 * pour la gestion des traces et effectuer manuellement la gestion des exceptions.
 * Pour l'ouverture de session de persistance : utiliser directement 
 * le provider de persistance.
 * Pour la fermeture de session de persistance : utiliser la methode closeSession de 
 * la classe d'assistance FacadeHelper. 
 * 
 */
public abstract class AbstractFacade implements IFacade {

	/** logger */
	private static final ILogger log =
		LoggingHelper.getInstance(AbstractFacade.class);

	/** provider de persistance */
	private static final IPersistenceProvider persistentProvider =
		PersistenceHelper.getPersistenceProvider();

	/**
	 * Ferme la session de persistance
	 * Gere les trace et les exceptions
	 * @param idTransaction id de la transaction
	 * @param session session de persistance
	 * @param nameMethod nom de la methode
	 * @throws JrafEnterpriseException
	 * @deprecated depuis JRAF v2.0
	 * L'id transaction n'existe plus en JRAFv2.
	 * Utiliser la methode closeSession de la classe d'assistance FacadeHelper.
	 */
	public static void closeSession(
		Long idTransaction,
		ISession session,
		String nameMethod)
		throws JrafEnterpriseException {
		try {
			if (session != null) {
				// on ferme toujours la session en tenant compte d'une éventuelle transaction
				session.closeSession();
			}
		} catch (Exception e2) {
			logFacade(log, e2, nameMethod);
		}
	}

	/**
	 * Ferme la session de persistance
	 * Gere les trace et les exceptions
	 * @param session session de persistance
	 * @param nameMethod nom de la methode
	 * @throws JrafEnterpriseException
	 * @deprecated depuis JRAF v2.0
	 * Utiliser la methode closeSession de la classe d'assistance FacadeHelper.
	 */
	public static void closeSession(ISession session, String nameMethod)
		throws JrafEnterpriseException {
		try {
			if (session != null) {
				// on ferme toujours la session en tenant compte d'une éventuelle transaction
				session.closeSession();
			}
		} catch (Exception e2) {
			logFacade(log, e2, nameMethod);
		}
	}

	/**
	 * Si le mode debug est activé, affiche la pile d'exception, ainsi
	 * qu'une trace de niveau FACADELAYER décrivant le problème sur le composant.
	 * Génère également une exeception JrafEnterpriseException.
	 * @param log
	 * @param e
	 * @param nameMethod
	 * @throws JrafEnterpriseException
	 */
	public static void logFacade(ILogger log, Exception e, String nameMethod)
		throws JrafEnterpriseException {
		if (log.isDebugEnabled()) {
			e.printStackTrace();
			log.error(
				"Problème dans l'éxécution du component '"
					+ nameMethod
					+ "' dans la facade. Erreur : "
					+ e);
		}
		throw new JrafEnterpriseException(e);
	}

	/**
	 * Gere les traces applicatives
	 * Gere les exceptions: convertie l'exception en entree en EnterpriseException. 
	 * @param e exception en entree
	 * @param nameMethod nom de la methode
	 * @throws JrafEnterpriseException
	 * @deprecated depuis JRAF v2.0
	 * Utiliser directement le provider de log pour gere les traces.
	 * Gerer manuellement les exceptions.
	 */
	public static void logFacade(Exception e, String nameMethod)
		throws JrafEnterpriseException {
		if (log.isDebugEnabled()) {
			e.printStackTrace();
			log.error(
				"Problème dans l'éxécution du component '"
					+ nameMethod
					+ "' dans la facade. Erreur : "
					+ e);
		}
		throw new JrafEnterpriseException(e);
	}

	/**
	 * Recupère une session Jraf auprès du SessionManager.
	 * @param IdTransaction
	 * @return une session ISession
	 * @throws JrafEnterpriseException
	 * @deprecated depuis JRAF v2.0
	 * Recuperer directement la session a partir du provider de persistance.
	 */
	public static ISession getSession(Long idTransaction)
		throws JrafEnterpriseException {
		try {
			return persistentProvider.getSession();
		} catch (JrafPersistenceException e) {
			logFacade(log, e, "getSession");
			return null;
		}

	}

	/**
	 * Recupère une session Jraf auprès du Provider de persistance.
	 * @return une session ISession
	 * @throws JrafEnterpriseException
	 * @deprecated depuis JRAF v2.0
	 * Recuperer directement la session a partir du provider de persistance.
	 */
	public static ISession getSession() throws JrafEnterpriseException {
		try {
			IPersistenceProvider ipp =
				PersistenceHelper.getPersistenceProvider();
			return persistentProvider.getSession();
		} catch (Exception e) {
			logFacade(log, e, "getSession");
			return null;
		}
	}
}
