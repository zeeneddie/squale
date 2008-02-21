/*
 * Created on Dec 29, 2004
 */
package com.airfrance.jraf.provider.persistence.hibernate.config;

import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafConfigException;

/**
 * <p>Title : FileSystemXmlHibernateConfigReader.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public class FileSystemXmlHibernateConfigReader
	extends AbstractHibernateConfigReader {

	/** log */
	private static final Log log =
		LogFactory.getLog(FileSystemXmlHibernateConfigReader.class);

	/**
	 * Constructeur IOC 2
	 *
	 */
	public FileSystemXmlHibernateConfigReader() {
	}

	/**
	 * Constructeur IOC 3
	 * @param configFileName
	 */
	public FileSystemXmlHibernateConfigReader(String configFileName) {
		setConfigFileName(configFileName);
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.provider.persistence.hibernate.config.IHibernateConfigReader#readConfig()
	 */
	public SessionFactory readConfig(Configuration configuration) throws JrafConfigException {
		File lc_file = null;

		if (getConfigFileName() == null) {
			throw new JrafConfigException("Probleme de lecture du fichier de configuration : son chemin n'est pas renseigne.");
		}
		lc_file = new File(getConfigFileName());

		if (lc_file == null || !lc_file.exists()) {
			throw new JrafConfigException("Probleme de lecture du fichier de configuration : le chemin specifie n'est pas correct.");
		}

		return super.readConfig(lc_file, configuration);
	}

}
