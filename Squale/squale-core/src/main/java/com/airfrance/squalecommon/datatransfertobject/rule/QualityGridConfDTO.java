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
package com.airfrance.squalecommon.datatransfertobject.rule;

import java.io.Serializable;
import java.util.Date;

/**
 * Grille qualité Toutes les informations de la grille sont disponibles
 */
public class QualityGridConfDTO
    extends QualityGridDTO
    implements Serializable
{
    /** Date de mise à jour */
    private Date mDateOfUpdate;

    /**
     * @return date de mise à jour
     */
    public Date getDateOfUpdate()
    {
        return mDateOfUpdate;
    }

    /**
     * @param pDate date de mise à jour
     */
    public void setDateOfUpdate( Date pDate )
    {
        mDateOfUpdate = pDate;
    }

}
