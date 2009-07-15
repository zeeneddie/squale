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

import java.util.Collection;
import java.util.Iterator;

import org.squale.squalecommon.enterpriselayer.businessobject.rule.AbstractFormulaBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO;

/**
 * Extracteur des mesures utilisées par une formule Une formule de règle qualité est exprimée à l'aide de mesures qui
 * sont utilisées pour la construction du menu top ou du détail d'une pratique pour afficher les valeurs des mesures
 * liées à cette pratique
 */
public class FormulaMeasureExtractor
    implements PracticeRuleBO.MeasureExtractor
{

    /**
     * (non-Javadoc)
     * 
     * @see org.squale.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO.MeasureExtractor#getUsedMeasures(org.squale.squalecommon.enterpriselayer.businessobject.rule.AbstractFormulaBO)
     */
    public String[] getUsedMeasures( AbstractFormulaBO pFormula )
    {
        String[] mUsedMeasures;
        ParameterExtraction extracter = new ParameterExtraction();
        Collection params = extracter.getFormulaParameters( pFormula );
        mUsedMeasures = new String[params.size()];
        Iterator paramsIt = params.iterator();
        int i = 0;
        while ( paramsIt.hasNext() )
        {
            FormulaParameter param = (FormulaParameter) paramsIt.next();
            mUsedMeasures[i] =
                param.getMeasureKind() + "." + pFormula.getComponentLevel() + "." + param.getMeasureAttribute();
            i++;
        }
        return mUsedMeasures;
    }

}
