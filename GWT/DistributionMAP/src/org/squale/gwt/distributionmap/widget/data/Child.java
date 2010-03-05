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

/**
 * A data class that represents an element of the DMap.
 * 
 * @author Fabrice BELLINGARD
 */
public class Child
    implements Serializable
{

    /**
     * UID for serialization
     */
    private static final long serialVersionUID = 3710225568047601168L;

    /**
     * ID of the element
     */
    private long id;

    /**
     * Name of the element
     */
    private String name;

    /**
     * Grade of the element
     */
    private float grade;

    /**
     * Default constructor (needed by GWT)
     */
    public Child()
    {

    }

    /**
     * Main constructor
     * 
     * @param id ID
     * @param name name
     * @param grade grade
     */
    public Child( long id, String name, float grade )
    {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    /**
     * @return the id
     */
    public long getId()
    {
        return id;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return the grade
     */
    public float getGrade()
    {
        return grade;
    }

    @Override
    public String toString()
    {
        return "#" + id + " - " + name;
    }

}
