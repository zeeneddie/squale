/*
 * CrÚÚ le 13 avr. 04
 *
 * Pour changer le modÞle de ce fichier gÚnÚrÚ, allez Ó :
 * FenÛtre&gt;PrÚfÚrences&gt;Java&gt;GÚnÚration de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.table;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.ResponseUtils;

import com.airfrance.welcom.outils.Access;
import com.airfrance.welcom.outils.Charte;
import com.airfrance.welcom.outils.TrimStringBuffer;
import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.taglib.html.FormTag;
import com.airfrance.welcom.taglib.renderer.RendererFactory;

/**
 * @author M327837 Pour changer le modÞle de ce commentaire de type gÚnÚrÚ, allez Ó :
 *         FenÛtre&gt;PrÚfÚrences&gt;Java&gt;GÚnÚration de code&gt;Code et commentaires
 */
public class ColsTag
    extends BodyTagSupport
{

    /**
     * 
     */
    private static final long serialVersionUID = -7301192558063262845L;

    /** Style class Ligne paire */
    private String classLignePaire = "";

    /** Style Class Ligne Impaire */
    private String classLigneImpaire = "";

    /** Collection pour l'affichage */
    private Vector collectionForDisplay = null;

    /** Collection pour la mise en memoire */
    private Collection collectionForMemory = null;

    /** Correlction affichage memoire */
    private HashMap relationDisplayIdMemory = null;

    /** Tag table */
    private TableTag tableTag = null;

    /** Count */
    private int idCount = 0;

    /** Premier iteration */
    private boolean firstIterate = true;

    /** Les colonnes */
    private Cols currentLine = null;

    /** Table de trie */
    private ListColumnSort keySort = null;

    /** Message resources */
    protected MessageResources resources = null;

    /** La locale */
    protected Locale localeRequest = Locale.FRENCH;

    /** Nom de la table */
    private String name = "";

    /** Id : nom de l'element transitant dans l'iteration */
    private String id = "";

    /** Nom de l'index reel circulant */
    private String idIndex = "index";

    /** nom de la propritÚ pour la selection */
    private String selectProperty = "";

    /** Si la liste est selectionnable */
    private boolean selectable = false;

    /** Force l'accÚs en lecture/ecriture */
    private String forceReadWrite = "false";

    /** Action le signel select */
    private String enableSingleSelect = "false";

    /** Decoupe la table sur 2 lignes */
    private int splitNbLine = 1;

    /** Clef si vide */
    private String emptyKey = "";

    /** render */
    private static ITableRenderer render = (ITableRenderer) RendererFactory.getRenderer( RendererFactory.TABLE );

    /** Contructeur */
    public ColsTag()
    {
    }

    /**
     * Ajoute un cellule
     * 
     * @param col : colonne
     */
    public void addCellule( final Col col )
    {
        if ( currentLine == null )
        {
            initCurrentLine();
        }

        currentLine.addCellAtCurrentLine( col );
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag()
        throws JspException
    {
        final TrimStringBuffer sb = new TrimStringBuffer();

        if ( !( collectionForDisplay.isEmpty() )
            || ( collectionForDisplay.isEmpty() && !GenericValidator.isBlankOrNull( emptyKey ) ) )
        {
            sb.append( getBodyContent().getString().trim() );
            sb.append( render.drawTableEnd() );
            ResponseUtils.write( pageContext, sb.toString() );
        }

        if ( tableTag.getScrollHeight() != 0 )
        {
            if ( !( collectionForDisplay.isEmpty() )
                || ( collectionForDisplay.isEmpty() && !GenericValidator.isBlankOrNull( emptyKey ) ) )
            {
                ResponseUtils.write( pageContext, "</div>" );
            }
        }
        release();
        return EVAL_PAGE;
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag()
        throws JspException
    {
        // Recherche si un parent est du bon type
        Tag curParent = null;

        for ( curParent = getParent(); ( curParent != null ) && !( curParent instanceof TableTag ); )
        {
            curParent = curParent.getParent();
        }

        tableTag = (TableTag) curParent;

        if ( tableTag == null )
        {
            throw new JspException( "ColsTag  must be used between Table Tag." );
        }

        collectionForDisplay = tableTag.getCollectionForDisplay();
        collectionForMemory = tableTag.getCollection();
        keySort = tableTag.getListColumnSort();
        resources = tableTag.getResources();
        localeRequest = tableTag.getLocaleRequest();
        name = tableTag.getName();
        emptyKey = tableTag.getEmptyKey();

        if ( !( collectionForDisplay.isEmpty() ) || !GenericValidator.isBlankOrNull( emptyKey ) )
        {
            tableTag.writePixGris();

            return EVAL_BODY_AGAIN;
        }
        else
        {
            return SKIP_BODY;
        }
    }

    /**
     * @see javax.servlet.jsp.tagext.BodyTagSupport#doInitBody()
     */
    public void doInitBody()
        throws JspException
    {
        final TrimStringBuffer sb = new TrimStringBuffer();

        String nom = tableTag.getName();

        if ( !GenericValidator.isBlankOrNull( tableTag.getProperty() ) )
        {
            nom += ( "." + tableTag.getProperty() );
        }

        if ( tableTag.getScrollHeight() != 0 )
        {
            sb.append( render.drawTableStart( null, tableTag.getWidth() ) );
        }
        else
        {
            sb.append( render.drawTableStart( getTableName(), tableTag.getWidth() ) );
        }

        ResponseUtils.write( pageContext, sb.toString() );

        if ( !( collectionForMemory instanceof HTMLTable ) )
        {
            idCount = 0;
        }
        else
        {
            idCount = ( (HTMLTable) collectionForMemory ).getFrom();
        }

        // Charge le tableau associatif des Object d'afficagae avec les object Memoire
        initRelationDisplayIdMemory();

        Object o = null;

        // Si la collection n'est pas vide alors passes l'object sinon il est null
        if ( !collectionForDisplay.isEmpty() )
        {
            o = collectionForDisplay.elementAt( idCount );
            pageContext.setAttribute( getId(), o, PageContext.PAGE_SCOPE );
            pageContext.setAttribute( getIdIndex(), (Integer) relationDisplayIdMemory.get( o ), PageContext.PAGE_SCOPE );
        }
    }

    /**
     * @return RÚcuperation du nom de la table
     */
    private String getTableName()
    {
        // RÚcuperation du nom de la table
        String tableName = tableTag.getName();

        if ( !GenericValidator.isBlankOrNull( tableTag.getProperty() ) )
        {
            tableName = tableName + "." + tableTag.getProperty();
        }

        return tableName;
    }

    /**
     * @see javax.servlet.jsp.tagext.BodyTagSupport#doAfterBody()
     */
    public int doAfterBody()
        throws JspException
    {
        boolean autoDescription = false;

        // Blindage
        if ( currentLine == null )
        {
            // Vide les colonnes car le retourve a chaque fois
            currentLine = new Cols();
        }

        // Genere tout les columns
        if ( currentLine.isEmpty() )
        {
            currentLine.genAllCols( collectionForDisplay.elementAt( 0 ).getClass() );
            autoDescription = true;
        }

        if ( firstIterate )
        {
            if ( ( ( collectionForMemory != null ) && !collectionForMemory.isEmpty() )
                || ( ( collectionForMemory != null ) && collectionForMemory.isEmpty() && !GenericValidator.isBlankOrNull( emptyKey ) ) )
            {
                if ( tableTag.isDisplayHeader() )
                {
                    ResponseUtils.write( pageContext, tableTitre() );
                }

                // Cherche pour l'inclusion de table
                if ( tableTag.getScrollHeight() != 0 )
                {
                    if ( !( collectionForDisplay.isEmpty() )
                        || ( collectionForDisplay.isEmpty() && !GenericValidator.isBlankOrNull( emptyKey ) ) )
                    {
                        ResponseUtils.write( pageContext, "</table>" );
                        ResponseUtils.write( pageContext, "<div style=\"height:" + tableTag.getScrollHeight()
                            + "px;overflow:auto;\">" );

                        ResponseUtils.write( pageContext, render.drawTableStart( getTableName(), tableTag.getWidth() ) );

                        tableTag.setDisplayHeader( false );
                    }
                }

                if ( autoDescription )
                {
                    idCount = 0;
                    // Vide les colonnes car le retourve a chaque fois
                    currentLine = new Cols();
                    initCurrentLine();
                    firstIterate = false;

                    return EVAL_BODY_AGAIN;
                }
            }

            firstIterate = false;
        }

        final TrimStringBuffer sb = new TrimStringBuffer();

        // Si la table est vide affiche la ligne et part
        if ( collectionForMemory.isEmpty() && !GenericValidator.isBlankOrNull( emptyKey ) )
        {
            final String myClassLignePaire =
                WelcomConfigurator.getMessage( WelcomConfigurator.getCharte().getWelcomConfigFullPrefix()
                    + ".cols.even" );
            sb.append( "<tr class=\"" + myClassLignePaire + "\">" );

            if ( ( isSelectTr() || isSelectCheckBox() ) )
            {
                sb.append( "<td></td>" );
            }

            sb.append( "<td colspan=\"" + currentLine.size() + "\">" );

            final String emptyMessage = resources.getMessage( localeRequest, emptyKey );

            if ( emptyMessage != null )
            {
                sb.append( ResponseUtils.filter( emptyMessage ) );
            }
            else
            {
                sb.append( ResponseUtils.filter( emptyKey ) );
            }

            sb.append( "</tr>" );
            ResponseUtils.write( pageContext, sb.toString() );

            return EVAL_PAGE;
        }

        initCurrentLine();
        sb.append( currentLine.tabletrCorps(
                                             idCount,
                                             collectionForDisplay.elementAt( idCount ),
                                             (Integer) relationDisplayIdMemory.get( collectionForDisplay.elementAt( idCount ) ) ) );
        ResponseUtils.write( pageContext, sb.toString() );

        // Suppresion de l'aobjet courant
        pageContext.removeAttribute( getId() );
        pageContext.removeAttribute( getIdIndex() );
        currentLine = new Cols(); // Vide les colonnes car le retourve a chaque fois

        idCount++;

        if ( !( collectionForMemory instanceof HTMLTable ) )
        {
            if ( collectionForDisplay.size() > idCount )
            {
                final Object o = collectionForDisplay.elementAt( idCount );
                pageContext.setAttribute( getId(), o, PageContext.PAGE_SCOPE );
                pageContext.setAttribute( getIdIndex(), (Integer) relationDisplayIdMemory.get( o ),
                                          PageContext.PAGE_SCOPE );

                return EVAL_BODY_AGAIN;
            }
        }
        else
        {
            if ( ( collectionForDisplay.size() > idCount )
                && ( idCount < ( ( (HTMLTable) collectionForMemory ).getLength() + ( (HTMLTable) collectionForMemory ).getFrom() ) ) )
            {
                final Object o = collectionForDisplay.elementAt( idCount );
                pageContext.setAttribute( getId(), o, PageContext.PAGE_SCOPE );
                pageContext.setAttribute( getIdIndex(), (Integer) relationDisplayIdMemory.get( o ),
                                          PageContext.PAGE_SCOPE );

                return EVAL_BODY_AGAIN;
            }
        }

        return EVAL_PAGE;

        // ResponseUtils.write(pageContext,sb.toString());
    }

    /**
     * Initialise une nouvelle ligne
     */
    private void initCurrentLine()
    {
        if ( currentLine == null )
        {
            currentLine = new Cols();
        }

        currentLine.setPageContext( pageContext );
        currentLine.setSelectProperty( selectProperty );
        currentLine.setSelectable( selectable );
        currentLine.setSplitNbLine( splitNbLine );
        currentLine.setIdIndex( idIndex );

        if ( Util.isTrue( forceReadWrite ) )
        {
            currentLine.setForceReadWrite( true );
        }
        else
        {
            currentLine.setForceReadWrite( false );
        }

        if ( Util.isTrue( enableSingleSelect ) )
        {
            currentLine.setEnableSingleSelect( true );
        }
        else
        {
            currentLine.setEnableSingleSelect( false );
        }

        currentLine.setId( id );
        currentLine.setTable( tableTag );
    }

    /**
     * @return Ecriture dans une chaine, les entetes (tr)
     */
    public String tableTitre()
    {
        return render.drawTableTitle( writeColumnsTitre() );
    }

    /**
     * @return Ecrture de tous les th des colonnes
     */
    private String writeColumnsTitre()
    {
        final TrimStringBuffer sb = new TrimStringBuffer();

        if ( currentLine == null )
        {
            return "";
        }

        if ( currentLine.isEmpty() )
        {
            sb.append( "\t\t<th>\n" );
            sb.append( "\t\t" );
            sb.append( "To String" );
            sb.append( "&nbsp;&nbsp;&nbsp;\n" );
            sb.append( "\t\t</th>\n" );
        }
        else
        {
            if ( isSelectTr() || isSelectCheckBox() )
            {
                sb.append( "\t\t<th width=\"24px\">&nbsp;</th>" );
            }

            final int colInOneLine = currentLine.size() / splitNbLine;
            final Enumeration enumeration = currentLine.elements();

            for ( int i = 1; enumeration.hasMoreElements(); i++ )
            {
                final Col col = (Col) enumeration.nextElement();
                writeColumnTitre( sb, colInOneLine, i, col );
            }
        }

        return sb.toString();
    }

    /**
     * @return Recherche le nom du Form
     */
    public String getFormName()
    {
        // Recherche le nom du Form
        final FormTag formTag = (FormTag) pageContext.getRequest().getAttribute( "org.apache.struts.taglib.html.FORM" );

        if ( formTag != null )
        {
            return formTag.getFormName();
        }
        //
        // Tag curParent = null;
        //
        // for (curParent = getParent();(curParent != null) && !(curParent instanceof FormTag); curParent =
        // curParent.getParent()) {
        // ;
        // }
        //
        // if (curParent != null) {
        // final FormTag formTag = ((FormTag) curParent);
        //
        // return formTag.getFormName();
        // }

        return null;
    }

    /**
     * @return Nom de la servlet a utiliser pour le trie
     */
    protected String getServletName()
    {
        String servletName = "";
        if ( !GenericValidator.isBlankOrNull( tableTag.getCallBackUrl() ) )
        {
            if ( tableTag.getCallBackUrl().indexOf( '?' ) > 0 )
            {
                servletName = tableTag.getCallBackUrl() + "&action=sort&";
            }
            else
            {
                servletName = tableTag.getCallBackUrl() + "?action=sort&";
            }
        }
        else
        {
            servletName = Util.SERVEPATH + "?";
        }
        return servletName;
    }

    /**
     * Retourn si la colonne est sortable
     * 
     * @param col colonne
     * @return si la colonne est triable
     */
    private boolean isColumnSortableTitre( Col col )
    {
        return col.isSortable() && ( collectionForMemory instanceof HTMLTable );
    }

    /**
     * Ecrit le titre d'un colonne
     * 
     * @param sb : flux de sortie
     * @param colInOneLine : nombre de ligne pour l'entete
     * @param i : id
     * @param col : Colonne
     */
    private void writeColumnTitre( final TrimStringBuffer sb, final int colInOneLine, final int i, final Col col )
    {
        final ColSort colSort = keySort.getColSort( col );

        ColHeader colHeader = new ColHeader( resources, localeRequest, col, colSort );

        if ( isColumnSortableTitre( col ) )
        {
            // sb.append("\t\t<th class=\""+colSort.getSort()+"\"");
            sb.append( "\t\t<th class=\"sort" );

            if ( !GenericValidator.isBlankOrNull( col.getHeaderClass() ) )
            {
                sb.append( " " );
                sb.append( col.getHeaderClass() );
                sb.append( "\"" );
            }

            sb.append( "\"" );

            if ( InternalTableUtil.isTruncated( colHeader.getLibelle(), col.getHeaderTruncate(), null ) )
            {
                sb.append( " title=\"" );
                sb.append( colHeader.getLibelle() );
                sb.append( "\"" );
            }

            if ( tableTag.getScrollHeight() != 0 )
            {
                if ( WelcomConfigurator.getCharte() == Charte.V2_002 )
                {
                    sb.append( " style=\"padding-left:14px\"" );
                }
            }

        }
        else
        {
            sb.append( "\t\t<th" );

            if ( !GenericValidator.isBlankOrNull( col.getHeaderClass() ) )
            {
                sb.append( " class=\"" );
                sb.append( col.getHeaderClass() );
                sb.append( "\" " );
            }

        }

        if ( !GenericValidator.isBlankOrNull( col.getHeaderStyle() ) )
        {
            sb.append( " style=\"" );
            sb.append( col.getHeaderStyle() );
            sb.append( "\"" );
        }

        if ( !GenericValidator.isBlankOrNull( col.getWidth() ) )
        {
            sb.append( " width=\"" + col.getWidth() + "\"" );
        }

        sb.append( ">" );

        sb.append( getTitre( colHeader ) );

        sb.append( "</th>\n" );

        /** Calcule s'il doit splitter la table sur plusieurs lignes */
        if ( ( ( i % colInOneLine ) == 0 ) && ( i != 1 ) && ( splitNbLine > 1 ) )
        {
            if ( WelcomConfigurator.getCharte() == Charte.V2_002 )
            {
                sb.append( "</tr><tr class=\"haut\">" );
            }
            else if ( WelcomConfigurator.getCharte() == Charte.V2_001 )
            {
                sb.append( "</tr><tr class=\"trtete\">" );
            }
            else if ( WelcomConfigurator.getCharte() == Charte.V3_001 )
            {
                sb.append( "</tr><tr>" );
            }
            else
            {
                sb.append( "</tr>" );
            }
        }

    }

    /**
     * Retourn le libelle avec l'html necessaire, pour le tri eou pas
     * 
     * @param colHeader Header de la colonne
     * @return le libelle avec l'html necessaire
     */
    private String getTitre( ColHeader colHeader )
    {
        if ( isColumnSortableTitre( colHeader.getCol() ) )
        {
            return getSortedTitre( colHeader );
        }
        else
        {
            return getNoSortedTitre( colHeader );
        }
    }

    /**
     * Retourn le libelle avec l'html necessaire
     * 
     * @param colHeader Header de la colonne
     * @return le libelle avec l'html necessaire
     */
    private String getNoSortedTitre( ColHeader colHeader )
    {
        // string buffer
        StringBuffer sb = new StringBuffer();

        if ( colHeader.isNoWrap() )
        {
            sb.append( "<nobr>" );
        }
        sb.append( colHeader.getTuncatedIfNecessaryLibelle() );

        if ( colHeader.isNoWrap() )
        {
            sb.append( "</nobr>" );
        }

        return sb.toString();
    }

    /**
     * Retourne le libelle avec l'html necessaire si c'est un lien ou pas
     * 
     * @param colHeader Header de la colonne
     * @return le libelle avec l'html necessaire si c'est un lien ou pas
     */
    private String getSortedTitre( ColHeader colHeader )
    {
        // string buffer
        StringBuffer sb = new StringBuffer();

        // Url de trie
        String urlSort =
            colHeader.getSortUrl( tableTag, getServletName(), ( (HTMLTable) collectionForMemory ).getFrom() );

        // nom du formulaire
        String formName = getFormName();

        if ( !GenericValidator.isBlankOrNull( formName ) )
        {
            sb.append( "<span class=\"href\" onclick=\"" );
            sb.append( "javascript:tableForward('" );
            sb.append( formName );
            sb.append( "','" );
            sb.append( urlSort );
            sb.append( "')\" >" );
        }
        else
        {
            sb.append( "<a href=\"" );
            sb.append( urlSort );
            sb.append( "\" >" );
        }
        if ( colHeader.isNoWrap() )
        {
            sb.append( "<nobr>" );
        }
        sb.append( colHeader.getTuncatedIfNecessaryLibelle() );

        sb.append( "<img align=\"absmiddle\" border=\"0\" src=\"" + colHeader.getSrcImgOfSort() + "\">" );

        if ( colHeader.isNoWrap() )
        {
            sb.append( "</nobr>" );
        }
        if ( !GenericValidator.isBlankOrNull( formName ) )
        {
            sb.append( "</span>\n" );
        }
        else
        {
            sb.append( "</a>\n" );
        }

        return sb.toString();
    }

    /**
     * @return Vrai si on est en mode de selection TR pour la case a cochÚ
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

            if ( pageAccess.equals( Access.READONLY ) && Util.isFalse( forceReadWrite ) )
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
     * @return Vrai si on est en mode classique pour la gestion des checkbox
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

            if ( ( pageAccess != null ) && pageAccess.equals( Access.READONLY ) && Util.isFalse( forceReadWrite ) )
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
     * @return classLigneImpaire
     */
    public String getClassLigneImpaire()
    {
        return classLigneImpaire;
    }

    /**
     * @return classLignePaire
     */
    public String getClassLignePaire()
    {
        return classLignePaire;
    }

    /**
     * @param string accesseur
     */
    public void setClassLigneImpaire( final String string )
    {
        classLigneImpaire = string;
    }

    /**
     * @param string accesseur
     */
    public void setClassLignePaire( final String string )
    {
        classLignePaire = string;
    }

    /**
     * @return id accesseur
     */
    public String getId()
    {
        return id;
    }

    /**
     * @return idIndex accesseur
     */
    public String getIdIndex()
    {
        return idIndex;
    }

    /**
     * @param string accesseur
     */
    public void setId( final String string )
    {
        id = string;
    }

    /**
     * @param string accesseur
     */
    public void setIdIndex( final String string )
    {
        idIndex = string;
    }

    /**
     * @return selectProperty accesseur
     */
    public String getSelectProperty()
    {
        return selectProperty;
    }

    /**
     * @param string accesseur
     */
    public void setSelectProperty( final String string )
    {
        selectProperty = string;
    }

    /**
     * @return enableSingleSelect accesseur
     */
    public String getEnableSingleSelect()
    {
        return enableSingleSelect;
    }

    /**
     * @param string accesseur
     */
    public void setEnableSingleSelect( final String string )
    {
        enableSingleSelect = string;
    }

    /**
     * @return forceReadWrite accesseur
     */
    public String getForceReadWrite()
    {
        return forceReadWrite;
    }

    /**
     * @param string accesseur
     */
    public void setForceReadWrite( final String string )
    {
        forceReadWrite = string;
    }

    /**
     * @param b accesseur
     */
    public void setSelectable( final boolean b )
    {
        selectable = b;
    }

    /**
     * @return selectable accesseur
     */
    public boolean isSelectable()
    {
        return selectable;
    }

    /**
     * Initialise la relation memoire ...
     */
    private void initRelationDisplayIdMemory()
    {
        int myId = 0;
        relationDisplayIdMemory = new HashMap();

        final Iterator iter = collectionForMemory.iterator();

        while ( iter.hasNext() )
        {
            final Object element = (Object) iter.next();
            relationDisplayIdMemory.put( element, new Integer( myId++ ) );
        }
    }

    /**
     * @see javax.servlet.jsp.tagext.BodyTagSupport#release()
     */
    public void release()
    {
        classLignePaire = "";
        classLigneImpaire = "";
        collectionForDisplay = null;
        collectionForMemory = null;
        relationDisplayIdMemory = null;
        tableTag = null;
        idCount = 0;
        firstIterate = true;
        currentLine = null;
        keySort = null;
        name = "";
        id = "";
        idIndex = "index";
        selectProperty = "";
        selectable = false;
        forceReadWrite = "false";
        enableSingleSelect = "false";
        super.release();
    }

    /**
     * @return splitNbLine accesseur
     */
    public int getSplitNbLine()
    {
        return splitNbLine;
    }

    /**
     * @param i accesseur
     */
    public void setSplitNbLine( final int i )
    {
        splitNbLine = i;
    }

    /**
     * @return emptyKey accesseur
     */
    public String getEmptyKey()
    {
        return emptyKey;
    }

    /**
     * @param string accesseur
     */
    public void setEmptyKey( final String string )
    {
        emptyKey = string;
    }
}