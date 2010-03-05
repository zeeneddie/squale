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
/**
 * 
 */
package org.squale.gwt.distributionmap.widget.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A data class that represents the parent of an element of the DMap.
 * 
 * @author Fabrice BELLINGARD
 */
public class Parent
    implements Serializable
{
    /**
     * UID for serialization
     */
    private static final long serialVersionUID = 1691475443808497741L;

    /**
     * Name of the parent element
     */
    private String name;

    /**
     * The list of children of this parent
     */
    private ArrayList<Child> children;

    /**
     * Default constructor (needed by GWT)
     */
    public Parent()
    {
    }

    /**
     * Main constructor
     * 
     * @param name
     */
    public Parent( String name )
    {
        this.name = name;
        children = new ArrayList<Child>();
    }

    /**
     * Adds a child to this parent.
     * 
     * @param child the child to add
     */
    public void addChild( Child child )
    {
        children.add( child );
    }

    /**
     * Gets the children of this element
     * 
     * @return the chid collection
     */
    public Collection<Child> getChildren()
    {
        return children;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return name;
    }

}
