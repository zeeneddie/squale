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
