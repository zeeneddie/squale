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
package org.squale.jraf.provider.accessdelegate;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafConfigException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org
	.squale
	.jraf
	.provider
	.accessdelegate
	.config
	.IApplicationComponentConfig;
import org
	.squale
	.jraf
	.provider
	.accessdelegate
	.config
	.IApplicationComponentConfigReader;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.jraf.spi.initializer.IInitializableBean;

/**
 * <p>Title : LookUpComponent.java</p>
 * <p>Description : Composant permettant de retrouver des application component</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 * Cette classe met à disposition du applicationComponentDelegate les méthodes qui permettent de
 * créer ou retrouver les instances des businesses de component. Ces méthodes permettent de cacher l'implémentation des
 * components, la technologie utilisée, le fait qu'on utilise ou non des objets distribués, et
 * la gestion éventuelle d'un cache sur ces instances pour optimiser les performances.
*/
public class LookUpComponent
	implements Serializable, IInitializableBean, ILookupComponent {

	/** logger */
	private static final Log log = LogFactory.getLog(LookUpComponent.class);

	/** map d'application component */
	private Map appCompConfigs;

	/** lecteur de fichier de configuration XML */
	/** necessaire pour l'initialisation seulement */
	private IApplicationComponentConfigReader applicationConfigReader;

	/**
	 * Constructeur vide IOC type 2
	 */
	public LookUpComponent() {
	}

	/**
	 * Constructeur IOC type 3 avec lecteur de fichier de configuration XML
	 * @param applicationConfigReader lecteur de fichier de configuration xml
	 */
	public LookUpComponent(IApplicationComponentConfigReader applicationConfigReader) {
		setApplicationConfigReader(applicationConfigReader);
		afterPropertiesSet();
	}

	/**
	 * Constructeur IOC type 3 avec map des application component
	 * @param appCompConfigs map des application component
	 */
	public LookUpComponent(Map appCompConfigs) {
		setAppCompConfigs(appCompConfigs);
		afterPropertiesSet();
	}

	/**
	 * Methode appele apres l'initialisation.
	 * Valide l'initialisation du composant et le met en service.
	 */
	public void afterPropertiesSet() {

		if (getAppCompConfigs() == null) {

			if (getApplicationConfigReader() == null) {
				String message =
					"Probleme de configuration du lookupcomponent : pas de map ni de lecteur de fichier XML.";
				log.fatal(message);
				throw new JrafConfigException(message);
			}

			try {
				setAppCompConfigs(getApplicationConfigReader().readConfig());
			} catch (JrafConfigException e) {
				log.fatal(e.getMessage(), e);
				throw new JrafConfigException(e.getMessage(), e.getCause());

			}

		}

	}
	/**
	 * Method validateService. Retourne la valeur true si le component est valide.
	 * @param applicationcomponent
	 * @param namecomponent
	 * @return boolean
	 */
	public boolean validateService(
		String applicationcomponent,
		String nameService) {
		IApplicationComponentConfig lc_appCompConfig =
			(IApplicationComponentConfig) appCompConfigs.get(
				applicationcomponent);

		if (lc_appCompConfig != null) {
			return lc_appCompConfig.isComponent(nameService);
		} else {
			return false;
		}

	}

	/**
	 * Method findApplicationComponent. Retrouve un applicationComponent et retourne une implémentation
	 * concrète de celui-ci.
	 * @param applicationComponentName
	 * @return IApplicationComponent
	 * @throws JrafEnterpriseException
	 */
	public IApplicationComponent findApplicationComponent(String in_applicationComponentName)
		throws JrafEnterpriseException {

		IApplicationComponent lc_applicationComponent = null;

		IApplicationComponentConfig lc_appConfig = null;
		String lc_className = null;

		lc_appConfig =
			(IApplicationComponentConfig) appCompConfigs.get(
				in_applicationComponentName);

		lc_className = lc_appConfig.getImpl();
		try {
			if (lc_className != null) {

				// chargement de la classe par rapport au classloader du thread courant
				// depuis JRAFv2.1
				// corrige la FDS0311790
				lc_applicationComponent =
					(IApplicationComponent) Class
						.forName(
							lc_className,
							true,
							Thread.currentThread().getContextClassLoader())
						.newInstance();

				lc_applicationComponent.setApplicationComponent(
					in_applicationComponentName);
			} else {
				throw new JrafEnterpriseException(
					"Echec de l'instanciation de l'application component :"
						+ in_applicationComponentName
						+ ". Erreur : Nom de component incorrect");
			}
		} catch (Exception e) {
			throw new JrafEnterpriseException(
				"Echec de l'instanciation de l'application component :"
					+ in_applicationComponentName
					+ " classe :  "
					+ lc_className,
				e);
		}
		return lc_applicationComponent;
	}

	/**
	 * Retourne la map de configuration d'application component
	 * @return map de configuration d'application component
	 */
	public Map getAppCompConfigs() {
		return appCompConfigs;
	}

	/**
	 * Retourne le lecteur de fichier de configuration
	 * @return lecteur de fichier de configuration
	 */
	public IApplicationComponentConfigReader getApplicationConfigReader() {
		return applicationConfigReader;
	}

	/**
	 * Fixe la map de configuration d'application component
	 * @param map map de configuration d'application component
	 */
	public void setAppCompConfigs(Map map) {
		appCompConfigs = map;
	}

	/**
	 * Fixe le lecteur de fichier de configuration
	 * @param reader lecteur de fichier de configuration
	 */
	public void setApplicationConfigReader(IApplicationComponentConfigReader reader) {
		applicationConfigReader = reader;
	}

}
