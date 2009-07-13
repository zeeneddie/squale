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
 * Créé le 9 mars 04
 */
package org.squale.jraf.provider.accessdelegate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafConfigException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.spi.accessdelegate.IAccessDelegateProvider;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.jraf.spi.initializer.IInitializableBean;
import org.squale.jraf.spi.persistence.IPersistenceProvider;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafProviderAccessdelegate
 * <p>Title : AccessDelegateProviderImpl.java</p>
 * <p>Description : Access delegate provider</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 */
public class AccessDelegateProviderImpl
	implements IAccessDelegateProvider, IInitializableBean {

	/** logger */
	private static final Log log =
		LogFactory.getLog(AccessDelegateProviderImpl.class);
	
	/** provider de persistance */
	private IPersistenceProvider persistenceProvider;

	/**
	 * Lookup component
	 */
	private ILookupComponent lookupComponent;

	/**
	 * Constructeur vide type IOC 2
	 */
	public AccessDelegateProviderImpl() {
		super();
	}

	/**
	 * Constructeur IOC type 3
	 * @param lookup lookup component
	 */
	public AccessDelegateProviderImpl(ILookupComponent lookup) {
		super();
		setLookupComponent(lookup);
		afterPropertiesSet();
	}

	/**
	 * Constructeur IOC type 3
	 * @param lookup lookup component
	 * @param persistanceProvider provider de persistance
	 */
	public AccessDelegateProviderImpl(
		ILookupComponent lookup,
		IPersistenceProvider persistanceProvider) {
		super();
		setLookupComponent(lookup);
		setPersistenceProvider(persistanceProvider);
		afterPropertiesSet();
	}

	/**
	 * Recupere une instance d'un application component a partir de son nom
	 * @param in_applicationComponentName nom de l'application component
	 */
	public IApplicationComponent getInstance(String in_applicationComponentName)
		throws JrafEnterpriseException {
		IApplicationComponent instanceAppComponent = null;
		DefaultCMTExecuteComponent cmtApplicationComponent = null;
		IInitializableBean ibean = null;

		try {
			instanceAppComponent =
				lookupComponent.findApplicationComponent(
					in_applicationComponentName);

			// on fixe le provider dans l'application component
			instanceAppComponent.setAccessDelegateProvider(this);

			// cas du CMTApplicationComponent
			if (instanceAppComponent instanceof DefaultCMTExecuteComponent) {
				cmtApplicationComponent =
					(DefaultCMTExecuteComponent) instanceAppComponent;

				// si le provider de persistance est 'null' on le renseigne 
				// en faisant appel PersistenceHelper.
				if (getPersistenceProvider() == null) {
					setPersistenceProvider(
						PersistenceHelper.getPersistenceProvider());
				}

				// on fixe le provider de persistance
				cmtApplicationComponent.setPersistenceProvider(
					getPersistenceProvider());

				// le composant est initiateur de transaction
				cmtApplicationComponent.setTransactionInitiator(true);

			}

			// on valide l'initialisation
			if (instanceAppComponent instanceof IInitializableBean) {
				ibean = (IInitializableBean) instanceAppComponent;
				ibean.afterPropertiesSet();

			}

		} catch (JrafEnterpriseException e) {
			throw new JrafEnterpriseException(
				"Echec de la construction du applicationComponentdelegate, problème d'implémentation de la classe du domaine de component :"
					+ in_applicationComponentName,
				e);
		}
		return instanceAppComponent;
	}

	/**
	 * Valide l'existance d'un service sur un application component
	 * @param in_applicationcomponent nom de l'application component
	 * @param in_nameService nom du service 
	 * @return true si le service existe, false sinon
	 */
	public boolean validateService(
		String in_applicationcomponent,
		String in_nameService) {
		return lookupComponent.validateService(
			in_applicationcomponent,
			in_nameService);
	}
	/**
	 * Retourne le lookup component
	 * @return lookup component
	 */
	public ILookupComponent getLookupComponent() {
		return lookupComponent;
	}

	/**
	 * Fixe le lookup component
	 * @param component lookup component
	 */
	public void setLookupComponent(ILookupComponent component) {
		lookupComponent = component;
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.spi.initializer.IInitializableBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {

		if (getLookupComponent() == null) {
			String message = "Pas de lookup component fourni.";
			log.fatal(message);
			throw new JrafConfigException("Pas de lookup component fourni.");

		}

	}

	/**
	 * Retourne le provider de persistance
	 * @return provider de persistance
	 */
	public IPersistenceProvider getPersistenceProvider() {

		return persistenceProvider;
	}

	/**
	 * Fixe le provider de persistance
	 * @param provider provider de persistance
	 */
	public void setPersistenceProvider(IPersistenceProvider provider) {
		persistenceProvider = provider;
	}

}
