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
package org.squale.jraf.spi.persistence;

import java.io.Serializable;

import org.squale.jraf.commons.exception.JrafPersistenceException;
import org.squale.jraf.spi.provider.IProvider;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafSpi
 * <p>Title : IPersistenceProvider.java</p>
 * <p>Description : Comportement d'un provider de persistence</p>
 * <p>Copyright : Copyright (c) 2004</p>
 *  
 */

public interface IPersistenceProvider extends IProvider {
	/**
	 * Retourne true si gestion automatique des transactions, false sinon
	 * @return true si gestion automatique des transactions, false sinon
	 */
	public boolean isAutomaticTransaction(); 
	/**
	* Retourne true si les transaction sont gerees par le conteneur, false sinon.
	* @return true si les transaction sont gerees par le conteneur, false sinon.
	*/
	public boolean isContainerManagedTransaction();

	/**
	 * Retourne true si utilisation d'une session longue, false sinon
	 * @return true si utilisation d'une session longue, false sinon
	 */
	public boolean isLongSession();

	/**
	 * Retourne true si utilisation du design pattern thread local session, false sinon
	 * @return true si utilisation du design pattern thread local session, false sinon
	 */
	public boolean isThreadLocalSession();  
	
	/**
	 * Retourne true si l'introspection est utilisee, false sinon
	 * @return true si l'introspection est utilisee, false sinon
	 */
	public boolean isIntrospection();

    /**
     * Fournir une session de type ISession
	 * @return session de type ISession
	 * @throws JrafPersistenceException
	 */
	ISession getSession() throws JrafPersistenceException;


	/**
	 * Fournir un point d'accès aux meta données du mapping.
	 * @return un objet de type IMetaData.
	 */
	IMetaData getMetaData();

 
}
