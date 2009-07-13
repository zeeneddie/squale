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
 * Created on Dec 28, 2004
 */
package org.squale.jraf.provider.accessdelegate.config;

import java.io.InputStream;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafConfigException;

/**
 * <p>Title : ClassPathXmlApplicationComponentConfigReader.java</p>
 * <p>Description : Lecteur de fichier de configuration XML a partir du classpath.</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * 
 */
public class ClassPathXmlApplicationComponentConfigReader
	extends AbstractApplicationComponentConfigReader {

	/** logger */
	private static final Log log =
		LogFactory.getLog(ClassPathXmlApplicationComponentConfigReader.class);

	/**
	 * Constructeur IOC type 2
	 */
	public ClassPathXmlApplicationComponentConfigReader() {
	}

	/**
	 * Constructeur IOC type 3
	 */
	public ClassPathXmlApplicationComponentConfigReader(String fileName) {
		setConfigFileName(fileName);
	}

	/**
	 * Lis le fichier de configuration d'un plugin
	 * @param in_fileName chemin du fichier de configuration 
	 */
	public Map readConfig() throws JrafConfigException {

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
	 * @see org.squale.jraf.provider.accessdelegate.config.AbstractApplicationComponentConfigReader#setConfigFileName(java.lang.String)
	 */
	public void setConfigFileName(String string) {
		String path = string;
		if (path != null && path.startsWith("/")) {
			path = path.substring(1);
		}
		super.setConfigFileName(path);
	}

}
