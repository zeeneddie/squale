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
package com.airfrance.squaleweb.applicationlayer.action.accessRights;

import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;
import com.airfrance.squaleweb.applicationlayer.formbean.LogonBean;

/**
 */
public class ManagerAction
    extends BaseDispatchAction
{

    /**
     * vérifie les droits de l'utilisateur à effectuer cette action
     * 
     * @param pApplicationId l'id de l'application
     * @param pUser l'utilisateur courant
     * @return un booléen indiquant si l'utilisateur possède les droits suffisants
     */
    protected boolean checkRights( LogonBean pUser, Long pApplicationId )
    {
        return pUser.isAdmin() || ( pApplicationId != null && pUser.getApplicationRight( pApplicationId ) != null // illegal
                                                                                                                    // access
        && pUser.getApplicationRight( pApplicationId ).equals( ProfileBO.MANAGER_PROFILE_NAME ) );
    }
}
