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
 * Créé le 10 mars 04
 */
package org.squale.jraf.helper;

import org.squale.jraf.bootstrap.locator.ProviderLocator;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.spi.accessdelegate.IAccessDelegateProvider;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.jraf.spi.provider.IProviderConstants;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafProviderAccessdelegate
 * <p>Title : AccessDelegateHelper.java</p>
 * <p>Description : Helper pour faciliter la recuperation d'un application comopnent.</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 */
public class AccessDelegateHelper {

	/**
	 * Retourne une instance d'un application component
	 * @param applicationComponentName nom de l'application component
	 * @return application component
	 * @throws JrafEnterpriseException
	 */
	public final static IApplicationComponent getInstance(String applicationComponentName)
		throws JrafEnterpriseException {
		return getAccessDelegateProvider().getInstance(
			applicationComponentName);
	}

	/**
	 * Retourne une instance d'un access delegate provider
	 * @return access delegate provider
	 */
	public final static IAccessDelegateProvider getAccessDelegateProvider() {
		return (IAccessDelegateProvider)
			(ProviderLocator
				.getProvider(IProviderConstants.ACCESS_DELEGATE_PROVIDER_KEY));
	}
}
