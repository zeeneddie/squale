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
 * Created on Apr 9, 2004
 */
package com.airfrance.jraf.bootstrap.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.bootstrap.initializer.InitializableHelper;
import com.airfrance.jraf.bootstrap.initializer.Initializer;
import com.airfrance.jraf.bootstrap.locator.ProviderLocator;
import com.airfrance.jraf.bootstrap.test.log.LoggingMockProvider;
import com.airfrance.jraf.commons.exception.JrafConfigException;
import com.airfrance.jraf.spi.bootstrap.IBootstrapConstants;
import com.airfrance.jraf.spi.provider.IProvider;

/**
 * <p>Title : TestInitializerHelper.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public class TestInitializerHelper {
	private static final Log log =
		LogFactory.getLog(TestInitializerHelper.class);

	/**
	 * Initialise un provider locator pour les tests fonctionnels.
	 * Utilise la classe d'initialisation du bootstrap pour l'initialisation.
	 * @param in_rootContext chemin absolu du contexte 
	 * @param in_configFile chemin relatif du fichier de configuration
	 * par rapport au contexte
	 * @throws JrafConfigException
	 */
	public final static void initFunctionalProviderLocator(
		String in_rootContext,
		String in_configFile)
		throws JrafConfigException {

		// delegue le traitement
		initFunctionalProviderLocator(
			in_rootContext,
			in_configFile,
			Initializer.class.getName());

	}

	/**
	 * Initialise un provider locator pour les tests fonctionnels.
	 * @param in_rootContext chemin absolu du contexte 
	 * @param in_configFile chemin relatif du fichier de configuration
	 * @param in_initializationClass nom complet de la classe d'initialisation
	 * @throws JrafConfigException
	 */
	public final static void initFunctionalProviderLocator(
		String in_rootContext,
		String in_configFile,
		String in_initializationClass)
		throws JrafConfigException {
		log.debug("in_rootContext : " + in_rootContext);
		log.debug("in_configFile : " + in_configFile);

		IProvider lc_provider = null;
		Initializer lc_initializer = null;

		// construction des parametres
		Map lc_parameters = new HashMap();

		lc_parameters.put(IBootstrapConstants.ROOT_PATH_KEY, in_rootContext);
		lc_parameters.put(
			IBootstrapConstants.PROVIDER_CONFIG_KEY,
			in_configFile);

		lc_parameters.put(
			IBootstrapConstants.JNDI_BIND,
			Boolean.FALSE.toString());

		// instanciation de la classe d'initialisation
		lc_initializer =
			(Initializer) InitializableHelper.instanciateInitializable(
				in_initializationClass);

		// initialisation des providers
		lc_provider = lc_initializer.initialize(lc_parameters);
	}

	/**
	 * Initialise un provider locator pour les tests unitaires.
	 * Les providers de logs et de bootstrap sont renseignés avec 
	 * des providers "bouchons".
	 */
	public final static void initMockProviderLocator() {

		ProviderLocator lc_pl = new ProviderLocator(new HashMap());
		lc_pl.put("bootstrap", new BootstrapMockProvider());
		lc_pl.put("logging", new LoggingMockProvider());
		ProviderLocator.setProviderLocator(lc_pl);
	}

}
