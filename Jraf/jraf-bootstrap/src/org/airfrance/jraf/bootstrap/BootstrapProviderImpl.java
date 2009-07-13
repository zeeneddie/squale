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
package org.squale.jraf.bootstrap;

import java.util.List;
import java.util.Map;

import org.squale.jraf.spi.bootstrap.IBootstrapProvider;

/**
 * <p>Title : BootstrapProviderImpl.java</p>
 * <p>Description : Implementation du provider de bootstrap.
 * Provider responsable de l'initialisation de tous les providers d'une application.</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 * @author Eric BELLARD
 */
public class BootstrapProviderImpl implements IBootstrapProvider {

	/** liste pour stocker les configurations de provider */
	private List providers;

	/** map de parametres */
	private Map parameters;

	/**
	 * Constructeur du bootstrap vide IOC type 2
	 */
	public BootstrapProviderImpl() {
		super();
	}

	/**
	 * Constructeur du bootstrap provider avec parametres IOC type 3
	 * @param in_parameters parametres generaux d'initialisation
	 * @param in_providers liste des providers initialises
	 */
	public BootstrapProviderImpl(Map in_parameters, List in_providers) {
		parameters = in_parameters;
	}

	/**
	 * Retourne les parametres pour le provider locator
	 * @return parametres pour le provider locator
	 */
	public Map getParameters() {
		return parameters;
	}

	/**
	 * Retourne les providers
	 * @return providers
	 */
	public List getProviders() {
		return providers;
	}

	/**
	 * Fixe les parametres d'initialisation
	 * @param map
	 */
	public void setParameters(Map map) {
		parameters = map;
	}

	/**
	 * Fixe la liste de configuration de providers
	 * @param list liste des configurations de provider
	 */
	public void setProviders(List list) {
		providers = list;
	}
}
