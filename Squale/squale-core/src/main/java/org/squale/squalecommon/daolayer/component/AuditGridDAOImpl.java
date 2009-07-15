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
package org.squale.squalecommon.daolayer.component;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditGridBO;

/**
 * Interface avec la base de données pour les grilles d'audit
 */
public class AuditGridDAOImpl
    extends AbstractDAOImpl
{
    /**
     * Instance singleton
     */
    private static AuditGridDAOImpl instance = null;

    /** log */
    private static Log LOG;

    /** initialisation du singleton */
    static
    {
        instance = new AuditGridDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private AuditGridDAOImpl()
    {
        initialize( AuditGridBO.class );
        if ( null == LOG )
        {
            LOG = LogFactory.getLog( AuditGridDAOImpl.class );
        }
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static AuditGridDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * Obtention des grilles d'audit
     * 
     * @param pSession session
     * @param pProjectID projet
     * @param pAuditID audit
     * @return résultats associés
     * @throws JrafDaoException si erreur
     */
    public AuditGridBO findWhere( ISession pSession, Long pProjectID, Long pAuditID )
        throws JrafDaoException
    {
        String whereClause = "where ";
        whereClause += getAlias() + ".project.id = '" + pProjectID + "'";
        whereClause += " and ";
        whereClause += getAlias() + ".audit.id = '" + pAuditID + "'";

        AuditGridBO result = null;
        Collection col = findWhere( pSession, whereClause );
        if ( col.size() == 1 )
        {
            result = (AuditGridBO) col.iterator().next();
        }
        return result;
    }

    /**
     * Test d'utilisation d'une grille
     * 
     * @param pSession session
     * @param pGridId grille
     * @return true
     * @throws JrafDaoException si erreur
     */
    public boolean isGridUsed( ISession pSession, Long pGridId )
        throws JrafDaoException
    {
        String whereClause = "where " + getAlias() + ".grid.id=" + pGridId;
        return findWhere( pSession, whereClause ).size() > 0;
    }
}
