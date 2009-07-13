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
 * Created on Jul 23, 2004
 */
package org.squale.jraf.testenv.integrationlayer;

import org.squale.jraf.commons.exception.JrafEnterpriseException;

/**
 * <p>Title : ITestLauncher.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public interface ITestLauncher  {
	
	/** marque le nom du projet pour le build ant */	
	public final static String TYPE_EXT = "type.ext";

	/** marque l'emplacement de la vue clearcase des projets pour le build ant */	
	public final static String CC_PROJECT_DIR = "cc.project.dir";
	
	
	/**
	 * Lance l'execution des tests
	 * @throws JrafEnterpriseException
	 */
	public abstract void execute() throws JrafEnterpriseException;

}