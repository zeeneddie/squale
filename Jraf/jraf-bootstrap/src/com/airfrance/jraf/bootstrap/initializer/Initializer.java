/*
 * Created on Feb 20, 2004
 */
package com.airfrance.jraf.bootstrap.initializer;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.bootstrap.config.FileSystemXmlProviderConfigReader;
import com.airfrance.jraf.bootstrap.config.ProviderConfig;
import com.airfrance.jraf.bootstrap.config.ProviderConfigComparator;
import com.airfrance.jraf.bootstrap.locator.ProviderLocator;
import com.airfrance.jraf.commons.exception.JrafConfigException;
import com.airfrance.jraf.spi.bootstrap.IBootstrapConstants;
import com.airfrance.jraf.spi.bootstrap.IBootstrapProvider;
import com.airfrance.jraf.spi.initializer.IInitializable;
import com.airfrance.jraf.spi.initializer.IInitializableBean;
import com.airfrance.jraf.spi.provider.IProvider;
import com.airfrance.jraf.spi.provider.IProviderConstants;

/**
 * <p>Title : Initializer.java</p>
 * <p>Description : Initializer par defaut d'une application JRAF.
 * Initialise tous les providers declarees dans le fichier de configuration 
 * des providers.</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 * @author Eric BELLARD
 */
public class Initializer implements IInitializable, IInitializableBean {

	/** logger */
	private static final Log log = LogFactory.getLog(Initializer.class);

	/** chemin racine */
	private String rootPath;

	/** chemin relatif vers le fichier de configuration */
	private String configFile;

	/** bind jndi */
	private boolean isJndi = false;

	/** bind jndi */
	private boolean isBootstrapJndi = false;

	/**
	 * Constructeur vide type IOC2
	 */
	public Initializer() {
	}

	/**
	 * Constructeur avec parametres type IOC3
	 * @param rootPath chemin racine
	 * @param configFile chemin relatif vers le fichier de configuration
	 */
	public Initializer(String rootPath, String configFile) {
		this(rootPath, configFile, false);
	}

	/**
	 * Constructeur avec parametres type IOC3
	 * @param rootPath chemin racine 
	 * @param configFile chemin relatif vers le fichier de configuration
	 * @param isJndi enregistrement dans jndi
	 */
	public Initializer(String rootPath, String configFile, boolean isJndi) {
		setRootPath(rootPath);
		setConfigFile(configFile);
		setJndi(isJndi);
		afterPropertiesSet();
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.initializer.IInitializable#initialize(java.util.Map)
	 */
	public IProvider initialize(Map in_initParameters)
		throws JrafConfigException {

		// debut de l'initialisation
		log.info("Map des parametres : " + in_initParameters);
		Map lc_map = in_initParameters;

		// recuperation du chemin racine
		String lc_rootPath =
			(String) lc_map.get(IBootstrapConstants.ROOT_PATH_KEY);
		setRootPath(lc_rootPath);

		// recuperation du chemin relatif vers le fichier de configuration
		String lc_configFile =
			(String) lc_map.get(IBootstrapConstants.PROVIDER_CONFIG_KEY);
		setConfigFile(lc_configFile);

		// recuperation de l'information de bind jndi
		String lc_isJndi = (String) lc_map.get(IBootstrapConstants.JNDI_BIND);
		if (lc_isJndi != null) {
			boolean b = Boolean.valueOf(lc_isJndi).booleanValue();
			setJndi(b);
		}

		// verification de l'injection des dependances
		afterPropertiesSet();

		// on delegue l'initialisation
		return initialize();

	}

	/**
	 * Creation d'un provider standard
	 * @param in_config configuration de provider
	 * @param in_rootPath root path du contexte
	 * @param in_logger logger
	 * @return provider
	 * @throws JrafConfigException
	 */
	protected final IProvider createStandardProvider(
		ProviderConfig in_config,
		String in_rootPath)
		throws JrafConfigException {

		// declaration d'un provider
		IProvider lc_provider = null;

		// declaration d'un initializer
		IInitializable lc_Initializer = null;

		if (log.isDebugEnabled()) {
			log.debug(
				"Debut d'initialisation du provider " + in_config.getId());
		}

		// ajout du chemin courant dans la configuration
		in_config.putProperty(IBootstrapConstants.ROOT_PATH_KEY, in_rootPath);

		// creation de l'initializer
		lc_Initializer =
			InitializableHelper.instanciateInitializable(
				in_config.getClassName());

		// creation et retour du bootstrap provider
		lc_provider = lc_Initializer.initialize(in_config.getProperties());

		if (log.isDebugEnabled()) {
			log.debug(
				"Initialisation du provider "
					+ in_config.getId()
					+ " effectuee");
		}
		return lc_provider;

	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.initializer.IInitializable#initialize()
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
		log.info("Lecture du fichier de configuration des providers...");
		log.debug(
			"fichier de config : "
				+ lc_rootPath
				+ File.separator
				+ lc_configFile);

		// lecture du fichier de configuration
		try {
			FileSystemXmlProviderConfigReader file = new FileSystemXmlProviderConfigReader(lc_rootPath
			+ File.separator
			+ lc_configFile);
		lc_providers =	file.readConfig();
		} catch(Exception exc) {
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

		//		// controle de presence du provider de logging		
		//		if (locator.get("logging") == null) {
		//			String message =
		//				"Probleme de configuration: aucun provider de logging n'est renseigne";
		//			log.error(message);
		//			throw new JrafConfigException(message);
		//		}

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

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.initializer.IInitializableBean#afterPropertiesSet()
	 */
	public  void afterPropertiesSet() {

		// controle du fichier de configuration
		if (getConfigFile() == null) {
			throw new JrafConfigException("Fichier de configuration non fourni.");
		}

		// controle du chemin racine
		if (getRootPath() == null) {
			throw new JrafConfigException("Repertoire de configuration non fourni.");
		}
	}

	/**
	 * Retourne le chemin relatif vers le fichier de configuration
	 * @return chemin relatif vers le fichier de configuration
	 */
	public String getConfigFile() {
		return configFile;
	}

	/**
	 * Retourne true si enregistrement dans jndi, false sinon
	 * @return true si enregistrement dans jndi, false sinon
	 */
	public boolean isBootstrapJndi() {
		return isBootstrapJndi;
	}

	/**
	 * Retourne le chemin racine
	 * @return chemin racine
	 */
	public String getRootPath() {
		return rootPath;
	}

	/**
	 * Fixe le chemin du fichier de configuration
	 * @param string chemin du fichier de configuration
	 */
	public void setConfigFile(String string) {
		configFile = string;
	}

	/**
	 * Fixe si le locator doit etre enregistre dans jndi
	 * @param b true si le locator doit etre enregistre dans jndi, false sinon
	 */
	public void setBootstrapJndi(boolean b) {
		isBootstrapJndi = b;
	}

	/**
	 * Fixe le chemin vers le repertoire racine
	 * @param string chemin vers le repertoire racine
	 */
	public void setRootPath(String string) {
		rootPath = string;
	}

	/**
	 * Retourne true si enregistrement du locator dans jndi, false sinon
	 * @return true si enregistrement du locator dans jndi, false sinon
	 */
	public boolean isJndi() {
		return isJndi;
	}

	/**
	 * Fixe si le locator doit etre enregistre dans jndi
	 * @param b true si enregistrement du locator dans jndi, false sinon
	 */
	public void setJndi(boolean b) {
		isJndi = b;
	}

}
