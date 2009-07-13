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

import java.util.Map;

import org.squale.jraf.commons.exception.JrafConfigException;
import org.squale.jraf.spi.bootstrap.IBootstrapProvider;
import org.squale.jraf.spi.initializer.IInitializable;
import org.squale.jraf.spi.initializer.IInitializableBean;
import org.squale.jraf.spi.provider.IProvider;

/**
 * <p>Title : BootstrapInitializer.java</p>
 * <p>Description : Initializer du bootstrap</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 * @author Eric BELLARD
 */
public class BootstrapInitializer
	implements IInitializable, IInitializableBean {

	/** map de parametres */
	private Map initParams;

	/**
	 * Constructeur vide IOC type 2
	 */
	public BootstrapInitializer() {
		super();
	}

	/**
	 * Constructeur vide IOC type 2
	 */
	public BootstrapInitializer(Map params) {
		super();
		setInitParams(params);

	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.spi.initializer.IInitializable#initialize(java.util.Map)
	 */
	public IProvider initialize(Map initParams) throws JrafConfigException {
		setInitParams(initParams);
		afterPropertiesSet();
		return initialize();
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.spi.initializer.IInitializable#initialize()
	 */
	public IProvider initialize() {
		// creation du bootstrap 
		IBootstrapProvider lc_bootstrapProvider = new BootstrapProviderImpl();
		lc_bootstrapProvider.setParameters(getInitParams());

		return lc_bootstrapProvider;
	}

	/**
	 * Retourne la map de parametres
	 * @return map de parametres
	 */
	public Map getInitParams() {
		return initParams;
	}

	/**
	 * @param map
	 */
	public void setInitParams(Map map) {
		initParams = map;
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.spi.initializer.IInitializableBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		// rien a verifier
		// les parametres d'initialisation peuvent être null
	}
}
