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
 * Créé le 10 sept. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.taglib.dropdownpanel;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.ResponseUtils;
import org.squale.welcom.outils.TrimStringBuffer;
import org.squale.welcom.outils.Util;
import org.squale.welcom.outils.WelcomConfigurator;
import org.squale.welcom.struts.lazyLoading.WLazyLoadingPersistance;
import org.squale.welcom.struts.lazyLoading.WLazyLoadingType;
import org.squale.welcom.struts.lazyLoading.WLazyUtil;
import org.squale.welcom.taglib.field.util.LayoutUtils;
import org.squale.welcom.taglib.table.ColTag;
import org.squale.welcom.taglib.table.ColsTag;


/**
 * DropDownPanelTag
 */
public class DropDownPanelTag
    extends BodyTagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = -3286751636538006757L;

    /** Constante */
    private final static String BLUE = "BLUE";

    /** parametre tag */
    private String titleKey = "";

    /** parametre tag */
    private boolean expanded = false;

    /** parametre tag */
    private String width = null;

    /** parametre tag */
    private String headerClass = null;

    /** parametre tag */
    private String name = "";

    /** parametre tag */
    private String headerStyle = "";

    /** parametre tag */
    private String contentClass = null;

    /** parametre tag */
    private String contentStyle = "";

    /** parametre tag */
    private String iconColor = BLUE;

    /** parametre tag */
    private String filter = null;

    /** parametre tag */
    protected MessageResources resources = null;

    /** parametre tag */
    protected Locale localeRequest = Locale.FRENCH;

    /** parametre tag */
    private boolean lazyLoading = true;

    /** parametre tag */
    private String onExpand = "";

    /** parametre tag */
    private String onCollapse = "";

    /** parametre tag */
    private boolean topMode = false;

    /** Javascript appele en lieu et place de l'ouverture sur click sur le titre * */
    private String onClickTitle = "";

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    public int doStartTag()
        throws JspException
    {
        resources = (MessageResources) pageContext.getServletContext().getAttribute( Globals.MESSAGES_KEY );
        localeRequest = (Locale) pageContext.getSession().getAttribute( Globals.LOCALE_KEY );
        final TrimStringBuffer sb = new TrimStringBuffer();

        // Creation du Head du DDp
        prepareHeadDdp( sb );

        // Creation du body du ddp
        prepareBodyDdp( sb );

        ResponseUtils.write( pageContext, sb.toString() );

        return EVAL_PAGE;
    }

    /**
     * Creation du Head du ddp
     * 
     * @param sb stringbuffer
     * @throws JspException exception a la crecuperation des infos
     */
    private void prepareHeadDdp( final TrimStringBuffer sb )
        throws JspException
    {
        if ( topMode )
        {
            onExpand = "fixPos('" + getName() + "');" + onExpand;
            onCollapse = "ddp=null;" + onCollapse;
            contentClass = "richbox " + contentClass;
        }

        // Taille
        if ( !GenericValidator.isBlankOrNull( width ) )
        {
            headerStyle = "width:" + width + ";" + headerStyle;
        }

        // Div ouvrant
        sb.append( "<div " );

        // Style du header
        if ( !GenericValidator.isBlankOrNull( headerStyle ) )
        {
            sb.append( " style=\"" + headerStyle + "\"" );
        }

        // Style de la classe
        if ( !GenericValidator.isBlankOrNull( headerClass ) )
        {
            sb.append( " class=\"" + headerClass + "\"" );
        }

        // Style du onclik
        if ( !GenericValidator.isBlankOrNull( onClickTitle ) )
        {
            sb.append( " onclick=\"" + onClickTitle + ";\"" );
        }

        sb.append( ">" );

        // Le lien
        sb.append( "<a href=\"javascript:vis(document.getElementById('" + getName() + "')" );
        sb.append( ")\">" );

        sb.append( "<span id=\"pic" + getName() + "\" class=\"" );

        // Le picto
        sb.append( getClassPic() + "\"></span>" );
        final String text = getText();

        sb.append( "<span id=\"" + getName() + "Title\"" );

        if ( !GenericValidator.isBlankOrNull( headerStyle ) )
        {
            sb.append( " style=\"" + headerStyle + "\"" );
        }
        if ( !GenericValidator.isBlankOrNull( headerClass ) )
        {
            sb.append( " class=\"" + headerClass + "\"" );
        }

        sb.append( ">" );

        sb.append( text + "</span></a></div>\n" );
    }

    /**
     * Recupere la classe du picto
     * 
     * @return up / down et B (bleu) ou W (white)
     */
    private String getClassPic()
    {
        String pic = "up";
        if ( expanded )
        {
            pic = "down";
        }
        if ( iconColor.toUpperCase().equals( BLUE ) )
        {
            pic += "B";
        }
        else
        {
            pic += "W";
        }
        return pic;
    }

    /**
     * Retourne la eon contenu du ddp
     * 
     * @param sb stringbuffer
     */
    private void prepareBodyDdp( final TrimStringBuffer sb )
    {

        String divStyle = "";
        onExpand = onExpand.replaceAll( "this", "document.getElementById('" + getName() + "')" );
        onCollapse = onCollapse.replaceAll( "this", "document.getElementById('" + getName() + "')" );

        sb.append( "<div id=\"" + getName() + "\"" );

        // Event ouvert
        if ( !GenericValidator.isBlankOrNull( onExpand ) )
        {
            sb.append( " onExpand=\"" + onExpand + "\"" );
        }

        // event fermé
        if ( !GenericValidator.isBlankOrNull( onCollapse ) )
        {
            sb.append( " onCollapse=\"" + onCollapse + "\"" );
        }

        // Si lazy loading
        if ( WLazyUtil.isLazy( lazyLoading ) && !isExpanded()
            && Util.isTrue( WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_GLOBAL_LAZYLOADING_DDP ) ) )
        {
            sb.append( " load=\"true\"" );
        }

        // si pas ouvert
        if ( !expanded )
        {
            divStyle += "display:none;";
        }

        // taille ddp
        if ( !GenericValidator.isBlankOrNull( width ) )
        {
            divStyle += ( "width:" + width + ";" );
        }

        // style du contenu
        if ( !GenericValidator.isBlankOrNull( contentStyle ) )
        {
            divStyle += contentStyle;
        }

        // style du div calculé precedement
        if ( !GenericValidator.isBlankOrNull( divStyle ) )
        {
            sb.append( " style=\"" + divStyle + "\"" );
        }

        // class du contenu
        if ( !GenericValidator.isBlankOrNull( contentClass ) )
        {
            sb.append( " class=\"" + contentClass + "\"" );
        }

        sb.append( ">" );
    }

    /**
     * @return Retourne le titre de ddp
     * @throws JspException Probleme sur la recherche de la valeur de la colonne
     */
    private String getText()
        throws JspException
    {
        String text = resources.getMessage( localeRequest, titleKey );
        if ( GenericValidator.isBlankOrNull( text ) && GenericValidator.isBlankOrNull( titleKey ) )
        {
            text = getTextIfInColTag();
        }
        if ( GenericValidator.isBlankOrNull( text ) )
        {
            text = titleKey;
        }
        if ( Util.isTrue( filter ) || ( filter == null && findAncestorWithClass( this, ColTag.class ) != null ) )
        {
            text = ResponseUtils.filter( text );
        }

        return text;
    }

    /**
     * @return
     * @throws JspException
     */

    /**
     * @return Retourne le libelle de la colonne dans la quelle se trouve la tag
     * @throws JspException Probleme sur la recherche de la valeur de la colonne
     */
    private String getTextIfInColTag()
        throws JspException
    {

        String text = null;

        // Verfie si son papa est un ColTag
        if ( getParent() instanceof ColTag )
        {
            final ColTag colTag = (ColTag) getParent();
            // Recherche le nom de l'iteration
            Tag curTag = null;
            curTag = findAncestorWithClass( this, ColsTag.class );
            final ColsTag colsTag = (ColsTag) curTag;
            if ( ( colsTag != null ) && !GenericValidator.isBlankOrNull( colTag.getProperty() ) )
            {

                final Object o = pageContext.getAttribute( colsTag.getId() );
                final Object ovalue = LayoutUtils.getProperty( o, colTag.getProperty() );

                if ( ovalue != null )
                {
                    text = ResponseUtils.filter( ovalue.toString() );
                }
                else
                {
                    // Recupere celle spécifié
                    if ( !GenericValidator.isBlankOrNull( colTag.getEmptyKey() ) )
                    {
                        text = colTag.getEmptyKey();
                    }
                    else
                    {
                        text = WelcomConfigurator.getMessageWithCfgChartePrefix( ".default.char.if.empty" );
                    }
                }
            }
        }
        return text;
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    public int doEndTag()
        throws JspException
    {
        final TrimStringBuffer sb = new TrimStringBuffer();

        if ( getBodyContent() != null )
        {
            final String corps = getBodyContent().getString();

            if ( WLazyUtil.isLazy( lazyLoading ) && !isExpanded()
                && Util.isTrue( WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_GLOBAL_LAZYLOADING_DDP ) ) )
            {
                WLazyLoadingPersistance.find( pageContext.getSession() ).add( WLazyLoadingType.DROPDOWNPANEL,
                                                                              getName(), corps );

                final HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

                final ActionMapping mappingGenerique = (ActionMapping) request.getAttribute( Globals.MAPPING_KEY );
                final String scope = mappingGenerique.getScope();

                if ( Util.isEqualsIgnoreCase( scope, "session" ) )
                {
                    sb.append( WLazyUtil.getSuperLightBody( corps ) );
                }
                else
                {
                    sb.append( WLazyUtil.getLightBody( corps ) );
                }
            }
            else
            {
                sb.append( corps );
            }
        }

        sb.append( "</div>" );
        ResponseUtils.write( pageContext, sb.toString() );
        release();
        return EVAL_PAGE;
    }

    /**
     * @return le "unique" name
     */
    private String getUniqueName()
    {
        String nom = "";
        final String drpdwn = (String) pageContext.getSession().getAttribute( "drpdwn" );

        if ( GenericValidator.isBlankOrNull( drpdwn ) )
        {
            pageContext.getSession().setAttribute( "drpdwn", "1" );
            nom = "ddp1";
        }
        else
        {
            final int intdrpdwn = Integer.parseInt( drpdwn ) + 1;
            nom = "ddp" + ( intdrpdwn );
            pageContext.getSession().setAttribute( "drpdwn", "" + intdrpdwn );
        }

        return nom;
    }

    /**
     * @return expanded
     */
    public boolean isExpanded()
    {
        return expanded;
    }

    /**
     * @return iconColor
     */
    public String getIconColor()
    {
        return iconColor;
    }

    /**
     * @return titleKey
     */
    public String getTitleKey()
    {
        return titleKey;
    }

    /**
     * @return width
     */
    public String getWidth()
    {
        return width;
    }

    /**
     * @param b le expanded
     */
    public void setExpanded( final boolean b )
    {
        expanded = b;
    }

    /**
     * @param string le iconColor
     */
    public void setIconColor( final String string )
    {
        iconColor = string;
    }

    /**
     * @param string le titleKey
     */
    public void setTitleKey( final String string )
    {
        titleKey = string;
    }

    /**
     * @param string le width
     */
    public void setWidth( final String string )
    {
        width = string;
    }

    /**
     * @return contentClass
     */
    public String getContentClass()
    {
        return contentClass;
    }

    /**
     * @return contentStyle
     */
    public String getContentStyle()
    {
        return contentStyle;
    }

    /**
     * @return headerClass
     */
    public String getHeaderClass()
    {
        return headerClass;
    }

    /**
     * @return headerStyle
     */
    public String getHeaderStyle()
    {
        return headerStyle;
    }

    /**
     * @param string le contentClass
     */
    public void setContentClass( final String string )
    {
        contentClass = string;
    }

    /**
     * @param string le contentStyle
     */
    public void setContentStyle( final String string )
    {
        contentStyle = string;
    }

    /**
     * @param string le headerClass
     */
    public void setHeaderClass( final String string )
    {
        headerClass = string;
    }

    /**
     * @param string le headerStyle
     */
    public void setHeaderStyle( final String string )
    {
        headerStyle = string;
    }

    /**
     * @return lazyLoading
     */
    public boolean isLazyLoading()
    {
        return lazyLoading;
    }

    /**
     * @param b le lazyLoading
     */
    public void setLazyLoading( final boolean b )
    {
        lazyLoading = b;
    }

    /**
     * @return onCollapse
     */
    public String getOnCollapse()
    {
        return onCollapse;
    }

    /**
     * @return onExpand
     */
    public String getOnExpand()
    {
        return onExpand;
    }

    /**
     * @param string le onCollapse
     */
    public void setOnCollapse( final String string )
    {
        onCollapse = string;
    }

    /**
     * @param string le onExpand
     */
    public void setOnExpand( final String string )
    {
        onExpand = string;
    }

    /**
     * @return topMode
     */
    public boolean isTopMode()
    {
        return topMode;
    }

    /**
     * @param b le topMode
     */
    public void setTopMode( final boolean b )
    {
        topMode = b;
    }

    /**
     * @return name
     */
    public String getName()
    {
        if ( GenericValidator.isBlankOrNull( name ) )
        {
            name = getUniqueName();
        }
        return name;
    }

    /**
     * @param string le name
     */
    public void setName( final String string )
    {
        name = string;
    }

    /**
     * @see javax.servlet.jsp.tagext.BodyTagSupport#release()
     */
    public void release()
    {
        super.release();
        titleKey = "";
        expanded = false;
        width = null;
        headerClass = null;
        name = "";
        headerStyle = "";
        contentClass = null;
        contentStyle = "";
        iconColor = BLUE;
        filter = null;
        resources = null;
        localeRequest = Locale.FRENCH;
        lazyLoading = true;
        onExpand = "";
        onCollapse = "";
        topMode = false;
    }

    /**
     * @return accesseur
     */
    public String getFilter()
    {
        return filter;
    }

    /**
     * @param b accesseur
     */
    public void setFilter( final String b )
    {
        filter = b;
    }

    /**
     * @return accesseur
     */
    public String getOnClickTitle()
    {
        return onClickTitle;
    }

    /**
     * @param string accesseur
     */
    public void setOnClickTitle( String string )
    {
        onClickTitle = string;
    }

}