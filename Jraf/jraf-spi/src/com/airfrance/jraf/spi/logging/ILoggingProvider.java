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
package com.airfrance.jraf.spi.logging;

import org.apache.commons.logging.Log;

import com.airfrance.jraf.spi.provider.IProvider;

 
/**
 * <p>Project: JRAF 
 * <p>Module: jrafSpi
 * <p>Title : ILoggingProvider.java</p>
 * <p>Description : Interface definissant le comportement du provider de logging</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 */

public interface ILoggingProvider extends IProvider   {
	/**
	 * Retourne un logger jraf
	 * @param clazz classe a logger
	 * @return logger
	 */
	ILogger getInstance(Class clazz);
	
	/**
	 * Retourne un logger jraf
	 * @param clazz nom de la classe a logger
	 * @return logger
	 */
	ILogger getInstance(String clazz);
}