package com.airfrance.welcom.outils.pdf.advanced;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.SimpleBookmark;

/*
 * Créé le 10 nov. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WPdfSummary {
    /** le reader */
    private PdfReader pdfReader = null;
    /** format A4 */
    private final Rectangle rectA4 = new Rectangle(550, 842);

    /**
     * 
     * @param pPdfReader le reader
     */
    public WPdfSummary(final PdfReader pPdfReader) {
        pdfReader = pPdfReader;
    }

    /**
     * 
     * @return liste de favoris
     */
    private List getBookmark() {
        return SimpleBookmark.getBookmark(pdfReader);
    }

    /**
     * 
     * @param doc le document 
     * @throws DocumentException exception pouvant etre levee
     */
    private void createSummary(final Document doc) throws DocumentException {

        //Document doc = new Document(A4);
        doc.open();
        //doc.newPage();

        // PLace de header
        doc.add(new Paragraph("a"));
        doc.add(new Paragraph("a"));

        final Paragraph p = new Paragraph("SOMMAIRE");
        p.setAlignment(Element.ALIGN_CENTER);
        doc.add(p);

        final List list = getBookmark();
        if (list != null) {
            final Iterator it = list.iterator();
            while (it.hasNext()) {
                final HashMap h = (HashMap) it.next();
                final Hashtable ht = new Hashtable(h);

                final String anchor = (String) ht.get("Page");
                final int page = Integer.parseInt(anchor.substring(0, anchor.indexOf(' ')));
                doc.add(new Paragraph((String) ht.get("Title") + "............................ page " + (page + 1)));

                /*Anchor anchor = new Anchor((String)ht.get("Title"));
                //anchor1.set
                anchor.setReference("#2" );
                doc.add(anchor);
                */

                /*final Enumeration enumeration = ht.keys();
                while (enumeration.hasMoreElements()) {
                    final String key = (String) enumeration.nextElement();
                }*/
            }
        }

        doc.close();
        //return doc;
    }

    /**
     * 
     * @return le doc rempli
     * @throws DocumentException exception pouvant etre levee
     */
    public byte[] fill() throws DocumentException {

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final Document doc = new Document(rectA4);
        final PdfWriter pdfWriter = PdfWriter.getInstance(doc, out);
        createSummary(doc);

        return out.toByteArray();

    }

}
