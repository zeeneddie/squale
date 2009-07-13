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
 * Created on Mar 5, 2004
 */
package org.squale.jraf.bootstrap.locator;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SpringLayout;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.bootstrap.naming.IJndiBinder;
import org.squale.jraf.bootstrap.naming.JndiBinder;
import org.squale.jraf.commons.exception.JrafConfigException;
import org.squale.jraf.spi.initializer.IInitializableBean;
import org.squale.jraf.spi.provider.IProvider;

/**
 * <p>Title : ProviderLocator.java</p>
 * <p>Description : permet de retrouver les provider d'une application</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 * @author Eric BELLARD
 */
public class ProviderLocator
	implements Serializable, IInitializableBean, ILocator {

	/** logger */
	private final static Log log = LogFactory.getLog(ProviderLocator.class);

	/////// proprietes statiques 
	/** provider locator */
	private static ProviderLocator _providerLocator;

	/** map des providers */
	private Map providers;

	/**
	 * Constructeur vide type IOC2
	 */
	public ProviderLocator() {
		super();
	}

	/**
	 * Constructeur avec parametres type IOC3.
	 * La map de provider est fourni en parametres
	 * @param providers map de parametres
	 */
	public ProviderLocator(Map providers) {
		setProviders(providers);
		afterPropertiesSet();

	}

	/**
	 * Recupere un singleton de provider locator
	 * @return singleton de provider locator
	 * @throws JrafConfigException
	 */
	public static ProviderLocator getInstance() {
		ProviderLocator loc = _providerLocator;
		if (_providerLocator == null) {
			loc = lookup();
		}
		// on retourne le provider locator
		return loc;
	}

	/**
	 * Recupere le provider locator dans JNDI
	 * @param in_parameters parametres jndi
	 * @return provider locator
	 * @throws JrafConfigException
	 */
	private static ProviderLocator lookup() throws JrafConfigException {
		ProviderLocator locator = null;

		// url jndi
		String jndiUrl = null;

		// jndi class
		String jndiClass = null;

		// nom jndi du locator
		String locatorJndiName = null;

		if (log.isDebugEnabled()) {
			log.debug("Recuperation du contexte JNDI...");
		}

		// binder local	
		IJndiBinder binderLocal = new JndiBinder(null);

		// recuperation des parametres dans l'annuaire jndi local
		jndiUrl =
			(String) binderLocal.lookup(IProviderLocatorConstants.JNDI_URL_KEY);
		jndiClass =
			(String) binderLocal.lookup(
				IProviderLocatorConstants.JNDI_CLASSNAME_KEY);
		locatorJndiName =
			(String) binderLocal.lookup(
				IProviderLocatorConstants.PROVIDER_LOCATOR_NAME_KEY);

		// trace
		if (log.isDebugEnabled()) {
			log.debug("URL JNDI de l'annuaire JNDI=" + jndiUrl);
			log.debug("Classe d'acces JNDI=" + jndiClass);
			log.debug("Nom JNDI du locator=" + locatorJndiName);
		}
		// creation d'un binder JNDI distant
		IJndiBinder remoteBinder = new JndiBinder(jndiClass, jndiUrl);

		// binder jndi	
		locator = (ProviderLocator) remoteBinder.lookup(locatorJndiName);
		// on retourne le provider locator
		return locator;
	}

	/**
	 * Recupere un provider a partir de son nom
	 * @param in_name nom du provider
	 * @return provider
	 */
	public static IProvider getProvider(String in_name) {
		ProviderLocator loc = getInstance();
		if (loc == null) {
			log.debug("Pas de provider locator - test spring locator");
			SpringLocator springLocator = SpringLocator.getInstance();
			if(springLocator == null) {			
				throw new JrafConfigException("L'application n'a pas ete correctement initialise, le provider locator n'est pas present dans JNDI");
			}
			return (IProvider) springLocator.getBean(in_name);
		} else {
			return (IProvider) loc.getProviders().get(in_name);
		}
	}
	/**
	 * Retourne les providers
	 * @return providers
	 */
	public Map getProviders() {
		return providers;
	}

	/**
	 * Ajoute un provider sur le provider locator
	 * @param in_id id du provider
	 * @param in_provider provider
	 * @deprecated utliser la methode put en remplacement
	 */
	public void putProvider(String in_id, IProvider in_provider) {
		put(in_id, in_provider);
	}

	/**
	 * Supprime un provider du provider locator
	 * @param in_id id du provider
	 * @deprecated utliser la methode remove en remplacement
	 */
	public void removeProvider(String in_id) {
		remove(in_id);
	}

	/**
	 * Fixe le provider locator.
	 * Exécuté une seule fois à l'initialisation
	 * @param in_providerLocator provider locator
	 */
	public static void setProviderLocator(ProviderLocator in_providerLocator) {
		_providerLocator = in_providerLocator;
	}

	/**
	 * Rend le provider locator non modifiable.
	 */
	public void setReadOnly() {
		Map lc_providers = providers;
		lc_providers = Collections.unmodifiableMap(lc_providers);
		providers = lc_providers;
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.bootstrap.locator.IProviderLocator#get(java.lang.String)
	 */
	public IProvider get(String name) {
		return (IProvider) getProviders().get(name);
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.bootstrap.locator.IProviderLocator#put(java.lang.String, org.squale.jraf.spi.provider.IProvider)
	 */
	public void put(String name, IProvider provider) {
		getProviders().put(name, provider);
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.bootstrap.locator.IProviderLocator#remove(java.lang.String)
	 */
	public void remove(String name) {
		providers.remove(name);

	}

	/**
	 * Fixe la map de providers
	 * @param map map de providers
	 */
	public void setProviders(Map map) {
		providers = map;
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.spi.initializer.IInitializableBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		// initilize la map de providers au besoin
		if (getProviders() == null) {
			setProviders(new HashMap());
		}
	}

	/**
	 * Bind du locator dans JNDI
	 */
	public void bind() {
		log.info("Bind du locator dans JNDI...");

		// url jndi
		String jndiUrl = null;

		// jndi class
		String jndiClass = null;

		// nom jndi du locator
		String locatorJndiName = null;

		if (log.isDebugEnabled()) {
			log.debug("Recuperation du contexte JNDI...");
		}

		// binder local	
		IJndiBinder binderLocal = new JndiBinder(null);

		jndiUrl =
			(String) binderLocal.lookup(IProviderLocatorConstants.JNDI_URL_KEY);
		jndiClass =
			(String) binderLocal.lookup(
				IProviderLocatorConstants.JNDI_CLASSNAME_KEY);
		locatorJndiName =
			(String) binderLocal.lookup(
				IProviderLocatorConstants.PROVIDER_LOCATOR_NAME_KEY);

		// trace
		if (log.isDebugEnabled()) {
			log.debug("URL JNDI de l'annuaire JNDI=" + jndiUrl);
			log.debug("Classe d'acces JNDI=" + jndiClass);
			log.debug("Nom JNDI du locator=" + locatorJndiName);
		}

		// creation d'un binder JNDI distant
		IJndiBinder remoteBinder = new JndiBinder(jndiClass, jndiUrl);

		// rend le provider locator non modifiable
		setReadOnly();

		if (locatorJndiName != null) {
			// bind le provider locator dans jndi
			if (log.isDebugEnabled()) {
				log.debug("Bind dans JNDI du locator...");
			}
			remoteBinder.bind(locatorJndiName, this);
			log.info("Bind dans JNDI du locator effectue.");

		} else {
			log.info("Le nom JNDI n'a pas ete specifie. Bind JNDI annule.");
		}
	}
}
