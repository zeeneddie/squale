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
