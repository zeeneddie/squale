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
package org.squale.squalecommon.daolayer.message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.enterpriselayer.businessobject.message.MessageBO;

/**
 * Couche DAO pour les messages
 */
public class MessageDAOImpl
    extends AbstractDAOImpl
{
    /**
     * Instance singleton
     */
    private static MessageDAOImpl instance = null;

    /** initialisation du singleton */
    static
    {
        instance = new MessageDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private MessageDAOImpl()
    {
        initialize( MessageBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static MessageDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * @param pSession la session
     * @param pKey la clé
     * @param pLang la langue (peut etre null si sans importance)
     * @return les messages dont la clé contient la chaine passé en paramètre
     * @throws JrafDaoException en cas d'échec
     */
    public Collection findWhereKey( ISession pSession, String pKey, String pLang )
        throws JrafDaoException
    {
        Collection coll = null;
        String whereClause = "";
        whereClause += "where " + getAlias() + ".id.key = 'news." + pKey + "'";
        if ( pLang != null )
        {
            whereClause += " AND " + getAlias() + ".id.lang='" + pLang + "'";
        }
        coll = findWhere( pSession, whereClause );
        return coll;

    }

    /**
     * @param pSession la session
     * @return toutes les langues disponibles
     * @throws JrafDaoException en cas d'échec
     */
    public Collection findLangs( ISession pSession )
        throws JrafDaoException
    {
        Collection coll = null;
        Collection result = new ArrayList( 0 );
        coll = findAll( pSession );
        if ( coll != null )
        {
            Iterator it = coll.iterator();
            while ( it.hasNext() )
            {
                MessageBO mess = (MessageBO) it.next();
                if ( !result.contains( mess.getLang() ) )
                {
                    result.add( mess.getLang() );
                }
            }
        }
        return result;
    }

    /**
     * @param pSession la session
     * @param pMessBO le message à supprimer
     * @throws JrafDaoException en cas d'échecs
     */
    public void removeMessage( ISession pSession, MessageBO pMessBO )
        throws JrafDaoException
    {
        String whereClause = "where " + getAlias() + ".id.key like '%" + pMessBO.getKey() + "%'";
        removeWhere( pSession, whereClause );
    }
}
