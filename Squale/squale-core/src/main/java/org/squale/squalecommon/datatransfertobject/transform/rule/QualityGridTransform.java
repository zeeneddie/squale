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
package org.squale.squalecommon.datatransfertobject.transform.rule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;

import org.squale.squalecommon.datatransfertobject.rule.QualityGridConfDTO;
import org.squale.squalecommon.datatransfertobject.rule.QualityGridDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;

/**
 * Transformation d'une grille qualité
 */
public class QualityGridTransform
{
    /**
     * Conversion
     * 
     * @param pQualityGridDTO objet à convertir
     * @return résultat de la conversion
     */
    public static QualityGridBO dto2Bo( QualityGridDTO pQualityGridDTO )
    {
        QualityGridBO result = new QualityGridBO();
        result.setId( pQualityGridDTO.getId() );
        result.setName( pQualityGridDTO.getName() );
        return result;
    }

    /**
     * Conversion
     * 
     * @param pQualityGrid objet à convertir
     * @return résultat de la conversion
     */
    public static QualityGridDTO bo2Dto( QualityGridBO pQualityGrid )
    {
        QualityGridDTO result = new QualityGridDTO();
        result.setId( pQualityGrid.getId() );
        result.setUpdateDate( pQualityGrid.getDateOfUpdate() );
        result.setName( pQualityGrid.getName() );
        SortedSet factorsC = pQualityGrid.getFactors();
        Iterator factors = pQualityGrid.getFactors().iterator();
        ArrayList factorsDTO = new ArrayList();
        while ( factors.hasNext() )
        {
            factorsDTO.add( FactorRuleTransform.bo2Dto( (FactorRuleBO) factors.next() ) );
        }
        result.setFactors( factorsDTO );
        return result;
    }

    /**
     * Conversion for name and date
     * 
     * @param pQualityGrid grid to convert
     * @return result of conversion
     */
    public static QualityGridDTO bo2DtoLight( QualityGridBO pQualityGrid )
    {
        QualityGridDTO result = new QualityGridDTO();
        result.setUpdateDate( pQualityGrid.getDateOfUpdate() );
        result.setName( pQualityGrid.getName() );
        return result;
    }

    /**
     * Conversion
     * 
     * @param pQualityGrid grille à convertir
     * @param result conversion
     */
    public static void bo2Dto( QualityGridBO pQualityGrid, QualityGridConfDTO result )
    {
        result.setId( pQualityGrid.getId() );
        result.setName( pQualityGrid.getName() );
        result.setUpdateDate( pQualityGrid.getDateOfUpdate() );
        Iterator factors = pQualityGrid.getFactors().iterator();
        ArrayList factorsDTO = new ArrayList();
        while ( factors.hasNext() )
        {
            factorsDTO.add( QualityRuleTransform.bo2Dto( (FactorRuleBO) factors.next(), true ) );
        }
        result.setDateOfUpdate( pQualityGrid.getDateOfUpdate() );
        result.setFactors( factorsDTO );
    }
}
