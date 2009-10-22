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
/*
 * Créé le 6 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.squalecommon.daolayer.profile;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.daolayer.DAOMessages;
import org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;

/**
 * @author M400843
 */
public class ProfileDAOImpl
    extends AbstractDAOImpl
{
    /**
     * Instance singleton
     */
    private static ProfileDAOImpl instance = null;

    /** log */
    private static Log LOG;

    /** initialisation du singleton */
    static
    {
        instance = new ProfileDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private ProfileDAOImpl()
    {
        initialize( ProfileBO.class );
        if ( null == LOG )
        {
            LOG = LogFactory.getLog( UserDAOImpl.class );
        }
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static ProfileDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * Récupère ProfileBO associé à la clé passée en paramètre
     * 
     * @param pSession la session
     * @param pKey clé du profil
     * @return ProfileBO
     * @throws JrafDaoException exception Dao
     */
    public ProfileBO loadByKey( ISession pSession, String pKey )
        throws JrafDaoException
    {
        String whereClause = "where ";
        whereClause += getAlias() + ".name = '" + pKey + "'";

        ProfileBO profile = null;
        Collection col = findWhere( pSession, whereClause );
        if ( col.size() >= 1 )
        {
            profile = (ProfileBO) col.iterator().next();
            if ( col.size() > 1 )
            {
                String tab[] = { pKey };
                LOG.warn( DAOMessages.getString( "profile.many.key", tab ) );
            }
        }
        return profile;
    }
}
