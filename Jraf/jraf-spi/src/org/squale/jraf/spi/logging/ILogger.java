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
package org.squale.jraf.spi.logging;

import org.apache.commons.logging.Log;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafSpi
 * <p>Title : ILogger.java</p>
 * <p>Description : Logger JRAF</p>
 * <p>Copyright : Copyright (c) 2004</p>
 *  
 */

public interface ILogger extends Log {

	/**
	 * Ecriture d'une trace au format CEXI.
	 * Cette operation est optionelle.
	 * @throws UnsupportedOperationException si l'operation n'est pas implementee
	 */
	public abstract void logCexi(
		int code,
		String severity,
		String group,
		String sourceComposant,
		String message);
}