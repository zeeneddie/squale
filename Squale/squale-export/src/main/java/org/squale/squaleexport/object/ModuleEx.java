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
package org.squale.squaleexport.object;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 
 *
 */
@XStreamAlias( "module" )
public class ModuleEx
{

    /**
     * 
     */
    @XStreamAsAttribute
    private String name;

    /**
     * 
     */
    @XStreamImplicit
    private List<SegmentEx> segmentation;

    /**
     * 
     */
    @XStreamImplicit
    private List<DataEx> datas;

    /**
     * 
     */
    @XStreamImplicit
    private List<ComponentEx> components;

    /**
     * 
     */
    public ModuleEx()
    {

    }

    /**
     * @param pName
     * @param pSegmentation
     * @param pDatas
     * @param pComponents
     */
    public ModuleEx( String pName, List<SegmentEx> pSegmentation, List<DataEx> pDatas, List<ComponentEx> pComponents )
    {
        name = pName;
        segmentation = pSegmentation;
        datas = pDatas;
        components = pComponents;
    }

    /**
     * @param compo
     */
    public void addComponent( ComponentEx compo )
    {
        components.add( compo );
    }

    /**
     * @return
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param pName
     */
    public void setName( String pName )
    {
        name = pName;
    }

    /**
     * @return
     */
    public List<SegmentEx> getSegmentation()
    {
        return segmentation;
    }

    /**
     * @param pSegmentation
     */
    public void setSegmentation( List<SegmentEx> pSegmentation )
    {
        segmentation = pSegmentation;
    }

    /**
     * @return
     */
    public List<DataEx> getDatas()
    {
        return datas;
    }

    /**
     * @param pDatas
     */
    public void setDatas( List<DataEx> pDatas )
    {
        datas = pDatas;
    }

    /**
     * @return
     */
    public List<ComponentEx> getComponents()
    {
        return components;
    }

    /**
     * @param pComponents
     */
    public void setComponents( List<ComponentEx> pComponents )
    {
        components = pComponents;
    }

}
