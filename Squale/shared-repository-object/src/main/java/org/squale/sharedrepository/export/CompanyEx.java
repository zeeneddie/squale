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

import java.util.List;

import org.squale.sharedrepository.segment.SegmentEx;

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
     * The id / name of the company
     */
    @XStreamAsAttribute
    private String name;

    /**
     * The list of applications of the company
     */
    @XStreamAsAttribute
    private ApplicationEx application;
    
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
      * Default constructor
     */
    public CompanyEx()
    {

    }

    /**
     * Full constructor
     * 
     * @param pName The id / name of the company
     * @param pSegmentation The list of segment linked to the company
     * @param pDatas The list of data linked to the company
     */
    public CompanyEx( String pName, List<SegmentEx> pSegmentation, List<DataEx> pDatas)
    {
        super();
        name = pName;
        segmentation = pSegmentation;
        datas = pDatas;
    }

    /**
     * Add a new application {@link ApplicationEx} to the company
     * 
     * @param application The application to add
     */
    /*public void addApplication( ApplicationEx application )
    {
        applications.add( application );
    }*/

    /**
     * Getter method for the attribute name
     * 
     * @return The name / id of the company
     */
    public String getName()
    {
        return name;
    }

    /**
     * Setter method for the attribute name
     * 
     * @param pName The name / id of the company
     */
    public void setName( String pName )
    {
        name = pName;
    }

    /**
     * Getter method for the attribute segmentation
     * 
     * @return The list of segment linked to the company
     */
    public List<SegmentEx> getSegmentation()
    {
        return segmentation;
    }

    /**
     * Setter method for the attribute segmentation
     * 
     * @param pSegmentation The new list of segment linked to the company
     */
    public void setSegmentation( List<SegmentEx> pSegmentation )
    {
        segmentation = pSegmentation;
    }

    /**
     * Getter method for the attribute datas
     * 
     * @return The list of data linked to the company
     */
    public List<DataEx> getDatas()
    {
        return datas;
    }

    /**
     * Setter method for the attribute datas
     * 
     * @param pDatas The new list of data linked to the company
     */
    public void setDatas( List<DataEx> pDatas )
    {
        datas = pDatas;
    }

    /**
     * Getter method for the attribute applications
     * 
     * @return The list of application linked to the company
     */
    public ApplicationEx getApplication()
    {
        return application;
    }

    /**
     * Setter method for the attribute applications
     * 
     * @param pApplication The application linked to the company
     */
    public void setApplication( ApplicationEx pApplication )
    {
        application = pApplication;
    }

}
