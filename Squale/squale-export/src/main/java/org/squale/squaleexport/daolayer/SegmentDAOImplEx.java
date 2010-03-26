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
package org.squale.squaleexport.daolayer;

import java.util.List;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.segment.SegmentBO;

/**
 * DAO implementation for the business object : {@link SegmentBO}
 */
public final class SegmentDAOImplEx
    extends AbstractDAOImpl
{

    /**
     * Singleton
     */
    private static SegmentDAOImplEx instance;

    /** Initialization of the singleton */
    static
    {
        instance = new SegmentDAOImplEx();
    }

    /**
     * Private constructor
     */
    private SegmentDAOImplEx()
    {
        initialize( SegmentBO.class );
    }

    /**
     * Return the singleton instance of the dao
     * 
     * @return The singleton instance of the dao
     */
    public static SegmentDAOImplEx getInstance()
    {
        return instance;
    }

    /**
     * This method recover all the segment linked to the component given in argument
     * 
     * @param session The hibernate session
     * @param componentId The component id
     * @return The list of identifier of the segment linked to component given in argument
     * @throws JrafDaoException Exception occurs during the retrieve in the database
     */
    public List<Long> retrieveSegmentIdentifierByComponent( ISession session, long componentId )
        throws JrafDaoException
    {
        StringBuffer request = new StringBuffer( "Select seg.identifier from SegmentBO seg " );
        request.append( "where seg.moduleList.id = " );
        request.append( componentId );
        List<Long> listSegIdebntifierTechnicalId = find( session, request.toString() );
        return listSegIdebntifierTechnicalId;
    }

}
