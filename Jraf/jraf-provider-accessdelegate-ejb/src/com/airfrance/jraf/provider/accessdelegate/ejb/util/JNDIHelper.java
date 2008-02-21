package com.airfrance.jraf.provider.accessdelegate.ejb.util;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafConfigException;

/**
 * <p>Title : JNDIHelper.java</p>
 * <p>Description : Helper pour récupérer des EJBs dans JNDI</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public class JNDIHelper {

	/** logger */
	private static final Log log = LogFactory.getLog(JNDIHelper.class);

	/** jndi lookup root */
	private final static String JNDI_LOOKUP_ROOT = "java:comp/env/";

	/** singleton instance */
	private static JNDIHelper _instance = null;

	/** jndi initial context */
	private InitialContext initContext = null;

	/**
	 * Constructeur privee
	 * (Design pattern singleton)
	 */
	private JNDIHelper() {
		try {
			initContext = new InitialContext();
		} catch (Exception e) {
			log.error("Impossible d'initialiser le contexte", e);
		}
	}

	/**
	 * Retourne un ejb local a partir de son nom.
	 * @param EJBHomeName ejb home name
	 * @return Object
	 * Les EJBs sont recuperes via des references EJB, i.e cela commence par JNDI_LOOKUP_ROOT
	 * Cette methode fonctionne seulement pour les EJB Local.
	 * Modification de la signature depuis JRAFv2.1
	 */
	public Object lookupLocalHome(String EJBHomeName)
		throws JrafConfigException {
		try {
			return initContext.lookup(JNDI_LOOKUP_ROOT + EJBHomeName);
		} catch (Exception e) {
			log.error("Impossible de localiser l'EJB", e);
			throw new JrafConfigException(
				"Impossible de localiser l'EJB",
				e);
		}
	}

	/**
	 * Cree un singleton
	 * @return singleton
	 */
	public static JNDIHelper singleton() {
		if (_instance == null) {
			_instance = new JNDIHelper();
		}
		return _instance;
	}

}
