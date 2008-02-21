/*
 * Créé le 6 juin 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.pdf;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.struts.util.MessageResources;

import com.airfrance.welcom.outils.pdf.jasperreports.PDFJasperReportsWrapper;

/**
 * PDFDataEscaleListe
 */
public class PDFDataJasperReports extends PDFData {

    /** la liste des beans */
    private Collection collection;
    /** le nom du template */
    private String templateName;
    /** Liste des parametres */
    private Map parameters;
    /** Active la bufferisation sur le disque */
    private boolean virtualize;

    /**
     * Constructeur 
     * @param locale la locale
     * @param mess le messageRessource
     * @param pCollection la liste
     * @param pTemplateName le nom du template
     */
    public PDFDataJasperReports(final Locale locale, final MessageResources mess, final Collection pCollection, final String pTemplateName) {
        this(locale, mess, pCollection, pTemplateName, false, null);
    }

    /**
      * Constructeur 
      * @param locale la locale
      * @param mess le messageRessource
      * @param pCollection la liste
      * @param pTemplateName le nom du template
      * @param pVirtualize active le bufferisation sur le disque
      */
    public PDFDataJasperReports(final Locale locale, final MessageResources mess, final Collection pCollection, final String pTemplateName, final boolean pVirtualize) {
        this(locale, mess, pCollection, pTemplateName, pVirtualize, null);
    }

    /**
     * Constructeur 
     * @param locale la locale
     * @param mess le messageRessource
     * @param pCollection la liste
     * @param pTemplateName le nom du template
     * @param pParameters Paramtres du report
     * @param pVirtualize active le bufferisation sur le disque
     */
    public PDFDataJasperReports(final Locale locale, final MessageResources mess, final Collection pCollection, final String pTemplateName, final boolean pVirtualize, final Map pParameters) {
        super(locale, mess);
        collection = pCollection;
        templateName = pTemplateName;
        parameters = pParameters;
        virtualize = pVirtualize;
    }

    /**
     * @see com.airfrance.welcom.outils.pdf.PDFData#getTemplateName()
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
    * @param pdfGenerateur le pdfGenerateur
    * @throws PDFGenerateurException exception pouvant etre levee
    */
    public void fill(final PDFGenerateur pdfGenerateur) throws PDFGenerateurException {
        final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(collection);
        if (pdfGenerateur instanceof PDFJasperReportsWrapper) {
            ((PDFJasperReportsWrapper) pdfGenerateur).setDataSource(dataSource);
        }
    }

    /**
     * @return les parametres du report
     */
    public Map getParameters() {
        return parameters;
    }

    /**
     * @return si on active la bufferisation sur le disque
     */
    public boolean isVirtualize() {
        return virtualize;
    }

}
