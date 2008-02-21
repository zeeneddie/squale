package com.airfrance.welcom.outils.pdf.stylereport;

import inetsoft.report.ReportElement;
import inetsoft.report.ReportSheet;
import inetsoft.report.StyleConstants;
import inetsoft.report.StyleFont;
import inetsoft.report.StyleSheet;
import inetsoft.report.TabularSheet;
import inetsoft.report.io.Builder;
import inetsoft.report.pdf.PDF3Printer;

import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.airfrance.welcom.outils.pdf.PDFData;
import com.airfrance.welcom.outils.pdf.PDFGenerateur;
import com.airfrance.welcom.outils.pdf.PDFGenerateurException;
import com.airfrance.welcom.outils.pdf.PDFReportTypeException;
import com.airfrance.welcom.outils.pdf.advanced.WPdfDecoration;

/**
 * @author user
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class PDFStyleReportWrapper implements PDFGenerateur {
    
    /** Le  pdf data*/
    private PDFData pdfData;

    /** Longeur de page */
    private static final double PAGE_A4_LONGUEUR = 11.692913F;
    /** Largeur de page  */
    private static final double PAGE_A4_LARGEUR = 8.267716F;

    /** Constante pour une orientation Portrait */
    public static final String ORIENTATION_PORTRAIT = "portrait";

    /** Constance pour une orientation Paysage */
    public static final String ORIENTATION_PAYSAGE = "paysage";

    /** Format d'affichage de la date */
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
    
    /**Writer PDF*/
    private PDF3Printer fichier=null;

    /** Report */
    private ReportSheet report = null;

    /** Oriention par default */
    private String orientation = ORIENTATION_PORTRAIT;

    /** Stream de sortie pour la generation */
    private OutputStream os = null;

    /** font */
    private StyleFont font;
    /** font gras*/
    private StyleFont boldFont;
    /** font souligné*/
    private StyleFont underlinedFont;
    /** font petite*/
    private StyleFont smallFont;
    /** font size*/
    private int fontSize = 0;
    /** Taille du buffer*/
    protected int outputSize = 0;

    /**
     * Chargement du template
     * @param is : Liste des InputStream
     * @throws PDFGenerateurException : Leve l'exception si le fichier est null
     */
    public void loadTemplate(final InputStream is) throws PDFGenerateurException {
        if (is == null) {
            throw new PDFGenerateurException("Le fichier template est null");
        }

        try {
            final Builder builder = Builder.getBuilder(Builder.TEMPLATE, is);
            report = builder.read(".");

            if (fontSize != 0) {
                final float newSize = fontSize;
                font = new StyleFont(report.getCurrentFont().deriveFont(newSize));
                smallFont = new StyleFont(report.getCurrentFont().deriveFont(newSize - 2));
                report.setCurrentFont(font);
            } else {
                font = new StyleFont(report.getCurrentFont());

                final float newSize = font.getSize();
                smallFont = new StyleFont(report.getCurrentFont().deriveFont(newSize));
            }

            boldFont = new StyleFont(font.deriveFont(Font.BOLD));

            underlinedFont = new StyleFont(font.getName(), Font.BOLD | StyleFont.UNDERLINE, font.getSize(), StyleConstants.THIN_LINE);
        } catch (final IOException e) {
            throw new PDFGenerateurException("Fichier incorrect pour la generation du PDF : " + e.getMessage());
        }
    }

    /**
     * Affecte la date a l'element nomé : TextDate dans le template
     * @param texte : date
     * @throws PDFGenerateurException Probleme a l'ecriture
     */
    public void setTexteDate(final String texte) throws PDFGenerateurException {
        final String strDate = texte + ": " + sdf.format(new Date());

        setElement("TextDate", strDate);
    }

    /**
     * Affecte le titre a l'element nommé : TextTitre dans le template
     * @param texte Titre
     * @throws PDFGenerateurException Probleme a l'ecriture
     */
    public void setTitre(final String texte) throws PDFGenerateurException {
        final String strTitre = texte + " {P} / {N} ";
        setElement("TextTitre", strTitre);
    }

    /**
     * Affecte l'element nommé dans le template
     * @param elementName : Nom de l'element a affecté dans le Template
     * @param object object a affecté
     * @throws PDFGenerateurException Probleme a l'ecriture
     */
    public void setElement(final String elementName, final Object object) throws PDFGenerateurException {
        if (report == null) {
            throw new PDFGenerateurException("Aucun template de charger, effectuer un loadTemplate avant");
        }

        report.setElement(elementName, object);
    }

    /**
     * Spécifie le flux de sortie
     * @param pOs : Flux
     */
    public void setOutputStream(final OutputStream pOs) {
        this.os = pOs;
    }

    /**
     * fabrication du rapport et envoi du rapport dans le outputStream
     * @throws PDFGenerateurException : Verifie si le template a charger ou stream nulle
     */
    public void printPDF() throws PDFGenerateurException {
        
        
        if (report == null) {
            throw new PDFGenerateurException("Aucun template de charger, effectuer un loadTemplate avant");
        }

        if (os == null) {
            throw new PDFGenerateurException("Le stream de sortie est null sur le print PDF");
        }

        fichier = new PDF3Printer(os);

        if ((orientation != null) && orientation.equals(ORIENTATION_PAYSAGE)) {
            fichier.setPageSize(PAGE_A4_LONGUEUR, PAGE_A4_LARGEUR);
        } else {
            fichier.setPageSize(PAGE_A4_LARGEUR, PAGE_A4_LONGUEUR);
        }

        outputSize = fichier.getOutputSize();
        report.print(fichier.getPrintJob());
        fichier.close();
    }

    /**
     * Ajouter un element dans une cellule
     * @param col Colonne
     * @param row Ligne
     * @param re Element du report
     * @throws PDFReportTypeException Impossible d'ajouter ce type d'element
     */
    public void addElement(final int col, final int row, final ReportElement re) throws PDFReportTypeException {
        if (report instanceof StyleSheet) {
            final TabularSheet ts = (TabularSheet) report;
            ts.addElement(col, row, re);
        } else {
            throw new PDFReportTypeException("La methode addElement(int,int,ReportElement) ne s'utilise que sur une TabularSheet");
        }
    }

    /**
     * Ajoute un element
     * @param re : Ajote un element de report
     * @throws PDFReportTypeException : Impossible d'ajouter ce type d'element
     */
    public void addElement(final ReportElement re) throws PDFReportTypeException {
        if (report instanceof StyleSheet) {
            final StyleSheet ss = (StyleSheet) report;
            ss.addElement(re);
        } else {
            throw new PDFReportTypeException("La mathode addElement(ReportElement) ne s'utilise que sur une StyleSheet");
        }
    }

    /**
     * Spécifie l'orientation ...
     * @param pOrientation : ORIENTATION_PORTRAIT et ORIENTATION_PAYSAGE
     */
    public void setOrientation(final String pOrientation) {
        this.orientation = pOrientation;
    }

    /**
     * @return font gras
     */
    public StyleFont getBoldFont() {
        return boldFont;
    }

    /**
     * @return font
     */
    public StyleFont getFont() {
        return font;
    }

    /**
     * @return font souligné
     */
    public StyleFont getUnderlinedFont() {
        return underlinedFont;
    }

    /**
     * Spécifie la taille
     * @param pFontSize taille
     */
    public void setFontSize(final int pFontSize) {
        this.fontSize = pFontSize;
    }

    /**
     * @return petite font
     */
    public StyleFont getSmallFont() {
        return smallFont;
    }

    /**
     * @return Report ...
     */
    public Object getReport() {
        return report;
    }

    /** 
     * @see com.airfrance.welcom.outils.pdf.PDFGenerateur#close()
     */
    public void close() throws PDFGenerateurException {
        printPDF();
    }

    /**
     * @see com.airfrance.welcom.outils.pdf.PDFGenerateur#open(java.io.OutputStream)
     */
    public void open(final OutputStream pOs) {
        setOutputStream(pOs);
        
    }

    /**
     * @see com.airfrance.welcom.outils.pdf.PDFGenerateur#getPDFWriter()
     */
    public Object getPDFWriter() {
        return fichier;
    }

    /**
     * @see com.airfrance.welcom.outils.pdf.PDFGenerateur#setDecoration(com.airfrance.welcom.outils.pdf.advanced.WPdfDecoration)
     */
    public void setDecoration(final WPdfDecoration decoration) throws PDFGenerateurException {
        throw new PDFGenerateurException("Attention, la méthode setDecoration() n'est utilisable qu'avec un moteur de type Itext");
        
    }
    /**
     * @param data PDFData
     */
    public void setPdfData(final PDFData data) {
        pdfData = data;
    }

}