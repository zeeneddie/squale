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
package org.squale.jraf.provider.persistence.hibernate.config;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.squale.jraf.commons.exception.JrafConfigException;

public class UrlSystemXmlHibernateConfigReader extends
		AbstractHibernateConfigReader {

	
	/** log */
	private static final Log log =
		LogFactory.getLog(UrlSystemXmlHibernateConfigReader.class);

	/**
	 * Constructeur IOC 2
	 *
	 */
	public UrlSystemXmlHibernateConfigReader() {
	}

	/**
	 * Constructeur IOC 3
	 * @param configFileName
	 */
	public UrlSystemXmlHibernateConfigReader(String configFileName) {
		setConfigFileName(configFileName);
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.provider.persistence.hibernate.config.IHibernateConfigReader#readConfig()
	 */
	public SessionFactory readConfig(Configuration configuration) throws JrafConfigException {
		URL lc_file = null;
		try {
			
			if (getConfigFileName() == null) {
				throw new JrafConfigException("Probleme de lecture du fichier de configuration : son chemin n'est pas renseigne.");
			}
			lc_file = new URL(getConfigFileName());
			return super.readConfig(lc_file, configuration);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new JrafConfigException("Probleme de lecture du fichier de configuration : le chemin specifie n'est pas correct.",e);
		}

		//lc_file = new File("C:/Program Files/Apache Software Foundation/Tomcat 5.5/webapps/squaleWeb-3.3/WEB-INF/config/hibernate.cfg.xml");
		//lc_file = new File("/localhost/squaleWeb-3.3/WEB-INF/config/hibernate.cfg.xml");
		
	}

}
