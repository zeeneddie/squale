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
package org.squale.squalecommon.daolayer.config.web;

import java.util.List;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.enterpriselayer.businessobject.config.web.AbstractDisplayConfBO;

/**
 * DAO pour les configurations d'affichage
 */
public class AbstractDisplayConfDAOImpl
    extends AbstractDAOImpl
{

    /**
     * Instance singleton
     */
    private static AbstractDisplayConfDAOImpl instance = null;

    /** initialisation du singleton */
    static
    {
        instance = new AbstractDisplayConfDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private AbstractDisplayConfDAOImpl()
    {
        initialize( AbstractDisplayConfBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static AbstractDisplayConfDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * @param pSession la session
     * @param pSubclass la classe que l'on veut
     * @return les configurations du type réel de <code>pConf</code>
     * @throws JrafDaoException si exception
     */
    public List findAllSubclass( ISession pSession, Class pSubclass )
        throws JrafDaoException
    {
        String subclass = pSubclass.getName().substring( pSubclass.getName().lastIndexOf( "." ) + 1 );
        StringBuffer query = new StringBuffer( "select " );
        query.append( getAlias() );
        query.append( " from " );
        query.append( subclass );
        query.append( " as " );
        query.append( getAlias() );
        return find( pSession, query.toString() );
    }

    /**
     * Supprime les configurations qui ne sont plus utilisées
     * 
     * @param pSession la session hibernate
     * @throws JrafDaoException si erreur
     */
    public void removeUnusedConf( ISession pSession )
        throws JrafDaoException
    {
        StringBuffer whereClause = new StringBuffer( "where " );
        whereClause.append( getAlias() );
        // Pas de lien avec des profils (sinon pourra être utilisée)
        whereClause.append( ".id not in (select profileConf.displayConf.id from Profile_DisplayConfBO profileConf) and " );
        whereClause.append( getAlias() );
        // Pas de lien avec des audits
        whereClause.append( ".id not in (select auditConf.displayConf.id from AuditDisplayConfBO auditConf)" );
        removeWhere( pSession, whereClause.toString() );
    }
}
