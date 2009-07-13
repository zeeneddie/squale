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
package org.squale.welcom.taglib.onglet;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.jsp.JspException;

import org.apache.commons.validator.GenericValidator;
import org.squale.welcom.outils.Util;


/**
 * @author user To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class JSOnglet
{
    /** constante */
    public final static String DEFAULT_NAME = "D_Formulaire";

    /** constante */
    public final static String SELECTED_TABS = "org.squale.welcom.selctedtabs";

    /** name */
    private String name;

    /** le vecteur contenant les "enfants */
    private Vector items = new Vector();

    /**
     * Ajoute un onglet a la liste des enfants
     * 
     * @param o l'onglet a rajouter
     */
    public void addOnglet( final JSOngletItem o )
    {
        if ( items == null )
        {
            items = new Vector();
        }

        items.add( o );
    }

    /**
     * @return le title
     */
    public String doPrintTitle()
    {
        final StringBuffer buf = new StringBuffer();

        final Enumeration enumeration = items.elements();
        int i = 0;

        while ( enumeration.hasMoreElements() )
        {
            final JSOngletItem element = (JSOngletItem) enumeration.nextElement();
            buf.append( element.doPrintTitle( i++ ) );
        }

        return buf.toString();
    }

    /**
     * le corps
     * 
     * @throws JspException exception pouvant etre levee
     */
    public void doPrintCorps()
        throws JspException
    {
        final Enumeration enumeration = items.elements();
        int i = 0;

        while ( enumeration.hasMoreElements() )
        {
            final JSOngletItem element = (JSOngletItem) enumeration.nextElement();
            element.doPrintCorps( i++ );
        }
    }

    /**
     * @param parent le JSOngletTag
     * @return le name de l'onglet selectionne
     */
    public String checkSelected( final JSOngletTag parent )
    {
        for ( final Iterator iter = items.iterator(); iter.hasNext(); )
        {
            final JSOngletItem element = (JSOngletItem) iter.next();

            if ( Util.isTrue( element.getIsOnglet() ) )
            {
                return element.getName();
            }
        }

        final String selOnglet = parent.getSelectedOnglet();

        if ( !GenericValidator.isBlankOrNull( selOnglet ) )
        {
            for ( final Iterator iter = items.iterator(); iter.hasNext(); )
            {
                final JSOngletItem onglet = (JSOngletItem) iter.next();
                final String sel = new Boolean( selOnglet.equals( onglet.getName() ) ).toString();
                onglet.setIsOnglet( sel );

                if ( sel.equals( "true" ) )
                {
                    return onglet.getName();
                }
            }
        }
        else
        {
            if ( ( items == null ) || ( items.size() == 0 ) )
            {
                return null;
            }

            ( (JSOngletItem) items.get( 0 ) ).setIsOnglet( "true" );

            return ( (JSOngletItem) items.get( 0 ) ).getName();
        }

        return ( (JSOngletItem) items.get( 0 ) ).getName();
    }

    /**
     * @return true si l'onglet a des "enfants"
     */
    public boolean hasChild()
    {
        if ( items == null )
        {
            items = new Vector();
        }

        return !items.isEmpty();
    }

    /**
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param string le name
     */
    public void setName( final String string )
    {
        name = string;
    }
}