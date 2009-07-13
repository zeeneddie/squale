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
 * Created on Mar 5, 2004
 */
package org.squale.jraf.bootstrap.config;

import java.util.Comparator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.spi.provider.IProviderConstants;

/**
 * <p>Title : ProviderConfigComparator.java</p>
 * <p>Description : Objet permettant de trier des configurations de provider</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * 
 * @author Eric BELLARD
 */
public class ProviderConfigComparator implements Comparator {

	/** logger */
	private final static Log log =
		LogFactory.getLog(ProviderConfigComparator.class);

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object arg0, Object arg1) {

		ProviderConfig pc0 = (ProviderConfig) arg0;
		ProviderConfig pc1 = (ProviderConfig) arg1;

		// les identificateurs ne peuvent être null ou vide
		if (pc0.getId() == null
			|| pc1.getId().equals("")
			|| pc1.getId() == null
			|| pc1.getId().equals("")) {
			String message =
				"Les identificateurs de plugin ne peuvent être null ou vide.";
			log.error(message);
			throw new IllegalArgumentException(message);
		}

		// cas avec le provider de bootstrap
		if (isBootStrap(pc0, pc1)) {
			if (isBootStrap(pc0)) {
				return -1;
			} else {
				return 1;
			}
		}

		// cas avec le provider de logging
		else if (isLogging(pc0, pc1)) {

			if (isLogging(pc0)) {
				return -1;

			} else {
				return 1;
			}
		}

		// cas normal - on retourne la comparaison de l'ordre de chargement
		else {

			return pc0.getLoadOnStartup() - pc1.getLoadOnStartup();

		}

	}

	/**
	 * Retourne true si le provider est un bootstrap provider
	 * @param in_config0 configuration de provider
	 * @return true si le provider est un bootstrap provider
	 */
	private boolean isBootStrap(ProviderConfig in_config0) {

		return in_config0.getId().equals(
			IProviderConstants.BOOTSTRAP_PROVIDER_KEY);
	}

	/**
	 * Retourne true si l'un des providers est un bootstrap provider
	 * @param in_config0 configuration de provider
	 * @param in_config1 configuration de provider
	 * @return true si le provider est un bootstrap provider
	 */
	private boolean isBootStrap(
		ProviderConfig in_config0,
		ProviderConfig in_config1) {
		return isBootStrap(in_config0) || isBootStrap(in_config1);
	}

	/**
	 * Retourne true si le provider est un logging provider
	 * @param in_config0 configuration de provider
	 * @return true si le provider est un logging provider
	 */
	private boolean isLogging(ProviderConfig in_config0) {
		return in_config0.getId().equals(
			IProviderConstants.LOGGING_PROVIDER_KEY);
	}

	/**
	 * Retourne true si l'un des providers est un logging provider
	 * @param in_config0 configuration de provider
	 * @param in_config1 configuration de provider
	 * @return true si le provider est un logging provider
	 */
	private boolean isLogging(
		ProviderConfig in_config0,
		ProviderConfig in_config1) {

		return isLogging(in_config0) || isLogging(in_config1);

	}
}
