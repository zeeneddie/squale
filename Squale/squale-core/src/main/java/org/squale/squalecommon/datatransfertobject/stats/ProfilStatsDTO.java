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
package org.squale.squalecommon.datatransfertobject.stats;

/**
 */
public class ProfilStatsDTO
    extends CommonStatsDTO
{

    /**
     * Le nom du profil
     */
    private String mProfileName;

    /**
     * @return le nom du profil
     */
    public String getProfileName()
    {
        return mProfileName;
    }

    /**
     * @param pProfileName le nouveau nom du profile
     */
    public void setProfileName( String pProfileName )
    {
        mProfileName = pProfileName;
    }

}
