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
 * Created on Jan 7, 2005
 */
package org.squale.jraf.provider.accessdelegate;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;

/**
 * <p>Title : ILookupComponent.java</p>
 * <p>Description : Definition du comportement du lookup component.
 * Définit le comportement de recherche de composant et de validation de l'existance d'un composant.</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * 
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