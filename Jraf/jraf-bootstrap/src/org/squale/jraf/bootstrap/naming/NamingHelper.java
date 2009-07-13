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
 * Created on Mar 5, 2004
 */
package org.squale.jraf.bootstrap.naming;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.NoInitialContextException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.bootstrap.locator.ProviderLocator;
import org.squale.jraf.commons.exception.JrafConfigException;

/**
 * <p>Title : NamingHelper.java</p>
 * <p>Description : Helper pour la communication avec JNDI</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * 
 * @author Eric BELLARD
 */
public class NamingHelper {

	/** logger */
	private static final Log log = LogFactory.getLog(NamingHelper.class);

	/** nom de la classe d'acces a jndi par defaut */
	private final static String DEFAULT_JNDI_CLASS =
		"com.ibm.websphere.naming.WsnInitialContextFactory";

	/**
	 * Cree un contexte initial
	 * @param in_properties proprietes
	 * @return contexte initial
	 * @throws JrafConfigException
	 */
	public static Context getInitialContext() throws JrafConfigException {
		return getInitialContext(null, null);
	}

	/**
	 * Cree un contexte initial
	 * @param jndiUrl url de l'annuaire
	 * @param jndiClass classe d'acces
	 * @return contexte initial
	 * @throws JrafConfigException
	 */
	public static Context getInitialContext(String jndiUrl, String jndiClass)
		throws JrafConfigException {

		Hashtable hash = getJndiProperties(jndiUrl, jndiClass);

		if (log.isDebugEnabled()) {
			log.debug("JNDI InitialContext properties:" + hash.size());
		}
		Context lc_context = null;
		try {
			if (hash.size() == 0) {
				lc_context = new InitialContext();
				if (log.isDebugEnabled()) {
					try {
						log.debug("New initial context" + lc_context.getNameInNamespace());
					}catch(NoInitialContextException exc) {
						log.debug("No initial context...");
					}
				}
			} else {
				lc_context = new InitialContext(hash);
				if (log.isDebugEnabled()) {
							log.debug("new Initial context:" + lc_context.getNameInNamespace() + " " + hash.size());
						}
			}
			return lc_context;
		} catch (NamingException e) {
			log.error("Impossible de recuperer le contexte JNDI : ", e);
			throw new JrafConfigException(
				"Impossible de recuperer le contexte JNDI : ",
				e);
		}
	}

	/**
	 * Bind le couple nom/valeur dans le contexte jndi.
	 * @param in_context contexte jndi racine
	 * @param in_name nom
	 * @param in_value valeur
	 * @throws NamingException
	 */
	public static void bind(
		Context in_context,
		String in_name,
		Object in_value)
		throws JrafConfigException {

		if (log.isDebugEnabled()) {
			log.debug("binding : " + in_name);
		}
		String lc_name = in_name;

		try {
			in_context.rebind(lc_name, in_value);
		} catch (NamingException e) {

			try {
				Name n = in_context.getNameParser("").parse(lc_name);
				while (n.size() > 1) {
					String ctxName = n.get(0);

					Context subctx = null;
					try {
						if (log.isDebugEnabled()) {
							log.debug("lookup: " + ctxName);
						}
						subctx = (Context) in_context.lookup(ctxName);
					} catch (NameNotFoundException nfe) {
					}

					if (subctx != null) {
						if (log.isDebugEnabled()) {
							log.debug("Found subcontext: " + ctxName);
						}
						in_context = subctx;
					} else {
						//					log.info("Creating subcontext: " + ctxName);
						in_context = in_context.createSubcontext(ctxName);
					}
					n = n.getSuffix(1);
				}
				if (log.isDebugEnabled()) {
					log.debug("binding: " + n);
				}
				in_context.rebind(n, in_value);
			} catch (NamingException e1) {
				log.error("Impossible de recupere l'objet dans JNDI.", e);
				throw new JrafConfigException(
					"Impossible de recupere l'objet dans JNDI.",
					e);
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("Bound name: " + in_name);
		}
	}

	/**
	 * Recupere un objet dans un contexte jndi
	 * @param in_context contexte jndi d'entre
	 * @param in_name nom du composant a rechercher
	 * @return composant retourne
	 */
	public static Object lookup(Context in_context, String in_name)
		throws JrafConfigException {

		Object lc_value = null;
		if (in_name != null) {
			try {
				lc_value = in_context.lookup(in_name);
			}catch(NoInitialContextException noI)
			{	
				// A ce niveau, ce qui signifie qu'on est pas dans le 
				// cadre d'un serveur d'application.	
				//log.error(""); 
			}
			catch (NameNotFoundException nne) {
				// non jndi non trouve
				// on ne fait rien
				if (log.isDebugEnabled()) {
					log.debug(
						"L'element recherche suivant n'est pas present dans JNDI : "
							+ in_name);
				}

			} catch (NamingException e) {
				log.error(
					"Impossible de retrouver dans JNDI l'element : " + in_name,
					e);
				throw new JrafConfigException(
					"Impossible de retrouver dans JNDI l'element : " + in_name,
					e);
			}
		}
		return lc_value;
	}

	/**
	 * Retourne les proprietes JNDI associees a partir de l'url d'un annuaire et de la factory d'acces
	 * @param jndiUrl url de l'annuaire
	 * @param jndiClass factory d'acces
	 * @return
	 */
	public static Properties getJndiProperties(
		String jndiUrl,
		String jndiClass) {

		Properties properties = new Properties();

		// context factory
		if (jndiClass != null) {
			properties.put(Context.INITIAL_CONTEXT_FACTORY, jndiClass);
		}

		// URL
		if (jndiUrl != null) {
			properties.put(Context.PROVIDER_URL, jndiUrl);
		}

		return properties;
	}

}
