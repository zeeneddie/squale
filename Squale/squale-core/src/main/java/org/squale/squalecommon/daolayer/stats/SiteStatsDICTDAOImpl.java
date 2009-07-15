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
package org.squale.squalecommon.daolayer.stats;

import java.util.ArrayList;
import java.util.Collection;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.enterpriselayer.businessobject.stats.SiteStatsDICTBO;

/**
 */
public class SiteStatsDICTDAOImpl
    extends AbstractDAOImpl
{

    /**
     * Instance singleton
     */
    private static SiteStatsDICTDAOImpl instance = null;

    /**
     * initialisation du singleton
     */
    static
    {
        instance = new SiteStatsDICTDAOImpl();
    }

    /**
     * Constructeur privé
     * 
     * @throws JrafDaoException
     */
    private SiteStatsDICTDAOImpl()
    {
        initialize( SiteStatsDICTBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static SiteStatsDICTDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * @param pSession la session
     * @param pSite l'id du site
     * @return une liste contenant les objets contenant les stats du site (normalement 1 seul objet)
     * @throws JrafDaoException en cas d'échecs
     */
    public Collection findBySite( ISession pSession, long pSite )
        throws JrafDaoException
    {
        Collection result = new ArrayList( 0 );
        String whereClause = "where " + getAlias() + ".serveurBO.serveurId = '" + pSite + "'";
        result = findWhere( pSession, whereClause );
        return result;
    }

    /**
     * Supprime les stats pour ce site
     * 
     * @param pSession la session
     * @param pSite l'id du site
     * @throws JrafDaoException en cas d'échecs
     */
    public void removeWhereSite( ISession pSession, long pSite )
        throws JrafDaoException
    {
        String whereClause = "where " + getAlias() + ".serveurBO.serveurId = '" + pSite + "'";
        removeWhere( pSession, whereClause );

    }

}
