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
 * Created on Mar 12, 2004
 */
package org.squale.jraf.helper;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;

import org.squale.jraf.bootstrap.locator.*;
import org.squale.jraf.bootstrap.locator.ProviderLocator;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.provider.IProvider;
import org.squale.jraf.spi.provider.IProviderConstants;

/**
 * <p>Title : PersistenceHelper.java</p>
 * <p>Description : Helper pour acceder un provider de persistance</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public class PersistenceHelper {

	/**
	 * Retourne un provider de persistance.
	 * @return provider de persistance
	 */
	public final static IPersistenceProvider getPersistenceProvider() {
		IPersistenceProvider persistenceProvider =
			(IPersistenceProvider) ProviderLocator.getProvider(
				IProviderConstants.PERSISTENCE_PROVIDER_KEY);
		return persistenceProvider;
	}

	/**
	 * Retourne un provider de persistance.
	 * @param id nom du provider a retourner
	 * @return provider de persistance
	 */
	public final static IPersistenceProvider getPersistenceProvider(String id) {
		IPersistenceProvider persistenceProvider =
			(IPersistenceProvider) ProviderLocator.getProvider(id);
		return persistenceProvider;

	}
}
