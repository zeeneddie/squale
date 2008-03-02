package com.airfrance.welcom.outils.pdf.advanced;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PRAcroForm;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfNumber;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfString;

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
public class WPdfFieldReader
{
    /** le reader */
    private PdfReader pdfReader;

    /** les champs */
    private final WPdfFields fields = new WPdfFields();

    /** le pdfDecoration */
    private WPdfDecoration decoration = null;

    /**
     * Constructeur
     * 
     * @param pPdfReader le reader
     */
    public WPdfFieldReader( final PdfReader pPdfReader )
    {
        super();
        pdfReader = pPdfReader;
        // Si des champs saont present on les lits
        if ( pdfReader.getAcroForm() != null )
        {
            init();
        }
    }

    /**
     * init
     */
    public void init()
    {
        for ( int i = 0; i < pdfReader.getAcroForm().size(); i++ )
        {

            final PRAcroForm.FieldInformation p =
                (PRAcroForm.FieldInformation) pdfReader.getAcroForm().getFields().get( i );

            final WPdfField pf = new WPdfField();
            pf.setName( p.getName() );

            // Si ce n'est pas un champs text alors on l'ignore
            if ( !p.getInfo().contains( PdfName.FT )
                || !( ( (PdfName) p.getInfo().get( PdfName.FT ) ).toString().equals( PdfName.TX.toString() ) ) )
            {
                pf.setType( WPdfFieldType.INCONNU );
            }
            else
            {
                if ( p.getInfo().contains( PdfName.DV ) )
                {
                    pf.setDefaultValue( ( (PdfString) ( p.getInfo().get( PdfName.DV ) ) ).toUnicodeString() );
                }
                if ( p.getInfo().contains( PdfName.V ) )
                {
                    pf.setValue( ( (PdfString) ( p.getInfo().get( PdfName.V ) ) ).toUnicodeString() );
                }
                if ( p.getInfo().contains( PdfName.FF ) )
                {
                    final PdfNumber pn = (PdfNumber) p.getInfo().get( PdfName.FF );
                    if ( ( pn.intValue() & PdfFormField.FF_MULTILINE ) == PdfFormField.FF_MULTILINE )
                    {
                        pf.setType( WPdfFieldType.MUTILINETEXT );
                    }
                    else
                    {
                        pf.setType( WPdfFieldType.TEXT );
                    }
                }
                else
                {
                    pf.setType( WPdfFieldType.TEXT );
                }
            }
            fields.add( pf );
        }
    }

    /**
     * @return fields
     */
    public WPdfFields getPdfFields()
    {
        return fields;
    }

    /**
     * @param out l'outputstream
     * @throws IOException exception pouvant etre levee
     * @throws DocumentException exception pouvant etre levee
     */
    public void fill( final OutputStream out )
        throws IOException, DocumentException
    {
        final WPdfFieldWriter pw = new WPdfFieldWriter( pdfReader, out, fields );
        pw.fill();
        pw.close();
    }

    /**
     * fill
     * 
     * @return un pdfReader
     * @throws IOException exception pouvant etre levee
     * @throws DocumentException exception pouvant etre levee
     */
    public PdfReader fill()
        throws IOException, DocumentException
    {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        fill( out );
        return new PdfReader( out.toByteArray() );
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
