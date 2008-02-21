package com.airfrance.jraf.commons.util;

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
