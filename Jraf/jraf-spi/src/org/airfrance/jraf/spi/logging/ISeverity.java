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

/**
 * <p>Project: JRAF 
 * <p>Module: jrafSpi
 * <p>Title : ISeverity.java</p>
 * <p>Description : Niveau de severitées pour les logs cexi</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 */

public interface ISeverity {
	/**
	 * Niveau connus de severites 
	 */
	/** niveau inconnu */
	public static String UNKNOWN = "unknown";
	/** niveau normal */
	public static String NORMAL = "normal";
	/** niveau warning */
	public static String WARNING = "warning";
	/** niveau mnior */
	public static String MINOR = "minor";
	/** niveau major */
	public static String MAJOR = "major";
	/** niveau critique */
	public static String CRITICAL = "critical";
	/** niveau debug */
	public static String DEBUG = "debug";

}
