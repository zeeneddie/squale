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
/*
 * Créé le 10 mars 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.outils.pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WPdfUtil
{
    /**
     * Ajoute dans les parametres du report les paramtres pour l'ouverture en plein ecran Utilise itext.jar
     * 
     * @param report : report
     * @return flux PDF
     * @throws DocumentException : Probleme a la lecture du document
     * @throws IOException : Probleme a l'ecriture dans la stream
     */
    public static byte[] fullScreen( final byte report[] )
        throws DocumentException, IOException
    {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        fullScreen( report, out );

        return out.toByteArray();
    }

    /**
     * Ajoute dans les parametres du report les paramtres pour l'ouverture en plein ecran Utilise itext.jar
     * 
     * @param report : report
     * @param out : flux pour ecrire
     * @throws DocumentException : Probleme a la lecture du document
     * @throws IOException : Probleme a l'ecriture dans la stream
     */
    public static void fullScreen( final byte report[], final OutputStream out )
        throws DocumentException, IOException
    {
        final PdfStamper stamper = new PdfStamper( new PdfReader( report ), out );

        stamper.setViewerPreferences( PdfWriter.PageLayoutTwoColumnRight | PdfWriter.PageModeFullScreen
            | PdfWriter.NonFullScreenPageModeUseThumbs );

        stamper.close();
    }

    /**
     * Ajoute dans les parametres du report l'impression au chargement du document Utilise itext.jar
     * 
     * @param report : report
     * @return flux PDF
     * @throws DocumentException : Probleme a la lecture du document
     * @throws IOException : Probleme a l'ecriture dans la stream
     */
    public static byte[] autoPrint( final byte report[] )
        throws DocumentException, IOException
    {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        autoPrint( report, out );

        return out.toByteArray();
    }

    /**
     * Ajoute dans les parametres du report l'impression au chargement du document Utilise itext.jar
     * 
     * @param report : report
     * @param out : flux pour ecrire
     * @throws DocumentException : Probleme a la lecture du document
     * @throws IOException : Probleme a l'ecriture dans la stream
     */
    public static void autoPrint( final byte report[], final OutputStream out )
        throws DocumentException, IOException
    {
        final PdfStamper stamper = new PdfStamper( new PdfReader( report ), out );

        stamper.addJavaScript( "this.print(false);" );

        stamper.close();
    }

    /**
     * Ajoute dans les parametres du report l'impression au chargement du document avec choix pour l'imprimante Utilise
     * itext.jar
     * 
     * @param report : report
     * @return flux PDF
     * @throws DocumentException : Probleme a la lecture du document
     * @throws IOException : Probleme a l'ecriture dans la stream
     */
    public static byte[] autoPrintPopup( final byte report[] )
        throws DocumentException, IOException
    {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        autoPrintPopup( report, out );

        return out.toByteArray();
    }

    /**
     * Ajoute dans les parametres du report l'impression au chargement du document avec choix pour l'imprimante Utilise
     * itext.jar
     * 
     * @param report : report
     * @param out : flux pour ecrire
     * @throws DocumentException : Probleme a la lecture du document
     * @throws IOException : Probleme a l'ecriture dans la stream
     */
    public static void autoPrintPopup( final byte report[], final OutputStream out )
        throws DocumentException, IOException
    {
        final PdfStamper stamper = new PdfStamper( new PdfReader( report ), out );

        stamper.addJavaScript( "this.print(true);" );

        stamper.close();
    }
}