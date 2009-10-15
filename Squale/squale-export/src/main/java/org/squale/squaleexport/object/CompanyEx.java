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
 * This class is an object for the xstream mapping It represent an object of type company. It contains
 */
@XStreamAlias( "company" )
public class CompanyEx
{

    /**
     * The id name of the company
     */
    @XStreamAsAttribute
    private String name;

    /**
     * The list of segements of the company
     */
    @XStreamImplicit
    private List<SegmentEx> segmentation;

    /**
     * The list of datas
     */
    @XStreamImplicit
    private List<DataEx> datas;

    /**
     * 
     */
    @XStreamImplicit
    private List<ApplicationEx> applications;

    /**
     * 
     */
    public CompanyEx()
    {

    }

    /**
     * @param name
     * @param segmentation
     * @param datas
     * @param applications
     */
    public CompanyEx( String pName, List<SegmentEx> pSegmentation, List<DataEx> pDatas,
                      List<ApplicationEx> pApplications )
    {
        super();
        name = pName;
        segmentation = pSegmentation;
        datas = pDatas;
        applications = pApplications;
    }

    /**
     * @param app
     */
    public void addApplication( ApplicationEx app )
    {
        applications.add( app );
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
    public List<ApplicationEx> getApplications()
    {
        return applications;
    }

    /**
     * @param pApplications
     */
    public void setApplications( List<ApplicationEx> pApplications )
    {
        applications = pApplications;
    }

}
