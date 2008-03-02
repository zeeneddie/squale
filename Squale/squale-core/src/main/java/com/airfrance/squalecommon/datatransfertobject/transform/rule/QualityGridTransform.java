package com.airfrance.squalecommon.datatransfertobject.transform.rule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;

import com.airfrance.squalecommon.datatransfertobject.rule.QualityGridConfDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.QualityGridDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;

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
