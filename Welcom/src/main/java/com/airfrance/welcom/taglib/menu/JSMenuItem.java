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
package com.airfrance.welcom.taglib.menu;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import com.airfrance.welcom.taglib.renderer.RendererFactory;

/**
 * @author user To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class JSMenuItem
    extends JSMenuBase
{
    /** la largeur */
    private String width = "120";

    /** l'id */
    private int id = 0;

    /** les items */
    private final Vector items = new Vector();

    /** render */
    private static IMenuRender render = (IMenuRender) RendererFactory.getRenderer( RendererFactory.MENU );

    /**
     * Ajoute un menu Item
     * 
     * @param m le menuItem
     */
    public void addMenuItem( final JSMenuItem m )
    {
        items.add( m );
    }

    /**
     * @return true si le menuItem a des enfants
     */
    public boolean hasChild()
    {
        boolean child = false;

        if ( !items.isEmpty() )
        {
            final Enumeration enumeration = items.elements();

            while ( enumeration.hasMoreElements() )
            {
                final JSMenuItem js = (JSMenuItem) enumeration.nextElement();
                child = child || js.hasChild();
            }

            return child;
        }
        else
        {
            return getAction() != null;
        }
    }

    /**
     * @return true si c'est une feuille (pas d'enfant)
     */
    public boolean isLast()
    {
        return items.isEmpty();
    }

    /**
     * @param parent le parent
     * @param menuName le nom du menu
     * @param level le niveau
     * @param tab le tab
     * @return le js resultant associe
     */
    public String doPrint( final JSMenuBase parent, final String menuName, final int level, final int tab )
    {
        return render.doPrint( this, parent, menuName, level, tab );
    }

    /**
     * Ajoute la nombre de tabulation
     * 
     * @param tab le nombre de tabulation
     * @return le text correspondant
     */
    public String tabs( final int tab )
    {
        final StringBuffer buf = new StringBuffer();

        for ( int i = 0; i < tab; i++ )
        {
            buf.append( "\t" );
        }

        return buf.toString();
    }

    /**
     * Returns the width.
     * 
     * @return String
     */
    public String getWidth()
    {
        return width;
    }

    /**
     * Sets the width.
     * 
     * @param pWidth The width to set
     */
    public void setWidth( final String pWidth )
    {
        width = pWidth;
    }

    /**
     * Returns the id.
     * 
     * @return int
     */
    public int getId()
    {
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param pId The id to set
     */
    public void setId( final int pId )
    {
        id = pId;
    }

    /**
     * @return un iterator
     */
    public Iterator itemsIterator()
    {
        return items.iterator();
    }

}