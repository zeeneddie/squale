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
 * Cree le 21 janv. 05
 */
package org.squale.jraf.bootstrap.locator;

/**
 * <p>Project: JRAF 
 * <p>Title : IProviderLocatorConstants</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2005</p>
 * <p>Company : AIRFRANCE </p>
 */
public interface IProviderLocatorConstants {
	
	/** cle pour retrouver le nom jndi du provider locator dans jndi */
	public final static String PROVIDER_LOCATOR_NAME_KEY =
		"java:comp/env/providerLocator";

	/** cle pour retrouver le nom jndi du provider locator dans jndi */
	public final static String JNDI_URL_KEY = "java:comp/env/jndi.url";

	/** cle pour retrouver le nom de la classe factory d'acces à JNDI */
	public final static String JNDI_CLASSNAME_KEY = "java:comp/env/jndi.class";

}
