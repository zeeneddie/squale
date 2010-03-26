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
 * Module class used for the export
 */
@XStreamAlias( "module" )
public class ModuleEx
{

    /**
     * The name of the module
     */
    @XStreamAsAttribute
    private String name;

    /**
     * The list of segment of the module
     */
    @XStreamImplicit
    private List<SegmentEx> segmentation;

    /**
     * The list of data of the module
     */
    @XStreamImplicit
    private List<DataEx> datas;

    /**
     * The list of components of the module
     */
    @XStreamImplicit
    private List<ComponentEx> components;

    /**
     * Default constructor
     */
    public ModuleEx()
    {
        segmentation = new ArrayList<SegmentEx>();
        datas = new ArrayList<DataEx>();
        components = new ArrayList<ComponentEx>();
    }

    /**
     * Constructor
     * 
     * @param pName The name of the module
     * @param pSegmentation The list of segment linked to module
     * @param pDatas The list of data linked to the module
     * @param pComponents The list of component linked to the module
     */
    public ModuleEx( String pName, List<SegmentEx> pSegmentation, List<DataEx> pDatas, List<ComponentEx> pComponents )
    {
        name = pName;
        segmentation = pSegmentation;
        datas = pDatas;
        components = pComponents;
    }

    /**
     * This method add a component {@link ComponentEx} to the module
     * 
     * @param compo The component to add
     */
    public void addComponent( ComponentEx compo )
    {
        components.add( compo );
    }

    /**
     * Getter method for the attribute name
     * 
     * @return The name of the module
     */
    public String getName()
    {
        return name;
    }

    /**
     * Setter method for the attribute name
     * 
     * @param pName The new name of the module
     */
    public void setName( String pName )
    {
        name = pName;
    }

    /**
     * Getter method for the attribute segmentation
     * 
     * @return The list of segment linked to the module
     */
    public List<SegmentEx> getSegmentation()
    {
        return segmentation;
    }

    /**
     * Setter method for the attribute segmentation
     * 
     * @param pSegmentation The new list of segment linked to the module
     */
    public void setSegmentation( List<SegmentEx> pSegmentation )
    {
        segmentation = pSegmentation;
    }

    /**
     * Getter method for the attribute datas
     * 
     * @return The list of data linked to the module
     */
    public List<DataEx> getDatas()
    {
        return datas;
    }

    /**
     * Setter method for the attribute datas
     * 
     * @param pDatas The new list of data linked to the module
     */
    public void setDatas( List<DataEx> pDatas )
    {
        datas = pDatas;
    }

    /**
     * Getter method for the attribute components
     * 
     * @return The list of component linked to the module
     */
    public List<ComponentEx> getComponents()
    {
        return components;
    }

    /**
     * Setter method for the attribute components
     * 
     * @param pComponents The new list of component linked to the module
     */
    public void setComponents( List<ComponentEx> pComponents )
    {
        components = pComponents;
    }

}
