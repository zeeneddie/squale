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
package org.squale.jraf.commons.util;

import java.io.InputStream;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafCommons
 * <p>Title : DTDEntityResolver.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 */
public class DTDEntityResolver implements EntityResolver {

	private String _dtdRegistration[] = null;

	/**
	 * 
	 */
	public DTDEntityResolver() {
		super();
		// TODO Raccord de constructeur auto-généré
	}

	public DTDEntityResolver(String _dtdRegistration[]) {
		this();
		this._dtdRegistration = _dtdRegistration;
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
	 */
	public InputSource resolveEntity(String publicId, String systemId) {
		if (_dtdRegistration == null) {
			return null;
		}
		if (publicId != null && publicId.equals(_dtdRegistration[0])) {

			ClassLoader classLoader = this.getClass().getClassLoader();
			InputStream dtdStream =
				classLoader.getResourceAsStream(_dtdRegistration[1]);
			if (dtdStream == null) {
				return null;
			} else {
				InputSource source = new InputSource(dtdStream);
				source.setPublicId(publicId);
				source.setSystemId(systemId);
				return source;
			}
		} else {
			// fonctionnement par défaut
			return null;
		}
	}

}
