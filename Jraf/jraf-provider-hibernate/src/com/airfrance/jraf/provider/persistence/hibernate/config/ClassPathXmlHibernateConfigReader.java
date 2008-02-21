/*
 * Created on Dec 29, 2004
 */
package com.airfrance.jraf.provider.persistence.hibernate.config;

import java.net.URL;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafConfigException;

/**
 * <p>Title : ClassPathXmlHibernateConfigReader.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ClassPathXmlHibernateConfigReader
	extends AbstractHibernateConfigReader {

	/** logger */
	private static final Log log =
		LogFactory.getLog(ClassPathXmlHibernateConfigReader.class);

	/**
	 * Constructeur type IOC2
	 */
	public ClassPathXmlHibernateConfigReader() {
	}

	/**
	 * Constructeur type IOC3
	 * @param configFileName fichier de configuration
	 */
	public ClassPathXmlHibernateConfigReader(String configFileName) {
		setConfigFileName(configFileName);
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.provider.persistence.hibernate.config.IHibernateConfigReader#readConfig()
	 */
	public SessionFactory readConfig(Configuration configuration) throws JrafConfigException {
		URL lc_url = null;

		if (getConfigFileName() == null) {
			throw new JrafConfigException("Probleme de lecture du fichier de configuration : son chemin n'est pas renseigne.");
		}

		lc_url =
			Thread.currentThread().getContextClassLoader().getResource(
				getConfigFileName());

		if (lc_url == null)
			throw new JrafConfigException(
				"Le fichier '"
					+ getConfigFileName()
					+ "' est introuvable dans le classpath");

		return super.readConfig(lc_url, configuration);
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.provider.persistence.hibernate.config.AbstractHibernateConfigReader#setConfigFileName(java.lang.String)
	 */
	public void setConfigFileName(String string) {
		String path = string;
		if (path != null && path.startsWith("/")) {
			path = path.substring(1);
		}
		super.setConfigFileName(path);
	}
}
