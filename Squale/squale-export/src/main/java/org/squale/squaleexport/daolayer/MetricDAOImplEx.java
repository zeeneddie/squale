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
 *
 */
public final class MetricDAOImplEx
    extends AbstractDAOImpl
{

    /**
     * Instance singleton
     */
    private static MetricDAOImplEx instance;

    /** log */
    private static Log LOG;

    /**
     * Constructeur prive
     */
    private MetricDAOImplEx()
    {
        initialize( MetricBO.class );
        LOG = LogFactory.getLog( MetricDAOImplEx.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
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
     * 
     * @param session
     * @param compId
     * @param auditId
     * @param taskName
     * @param metricName
     * @return
     * @throws JrafDaoException
     */
    public List<MetricBO> findMetric( ISession session, Long compId, Long auditId, String taskName, String metricName )
        throws JrafDaoException
    {

        List<MetricBO> result = null;

        StringBuffer whereClause = new StringBuffer( " where " );
        whereClause.append( getAlias() );
        whereClause.append( ".measure.component.id = " );
        whereClause.append( compId );
        whereClause.append( " and " );
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

    /**
     * 
     * @param session
     * @param auditId
     * @param taskName
     * @param metricName
     * @return
     * @throws JrafDaoException
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
