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
 * Created on Mar 5, 2004
 */
package com.airfrance.jraf.spi.bootstrap;

import java.util.List;
import java.util.Map;

import com.airfrance.jraf.spi.provider.IProvider;

/**
 * <p>Title : IBootstrapProvider.java</p>
 * <p>Description : Provider du bootstrap</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 * @author Eric BELLARD
 */
public interface IBootstrapProvider extends IProvider {

	/**
	 * Retourne les parametres d'initialisation.
	 * @return parametres d'initialisation
	 */
	public Map getParameters();
	
	/**
	 * Fixe la liste des parametres d'initialisation
	 * @param parameters liste des parametres d'initialisation
	 */
	public void setParameters(Map in_parameters);
	
	/**
	 * Retourne les providers initialises
	 * @return providers
	 */
	public List getProviders();

	/**
	 * Fixe la liste de configuration des providers 
	 * @param providers
	 */
	public void setProviders(List in_providers);


}
