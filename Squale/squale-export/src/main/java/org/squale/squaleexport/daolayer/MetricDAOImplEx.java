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
import org.squale.squalecommon.enterpriselayer.businessobject.result.MetricBO;

/**
 * This class is an implementation of the abstract class {@link AbstractDAOImpl}. This class is a dao linked to the
 * business object {@link MetricBO } specific to SqualeExport.
 */
public final class MetricDAOImplEx
    extends AbstractDAOImpl
{

    /**
     * Singleton
     */
    private static MetricDAOImplEx instance;

    /** Log */
    private static Log LOG;

    /**
     * Private constructor
     */
    private MetricDAOImplEx()
    {
        initialize( MetricBO.class );
        LOG = LogFactory.getLog( MetricDAOImplEx.class );
    }

    /**
     * Return the single instance of the DAO
     * 
     * @return The singleton of the DAO
     */
    public static MetricDAOImplEx getInstance()
    {
        if ( instance == null )
        {
            instance = new MetricDAOImplEx();
        }
        return instance;
    }

    /**
     * <p>
     * This method search in the db all the metric which :
     * <ul>
     * <li>are involved in the audit : auditId</li>
     * <li>come from the task whose name is : taskName</li>
     * <li>whose name is : metricName</li>
     * </ul>
     * </p>
     * 
     * @param session The hibernate session
     * @param auditId The audit id
     * @param taskName The name of the task
     * @param metricName The name of the metric
     * @return The list of metric which correspond to the element given in argument
     * @throws JrafDaoException Exception occurs during the search in the datbase
     */
    public List<MetricBO> findMetricByMetricName( ISession session, Long auditId, String taskName, String metricName )
        throws JrafDaoException
    {
        List<MetricBO> result = null;
        StringBuffer whereClause = new StringBuffer( " where " );
        whereClause.append( getAlias() );
        whereClause.append( ".measure.audit.id = " );
        whereClause.append( auditId );
        whereClause.append( " and " );
        whereClause.append( getAlias() );
        whereClause.append( ".measure.taskName = '" );
        whereClause.append( taskName );
        whereClause.append( "' and " );
        whereClause.append( getAlias() );
        whereClause.append( ".name ='" );
        whereClause.append( metricName );
        whereClause.append( "'" );
        LOG.debug( whereClause.toString() );
        result = (List<MetricBO>) findWhere( session, whereClause.toString() );
        return result;
    }

}
