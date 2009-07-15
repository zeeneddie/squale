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
package com.airfrance.squalecommon.daolayer.component;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditDisplayConfBO;

/**
 * Interface avec la base de données pour les configurations d'affichage des audits
 */
public class AuditDisplayConfDAOImpl
    extends AbstractDAOImpl
{
    /**
     * Instance singleton
     */
    private static AuditDisplayConfDAOImpl instance = null;

    /** log */
    private static Log LOG;

    /** initialisation du singleton */
    static
    {
        instance = new AuditDisplayConfDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private AuditDisplayConfDAOImpl()
    {
        initialize( AuditDisplayConfBO.class );
        if ( null == LOG )
        {
            LOG = LogFactory.getLog( AuditDisplayConfDAOImpl.class );
        }
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static AuditDisplayConfDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * Obtention d'une configuration
     * 
     * @param pSession session
     * @param pProjectID projet
     * @param pAuditID audit
     * @param pSubclass le type de configuration à chercher
     * @return résultats associés
     * @throws JrafDaoException si erreur
     */
    public AuditDisplayConfBO findConfigurationWhere( ISession pSession, Long pProjectID, Long pAuditID,
                                                      String pSubclass )
        throws JrafDaoException
    {
        return findConfigurationWhere( pSession, pProjectID, pAuditID, pSubclass, null );
    }

    /**
     * Obtention d'une configuration
     * 
     * @param pSession session
     * @param pProjectID projet
     * @param pAuditID audit
     * @param pSubclass le type de configuration à chercher
     * @param pComponentType le type de composant (application, projet,...) ou null si inutile
     * @return résultats associés
     * @throws JrafDaoException si erreur
     */
    public AuditDisplayConfBO findConfigurationWhere( ISession pSession, Long pProjectID, Long pAuditID,
                                                      String pSubclass, String pComponentType )
        throws JrafDaoException
    {
        String whereClause = "where ";
        whereClause += getAlias() + ".project.id = '" + pProjectID + "'";
        whereClause += " and ";
        whereClause += getAlias() + ".audit.id = '" + pAuditID + "'";
        whereClause += " and ";
        whereClause += getAlias() + ".displayConf.class = '" + pSubclass + "'";
        if ( null != pComponentType )
        {
            whereClause += " and ";
            whereClause += getAlias() + ".displayConf.componentType = '" + pComponentType + "'";
        }

        AuditDisplayConfBO result = null;
        Collection col = findWhere( pSession, whereClause );
        if ( col.size() == 1 )
        {
            result = (AuditDisplayConfBO) col.iterator().next();
        }
        return result;
    }
}
