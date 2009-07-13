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
 * Created on Sep 16, 2004
 */
package org.squale.jraf.initializer.struts;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import org.squale.jraf.bootstrap.ApplicationContextFactoryInitializer;
import org.squale.jraf.bootstrap.initializer.InitializableHelper;
import org.squale.jraf.bootstrap.initializer.Initializer;
import org.squale.jraf.commons.exception.JrafConfigException;
import org.squale.jraf.spi.bootstrap.IBootstrapConstants;
import org.squale.jraf.spi.bootstrap.IBootstrapProvider;
import org.squale.jraf.spi.initializer.IInitializable;
import org.springframework.context.ApplicationContext;


/**
 * <p>Title : StrutsInitializer.java</p>
 * <p>Description : Initialisateur d'une application JRAF
 * sous la forme d'un plugin struts.
 * Cette classe est utile pour initialiser JRAF dans des applications
 * deployees sur des plate-formes WAS4 (J2EE 1.2).
 * Si le niveau J2EE est >= 1.3 il est preconise d'utiliser la classe 
 * org.squale.jraf.initializer.web.WebInitializer.</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 * MODIFICATION : Mai 2006
 * @author DIAKITE Tidiani
 * <p>Copyright : Copyright (c) 2006</p>
 */
public class StrutsInitializer implements PlugIn {
	/** logger */
	private static final Log log = LogFactory.getLog(StrutsInitializer.class);
	private Initializer initializer = null;
	private Map lc_Map = null;
	
	
	/* (non-Javadoc)
	 * @see org.apache.struts.action.PlugIn#destroy()
	 */
	public void destroy() {
		// rien n'est effectue
	}

	/* (non-Javadoc)
	 * @see org.apache.struts.action.PlugIn#init(org.apache.struts.action.ActionServlet, org.apache.struts.config.ModuleConfig)
	 */
	public void init(
		ActionServlet in_actionServlet,
		ModuleConfig in_moduleConfig)
		throws ServletException {

		// initializer
		IInitializable lc_initialize = null;

		// map des parametres
		Map lc_paramMap = null;

		// context
		ServletContext lc_context = in_actionServlet.getServletContext();
		ServletConfig lc_config = in_actionServlet.getServletConfig();
		// log
		log.info("Debut de l'initialisation de l'application Jraf...");

		// repertoire racine
		String lc_rootPath = lc_context.getRealPath("");
		
		// Initialisation
		lc_context.log("INIT Ok - Recupération du Bean initializer ... ");
		ApplicationContextFactoryInitializer.init(
						lc_context.getInitParameter(
						IBootstrapConstants.SPRING_CONTEXT_CONFIG));
		// Récupération du bean initialize.			
		initializer =(Initializer)ApplicationContextFactoryInitializer.
						getApplicationContext().getBean("initialize");
			lc_context.log("Provider config file = " + initializer.getConfigFile());			
		// classe d'initialisation
		String lc_initClassName = initializer.getClass().getName();
	
		// fichier de configuration d'un provider
		String lc_providerConfigFile = initializer.getConfigFile();
	
		// bind jndi
		String lc_isJndi =Boolean.toString(initializer.isJndi());
		
		try {
			// test des parametres recuperees
			if (lc_rootPath == null
				|| lc_rootPath.equals("")
				|| lc_providerConfigFile == null
				|| lc_providerConfigFile.equals("")) {

				// affiche une erreur de configuration
				String lc_error =
					"Pb de configuration : le chemin du contexte racine est vide ou le fichier de configuration des plugins n'est pas specifie.";
				log.fatal(lc_error);
				throw new JrafConfigException(lc_error);

			} else {
				// les parametres ont bien ete recuperes
				// creation de l'initializer
				lc_initialize =
					InitializableHelper.instanciateInitializable(
						lc_initClassName);

				// recuperation/creation des parametres
				lc_paramMap = new HashMap();
				lc_paramMap.put(IBootstrapConstants.ROOT_PATH_KEY, lc_rootPath);

				lc_paramMap.put(
					IBootstrapConstants.PROVIDER_CONFIG_KEY,
					lc_providerConfigFile);

				// si le bind jndi est positionne
				if (lc_isJndi != null) {
					lc_paramMap.put(IBootstrapConstants.JNDI_BIND, lc_isJndi);
				}

				// execution de la methode d'initialisation
				IBootstrapProvider lc_bootstrapProvider =
					(IBootstrapProvider) lc_initialize.initialize(lc_paramMap);

				// log de succes
				log.info("L'application s'est initialisee avec succes");
				log.info(
					"Les providers suivants ont ete initialises:"
						+ lc_bootstrapProvider.getProviders());
			}
		} catch (JrafConfigException e) {
			// log
			log.fatal(
				"Probleme lors de l'intialisation de l'application : ",
				e);
			throw new ServletException(
				"Probleme lors de l'intialisation de l'application : ",
				e);
		} catch (RuntimeException e) {
			// log
			log.fatal(
				"Probleme lors de l'intialisation de l'application : ",
				e);
			throw new ServletException(
				"Probleme lors de l'intialisation de l'application : ",
				e);
		}
	}
}
