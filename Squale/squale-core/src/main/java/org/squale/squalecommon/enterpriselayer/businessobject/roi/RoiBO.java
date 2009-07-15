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
package org.squale.squalecommon.enterpriselayer.businessobject.roi;

import java.util.Date;

import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;

/**
 * Représente un retour sur investissement
 */
public class RoiBO
{

    /** L'application concerné par le ROI */
    private ApplicationBO mApplication;

    /** Date de calcul du ROI */
    private Date mDate;

    /**
     * @return l'application
     */
    public ApplicationBO getApplication()
    {
        return mApplication;
    }

    /**
     * @return la date de calcul du ROI
     */
    public Date getDate()
    {
        return mDate;
    }

    /**
     * @param pApplicationBO l'application
     */
    public void setApplication( ApplicationBO pApplicationBO )
    {
        mApplication = pApplicationBO;
    }

    /**
     * @param pDate la date de calcul du ROI
     */
    public void setDate( Date pDate )
    {
        mDate = pDate;
    }

}
