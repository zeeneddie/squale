/*
 * Créé le 10 mars 04
 */
package com.airfrance.jraf.helper;

import com.airfrance.jraf.bootstrap.locator.ProviderLocator;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.spi.accessdelegate.IAccessDelegateProvider;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.jraf.spi.provider.IProviderConstants;

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
