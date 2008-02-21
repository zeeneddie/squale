package com.airfrance.jraf.provider.persistence.hibernate;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafConfigException;
import com.airfrance.jraf.commons.exception.JrafPersistenceException;
import com.airfrance.jraf.commons.exception.JrafRuntimeException;
import com.airfrance.jraf.spi.initializer.IInitializableBean;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;

/**
 * Objet qui sert à cacher l'implémentation d'une session propre au produit de persistence utilisé
 * Il encapsule la session du produit et une éventuelle transaction associé
 * @author Courtial 
 * @version 2.0
 */
public class SessionImpl implements ISession, IInitializableBean {
 
	/** log */
	private static final Log log = LogFactory.getLog(SessionImpl.class);

	/** provider de persistance */

	private IPersistenceProvider persistenceProvider;

	/** la session Hibernate */
	private Session session = null;

	/** la transaction Hibernate */
	private Transaction tx = null;
	
	private boolean isValid = true;

	/**
	 * Constructeur vide IOC type 2
	 */
	public SessionImpl() {
		super();
	}

	/**
	 * Constructeur avec parametres IOC type 3.
	 * @param session hibernate
	 * @param provider provider de persistance
	 */
	public SessionImpl(
		Session sess,
		IPersistenceProvider provider) {
		this.setSession(sess);
		this.setPersistenceProvider(provider);
		this.afterPropertiesSet();
	}

	/**
	 * Retourne la session hibernate
	 * @return Session session hibernate
	 */
	public Session getSession() {
		return session;
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.persistence.ISession#closeSession()
	 */
	public void closeSession() throws JrafPersistenceException {
		if (session != null) {
			try {
				if (tx == null) {
					session.flush();

					// on effectue le commit seulement si les transactions 
					// ne sont pas gerees par le conteneur
					if (!isContainerManagedTransaction() && session.isOpen()) {
						// FB depuis Hibernate 3 le session.connection() ouvre
						// une nouvelle connection jdbc avec la base et l'utilisateur
						// doit la refermer explicitement
						Connection connection =session.connection();
						if(connection.getAutoCommit()) {
							connection.close();							
						} else {
							connection.commit();
						}
					}
					session.close();
				}
			} catch (HibernateException e) {
				log.error("Probleme lors de la fermeture la session", e);
				throw new JrafPersistenceException(
					"Probleme lors de la fermeture la session",
					e);
			} catch (SQLException e) {
				log.error("Probleme lors de la fermeture la session", e);
				throw new JrafPersistenceException(
					"Probleme lors de la fermeture la session",
					e);
			} finally {

			}
		} else {
			throw new JrafPersistenceException("Pas de session ouverte");
		}

	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.persistence.ISession#beginTransaction()
	 */
	public void beginTransaction() throws JrafPersistenceException {
		try {
			if (tx != null) {
				throw new JrafPersistenceException("Une transaction est déjà en cours");
			}

			tx = session.beginTransaction();
		} catch (HibernateException e) {
			if (session != null) {
				try {
					session.close();
				} catch (HibernateException e1) {
					throw new JrafPersistenceException(
						"Probleme lors de la creation d'une transaction",
						e1);
				}
			}
			throw new JrafPersistenceException(
				"Probleme lors de la creation d'une transaction",
				e);
		}
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.persistence.ISession#commitTransaction()
	 */
	public void commitTransaction() throws JrafPersistenceException {
		if (tx != null) {
			try {
				// execution du commit hibernate
				// en cas de probleme hibernate realise automatiquement le rollback
				if (log.isDebugEnabled()) {
					log.debug("Execution du commit hibernate...");
				}
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug("Commit hibernate OK");
					log.debug("Mise de la transaction a null...");
				}

				// la transaction est mise a null car validee par commit 
				tx = null;
				try {
					if (log.isDebugEnabled()) {
						log.debug("Fermeture de la session...");
					}
					session.close();
					if (log.isDebugEnabled()) {
						log.debug("Commit OK");
					}

				} catch (HibernateException e) {
					String message =
						"Probleme de fermeture de session apres le commit";
					log.error(message, e);
					throw new JrafPersistenceException(message, e);
				}

			} catch (HibernateException e) {
				String message = "Probleme lors du commit";
				log.error(message, e);
				throw new JrafPersistenceException(message, e);
			} finally {
				// rien a faire
			}
		} else {
			log.error("commitTransaction: aucune transaction en cours");
			throw new JrafPersistenceException("commitTransaction: aucune transaction en cours");
		}
	}

	/**
	 * Commit d'une transaction sans fermer la session
	 * @throws JrafPersistenceException
	 */
	public void commitTransactionWithoutClose()
		throws JrafPersistenceException {
		if (tx != null) {
			try {
				// execution du commit hibernate
				// en cas de probleme hibernate realise automatiquement le rollback
				if (log.isDebugEnabled()) {
					log.debug("Execution du commit hibernate...");
				}
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug("Commit hibernate OK");
					log.debug("Mise de la transaction a null...");
				}

				// la transaction est mise a null car validee par commit 
				tx = null;

			} catch (HibernateException e) {

				// on propage l'exception
				String message = "Probleme lors du commit";
				log.error(message, e);
				throw new JrafPersistenceException(message, e);

			} finally {
				// rien a faire
			}
		} else {
			log.error("commitTransaction: aucune transaction en cours");
			throw new JrafPersistenceException("commitTransaction: aucune transaction en cours");
		}
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.persistence.ISession#rollbackTransaction()
	 */
	public void rollbackTransaction() {
		if (tx != null) {
			try {
				if (log.isDebugEnabled()) {
					log.debug("Execution du rollback hibernate...");
				}
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug("Rollback hibernate OK");
				}
			} catch (HibernateException e) {
				log.error("Probleme lors d'un rollback", e);
				throw new JrafRuntimeException(
					"Probleme lors d'un rollback",
					e);
			} finally {
				if (log.isDebugEnabled()) {
					log.debug("Mise de la transaction hibernate a null...");
				}
				tx = null;
				try {
					if (log.isDebugEnabled()) {
						log.debug("Fermeture de la session hibernate...");
					}
					session.close();
					if (log.isDebugEnabled()) {
						log.debug("Rollback OK");
					}

				} catch (HibernateException e1) {
					log.error(
						"Probleme lors de la fermeture d'une session lors d'un rollback",
						e1);
				}
			}
		} else {
			if (log.isDebugEnabled()) {
				// aucune transaction
				String message =
					"rollbackTransaction: aucune transaction en cours";

				log.debug(message);
			}
		}

	}

	/**
	 * Rollback d'une transaction sans fermeture de session.
	 * La session est reinitialisee pour etre remise en phase avec la base de donnees.
	 */
	public void rollbackTransactionWithoutClose() {
		if (tx != null) {
			try {
				if (log.isDebugEnabled()) {
					log.debug("Execution du rollback hibernate...");
				}
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug("Rollback hibernate OK");
				}
			} catch (HibernateException e) {
				log.error("Probleme lors d'un rollback", e);
				throw new JrafRuntimeException(
					"Probleme lors d'un rollback",
					e);
			} finally {
				if (log.isDebugEnabled()) {
					log.debug("Mise de la transaction hibernate a null...");
				}
				tx = null;
				// la session est reinitialisee pour revenir a un etat coherent
				if (log.isDebugEnabled()) {
					log.debug("Reeinitialisation de la session hibernate...");
				}
				session.clear();
			}
		} else {
			if (log.isDebugEnabled()) {
				// aucune transaction
				String message =
					"rollbackTransaction: aucune transaction en cours";

				log.debug(message);
			}
		}

	}

