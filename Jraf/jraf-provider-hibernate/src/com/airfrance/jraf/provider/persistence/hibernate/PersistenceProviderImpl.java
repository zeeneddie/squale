package com.airfrance.jraf.provider.persistence.hibernate;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafConfigException;
import com.airfrance.jraf.commons.exception.JrafPersistenceException;
import com.airfrance.jraf.provider.persistence.hibernate.config.IHibernateConfigReader;
import com.airfrance.jraf.spi.initializer.IInitializableBean;
import com.airfrance.jraf.spi.persistence.IMetaData;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafProviderPersistence
 * <p>Title : PersistenceProviderImpl.java</p>
 * <p>Description : Provider de persistance.</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 */
public class PersistenceProviderImpl
	implements IPersistenceProvider, IInitializableBean {

	/** log */
	private static final Log log =
		LogFactory.getLog(PersistenceProviderImpl.class);

	/** session factory hibernate */
	private SessionFactory sessions = null;

	/** Configuration hibernate */
	private Configuration configuration;
	
	/** metadata */
	private IMetaData metaData = null;

	/** true si les transactions sont gerees par le conteneur, false sinon */
	/** false par defaut */
	private boolean containerManagedTransaction = false;

	/** lecteur de fichier de configuration */
	private IHibernateConfigReader configReader;

	/**
	 * true si la session est stockee dans le thread local
	 */
	private boolean threadLocalSession = false;

	/**
	 * true si politique de session longue, false sinon
	 * false par defaut.
	 */
	private boolean longSession = false;

	/** true si on conserve la configuration du ficher de mapping pour introspection */
	private boolean introspection = false;

	/**
	 * true si ouverture/fermeture de transaction automatique, false sinon.
	 * false par defaut.
	 */
	private boolean automaticTranscation = false;

	/**
	 * Thread local pour stocker la session de persistance
	 */
	private ThreadLocal threadLocal = null;

	/**
	 * Constructeur vide type IOC2
	 */
	public PersistenceProviderImpl() {
	}

	/**
	 * Constructeur avec parametres type IOC3.
	 * Transaction non gerees par le conteneur
	 * @param sf session factory
	 */
	public PersistenceProviderImpl(SessionFactory sf) {
		this(sf, false);

	}

	/**
	 * Constructeur avec parametres type IOC3
	 * @param sf session factory
	 * @param isContainerManagedTransaction transaction geree par le conteneur
	 */
	public PersistenceProviderImpl(
		SessionFactory sf,
		boolean isContainerManagedTransaction) {

		setSessions(sf);
		setContainerManagedTransaction(isContainerManagedTransaction);
		afterPropertiesSet();

	}

	/**
	 * Constructeur avec parametres type IOC3
	 * @param sf session factory
	 * @param isContainerManagedTransaction transaction geree par le conteneur
	 */
	public PersistenceProviderImpl(
		SessionFactory sf,
		boolean isContainerManagedTransaction,
		boolean tls) {
		this(
			sf,
			isContainerManagedTransaction,
			tls,
			false,
			false,
			false);
	}

	/**
	 * Constructeur avec parametres type IOC3
	 * @param sf session factory
	 * @param isContainerManagedTransaction transaction geree par le conteneur
	 * @param threadLocalSession session place dans le thread local
	 * @param longSession politique de gestion de session longue
	 * @param automaticTransaction gestion automatique des transactions
	 */
	public PersistenceProviderImpl(
		SessionFactory sf,
		boolean isContainerManagedTransaction,
		boolean tls,
		boolean longSessionp,
		boolean automaticTransaction,
		boolean introspec) {

		setSessions(sf);
		setContainerManagedTransaction(isContainerManagedTransaction);
		setThreadLocalSession(tls);
		setLongSession(longSessionp);
		setAutomaticTranscation(automaticTransaction);
		setIntrospection(introspec);
		afterPropertiesSet();

	}

	/**
	 * Constructeur avec parametres type IOC3
	 * @param hConfigReader lecteur de fichier de configuration hibernate
	 * @param isContainerManagedTransaction transaction geree par le conteneur
	 */
	public PersistenceProviderImpl(
		IHibernateConfigReader hConfigReader,
		boolean isContainerManagedTransaction) {
		this(hConfigReader, isContainerManagedTransaction, false, false, false, false);

	}

	/**
	 * Constructeur avec parametres type IOC3
	 * @param hConfigReader lecteur de fichier de configuration hibernate
	 * @param isContainerManagedTransaction transaction geree par le conteneur
	 * @param tls session stocke dans le thread local
	 * @param longSession politique de session longue
	 * @param automaticTransaction ouverture/fermeture des transactions automatique
	 */
	public PersistenceProviderImpl(
		IHibernateConfigReader hConfigReader,
		boolean isContainerManagedTransaction,
		boolean tls) {
		this(
			hConfigReader,
			isContainerManagedTransaction,
			tls,
			false,
			false,
			false);

	}

	/**
	 * Constructeur avec parametres type IOC3
	 * @param hConfigReader lecteur de fichier de configuration hibernate
	 * @param isContainerManagedTransaction transaction geree par le conteneur
	 * @param tls session stocke dans le thread local
	 * @param longSessionp politique de session longue
	 * @param automaticTransaction ouverture/fermeture des transactions automatique
	 */
	public PersistenceProviderImpl(
		IHibernateConfigReader hConfigReader,
		boolean isContainerManagedTransaction,
		boolean tls,
		boolean longSessionp,
		boolean automaticTransaction,
		boolean introspec) {

		setConfigReader(hConfigReader);
		setContainerManagedTransaction(isContainerManagedTransaction);
		setThreadLocalSession(tls);
		setLongSession(longSessionp);
		setAutomaticTranscation(automaticTransaction);
		setIntrospection(introspec);

		afterPropertiesSet();
	}

	/**
	 * Constructeur avec parametres type IOC3
	 * @param hConfigReader lecteur de fichier de configuration hibernate
	 * @param isContainerManagedTransaction transaction geree par le conteneur
	 */
	public PersistenceProviderImpl(IHibernateConfigReader hConfigReader) {
		this(hConfigReader, false);

	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.persistence.IPersistenceProvider#getSession()
	 */
	public ISession getSession() throws JrafPersistenceException {

		SessionImpl lc_session = null;

		if (sessions != null) {

			try {

				// cas Thread local session
				if (isThreadLocalSession()) {

					if (log.isDebugEnabled()) {
						log.debug("Cas Thread Local Session...");
					}

					// on cree un thread local si il n'existe pas
					if (getThreadLocal() == null) {
						setThreadLocal(new ThreadLocal());
					}

					// recuperation de la session du thread local
					lc_session = (SessionImpl) threadLocal.get();
					if (lc_session == null) {
						if (log.isDebugEnabled()) {
							log.debug("Cas session null...");
						}

						// creation de la session
						lc_session =
							new SessionImpl(sessions.openSession(), this);
						// on met la session dans le thread local
						threadLocal.set(lc_session);
					} else {
						if (log.isDebugEnabled()) {
							log.debug("Cas session deja creee...");
						}

						// cas session longue
						if (isLongSession()) {
							if (log.isDebugEnabled()) {
								log.debug("Cas session longue...");
							}

							// reconnexion de la session si besoin
							if (!lc_session.getSession().isConnected()) {
								if (log.isDebugEnabled()) {
									log.debug("Connexion de la session...");
								}
								// Depsuis hibernate 3 plus besoin de faire de reconnect et disconnect 
	//							lc_session.getSession().reconnect();
							}

						}
					}

					// cas transaction automatique
					if (isAutomaticTransaction()) { 
						if (log.isDebugEnabled()) {
							log.debug("Cas transaction automatique...");
						}

						if (!lc_session.isInTransaction()) {
							if (log.isDebugEnabled()) {
								log.debug("Pas de transaction existante...");
								log.debug("Demarrage d'une transaction...");
							}

							lc_session.beginTransaction();
						}

					}

				}
				// cas simple
				// pas de thread local session
				else {
					if (log.isDebugEnabled()) {
						log.debug("Cas simple...");
						log.debug("Creation d'une nouvelle session...");
					}
					// initialisation de la session
					lc_session = new SessionImpl(sessions.openSession(), this);
				}

				return lc_session;

			} catch (Exception e) {
				String message =
					"La demande d'une nouvelle session hibernate a échoué à cause d'un problème de SGBD";
				log.fatal(message, e);
				throw new JrafPersistenceException(message, e);
			}

		} else {
			log.fatal(
				"La demande d'une nouvelle session hibernate a échoué. Erreur : l'objet sessions hibernate est null");
			throw new JrafPersistenceException("La demande d'une nouvelle session hibernate a échoué. Erreur : l'objet sessions hibernate est null");
		}
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.daolayer.itface.ISessionManager#getMetaData()
	 */
	public IMetaData getMetaData() {
		return metaData;
	}

	/**
	 * @see com.airfrance.jraf.daolayer.itface.ISessionManager#closeSession(ISession, Long)
	 * suppression de la session hibernate passée en paramètre
	 */
	public void closeSession(ISession session)
		throws JrafPersistenceException {
		if ((session != null)) {
			try {
				session.closeSession();
			} catch (Exception e) {
				log.error(
					"Echec lors de la fermeture d'une session Hibernate",
					e);
				throw new JrafPersistenceException(
					"Echec lors de la fermeture d'une session Hibernate",
					e);
			}
		}
	}

	/**
	 * Retourne true si les transaction sont gerees par le conteneur, false sinon.
	 * @return true si les transaction sont gerees par le conteneur, false sinon.
	 */
	public boolean isContainerManagedTransaction() {

		return containerManagedTransaction;
	}

	/**
	 * Fixe si les transactions sont gerees par le conteneur
	 * @param b 
	 */
	public void setContainerManagedTransaction(boolean b) {
		containerManagedTransaction = b;
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.initializer.IInitializableBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {

		// si le lecteur de fichier de configuration est renseigne
		if (getConfigReader() != null) {
			setConfiguration( new Configuration());
			setSessions(getConfigReader().readConfig(getConfiguration()));
		}

		// cas SessionFactory hibernate non intialisee = null	
		if (sessions == null) {
			String message =
				"Le provider hibernate n'a pas ete correctement initiailise. La SessionFactory est 'null'.";
			log.error(message);
			throw new JrafConfigException(message);

		}

		// Dans le cas ou on utilise pas l'introspection,
		// inutile de conserver la Configuration
		if(!isIntrospection()){
			setConfiguration(null);
		}
		
		// on cree les metadatas
		metaData = new MetaDataImpl(sessions, getConfiguration());

		// cas non thread local session et automatique transaction et long session		
		if (!isThreadLocalSession()
			&& (isAutomaticTransaction() || isLongSession())) {
			String message =
				"Impossible d'utiliser les transactions automatiques ou les session longues sans utiliser le thread local session";
			log.error(message);
			throw new JrafConfigException(message);
		}

	}

	/**
	 * Retourne la session factory hibernate
	 * @return session factory hibernate
	 */
	public SessionFactory getSessions() {
		return sessions;
	}

	/**
	 * Fixe la session factory hibernate
	 * @param factory sesson factory hibernate
	 */
	public void setSessions(SessionFactory factory) {
		sessions = factory;
	}

	/**
	 * Retourne le lecteur de fichier de configuration
	 * @return lecteur de fichier de configuration
	 */
	public IHibernateConfigReader getConfigReader() {
		return configReader;
	}

	/**
	 * Renseigne le lecteur de fichier de configuration
	 * @param reader lecteur de fichier de configuration
	 */
	public void setConfigReader(IHibernateConfigReader reader) {
		configReader = reader;
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
	public void setThreadLocalSession(boolean b) {
		threadLocalSession = b;
	}

	/**
	 * @return
	 */
	public boolean isAutomaticTransaction() {
		return automaticTranscation;
	}

	/**
	 * @return
	 */
	public boolean isLongSession() {
		return longSession;
	}

	/**
	 * @param b
	 */
	public void setAutomaticTranscation(boolean b) {
		automaticTranscation = b;
	}

	/**
	 * @param b
	 */
	public void setLongSession(boolean b) {
		longSession = b;
	}

	/**
	 * @return
	 */
	public ThreadLocal getThreadLocal() {
		return threadLocal;
	}

	/**
	 * @param local
	 */
	public void setThreadLocal(ThreadLocal local) {
		threadLocal = local;
	}

	/**
	 * @return
	 */
	public Configuration getConfiguration() {
		return configuration;
	}

	/**
	 * @param configuration
	 */
	public void setConfiguration(Configuration config) {
		this.configuration = config;
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
