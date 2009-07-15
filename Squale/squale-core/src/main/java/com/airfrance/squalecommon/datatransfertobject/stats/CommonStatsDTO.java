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
package com.airfrance.squalecommon.datatransfertobject.stats;

/**
 */
public class CommonStatsDTO
{

    /**
     * Le nombre de projets
     */
    protected int mNbProjects;

    /**
     * Le nombre de lignes de code
     */
    protected int mLoc;

    /**
     * @return le nombre de lignes de codes
     */
    public int getLoc()
    {
        return mLoc;
    }

    /**
     * @return le nombre de projets
     */
    public int getNbProjects()
    {
        return mNbProjects;
    }

    /**
     * @param pLoc le nombre de lignes de code
     */
    public void setLoc( int pLoc )
    {
        mLoc = pLoc;
    }

    /**
     * @param pNbProjects le nombre de projets
     */
    public void setNbProjects( int pNbProjects )
    {
        mNbProjects = pNbProjects;
    }

}
