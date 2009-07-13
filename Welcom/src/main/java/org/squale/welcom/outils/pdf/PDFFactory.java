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

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.squale.welcom.outils.pdf.itext.PDFiTextWrapper;
import org.squale.welcom.outils.pdf.jasperreports.PDFJasperReportsWrapper;


/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class PDFFactory
{
    /** Content Type */
    protected final static String CONTENT_TYPE = "application/pdf";

    /**
     * @deprecated Factory pour prosuire un PDF
     * @param pdfgenerateur : Moteur a utiliser
     * @param pdfdata : Donné a y mettre
     * @throws PDFGenerateurException : Probleme a la generation du report
     */
    public static void generationPDF( final PDFGenerateur pdfgenerateur, final PDFData pdfdata )
        throws PDFGenerateurException
    {
        // Chargement du template
        pdfgenerateur.loadTemplate( pdfdata.getClass().getResourceAsStream( pdfdata.getTemplateName() ) );

        // Remplissage du template
        pdfdata.fill( pdfgenerateur );

        // Generation du PDF
        pdfgenerateur.close();
    }

    /**
     * @since welcom 2.3
     * @param pdfdata le pdfData
     * @param os l'outputstream
     * @param engine l'engine
     * @throws PDFGenerateurException exception pouvant etre levee
     */
    public static void generatePDF( final PDFData pdfdata, final OutputStream os, final PDFEngine engine )
        throws PDFGenerateurException
    {
        final PDFGenerateur generateur = getGenerateur( engine );
        generateur.setPdfData( pdfdata );
        generateur.open( os );
        generateur.loadTemplate( pdfdata.getClass().getResourceAsStream( pdfdata.getTemplateName() ) );
        pdfdata.fill( generateur );
        generateur.close();
    }

    /**
     * @param pdfdata le pdfdata
     * @param response la response
     * @param attachementFileName le nom du fichier dans le cas d'un save as
     * @param engine le pdfengine
     * @throws PDFGenerateurException exception pouvant etre levee
     */
    public static void generatePDFToHTTPResponse( final PDFData pdfdata, final HttpServletResponse response,
                                                  final String attachementFileName, final PDFEngine engine )
        throws PDFGenerateurException
    {
        try
        {
            final OutputStream os = response.getOutputStream();
            response.setContentType( CONTENT_TYPE );
            if ( !GenericValidator.isBlankOrNull( attachementFileName ) )
            {
                response.setHeader( "Content-Disposition", "attachment;filename=" + attachementFileName + ";" );
            }
            generatePDF( pdfdata, os, engine );
        }
        catch ( final IOException e )
        {
            throw new PDFGenerateurException( e.getMessage() );
        }
    }

    /**
     * @param engine le pdfEngine
     * @return le pdfGenerateur
     */
    private static PDFGenerateur getGenerateur( final PDFEngine engine )
    {
        PDFGenerateur generateur = null;
        if ( engine == PDFEngine.JASPERREPORTS )
        {
            generateur = new PDFJasperReportsWrapper();
        }
        else if ( engine == PDFEngine.ITEXT )
        {
            generateur = new PDFiTextWrapper();
        }
        return generateur;
    }
}