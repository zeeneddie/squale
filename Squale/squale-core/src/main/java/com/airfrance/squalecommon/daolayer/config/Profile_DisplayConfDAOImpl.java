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

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.Profile_DisplayConfBO;

/**
 * DAO pour le lien profil-configuration
 */
public class Profile_DisplayConfDAOImpl
    extends AbstractDAOImpl
{

    /**
     * Instance singleton
     */
    private static Profile_DisplayConfDAOImpl instance = null;

    /** initialisation du singleton */
    static
    {
        instance = new Profile_DisplayConfDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private Profile_DisplayConfDAOImpl()
    {
        initialize( Profile_DisplayConfBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static Profile_DisplayConfDAOImpl getInstance()
    {
        return instance;
    }
}
