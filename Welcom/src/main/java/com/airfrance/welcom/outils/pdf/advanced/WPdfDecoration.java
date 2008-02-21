/*
 * Créé le 2 mars 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.pdf.advanced;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WPdfDecoration {
    /** le header*/
    private WIPdfHeaderFooter header;
    /** le footer*/
    private WIPdfHeaderFooter footer;
    /** int */
    private int startDecorationPage = 1;

    /**
     * Constructeur  
     * @param pHeader le header
     * @param pFooter le footer
     */
    public WPdfDecoration(final WIPdfHeaderFooter pHeader, final WIPdfHeaderFooter pFooter) {
        footer = pFooter;
        header = pHeader;
    }

    /**
     * Constructeur 
     *
     */
    public WPdfDecoration() {
        this(null, null);
    }

    /**
     * 
     * @param pdfReader le reader
     * @return le byte array "rempli"
     * @throws DocumentException exception pouvant etre levee
     * @throws IOException exception pouvant etre levee
     */
    public byte[] fill(final PdfReader pdfReader) throws DocumentException, IOException {
        final ByteArrayOutputStream tmpout = new ByteArrayOutputStream();
        fill(pdfReader, tmpout);
        return tmpout.toByteArray();
    }

    /**
     * Met a la decoration du document;
     * @param pdfReader le reader
     * @param out l'outputstream 
     * @throws DocumentException exception pouvant etre levee
     * @throws IOException exception pouvant etre levee
     */
    public void fill(final PdfReader pdfReader, final OutputStream out) throws DocumentException, IOException {
        final int n = pdfReader.getNumberOfPages();
        final PdfStamper stamp = new PdfStamper(pdfReader, out);
        for (int i = 1; i <= n; i++) {
            if (i >= startDecorationPage) {
                //PdfImportedPage page = stamp.getImportedPage (pdfReader, i);
                final PdfContentByte over = stamp.getOverContent(i);
                final Rectangle pageSize = pdfReader.getPageSizeWithRotation(i);

                if (header != null) {
                    header.fill(over, pageSize, i, n);
                }

                if (footer != null) {
                    footer.fill(over, pageSize, i, n);
                }

            }
        }
        stamp.close();
    }

    /**
     * @return footer
     */
    public WIPdfHeaderFooter getFooter() {
        return footer;
    }

    /**
     * @return header
     */
    public WIPdfHeaderFooter getHeader() {
        return header;
    }

    /**
     * @param pFooter footer
     */
    public void setFooter(final WIPdfHeaderFooter pFooter) {
        footer = pFooter;
    }

    /**
     * @param pFooter header
     */
    public void setHeader(final WIPdfHeaderFooter pFooter) {
        header = pFooter;
    }

    /**
     * @return startDecorationPage
     */
    public int getStartDecorationPage() {
        return startDecorationPage;
    }

    /**
     * @param i startDecorationPage
     */
    public void setStartDecorationPage(final int i) {
        startDecorationPage = i;
    }

}
