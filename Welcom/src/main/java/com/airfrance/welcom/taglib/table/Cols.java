/*
 * Créé le 13 avr. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.table;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.util.ResponseUtils;

import com.airfrance.welcom.outils.Access;
import com.airfrance.welcom.outils.Charte;
import com.airfrance.welcom.outils.DateUtil;
import com.airfrance.welcom.outils.TrimStringBuffer;
import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.struts.bean.WIEditable;
import com.airfrance.welcom.struts.bean.WISelectable;
import com.airfrance.welcom.taglib.field.util.LayoutUtils;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class Cols
{
    /** logger */
    private static Log log = LogFactory.getLog( Cols.class );

    /** Constante */
    public static final int NONE = 0;

    /** Constante */
    public static final int FIRST = 1;

    /** Constante */
    public static final int LAST = 2;

    /** Vecteur des colonnes */
    private Vector cols = null;

    /** parametre du tag */
    private String id = "";

    /** pageContext */
    private PageContext pageContext = null;

    /** le parent */
    private TableTag table;

    /** parametre du tag */
    private String selectProperty = "";

    /** parametre du tag */
    private boolean selectable;

    /** parametre du tag */
    private boolean forceReadWrite = false;

    /** parametre du tag */
    private boolean enableSingleSelect = false;

    /** parametre du tag */
    private int splitNbLine = 1;

    /** parametre du tag */
    private String idIndex = "";

    /** formName */
    private String formName = "";

    /** class de la css */
    private String classLignePaire;

    /** class de la css */
    private String classLigneImpaire;

    /** class de la css */
    private String classSelectPaire;

    /** class de la css */
    private String classSelectImpaire;

    /** valeur d'une case interne à la classe */
    private String internalValue = "";

    /**
     * Constructeur
     */
    public Cols()
    {
        // récupère les classes de styles pour les lignes paires et impaires
        classLignePaire = WelcomConfigurator.getMessageWithFullCfgChartePrefix( ".cols.even" );
        classLigneImpaire = WelcomConfigurator.getMessageWithFullCfgChartePrefix( ".cols.odd" );
        classSelectPaire = WelcomConfigurator.getMessageWithFullCfgChartePrefix( ".cols.select.even" );
        classSelectImpaire = WelcomConfigurator.getMessageWithFullCfgChartePrefix( ".cols.select.odd" );
    }

    /**
     * @param c la colonne a ajouter
     */
    public void addCellAtCurrentLine( final Col c )
    {
        if ( cols == null )
        {
            cols = new Vector();
        }
        c.setCols( this );
        cols.add( c );
    }

    /**
     * Genere les colonnes
     * 
     * @param c class
     */
    public void genAllCols( final Class c )
    {
        try
        {
            final Map map = PropertyUtils.describe( c );
            final Field fields[] = (Field[]) map.get( "declaredFields" );

            for ( int i = 0; i < fields.length; i++ )
            {
                final Col col = new Col();
                col.setKey( fields[i].getName() );
                col.setProperty( fields[i].getName() );
                addCellAtCurrentLine( col );
            }

            if ( cols == null )
            {
                cols = new Vector();
            }

            Collections.sort( cols );
        }
        catch ( final Exception e )
        {
            log.error( "Impossible de recupere la liste des elements par defaut", e );
        }
    }

    /**
     * @return true si cols est null ou vide
     */
    public boolean isEmpty()
    {
        if ( cols == null )
        {
            return true;
        }

        return cols.isEmpty();
    }

    /**
     * @return enumeration des colonnes
     */
    public Enumeration elements()
    {
        return cols.elements();
    }

    /**
     * @param i numero de ligne
     * @param o objet a representer
     * @param pIdIndex son idIndex
     * @return le tr de la ligne i
     * @throws JspException exception pouvant etre levee
     */
    public String tabletrCorps( final int i, final Object o, final Integer pIdIndex )
        throws JspException
    {

        final TrimStringBuffer sb = new TrimStringBuffer();

        String style = "";
        String styleselect = "";

        // spécfie les sytle si on est pair ou impair
        if ( ( i % 2 ) == 0 )
        {
            style = classLignePaire;
            styleselect = classSelectPaire;
        }
        else
        {
            style = classLigneImpaire;
            styleselect = classSelectImpaire;
        }

        sb.append( Table.ligneRail() );

        if ( ( i == 0 ) && enableSingleSelect )
        {
            sb.append( "<input type=\"hidden\" name=\"oldSel\" value=\"\">" );
        }

        sb.append( "\t <tr " );

        String currentStyle = style;

        // if (o instanceof WISelectable && (isSelectCheckBox() || isSelectTr())) {
        if ( o instanceof WISelectable )
        {
            if ( ( (WISelectable) o ).isSelected() )
            {
                currentStyle = styleselect;
            }
        }
        else
        {
            if ( isColSelected( o, i ) )
            {
                currentStyle = styleselect;
            }
        }

        sb.append( "class=\"" + currentStyle + "\"\n" );

        sb.append( ">\n" );

        String wdt = "";

        // Spécidifela taille de la colonne si non spécifier dans le header
        if ( ( table.isDisplayHeader() == false ) && ( i == 1 ) )
        {
            wdt = " width=\"24px\"";
        }

        // Ecriture des check box en legacy
        sb.append( writeLegacyCheckBox( o, pIdIndex, style, styleselect, currentStyle, wdt ) );

        // ecriture des colonnes
        sb.append( writeColumnsValue( o, i, style, pIdIndex.intValue(), styleselect ) );

        sb.append( "\t </tr> \n" );

        return sb.toString();
    }

    /**
     * Recherche si la ligne n'est pas sélectionnée via une propriété indiqué dans l'attribut property de colSelect.
     * 
     * @param bean bean associé au tag cols (collection des lignes)
     * @return true si la ligne est sélectionnée
     * @throws JspException
     */
    private boolean isColSelected( final Object bean, final int i )
        throws JspException
    {
        Iterator iter = cols.iterator();
        while ( iter.hasNext() )
        {
            Object col = iter.next();
            if ( col instanceof ColSelect )
            {
                ColSelect colSelect = (ColSelect) col;
                if ( !GenericValidator.isBlankOrNull( colSelect.getProperty() ) )
                {
                    try
                    {
                        String value = BeanUtils.getProperty( bean, colSelect.getProperty() );

                        if ( value.equalsIgnoreCase( "true" ) )
                        {
                            return true;
                        }
                        else
                        {
                            return false;
                        }

                    }
                    catch ( final Exception e )
                    {
                        log.error( e, e );
                        throw new JspException( "Impossible de recupere la clef " + colSelect.getProperty()
                            + " sur le bean" );
                    }
                }
            }
            if ( col instanceof ColSingleSelect )
            {
                ColSingleSelect colSelect = (ColSingleSelect) col;
                return colSelect.getLineIsSelected( bean, i );
            }
        }
        return false;
    }

    /**
     * Ecrit les ancienne case a cocher
     * 
     * @param o objet de la ligne
     * @param pIdIndex index
     * @param style style non selectionné
     * @param styleselect style si selectionné
     * @param currentStyle Sytle courant
     * @param wdt taille
     * @return le flux html de la check box
     * @throws JspException
     */
    private String writeLegacyCheckBox( final Object o, final Integer pIdIndex, String style, String styleselect,
                                        String currentStyle, String wdt )
        throws JspException
    {
        final TrimStringBuffer sb = new TrimStringBuffer();

        if ( WelcomConfigurator.getCharte() == Charte.V2_002 )
        {
            currentStyle = "normal";
        }

        // Si on est en mode checkbox sans avec envoie un table des case a coché coché
        if ( isSelectTr() )
        {
            String key = "";
            try
            {
                key = BeanUtils.getProperty( o, selectProperty );
            }
            catch ( final Exception e )
            {
                log.error( e, e );
                throw new JspException( "Impossible de recupere la clef" );
            }

            String name = getTable().getName();

            if ( !GenericValidator.isBlankOrNull( getTable().getProperty() ) )
            {
                name = getTable().getProperty();
            }

            name = "check_" + name;

            if ( !enableSingleSelect )
            {
                sb.append( "<td" + wdt + " classSelect=\"" + styleselect + "\" classDefault=\"" + style + "\">" );
                sb.append( "<input type=\"hidden\" name=\"checkName\" value=\"" + name + "\">" );
                sb.append( "<input class=\"" + currentStyle + "\" type=\"checkbox\" name=\"" + name + "\" value=\""
                    + key + "\" onclick='check(this);'" );
                sb.append( "></td>" );
            }
            else
            {
                sb.append( "<td" + wdt + " classSelect=\"" + styleselect + "\" classDefault=\"" + style
                    + "\"><input class=\"" + currentStyle + "\" type=\"checkbox\" name=\"" + name + "\" value=\"" + key
                    + "\" onclick='checkSingle(this);'" );
                sb.append( "></td>" );
            }
        }

        // Si on utilise le mode WIselected
        if ( isSelectCheckBox() )
        {
            if ( o instanceof WISelectable )
            {
                String name = getTable().getName();

                if ( !GenericValidator.isBlankOrNull( getTable().getProperty() ) )
                {
                    name = getTable().getProperty();
                }

                if ( !enableSingleSelect )
                {
                    sb.append( "<td " );
                    sb.append( wdt + " classSelect=\"" + styleselect + "\" classDefault=\"" + style + "\">" );

                    if ( Util.isFalse( WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_AUTORESET_CHECKBOX ) ) )
                    {
                        sb.append( "<input type=\"hidden\" name=\"checkName\" value=\"" + name + "[" + pIdIndex
                            + "].selected" + "\">" );
                    }

                    sb.append( "<input class=\"" + currentStyle + "\" type=\"checkbox\" name=\"" + name + "["
                        + pIdIndex + "].selected" + "\" value=\"true\" onclick='check(this);'" );

                    if ( ( (WISelectable) o ).isSelected() )
                    {
                        sb.append( " checked " );
                    }

                    sb.append( "></td>" );
                }
                else
                {
                    sb.append( "<td " );
                    sb.append( wdt + " classSelect=\"" + styleselect + "\" classDefault=\"" + style
                        + "\"><input class=\"" + currentStyle + "\" type=\"checkbox\" name=\"" + name + "[" + pIdIndex
                        + "].selected" + "\" value=\"true\" onclick='checkSingle(this);'" );

                    if ( ( (WISelectable) o ).isSelected() )
                    {
                        sb.append( " checked " );
                    }

                    sb.append( "></td>" );
                }
            }
            else
            {
                throw new JspException( "Bean must be of type WFormSelectable or WISelectable : " + o.getClass() );
            }
        }
        return sb.toString();
    }

    /**
     * @param o objet a representer
     * @param index numero de ligne
     * @param style style de la ligne
     * @param styleSelect Style selectionne
     * @param pIdIndex Index
     * @return le html correspondant
     */
    private String writeColumnsValue( final Object o, final int index, final String style, final int pIdIndex,
                                      final String styleSelect )
    {
        final TrimStringBuffer sb = new TrimStringBuffer();

        if ( cols == null )
        {
            cols = new Vector();
        }

        final Enumeration enumeration = cols.elements();

        if ( cols.isEmpty() )
        {
            sb.append( writeColumnsValueEmpty( o ) );
        }
        else
        {
            final int colInOneLine = cols.size() / splitNbLine;
            for ( int i = 1; enumeration.hasMoreElements(); i++ )
            {
                final Col col = (Col) enumeration.nextElement();

                // ecrit la colonne complete
                writeColumnValue( o, index, style, pIdIndex, styleSelect, sb, col );

                // Ajoute le split au niveau des cellules, si on met la table sur plusieurs lignes
                if ( ( ( i % colInOneLine ) == 0 ) && ( i != 1 ) && ( splitNbLine > 1 ) )
                {
                    sb.append( "</tr>" );
                    sb.append( "<tr class=\"" );
                    sb.append( style );
                    sb.append( "\">" );

                    if ( isSelectCheckBox() )
                    {
                        sb.append( "<td>&nbsp;</td>" );
                    }
                }
            }
        }

        return sb.toString();
    }

    /**
     * Ecrit la cellule de la table
     * 
     * @param o objet a representer
     * @param index index numero de ligne
     * @param style style style de la ligne
     * @param pIdIndex Index
     * @param styleSelect Style selectionne
     * @param sb StringBuffer
     * @param col Colonne
     */
    private void writeColumnValue( final Object o, final int index, final String style, final int pIdIndex,
                                   final String styleSelect, final TrimStringBuffer sb, final Col col )
    {
        internalValue = "";
        if ( col.isWriteTD() )
        {
            sb.append( "\t\t<td " );

            if ( !GenericValidator.isBlankOrNull( col.getContentStyle() ) )
            {
                sb.append( "style=\"" );

                if ( WelcomConfigurator.getCharte() == Charte.V2_001 )
                {
                    sb.append( "padding-left:2px;" );
                }
                sb.append( col.getContentStyle() );
                sb.append( "\" " );
            }

            try
            {
                getColumnValue( o, col );
            }
            catch ( final JspException e )
            {
                log.error( e, e );
            }
            if ( InternalTableUtil.isTruncated( internalValue, col.getContentTruncate(), col.getType() ) )
            {
                sb.append( "title=\"" );
                sb.append( internalValue );
                sb.append( "\" " );
            }

            if ( !GenericValidator.isBlankOrNull( col.getContentClass() ) )
            {
                sb.append( "class=\"" );
                sb.append( col.getContentClass() );
                sb.append( "\" " );
            }

            if ( ( table.isDisplayHeader() == false ) && !GenericValidator.isBlankOrNull( col.getWidth() ) )
            {
                sb.append( "width=\"" + col.getWidth() + "\" " );
            }

            sb.append( "\t\t>" );
        }

        if ( col.isContentNoWrap() )
        {
            sb.append( "<nobr>" );
            sb.append( writeContentColumnValue( o, col, getPosition( index ), pIdIndex, style, styleSelect ) );
            sb.append( "</nobr>" );
        }
        else
        {
            sb.append( writeContentColumnValue( o, col, getPosition( index ), pIdIndex, style, styleSelect ) );
        }

        if ( col.isWriteTD() )
        {
            sb.append( "</td>\n" );
        }
    }

    /**
     * Retourn la postion en fonction de l'index de la table
     * 
     * @param index index de la colonne
     * @return Cols.NONE ou cols.FIRST o Cols.LAST
     */
    private int getPosition( final int index )
    {
        int position = Cols.NONE;

        if ( index == 0 )
        {
            position = Cols.FIRST;
        }

        if ( index == ( table.getCollection().size() - 1 ) )
        {
            position = Cols.LAST;
        }
        return position;
    }

    /**
     * Retourne la colonnevide
     * 
     * @param o object
     * @return retoune comment est ecrit la ligne si l'objet vide, s'il est null on met chartev2.default.char.if.empty
     */
    private String writeColumnsValueEmpty( final Object o )
    {
        final TrimStringBuffer sb = new TrimStringBuffer();

        sb.append( "\t\t<td>\n" );

        if ( o == null )
        {
            sb.append( WelcomConfigurator.getMessageWithCfgChartePrefix( ".default.char.if.empty" ) );
        }
        else
        {
            sb.append( o.toString() );
        }

        sb.append( "</td>\n" );

        return sb.toString();
    }

    /**
     * @param o l'object a representer
     * @param col colonne a representer
     * @throws JspException exception pouvant etre levee
     */
    private void getColumnValue( final Object o, final Col col )
        throws JspException
    {
        Object ovalue = null;
        if ( GenericValidator.isBlankOrNull( internalValue ) )
        {
            if ( !GenericValidator.isBlankOrNull( col.getProperty() ) )
            {
                ovalue = LayoutUtils.getProperty( o, col.getProperty() );
                if ( ovalue != null )
                {
                    if ( !( ovalue instanceof Date ) )
                    {
                        internalValue = ResponseUtils.filter( ovalue.toString() );
                    }
                }
                else
                {
                    // Recupere celle spécifié
                    if ( !GenericValidator.isBlankOrNull( col.getEmptyKey() ) )
                    {
                        internalValue = col.getEmptyKey();
                    }
                    else
                    {
                        internalValue = WelcomConfigurator.getMessageWithCfgChartePrefix( ".default.char.if.empty" );
                    }
                }
                // Si on est sur une date on doit faire un traitement spécifique
                if ( ( col.getType() != null ) && ( col.getType().equals( ColComparator.TYPE_DATE ) ) )
                {
                    if ( ( ovalue instanceof Date ) || ( ( ( DateUtil.parseAllDate( internalValue ) ) ) != null ) )
                    {
                        SimpleDateFormat df;
                        if ( col.getDateFormatKey() != null )
                        {
                            df =
                                new SimpleDateFormat( table.getResources().getMessage( table.getLocaleRequest(),
                                                                                       col.getDateFormatKey() ),
                                                      table.getLocaleRequest() );
                        }
                        else if ( col.getDateFormat() != null )
                        {
                            df = new SimpleDateFormat( col.getDateFormat(), table.getLocaleRequest() );
                        }
                        else
                        {
                            df = Util.formatDtHr;
                        }
                        if ( ovalue instanceof Date )
                        {
                            internalValue = df.format( (Date) ovalue );
                        }
                        else
                        {
                            internalValue = df.format( DateUtil.parseAllDate( internalValue ) );
                        }
                    }
                }
            }
        }
    }

    /**
     * @param position Position ou se trouve le curseur
     * @param o objet a representer
     * @param col colonne a representer
     * @param index Index
     * @param style Style
     * @param styleSelect Style selectionne
     * @return html correspondant a la cellule : objet o colonne col
     */
    private String writeContentColumnValue( final Object o, final Col col, final int position, final int index,
                                            final String style, final String styleSelect )
    {
        try
        {
            final String val = col.getCurrentValue( position, o, index, style, styleSelect, table.getPageLength() );

            if ( !GenericValidator.isBlankOrNull( val ) )
            {
                return val;
            }
            getColumnValue( o, col );

            if ( GenericValidator.isBlankOrNull( internalValue ) )
            {
                return WelcomConfigurator.getMessageWithCfgChartePrefix( ".default.char.if.empty" );
            }

            if ( ( o instanceof WIEditable ) && ( (WIEditable) o ).isEdited() && col.isEditable() )
            {
                final String ovalue = internalValue;
                internalValue =
                    "<input type=\"text\" name=\"" + getPropertyFull( index ) + "." + col.getProperty() + "\"";
                if ( !GenericValidator.isBlankOrNull( ovalue ) )
                {
                    internalValue += " value=\"" + ResponseUtils.filter( ovalue.toString() ) + "\"";
                }
                internalValue += ">";
            }
            else
            {

                pageContext.setAttribute( id, o );

                final String href = col.getHref();
                pageContext.removeAttribute( id );

                internalValue =
                    InternalTableUtil.getTruncatedString( internalValue, col.getContentTruncate(), col.getType() );

                if ( href != null )
                {
                    internalValue = "<A HREF='" + href + "'>" + internalValue + "</A>";
                }
            }

            return internalValue;
        }
        catch ( final Exception e )
        {
            log.error( e, e );

            return null;
        }
    }

    /**
     * @return true si la ligne est selectionnable (bean pas besoin d'etre en session)
     */
    private boolean isSelectTr()
    {
        final String pageAccess = (String) pageContext.getAttribute( "access" );
        boolean res = false;
        boolean test = false;
        test = !GenericValidator.isBlankOrNull( selectProperty );

        if ( test )
        {
            res = true;

            if ( pageAccess.equals( Access.READONLY ) && !forceReadWrite )
            {
                res = false;
            }
            else
            {
                res = true;
            }
        }

        return res;
    }

    /**
     * @return true si la ligne est selectionnable (le bean doit etre en session)
     */
    private boolean isSelectCheckBox()
    {
        final String pageAccess = (String) pageContext.getAttribute( "access" );
        boolean res = false;
        boolean test = false;
        test = ( selectable == true );

        if ( test )
        {
            res = true;

            if ( ( pageAccess != null ) && pageAccess.equals( Access.READONLY ) && !forceReadWrite )
            {
                res = false;
            }
            else
            {
                res = true;
            }
        }

        return res;
    }

    /**
     * @param index la position de la colonne
     * @return La chaine complete
     */
    public String getPropertyFull( final int index )
    {

        final String tableName = getTable().getName();
        final String tableProperty = getTable().getProperty();
        final String myId = Integer.toString( index );

        final StringBuffer sb = new StringBuffer();
        if ( Util.isNonEquals( getFormName(), tableName ) )
        {
            sb.append( tableName + "." );
        }
        sb.append( tableProperty );
        sb.append( "[" );
        sb.append( myId );
        sb.append( "]" );

        return sb.toString();

    }

    /**
     * @return id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @return pageContext
     */
    public PageContext getPageContext()
    {
        return pageContext;
    }

    /**
     * @param string le nouvel id
     */
    public void setId( final String string )
    {
        id = string;
    }

    /**
     * @param context le nouveau pageContext
     */
    public void setPageContext( final PageContext context )
    {
        pageContext = context;
    }

    /**
     * @return table
     */
    public TableTag getTable()
    {
        return table;
    }

    /**
     * @param tag la nouvelle table
     */
    public void setTable( final TableTag tag )
    {
        table = tag;
    }

    /**
     * @return selectProperty
     */
    public String getSelectProperty()
    {
        return selectProperty;
    }

    /**
     * @param string le nouveau selectProperty
     */
    public void setSelectProperty( final String string )
    {
        selectProperty = string;
    }

    /**
     * @return forceReadWrite
     */
    public boolean isForceReadWrite()
    {
        return forceReadWrite;
    }

    /**
     * @param b le nouveau forceReadWrite
     */
    public void setForceReadWrite( final boolean b )
    {
        forceReadWrite = b;
    }

    /**
     * @return enableSingleSelect
     */
    public boolean isEnableSingleSelect()
    {
        return enableSingleSelect;
    }

    /**
     * @param b le nouveau enableSingleSelect
     */
    public void setEnableSingleSelect( final boolean b )
    {
        enableSingleSelect = b;
    }

    /**
     * @return selectable
     */
    public boolean isSelectable()
    {
        return selectable;
    }

    /**
     * @param b le nouveau selectable
     */
    public void setSelectable( final boolean b )
    {
        selectable = b;
    }

    /**
     * @return splitNbLine
     */
    public int getSplitNbLine()
    {
        return splitNbLine;
    }

    /**
     * @param i le nouveau splitNbLine
     */
    public void setSplitNbLine( final int i )
    {
        splitNbLine = i;
    }

    /**
     * @return taille du vecteur cols
     */
    public int size()
    {
        if ( cols != null )
        {
            return cols.size();
        }
        else
        {
            return 0;
        }
    }

    /**
     * @return accesseur
     */
    public String getIdIndex()
    {
        return idIndex;
    }

    /**
     * @param string accesseur
     */
    public void setIdIndex( final String string )
    {
        idIndex = string;
    }

    /**
     * @return Nom du formulaire
     */
    public String getFormName()
    {
        if ( GenericValidator.isBlankOrNull( formName ) )
        {
            formName = getTable().getFormName();
        }
        return formName;
    }

}