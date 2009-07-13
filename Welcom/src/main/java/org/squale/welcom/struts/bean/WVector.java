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

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

import org.apache.struts.action.ActionForm;

/**
 * Class WVector (actionForm contient un Vector et les methodes pour etre utilise par Struts)
 */
public class WVector
    extends ActionForm
    implements List
{
    /**
     * 
     */
    private static final long serialVersionUID = -1514815944396131988L;

    /** le vecteur */
    private java.util.Vector vector = new java.util.Vector();

    /**
     * Constructeur
     * 
     * @param v le vecteur
     */
    public WVector( final Vector v )
    {
        vector.addAll( v );
    }

    /**
     * Constructeur
     */
    public WVector()
    {
    }

    /**
     * Insérez la description de la méthode ici. Date de création : (29/10/2001 14:29:36)
     * 
     * @return int
     */
    public int getSize()
    {
        return vector.size();
    }

    /**
     * Insérez la description de la méthode ici. Date de création : (12/12/2001 16:10:26)
     * 
     * @return java.util.Vector
     */
    public java.util.Vector getVector()
    {
        return vector;
    }

    /**
     * Insérez la description de la méthode ici. Date de création : (29/10/2001 14:48:15)
     * 
     * @param i l'index
     * @return l'actionForm à l'index i
     */
    public org.apache.struts.action.ActionForm getVector( final int i )
    {
        if ( i < vector.size() )
        {
            return (org.apache.struts.action.ActionForm) vector.elementAt( i );
        }
        else
        {
            return null;
        }
    }

    /**
     * Insérez la description de la méthode ici. Date de création : (29/10/2001 14:25:18)
     * 
     * @param newVector java.util.Vector
     */
    public void setVector( final java.util.Vector newVector )
    {
        vector = newVector;
    }

    /**
     * @return le premier element
     */
    public Object getFirstElement()
    {
        return vector.firstElement();
    }

    /**
     * @see java.util.Collection#size()
     */
    public int size()
    {
        return vector.size();
    }

    /**
     * @see java.util.Collection#isEmpty()
     */
    public boolean isEmpty()
    {
        return vector.isEmpty();
    }

    /**
     * @see java.util.Collection#contains(Object)
     */
    public boolean contains( final Object arg0 )
    {
        return vector.contains( arg0 );
    }

    /**
     * @see java.util.Collection#iterator()
     */
    public Iterator iterator()
    {
        return vector.iterator();
    }

    /**
     * @see java.util.Collection#toArray()
     */
    public Object[] toArray()
    {
        return vector.toArray();
    }

    /**
     * @see java.util.Collection#toArray(Object[])
     */
    public Object[] toArray( final Object arg0[] )
    {
        return vector.toArray( arg0 );
    }

    /**
     * @see java.util.Collection#add(Object)
     */
    public boolean add( final Object arg0 )
    {
        return vector.add( arg0 );
    }

    /**
     * @see java.util.Collection#remove(Object)
     */
    public boolean remove( final Object arg0 )
    {
        return vector.remove( arg0 );
    }

    /**
     * @see java.util.Collection#containsAll(Collection)
     */
    public boolean containsAll( final Collection arg0 )
    {
        return vector.containsAll( arg0 );
    }

    /**
     * @see java.util.Collection#addAll(Collection)
     */
    public boolean addAll( final Collection arg0 )
    {
        return vector.addAll( arg0 );
    }

    /**
     * @see java.util.List#addAll(int, Collection)
     */
    public boolean addAll( final int arg0, final Collection arg1 )
    {
        return vector.addAll( arg0, arg1 );
    }

    /**
     * @see java.util.Collection#removeAll(Collection)
     */
    public boolean removeAll( final Collection arg0 )
    {
        return vector.removeAll( arg0 );
    }

    /**
     * @see java.util.Collection#retainAll(Collection)
     */
    public boolean retainAll( final Collection arg0 )
    {
        return vector.retainAll( arg0 );
    }

    /**
     * @see java.util.Collection#clear()
     */
    public void clear()
    {
        vector.clear();
    }

    /**
     * @see java.util.List#get(int)
     */
    public Object get( final int arg0 )
    {
        return vector.get( arg0 );
    }

    /**
     * @see java.util.List#set(int, Object)
     */
    public Object set( final int arg0, final Object arg1 )
    {
        return vector.set( arg0, arg1 );
    }

    /**
     * @see java.util.List#add(int, Object)
     */
    public void add( final int arg0, final Object arg1 )
    {
        vector.add( arg0, arg1 );
    }

    /**
     * @see java.util.List#remove(int)
     */
    public Object remove( final int arg0 )
    {
        return vector.remove( arg0 );
    }

    /**
     * @see java.util.List#indexOf(Object)
     */
    public int indexOf( final Object arg0 )
    {
        return vector.indexOf( arg0 );
    }

    /**
     * @see java.util.List#lastIndexOf(Object)
     */
    public int lastIndexOf( final Object arg0 )
    {
        return vector.lastIndexOf( arg0 );
    }

    /**
     * @see java.util.List#listIterator()
     */
    public ListIterator listIterator()
    {
        return vector.listIterator();
    }

    /**
     * @see java.util.List#listIterator(int)
     */
    public ListIterator listIterator( final int arg0 )
    {
        return vector.listIterator( arg0 );
    }

    /**
     * @see java.util.List#subList(int, int)
     */
    public List subList( final int arg0, final int arg1 )
    {
        return vector.subList( arg0, arg1 );
    }

    /**
     * @return La liste des elements
     */
    public Enumeration elements()
    {
        return vector.elements();
    }
}