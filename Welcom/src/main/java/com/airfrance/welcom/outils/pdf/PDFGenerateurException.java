package com.airfrance.welcom.outils.pdf;

/**
 * @author user
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class PDFGenerateurException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = -2132430315750432616L;

    /** 
     * Probleme sur la generation du PDF 
     * @param arg0 : Message
     * */
    public PDFGenerateurException(final String arg0) {
        super(arg0);
    }
}