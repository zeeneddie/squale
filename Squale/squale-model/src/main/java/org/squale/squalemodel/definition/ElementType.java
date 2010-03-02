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
package org.squale.squalemodel.definition;

/**
 * This {@link Enum} list all the component type for a java module
 */
public enum ElementType
{
    /**
     * Company component
     */
    COMPANY( "company" ),

    /**
     * Application component
     */
    APPLICATION( "application" ),

    /**
     * Module component
     */
    MODULE( "module" ),

    /**
     * Package component
     */
    PACKAGE( "package" ),

    /**
     * Class component
     */
    CLASS( "class" ),

    /**
     * Method component
     */
    METHOD( "method" );

    /**
     * Constructor
     * 
     * @param pXmlTag The xml tag
     */
    private ElementType( String pXmlTag )
    {
        xmlTag = pXmlTag;
    }

    /**
     * The xml tag name
     */
    private String xmlTag;

    /**
     * The language
     */
    //private Language language;

    /**
     * The language linked to this kind of component
     * 
     * @return The language
     */
    /*
     * public Language getLanguage() { return language; }
     */

    /**
     * Getter method for the attribute xmlTag
     * 
     * @return The corresponding xml tag
     */
    public String getXmlTag()
    {
        return xmlTag;
    }

}
