package com.airfrance.jraf.provider.persistence.hibernate;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.airfrance.jraf.commons.exception.JrafConfigException;
import com.airfrance.jraf.provider.persistence.hibernate.config.FileSystemXmlHibernateConfigReader;
import com.airfrance.jraf.provider.persistence.hibernate.config.IHibernateConfigReader;
import com.airfrance.jraf.provider.persistence.hibernate.config.UrlSystemXmlHibernateConfigReader;

public class PersistenceProviderSpringUrlImpl
	extends PersistenceProviderImpl
	implements ApplicationContextAware{

	/** logger */
	private static final Log log = LogFactory.getLog(PersistenceProviderSpringImpl.class);

	/** Spring context */
	private ApplicationContext applicationContext = null;

	/** root path */
	private String rootPath;

	/** hibernate relative config path */
	private String configFile;

	/**
	 * @return
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @param context
	 */
	public void setApplicationContext(ApplicationContext context) {
		applicationContext = context;
	}
	
	/**
	 * Initialisation du provider a partir des informations renseignees par le constructeur 
	 * ou par des getter/setter.
	 * @return provider
	 */
	public void initialize() {

		// recuperation du chemin racine
		String lc_rootPath = getRootPath();

		// recuperation du fichier de configuration
		String lc_configFile = getConfigFile();
		String lc_fullPathConfigFile = null;
		//URL lc_fullUrl = null;

		if (lc_rootPath == null) {
			if (getApplicationContext() == null) {
				log.fatal(
					"répertoire de configuration non fourni (ROOT_PATH_KEY)");
				throw new JrafConfigException("répertoire de configuration non fourni (ROOT_PATH_KEY)");
			}		
			
			lc_fullPathConfigFile = fullPath();
			log.info("url en string : "+lc_fullPathConfigFile);
			lc_rootPath =
				lc_fullPathConfigFile.substring(
					0,
					lc_fullPathConfigFile.indexOf(lc_configFile));
			setRootPath(lc_rootPath);
		} else {
			lc_fullPathConfigFile =
				lc_rootPath + File.separator + lc_configFile;
		}
		log.info("Lecture du fichier de configuration hibernate...");
		log.debug("fichier de config : " + lc_fullPathConfigFile);

		IHibernateConfigReader reader = getReader(lc_fullPathConfigFile);


		Configuration configuration = new Configuration();
		SessionFactory sf = reader.readConfig(configuration);

		this.setSessions(sf);
		afterPropertiesSet();

	}
	
	protected IHibernateConfigReader getReader(String configPath){
		return new UrlSystemXmlHibernateConfigReader(configPath);
	}
	
	protected String fullPath(){
		try{
			URL lc_fullUrl = getApplicationContext()
			.getResource(getConfigFile())
			.getURL();
			return lc_fullUrl.toString();
			
			/*
			 * pour l'ancien :
			 * String full = getApplicationContext()
			 *  .getResource(getConfigFile())
			 *  .getURL()
			 *  .getPath();
			 * return full;
			 */
		}
		catch (IOException e) {
			log.fatal("fichier de configuration introuvable");
			throw new JrafConfigException("fichier de configuration introuvable");
		}
		
	}


	/**
	 * @return
	 */
	public String getRootPath() {
		return rootPath;
	}

	/**
	 * @param string
	 */
	public void setRootPath(String string) {
		rootPath = string;
	}

	/**
	 * @return
	 */
	public String getConfigFile() {
		return configFile;
	}

	/**
	 * @param string
	 */
	public void setConfigFile(String string) {
		configFile = string;
	}

}
