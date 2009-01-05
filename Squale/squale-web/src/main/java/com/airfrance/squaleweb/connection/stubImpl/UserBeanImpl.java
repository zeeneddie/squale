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
package com.airfrance.squaleweb.connection.stubImpl;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.squaleweb.connection.IUserBean;

/**
 * This class is a stub implementation of userBean.
 * So in this case the method isAdmin always return true
 */
public class UserBeanImpl
    implements IUserBean
{

    /** Profiles list */
    protected List profiles;

    /**
     * Default constructor
     */
    public UserBeanImpl()
    {
        profiles = new ArrayList( 0 );
    }

    /**
     * In this implementation the authenticated user is always an admin
     * @return true
     */
    public boolean isAdmin()
    {
        return true;
    }



}
