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
package org.squale.squaleweb.connection.basic;

import java.util.ArrayList;
import java.util.List;
import org.squale.squaleweb.connection.IUserBean;

/**
 * This class is the implementation of the basic userBean.
 * The userBean contains the profiles of the authenticated user. 
 * In the basic implementation the authentication verification is done directly in the
 * squale database (in the userBO table)
 */
public class BasicUserBeanImpl
    implements IUserBean
{

    /** The administrator profile */
    private static final String ADMIN_PROFILE = "bo.profile.name.admin";

    /** the profiles of the userBean */
    protected List profiles;

    /**
     * Default constructor
     */
    public BasicUserBeanImpl()
    {
        profiles = new ArrayList(0);
    }

    /**
     * Constructor with one arguments
     * 
     * @param userProfiles The profiles of the authenticated user
     */
    public BasicUserBeanImpl( List userProfiles )
    {
        profiles = userProfiles;
    }

    /**
     * This method indicate if the authenticated user is a squale administrator. An authenticated user is a squale
     * administrator if its list of profiles contains the profile : bo.profile.name.admin
     * 
     * @return return true if the authenticated user is a squale administrator
     */
    public boolean isAdmin()
    {
        return profiles.contains( ADMIN_PROFILE );

    }

}
