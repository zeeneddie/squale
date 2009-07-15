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
public class FactorsStatsDTO
{

    /** le nombre de facteurs acceptés */
    private int mNbFactorsAccepted;

    /** le nombre de facteurs acceptés avec réserves */
    private int mNbFactorsReserved;

    /** le nombre de facteurs refusés */
    private int mNbFactorsRefused;

    /**
     * Total
     */
    private int mNbTotal;

    /**
     * @return le nombre de facteurs acceptés
     */
    public int getNbFactorsAccepted()
    {
        return mNbFactorsAccepted;
    }

    /**
     * @return le nombre de facteurs refusés
     */
    public int getNbFactorsRefused()
    {
        return mNbFactorsRefused;
    }

    /**
     * @return le nombre de facteurs acceptés avec réserves
     */
    public int getNbFactorsReserved()
    {
        return mNbFactorsReserved;
    }

    /**
     * @param pNbAccepted le nombre de facteurs acceptés
     */
    public void setNbFactorsAccepted( int pNbAccepted )
    {
        mNbFactorsAccepted = pNbAccepted;
    }

    /**
     * @param pNbRefused le nombre de facteurs refusés
     */
    public void setNbFactorsRefused( int pNbRefused )
    {
        mNbFactorsRefused = pNbRefused;
    }

    /**
     * @param pReserved le nombre de facteurs réservés
     */
    public void setNbFactorsReserved( int pReserved )
    {
        mNbFactorsReserved = pReserved;
    }

    /**
     * @return le total
     */
    public int getNbTotal()
    {
        return mNbTotal;
    }

    /**
     * @param pTotal le total
     */
    public void setNbTotal( int pTotal )
    {
        mNbTotal = pTotal;
    }

}
