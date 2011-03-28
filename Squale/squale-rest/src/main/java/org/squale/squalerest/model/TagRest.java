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
package org.squale.squalerest.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Application class used for the export
 */
@XStreamAlias( "tag" )
public class TagRest
{

    /**
     * Name of the application
     */
    @XStreamAsAttribute
    private String name;
    
    /**
     * Name of the tag category
     */
    @XStreamAsAttribute
    private String category;

    /**
     * Constructor
     */
    public TagRest()
    {

    }
    
    /**
     * Full constructor
     * 
     * @param pName Name of the application
     * @param pCategoryName Name of the category
     */
    public TagRest( String pName, String pCategoryName )
    {
        name = pName;
        category = pCategoryName;
    }

    /**
     * Getter method for the attribute name
     * 
     * @return The application name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Setter method for the attribute name
     * 
     * @param pName The new name of the application
     */
    public void setName( String pName )
    {
        name = pName;
    }

    /**
     * Getter method for the attribute categoryName
     * 
     * @return The category name
     */
    public String getCategoryName()
    {
        return category;
    }

    /**
     * Setter method for the attribute categoryName
     * 
     * @param pCategoryName The new catgory name
     */
    public void setCategoryName( String pCategoryName )
    {
        this.category = pCategoryName;
    }

}