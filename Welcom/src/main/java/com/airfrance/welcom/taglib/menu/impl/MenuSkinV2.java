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

import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.taglib.canvas.CanvasUtil;
import com.airfrance.welcom.taglib.menu.IMenuRender;
import com.airfrance.welcom.taglib.menu.JSMenu;
import com.airfrance.welcom.taglib.menu.JSMenuBase;
import com.airfrance.welcom.taglib.menu.JSMenuItem;

/**
 *
 */
public class MenuSkinV2
    implements IMenuRender
{

    /** constante */
    private static final String MENU_KEY = "com.airfrance.welcom.taglib.menu";

    /**
     * @see com.airfrance.welcom.taglib.menu.IMenuRender#doPrintBase(com.airfrance.welcom.taglib.menu.JSMenu, int)
     */
    public String doPrintBase( final JSMenu menu, int level )
    {
        menu.setId( level );
        final Iterator iter = menu.itemsIterator();
        final StringBuffer buf = new StringBuffer();

        // Vector temporisation = new Vector();
        buf.append( menu.getName() + " = new comMenuAF_v2(" + menu.getOrientation() + " , \"t" + menu.getName() + "-"
            + level + "\");\n" );

        while ( iter.hasNext() )
        {
            final JSMenuItem element = (JSMenuItem) iter.next();

            if ( element.getAction() != null )
            {
                buf.append( menu.getName() + ".ajouterElement(" );
                buf.append( "\"t" + menu.getName() + "-" + menu.getId() );
                buf.append( "\",\"" );
                buf.append( element.getLibelle() );
                buf.append( "\",\"" );
                buf.append( element.getColor().getId() );
                buf.append( "\",\"" );
                buf.append( element.getAction() );
                buf.append( "\"," );
                buf.append( "null" );
                buf.append( ");\n" );
            }
            else
            {
                if ( element.hasChild() )
                {
                    level++;
                    buf.append( menu.getName() + ".ajouterElement(" );
                    buf.append( "\"t" + menu.getName() + "-" + menu.getId() );
                    buf.append( "\",\"" );
                    buf.append( element.getLibelle() );
                    buf.append( "\",\"" );
                    buf.append( element.getColor().getId() );
                    buf.append( "\"," );
                    buf.append( "null" );
                    buf.append( ",\"" );
                    buf.append( "t" + menu.getName() + "-" + ( level * RECUSIVE_MAGIC_NUMBER ) );
                    buf.append( "\");\n" );
                    buf.append( ( element.doPrint( menu, menu.getName(), level * RECUSIVE_MAGIC_NUMBER, 1 ) ) );
                }
                else
                {
                    // N'affiche pas le menu s'il n'y a pas d'action.
                    if ( !GenericValidator.isBlankOrNull( element.getAction() ) )
                    {
                        buf.append( menu.getName() + ".ajouterElement(" );
                        buf.append( "\"t" + menu.getName() + "-" + menu.getId() );
                        buf.append( "\",\"" );
                        buf.append( element.getLibelle() );
                        buf.append( "\",\"" );
                        buf.append( element.getColor().getId() );
                        buf.append( "\",\"" );
                        buf.append( element.getAction() );
                        buf.append( "\"," );
                        buf.append( "null" );
                        buf.append( ");\n" );
                    }
                }
            }
        }

        buf.append( menu.getName() + ".construire();\n" );

        return buf.toString();
    }

    /**
     * @see com.airfrance.welcom.taglib.menu.IMenuRender#doPrint(com.airfrance.welcom.taglib.menu.JSMenuItem,
     *      com.airfrance.welcom.taglib.menu.JSMenuBase, java.lang.String, int, int)
     */
    public String doPrint( final JSMenuItem menuItem, final JSMenuBase parent, final String menuName, int level, int tab )
    {
        menuItem.setId( level );

        final int oldtab = tab;
        final Iterator iter = menuItem.itemsIterator();
        final StringBuffer buf = new StringBuffer();
        menuItem.setName( parent.getName() );

        // Affciahe pas de menu si ne contient pas d'element
        if ( menuItem.hasChild() )
        {
            while ( iter.hasNext() )
            {
                final JSMenuItem element = (JSMenuItem) iter.next();

                if ( element.hasChild() && !element.isLast() )
                {
                    level++;
                    buf.append( menuItem.tabs( oldtab ) );
                    buf.append( menuName + ".ajouterElement(" );
                    buf.append( "\"t" + menuItem.getName() + "-" + menuItem.getId() );
                    buf.append( "\",\"" );
                    buf.append( element.getLibelle() );
                    buf.append( "\",\"" );
                    buf.append( element.getColor().getId() );
                    buf.append( "\"," );
                    buf.append( "null" );
                    buf.append( ",\"" );
                    buf.append( "t" + menuItem.getName() + "-" + ( level * RECUSIVE_MAGIC_NUMBER ) );
                    buf.append( "\");\n" );
                    buf.append( element.doPrint( menuItem, menuName, level * RECUSIVE_MAGIC_NUMBER, tab++ ) );
                }
                else
                {
                    // N'affiche pas le menu s'il n'y a pas d'action.
                    if ( !GenericValidator.isBlankOrNull( element.getAction() ) )
                    {
                        buf.append( menuItem.tabs( oldtab ) );
                        buf.append( menuName + ".ajouterElement(" );
                        buf.append( "\"t" + menuItem.getName() + "-" + menuItem.getId() );
                        buf.append( "\",\"" );
                        buf.append( element.getLibelle() );
                        buf.append( "\",\"" );
                        buf.append( element.getColor().getId() );
                        buf.append( "\",\"" );
                        buf.append( element.getAction() );
                        buf.append( "\"," );
                        buf.append( "null" );
                        buf.append( ");\n" );
                    }
                }
            }
        }

        return buf.toString();
    }

    /**
     * @see com.airfrance.welcom.taglib.menu.IMenuRender#doPrintHeader()
     */
    public String doPrintHeader( final Tag tag, final PageContext pageContext )
        throws JspException
    {
        final StringBuffer buf = new StringBuffer();

        if ( pageContext.getRequest().getAttribute( MENU_KEY ) == null )
        {
            CanvasUtil.addJs( WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV2_MENU_JSURL ), tag, pageContext );
            pageContext.getRequest().setAttribute( MENU_KEY, "in" );
        }

        buf.append( "<script language=\"JavaScript\">\n" );
        if ( "UNI".equals( WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV2_ID ) ) )
        {
            buf.append( "mv2_style_top_menuv = 110;" );
        }
        buf.append( "/* Debut de la generation du menu */\n" );

        return buf.toString();
    }

    /**
     * @see com.airfrance.welcom.taglib.menu.IMenuRender#doPrintFooter()
     */
    public String doPrintFooter()
    {
        final StringBuffer buf = new StringBuffer();
        buf.append( "/* fin de configuration et construction du menu */\n" );
        buf.append( "</script>" );

        return buf.toString();
    }

    /**
     * @see com.airfrance.welcom.taglib.menu.IMenuRender#getAction(java.lang.String)
     */
    public String getAction( final String action )
    {
        if ( action == null )
        {
            return null;
        }

        if ( ( action.length() > 11 ) && ( action.indexOf( "javascript:" ) > -1 ) )
        {
            return action.substring( 11 ); // Si c'est du javascript // supprime la balise javascript
        }
        else
        {
            return "document.location='" + action + "';"; // Si c'est un url ajoute ce qu'il faut.
        }
    }

}
