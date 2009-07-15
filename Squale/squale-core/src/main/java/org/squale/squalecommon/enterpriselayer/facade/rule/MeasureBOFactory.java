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
package org.squale.squalecommon.enterpriselayer.facade.rule;

import org.squale.squalecommon.enterpriselayer.businessobject.result.MeasureBO;
import org.squale.squalecommon.util.mapping.Mapping;

/**
 * Fabrique de mesure
 */
public class MeasureBOFactory
{

    /**
     * Création d'une mesure
     * 
     * @param pComponentKind type de composant
     * @param pMeasureKind type de mesure
     * @return mesure correspondante ou null s'il n'existe pas une telle mesure
     */
    public MeasureBO createMeasure( String pComponentKind, String pMeasureKind )
    {
        String mes = pMeasureKind + "." + pComponentKind;

        MeasureBO result;
        try
        {
            result = (MeasureBO) Mapping.getMeasureClass( mes ).newInstance();
        }
        catch ( Throwable e )
        {
            // measure inexistante, on renvoie null qui
            // sera traité par checkParameters
            result = null;
        }
        return result;
    }
}
