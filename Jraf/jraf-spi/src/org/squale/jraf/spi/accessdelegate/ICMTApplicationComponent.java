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
 * Created on Dec 24, 2004
 */
package org.squale.jraf.spi.accessdelegate;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;

/**
 * <p>Title : ICMTApplicationComponent.java</p>
 * <p>Description : Comportement d'un CMTApplicationComponent</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public interface ICMTApplicationComponent extends IApplicationComponent {

	/**
	 * Retourne un application component.
	 * Si il est de type ICMTApplicationComponent sa session est renseigne pour qu'il s'integre dans la transaction courante.
	 * @param in_name nom de l'application component
	 * @return CMTApplicationComponent
	 * @throws JrafEnterpriseException
	 */
	public IApplicationComponent getCompositeApplicationComponent(String in_name)
		throws JrafEnterpriseException;

	/**
	 * Retourne la session de la transaction courante
	 * @return session de la transaction courante
	 */
	public ISession getSession();
	
}
