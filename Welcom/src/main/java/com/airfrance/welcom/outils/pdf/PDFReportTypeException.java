package com.airfrance.welcom.outils.pdf;

/**
 * @author user
 *
 */
public class PDFReportTypeException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 910799090779541323L;

    /**
     * Procleme sur le report
     * @param arg0 : message
     */
    public PDFReportTypeException(final String arg0) {
        super(arg0);
    }
}