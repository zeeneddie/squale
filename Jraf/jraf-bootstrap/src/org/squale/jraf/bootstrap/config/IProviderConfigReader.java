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
 * Created on Dec 29, 2004
 */
package org.squale.jraf.bootstrap.config;

import java.util.List;

import org.squale.jraf.commons.exception.JrafConfigException;

/**
 * <p>Title : IProviderConfigReader.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * 
 */
public interface IProviderConfigReader {
	
	/**
	 * Lis la configuration de provider
	 * @return List d'element de configuration de provider
	 * @throws JrafConfigException
	 */
	public List readConfig() throws JrafConfigException;

}
