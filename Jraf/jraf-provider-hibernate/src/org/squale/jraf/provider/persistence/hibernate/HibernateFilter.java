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
 * Cree le 31 janv. 05
 */
package org.squale.jraf.provider.persistence.hibernate;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.WeakHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.bootstrap.locator.ProviderLocator;
import org.squale.jraf.commons.exception.JrafPersistenceException;
import org.squale.jraf.commons.exception.JrafRuntimeException;
import org.squale.jraf.spi.persistence.IPersistenceProvider;

/**
 * <p>Project: JRAF 
 * <p>Title : HibernateFilter</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2005</p>
 *  
 */
public class HibernateFilter implements Filter {

	/** constante d'initialisation pour les providers de persistance */
	public final static String PERSISTENCE_PROVIDER_INIT =
		"persistenceProvider";

	/** cle pour stocker une session longue dans la session HTTP */
	public final static String SESSIONS_KEY = "JRAF_PERSISTENCEPROVIDER_";

	/** logger */
	private final static Log log = LogFactory.getLog(HibernateFilter.class);

	/** 
	 * map de providers
	 */
	private Map providersMap = new WeakHashMap();

	/**
	 * 
	 */
	public HibernateFilter() {
		super();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(
		ServletRequest request,
		ServletResponse response,
		FilterChain chain)
		throws IOException, ServletException {

		try {

			// pre-process
			if (log.isDebugEnabled()) {
				log.debug("preprocess...");
			}
			preProcess(request, response);

			// execution de la chaine de filtre
			chain.doFilter(request, response);

			// post-process
			if (log.isDebugEnabled()) {
				log.debug("postprocess...");
			}
			postProcess(request, response);

		} catch (IOException ioe) {
			log.error(ioe);
			throw ioe;
		} catch (ServletException se) {
			log.error(se);
			throw se;
		} finally {
			// nothing to do
		}

	}

	/**
	 * Pre-traitement d'une requete HTTP
	 */
	public void preProcess(ServletRequest request, ServletResponse response)
		throws IOException, ServletException {

		SessionImpl session = null;
		Iterator iterator = getProvidersMap().entrySet().iterator();
		Map.Entry entry = null;
		PersistenceProviderImpl persistenceProvider = null;
		String providerName = null;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession httpSession = null;

		// pour chaque provider
		while (iterator.hasNext()) {

			entry = (Map.Entry) iterator.next();
			providerName = (String) entry.getKey();
			persistenceProvider = (PersistenceProviderImpl) entry.getValue();

			if (log.isDebugEnabled()) {
				log.debug("providerName=" + providerName);
			}

			// cas thread local session et longue session
			if (persistenceProvider.isThreadLocalSession()
				&& persistenceProvider.isLongSession()) {
				if (log.isDebugEnabled()) {
					log.debug("Cas thread local session et session longue...");
				}

				httpSession = httpRequest.getSession();

				if (log.isDebugEnabled()) {
					log.debug("Recuperation de la session...");
				}
				session =
					(SessionImpl) httpSession.getAttribute(
						SESSIONS_KEY + providerName);

				if (session != null) {
					if (log.isDebugEnabled()) {
						log.debug("Session existante...");
						log.debug(
							"Mise de la session dans le thread local storage...");
					}

					ThreadLocal tl = new ThreadLocal();
					tl.set(session);
					persistenceProvider.setThreadLocal(tl);
				} else {
					if (log.isDebugEnabled()) {
						log.debug("Session non existante...");
						log.debug("Elle sera creee en cas d'appel...");
					}
				}

			}

		}

		// rien a faire par defaut

	}

	/**
	 * Post-traitement d'une requete HTTP
	 */
	public void postProcess(ServletRequest request, ServletResponse response)
		throws IOException, ServletException {

		Iterator iterator = getProvidersMap().entrySet().iterator();
		Map.Entry entry = null;
		PersistenceProviderImpl persistenceProvider = null;

		String providerName = null;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession httpSession = null;
		SessionImpl session = null;

		while (iterator.hasNext()) {

			entry = (Map.Entry) iterator.next();
			providerName = (String) entry.getKey();
			persistenceProvider = (PersistenceProviderImpl) entry.getValue();

			// cas thread local session
			if (persistenceProvider.isThreadLocalSession()) {
				if (log.isDebugEnabled()) {
					log.debug("Cas Thread Local Session...");
				}

				if (persistenceProvider.getThreadLocal() != null) {
					// cas thread local existe
					if (log.isDebugEnabled()) {
						log.debug("Cas thread local existe...");
					}
					session =
						(SessionImpl) persistenceProvider
							.getThreadLocal()
							.get();
					// cas session non null
					if (session != null) {
						if (log.isDebugEnabled()) {
							log.debug("Cas session existante...");
						}

						// cas transaction automatique + transaction existante
						if (persistenceProvider.isAutomaticTransaction()
							&& session.isInTransaction()) {
							if (log.isDebugEnabled()) {
								log.debug(
									"Cas transaction automatique existante...");
							}
							if (session.isValid()) {
								try {
									if (log.isDebugEnabled()) {
										log.debug(
											"Commit de la transaction...");
									}
									session.commitTransactionWithoutClose();
									if (log.isDebugEnabled()) {
										log.debug("Commit OK");
									}
								} catch (JrafPersistenceException e) {
									String message =
										"Probleme lors de la validation de la transaction";
									log.error(message, e);

									if (log.isDebugEnabled()) {
										log.debug(
											"Rollback de la transaction...");
									}
									session.rollbackTransactionWithoutClose();

									// on lance une runtime exception
									throw new JrafRuntimeException(message, e);

								}
							} else {
								// cas de session non valide (une exception a ete levee durant le traitement)
								if (log.isDebugEnabled()) {
									log.debug(
										"invalid session : rollback de la transaction ...");
								}
								session.rollbackTransactionWithoutClose();
							}

						}

						// cas session longue
						if (persistenceProvider.isLongSession()) {
							if (log.isDebugEnabled()) {
								log.debug("Cas de la session longue...");
							}

							try {

								// si la session est connectee et ouverte
								if (session.isConnected()
									&& session.isOpen()) {

									if (session.isValid()) {
										if (log.isDebugEnabled()) {
											log.debug("Flush de la session...");
										}
										session.getSession().flush();
									} else {
										if (log.isDebugEnabled()) {
											log.debug(
												"invalid session : clear ...");
										}
									}
									if (log.isDebugEnabled()) {
										log.debug(
											"Deconnexion de la session...");
									}
									// Depsuis hibernate 3 plus besoin de faire de reconnect et disconnect 

//									session.getSession().disconnect();
								}
							} catch (HibernateException e) {
								String message =
									"Probleme lors du flush/deconnexion de la session longue";
								log.error(message, e);

								if (log.isDebugEnabled()) {
									log.debug("On reinitialise la session...");
								}
								session.clear();

								// on lance une runtime exception
								throw new JrafRuntimeException(message, e);

							} finally {
								if (log.isDebugEnabled()) {
									log.debug(
										"On attache la session dans la session http...");
								}
								httpRequest.getSession().setAttribute(
									SESSIONS_KEY + providerName,
									session);
								if (log.isDebugEnabled()) {
									log.debug("On vide le thread local...");
								}
								persistenceProvider.getThreadLocal().set(null);
							}

						}

						// cas thread local session sans session longue
						else {

							try {
								if (log.isDebugEnabled()) {
									log.debug("closing session...");
								}

								// si la session est connectee et ouverte
								if (session.isConnected()
									&& session.isOpen()) {

									if (session.isValid()) {
										// flush de la session
										if (log.isDebugEnabled()) {
											log.debug("flush session ...");
										}
										session.flush();
									} else {
										if (log.isDebugEnabled()) {
											log.debug("invalid session : clear session ...");
										}
										session.clear();
									}

									// fermeture de la session
									if (log.isDebugEnabled()) {
										log.debug("close session ...");
									}
									session.getSession().close();

								}
							} catch (Exception e) {

								String message =
									"Probleme lors de la fermeture de la session";
								log.error(message, e);
								// on lance une runtime exception
								throw new JrafRuntimeException(message, e);

							} finally {
								if (log.isDebugEnabled()) {
									log.debug("On vide le thread local...");
								}
//								persistenceProvider.setThreadLocal(null);
								persistenceProvider.getThreadLocal().set(null);
							}

						}

					}

				}
			}

		}

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {

		// providers
		String providers =
			filterConfig.getInitParameter(PERSISTENCE_PROVIDER_INIT);

		if (providers != null) {
			// map de providers
			Map mProviders = getProviders(providers);

			// on fixe la map de providers
			setProvidersMap(mProviders);
		} else {
			log.debug("Aucun provider en parametre");
		}

	}

	/** Retourne une map de providers
	 * @param providers
	 * @return
	 */
	protected final Map getProviders(String providers) {

		// pas de providers on retourne null
		if (providers == null) {
			return null;
		}

		StringTokenizer st = new StringTokenizer(providers, ",");
		String providerName;
		IPersistenceProvider persistenceProvider = null;

		Map m = new WeakHashMap();

		while (st.hasMoreTokens()) {

			providerName = st.nextToken();

			persistenceProvider =
				(IPersistenceProvider) ProviderLocator.getProvider(
					providerName);

			if (persistenceProvider != null) {
				m.put(providerName, persistenceProvider);
			}
		}

		return m;
	}

	/**
	 * @return
	 */
	public Map getProvidersMap() {
		return providersMap;
	}

	/**
	 * @param map
	 */
	public void setProvidersMap(Map map) {
		providersMap = map;
	}

}
