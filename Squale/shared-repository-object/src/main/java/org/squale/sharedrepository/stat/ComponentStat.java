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
package org.squale.sharedrepository.stat;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Component class used for the export
 */
@XStreamAlias( "component" )
public class ComponentStat
{

    /**
     * Type of the component
     */
    @XStreamAsAttribute
    private String type;

    /**
     * Indicate to which language the component is linked 
     */
    @XStreamAsAttribute
    private String language;
    
    /**
     * List of the data linked to the component
     */
    @XStreamImplicit
    private List<DataStat> datas;

    /**
     * Default constructor
     */
    public ComponentStat()
    {
        datas = new ArrayList<DataStat>();
    }

    /**
     * Full constructor
     * 
     * @param pType The type of the component
     * @param pLanguage The language
     * @param pDatas The list of data
     */
    public ComponentStat( String pType, String pLanguage, List<DataStat> pDatas )
    {
        type = pType;
        language = pLanguage;
        datas = pDatas;
    }

    /**
     * Getter method for the attribute type
     * 
     * @return The type of the component
     */
    public String getType()
    {
        return type;
    }

    /**
     * Setter method for the attribute type
     * 
     * @param pType The new type of the component
     */
    public void setType( String pType )
    {
        type = pType;
    }

    /**
     * Getter method for the attribute datas
     * 
     * @return The list of data linked to the component
     */
    public List<DataStat> getDatas()
    {
        return datas;
    }

    /**
     * Setter method for the attribute datas
     * 
     * @param pDatas The new list of data linked to the component
     */
    public void setDatas( List<DataStat> pDatas )
    {
        datas = pDatas;
    }
    
    /**
     * Getter method for the attribute language
     * 
     * @return The language
     */
    public String getLanguage()
    {
        return language;
    }

    /**
     * Setter method for the attribute language
     * 
     * @param pLanguage The new language
     */
    public void setLanguage( String pLanguage )
    {
        language = pLanguage;
    }

}
