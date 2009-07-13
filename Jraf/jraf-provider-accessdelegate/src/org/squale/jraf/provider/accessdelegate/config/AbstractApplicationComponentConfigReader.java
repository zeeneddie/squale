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
package org.squale.jraf.provider.accessdelegate.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import org.squale.jraf.commons.exception.JrafConfigException;
import org.squale.jraf.commons.util.DTDEntityResolver;

/**
 * <p>Title : AbstractApplicationComponentConfigReader.java</p>
 * <p>Description : Classe permettant de lire le fichier de configuration 
 * des application component</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * 
 * @author Eric BELLARD
 */
public abstract class AbstractApplicationComponentConfigReader
	extends RuleSetBase
	implements IApplicationComponentConfigReader, Serializable {

	/** logger */
	private static final Log log =
		LogFactory.getLog(AbstractApplicationComponentConfigReader.class);

	/** dtd location */
	private final static String _dtdRegistration[] =
		{
			"-//Squale Software,Inc.//DTD Jraf Configuration 2.0//EN",
			"org/squale/jraf/commons/resources/configApplicationComponent.dtd" };

	protected String configFileName;

	/**
	 * Methode qui declenche la lecture du fichier de configuration
	 * @throws JrafConfigException
	 */
	public abstract Map readConfig() throws JrafConfigException;

	/**
	 * Lis le fichier de configuration d'un plugin
	 * @param in_stream stream pour la lecture du fichier de configuration 
	 */
	public Map readConfig(InputStream in_stream) throws JrafConfigException {

		List configList = new ArrayList();
		Map configMap = new HashMap();

		Digester lc_digester = new Digester();
		lc_digester.setEntityResolver(new DTDEntityResolver(_dtdRegistration));
		lc_digester.addRuleSet(this);
		lc_digester.push(configList);
		try {
			lc_digester.parse(in_stream);
		} catch (IOException ioe) {
			log.fatal("Impossible de lire le fichier ", ioe);
			throw new JrafConfigException(
				"Impossible de lire le fichier ",
				ioe);
		} catch (SAXException saxe) {
			log.fatal("Impossible de parser le fichier ", saxe);
			throw new JrafConfigException(
				"Impossible de parser le fichier ",
				saxe);
		} finally {
			if (in_stream != null) {
				try {
					in_stream.close();
					lc_digester.clear();
				} catch (IOException ie) {
					// trace de niveau debug
					if (log.isDebugEnabled()) {
						log.debug(
							"Probleme lors de la fermeture du flux et du digester: ",
							ie);
					}
				}
			}
		}

		// transformation de la liste de configuration en map de configuration
		Iterator i = configList.iterator();
		while (i.hasNext()) {
			IApplicationComponentConfig conf =
				(IApplicationComponentConfig) i.next();

			configMap.put(conf.getName(), conf);
		}

		// on retourbe la map de configuration
		return configMap;

	}

	/**
	 * Digester configuration
	 */
	public void addRuleInstances(Digester in_digester) {

		// creation d'une configuration de plugin
		in_digester.addObjectCreate(
			"listApplicationComponent/applicationComponent",
			ApplicationComponentConfigImpl.class);

		// fixe les proprietes
		in_digester.addSetProperties(
			"listApplicationComponent/applicationComponent",
			"name",
			"name");
		in_digester.addSetProperties(
			"listApplicationComponent/applicationComponent",
			"description",
			"description");
		in_digester.addSetProperties(
			"listApplicationComponent/applicationComponent",
			"impl",
			"impl");
		in_digester.addSetProperties(
			"listApplicationComponent/applicationComponent",
			"jndiName",
			"jndiName");

		in_digester.addCallMethod(
			"listApplicationComponent/applicationComponent/component",
			"putComponent",
			1);
		in_digester.addCallParam(
			"listApplicationComponent/applicationComponent/component",
			0,
			"name");

		// passe a l'application component suivant
		in_digester.addSetNext(
			"listApplicationComponent/applicationComponent",
			"add",
			ApplicationComponentConfigImpl.class.getName());

	}

	/**
	 * @return
	 */
	public String getConfigFileName() {
		return configFileName;
	}

	/**
	 * @param string
	 */
	public void setConfigFileName(String string) {
		configFileName = string;
	}

}
