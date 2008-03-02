/*
 * Créé le 13 sept. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.menu.impl;

import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.taglib.canvas.CanvasUtil;
import com.airfrance.welcom.taglib.menu.IMenuRender;
import com.airfrance.welcom.taglib.menu.JSMenu;
import com.airfrance.welcom.taglib.menu.JSMenuBase;
import com.airfrance.welcom.taglib.menu.JSMenuItem;

/**
 *
 */
public class MenuSkinLight
    implements IMenuRender
{
    /** constante */
    private static final String MENU_KEY = "com.airfrance.welcom.taglib.menu";

    /**
     * @see com.airfrance.welcom.taglib.menu.IMenuRender#doPrintBase(com.airfrance.welcom.taglib.menu.JSMenu, int)
     */
    public String doPrintBase( final JSMenu menu, int level )
    {
        String menuId;
        final StringBuffer buf = new StringBuffer();
        boolean horizontal = menu.getOrientation().equals( "0" );
        if ( horizontal )
        { // menu horizontal
            buf.append( "<div id=\"navigationP\"><div id=\"menuH\">" );
            menuId = getProfondeur( level ) + "menuH";
        }
        else
        { // menu vertical
            buf.append( "<div id=\"menuV\">" );
            menuId = "smenuV";
        }

        buf.append( "<ul><div id=\"" );
        buf.append( menuId );
        buf.append( "\">" );

        final Iterator iter = menu.itemsIterator();
        while ( iter.hasNext() )
        {
            final JSMenuItem element = (JSMenuItem) iter.next();
            level++;
            if ( element.getAction() != null )
            {
                buf.append( "<li><a href=\"" );
                buf.append( element.getAction() );
                buf.append( "\">" );
                buf.append( element.getLibelle() );
                buf.append( "</a></li>" );
            }
            else
            {
                if ( element.hasChild() )
                {
                    buf.append( "<li onmouseover=\"montrer('" );
                    buf.append( menuId );
                    buf.append( level );
                    buf.append( "');\" onmouseout=\"cacher('" );
                    buf.append( menuId );
                    buf.append( level );
                    buf.append( "');\"><a href=\"#\">" );
                    buf.append( element.getLibelle() );
                    if ( !horizontal )
                    {
                        buf.append( "<div>&nbsp;</div>" );
                    }
                    buf.append( "</a>" );
                    buf.append( "<ul id=\"" );
                    buf.append( menuId );
                    buf.append( level );
                    buf.append( "\">" );
                    buf.append( ( element.doPrint( menu, menu.getName(), level * RECUSIVE_MAGIC_NUMBER, 1 ) ) );
                    buf.append( "</ul>" );
                    buf.append( "</li>" );
                }
                else
                {
                    // N'affiche pas le menu s'il n'y a pas d'action.
                    if ( !GenericValidator.isBlankOrNull( element.getAction() ) )
                    {
                        buf.append( "<li><a href=\"" );
                        buf.append( element.getAction() );
                        buf.append( "\">" );
                        buf.append( element.getLibelle() );
                        buf.append( "</a></li>" );
                    }
                }
            }
        }
        buf.append( "</div></ul></div>" );
        if ( menu.getOrientation().equals( "0" ) )
        { // menu horizontal
            buf.append( "</div>" );
        }

        return buf.toString();
    }

    /**
     * @see com.airfrance.welcom.taglib.menu.IMenuRender#doPrint(com.airfrance.welcom.taglib.menu.JSMenuItem,
     *      com.airfrance.welcom.taglib.menu.JSMenuBase, java.lang.String, int, int)
     */
    public String doPrint( final JSMenuItem menuItem, final JSMenuBase parent, final String menuName, int level, int tab )
    {
        String menuId;
        boolean horizontal = ( (JSMenu) parent ).getOrientation().equals( "0" );
        if ( horizontal )
        { // menu horizontal
            menuId = getProfondeur( level ) + "menuH";
        }
        else
        { // menu vertical
            menuId = "menuV";
        }

        final StringBuffer buf = new StringBuffer();

        final Iterator iter = menuItem.itemsIterator();
        // Affiche pas de menu si ne contient pas d'element
        if ( menuItem.hasChild() )
        {
            while ( iter.hasNext() )
            {
                final JSMenuItem element = (JSMenuItem) iter.next();
                level++;
                if ( element.hasChild() && !element.isLast() )
                {
                    buf.append( "<li onmouseover=\"montrer('" );
                    buf.append( menuId );
                    buf.append( ( level + tab ) );
                    buf.append( "');\" onmouseout=\"cacher('" );
                    buf.append( menuId );
                    buf.append( ( level + tab ) );
                    buf.append( "');\"><a href=\"#\">" );
                    if ( horizontal )
                    {
                        buf.append( "<span>&nbsp;</span>" );
                    }

                    buf.append( element.getLibelle() );
                    if ( !horizontal )
                    {
                        buf.append( "<div>&nbsp;</div>" );
                    }
                    buf.append( "</a>" );
                    if ( horizontal )
                    {
                        buf.append( "<div id=\"" );
                        buf.append( menuId );
                        buf.append( "\">" );
                    }
                    buf.append( "<ul id=\"" );
                    buf.append( menuId );
                    buf.append( ( level + tab ) );
                    buf.append( "\">" );
                    buf.append( ( element.doPrint( parent, menuName, level * RECUSIVE_MAGIC_NUMBER, tab++ ) ) );
                    buf.append( "</ul>" );
                    if ( horizontal )
                    {
                        buf.append( "</div>" );
                    }

                    buf.append( "</li>" );
                }
                else
                {
                    // N'affiche pas le menu s'il n'y a pas d'action.
                    if ( !GenericValidator.isBlankOrNull( element.getAction() ) )
                    {
                        buf.append( "<li><a href=\"" );
                        buf.append( element.getAction() );
                        buf.append( "\">" );
                        if ( horizontal )
                        {
                            buf.append( "<span>&nbsp;</span>" );
                        }
                        buf.append( element.getLibelle() );
                        buf.append( "</a></li>" );
                    }
                }
            }
        }

        return buf.toString();
    }

    /**
     * on retourne "s", jusqu'au niveau 2, "ss" apres
     * 
     * @param level la profondeur
     * @return le prefixe du menuId
     */
    private String getProfondeur( final int level )
    {
        if ( level < RECUSIVE_MAGIC_NUMBER )
        {
            return "s";
        }
        else
        {
            return "ss";
        }
    }

    /**
     * @see com.airfrance.welcom.taglib.menu.IMenuRender#doPrintHeader(javax.servlet.jsp.tagext.Tag,
     *      javax.servlet.jsp.PageContext)
     */
    public String doPrintHeader( final Tag tag, final PageContext pageContext )
        throws JspException
    {
        if ( pageContext.getRequest().getAttribute( MENU_KEY ) == null )
        {
            CanvasUtil.addCss( "css/comMenuLight.css", tag, pageContext );
            pageContext.getRequest().setAttribute( MENU_KEY, this );
        }

        return "";
    }

    /**
     * @see com.airfrance.welcom.taglib.menu.IMenuRender#doPrintFooter()
     */
    public String doPrintFooter()
    {
        return "";
    }

    /**
     * @see com.airfrance.welcom.taglib.menu.IMenuRender#getAction(java.lang.String)
     */
    public String getAction( final String action )
    {
        return action;
    }

}
