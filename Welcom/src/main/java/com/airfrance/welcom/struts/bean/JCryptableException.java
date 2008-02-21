package com.airfrance.welcom.struts.bean;

/**
 * Classe WCryptableException 
 *
 */
public class JCryptableException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 2651935101132690178L;

    /**
     * Constructeur
     * @param message le message de l'exception
     */
    public JCryptableException(final String message) {
        super(message);
    }
}