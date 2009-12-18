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

import java.util.List;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.ApplicationExportBO;

/**
 * DAO implementation for the business object : {@link ApplicationExportBO}
 */
public final class ApplicationExportDAOImpl
    extends AbstractDAOImpl
{

    /**
     * Singleton
     */
    private static ApplicationExportDAOImpl instance;

    /** Initialization of the singleton */
    static
    {
        instance = new ApplicationExportDAOImpl();
    }

    /**
     * Private constructor
     */
    private ApplicationExportDAOImpl()
    {
        initialize( ApplicationExportBO.class );
    }

    /**
     * Return the singleton instance of the dao
     * 
     * @return The singleton instance of the dao
     */
    public static ApplicationExportDAOImpl getInstance()
    {
        return instance;
    }
    
    /**
     * This method search the applicationExport by their application id
     * 
     * @param session The hibernate session
     * @param appId The applications
     * @return The list of applicationExport found
     * @throws JrafDaoException Exception occurs during the search
     */
    public List<ApplicationExportBO> getLastExportByApplication ( ISession session, long appId ) throws JrafDaoException
    {
        StringBuffer whereClause = new StringBuffer("where ");
        whereClause.append( getAlias() );
        whereClause.append( ".application = " );
        whereClause.append( appId );
        List<ApplicationExportBO> appExport = (List<ApplicationExportBO>)findWhere( session, whereClause.toString() );
        return appExport;
    }

}
