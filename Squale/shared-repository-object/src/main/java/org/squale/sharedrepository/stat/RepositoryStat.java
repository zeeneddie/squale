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
 * Repository use for create the reference file. This class correspond to the root node of the reference file
 */
@XStreamAlias( "repository" )
public class RepositoryStat
{

    /**
     * The reference version
     */
    @XStreamAsAttribute
    private int version;

    /**
     * The shred repository version
     */
    @XStreamAsAttribute
    private String sharedRepositoryVersion;

    /**
     * The list of segmentation
     */
    @XStreamImplicit
    private List<SegmentationStat> segmentationList;

    /**
     * Constructor
     */
    public RepositoryStat()
    {
        segmentationList = new ArrayList<SegmentationStat>();
    }

    /**
     * Constructor
     * 
     * @param pVersion The reference version
     * @param pSharedRepositoryVersion The shared repository version
     * @param pSegmentationList The segmentation list
     */
    public RepositoryStat( int pVersion, String pSharedRepositoryVersion, List<SegmentationStat> pSegmentationList )
    {
        version = pVersion;
        sharedRepositoryVersion = pSharedRepositoryVersion;
        segmentationList = pSegmentationList;
    }

    /**
     * Getter method for the reference version
     * 
     * @return The reference version
     */
    public int getVersion()
    {
        return version;
    }

    /**
     * Setter method for the reference version
     * 
     * @param pVersion The new reference version
     */
    public void setVersion( int pVersion )
    {
        version = pVersion;
    }

    /**
     * Getter method for the shared repository version
     * 
     * @return The shared repository version
     */
    public String getSharedRepositoryVersion()
    {
        return sharedRepositoryVersion;
    }

    /**
     * Setter method for the shared repository version
     * 
     * @param pSharedRepositoryVersion The new shared repository version
     */
    public void setSharedRepositoryVersion( String pSharedRepositoryVersion )
    {
        sharedRepositoryVersion = pSharedRepositoryVersion;
    }

    /**
     * Getter method for the list of segmentation
     * 
     * @return The list of segmentation
     */
    public List<SegmentationStat> getSegmentationList()
    {
        return segmentationList;
    }

    /**
     * Add a new segmentation to the list of segmentation
     * 
     * @param segmentation The segmentation to add
     */
    public void addSegmentation( SegmentationStat segmentation )
    {
        segmentationList.add( segmentation );
    }

}
