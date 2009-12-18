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
 * Application class used for the export
 */
@XStreamAlias( "application" )
public class ApplicationEx
{

    /**
     * Id of the application
     */
    @XStreamAsAttribute
    private String name;

    /**
     * List of segment ({@link SegmentEx}) linked to the application
     */
    @XStreamImplicit
    private List<SegmentEx> segmentation;

    /**
     * List of data ({@link DataEx}) linked to the application
     */
    @XStreamImplicit
    private List<DataEx> datas;

    /**
     * List of modules ({@link ModuleEx}) linked to the application
     */
    @XStreamImplicit
    private List<ModuleEx> modules;

    /**
     * Constructor
     */
    public ApplicationEx()
    {

    }

    /**
     * Full constructor
     * 
     * @param pName Id of the application
     * @param pSegmentation List of segment ({@link SegmentEx}) linked to the application
     * @param pDatas List of data ({@link DataEx}) linked to the application
     * @param pModules List of module ({@link ModuleEx} linked to the application
     */
    public ApplicationEx( String pName, List<SegmentEx> pSegmentation, List<DataEx> pDatas, List<ModuleEx> pModules )
    {
        name = pName;
        segmentation = pSegmentation;
        datas = pDatas;
        modules = pModules;
    }

    /**
     * Add a new module({@link ModuleEx} to the application
     * 
     * @param module The new module to add
     */
    public void addModule( ModuleEx module )
    {
        modules.add( module );
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
     * Getter method for the attribute segmentation
     * 
     * @return The list of segmentEx
     */
    public List<SegmentEx> getSegmentation()
    {
        return segmentation;
    }

    /**
     * Setter method for the attribute segmentation
     * 
     * @param pSegmentation The new list of segmentEx
     */
    public void setSegmentation( List<SegmentEx> pSegmentation )
    {
        segmentation = pSegmentation;
    }

    /**
     * Getter method for the attribute datas
     * 
     * @return The list of dataEx
     */
    public List<DataEx> getDatas()
    {
        return datas;
    }

    /**
     * Setter method for the attribute datas
     * 
     * @param pDatas The new list of dataEx
     */
    public void setDatas( List<DataEx> pDatas )
    {
        datas = pDatas;
    }

    /**
     * Getter method for the attribute modules
     * 
     * @return The list of moduleEx
     */
    public List<ModuleEx> getModules()
    {
        return modules;
    }

    /**
     * Setter method for the attribute modules
     * 
     * @param pModules The new list of moduleEx
     */
    public void setModules( List<ModuleEx> pModules )
    {
        modules = pModules;
    }

}
