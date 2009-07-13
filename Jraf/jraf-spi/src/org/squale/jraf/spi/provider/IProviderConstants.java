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
 * Created on Mar 9, 2004
 */
package org.squale.jraf.spi.provider;

/**
 * <p>Title : IProviderConstants.java</p>
 * <p>Description : Constantes communes aux providers</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * 
 */
public interface IProviderConstants {

	/** cle pour retrouver le provider de logging */
	public final static String BOOTSTRAP_PROVIDER_KEY = "bootstrap";

	/** cle pour retrouver le provider de logging */
	public final static String LOGGING_PROVIDER_KEY = "logging";
	
	/** cle pour retrouver le provider de persistence */
	public final static String PERSISTENCE_PROVIDER_KEY = "persistence";

	/** cle pour retrouver le provider d'access */
	public final static String ACCESS_DELEGATE_PROVIDER_KEY = "accessdelegate";

	/** cle pour retrouver le provider adhesion */
	public final static String ADHESION_PROVIDER_KEY = "adhesion";

}
