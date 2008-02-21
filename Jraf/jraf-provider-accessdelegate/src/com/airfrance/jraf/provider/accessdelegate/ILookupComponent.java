/*
 * Created on Jan 7, 2005
 */
package com.airfrance.jraf.provider.accessdelegate;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;

/**
 * <p>Title : ILookupComponent.java</p>
 * <p>Description : Definition du comportement du lookup component.
 * Définit le comportement de recherche de composant et de validation de l'existance d'un composant.</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public interface ILookupComponent {
	/**
	 * Method validateService. Retourne la valeur true si le component est valide.
	 * @param applicationcomponent
	 * @param namecomponent
	 * @return boolean
	 */
	public abstract boolean validateService(
		String applicationcomponent,
		String nameService);
	/**
	 * Method findApplicationComponent. Retrouve un applicationComponent et retourne une implémentation
	 * concrète de celui-ci.
	 * @param applicationComponentName
	 * @return IApplicationComponent
	 * @throws JrafEnterpriseException
	 */
	public abstract IApplicationComponent findApplicationComponent(String in_applicationComponentName)
		throws JrafEnterpriseException;
}