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
 * Créé le 10 mars 04
 */
package com.airfrance.jraf.commons.exception;

import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafCommons
 * <p>Title : JrafRuntimeException.java</p>
 * <p>Description : Runtime-Exception JRAF</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 */
public class JrafRuntimeException extends NestableRuntimeException {

	/**
	 * 
	 */
	public JrafRuntimeException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public JrafRuntimeException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public JrafRuntimeException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public JrafRuntimeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
