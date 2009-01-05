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
package com.airfrance.jraf.bootstrap.config;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafConfigException;

/**
 * <p>Title : ClassPathXmlProviderConfigReader.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ClassPathXmlProviderConfigReader
	extends AbstractProviderConfigReader {

	/** logger */
	private static Log log =
		LogFactory.getLog(ClassPathXmlProviderConfigReader.class);

	/**
	 * Constructeur vide type IOC 2
	 */
	public ClassPathXmlProviderConfigReader() {
	}

	/**
	 * Constructeur avec parametre type IOC 3
	 * @param configFileName fichier de configuration
	 */
	public ClassPathXmlProviderConfigReader(String configFileName) {
		setConfigFileName(configFileName);
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.bootstrap.config.IProviderConfigReader#readConfig()
	 */
	public List readConfig() throws JrafConfigException {

		InputStream lc_stream = null;

		try {
			lc_stream =
				Thread
					.currentThread()
					.getContextClassLoader()
					.getResourceAsStream(
					getConfigFileName());

			if (lc_stream == null)
				throw new JrafConfigException(
					"Le fichier '"
						+ getConfigFileName()
						+ "' est introuvable dans le classpath");

			return super.readConfig(lc_stream);
		} catch (Exception e) {
			log.fatal(
				"Le fichier '"
					+ getConfigFileName()
					+ "' est introuvable dans le classpath",
				e);
			throw new JrafConfigException(
				"Le fichier specifie est introuvable",
				e);
		}

	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.bootstrap.config.AbstractProviderConfigReader#setConfigFileName(java.lang.String)
	 */
	public void setConfigFileName(String string) {
		String path = string;
		if (path != null && path.startsWith("/")) {
			path = path.substring(1);
		}
		super.setConfigFileName(path);
	}

}
