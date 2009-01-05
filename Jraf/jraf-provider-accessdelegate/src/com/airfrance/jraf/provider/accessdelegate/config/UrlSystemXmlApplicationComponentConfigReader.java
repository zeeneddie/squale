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
package com.airfrance.jraf.provider.accessdelegate.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafConfigException;

public class UrlSystemXmlApplicationComponentConfigReader 
	extends AbstractApplicationComponentConfigReader {
	
	/** logger */
	private static final Log log =
		LogFactory.getLog(UrlSystemXmlApplicationComponentConfigReader.class);

	/**
	 * Constructeur IOC type 2
	 */
	public UrlSystemXmlApplicationComponentConfigReader() {
	}

	/**
	 * Constructeur IOC type 3
	 */
	public UrlSystemXmlApplicationComponentConfigReader(String fileName) {
		setConfigFileName(fileName);
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.provider.accessdelegate.config.AbstractApplicationComponentConfigReader#readConfig()
	 */
	/**
	 * Lis le fichier de configuration d'un plugin
	 * @param in_fileName chemin du fichier de configuration 
	 */

	public Map readConfig() throws JrafConfigException {

		InputStream lc_stream = null;
		if (getConfigFileName() == null) {
			String message =
				"Le chemin du fichier de configuration n'est pas renseigne.";
			log.fatal(message);
			throw new JrafConfigException(message);
		}
		URL urlPath= null;

		
		
		try {
			urlPath = new URL(getConfigFileName());
		} catch (MalformedURLException e1) {
			String message =
				"Le chemin donnée pour le fichier de configuration n'est pas une URL.";
			log.fatal(message+getConfigFileName());
			throw new JrafConfigException(message+e1);
		}
		
		try {
			lc_stream = urlPath.openStream();
		} catch (IOException e) {
			String message = "Le fichier"+getConfigFileName()+"est introuvaleble à l'emplacement indiqué";
			log.fatal(message);
			throw new JrafConfigException(message+e);
		}
		return super.readConfig(lc_stream);
		
	}



}
