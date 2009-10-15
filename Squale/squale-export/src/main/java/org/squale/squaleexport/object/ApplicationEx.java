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
    public List<ModuleEx> getModules()
    {
        return modules;
    }

    /**
     * @param pModules
     */
    public void setModules( List<ModuleEx> pModules )
    {
        modules = pModules;
    }

}
