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
package com.airfrance.squaleweb.applicationlayer.formbean.stats;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 */
public class AuditsStatsForm
    extends RootForm
{

    /**
     * Le nombre d'audits partiels
     */
    private int mNbPartial;

    /**
     * Le nombre total d'audits
     */
    private int mNbTotal;

    /**
     * Le nombre d'audits failed
     */
    private int mNbFailed;

    /**
     * Le nombre d'audits réussis
     */
    private int mNbSuccessfuls;

    /**
     * Le nombre d'audits programmés
     */
    private int mNbNotAttempted;

    /**
     * @return Le nombre d'audits failed
     */
    public int getNbFailed()
    {
        return mNbFailed;
    }

    /**
     * @return Le nombre d'audits partiels
     */
    public int getNbPartial()
    {
        return mNbPartial;
    }

    /**
     * @return Le nombre d'audits programmés
     */
    public int getNbNotAttempted()
    {
        return mNbNotAttempted;
    }

    /**
     * @return Le nombre d'audits réussis
     */
    public int getNbSuccessfuls()
    {
        return mNbSuccessfuls;
    }

    /**
     * @return Le nombre total d'audits
     */
    public int getNbTotal()
    {
        return mNbTotal;
    }

    /**
     * @param pNbFailed le nouveau nombre d'audits en échec
     */
    public void setNbFailed( int pNbFailed )
    {
        mNbFailed = pNbFailed;
    }

    /**
     * @param pNbNotAttempted le nouveau nombre d'audits programmés
     */
    public void setNbNotAttempted( int pNbNotAttempted )
    {
        mNbNotAttempted = pNbNotAttempted;
    }

    /**
     * @param pNbSuccessfuls le nouveau nombre d'audits réussis
     */
    public void setNbSuccessfuls( int pNbSuccessfuls )
    {
        mNbSuccessfuls = pNbSuccessfuls;
    }

    /**
     * @param pNbTotal le nouveau nombre d'audits en échec
     */
    public void setNbTotal( int pNbTotal )
    {
        mNbTotal = pNbTotal;
    }

    /**
     * @param pNbPartial le nouveau nombre d'audits partiels
     */
    public void setNbPartial( int pNbPartial )
    {
        mNbPartial = pNbPartial;
    }

}
