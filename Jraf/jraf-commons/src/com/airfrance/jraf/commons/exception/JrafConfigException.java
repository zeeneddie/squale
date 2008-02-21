package com.airfrance.jraf.commons.exception;

/**
 * <p>Title : JrafConfigException.java</p>
 * <p>Description : Exception runtime de la couche configuration</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public class JrafConfigException extends JrafRuntimeException {

	public JrafConfigException(String msg) {
		super(msg);
	}

	public JrafConfigException(Throwable root) {
		super(root);
	}

	public JrafConfigException(String msg, Throwable root) {
		super(msg, root);
	}

}
