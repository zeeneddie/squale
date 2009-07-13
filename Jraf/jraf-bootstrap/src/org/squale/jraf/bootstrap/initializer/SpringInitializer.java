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
 * Created on Feb 20, 2004
 */
package org.squale.jraf.bootstrap.initializer;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

import org.squale.jraf.bootstrap.ApplicationContextFactoryInitializer;
import org.squale.jraf.bootstrap.config.ClassPathXmlProviderConfigReader;
import org.squale.jraf.bootstrap.config.FileSystemXmlProviderConfigReader;
import org.squale.jraf.bootstrap.config.ProviderConfig;
import org.squale.jraf.bootstrap.config.ProviderConfigComparator;
import org.squale.jraf.bootstrap.initializer.InitializableHelper;
import org.squale.jraf.bootstrap.locator.ProviderLocator;
import org.squale.jraf.bootstrap.test.ApplicationContextFactoryHelper;
import org.squale.jraf.commons.exception.JrafConfigException;
import org.squale.jraf.spi.bootstrap.IBootstrapConstants;
import org.squale.jraf.spi.bootstrap.IBootstrapProvider;
import org.squale.jraf.spi.initializer.IInitializable;
import org.squale.jraf.spi.initializer.IInitializableBean;
import org.squale.jraf.spi.provider.IProvider;
import org.squale.jraf.spi.provider.IProviderConstants;

/**
 * <p>Title : Initializer.java</p>
 * <p>Description : Initializer  d'une application JRAF a partir de SPRING.
 * Initialise tous les providers declarees dans le fichier de configuration 
 * des providers.</p>
 * <p>Copyright : Copyright (c) 2007</p>
 * 
 * @author Franck Bisti
 */
public class SpringInitializer
	extends Initializer
	implements IInitializable, IInitializableBean, ApplicationContextAware {

	/** logger */
	private static final Log log = LogFactory.getLog(SpringInitializer.class);

	/** Spring context */
	private ApplicationContext applicationContext = null;

	/**
	 * Constructeur vide type IOC2
	 */
	public SpringInitializer() {
		setRootPath(null);
	}

	/**
	 * Constructeur avec parametres type IOC3
	 * @param rootPath chemin racine
	 * @param configFile chemin relatif vers le fichier de configuration
	 */
	public SpringInitializer(String rootPath, String configFile) {
		this(rootPath, configFile, false);
	}

	/**
	 * Constructeur avec parametres type IOC3
	 * @param rootPath chemin racine 
	 * @param configFile chemin relatif vers le fichier de configuration
	 * @param isJndi enregistrement dans jndi
	 */
	public SpringInitializer(
		String rootPath,
		String configFile,
		boolean isJndi) {
		super(rootPath, configFile, isJndi);
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.spi.initializer.IInitializable#initialize()
	 */
	public IProvider initialize() throws JrafConfigException {

		// liste des providers
		List lc_providers = null;

		// debut de l'initialisation
		log.info("Debut de l'initialisation des providers JRAF...");

		// creation du locator
		ProviderLocator locator = null;
		locator = ProviderLocator.getInstance();
		// Test pour vérifier que le locateur est null.
		// Si oui, implique que l'exécution se fait 
		// dans un context hors serveur d'application.
		if (locator == null) {
			log.info("Exécution en mode standalone ... ");
			locator = new ProviderLocator(new HashMap());
		}

		// recuperation du chemin racine
		String lc_rootPath = getRootPath();

		// recuperation du fichier de configuration
		String lc_configFile = getConfigFile();
		if (lc_configFile != null) {
			lc_providers =
				providersInitialize(
					lc_providers,
					locator,
					lc_rootPath,
					lc_configFile);
		}

		// renseignement des providers dans le provider de bootstrap
		// si il existe
		IBootstrapProvider lc_bootstrapProvider =
			(IBootstrapProvider) locator.get(
				IProviderConstants.BOOTSTRAP_PROVIDER_KEY);

		String bootStrapJndi = null;
		if (lc_bootstrapProvider != null) {
			lc_bootstrapProvider.setProviders(lc_providers);

			// cas ancienne version des fichiers de configuration
			bootStrapJndi =
				(String) lc_bootstrapProvider.getParameters().get(
					IBootstrapConstants.JNDI_BIND);

			setBootstrapJndi(Boolean.valueOf(bootStrapJndi).booleanValue());

		}

		// bind jndi si necessaire
		if (isBootstrapJndi() || isJndi()) {

			log.info("Bind du provider locator dans JNDI...");
			locator.bind();

		} else {
			// on fixe le singleton	du provider locator	
			ProviderLocator.setProviderLocator(locator);
		}

		log.info("Reussite de l'intialisation des providers");
		return lc_bootstrapProvider;
	}

	private List providersInitialize(
		List lc_providers,
		ProviderLocator locator,
		String lc_rootPath,
		String lc_configFile) {
		String lc_fullPathConfigFile = null;

		try {
			if (lc_rootPath == null) {
				lc_fullPathConfigFile =
					getApplicationContext()
						.getResource(getConfigFile())
						.getURL()
						.getPath();
				lc_rootPath =
					lc_fullPathConfigFile.substring(
						0,
						lc_fullPathConfigFile.indexOf(lc_configFile));
				setRootPath(lc_rootPath);
			} else {
				lc_fullPathConfigFile =
					lc_rootPath + File.separator + lc_configFile;
			}
			log.info("Lecture du fichier de configuration des providers...");
			log.debug("fichier de config : " + lc_fullPathConfigFile);

			// lecture du fichier de configuration
			FileSystemXmlProviderConfigReader file =
				new FileSystemXmlProviderConfigReader(lc_fullPathConfigFile);
			lc_providers = file.readConfig();
		} catch (Exception exc) {
			exc.printStackTrace();
			throw new JrafConfigException(exc);
		}

		if (log.isDebugEnabled())
			log.debug(
				"Lecture du fichier de configuration des providers effectue.");

		// on trie la liste des providers
		Collections.sort(lc_providers, new ProviderConfigComparator());

		Iterator lc_i = lc_providers.iterator();

		// initialisation des probiders
		while (lc_i.hasNext()) {

			ProviderConfig lc_config = (ProviderConfig) lc_i.next();

			if (log.isDebugEnabled())
				log.debug(
					"Initialisation du provider " + lc_config.getId() + "...");

			// creation du provider
			IProvider lc_provider =
				createStandardProvider(lc_config, getRootPath());

			// on teste si le provider existe déjà dans le Locator
			String id = lc_config.getId();
			if (locator.get(id) == null) {
				locator.put(lc_config.getId(), lc_provider);

				if (log.isDebugEnabled())
					log.debug(
						"Initialisation du provider "
							+ lc_config.getId()
							+ " effectue.");
			}
		}

		log.info("Initialisation des providers optionnels effectue");

		return lc_providers;
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.spi.initializer.IInitializableBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {

		// controle du fichier de configuration
		if (getConfigFile() == null) {
			throw new JrafConfigException("Fichier de configuration non fourni.");
		}

	}

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext)
		throws BeansException {
		this.applicationContext = applicationContext;

	}

	private ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
