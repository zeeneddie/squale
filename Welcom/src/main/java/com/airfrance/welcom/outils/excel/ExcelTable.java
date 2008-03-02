/*
 * Créé le 1 sept. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.excel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import jxl.CellView;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.util.MessageResources;

import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.struts.lazyLoading.font.WFontSimulator;
import com.airfrance.welcom.taglib.field.util.LayoutUtils;

/**
 * @author M327836 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ExcelTable
{
    /** logger */
    private static Log log = LogFactory.getLog( ExcelTable.class );

    /**
     * Collection a exporter
     */
    private Collection table;

    /**
     * Liste des entêtes a inclure dans le tableau
     */
    private Vector header;

    /**
     * hashtable contenant les dateformats
     */
    private final Hashtable writableCellFormat = new Hashtable();

    /**
     * Internationnalisaton
     */
    protected MessageResources resources = null;

    /**
     * Locale que la requete
     */
    protected Locale localeRequest = Locale.FRENCH;

    /** Nombre magique pour la taille des cellules */
    private static final double C4 = 3.3;

    /**
     * Contruction d'un export table, sous excel
     */
    public ExcelTable()
    {
        this( null, null );
    }

    /**
     * Constructieur d'un export table avec internalionnalisation
     * 
     * @param res : Bundle
     * @param loc : locale de la requete
     */
    public ExcelTable( final MessageResources res, final Locale loc )
    {
        header = new Vector();

        resources = res;
        localeRequest = loc;
    }

    /**
     * Ajoute une colonne
     * 
     * @param key : c'est la clé du libellé de la colone déclaré dans l'Application ressources.properties.
     * @param property : c'est le nom de la propriété de bean de la collection à aller chercher.
     * @param type : type des élements de la colone (voir constantes de HeaderElement)
     * @param dateformat : format de la date (simplaeDateFormat)
     */
    public void addHeader( final String key, final String property, final int type, final String dateformat )
    {
        header.add( new HeaderElement( key, property, type, dateformat ) );
    }

    /**
     * Ajoute une colonne dans l'entête
     * 
     * @param key : c'est la clé du libellé de la colone déclaré dans l'Application ressources.properties.
     * @param property : c'est le nom de la propriété de bean de la collection à aller chercher.
     */
    public void addHeader( final String key, final String property )
    {
        header.add( new HeaderElement( key, property ) );
    }

    /**
     * Ajoute une colonne dans l'entête
     * 
     * @param key : c'est la clé du libellé de la colone déclaré dans l'Application ressources.properties.
     * @param property : c'est le nom de la propriété de bean de la collection à aller chercher.
     * @param type : NUMBER,TEXT,DATE
     */
    public void addHeader( final String key, final String property, final int type )
    {
        header.add( new HeaderElement( key, property, type ) );
    }

    /**
     * Genere la table au format excel
     * 
     * @param workbook : Classeur Excel
     * @throws WriteException : Problmem lors de l'ecriture du flux
     */
    public void writeTable( final WritableWorkbook workbook )
        throws WriteException
    {
        final int fontSize = 10;

        final WritableSheet sheet = workbook.createSheet( "Liste", 0 );

        final WritableFont arial10pt = new WritableFont( WritableFont.ARIAL, fontSize, WritableFont.NO_BOLD );
        final WritableCellFormat allThin = new WritableCellFormat( arial10pt );
        allThin.setBorder( Border.ALL, BorderLineStyle.THIN );
        allThin.setWrap( true );

        // header
        writeHeader( fontSize, sheet );
        final double sizeMax[] = new double[header.size()];

        int j = 1;

        for ( final Iterator iter = table.iterator(); iter.hasNext(); )
        {
            final Object element = iter.next();

            int i = 0;

            for ( final Enumeration e = header.elements(); e.hasMoreElements(); )
            {
                final HeaderElement elem = (HeaderElement) e.nextElement();

                final double tmpSize = writeCell( sheet, allThin, element, elem, j, i );
                if ( tmpSize > sizeMax[i] )
                {
                    sizeMax[i] = tmpSize;
                }

                i++;
            }

            j++;
        }

        // Definition de la largeur des colonnes
        // On utilise la taille max des cellules + la taille du header
        for ( int i = 0; i < header.size(); i++ )
        {
            final CellView cv = new CellView();

            double maxSize = sizeMax[i];
            String libelle = resources.getMessage( localeRequest, ( (HeaderElement) header.get( i ) ).getKey() );
            double headSize = WFontSimulator.getSize( libelle );

            if ( headSize > maxSize )
            {
                maxSize = headSize;
            }

            int colSize = (int) ( maxSize / C4 );
            cv.setSize( colSize );
            sheet.setColumnView( i, cv );
        }
    }

    /**
     * Ecrit le format des cellule
     * 
     * @param dateFormat dateFormat
     * @return Cellule formaté
     */
    private WritableCellFormat getWritableCellFormat( final String dateFormat )
    {
        final Object returnValue = writableCellFormat.get( dateFormat );
        if ( returnValue == null )
        {
            final DateFormat customDateFormat = new DateFormat( dateFormat );
            final WritableCellFormat wcl = new WritableCellFormat( customDateFormat );
            try
            {
                wcl.setBorder( Border.ALL, BorderLineStyle.THIN );
            }
            catch ( final WriteException e )
            {
                log.error( e, e );
            }
            writableCellFormat.put( dateFormat, wcl );
            return wcl;
        }
        return (WritableCellFormat) returnValue;
    }

    /**
     * Ecrit la valeur d'une cellule
     * 
     * @param sheet : Feuille Excel
     * @param cellFormat : Formateg de la cellule
     * @param element : Element a écrire
     * @param headerElement : Colonne
     * @param posY : Position Y de la feuille
     * @param posX : POsition X de la feuille
     * @throws WriteException : Probleme sur l'ecriture du flux
     * @throws RowsExceededException : Trop de lignes
     * @return : retourne la taille estime en font verdana
     */
    private double writeCell( final WritableSheet sheet, final WritableCellFormat cellFormat, final Object element,
                              final HeaderElement headerElement, final int posY, final int posX )
        throws WriteException, RowsExceededException
    {
        final Object val = getValue( element, headerElement );
        try
        {
            if ( val != null )
            {
                switch ( headerElement.getType() )
                {
                    case HeaderElement.DATE_HEURE:
                        final DateTime dateHeureCell =
                            new DateTime( posX, posY, (Date) val,
                                          getWritableCellFormat( headerElement.getDateHeureFormat() ) );
                        sheet.addCell( dateHeureCell );
                        return WFontSimulator.getSize( headerElement.getDateHeureFormat() );
                    case HeaderElement.DATE:
                        final DateTime dateCell =
                            new DateTime( posX, posY, (Date) val, getWritableCellFormat( headerElement.getDateFormat() ) );
                        sheet.addCell( dateCell );
                        return WFontSimulator.getSize( headerElement.getDateFormat() );
                    case HeaderElement.NUMBER:
                        final Number number = new Number( posX, posY, Double.parseDouble( (String) val ), cellFormat );
                        sheet.addCell( number );
                        return WFontSimulator.getSize( number.getContents() );

                    default:
                        final Label label = new Label( posX, posY, (String) val, cellFormat );
                        sheet.addCell( label );
                        return WFontSimulator.getSize( label.getContents() );

                }
            }
            else
            {
                final Label label = new Label( posX, posY, "-", cellFormat );
                sheet.addCell( label );
            }
        }
        catch ( final NumberFormatException nfex )
        {
            nfex.printStackTrace();
            String strVal = "-";
            if ( val != null )
                strVal = (String) val;
            final Label label = new Label( posX, posY, strVal, cellFormat );
            sheet.addCell( label );
        }
        return 0;
    }

    /**
     * Retourne la valeur d'un element Renvoie une date ou une string selon le cas
     * 
     * @param element : Object
     * @param headerElement :Property de la colonne
     * @return : Valeur de l'objet
     */
    private Object getValue( final Object element, final HeaderElement headerElement )
    {
        try
        {
            Object value = LayoutUtils.getProperty( element, headerElement.getProperty() );
            Object retour = "";

            if ( value instanceof Date )
            {
                retour = (Date) value;
            }
            else if ( ( headerElement.getType() == HeaderElement.DATE_HEURE || headerElement.getType() == HeaderElement.DATE )
                && value instanceof String )
            {
                try
                {
                    retour = new Date( Long.parseLong( (String) value ) );
                }
                catch ( final NumberFormatException nfe )
                {
                    // On a pas un long dans la chaîne donc on va la parser avec un dateformat
                    // On gère les deux cas possibles pour éviter au maximum les soucis

                    SimpleDateFormat sdf = null;
                    sdf = Util.formatDtHr;
                    sdf.setLenient( false );

                    try
                    {
                        retour = sdf.parse( (String) value );
                    }
                    catch ( final ParseException pe )
                    {
                        sdf = Util.formatDt;
                        sdf.setLenient( false );

                        try
                        {
                            retour = sdf.parse( (String) value );
                        }
                        catch ( final ParseException pe2 )
                        {
                            log.error( pe2, pe2 );
                            return null;
                        }
                    }

                }
            }
            else
            {
                retour = BeanUtils.getProperty( element, headerElement.getProperty() );
            }

            return retour;
        }
        catch ( final Exception ex )
        {
            log.error( ex, ex );
            return null;
        }
    }

    /**
     * Ecrit l'entete des colonne pour l'export excel
     * 
     * @param fontSize : Taille de la font
     * @param sheet : Feuille xls
     * @throws WriteException : Erreur lors de l'écriture de la feuille
     * @throws RowsExceededException : Trop de lignes
     */
    private void writeHeader( final int fontSize, final WritableSheet sheet )
        throws WriteException, RowsExceededException
    {
        for ( int i = 0; i < header.size(); i++ )
        {
            final WritableFont arial10ptBold = new WritableFont( WritableFont.ARIAL, fontSize, WritableFont.BOLD );
            final WritableCellFormat allThinBold = new WritableCellFormat( arial10ptBold );
            allThinBold.setBorder( Border.ALL, BorderLineStyle.THIN );
            String libelle = resources.getMessage( localeRequest, ( (HeaderElement) header.get( i ) ).getKey() );
            if ( GenericValidator.isBlankOrNull( libelle ) )
            {
                libelle = ( (HeaderElement) header.get( i ) ).getKey();
            }
            final Label label = new Label( i, 0, libelle, allThinBold );
            sheet.addCell( label );
        }
    }

    /**
     * @return Liste des colonnes du tableau
     */
    public Vector getHeader()
    {
        return header;
    }

    /**
     * @return Collection a afficher
     */
    public Collection getTable()
    {
        return table;
    }

    /**
     * @param collection Force la liste des en-tête a afficher
     */
    public void setHeader( final Vector collection )
    {
        header = collection;
    }

    /**
     * @param collection Force la collection
     */
    public void setTable( final Collection collection )
    {
        table = collection;
    }

    /**
     * @return retourne le Bundle pour l'internationnalisation
     */
    public MessageResources getResources()
    {
        return resources;
    }

    /**
     * @param pResources Force le bundle
     */
    public void setResources( final MessageResources pResources )
    {
        this.resources = pResources;
    }

    /**
     * @return recupere la locale utilisé
     */
    public Locale getLocaleRequest()
    {
        return localeRequest;
    }

    /**
     * @param locale locale utilisé pour les entêtes
     */
    public void setLocaleRequest( final Locale locale )
    {
        localeRequest = locale;
    }

    /**
     * Classe definissant une collection d'entête
     * 
     * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
     *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
     */
    public class HeaderElement
    {

        /**
         * Definit le type STRING de la colonne
         */
        public final static int STRING = 1;

        /**
         * Definti le type NUMBER de la colonne
         */
        public final static int NUMBER = 2;

        /**
         * Definit DATE comme type
         */
        public final static int DATE = 3;

        /**
         * Definit DATE comme type
         */
        public final static int DATE_HEURE = 4;

        /**
         * Clef dans l'application resources
         */
        private String key;

        /**
         * Property du bean pour afficher la colonne
         */
        private String property;

        /**
         * Type par default de la colonne (STRING)
         */
        private int type = STRING;

        /**
         * Format Date sur l'export
         */
        private String dateFormat = Util.stringFormatDt;

        /**
         * Format Date + heure sur l'export
         */
        private String dateHeureFormat = Util.stringFormatDtHr;

        /**
         * Contructeur d'une colonne
         */
        public HeaderElement()
        {
        }

        /**
         * Creation d'une colonne
         * 
         * @param pKey : Clef dans l'application resources
         * @param pProperty : Property du bean pour afficher la colonne
         * @param pType : Type par default de la colonne
         * @param pDateformat : Format Date sur l'export
         */
        public HeaderElement( final String pKey, final String pProperty, final int pType, final String pDateformat )
        {
            this.key = pKey;
            this.property = pProperty;
            this.type = pType;
            this.dateFormat = pDateformat;
        }

        /**
         * Creation d'une colonne
         * 
         * @param pKey : Clef dans l'application resources
         * @param pProperty : Property du bean pour afficher la colonne
         */
        public HeaderElement( final String pKey, final String pProperty )
        {
            this( pKey, pProperty, STRING, Util.stringFormatDt );
        }

        /**
         * Creation d'une colonne
         * 
         * @param pKey : Clef dans l'application resources
         * @param pProperty : Property du bean pour afficher la colonne
         * @param pType : Type par default de la colonne
         */
        public HeaderElement( final String pKey, final String pProperty, final int pType )
        {
            this( pKey, pProperty, pType, Util.stringFormatDt );
        }

        /**
         * @return clef de l'entete de la colonne
         */
        public String getKey()
        {
            return key;
        }

        /**
         * @return property Nom de la poperty a attaqué sur le bean pour l'affichage des valeurs
         */
        public String getProperty()
        {
            return property;
        }

        /**
         * @param string clef de l'entete de la colonne
         */
        public void setKey( final String string )
        {
            key = string;
        }

        /**
         * @param string Nom de la poperty a attaqué sur le bean pour l'affichage des valeurs
         */
        public void setProperty( final String string )
        {
            property = string;
        }

        /**
         * @return format de la date pour affichage de la colonne, default jj/mm/yyyy
         */
        public String getDateFormat()
        {
            return dateFormat;
        }

        /**
         * @param format format de la date pour affichage de la colonne
         */
        public void setDateFormat( final String format )
        {
            dateFormat = format;
        }

        /**
         * @return type de la colonne (STRING/NUMBER/DATE)
         */
        public int getType()
        {
            return type;
        }

        /**
         * @param string type de la colonne (STRING/NUMBER/DATE)
         */
        public void setType( final int string )
        {
            type = string;
        }

        /**
         * @return format de la date pour affichage de la colonne
         */
        public String getDateHeureFormat()
        {
            return dateHeureFormat;
        }

        /**
         * @param format format de la date pour affichage de la colonne
         */
        public void setDateHeureFormat( String format )
        {
            dateHeureFormat = format;
        }

    }
}