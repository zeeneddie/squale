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
package org.squale.welcom.struts.bean;

import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.util.MessageResources;

/**
 * Insérez la description du type ici. Date de création : (31/10/2001 15:40:32)
 * 
 * @author: Fabienne Madaule
 */
public class WComboValueLabel
{
    /** la liste des WCouple */
    private java.util.Vector liste;

    /** Resource */
    private MessageResources resources;

    /** Locale */
    private Locale locale;

    /**
     * Commentaire relatif au constructeur .
     * 
     * @param pLocale : locale
     * @param pResources : Resources
     */
    public WComboValueLabel( final MessageResources pResources, final Locale pLocale )
    {
        super();
        resources = pResources;
        locale = pLocale;
        liste = new Vector();
    }

    /**
     * Commentaire relatif au constructeur .
     */
    public WComboValueLabel()
    {
        this( null, null );
    }

    /**
     * Insérez la description de la méthode ici. Date de création : (31/10/2001 15:47:30)
     * 
     * @return java.lang.String[]
     */
    public java.util.Vector getListe()
    {
        return liste;
    }

    /**
     * @return un vecteur contenant toutes les values
     */
    public java.util.Vector getListeValue()
    {
        final Vector newListe = new Vector();
        final Enumeration enumeration = liste.elements();

        while ( enumeration.hasMoreElements() )
        {
            final WCouple c = (WCouple) enumeration.nextElement();
            newListe.add( c.getValue() );
        }

        return newListe;
    }

    /**
     * @return un vecteur contenant tous les labels
     */
    public java.util.Vector getListeLabel()
    {
        final Vector newListe = new Vector();
        final Enumeration enumeration = liste.elements();

        while ( enumeration.hasMoreElements() )
        {
            final WCouple c = (WCouple) enumeration.nextElement();
            newListe.add( c.getLabel() );
        }

        return newListe;
    }

    /**
     * @param value la value du label recherche
     * @return le label
     */
    public String getLabel( final String value )
    {
        for ( final Iterator iter = liste.iterator(); iter.hasNext(); )
        {
            final WCouple couple = (WCouple) iter.next();

            if ( couple.getValue().equals( value ) )
            {
                return couple.getLabel();
            }
        }

        return "";
    }

    /**
     * @return la taille du vecteur
     */
    public int getSize()
    {
        return liste.size();
    }

    /**
     * @param i index
     * @return la value se trouvant a l'index i
     */
    public java.lang.String getValue( final int i )
    {
        return ( (WCouple) liste.elementAt( i ) ).getValue();
    }

    /**
     * @param newListe la nouvelle liste
     */
    public void setListe( final java.util.Vector newListe )
    {
        liste = new Vector( newListe );
    }

    /**
     * @param newValue nouvelle value a ajouter
     */
    public void addValue( final java.lang.String newValue )
    {
        addValueLabel( newValue, newValue );
    }

    /**
     * @param newValue nouvelle value a ajouter
     * @param newLabel nouveau label associe
     */
    public void addValueLabel( final java.lang.String newValue, final String newLabel )
    {
        liste.add( new WCouple( newValue, convert( newLabel ) ) );
    }

    /**
     * @param newValue nouvelle value ajoutee en 1ere position
     */
    public void pushValueLabel( final java.lang.String newValue )
    {
        pushValueLabel( newValue, newValue );
    }

    /**
     * @param newValue nouvelle value a ajouter en 1ere position
     * @param newLabel nouveau label associe
     */
    public void pushValueLabel( final java.lang.String newValue, final String newLabel )
    {
        liste.add( 0, new WCouple( newValue, convert( newLabel ) ) );
    }

    /**
     * Traduit si possible
     * 
     * @param s : Chaine a traduire
     * @return Recherché dans le message resources avec la locale
     */
    private String convert( final String s )
    {
        String mylabel = null;
        if ( ( resources != null ) && ( locale != null ) )
        {
            mylabel = resources.getMessage( locale, mylabel );
        }
        if ( GenericValidator.isBlankOrNull( mylabel ) )
        {
            mylabel = s;
        }
        return mylabel;
    }

    /**
     * Insérez la description de la méthode ici. Date de création : (29/11/2001 10:13:37)
     */
    public void sort()
    {
        Collections.sort( liste, new WCoupleComparator() );
    }

    /**
     * @see java.util.Collection#iterator()
     */
    public Iterator iterator()
    {
        return liste.iterator();
    }

    /**
     * Class WCoupleComparator
     */
    class WCoupleComparator
        implements Comparator
    {
        /**
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        public int compare( final Object arg0, final Object arg1 )
        {
            return ( (WCouple) arg0 ).getLabel().compareTo( ( (WCouple) arg1 ).getLabel() );
        }

        /**
         * @see java.lang.Object#equals(java.lang.Object)
         */
        public boolean equals( final Object arg0 )
        {
            return false;
        }
    }
}