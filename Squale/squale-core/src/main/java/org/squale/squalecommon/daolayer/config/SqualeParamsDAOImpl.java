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
package org.squale.squalecommon.daolayer.config;

import java.util.List;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.enterpriselayer.businessobject.config.SqualeParamsBO;

/**
 * Implementation of the DAO for the {@link SqualeParamsBO}
 */
public final class SqualeParamsDAOImpl
    extends AbstractDAOImpl
{

    /**
     * Singleton
     */
    private static SqualeParamsDAOImpl instance;

    /** Initialization of singleton */
    static
    {
        instance = new SqualeParamsDAOImpl();
    }

    /**
     * Private constructor
     * 
     * @throws JrafDaoException
     */
    private SqualeParamsDAOImpl()
    {
        initialize( SqualeParamsBO.class );
    }

    /**
     * Return the singleton of the DAO
     * 
     * @return the singleton of the DAO
     */
    public static SqualeParamsDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * Find an adminParamsBO by its paramKey
     * 
     * @param session The hibernate session
     * @param paramKey The parameter to search
     * @return The list of SqualeParamsBO found
     * @throws JrafDaoException Exception happened during the search by hibernate
     */
    public List<SqualeParamsBO> findByKey( ISession session, String paramKey )
        throws JrafDaoException
    {
        StringBuffer whereClause = new StringBuffer( "where " );
        whereClause.append( getAlias() );
        whereClause.append( ".paramKey = '" );
        whereClause.append( paramKey );
        whereClause.append( "'" );
        List<SqualeParamsBO> resultFind = findWhere( session, whereClause.toString() );
        return resultFind;
    }
}
