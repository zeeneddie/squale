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
package org.squale.squalecommon.daolayer.job;

import java.util.List;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.enterpriselayer.businessobject.job.JobBO;

/**
 * 
 * DAO implemetation for {@link JobBO}
 *
 */
public final class JobDAOImpl
    extends AbstractDAOImpl
{

    /**
     * Singleton
     */
    private static JobDAOImpl instance;

    /** Initialization of the singleton */
    static
    {
        instance = new JobDAOImpl();
    }

    /**
     * Private constructor
     */
    private JobDAOImpl()
    {
        initialize( JobBO.class );
    }

    /**
     * Return the singleton instance of the dao
     * 
     * @return The singleton instance of the dao
     */
    public static JobDAOImpl getInstance()
    {
        return instance;
    }
    
    /**
     * This method search in the database all the job which have for name the one given in argument 
     * 
     * @param session The hibernate session
     * @param name The job name
     * @return The list of jobs found
     * @throws JrafDaoException Exception occurs during the search of the JobBO in the database
     */
    public List<JobBO> getJobsByName(ISession session, String name) throws JrafDaoException
    {
        StringBuffer whereClause = new StringBuffer("where ");
        whereClause.append( getAlias() );
        whereClause.append( ".jobName = '");
        whereClause.append( name );
        whereClause.append( "' order by " );
        whereClause.append( getAlias() );
        whereClause.append( ".jobDate" );
        List<JobBO> listJobFound =(List<JobBO>)findWhere( session, whereClause.toString() );
        return listJobFound;
    }
    
}
