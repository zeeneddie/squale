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

import java.util.List;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.sharedrepository.segmentref.CategoryRefType;
import org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.segment.SegmentCategoryBO;

/**
 * DAO implementation for the business object : {@link SegmentCategoryBO}
 */
public final class SegmentCategoryDAOImpl
    extends AbstractDAOImpl
{

    /**
     * Singleton
     */
    private static SegmentCategoryDAOImpl instance;

    /**
     * Initialization of the singleton
     */
    static
    {
        instance = new SegmentCategoryDAOImpl();
    }

    /**
     * Private constructor
     */
    private SegmentCategoryDAOImpl()
    {
        initialize( SegmentCategoryBO.class );
    }

    /**
     * Return the singleton instance of the dao
     * 
     * @return The singleton instance of the dao
     */
    public static SegmentCategoryDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * This method retrieve the identifier and the technical id for each segment category
     * 
     * @param session The hibernate session
     * @return A list of array : [ Long identifier , Long technicalId ]
     * @throws JrafDaoException Exception occurs during the retrieve in the database
     */
    public List<Object[]> retrieveAllCatIdentifierTechnicalId( ISession session )
        throws JrafDaoException
    {
        StringBuffer request = new StringBuffer( "Select cat.identifier, cat.technicalId from SegmentCategoryBO cat" );
        List<Object[]> listCatIdentifierTechnicalId = find( session, request.toString() );
        return listCatIdentifierTechnicalId;
    }

    /**
     * This method retrieve a list of segment category
     * 
     * @param session The hibernate session
     * @param type The category type
     * @return a list of segment category
     * @throws JrafDaoException exception occurs during the search ins the database
     */
    public List<SegmentCategoryBO> getByType( ISession session, CategoryRefType type )
        throws JrafDaoException
    {
        StringBuffer whereClause = new StringBuffer( "where " );
        whereClause.append( getAlias() );
        whereClause.append( ".type = '" );
        whereClause.append( type );
        whereClause.append( "' order by " );
        whereClause.append( getAlias() );
        whereClause.append( ".categoryName " );
        return findWhere( session, whereClause.toString() );
    }
}
