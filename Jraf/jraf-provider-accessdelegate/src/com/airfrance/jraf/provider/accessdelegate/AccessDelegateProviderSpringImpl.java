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
 * Créé le 10 janv. 2007
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.jraf.provider.accessdelegate;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import org.squale.jraf.commons.exception.JrafConfigException;
import org.squale.jraf.provider.accessdelegate.config.FileSystemXmlApplicationComponentConfigReader;
import org.squale.jraf.provider.accessdelegate.config.IApplicationComponentConfigReader;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafProviderAccessdelegate
 * <p>Title : AccessDelegateProviderSpringImpl</p>
 * <p>Description : Access delegate provider initializable à partir de Spring</p>
 * <p>Copyright : Copyright (c) 2007</p>
 * <p>Company : AIRFRANCE </p>
 */
public class AccessDelegateProviderSpringImpl
	extends AccessDelegateProviderImpl implements ApplicationContextAware {
		/** logger */
		private static final Log log = LogFactory.getLog(AccessDelegateProviderSpringImpl.class);

		/** Spring context */
		private ApplicationContext applicationContext = null;

		/** root path */
		private String rootPath;

		/** acces delegate relative config path */
		private String configFile;

		/**
		 * @return
		 */
		public ApplicationContext getApplicationContext() {
			return applicationContext;
		}

		/**
		 * @param context
		 */
		public void setApplicationContext(ApplicationContext context) {
			applicationContext = context;
		}

		/**
		 * Initialisation du provider a partir des informations renseignees par le constructeur 
		 * ou par des getter/setter.
		 * @return provider
		 */
		public void initialize() throws JrafConfigException {

			// recuperation du chemin racine
			String lc_rootPath = getRootPath();

			// recuperation du fichier de configuration
			String lc_configFile = getConfigFile();
			String lc_fullPathConfigFile = null;

			if (lc_rootPath == null) {
				if (getApplicationContext() == null) {
					log.fatal(
						"répertoire de configuration non fourni (ROOT_PATH_KEY)");
					throw new JrafConfigException("répertoire de configuration non fourni (ROOT_PATH_KEY)");
				}
				try {
					lc_fullPathConfigFile =
						getApplicationContext()
							.getResource(getConfigFile())
							.getURL()
							.getPath();
				} catch (IOException e) {
					log.fatal(
						"fichier de configuration introuvable");
					throw new JrafConfigException("fichier de configuration introuvable");
				}
				lc_rootPath =
					lc_fullPathConfigFile.substring(
						0,
						lc_fullPathConfigFile.indexOf(lc_configFile));
				setRootPath(lc_rootPath);
			} else {
				lc_fullPathConfigFile =
					lc_rootPath + File.separator + lc_configFile;
			}
			// instanciation d'un lecteur de fichier de configuration
			IApplicationComponentConfigReader reader =
				new FileSystemXmlApplicationComponentConfigReader(
			lc_fullPathConfigFile);

			// instanciation du lookup
			ILookupComponent lookup = buildLookupComponent(reader);

			setLookupComponent(lookup);
			afterPropertiesSet();

		}

		/**
		 * @return
		 */
		public String getRootPath() {
			return rootPath;
		}

		/**
		 * @param string
		 */
		public void setRootPath(String string) {
			rootPath = string;
		}

		/**
		 * @return
		 */
		public String getConfigFile() {
			return configFile;
		}

		/**
		 * @param string
		 */
		public void setConfigFile(String string) {
			configFile = string;
		}
		
		/* (non-Javadoc)
		 * @see org.squale.jraf.provider.accessdelegate.AbstractInitializer#buildLookupComponent(org.squale.jraf.provider.accessdelegate.config.IApplicationComponentConfigReader)
		 */
		protected ILookupComponent buildLookupComponent(IApplicationComponentConfigReader reader) {
			// lookup
			return new LookUpComponent(reader);
		}

}
