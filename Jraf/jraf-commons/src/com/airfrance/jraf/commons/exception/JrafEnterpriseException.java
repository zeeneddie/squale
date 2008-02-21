package com.airfrance.jraf.commons.exception;

/**
 * 
 * <p>Title : JrafEnterpriseException.java</p>
 * <p>Description : Exception de la couche enterprise</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public class JrafEnterpriseException extends JrafException {

    // private static LogAf4J log =  LogAf4J.getInstance(JrafEnterpriseException.class);

    public JrafEnterpriseException(String msg) {
		super(msg);
	}
	
	public JrafEnterpriseException(Throwable root) {
            super(root);
	}
	
	public JrafEnterpriseException(String msg, Throwable root) {
		super(msg, root);			
	}

}
