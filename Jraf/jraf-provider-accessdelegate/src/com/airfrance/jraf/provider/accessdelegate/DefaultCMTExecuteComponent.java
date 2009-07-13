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
package org.squale.jraf.provider.accessdelegate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafConfigException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.commons.exception.JrafPersistenceException;
import org.squale.jraf.spi.accessdelegate.IAccessDelegateProvider;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.jraf.spi.accessdelegate.ICMTApplicationComponent;
import org.squale.jraf.spi.initializer.IInitializableBean;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafProviderAccessdelegate
 * <p>Title : DefaultCMTExecuteComponent.java</p>
 * <p>Description : Application component permettant l'execution de tout composant 
 * au sein d'une transaction. Il gere lui meme l'ouverture et la fermeture de transaction.
 * Il inclus un autre CMTApplicationComponent dans son contexte transactionnel.
 * Les classes utilisateurs de type 'CMTApplicationComponent' heritent de cette classe.</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 * implémentation standard d'appel d'un component contenu dans un 
 * applicationComponent. 
 * <br>exemple:<br>
 * <b>IApplicationComponent id = ApplicationComponentDelegate.getInstance("Crud");<br>
 * nbInstances = (Integer) id.execute("countAvion");</b><br>
 */
public class DefaultCMTExecuteComponent
	implements ICMTApplicationComponent, IInitializableBean {

	/** logger */
	private static final Log log =
		LogFactory.getLog(DefaultCMTExecuteComponent.class);

	/** nom de l'application component */
	private String applicationComponent = null;

	/** provider access delegate */
	private IAccessDelegateProvider accessDelegateProvider = null;

	/** provider de persistance */
	private IPersistenceProvider persistenceProvider = null;

	/** session de persistance */
	private ISession session = null;

	/** initiative de la transaction */
	private boolean transactionInitiator;

	/**
	 * Constructeur type vide IOC 2
	 *
	 */
	public DefaultCMTExecuteComponent() {
		super();
	}

	/**
	 * Constructeur avec parametres type IOC3
	 * @param name nom de l'application component
	 * @param adp access delegate provider
	 * @param pp persistence provider
	 * @param isTransactionInitiator transaction initiator
	 */
	public DefaultCMTExecuteComponent(
		String name,
		IAccessDelegateProvider adp,
		IPersistenceProvider pp,
		boolean isTransactionInitiator) {

		super();
		setApplicationComponent(name);
		setAccessDelegateProvider(adp);
		setPersistenceProvider(pp);
		setTransactionInitiator(isTransactionInitiator);
		afterPropertiesSet();
	}

	/**
	 * @see org.squale.jraf.enterpriselayer.itface.IApplicationComponent#execute(java.lang.String)
	 */
	public Object execute(String method) throws JrafEnterpriseException {
		return execute(method, null);
	}

	/** 
	 * @see org.squale.jraf.enterpriselayer.itface.IApplicationComponent#execute(java.lang.String, java.lang.Object[])
	 * */
	public Object execute(String method, Object[] parameter)
		throws JrafEnterpriseException {

		// declaration de la valeur retourne
		Object lc_returnedValue = null;

		try {

			// initialisation de l'etat transactionnel
			if (isTransactionInitiator()) {

				// si pas de session on recupere une session
				if (session == null || !session.isOpen()) {
					session = getPersistenceProvider().getSession();

				}

				if (log.isDebugEnabled()) {
					log.debug("Begin transaction...");
				}
				session.beginTransaction();
			}

			// delegation de l'appel de methode sur le helper
			lc_returnedValue = ExecuteHelper.execute(this, method, parameter);

			if (isTransactionInitiator()) {
				if (log.isDebugEnabled()) {
					log.debug("Commit transaction...");
				}
				session.commitTransaction();
			}

		} catch (JrafPersistenceException e) {
			if (isTransactionInitiator()) {

				if (log.isDebugEnabled()) {
					log.debug("Rollback transaction...");
				}
				session.rollbackTransaction();
			}
			log.error("Probleme lors de la transaction", e);
			throw new JrafEnterpriseException(
				"Probleme lors de la transaction",
				e);
		} catch (JrafEnterpriseException e) {
			if (isTransactionInitiator()) {
				if (log.isDebugEnabled()) {
					log.debug("Rollback transaction...");
				}

				session.rollbackTransaction();
			}
			throw e;
		}

		// on retourne le resultat de la delegation
		return lc_returnedValue;

	}

	/**
	 * @see org.squale.jraf.enterpriselayer.itface.IApplicationComponent#getApplicationComponent()
	 */
	public String getApplicationComponent() {
		return applicationComponent;
	}

	/**
	 * @see org.squale.jraf.enterpriselayer.itface.IApplicationComponent#setApplicationComponent(String)
	 */
	public void setApplicationComponent(String applicationComponent) {
		this.applicationComponent = applicationComponent;
	}
	/**
	 * Retourne le provider access delegate
	 * @return
	 */
	public IAccessDelegateProvider getAccessDelegateProvider() {
		return accessDelegateProvider;
	}

	/**
	 * Fixe le provider access delegate
	 * @param provider
	 */
	public void setAccessDelegateProvider(IAccessDelegateProvider provider) {
		accessDelegateProvider = provider;
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.spi.accessdelegate.ICMTApplicationComponent#getCompositeApplicationComponent(java.lang.String)
	 */
	public IApplicationComponent getCompositeApplicationComponent(String in_name)
		throws JrafEnterpriseException {

		IApplicationComponent applicationComponent =
			getAccessDelegateProvider().getInstance(in_name);
		DefaultCMTExecuteComponent cmtApplicationComponent = null;

		// application componenet non null
		if (applicationComponent != null) {

			// cas d'un CMT application component, on transmet la session
			// pour l'inclure dans la transaction courante	
			if (applicationComponent instanceof DefaultCMTExecuteComponent) {
				cmtApplicationComponent =
					(DefaultCMTExecuteComponent) applicationComponent;
				cmtApplicationComponent.setSession(getSession());
				cmtApplicationComponent.setTransactionInitiator(false);
			}
						
			if (applicationComponent instanceof IInitializableBean) {
				((IInitializableBean)applicationComponent).afterPropertiesSet();
			}

		}

		// retourne l'application component
		return applicationComponent;
	}

	/**
	 * Retourne le provider de persistance
	 * @return provider de persitance
	 */
	public IPersistenceProvider getPersistenceProvider() {
		return persistenceProvider;
	}

	/**
	 * Fixe le provider de persistance
	 * @param provider fixe le provider de persistance
	 */
	public void setPersistenceProvider(IPersistenceProvider provider) {
		persistenceProvider = provider;
	}

	/**
	 * Fixe une session de persistance
	 * @param session session de persistance
	 */
	private void setSession(ISession session) {
		this.session = session;
	}

	/**
	 * Retourne la session de la transaction courante
	 * @return session de la transaction courante
	 */
	public ISession getSession() {
		return session;
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.spi.accessdelegate.ICMTApplicationComponent#isTransactionInitiator()
	 */
	public boolean isTransactionInitiator() {
		return transactionInitiator;
	}

	/**
	 * Fixe si l'application component est à l'initiative de la transaction
	 * @param b
	 */
	public void setTransactionInitiator(boolean b) {
		transactionInitiator = b;
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.spi.initializer.IInitializableBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {

		if (getAccessDelegateProvider() == null) {
			throw new JrafConfigException("Le provider access delegate n'est pas renseigne.");
		}

		if (getApplicationComponent() == null) {
			throw new JrafConfigException("Le nom de l'application component n'est pas renseigne.");
		}

		if (getPersistenceProvider() == null) {
			throw new JrafConfigException("Le provider de persistance n'est pas renseigne.");
		}

	}

}
