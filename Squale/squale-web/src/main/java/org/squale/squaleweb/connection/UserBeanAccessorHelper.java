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
package com.airfrance.squaleweb.connection;

import com.airfrance.jraf.bootstrap.locator.SpringLocator;

/**
 * Accès au provider de sécurité
 */
public class UserBeanAccessorHelper
{

    /**
     * Retourne le bean permettant d'accèder à l'utilisateur authentifié
     * 
     * @return l'accesseur de bean
     */
    public final static IUserBeanAccessor getUserBeanAccessor()
    {
        IUserBeanAccessor accessor = null;
        SpringLocator springLocator = SpringLocator.getInstance();
        accessor = (IUserBeanAccessor) springLocator.getBean( "userBeanAccessor" );
        return accessor;
    }

}