	/**
	 * Retourne true si les transactions sont gerees par le conteneur, false sinon
	 * @return true si les transactions sont gerees par le conteneur, false sinon
	 */
	public boolean isContainerManagedTransaction() {

		return getPersistenceProvider().isContainerManagedTransaction();
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.persistence.ISession#evict(java.lang.Object)
	 */
	public void evict(Object object) throws JrafPersistenceException {
		try {
			session.evict(object);
		} catch (HibernateException e) {
			throw new JrafPersistenceException(e);
		}
	}

	/**
	 * Execute un flush sur la session hibernate
	 */
	public void flush() throws JrafPersistenceException {
		try {
			session.flush();
		} catch (HibernateException e) {
			throw new JrafPersistenceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.persistence.ISession#contains(java.lang.Object)
	 */
	public boolean contains(Object object) {
		return session.contains(object);
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.persistence.ISession#clear()
	 */
	public void clear() {
		session.clear();
	}

	public SchemaExport getSchemaExport() {
		if (log.isDebugEnabled()) {
			log.debug("getSchemaExport() - start");
		}

		SchemaExport sExport = null;
		try {
			Configuration cf = new Configuration().configure();
			sExport = new SchemaExport(cf);

		} catch (Exception e) {
			log.error("getSchemaExport()", e);
		}

		if (log.isDebugEnabled()) {
			log.debug("getSchemaExport() - end");
		}
		return sExport;

	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.persistence.ISession#isOpen()
	 */
	public boolean isOpen() {

		// si pas de session retourne false
		if (session == null) {
			return false;
		}

		// retourne l'etat d'ouverture de la session
		return session.isOpen();
	}

	/**
	 * Retourne true si la session est liee a une connexion jdbc, false sinon
	 * @return true si la session est liee a une connexion jdbc, false sinon
	 */
	public boolean isConnected() {

		// si pas de session retourne false
		if (session == null) {
			return false;
		}

		// retourne l'etat de connexion de la session
		return session.isConnected();
	}

	/**
	 * @param session
	 */
	public void setSession(org.hibernate.Session sess) {
		this.session = sess;
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.initializer.IInitializableBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		if (getSession() == null) {
			throw new JrafConfigException("La session hibernate ne peut etre 'null'");
		}

		if (getPersistenceProvider() == null) {
			throw new JrafConfigException("Le provider de persistance ne peut etre 'null'");
		}
	}

	/**
	 * Retourne le provider de persistance
	 * @return provider de persistance
	 */
	public IPersistenceProvider getPersistenceProvider() {
		return persistenceProvider;
	}

	/**
	 * Renseigne le provider de persistance
	 * @param provider provider de persistance
	 */
	public void setPersistenceProvider(IPersistenceProvider provider) {
		persistenceProvider = provider;
	}

	/**
	 * Retourne true si il y a une transaction en cours sur cette session
	 * Retourne false sinon.
	 * @return true si il y a une transaction en cours sur cette session, false sinon
	 */
	public boolean isInTransaction() {
		return (tx != null);

	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.persistence.ISession#invalidate()
	 */
	public void invalidate() {
		isValid=false;
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.persistence.ISession#isValid()
	 */
	public boolean isValid() {
		return isValid;
	}
}
