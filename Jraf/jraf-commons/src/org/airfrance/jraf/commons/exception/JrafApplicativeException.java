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
package org.squale.jraf.commons.exception;

/**
 * <p>Title : JrafApplicativeException.java</p>
 * <p>Description : Exception de la couche applicative</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public class JrafApplicativeException extends JrafException {

	public JrafApplicativeException() {
		super();
	}

	/**
	 * @param root
	 */
	public JrafApplicativeException(Throwable root) {
		super(root);
	}

	/**
	 * @param msg
	 * @param root
	 */
	public JrafApplicativeException(String msg, Throwable root) {
		super(msg, root);
	}

	public JrafApplicativeException(String msg) {
		super(msg);
	}
}
