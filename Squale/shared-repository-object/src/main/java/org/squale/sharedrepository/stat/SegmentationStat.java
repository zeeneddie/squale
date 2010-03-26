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

import org.squale.sharedrepository.export.SegmentEx;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This class represent the reference for one segmentation (segmentation = group of segment)
 */
@XStreamAlias( "segmentation" )
public class SegmentationStat
{

    /**
     * The list of segment which compose the reference
     */
    @XStreamImplicit
    private List<SegmentEx> segmentIdList;

    /**
     * Statistics for the module
     */
    @XStreamAsAttribute
    private ModuleStat module;

    /**
     * The list of components of the module
     */
    @XStreamImplicit
    private List<ComponentStat> components;

    /**
     * Constructor
     */
    public SegmentationStat()
    {
        segmentIdList = new ArrayList<SegmentEx>();
        components = new ArrayList<ComponentStat>();
    }

    /**
     * Full constructor
     * 
     * @param pSegmentList List of SegmentEx (segment identifier)
     * @param pModule The ModuleStat
     * @param pComponentStats The list of ComponentStat
     */
    public SegmentationStat( List<SegmentEx> pSegmentList, ModuleStat pModule, List<ComponentStat> pComponentStats )
    {
        segmentIdList = pSegmentList;
        module = pModule;
        components = pComponentStats;
    }

    /**
     * Getter method for the attribute segmentList
     * 
     * @return The list of segment
     */
    public List<SegmentEx> getSegmentIdList()
    {
        return segmentIdList;
    }

    /**
     * Getter method for the attribute segmentList
     * 
     * @param pSegmentIdList The list of segmentEx
     */
    public void setSegmentIdList( List<SegmentEx> pSegmentIdList )
    {
        segmentIdList = pSegmentIdList;
    }

    /**
     * Add a segment to the list of segment
     * 
     * @param segment The SegmentEx to add
     */
    public void addSegment( SegmentEx segment )
    {
        segmentIdList.add( segment );
    }

    /**
     * Getter method for the attribute module
     * 
     * @return the module
     */
    public ModuleStat getModule()
    {
        return module;
    }

    /**
     * Setter for the attribute module
     * 
     * @param pModule The new module
     */
    public void setModule( ModuleStat pModule )
    {
        module = pModule;
    }

    /**
     * Getter method for the attribute components
     * 
     * @return The list of component
     */
    public List<ComponentStat> getComponents()
    {
        return components;
    }

    /**
     * Setter method for the attribute components
     * 
     * @param pComponents The new list of components
     */
    public void setComponents( List<ComponentStat> pComponents )
    {
        components = pComponents;
    }

    /**
     * This method add a new component to the list of component
     * 
     * @param pComponent Add a new ComponentStat
     */
    public void addComponent( ComponentStat pComponent )
    {
        components.add( pComponent );
    }

}
