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
package org.squale.squalecommon.daolayer.sharedrepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.SegmentationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.segment.SegmentBO;

/**
 * DAO implementation for the business object : {@link SegmentationBO}
 */
public final class SegmentationDAOImpl
    extends AbstractDAOImpl
{

    /**
     * Singleton
     */
    private static SegmentationDAOImpl instance;

    /** Initialization of the singleton */
    static
    {
        instance = new SegmentationDAOImpl();
    }

    /**
     * Private constructor
     */
    private SegmentationDAOImpl()
    {
        initialize( SegmentationBO.class );
    }

    /**
     * Return the singleton instance of the dao
     * 
     * @return The singleton instance of the dao
     */
    public static SegmentationDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * This method retrieves the segmentation corresponding to the list of segments given in argument
     * 
     * @param session The hibernate session
     * @param segmentlist The list of segment
     * @return The corresponding segmentation
     * @throws JrafDaoException Exception occurs during the search
     */
    public List<SegmentationBO> findContainsSegments( ISession session, List<SegmentBO> segmentlist )
        throws JrafDaoException
    {
        List<SegmentationBO> segmentationList = new ArrayList<SegmentationBO>();
        StringBuffer request = new StringBuffer( "select seg from SegmentationBO seg where " );
        Iterator<SegmentBO> segmentIt = segmentlist.iterator();
        for ( SegmentBO segmentBO : segmentlist )
        {
            request.append( segmentBO.getTechnicalId() );
            request.append( " member of seg.segmentList" );
            request.append( " and " );
        }
        request.append( " size ( seg.segmentList ) = " );
        request.append( segmentlist.size() );
        segmentationList = find( session, request.toString() );
        return segmentationList;
    }

}
