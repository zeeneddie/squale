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
package org.squale.squalecommon.daolayer.sharedrepository.segment;

import java.util.ArrayList;
import java.util.List;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.sharedrepository.segmentref.CategoryRefType;
import org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.segment.SegmentBO;

/**
 * DAO implementation for the business object : {@link SegmentBO}
 */
public final class SegmentDAOImpl
    extends AbstractDAOImpl
{

    /**
     * Singleton
     */
    private static SegmentDAOImpl instance;

    /** Initialization of the singleton */
    static
    {
        instance = new SegmentDAOImpl();
    }

    /**
     * Private constructor
     */
    private SegmentDAOImpl()
    {
        initialize( SegmentBO.class );
    }

    /**
     * Return the singleton instance of the dao
     * 
     * @return The singleton instance of the dao
     */
    public static SegmentDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * This method retrieve the identifier and the technical id for each segment whose category has for technical id has
     * the one given in argument
     * 
     * @param session The hibernate session
     * @param technicalId The technical id of the category link to the segment
     * @return A list of array : [ Long identifier , Long technicalId ]
     * @throws JrafDaoException Exception occurs during the retrieve in the database
     */
    public List<Object[]> retrieveAllSegIdentifierTechnicalId( ISession session, long technicalId )
        throws JrafDaoException
    {
        StringBuffer request = new StringBuffer( "Select seg.identifier, seg.technicalId from SegmentBO seg " );
        request.append( "where seg.segmentCategory.technicalId = " );
        request.append( technicalId );
        List<Object[]> listSegIdentifierTechnicalId = find( session, request.toString() );
        return listSegIdentifierTechnicalId;
    }

    /**
     * This method retrieve the technical id of all the segment selected for the component given in argument
     * 
     * @param session The hibernate session
     * @param id The id of the component
     * @param type The category type
     * @return A list of array : [ Long technicalId ]
     * @throws JrafDaoException exception occurs during the search
     */
    public List<Long> findSegmentByType( ISession session, long id, CategoryRefType type )
        throws JrafDaoException
    {
        List<Long> segmentList = new ArrayList<Long>();
        StringBuffer request = new StringBuffer( "select seg.technicalId " );
        request.append( "from SegmentBO seg " );
        request.append( "where " );
        request.append( id );
        request.append( " in elements ( seg.moduleList ) and " );
        request.append( "seg.segmentCategory.type = '" );
        request.append( type );
        request.append( "'" );
        segmentList = (List<Long>) find( session, request.toString() );
        return segmentList;

    }

    /**
     * This method returns the list of segment linked to the module (id) given in argument
     * 
     * @param session the hibernate session
     * @param id The module id
     * @return The list of segment
     * @throws JrafDaoException exception occurs during the search in the database
     */
    public List<SegmentBO> findModuleSegments( ISession session, long id )
        throws JrafDaoException
    {
        List<SegmentBO> segmentList = new ArrayList<SegmentBO>();
        StringBuffer request = new StringBuffer( "select seg " );
        request.append( "from SegmentBO seg " );
        request.append( "where " );
        request.append( id );
        request.append( " in elements ( seg.moduleList )");
        segmentList = find( session, request.toString() ); 
        return segmentList;
    }

}
