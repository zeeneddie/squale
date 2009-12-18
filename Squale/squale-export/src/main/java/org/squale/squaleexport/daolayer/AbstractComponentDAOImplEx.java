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
import org.squale.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ComponentType;
import org.squale.squalecommon.util.mapping.Mapping;

/**
 * This class is an implementation of the abstract class {@link AbstractDAOImpl}. This class is a dao linked to the
 * business object {@link AbstractComponentBO } specific to SqualeExport.
 */
public final class AbstractComponentDAOImplEx
    extends AbstractDAOImpl
{

    /**
     * Singleton instance of AbstractComponentDAOImplEx
     */
    private static AbstractComponentDAOImplEx instance;

    /** Log */
    private static Log LOG;

    /**
     * Private constructor
     */
    private AbstractComponentDAOImplEx()
    {
        initialize( AbstractComponentBO.class );
        // Initiate the log
        LOG = LogFactory.getLog( AbstractComponentDAOImplEx.class );
    }

    /**
     * Return the singleton. Create the instance if it not already exist.
     * 
     * @return The instance of the AbstractComponentDAOImplEx
     */
    public static AbstractComponentDAOImplEx getInstance()
    {
        if ( instance == null )
        {
            instance = new AbstractComponentDAOImplEx();
        }
        return instance;
    }

    /**
     * <p>
     * This method search in the db all the component which :
     * <ul>
     * <li>have for parent : parentId</li>
     * <li>are involved in the audit : auditId</li>
     * <li>are components of type : type (clas, method, ...)</li>
     * </ul>
     * </p>
     * 
     * @param session The Hibernate session
     * @param auditId The id of the audit
     * @param parentId The id of the parent component
     * @param type The type of component search
     * @return The list of component which matching the criterium given in argument
     * @throws JrafDaoException Error happened during the search in the database
     */
    public List<AbstractComponentBO> searchChildrenByAuditByTypeByParent( ISession session, Long auditId,
                                                                          Long parentId, String type )
        throws JrafDaoException
    {
        List<AbstractComponentBO> childList = null;
        StringBuffer whereClause = new StringBuffer( " where " );
        whereClause.append( getAlias() );
        whereClause.append( ".audits.id = " );
        whereClause.append( auditId );
        if ( parentId != null )
        {
            whereClause.append( " and " );
            whereClause.append( getAlias() );
            whereClause.append( ".parent.id = " );
            whereClause.append( parentId );
        }
        if ( type != null )
        {
            String className = Mapping.getComponentMappingName( type );
            whereClause.append( " and " );
            whereClause.append( getAlias() );
            whereClause.append( ".class = '" );
            whereClause.append( className );
            whereClause.append( "'" );
        }

        LOG.debug( whereClause.toString() );

        childList = (List<AbstractComponentBO>) findWhere( session, whereClause.toString() );

        return childList;
    }

    /**
     * <p>
     * This method search in the db all the component which :
     * <ul>
     * <li>have for parent : parentId</li>
     * <li>are involved in the audit : auditId</li>
     * </ul>
     * </p>
     * 
     * @param session The Hibernate session
     * @param auditId The id of the audit
     * @param parentId The id of the parent component
     * @return A list of all the component which are involved in the audit : auditId and have to parent : parentId
     * @throws JrafDaoException Error happened during the search in the database
     */
    public List<AbstractComponentBO> searchModuleByAudit( ISession session, Long auditId, Long parentId )
        throws JrafDaoException
    {
        return searchChildrenByAuditByTypeByParent( session, auditId, parentId, ComponentType.PROJECT );
    }

    /**
     * This method search in the db
     * 
     * @param session The Hibernate session
     * @param auditId The auditId
     * @param parentId The id of the parent component
     * @return All the components which are involved in the audit : auditId and which have for parent component :
     *         parentId
     * @throws JrafDaoException Error happened during the search in the database
     */
    public List<AbstractComponentBO> searchChildrenByAuditAndParent( ISession session, Long auditId, Long parentId )
        throws JrafDaoException
    {

        return searchChildrenByAuditByTypeByParent( session, auditId, parentId, null );
    }

    /**
     * This method search in the database all the components which are involved in the audit specified in argument
     * 
     * @param session The Hibernate session
     * @param auditId The id of the audit
     * @return All the components involved in the audit : auditId
     * @throws JrafDaoException Error happened during the search in the database
     */
    public List<AbstractComponentBO> allComponentInvolvedInAudit( ISession session, Long auditId )
        throws JrafDaoException
    {
        return searchChildrenByAuditByTypeByParent( session, auditId, null, null );
    }

}
