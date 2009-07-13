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
 * Created on Apr 2, 2004
 */
package org.squale.jraf.bootstrap.test;

import java.util.List;
import java.util.Map;

import org.squale.jraf.spi.bootstrap.IBootstrapProvider;

/**
 * <p>Title : BootstrapProviderMock.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * 
 */
public class BootstrapMockProvider implements IBootstrapProvider {

	/* (non-Javadoc)
	 * @see org.squale.jraf.bootstrap.IBootstrapProvider#getParameters()
	 */
	public Map getParameters() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.bootstrap.IBootstrapProvider#getProviders()
	 */
	public List getProviders() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.bootstrap.IBootstrapProvider#setProviders(java.util.List)
	 */
	public void setProviders(List in_providers) {

	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.spi.bootstrap.IBootstrapProvider#setParameters(java.util.Map)
	 */
	public void setParameters(Map in_parameters) {

	}
}
