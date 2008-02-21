package com.airfrance.jraf.commons.exception;

/**
 * <p>Title : JrafPersistenceException.java</p>
 * <p>Description : Exception de la couche de persistance.
 * La couche de persistance est une sous-couche de la couche de données.</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public class JrafPersistenceException extends JrafDaoException{

	/**
	 * Constructeur
	 * @param msg message
	 */
    public JrafPersistenceException(String msg) { 
		super(msg);
	}
	
	/**
	 * Constructeur
	 * @param root exception
	 */	
	public JrafPersistenceException(Throwable root) {
            super(root);
	}
	
	/**
	 * Constructeur
	 * @param msg message
	 * @param root exception
	 */
	public JrafPersistenceException(String msg, Throwable root) {
		super(msg, root);
	}
	
}
