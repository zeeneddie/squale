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
import java.net.URL;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafConfigException;
import org.squale.jraf.commons.exception.JrafRuntimeException;

/**
 * <p>Title : AbstractHibernateConfigReader.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * 
 */
public abstract class AbstractHibernateConfigReader
	implements IHibernateConfigReader {

	private String configFileName;

	/** log */
	private static final Log log =
		LogFactory.getLog(AbstractHibernateConfigReader.class);

	/**
	 * Lecture du fichier de configuration
	 */
	public abstract SessionFactory readConfig(Configuration configuration) throws JrafConfigException;

	/**
	 * Lecture du fichier de configuration a partir d'une URL
	 * @param in_url url
	 * @return session factory hibernate
	 */
	public SessionFactory readConfig(URL in_url, Configuration configutration) {

		SessionFactory sf = null;
		Configuration configuration = new Configuration();
		try {
			sf = configuration.configure(in_url).buildSessionFactory();
		} catch (HibernateException e) {
			String hibernateProblem = "Probleme d'initialisation hibernate.";
			log.fatal(hibernateProblem, e);
			throw new JrafRuntimeException(hibernateProblem, e);
		}
		// depuis JRAF 2.2 : permet d'afficher un message en cas d'exception hibernate speciale:
		// probleme de cache...
		catch (Throwable t) {
			String hibernateProblem = "Probleme d'initialisation hibernate.";
			log.fatal(hibernateProblem, t);
			throw new JrafRuntimeException(hibernateProblem, t);
		}

		return sf;

	}

	/**
	 * Lecture du fichier de configuration a partir d'un fichier
	 * @param in_file fichier
	 * @return session factory hibernate
	 */
	public SessionFactory readConfig(File in_file, Configuration configuration) {

		SessionFactory sf = null;
		try {
			sf = configuration.configure(in_file).buildSessionFactory();
		} catch (HibernateException e) {
			String hibernateProblem = "Probleme d'initialisation hibernate.";
			log.fatal(hibernateProblem, e);
			throw new JrafRuntimeException(hibernateProblem, e);
		}
		// depuis JRAF 2.2 : permet d'afficher un message en cas d'exception hibernate speciale:
		// probleme de cache...
		catch (Throwable t) {
			String hibernateProblem = "Probleme d'initialisation hibernate.";
			log.fatal(hibernateProblem, t);
			throw new JrafRuntimeException(hibernateProblem, t);
		}
		return sf;
	}

	/**
	 * Renvoie le fichier de config.
	 * @return configFileName
	 */
	public String getConfigFileName() {
		return configFileName;
	}

	/**
	 * Ajoute le nom du fichier de config.
	 * @param string
	 */
	public void setConfigFileName(String string) {
		configFileName = string;
	}
}
