/*
 * Cree le 9 mars 04
 */
package com.airfrance.jraf.provider.accessdelegate;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.airfrance.jraf.commons.exception.JrafConfigException;
import com.airfrance.jraf.provider.accessdelegate.config.FileSystemXmlApplicationComponentConfigReader;
import com.airfrance.jraf.provider.accessdelegate.config.IApplicationComponentConfigReader;
import com.airfrance.jraf.spi.bootstrap.IBootstrapConstants;
import com.airfrance.jraf.spi.initializer.IInitializable;
import com.airfrance.jraf.spi.initializer.IInitializableBean;
import com.airfrance.jraf.spi.provider.IProvider;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafProviderAccessdelegate
 * <p>Title : AbstractInitializer</p>
 * <p>Description : Classe d'initialisation abastraite du provider access delegate.
 * La methode buildLookupComponent() permet aux classes d'implémentation d'utiliser le lookupComponent
 * adequat pour leur contexte. (design pattern 'template method')
 * </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 */
public abstract class AbstractSpringInitializer
	implements IInitializable, IInitializableBean, ApplicationContextAware {

	/** logger */
	private static final Log log = LogFactory.getLog(AbstractInitializer.class);

	/** intitule du parametre du fichier de configuration */
	protected final static String CONFIG_FILE = "configFile";

	/** chemin relatif du fichier de configuration */
	private String configFile;

	/** chemin racine du fichier de configuration */
	private String rootPath;

	/** Spring context */
	private ApplicationContext applicationContext = null;

	/**
	 * Constructeur vide type IOC 2
	 */
	public AbstractSpringInitializer() {
		super();
	}

	/**
	 * Constructeur avec parametres type IOC 3
	 * @param rootPath chemin racine
	 * @param configFileName fichier de configuration
	 */
	public AbstractSpringInitializer(String rootPath, String configFileName) {
		setConfigFile(configFileName);
		setRootPath(rootPath);
		afterPropertiesSet();
	}

	/**
	 * Constructeur avec parametres type IOC 3
	 * le RootPath est calcule
	 * @param configFileName fichier de configuration
	 */
	public AbstractSpringInitializer( String configFileName) {
		setConfigFile(configFileName);
		afterPropertiesSet();
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.initializer.IInitializable#initialize(java.util.Map)
	 */
	public IProvider initialize(Map objectInitialize)
		throws JrafConfigException {

		if (log.isDebugEnabled()) {
			log.debug(
				"Parametres d'initialisation du provider access delegate : "
					+ objectInitialize);
		}
		parseParameters(objectInitialize);
		afterPropertiesSet();
		return initialize();
	}

	/**
	 * Analyse les paramètres d'initialisation du provider d'access
	 * @param objectInitialize map des parametres
	 * @throws JrafConfigException
	 */
	private final void parseParameters(Map objectInitialize)
		throws JrafConfigException {
		if (objectInitialize.isEmpty())
			throw new JrafConfigException("Configuration du provider d'acces incorrect");

		String param = (String) objectInitialize.get(CONFIG_FILE);
		setConfigFile(param);

		String path =
			(String) objectInitialize.get(IBootstrapConstants.ROOT_PATH_KEY);

		setRootPath(path);

	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.initializer.IInitializable#initialize()
	 */
	public IProvider initialize() throws JrafConfigException {

		// recuperation du chemin racine
		String lc_rootPath = getRootPath();

		// recuperation du fichier de configuration
		String lc_configFile = getConfigFile();
		String lc_fullPathConfigFile = null;

		if (lc_rootPath == null) {
			if (getApplicationContext() == null) {
				log.fatal(
					"répertoire de configuration non fourni (ROOT_PATH_KEY)");
				throw new JrafConfigException("répertoire de configuration non fourni (ROOT_PATH_KEY)");
			}
			try {
				lc_fullPathConfigFile =
					getApplicationContext()
						.getResource(getConfigFile())
						.getURL()
						.getPath();
			} catch (IOException e) {
				log.fatal(
					"fichier de configuration introuvable");
				throw new JrafConfigException("fichier de configuration introuvable");
			}
			lc_rootPath =
				lc_fullPathConfigFile.substring(
					0,
					lc_fullPathConfigFile.indexOf(lc_configFile));
			setRootPath(lc_rootPath);
		} else {
			lc_fullPathConfigFile =
				lc_rootPath + File.separator + lc_configFile;
		}
		// instanciation d'un lecteur de fichier de configuration
		IApplicationComponentConfigReader reader =
			new FileSystemXmlApplicationComponentConfigReader(
		lc_fullPathConfigFile);

		// instanciation du lookup
		ILookupComponent lookup = buildLookupComponent(reader);

		// instanciation du provider
		IProvider provider = new AccessDelegateProviderImpl(lookup);

		// retourne le provider initialise
		return provider;
	}

	/**
	 * Retourne le chemin relatif vers fichier de configuration
	 * @return chemin relatif du fichier de configuration
	 */
	public String getConfigFile() {
		return configFile;
	}

	/**
	 * Retourne le repertoire de base du fichier de configuration
	 * @return chemin de base des fichiers de configuration
	 */
	public String getRootPath() {
		return rootPath;
	}

	/**
	 * Fixe le chemin relatif vers fichier de configuration
	 * @param string
	 */
	public void setConfigFile(String string) {
		configFile = string;
	}

	/**
	 * Fixe le repertoire de base du fichier de configuration
	 * @param string
	 */
	public void setRootPath(String string) {
		rootPath = string;
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.initializer.IInitializableBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {

		if (getConfigFile() == null) {
			throw new JrafConfigException("fichier de configuration non fourni.");
		}


	}


	protected abstract ILookupComponent buildLookupComponent(IApplicationComponentConfigReader reader);
	
	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext)
		throws BeansException {
		this.applicationContext = applicationContext;

	}

	/**
	 * @return the spring context
	 */
	private ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
