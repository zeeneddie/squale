package com.airfrance.jraf.initializer.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.airfrance.jraf.bootstrap.initializer.InitializableHelper;
import com.airfrance.jraf.commons.exception.JrafConfigException;
import com.airfrance.jraf.spi.bootstrap.IBootstrapConstants;
import com.airfrance.jraf.spi.bootstrap.IBootstrapProvider;
import com.airfrance.jraf.spi.initializer.IInitializable;

/**
 * <p>Title : WebInitializer.java</p>
 * <p>Description : Listener pour l'initialisation 
 * d'une application JRAF WEB et/ou EJB</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 * @author Eric BELLARD
 */
public class WebInitializer implements ServletContextListener {

	/**
	* @see javax.servlet.ServletContextListener#void (javax.servlet.ServletContextEvent)
	*/
	public void contextDestroyed(ServletContextEvent arg0) {
		// nothing to do
	}

	/**
	* @see javax.servlet.ServletContextListener#void (javax.servlet.ServletContextEvent)
	*/
	public void contextInitialized(ServletContextEvent in_servletContextEvent) {

		// initializer
		IInitializable lc_initialize = null;

		// map des parametres
		Map lc_paramMap = null;

		// context
		ServletContext lc_context = in_servletContextEvent.getServletContext();

		// log
		lc_context.log("Debut de l'initialisation de l'application Jraf...");

		// repertoire racine
		String lc_rootPath = lc_context.getRealPath("");

		// classe d'initialisation
		String lc_initClassName =
			lc_context.getInitParameter(IBootstrapConstants.INITIALIZE_KEY);

		// bind jndi
		String lc_isJndi =
			lc_context.getInitParameter(IBootstrapConstants.JNDI_BIND);

		// fichier de configuration d'un provider
		String lc_providerConfigFile =
			lc_context.getInitParameter(
				IBootstrapConstants.PROVIDER_CONFIG_KEY);

		try {

			// test des parametres recuperees
			if (lc_rootPath == null
				|| lc_rootPath.equals("")
				|| lc_providerConfigFile == null
				|| lc_providerConfigFile.equals("")) {

				// affiche une erreur de configuration
				String lc_error =
					"Pb de configuration : le chemin du contexte racine est vide ou le fichier de configuration des plugins n'est pas specifie.";

				lc_context.log(lc_error);

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

				if (lc_isJndi != null) {
					lc_paramMap.put(IBootstrapConstants.JNDI_BIND, lc_isJndi);
				}

				// execution de la methode d'initialisation
				IBootstrapProvider lc_bootstrapProvider =
					(IBootstrapProvider) lc_initialize.initialize(lc_paramMap);

				// log de succes
				lc_context.log("L'application s'est initialisee avec succes");
				lc_context.log(
					"Les providers suivants ont ete initialises:"
						+ lc_bootstrapProvider.getProviders());

			}
		} catch (JrafConfigException e) {
			// log
			lc_context.log(
				"Probleme lors de l'initialisation de l'application : ",
				e);
		} catch (RuntimeException e) {
			// log
			lc_context.log(
				"Probleme lors de l'intialisation de l'application : ",
				e);

		}
	}

}
