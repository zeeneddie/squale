package com.airfrance.welcom.outils.pdf.advanced;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfNumber;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

/*
 * Créé le 3 nov. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WPdfFieldWriter
{
    /** fields */
    private WPdfFields pdfFields = null;

    /** reader */
    private PdfReader pdfReader = null;

    /** outputstream */
    private OutputStream out = null;

    /** stamper */
    private PdfStamper pdfStamper = null;

    /**
     * Constructeur
     * 
     * @param pPdfReader reader
     * @param pOut outputstream
     * @param pFields field
     */
    public WPdfFieldWriter( final PdfReader pPdfReader, final OutputStream pOut, final WPdfFields pFields )
    {
        pdfReader = pPdfReader;
        out = pOut;
        pdfFields = pFields;
    }

    /**
     * Class pour le remplissage du template
     * 
     * @param pPdfReader : Fichier source
     * @param pOut : cible
     */
    public WPdfFieldWriter( final PdfReader pPdfReader, final OutputStream pOut )
    {
        pdfReader = pPdfReader;
        out = pOut;
        pdfFields = null;
    }

    /**
     * Ajoute le field pour le remplissage de template pdf
     * 
     * @param pdfField le champ
     */
    public void add( final WPdfField pdfField )
    {
        if ( pdfFields == null )
        {
            pdfFields = new WPdfFields();
        }

        pdfFields.add( pdfField );
    }

    /**
     * Ajoute la collection de fields pour le remplissage de template pdf
     * 
     * @param all le champs a ajouter
     */
    public void addAll( final WPdfFields all )
    {
        if ( pdfFields == null )
        {
            pdfFields = new WPdfFields();
        }

        pdfFields.addAll( all );
    }

    /**
     * Remplissage du tempate
     * 
     * @throws IOException exception pouvant etre levee
     * @throws DocumentException exception pouvant etre levee
     */
    public void fill()
        throws IOException, DocumentException
    {
        pdfStamper = new PdfStamper( pdfReader, out );
        final Enumeration enumeration = pdfFields.elements();
        while ( enumeration.hasMoreElements() )
        {
            final WPdfField pdfField = (WPdfField) enumeration.nextElement();
            fillField( pdfField, true );
        }
    }

    /**
     * @param pdfField champ
     * @param lock lock
     * @throws IOException exception pouvant etre levee
     * @throws DocumentException exception pouvant etre levee
     */
    private void fillField( final WPdfField pdfField, final boolean lock )
        throws IOException, DocumentException
    {
        if ( pdfField.getType() != WPdfFieldType.INCONNU )
        {
            fillField( pdfField.getName(), pdfField.getValue(), lock );
        }
    }

    /**
     * @param fieldName nom du champ
     * @param value value du champ
     * @param lock lock
     * @throws IOException exception pouvant etre levee
     * @throws DocumentException exception pouvant etre levee
     */
    private void fillField( final String fieldName, final String value, final boolean lock )
        throws IOException, DocumentException
    {
        pdfStamper.getAcroFields().setField( fieldName, value );
        if ( lock )
        {
            lockField( pdfStamper.getAcroFields(), fieldName );
        }
    }

    /**
     * ferme le pdfStamper
     * 
     * @throws DocumentException exception pouvant etre levee
     * @throws IOException exception pouvant etre levee
     */
    public void close()
        throws DocumentException, IOException
    {
        if ( pdfStamper != null )
        {
            pdfStamper.close();
        }
    }

    /**
     * Tranforme les champs en nom modifiable
     * 
     * @param form le field
     * @param name le nom
     */
    private static void lockField( final AcroFields form, final String name )
    {
        final AcroFields.Item item = form.getFieldItem( name );
        if ( item != null )
        {
            for ( int k = 0; k < item.merged.size(); ++k )
            {
                PdfNumber num =
                    (PdfNumber) PdfReader.getPdfObject( ( (PdfDictionary) item.values.get( k ) ).get( PdfName.FF ) );
                int val = 0;

                if ( num != null )
                {
                    val = num.intValue();
                }

                num = new PdfNumber( val | PdfFormField.FF_READ_ONLY );

                ( (PdfDictionary) item.merged.get( k ) ).put( PdfName.FF, num );
                ( (PdfDictionary) item.values.get( k ) ).put( PdfName.FF, num );
            }
        }
    }
}
