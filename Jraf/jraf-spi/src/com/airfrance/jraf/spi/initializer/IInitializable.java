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
 * Créé le 5 mars 04
 */
package com.airfrance.jraf.spi.initializer;

import java.util.Map;

import com.airfrance.jraf.commons.exception.JrafConfigException;
import com.airfrance.jraf.spi.provider.IProvider;

/**
 * <p>Title : IInitializable.java</p>
 * <p>Description : Interface definissant le comportement d'initialisation</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public interface IInitializable {

	/**
	 * Initialisation du Provider à partir des informations retournées
	 * par le socle. 
	 * @param objectInitialize contient la liste des paramètres sous la forme
	 * clé => valeur 
	 * @return IProvider
	 */
	public IProvider initialize(Map objectInitialize)
		throws JrafConfigException;

	
	/**
	 * Initialisation du provider a partir des informations renseignees par le constructeur 
	 * ou par des getter/setter.
	 * @return provider
	 */
	public IProvider initialize() throws JrafConfigException;
}
