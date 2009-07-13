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
 * Created on Dec 29, 2004
 */
package org.squale.jraf.provider.persistence.hibernate.config;

import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafConfigException;

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
	 * @see org.squale.jraf.provider.persistence.hibernate.config.IHibernateConfigReader#readConfig()
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
