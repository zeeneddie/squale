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
package com.airfrance.squalecommon.datatransfertobject.transform.rule;

import com.airfrance.squalecommon.datatransfertobject.rule.FactorRuleDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO;

/**
 * Transformation d'un facteur
 */
public class FactorRuleTransform
{
    /**
     * Conversion
     * 
     * @param pFactorRule objet à convertir
     * @return résultat de la conversion
     */
    public static FactorRuleDTO bo2Dto( FactorRuleBO pFactorRule )
    {
        FactorRuleDTO result = new FactorRuleDTO();
        bo2Dto( result, pFactorRule );
        return result;
    }

    /**
     * Conversion
     * 
     * @param pFactorRule objet à convertir
     * @param pDto objet converti
     */
    public static void bo2Dto( FactorRuleDTO pDto, FactorRuleBO pFactorRule )
    {
        QualityRuleTransform.bo2Dto( pDto, pFactorRule );
    }

}
