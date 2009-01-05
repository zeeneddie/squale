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
 * Créé le 30 mars 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.table;

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

import com.airfrance.welcom.outils.Charte;
import com.airfrance.welcom.outils.TrimStringBuffer;
import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.taglib.html.FormTag;
import com.airfrance.welcom.taglib.renderer.RendererFactory;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class TableTag
    extends BodyTagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = -3610096801669395035L;

    /** logger */
    private static Log log = LogFactory.getLog( TableTag.class );

    /** Constante */
    public final static int DEFAULT_PAGE_LENGTH = 10;

    /** Constante */
    private final static int DEFAULT_OFFSET = 0;

    /** Constante */
    private final static String DEFAULT_TOTAL_LABEL_KEY = "welcom.internal.table.total";

    /** Constante */
    private final static String TOP = "top";

    /** Constante */
    private final static String BOTTOM = "bottom";

    /** Constante */
    private final static String BOTH = "topbottom";

    /** parametre du tag */
    private boolean optimizeHTML = true;

    /** parametre du tag */
    private String totalLabelKey = DEFAULT_TOTAL_LABEL_KEY;

    /** parametre du tag */
    private String totalLabelPos = BOTH;

    /** parametre du tag */
    private String paginationPos = BOTH;

    /** parametre du tag */
    private Collection collection = null;

    /** parametre du tag */
    private Vector collectionForDisplay = null;

    /** parametre du tag */
    private String name = "";

    /** parametre du tag */
    protected MessageResources resources = null;

    /** parametre du tag */
    protected Locale localeRequest = Locale.FRENCH;

    /** parametre du tag */
    private boolean displayNavigation = true;

    /** parametre du tag */
    private String bottomValue = "";

    /** parametre du tag */
    private String property = null;

    /** parametre du tag */
    private String scope = null;

    /** parametre du tag */
    /** parametre du tag */
    private int pageLength = DEFAULT_PAGE_LENGTH;

    /** parametre du tag */
    private int offSet = DEFAULT_OFFSET;

    /** parametre du tag */
    private String pageForward = null;

    /** parametre du tag */
    private String pagePerNavBar = null;

    /** parametre du tag */
    private String requestURI = null;

    /** parametre du tag */
    private String width = "100%";

    /** parametre du tag */
    private String emptyKey = "";

    /** parametre du tag */
    private boolean displayFooter = true;

    /** parametre du tag */
    private boolean displayHeader = true;

    /** parametre du tag */
    private int scrollHeight = 0;

    /** parametre du tag */
    private String callBackUrl = "";

    /** render */
    private static ITableRenderer render = (ITableRenderer) RendererFactory.getRenderer( RendererFactory.TABLE );

    /** NomUnique de la table */
    private String uniqueName = "";

    /** Liste Column Sort */
    private ListColumnSort listColumnSort = null;

    /**
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag()
        throws JspException
    {
        // Recuperer le fichier des Bundle
        resources = (MessageResources) pageContext.getServletContext().getAttribute( Globals.MESSAGES_KEY );
        uniqueName = generateUniqueName();
        // Recupere la locale de la page
        localeRequest = (Locale) pageContext.getSession().getAttribute( Globals.LOCALE_KEY );

        // Recupere le table trie tag
        listColumnSort = ListColumnSort.getKeySortOfTable( pageContext.getSession(), name, property );
        listColumnSort.addColSort( pageContext );

        requestURI = ( (HttpServletRequest) pageContext.getRequest() ).getRequestURI();

        // Recuperation de la collection
        Object o = null;

        try
        {
            o = RequestUtils.lookup( super.pageContext, name, property, scope );
        }
        catch ( final JspException je )
        {
            log.error( je, je );
        }
        finally
        {
            if ( o == null )
            {
                final JspException e =
                    new JspException( "Objet " + name + "," + property + "introuvable dans le scope : " + scope
                        + " ou l'objet est null" );
                RequestUtils.saveException( super.pageContext, e );
                throw e;
            }
        }

        // Object o = LayoutUtils.getBeanFromPageContext(pageContext, name,null);
        if ( !( ( o instanceof Collection ) || ( o instanceof HTMLTable ) ) )
        {
            throw new JspException( "Le bean doit être de type Collection ou HTMLTable" );
        }

        HTMLTable table = null;

        if ( o instanceof HTMLTable )
        {
            table = (HTMLTable) o; // Conserve la table est ses parametres si existe deja
        }
        else
        {
            table = new HTMLTable();
            table.addAll( (Collection) o );
            table.setVolume( table.size() );
            table.setMapping( pageForward );
            table.setPreviousFrom( table.getFrom() );
            table.setLength( pageLength );
        }

        if ( InternalTableUtil.rememberPageNumber( pageContext.getRequest() ) )
        {
            try
            {
                if ( pageContext.getRequest().getParameter( "from" ) != null )
                {
                    offSet = Integer.parseInt( pageContext.getRequest().getParameter( "from" ) );
                }
                else if ( pageContext.getRequest().getParameter( uniqueName + ".from" ) != null )
                {
                    offSet = Integer.parseInt( pageContext.getRequest().getParameter( uniqueName + ".from" ) );
                }
            }
            catch ( final NumberFormatException e )
            {
                try
                {
                    if ( pageContext.getRequest().getParameter( uniqueName + ".savedFrom" ) != null )
                    {
                        offSet = Integer.parseInt( pageContext.getRequest().getParameter( uniqueName + ".savedFrom" ) );
                    }
                }
                catch ( final NumberFormatException ex )
                {
                    offSet = 0; // On concerve l'offeset qui a été proposé en parametres ...
                }
            }
            if ( offSet >= table.getVolume() )
            {
                offSet = new Integer( ( table.getVolume() - 1 ) / table.getLength() ).intValue() * table.getLength();
            }
            table.setFrom( offSet );
        }

        // Affiche touts les elements si on ne veux pas la navigation
        if ( displayNavigation == false )
        {
            table.setLength( table.getVolume() );
        }

        this.collection = table;

        // this.collection = (Collection)o;
        this.collectionForDisplay = new Vector( collection );

        if ( !listColumnSort.isEmpty() )
        {
            Collections.sort( collectionForDisplay, new ColComparator( listColumnSort ) );
        }

        TrimStringBuffer.setTrim( optimizeHTML );

        if ( displayNavigation )
        {
            if ( ( totalLabelPos.indexOf( TOP ) >= 0 ) || ( paginationPos.indexOf( TOP ) >= 0 ) )
            {
                writeTableNavigation( TOP );
            }
        }

        return EVAL_PAGE;
    }

    /**
     * @return le "unique" name
     */
    private String generateUniqueName()
    {
        String nom = "";
        final String tbl = (String) pageContext.getRequest().getAttribute( "welcomTable" );

        if ( GenericValidator.isBlankOrNull( tbl ) )
        {
            pageContext.getRequest().setAttribute( "welcomTable", "1" );
            nom = "welcomTable1";
        }
        else
        {
            final int intdrpdwn = Integer.parseInt( tbl ) + 1;
            nom = "welcomTable" + ( intdrpdwn );
            pageContext.getRequest().setAttribute( "welcomTable", "" + intdrpdwn );
        }

        return nom;
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag()
        throws JspException
    {
        final TrimStringBuffer sb = new TrimStringBuffer();

        if ( getBodyContent() != null )
        {
            sb.append( getBodyContent().getString() );
        }

        if ( ( !( collectionForDisplay.isEmpty() ) || ( collectionForDisplay.isEmpty() && !GenericValidator.isBlankOrNull( emptyKey ) ) )
            && displayFooter )
        {
            sb.append( writeTableBottom() );
        }

        sb.append( "<input type=\"hidden\" name=\"" + uniqueName + ".savedFrom\" value=\"" );
        sb.append( offSet );
        sb.append( "\">" );

        ResponseUtils.write( pageContext, sb.toString() );

        if ( displayNavigation && !( collectionForDisplay.isEmpty() ) )
        {
            if ( ( totalLabelPos.indexOf( BOTTOM ) >= 0 ) || ( paginationPos.indexOf( BOTTOM ) >= 0 ) )
            {
                writeTableNavigation( BOTTOM );
            }

            writePixGris();
        }

        return EVAL_PAGE;
    }

    /**
     * @return la bas de la table
     */
    private String writeTableBottom()
    {
        return render.drawTableBottom( bottomValue, width );
    }

    /**
     * gère la navigation de la table
     * 
     * @param pos la position
     * @throws JspException exception pouvant etre levee
     */
    public void writeTableNavigation( final String pos )
        throws JspException
    {

        String message = null;
        String navigation = null;

        // Recherche du libelle
        if ( totalLabelPos.indexOf( pos ) >= 0 )
        {

            message = getMessageComputed();

        }
        BodyContent tempBody = pageContext.pushBody();

        if ( paginationPos.indexOf( pos ) >= 0 )
        {
            if ( collection instanceof HTMLTable )
            {
                final HTMLTable ht = (HTMLTable) collection;

                if ( ( ht.getVolume() > 0 ) && ( ht.getVolume() > ht.getLength() ) )
                {

                    final TableNavigatorTag nav = new TableNavigatorTag();
                    nav.setPageContext( pageContext );
                    nav.setParent( this );
                    nav.setName( uniqueName );
                    nav.setTable( ht );
                    nav.setPagesPerNavBar( pagePerNavBar );
                    nav.setCallBackUrl( getCallBackUrl() );
                    nav.doStartTag();
                    nav.doEndTag();
                }
            }
        }
        pageContext.popBody();

        navigation = tempBody.getString();

        ResponseUtils.write( pageContext, render.drawNavigation( message, navigation, width ) );
    }

    /**
     * @return retourne le message calculé Va voir en premier si on a definit une clef, alors recupere celle-ci, sinon,
     *         prend celle dans l' applicationresource.properties
     */
    private String getMessageComputed()
    {
        String message;
        if ( !GenericValidator.isBlankOrNull( totalLabelKey ) )
        {
            message = resources.getMessage( localeRequest, totalLabelKey, Integer.toString( collection.size() ) );
        }
        else
        {
            message =
                resources.getMessage( localeRequest, DEFAULT_TOTAL_LABEL_KEY, Integer.toString( collection.size() ) );
        }

        if ( message == null )
        {
            message = totalLabelKey;
        }
        return message;
    }

    /**
     * Cas de la charte 001
     * 
     * @throws JspException exception pouvant etre levee
     */
    public void writePixGris()
        throws JspException
    {
        if ( WelcomConfigurator.getCharte() == Charte.V2_001 )
        {
            String imgPixGrey = WelcomConfigurator.getMessage( "chartev2.pix_grey.gif" );
            final TrimStringBuffer sb = new TrimStringBuffer();
            sb.append( "<table class=\"noborder\" width=\"" + width + "\">\n" );
            sb.append( "\t <tr>\n" );
            sb.append( "\t\t <td height=\"1\"><img src=\"" + imgPixGrey + "\" height=\"1\" width=\"100%\"></td> \n" );
            sb.append( "\t </tr>\n" );
            sb.append( "</table>\n" );
            ResponseUtils.write( pageContext, sb.toString() );
        }
    }

    /**
     * @return Recherche le nom du Form
     */
    public String getFormName()
    {
        // Recherche le nom du Form
        Tag curParent = null;

        for ( curParent = getParent(); ( curParent != null ) && !( curParent instanceof FormTag ); curParent =
            curParent.getParent() )
        {
            ;
        }

        if ( curParent != null )
        {
            final FormTag formTag = ( (FormTag) curParent );

            return formTag.getFormName();
        }

        return null;
    }

    /**
     * @return collection
     */
    public Collection getCollection()
    {
        return collection;
    }

    /**
     * @param pCollection la nouvelle collection
     */
    public void setCollection( final Collection pCollection )
    {
        collection = pCollection;
    }

    /**
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param string le nouveau name
     */
    public void setName( final String string )
    {
        name = string;
    }

    /**
     * @return optimizeHTML
     */
    public boolean isOptimizeHTML()
    {
        return optimizeHTML;
    }

    /**
     * @param b le nouveau optimizeHTML
     */
    public void setOptimizeHTML( final boolean b )
    {
        optimizeHTML = b;
    }

    /**
     * @return displayNavigation
     */
    public boolean isDisplayNavigation()
    {
        return displayNavigation;
    }

    /**
     * @param b le nouveau displayNavigation
     */
    public void setDisplayNavigation( final boolean b )
    {
        displayNavigation = b;
    }

    /**
     * @return collectionForDisplay
     */
    public Vector getCollectionForDisplay()
    {
        return collectionForDisplay;
    }

    /**
     * @return localeRequest
     */
    public Locale getLocaleRequest()
    {
        return localeRequest;
    }

    /**
     * @return resources
     */
    public MessageResources getResources()
    {
        return resources;
    }

    /**
     * @param string le nouveau bottomValue
     */
    public void setBottomValue( final String string )
    {
        bottomValue = string;
    }

    /**
     * @return property
     */
    public String getProperty()
    {
        return property;
    }

    /**
     * @return scope
     */
    public String getScope()
    {
        return scope;
    }

    /**
     * @param string la nouvelle property
     */
    public void setProperty( final String string )
    {
        property = string;
    }

    /**
     * @param string le nouveau scope
     */
    public void setScope( final String string )
    {
        scope = string;
    }

    /**
     * @return totalLabelKey
     */
    public String getTotalLabelKey()
    {
        return totalLabelKey;
    }

    /**
     * @param string le nouveau totalLabelKey
     */
    public void setTotalLabelKey( final String string )
    {
        totalLabelKey = string;
    }

    /**
     * @return offSet
     */
    public int getOffSet()
    {
        return offSet;
    }

    /**
     * @return pageForward
     */
    public String getPageForward()
    {
        return pageForward;
    }

    /**
     * @return pageLength
     */
    public int getPageLength()
    {
        return pageLength;
    }

    /**
     * @param i le nouveau offSet
     */
    public void setOffSet( final int i )
    {
        offSet = i;
    }

    /**
     * @param string le nouveau pageForward
     */
    public void setPageForward( final String string )
    {
        pageForward = string;
    }

    /**
     * @param i le nouveau pageLength
     */
    public void setPageLength( final int i )
    {
        pageLength = i;
    }

    /**
     * @return pagePerNavBar
     */
    public String getPagePerNavBar()
    {
        return pagePerNavBar;
    }

    /**
     * @param string le nouveau pagePerNavBar
     */
    public void setPagePerNavBar( final String string )
    {
        pagePerNavBar = string;
    }

    /**
     * @return paginationPos
     */
    public String getPaginationPos()
    {
        return paginationPos;
    }

    /**
     * @return totalLabelPos
     */
    public String getTotalLabelPos()
    {
        return totalLabelPos;
    }

    /**
     * @param string le nouveau paginationPos
     */
    public void setPaginationPos( final String string )
    {
        if ( Util.isEqualsIgnoreCase( string, "top" ) )
        {
            paginationPos = TOP;
        }
        else if ( Util.isEqualsIgnoreCase( string, "bottom" ) )
        {
            paginationPos = BOTTOM;
        }
        else
        {
            paginationPos = BOTH;
        }
    }

    /**
     * @param string le nouveau totalLabelPos
     */
    public void setTotalLabelPos( final String string )
    {
        if ( Util.isEqualsIgnoreCase( string, "top" ) )
        {
            totalLabelPos = TOP;
        }
        else if ( Util.isEqualsIgnoreCase( string, "bottom" ) )
        {
            totalLabelPos = BOTTOM;
        }
        else
        {
            totalLabelPos = BOTH;
        }
    }

    /**
     * @return requestURI
     */
    public String getRequestURI()
    {
        return requestURI;
    }

    /**
     * @param string le nouveau requestURI
     */
    public void setRequestURI( final String string )
    {
        requestURI = string;
    }

    /**
     * @return width
     */
    public String getWidth()
    {
        return width;
    }

    /**
     * @param string le nouveau width
     */
    public void setWidth( final String string )
    {
        width = string;
    }

    /**
     * @return emptyKey
     */
    public String getEmptyKey()
    {
        return emptyKey;
    }

    /**
     * @param string le nouveau emptyKey
     */
    public void setEmptyKey( final String string )
    {
        emptyKey = string;
    }

    /**
     * @return displayFooter
     */
    public boolean isDisplayFooter()
    {
        return displayFooter;
    }

    /**
     * @param b le nouveau displayFooter
     */
    public void setDisplayFooter( final boolean b )
    {
        displayFooter = b;
    }

    /**
     * @return displayHeader
     */
    public boolean isDisplayHeader()
    {
        return displayHeader;
    }

    /**
     * @param b le nouveau displayHeader
     */
    public void setDisplayHeader( final boolean b )
    {
        displayHeader = b;
    }

    /**
     * @return scrollHeight
     */
    public int getScrollHeight()
    {
        return scrollHeight;
    }

    /**
     * @param i le nouveau scrollHeight
     */
    public void setScrollHeight( final int i )
    {
        scrollHeight = i;
    }

    /**
     * @return callBackUrl
     */
    public String getCallBackUrl()
    {
        return callBackUrl;
    }

    /**
     * @param string le nouveau callBackUrl
     */
    public void setCallBackUrl( final String string )
    {
        callBackUrl = string;
    }

    /**
     * @return listColumnSort attribut
     */
    public ListColumnSort getListColumnSort()
    {
        return listColumnSort;
    }
}