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
 * Créé le 5 mars 04
 */
package org.squale.jraf.provider.persistence.hibernate;

import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafConfigException;
import org.squale.jraf.provider.persistence.hibernate.config.FileSystemXmlHibernateConfigReader;
import org.squale.jraf.provider.persistence.hibernate.config.IHibernateConfigReader;
import org.squale.jraf.spi.bootstrap.IBootstrapConstants;
import org.squale.jraf.spi.initializer.IInitializable;
import org.squale.jraf.spi.initializer.IInitializableBean;
import org.squale.jraf.spi.provider.IProvider;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafProviderPersistence
 * <p>Title : Initializer.java</p>
 * <p>Description : Initialisateur du provider de persistance.</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 */
public class Initializer implements IInitializable, IInitializableBean {

	/** logger */
	private static final Log log = LogFactory.getLog(Initializer.class);

	/** cle pour recuperer le fichier de configuration */
	public final static String CONFIG_FILE_KEY = "configFile";

	/** cle pour recuperer si les transactions sont gérés par le conteneur */
	public final static String CONTAINER_MANAGED_TRANSACTION_KEY =
		"containerManagedTransaction";

	/** cle pour savoir si la session est stockée dans le thread local */
	public final static String THREAD_LOCAL_SESSION_KEY = "threadLocalSession";

	/** cle pour recuperer si les transactions sont gérés par le conteneur */
	public final static String LONG_SESSION_KEY = "longSession";
	/** cle pour recuperer si les transactions sont gérés par le conteneur */
	public final static String AUTOMATIC_TRANSACTION_KEY =
		"automaticTransaction";
	/** cle pour recuperer si les transactions sont gérés par le conteneur */
	public final static String INTROSPECTION_KEY = "introspection";
	/** root path */
	private String rootPath;

	/** hibernate relative config path */
	private String configFile;

	/** container managed transaction */
	private boolean containerManagedTransaction;

	/** thread local session */
	private boolean threadLocalSession;

	/** automatic transaction */
	private boolean automaticTransaction;

	/** long session */
	private boolean longSession;

	/** introspection */
	private boolean introspection;
	
	/**
	 * Constructeur vide type IOC 2
	 */
	public Initializer() {
	}

	/**
	 * Constructeur avec parametres type IOC 3
	 * @param rootpath chemin racine
	 * @param configFile chemin relatif vers le fichier de configuration
	 * @param isContainerManagedTransaction transaction gere par le container si true, false sinon
	 */
	public Initializer(
		String rootpath,
		String config,
		boolean isContainerManagedTransaction) {
		super();
		setRootPath(rootpath);
		setConfigFile(config);
		setContainerManagedTransaction(isContainerManagedTransaction);
		afterPropertiesSet();

	}

	/**
	 * Constructeur avec parametres type IOC 3
	 * @param rootpath chemin racine
	 * @param configFile chemin relatif vers le fichier de configuration
	 * @param isContainerManagedTransaction transaction gere par le container si true, false sinon
	 * @param isThreadLocalSession true si utilisation du thread local session, false sinon
	 * @param isLongSession true si utilisation d'une session longue, false sinon
	 * @param isAutomaticTransaction true si utilisation des transactions automatiques, false sinon
	 */
	public Initializer(
		String rootpath,
		String config,
		boolean isContainerManagedTransaction,
		boolean isThreadLocalSession,
		boolean isLongSession,
		boolean isAutomaticTransaction,
		boolean isIntrospection) {
		super();
		setRootPath(rootpath);
		setConfigFile(config);
		setContainerManagedTransaction(isContainerManagedTransaction);
		setThreadLocalSession(isThreadLocalSession);
		setLongSession(isLongSession);
		setAutomaticTransaction(isAutomaticTransaction);
		setIntrospection(isIntrospection);
		afterPropertiesSet();

	}

