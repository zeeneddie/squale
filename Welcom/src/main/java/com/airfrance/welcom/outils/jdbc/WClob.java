package com.airfrance.welcom.outils.jdbc;

import java.io.ByteArrayInputStream;

/**
 * @author user
 *
 * @deprecated
 */
public class WClob {
    
    /** Represtation interne du clob */
    private String clob = null;

    /** Constreur privé inacessible */
    private WClob() {
    }

    /** 
     * Constructeur d'un clob
     * @param pClob Chaine du clob : permet de depassé la limite de 4000 Char
     */
    public WClob(final String pClob) {
        this.clob = pClob;
    }

    /**
     * 
     * @return la longuer de la chaine
     */
    public int getLength() {
        return clob.length();
    }

    /**
     * 
     * @return Transforme la chaine en une inputStream
     */
    public ByteArrayInputStream getInputStream() {
        return new ByteArrayInputStream(clob.getBytes());
    }
}