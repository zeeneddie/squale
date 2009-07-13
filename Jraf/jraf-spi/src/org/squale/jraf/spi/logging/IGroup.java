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
 * <p>Title : IGroup.java</p>
 * <p>Description : Groupes connus pour les log cexi</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 */

public interface IGroup {
	/**
	 * Groupes connus
	 */
	public static String APPLICATION = "Application";
	public static String OS = "OS";
	public static String COMMUNICATION = "Communication";
	public static String BACKUP = "Backup";
	public static String SECURITY = "Security";
	public static String DATASECURITY = "DateSecurity";
	public static String CONFIGURATION = "Configuration";
	public static String GLOBAL_SERVICES = "Global Services";
	public static String HARDWARE = "Hardware";
	public static String MAIL = "Mail";
	public static String NETWORK = "Network";
	public static String ROUTING = "Rooting";
	public static String OPC = "OpC";
	public static String DATABASE = "Database";
	public static String OUTPUT = "Output";
	public static String PERFORMANCE = "Performance";
	public static String STATUS = "Status";
	public static String MISC = "Misc";

}
