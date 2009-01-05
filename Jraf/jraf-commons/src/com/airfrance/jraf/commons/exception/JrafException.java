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
package com.airfrance.jraf.commons.exception;

import org.apache.commons.lang.exception.NestableException;

/**
 * <p>Title : JrafException.java</p>
 * <p>Description : Exception JRAF mere</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public class JrafException extends NestableException {
	/**
	 * ajoute le message.
	 * @param msg
	 */
	public JrafException(String msg) {
		super(msg);
	}
	
	/**
	 * ajoute le Throwable.
	 * @param root
	 */
	public JrafException(Throwable root) {
		super(root);
	}

	/**
	 *
	 */
	public JrafException() {
		super();
	}
	
	/**
	 * Ajoute le message et le Throwable.
	 * @param msg
	 * @param root
	 */
	public JrafException(String msg, Throwable root) {
		super(msg, root);
	}
}
