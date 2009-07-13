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
package org.squale.welcom.taglib.menu;

import java.util.Iterator;
import java.util.Vector;

import org.squale.welcom.taglib.renderer.RendererFactory;


/**
 * @author user To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class JSMenu
    extends JSMenuBase
{
    /** attribut */
    private String bayadere;

    /** attribut */
    private final Vector items = new Vector();

    /** attribut */
    private int id = 0;

    /** attribut */
    private String orientation = "0";

    /** render */
    private IMenuRender render = (IMenuRender) RendererFactory.getRenderer( RendererFactory.MENU );

    /**
     * @param m menu item a rajouter
     */
    public void addMenuItem( final JSMenuItem m )
    {
        items.add( m );
    }

    /**
     * @param level le niveau
     * @return le resultat du print
     */
    public String doPrint( final int level )
    {
        return render.doPrintBase( this, level );
    }

    /**
     * Returns the bayadere.
     * 
     * @return String
     */
    public String getBayadere()
    {
        return bayadere;
    }

    /**
     * @return true si il y a des enfants
     */
    public boolean hasChild()
    {
        return items.size() != 0;
    }

    /**
     * Sets the bayadere.
     * 
     * @param pBayadere The bayadere to set
     */
    public void setBayadere( final String pBayadere )
    {
        bayadere = pBayadere;
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
     * @return orientation
     */
    public String getOrientation()
    {
        return orientation;
    }

    /**
     * @param string orientation
     */
    public void setOrientation( final String string )
    {
        orientation = string;
    }

    /**
     * @return un iterator
     */
    public Iterator itemsIterator()
    {
        return items.iterator();
    }
}