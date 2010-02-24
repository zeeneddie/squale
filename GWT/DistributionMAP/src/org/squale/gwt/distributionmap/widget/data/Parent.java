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
 * @author fabrice
 */
public class Parent
    implements Serializable
{

    private static final long serialVersionUID = 1691475443808497741L;

    private String name;

    private ArrayList<Child> children;

    public Parent()
    {
    }

    public Parent( String name )
    {
        this.name = name;
        children = new ArrayList<Child>();
    }

    public void addChild( Child child )
    {
        children.add( child );
    }

    public Collection<Child> getChildren()
    {
        return children;
    }

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
