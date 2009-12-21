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
package org.squale.squaleweb.applicationlayer.action.accessRights;

import org.apache.commons.lang.ArrayUtils;
import org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;
import org.squale.squaleweb.applicationlayer.formbean.LogonBean;

/**
 */
public class ReaderAction
    extends BaseDispatchAction
{
    /**
     * rem : admin profile is authorized too but authorization relies on {@link LogonBean#isAdmin()} instead.
     */
    private static final String[] AUTHORIZED_PROFILES_NAMES =
        { ProfileBO.MANAGER_PROFILE_NAME, ProfileBO.AUDITOR_PROFILE_NAME, ProfileBO.READER_PROFILE_NAME };

    /**
     * vérifie les droits de l'utilisateur à effectuer cette action
     * 
     * @param pApplicationId la liste des utilisateurs de l'application
     * @param pUser l'utilisateur courant
     * @return un booléen indiquant si l'utilisateur possède les droits suffisants
     */
    protected boolean checkRights( LogonBean pUser, Long pApplicationId )
    {
        boolean res = false;

        if ( pUser.isAdmin() )
        {
            res = true;
        }
        else
        {
            res = isUserAuthorizedForApp( pUser, pApplicationId );
        }

        return res;
    }

    /**
     * Tells if some user's profile for a given app belongs to the list of authorized profiles.
     * 
     * @param pUser the user we want to check the authorization
     * @param pApplicationId application id. if null, this method returns <code>false</code>.
     * @return true if user has an authorized profile for the app
     */
    private boolean isUserAuthorizedForApp( LogonBean pUser, Long pApplicationId )
    {
        if ( pApplicationId == null )
        {
            return false;
        }

        String profile = pUser.getApplicationRight( pApplicationId );
        return ArrayUtils.contains( AUTHORIZED_PROFILES_NAMES, profile );
    }
}
