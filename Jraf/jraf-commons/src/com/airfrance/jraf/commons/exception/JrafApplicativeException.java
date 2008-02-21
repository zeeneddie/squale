package com.airfrance.jraf.commons.exception;

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
