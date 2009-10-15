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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;

/**
 * This class is an implementation of the abstract class {@link AbstractDAOImpl}. This class is a dao linked to the
 * business object {@link AuditDAOImplEx } specific to SqualeExport.
 */
public final class AuditDAOImplEx
    extends AbstractDAOImpl
{

    /**
     * singleton
     */
    private static AuditDAOImplEx instance;

    /** log */
    private static Log LOG;

    /**
     * private constructor
     */
    private AuditDAOImplEx()
    {
        initialize( AuditBO.class );
        LOG = LogFactory.getLog( AuditDAOImplEx.class );
    }

    /**
     * Return the singleton of the DAO. Instanciate it, if it not already exist.
     * 
     * @return return the singleton of the DAO
     */
    public static AuditDAOImplEx getInstance()
    {
        if ( instance == null )
        {
            instance = new AuditDAOImplEx();
        }
        return instance;
    }

    /**
     * Recover the id of the last successful audit (milestone or follow up) for the application
     * 
     * @param session The hibernate session
     * @param applicationId The id of the application for which we search the audit
     * @return The id of the last successful
     * @throws JrafDaoException Error happened during the request in the db
     */
    public long lastSuccesfullAudit( ISession session, Long applicationId )
        throws JrafDaoException
    {
        long auditId = -1;

        StringBuffer whereClause = new StringBuffer( " where " );
        // The audit should be associate to our application
        whereClause.append( applicationId );
        whereClause.append( " in elements( " );
        whereClause.append( getAlias() );
        whereClause.append( ".components )" );
        // The audit should be successful
        whereClause.append( " and " );
        whereClause.append( getAlias() );
        whereClause.append( ".status = " );
        whereClause.append( AuditBO.TERMINATED );
        // The result should be ordered
        whereClause.append( " order by coalesce( " );
        whereClause.append( getAlias() );
        whereClause.append( ".historicalDate, " );
        whereClause.append( getAlias() );
        whereClause.append( ".date) desc" );

        LOG.debug( whereClause.toString() );

        List<AuditBO> result = (List<AuditBO>) findWhere( session, whereClause.toString() );
        if ( !result.isEmpty() )
        {
            AuditBO audit = result.get( 0 );
            auditId = audit.getId();
        }

        return auditId;
    }

}