	/**
	 * Constructeur avec parametres type IOC 3.
	 * Transaction non gerees par le conteneur.
	 * @param rootpath chemin racine
	 * @param configFile chemin relatif vers le fichier de configuration
	 */
	public Initializer(String rootpath, String config) {
		this(rootpath, config, false);

	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.spi.initializer.IInitializable#initialize(java.util.Map)
	 */
	public IProvider initialize(Map in_configProperties) {

		// fichier de configuration hibernate
		String lc_rootPath = getRootPath(in_configProperties);

		// fichier de configuration hibernate
		String lc_hibernateConfigFilePath =
			getHibernateConfigFilePath(in_configProperties);

		// transaction geree ou pas par le conteneur
		boolean lc_containerManagedTransaction =
			getContainerManagedTransaction(in_configProperties);

		// transaction geree ou pas par le conteneur
		boolean lc_threadLocalSession =
			getThreadLocalSession(in_configProperties);

		// transaction geree ou pas par le conteneur
		boolean lc_longSession = getLongSession(in_configProperties);

		// transaction geree ou pas par le conteneur
		boolean lc_automaticTransaction =
			getAutomaticTransaction(in_configProperties);

		// introspection autorisee ou pas par le conteneur
		boolean lc_instrospection = getIntrospection(in_configProperties);
		
		// on fixe les proprietes
		setRootPath(lc_rootPath);
		setConfigFile(lc_hibernateConfigFilePath);
		setContainerManagedTransaction(lc_containerManagedTransaction);
		setThreadLocalSession(lc_threadLocalSession);
		setAutomaticTransaction(lc_automaticTransaction);
		setLongSession(lc_longSession);
		setIntrospection(lc_instrospection);

		// on valide l'initialisation du composant
		afterPropertiesSet();

		// lance l'initialisation		
		return initialize();

	}

	/**
	 * Retourne true si les transactions sont gerees par le conteneur, false sinon
	 * @param in_configProperties fichier de configuration des proprietes
	 * @return true si les transactions sont gerees par le conteneur, false sinon
	 */
	private final boolean getContainerManagedTransaction(Map in_configProperties) {

		return getBoolean(
			in_configProperties,
			CONTAINER_MANAGED_TRANSACTION_KEY);

	}

	/**
	 * Retourne true si utilisation du design pattern thread local session, false sinon
	 * @param in_configProperties fichier de configuration des proprietes
	 * @return true si utilisation du design pattern thread local session, false sinon
	 */
	private final boolean getThreadLocalSession(Map in_configProperties) {

		return getBoolean(in_configProperties, THREAD_LOCAL_SESSION_KEY);

	}

	/**
	 * Retourne true si utilisation d'une session longue, false sinon
	 * @param in_configProperties fichier de configuration des proprietes
	 * @return true si utilisation d'une session longue, false sinon
	 */
	private final boolean getLongSession(Map in_configProperties) {

		return getBoolean(in_configProperties, LONG_SESSION_KEY);

	}

	/**
	 * Retourne true si utilisation de la gestion automatique des transactions, false sinon
	 * @param in_configProperties fichier de configuration des proprietes
	 * @return true si utilisation de la gestion automatique des transactions, false sinon
	 */
	private final boolean getAutomaticTransaction(Map in_configProperties) {

		return getBoolean(in_configProperties, AUTOMATIC_TRANSACTION_KEY);

	}

	/**
	 * Retourne true si on doit garder la configuration du mapping afin de realiser une introspection,
	 *  false sinon
	 * @param in_configProperties fichier de configuration des proprietes
	 * @return true si on autorise introspection, false sinon
	 */
	private final boolean getIntrospection(Map in_configProperties) {

		return getBoolean(in_configProperties, INTROSPECTION_KEY);

	}
	
	/**
	 * Retourne la valeur booleenne d'une propriete
	 * @param in_configProperties map de proprietes de configuration
	 * @param in_propertiesKey cle de la propriete booleenne
	 * @return valeur de la propriete
	 */
	private final boolean getBoolean(
		Map in_configProperties,
		String in_propertiesKey) {

		String lc_value = null;

		lc_value = (String) in_configProperties.get(in_propertiesKey);

		if (StringUtils.isEmpty(lc_value)) {
			return false;
		}
		return new Boolean(lc_value).booleanValue();

	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.spi.initializer.IInitializable#initialize()
	 */
	public IProvider initialize() {

		IHibernateConfigReader reader =
			new FileSystemXmlHibernateConfigReader(
				getRootPath() + getConfigFile());

		Configuration configuration = new Configuration();
		SessionFactory sf = reader.readConfig(configuration);

		PersistenceProviderImpl lc_provider =
			new PersistenceProviderImpl(
				sf,
				isContainerManagedTransaction(),
				isThreadLocalSession(),
				isLongSession(),
				isAutomaticTransaction(),
				isIntrospection());

		// retourne le provider 
		return lc_provider;

	}

	/**
	 * Retourne le chemin vers le fichier de configuration
	 * @return chemin du fichier de configuration
	 */
	public String getConfigFile() {
		return configFile;
	}

	/**
	 * @return
	 */
	public boolean isContainerManagedTransaction() {
		return containerManagedTransaction;
	}

	/**
	 * Retourne le repertoire racine des fichiers de configuration
	 * @return repertoire racine des fichiers de configuration
	 */
	public String getRootPath() {
		return rootPath;
	}

	/**
	 * Fixe le chemin relatif vers le fichier de configuration
	 * @param string chemin relation vers le fichier de configuration
	 */
	public void setConfigFile(String string) {
		configFile = string;
	}

	/**
	 * Fixe le mode transactionnel
	 * @param b mode transactionnel
	 */
	public void setContainerManagedTransaction(boolean b) {
		containerManagedTransaction = b;
	}

	/**
	 * Fixe le chemin racine
	 * @param string
	 */
	public void setRootPath(String string) {
		rootPath = string;
	}

	/**
	 * Analyse les paramètres d'initialisation du provider de persistence
	 * @param objectInitialize
	 * @return
	 * @throws JrafConfigException
	 */
	private final String getHibernateConfigFilePath(Map objectInitialize) {
		if (objectInitialize.isEmpty()) {
			throw new JrafConfigException("Configuration du provider de persistence incorrect");
		}

		String param = (String) objectInitialize.get(CONFIG_FILE_KEY);

		return param;
	}

	private String getRootPath(Map objectInitialize)
		throws JrafConfigException {
		if (objectInitialize.isEmpty()) {
			throw new JrafConfigException("Configuration du provider de persistence incorrect");
		}

		String path =
			(String) objectInitialize.get(IBootstrapConstants.ROOT_PATH_KEY);

		return path;
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.spi.initializer.IInitializableBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {

		if (getRootPath() == null) {
			log.fatal("répertoire de configuration non fourni (ROOT_PATH_KEY)");
			throw new JrafConfigException("répertoire de configuration non fourni (ROOT_PATH_KEY)");
		}
		if (getConfigFile() == null) {
			log.fatal("fichier de configuration non fourni");
			throw new JrafConfigException("fichier de configuration non fourni");
		}
	}

	/**
	 * @return
	 */
	public boolean isAutomaticTransaction() {
		return automaticTransaction;
	}

	/**
	 * @return
	 */
	public boolean isLongSession() {
		return longSession;
	}

	/**
	 * @return
	 */
	public boolean isThreadLocalSession() {
		return threadLocalSession;
	}

	/**
	 * @param b
	 */
	public void setAutomaticTransaction(boolean b) {
		automaticTransaction = b;
	}

	/**
	 * @param b
	 */
	public void setLongSession(boolean b) {
		longSession = b;
	}

	/**
	 * @param b
	 */
	public void setThreadLocalSession(boolean b) {
		threadLocalSession = b;
	}

	/**
	 * @return
	 */
	public boolean isIntrospection() {
		return introspection;
	}

	/**
	 * @param b
	 */
	public void setIntrospection(boolean b) {
		introspection = b;
	}

}
