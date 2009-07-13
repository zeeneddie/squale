/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.squale.welcom.outils.pdf;

import java.io.InputStream;
import java.io.OutputStream;

import org.squale.welcom.outils.pdf.advanced.WPdfDecoration;


/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public interface PDFGenerateur
{
    /**
     * @param pdfData le pdfData(utilise pour recuperer la locale et la request)
     */
    public void setPdfData( PDFData pdfData );

    /**
     * Chargement le template
     * 
     * @param is : Steram contenant le template
     * @throws PDFGenerateurException : Erreur PDF sur l'ecture du template
     */
    public void loadTemplate( InputStream is )
        throws PDFGenerateurException;

    /**
     * Gere le PDF
     * 
     * @throws PDFGenerateurException : Probleme a la generation
     */
    public void close()
        throws PDFGenerateurException;

    /**
     * assigne l'outputstream
     * 
     * @param os outputStream à setter
     * @throws PDFGenerateurException exception pouvant etre levee
     */
    public void open( OutputStream os )
        throws PDFGenerateurException;

    /**
     * retourne le document en cours de génération.
     * 
     * @return report
     */
    public Object getReport();

    /**
     * retourne l'objet d'écriture dans le pdf
     * 
     * @return writer
     */
    public Object getPDFWriter();

    /**
     * Attention cette méthode n'est utilisable qu'avec un moteur de rendu IText
     * 
     * @param decoration décoration contenant le header et le footer
     * @throws PDFGenerateurException exception pouvant etre levee
     */
    public void setDecoration( WPdfDecoration decoration )
        throws PDFGenerateurException;

}