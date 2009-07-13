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
 * Created on Apr 6, 2004
 */
package org.squale.jraf.helper;

import org.squale.jraf.bootstrap.initializer.Initializer;
import org.squale.jraf.bootstrap.test.TestInitializerHelper;
import org.squale.jraf.commons.exception.JrafConfigException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.jraf.spi.logging.ILogger;

/**
 * <p>Title : TestHelper.java</p>
 * <p>Description : Helper pour les tests. 
 * Permet d'initialiser le provider locator.</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * 
 */
public class TestHelper {

	/**
	 * Initialise un provider locator pour les tests unitaires.
	 * Les providers de logs et de bootstrap sont renseignés avec 
	 * des providers "bouchons".
	 */
	public final static void initMockProviderLocator() {

		// delegation sur le helper du provider de bootstrap
		TestInitializerHelper.initMockProviderLocator();

	}

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
		TestInitializerHelper.initFunctionalProviderLocator(
			in_rootContext,
			in_configFile,
			Initializer.class.getName());

	}

	/**
	 * Execute un component d'un ApplicationComponent
	 * @param in_applicationComponent nom de l'application component
	 * @param in_component nom du component
	 * @return resultat de l'execution
	 * @throws JrafEnterpriseException
	 */
	public static Object testComponent(
		final String in_applicationComponent,
		final String in_component)
		throws JrafEnterpriseException {
		return testComponent(in_applicationComponent, in_component, null);
	}

	/**
	 * Execute un component d'un ApplicationComponent
	 * @param in_applicationComponent nom de l'application component
	 * @param in_component nom du component
	 * @param in_args arguments
	 * @return resultat de l'execution
	 * @throws JrafEnterpriseException
	 */
	public static Object testComponent(
		final String in_applicationComponent,
		final String in_component,
		final Object[] in_args)
		throws JrafEnterpriseException {

		Object lc_return = null;

		IApplicationComponent id =
			AccessDelegateHelper.getInstance(in_applicationComponent);

		int limite = 1;

		ILogger log = LoggingHelper.getInstance(TestHelper.class);

		log.info(
			"Debut du Test : ApplicationComponent: "
				+ in_applicationComponent
				+ " component: "
				+ in_component);

		try {
			lc_return = id.execute(in_component, in_args);
			log.info(
				"Test effectue avec succes : ApplicationComponent: "
					+ in_applicationComponent
					+ " component: "
					+ in_component);

		} catch (JrafEnterpriseException e) {
			log.error(
				"Probleme lors de l'execution du test : ApplicationComponent: "
					+ in_applicationComponent
					+ " component: "
					+ in_component,
				e);
			throw e;
		}
		// on retourne le resultat d'execution
		return lc_return;
	}

}
