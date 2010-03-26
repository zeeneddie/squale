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
package org.squale.sharedrepository.segmentref;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This class represents a label for a segment or a segment category
 */
@XStreamAlias( "label" )
public class Label
{

    /**
     * The label language
     */
    @XStreamAsAttribute
    private String lang;

    /**
     * The label value
     */
    @XStreamAsAttribute
    private String label;

    /**
     * Constructor
     * 
     * @param pLang The language label
     * @param pLabel The value of the label
     */
    public Label( String pLang, String pLabel )
    {
        this.lang = pLang;
        this.label = pLabel;
    }

    /**
     * Getter method for the attribute lang
     * 
     * @return The language of the label
     */
    public String getLang()
    {
        return lang;
    }

    /**
     * Setter method for the attribute lang
     * 
     * @param pLang The new language of the label
     */
    public void setLang( String pLang )
    {
        lang = pLang;
    }

    /**
     * Getter method for the attribute label
     * 
     * @return The value of the label
     */
    public String getLabel()
    {
        return label;
    }

    /**
     * Setter method for the attribute label
     * 
     * @param pLabel the new value of the label
     */
    public void setLabel( String pLabel )
    {
        label = pLabel;
    }

}
