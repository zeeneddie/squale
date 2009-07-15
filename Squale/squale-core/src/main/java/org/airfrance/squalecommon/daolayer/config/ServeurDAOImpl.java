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
package com.airfrance.squalecommon.daolayer.config;

import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ServeurBO;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;

import java.util.Collection;
import java.util.Iterator;

/**
 * DAO du Serveur d'exécution de Squalix
 */
public class ServeurDAOImpl
    extends AbstractDAOImpl
{

    /**
     * Instance singleton
     */
    private static ServeurDAOImpl instance = null;

    /** initialisation du singleton */
    static
    {
        instance = new ServeurDAOImpl();
    }

    /**
     * Constructeur privé
     */
    private ServeurDAOImpl()
    {
        initialize( ServeurBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static ServeurDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * Donne la liste de tous les serveurs
     * 
     * @param pSession la session hibernate
     * @return la liste des serveurs
     * @throws JrafDaoException si une erreur survient
     */
    public Collection listeServeurs( ISession pSession )
        throws JrafDaoException
    {
        Collection pCollection = super.findAll( pSession );
        return pCollection;
    }
    
    /**
     * Retourne le serveur correspondant à l'ID fourni
     * @param pSession la session Hibernate
     * @param pServeurId l'ID du serveur à chercher
     * @return le serveur trouvé
     * @throws JrafDaoException si une erreur survient
     */
    public ServeurBO findWhereId ( ISession pSession, Long pServeurId )
    	throws JrafDaoException
    {
    	ServeurBO result = null;
    	StringBuffer whereClause = new StringBuffer( "where " );
		whereClause.append( getAlias() );
        whereClause.append( ".serveurId = '" );
        whereClause.append( pServeurId );
        whereClause.append( "'" );
        Collection results = this.findWhere( pSession, whereClause.toString() );
        Iterator it = results.iterator();
        // Il ne doit y avoir qu'un résultat:
        if ( it.hasNext() )
        {
            result = (ServeurBO) it.next();
        }
        return result;
    }
}
