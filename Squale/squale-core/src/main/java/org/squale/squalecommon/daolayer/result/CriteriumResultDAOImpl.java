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
 * Créé le 19 juil. 05
 *
 */
package org.squale.squalecommon.daolayer.result;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import org.squale.squalecommon.enterpriselayer.businessobject.result.CriteriumResultBO;

/**
 * @author M400843
 */
public class CriteriumResultDAOImpl
    extends AbstractDAOImpl
{
    /**
     * Instance singleton
     */
    private static CriteriumResultDAOImpl instance = null;

    /** initialisation du singleton */
    static
    {
        instance = new CriteriumResultDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private CriteriumResultDAOImpl()
    {
        initialize( CriteriumResultBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     * @deprecated
     */
    public static CriteriumResultDAOImpl getInstance()
    {
        return instance;
    }
}
