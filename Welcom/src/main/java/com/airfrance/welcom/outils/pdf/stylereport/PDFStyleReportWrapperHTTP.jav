package com.airfrance.welcom.outils.pdf.stylereport;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.welcom.outils.pdf.PDFGenerateurException;

/**
 * 
 * @author M327837
 * @deprecated
 */ 
public class PDFStyleReportWrapperHTTP extends PDFStyleReportWrapper{
    /** logger */
    private static Log log = LogFactory.getLog(PDFStyleReportWrapperHTTP.class);

    /** Content Type */
    protected final static String CONTENT_TYPE = "application/pdf";
  
    /** nom de l'attachement par defaut */
    protected String attachementFileName="rapport.pdf";
    
    /** Response pour la sortie du flux */
    protected HttpServletResponse response = null;

    /**
     * @param pResponse Reponse pour ecrire le flux PDF
     */
    public PDFStyleReportWrapperHTTP(final HttpServletResponse pResponse) {
        super();
        this.response = pResponse;
    }

    /**
     * @see com.airfrance.welcom.outils.pdf.PDFGenerateurHTTP#setFileNameAttachement(java.lang.String)
     */
    public void setFileNameAttachement(final String filename) {
        attachementFileName=filename;
    }


    /**
     * Impression dans la stream du document pdf
     * @throws PDFGenerateurException : Probleme a la generetion
     */
    public void printPDF() throws PDFGenerateurException {
        // Initilise les valeur du Header http
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Content-Disposition", "attachment;filename="+attachementFileName+";");

        // Ecrit le resultat dans le response
        try {
            final ServletOutputStream flux_sortant = response.getOutputStream();
            setOutputStream(flux_sortant);
            super.printPDF();
            response.setContentLength(outputSize);
            flux_sortant.flush();
            flux_sortant.close();
        } catch (final Exception e) {
            log.error(e,e);
            throw new PDFGenerateurException(e.getMessage());
        }
    }
}