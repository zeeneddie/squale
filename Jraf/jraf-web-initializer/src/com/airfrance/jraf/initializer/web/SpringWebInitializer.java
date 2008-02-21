package com.airfrance.jraf.initializer.web;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.context.ApplicationContext;
import com.airfrance.jraf.bootstrap.ApplicationContextFactoryInitializer;
import com.airfrance.jraf.bootstrap.initializer.InitializableHelper;
import com.airfrance.jraf.commons.exception.JrafConfigException;
import com.airfrance.jraf.spi.bootstrap.IBootstrapConstants;
import com.airfrance.jraf.spi.bootstrap.IBootstrapProvider;
import com.airfrance.jraf.spi.initializer.IInitializable;
import com.airfrance.jraf.bootstrap.initializer.Initializer;

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
