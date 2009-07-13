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
package org.squale.welcom.outils.pdf.advanced;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PRAcroForm;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.SimpleBookmark;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WPdfMerge
{
    /** dernier id (date du jour) */
    private static long lastIdField = new Date().getTime();

    /** le prefixe */
    private static final String prefixField = "T";

    /** la liste des readers */
    private final ArrayList readers = new ArrayList();

    /** l'outputstream */
    private OutputStream out = null;

    /** le pdfDecoration */
    private WPdfDecoration decoration = null;

    /**
     * @param pOut l'outputstream
     */
    public WPdfMerge( final OutputStream pOut )
    {
        out = pOut;
    }

    /**
     * @param reader le pdf reader
     */
    public void add( final PdfReader reader )
    {
        if ( reader != null )
        {
            readers.add( reader );
        }
    }

    /**
     * @param reader le reader
     * @throws IOException exception pouvant etre levee
     */
    public void add( final byte[] reader )
        throws IOException
    {
        if ( reader != null )
        {
            readers.add( new PdfReader( reader ) );
        }
    }

    /**
     * @throws DocumentException exception pouvant etre levee
     * @throws IOException exception pouvant etre levee
     */
    public void close()
        throws DocumentException, IOException
    {
        byte[] theReport = merge();
        if ( theReport != null )
        {
            final byte[] filledReport = fillDecoration( new PdfReader( theReport ) );
            if ( filledReport != null )
            {
                theReport = filledReport;
            }
            // theReport=fillFooter(new PdfReader(theReport));
            out.write( theReport );
        }

    }

    /**
     * @param pdfReader le reader
     * @return decoration ajoutee
     * @throws DocumentException exception pouvant etre levee
     * @throws IOException exception pouvant etre levee
     */
    private byte[] fillDecoration( final PdfReader pdfReader )
        throws DocumentException, IOException
    {
        if ( decoration != null )
        {
            return decoration.fill( pdfReader );
        }
        else
        {
            return null;
        }
    }

    /**
     * merge
     * 
     * @return byte array rempli
     */
    private byte[] merge()
    {
        final ByteArrayOutputStream tmpout = new ByteArrayOutputStream();
        int pageOffset = 0;
        int f = 0;
        Document document = null;
        final ArrayList master = new ArrayList();
        PdfCopy writer = null;

        final Iterator it = readers.iterator();

        while ( it.hasNext() )
        {
            PdfReader reader = (PdfReader) it.next();

            try
            {
                // Renome tout les champs;
                reader = new PdfReader( renameFieldUnique( reader ) );

                reader.consolidateNamedDestinations();
                // we retrieve the total number of pages
                final int n = reader.getNumberOfPages();
                final List bookmarks = SimpleBookmark.getBookmark( reader );
                if ( bookmarks != null )
                {
                    if ( pageOffset != 0 )
                    {
                        SimpleBookmark.shiftPageNumbers( bookmarks, pageOffset, null );
                    }
                    master.addAll( bookmarks );
                }
                pageOffset += n;

                if ( f == 0 )
                {
                    // step 1: creation of a document-object
                    document = new Document( reader.getPageSizeWithRotation( 1 ) );
                    // step 2: we create a writer that listens to the document
                    writer = new PdfCopy( document, tmpout );
                    // step 3: we open the document
                    document.open();
                }
                // step 4: we add content
                PdfImportedPage page;
                for ( int i = 0; i < n; )
                {
                    ++i;
                    page = writer.getImportedPage( reader, i );
                    writer.addPage( page );
                }
                final PRAcroForm form = reader.getAcroForm();
                if ( form != null )
                {
                    writer.copyAcroForm( reader );
                }
                f++;
                if ( master.size() > 0 )
                {
                    writer.setOutlines( master );
                }
                // step 5: we close the document
                // document.close();

            }
            catch ( final Exception e )
            {
                e.printStackTrace();
            }
        }
        if ( document != null )
        {
            document.close();
        }
        return tmpout.toByteArray();
    }

    /**
     * Renomme tous le champs du pdf avoir qu'il soit tous uniques
     * 
     * @param pdfReader le reader
     * @return les champs renommes
     * @throws DocumentException exception pouvant etre levee
     * @throws IOException exception pouvant etre levee
     */
    private byte[] renameFieldUnique( final PdfReader pdfReader )
        throws DocumentException, IOException
    {
        final ByteArrayOutputStream pOut = new ByteArrayOutputStream();
        final PdfStamper pdfStamper = new PdfStamper( pdfReader, pOut );
        if ( pdfReader.getAcroForm() != null )
        {
            for ( int i = 0; i < pdfReader.getAcroForm().size(); i++ )
            {
                final PRAcroForm.FieldInformation p =
                    (PRAcroForm.FieldInformation) pdfReader.getAcroForm().getFields().get( i );
                final String newName = WPdfMerge.getUniqueFieldName();
                pdfStamper.getAcroFields().renameField( p.getName(), newName );
            }
        }
        pdfStamper.close();
        return pOut.toByteArray();
    }

    /**
     * @return le nom du champ unique
     */
    private synchronized static String getUniqueFieldName()
    {
        return prefixField + ( lastIdField++ );
    }

    /**
     * @return decoration
     */
    public WPdfDecoration getDecoration()
    {
        return decoration;
    }

    /**
     * @param pDecoration decoration
     */
    public void setDecoration( final WPdfDecoration pDecoration )
    {
        decoration = pDecoration;
    }

}
