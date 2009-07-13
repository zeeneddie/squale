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
package org.squale.jraf.provider.logging;

import java.util.Map;

import org.squale.jraf.commons.exception.JrafConfigException;
import org.squale.jraf.spi.initializer.IInitializable;
import org.squale.jraf.spi.provider.IProvider;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafProviderLogging
 * <p>Title : Initializer.java</p>
 * <p>Description : Initializer du provider de logging</p>
 * <p>Copyright : Copyright (c) 2004</p>
 *  
 */
public class Initializer implements IInitializable {

	/**
	 * Constructeur vide IOC 3
	 */
	public Initializer() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.spi.initializer.IInitializable#initialize(java.util.Map)
	 */
	public IProvider initialize(Map objectInitialize)
		throws JrafConfigException {
		return initialize();
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.spi.initializer.IInitializable#initialize()
	 */
	public IProvider initialize() {
		return new LoggingProviderImpl();
	}

}
