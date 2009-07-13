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
package org.squale.jraf.initializer.web;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.context.ApplicationContext;
import org.squale.jraf.bootstrap.ApplicationContextFactoryInitializer;
import org.squale.jraf.bootstrap.initializer.InitializableHelper;
import org.squale.jraf.commons.exception.JrafConfigException;
import org.squale.jraf.spi.bootstrap.IBootstrapConstants;
import org.squale.jraf.spi.bootstrap.IBootstrapProvider;
import org.squale.jraf.spi.initializer.IInitializable;
import org.squale.jraf.bootstrap.initializer.Initializer;

/**
 * <p>Title : WebInitializer.java</p>
 * <p>Description : Listener pour l'initialisation 
 * d'une application JRAF WEB et/ou EJB</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 * @author Eric BELLARD
 * Modficiation : Mars - 2006 
 * 				: Avril - 2006
 * @author DIAKITE Tidiani
 */
public class SpringWebInitializer implements ServletContextListener {
	/**Application context.*/
	private ApplicationContext app_context = null;
	/**L'initializer. */
	private Initializer initializer = null;
	/**Récupérateur des paramètres. */
	private Map lc_paramMap = null;
	/**Interface initializable. */
	private IInitializable lc_initialize = null;
	
	/**
	* @see javax.servlet.ServletContextListener#void 
	* (javax.servlet.ServletContextEvent)
	*/
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	/**
	* @see javax.servlet.ServletContextListener#void 
	* (javax.servlet.ServletContextEvent)
	*/
	public void contextInitialized(ServletContextEvent in_servletContextEvent) {
		
		//	IInitializable lc_initialize = null;
		// map des parametres
		Map lc_paramMap = null;
		// context
		ServletContext lc_context = in_servletContextEvent.getServletContext();
		lc_context.log("Mode Web entree dans la classe WebInitializer ");
		// repertoire racine
		String lc_rootPath = lc_context.getRealPath("");
		lc_context.log("Root path : "+lc_rootPath); 
		try {			
			lc_paramMap = new HashMap();
		// Initialisation du fichier de configuration spring.
		lc_context.log("Initialisation du fichier de configuration spring ... ");
		// Permet de récupérer le fichier de configuration xml spring stocké dans 
		// le répertoire de WEB-INF/config d'une application web.
		// Exemple : cas COMOR = 
		ApplicationContextFactoryInitializer.init(
				lc_context.getInitParameter(
				IBootstrapConstants .SPRING_CONTEXT_CONFIG));
				// Recupération du Bean initializer.
			lc_context.log("INIT Ok - Recupération du Bean initializer ... ");
			// Lecture du fichier .			
			ApplicationContext ctx = ApplicationContextFactoryInitializer.
			getApplicationContext();
			// Récupération du bean.
			initializer =(Initializer)ctx.getBean("initialize");
			lc_context.log(
				"Provider config file = " + initializer.getConfigFile());
			lc_context.log("Debut de l'initialisation de jraf ...");
			lc_initialize =
				InitializableHelper.instanciateInitializable(
					initializer.getClass().getName());
			lc_paramMap.put(IBootstrapConstants.ROOT_PATH_KEY, lc_rootPath);
			lc_paramMap.put(
				IBootstrapConstants.PROVIDER_CONFIG_KEY,
				initializer.getConfigFile());
			IBootstrapProvider lc_bootstrapProvider =
				(IBootstrapProvider) lc_initialize.initialize(lc_paramMap);
			// Ajout jndi.bind
			boolean jndi = initializer.isJndi();
			
		//	lc_paramMap.put(IBootstrapConstants.JNDI_BIND, initializer.isJndi());
			lc_context.log("INITILISATION DU CONTEXT JRAF EFFECTUEE AVEC SUCCES .");
			lc_context.log(
				"LES PROVIDERS SUIVANTS ONT ETE INITIALISES : "
					+ lc_bootstrapProvider.getProviders());
		} catch (JrafConfigException e) {
			// log
			lc_context.log(
				"Probleme lors de l'initialisation de l'application : ",
				e);
		} catch (RuntimeException e) {
			lc_context.log("Probleme lors de l'initialisation de l'application : ",e);
		} catch (Exception e) {
			lc_context.log("Probleme lors de l'intialisation de l'application : ",e);
		}
	}
}
