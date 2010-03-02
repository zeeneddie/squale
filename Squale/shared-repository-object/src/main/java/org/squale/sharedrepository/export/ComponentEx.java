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
package org.squale.sharedrepository.export;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Component class used for the export
 */
@XStreamAlias( "component" )
public class ComponentEx
{

    /**
     * Id of the component
     */
    @XStreamAsAttribute
    private String name;

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
    private List<DataEx> datas;

    /**
     * List of the components linked to the component
     */
    @XStreamImplicit
    private List<ComponentEx> components;

    /**
     * Default constructor
     */
    public ComponentEx()
    {
        datas = new ArrayList<DataEx>();
        components = new ArrayList<ComponentEx>();
    }

    /**
     * Full constructor
     * 
     * @param pName The name of the component
     * @param pType The type of the component
     * @param pLanguage The language linked to the component
     * @param pDatas The list of data
     * @param pComponents The list of components
     */
    public ComponentEx( String pName, String pType, String pLanguage ,List<DataEx> pDatas, List<ComponentEx> pComponents )
    {
        name = pName;
        type = pType;
        language = pLanguage;
        datas = pDatas;
        components = pComponents;
    }

    /**
     * Add a new child component({@link ComponentEx} to the component
     * 
     * @param compo The new child component
     */
    public void addComponent( ComponentEx compo )
    {
        components.add( compo );
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
    public List<DataEx> getDatas()
    {
        return datas;
    }

    /**
     * Setter method for the attribute datas
     * 
     * @param pDatas The new list of data linked to the component
     */
    public void setDatas( List<DataEx> pDatas )
    {
        datas = pDatas;
    }

    /**
     * Getter method for the attribute components
     * 
     * @return The list of child components linked to the component
     */
    public List<ComponentEx> getComponents()
    {
        return components;
    }

    /**
     * Setter method for the attribute components
     * 
     * @param pComponents The new list of child components linked to the component
     */
    public void setComponents( List<ComponentEx> pComponents )
    {
        components = pComponents;
    }

    /**
     * Getter method for the attribute naame
     * 
     * @return The name of the component
     */
    public String getName()
    {
        return name;
    }

    /**
     * Setter method for the attribute name
     * 
     * @param pName The new name of the component
     */
    public void setName( String pName )
    {
        name = pName;
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
