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
import org.squale.squalecommon.enterpriselayer.businessobject.result.QualityResultBO;

/**
 * This class is an implementation of the abstract class {@link AbstractDAOImpl}. This class is a dao linked to the
 * business object {@link QualityResultBO } specific to SqualeExport.
 */
public final class QualityResultDAOImplEx
    extends AbstractDAOImpl
{

    /**
     * Singleton
     */
    private static QualityResultDAOImplEx instance;

    /** Log */
    private static Log LOG;

    /**
     * Private constructor
     */
    private QualityResultDAOImplEx()
    {
        initialize( QualityResultBO.class );
        LOG = LogFactory.getLog( QualityResultDAOImplEx.class );
    }

    /**
     * Return the single instance of the dao
     * 
     * @return The singleton of the DAO
     */
    public static QualityResultDAOImplEx getInstance()
    {
        if ( instance == null )
        {
            instance = new QualityResultDAOImplEx();
        }
        return instance;
    }

    /**
     *  <p>
     * This method search in the db all the factor which :
     * <ul>
     * <li>are factor of the module : moduleId</li>
     * <li>are involved in the audit : auditId</li>
     * </ul>
     * </p>
     * 
     * @param session The hibernate session
     * @param moduleId The module id
     * @param auditId The audit id
     * @return The list of factor find
     * @throws JrafDaoException Exception occurs during the search in the db
     */
    public List<QualityResultBO> findFactor( ISession session, Long moduleId, Long auditId )
        throws JrafDaoException
    {

        StringBuffer whereClause = new StringBuffer( " where " );
        whereClause.append( getAlias() );
        whereClause.append( ".project.id = " );
        whereClause.append( moduleId );
        whereClause.append( " and " );
        whereClause.append( getAlias() );
        whereClause.append( ".audit.id = " );
        whereClause.append( auditId );
        whereClause.append( " and " );
        whereClause.append( getAlias() );
        whereClause.append( ".rule.class='FactorRule' order by " );
        whereClause.append( getAlias() );
        whereClause.append( ".rule.name" );

        List<QualityResultBO> result = null;
        result = (List<QualityResultBO>) findWhere( session, whereClause.toString() );

        LOG.debug( whereClause.toString() );
        return result;
    }

    /**
     *  <p>
     * This method search in the db all the criterium which :
     * <ul>
     * <li>are factor of the module : moduleId</li>
     * <li>are involved in the audit : auditId</li>
     * </ul>
     * </p>
     * 
     * @param session The hibernate session
     * @param moduleId The module id
     * @param auditId The audit id
     * @return The list of criterium find
     * @throws JrafDaoException Exception occurs during the search in the db
     */
    public List<QualityResultBO> findCriterium( ISession session, Long moduleId, Long auditId )
        throws JrafDaoException
    {

        StringBuffer whereClause = new StringBuffer( " where " );
        whereClause.append( getAlias() );
        whereClause.append( ".project.id = " );
        whereClause.append( moduleId );
        whereClause.append( " and " );
        whereClause.append( getAlias() );
        whereClause.append( ".audit.id = " );
        whereClause.append( auditId );
        whereClause.append( " and " );
        whereClause.append( getAlias() );
        whereClause.append( ".rule.class='CriteriumRule' order by " );
        whereClause.append( getAlias() );
        whereClause.append( ".rule.name" );

        List<QualityResultBO> result = null;
        result = (List<QualityResultBO>) findWhere( session, whereClause.toString() );

        LOG.debug( whereClause.toString() );
        return result;
    }

}
