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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.enterpriselayer.businessobject.config.AdminParamsBO;

/**
 * Implementation of the DAO for the adminParamsBO
 */
public final class AdminParamsDAOImpl
    extends AbstractDAOImpl
{

    /**
     * Singleton
     */
    private static AdminParamsDAOImpl instance;

    /** Initialization of singleton */
    static
    {
        instance = new AdminParamsDAOImpl();
    }

    /**
     * Private constructor
     * 
     * @throws JrafDaoException
     */
    private AdminParamsDAOImpl()
    {
        initialize( AdminParamsBO.class );
    }

    /**
     * Return the singleton of the DAO
     * 
     * @return the singleton of the DAO
     */
    public static AdminParamsDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * Find an adminParamsBO by its paramKey
     * 
     * @param session The hibernate session
     * @param paramKey The parameter to search
     * @return The list of adminsParamsBO found
     * @throws JrafDaoException Exception happened during the search by hibernate
     */
    public List findByKey( ISession session, String paramKey )
        throws JrafDaoException
    {
        StringBuffer whereClause = new StringBuffer( "where " );
        whereClause.append( getAlias() );
        whereClause.append( ".paramKey = '" );
        whereClause.append( paramKey );
        whereClause.append( "'" );
        List resultFind = findWhere( session, whereClause.toString() );
        return resultFind;
    }

    /**
     * Find an adminParamsBO by its paramKey
     * 
     * @param session The hibernate session
     * @param paramKey The parameter to search
     * @return The list of adminsParamsBO found
     * @throws JrafDaoException Exception happened during the search by hibernate
     */
    public List findByKeyLike( ISession session, String paramKey )
        throws JrafDaoException
    {
        StringBuffer whereClause = new StringBuffer( "where " );
        whereClause.append( getAlias() );
        whereClause.append( ".paramKey like '" );
        whereClause.append( paramKey );
        whereClause.append( "%" );
        whereClause.append( "'" );
        List resultFind = findWhere( session, whereClause.toString() );
        return resultFind;
    }
    
    /**
     * This method create a new adminParamsBO in the database if one with the same paramKey doesn't exist. If only one
     * already exists then it is update. If the method found many match for the paramKey then the method return false
     * 
     * @param session The hibernate session
     * @param adminParamsBOCollection The collection of adminparamsBO to create or update in the database
     * @return false in the method find many match for the search on paramKey
     * @throws JrafDaoException exception happened during the hibernate work
     */
    public boolean createOrUpdate( ISession session, Collection<AdminParamsBO> adminParamsBOCollection )
        throws JrafDaoException
    {
        boolean onlyOneMatch = true;
        AdminParamsBO bo = new AdminParamsBO();
        Iterator<AdminParamsBO> paramsBOIterator = adminParamsBOCollection.iterator();
        while ( paramsBOIterator.hasNext() )
        {
            bo = paramsBOIterator.next();

            // We search record in the database with the same paramKey
            List resultFind = findByKey( session, bo.getParamKey() );

            // only one record with the same paramKey should exist
            if ( resultFind.size() > 1 )
            {
                onlyOneMatch = false;
            }

            // If no record with this paramKey exist then we create it
            else if ( resultFind.size() == 0 )
            {
                create( session, bo );
            }

            // If one record already exist we update it
            else
            {
                AdminParamsBO find = (AdminParamsBO) resultFind.get( 0 );
                find.setParamValue( bo.getParamValue() );
                save( session, find );
            }

        }
        return onlyOneMatch;
    }
    
    

}
