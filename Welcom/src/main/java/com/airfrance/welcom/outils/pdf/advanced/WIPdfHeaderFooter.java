/*
 * Créé le 2 mars 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.pdf.advanced;

import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public interface WIPdfHeaderFooter {
    /**
     * Impression sur le doc du header
     * @param over le pdfcontentbyte
     * @param pageSize la taille de la page
     * @param currentPage la page courante
     * @param totalPage le nombre total de page
     */
    public abstract void fill(PdfContentByte over, Rectangle pageSize, int currentPage, int totalPage);
}