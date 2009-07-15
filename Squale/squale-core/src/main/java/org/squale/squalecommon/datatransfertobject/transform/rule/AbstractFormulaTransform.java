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

import java.util.List;

import org.squale.squalecommon.datatransfertobject.rule.AbstractFormulaDTO;
import org.squale.squalecommon.datatransfertobject.rule.ConditionFormulaDTO;
import org.squale.squalecommon.datatransfertobject.rule.FormulaDTOVisitor;
import org.squale.squalecommon.datatransfertobject.rule.SimpleFormulaDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.AbstractFormulaBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.ConditionFormulaBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.FormulaVisitor;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.SimpleFormulaBO;

/**
 * Transformation d'une règle qualité
 */
public class AbstractFormulaTransform
    implements FormulaVisitor, FormulaDTOVisitor
{
    /**
     * Conversion d'une formule
     * 
     * @param pFormula formule
     * @return formule
     */
    public static AbstractFormulaDTO bo2Dto( AbstractFormulaBO pFormula )
    {
        AbstractFormulaDTO result = (AbstractFormulaDTO) pFormula.accept( new AbstractFormulaTransform(), null );
        result.setId( pFormula.getId() );
        result.setType( pFormula.getType() );
        return result;
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.squale.squalecommon.enterpriselayer.businessobject.rule.FormulaVisitor#visit(org.squale.squalecommon.enterpriselayer.businessobject.rule.ConditionFormulaBO,
     *      java.lang.Object)
     */
    public Object visit( ConditionFormulaBO pConditionFormula, Object pArgument )
    {
        ConditionFormulaDTO dto = new ConditionFormulaDTO();
        setAttributes( pConditionFormula, dto );
        dto.setMarkConditions( pConditionFormula.getMarkConditions() );
        return dto;
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.squale.squalecommon.enterpriselayer.businessobject.rule.FormulaVisitor#visit(org.squale.squalecommon.enterpriselayer.businessobject.rule.SimpleFormulaBO,
     *      java.lang.Object)
     */
    public Object visit( SimpleFormulaBO pSimpleFormula, Object pArgument )
    {
        SimpleFormulaDTO dto = new SimpleFormulaDTO();
        setAttributes( pSimpleFormula, dto );
        dto.setFormula( pSimpleFormula.getFormula() );
        return dto;
    }

    /**
     * @param pFormulaBO formule
     * @param pFormulaDTO formule
     */
    private void setAttributes( AbstractFormulaBO pFormulaBO, AbstractFormulaDTO pFormulaDTO )
    {
        pFormulaDTO.setComponentLevel( pFormulaBO.getComponentLevel() );
        pFormulaDTO.setTriggerCondition( pFormulaBO.getTriggerCondition() );
        pFormulaDTO.setMeasureKinds( pFormulaBO.getMeasureKinds() );
    }

    /**
     * @param pFormulaDTO la formule à transformer
     * @return la formule sous forme BO
     */
    public static AbstractFormulaBO dto2Bo( AbstractFormulaDTO pFormulaDTO )
    {
        AbstractFormulaBO result = (AbstractFormulaBO) pFormulaDTO.accept( new AbstractFormulaTransform() );
        result.setId( pFormulaDTO.getId() );
        return result;
    }

    /**
     * @see org.squale.squalecommon.datatransfertobject.rule.FormulaDTOVisitor#visit(org.squale.squalecommon.datatransfertobject.rule.ConditionFormulaDTO)
     */
    public Object visit( ConditionFormulaDTO pConditionFormula )
    {
        ConditionFormulaBO bo = new ConditionFormulaBO();
        setAttributes( pConditionFormula, bo );
        bo.setMarkConditions( (List) pConditionFormula.getMarkConditions() );
        return bo;
    }

    /**
     * @see org.squale.squalecommon.datatransfertobject.rule.FormulaDTOVisitor#visit(org.squale.squalecommon.datatransfertobject.rule.SimpleFormulaDTO)
     */
    public Object visit( SimpleFormulaDTO pSimpleFormula )
    {
        SimpleFormulaBO bo = new SimpleFormulaBO();
        setAttributes( pSimpleFormula, bo );
        bo.setFormula( pSimpleFormula.getFormula() );
        return bo;
    }

    /**
     * @param pFormulaBO formule
     * @param pFormulaDTO formule
     */
    private void setAttributes( AbstractFormulaDTO pFormulaDTO, AbstractFormulaBO pFormulaBO )
    {
        pFormulaBO.setComponentLevel( pFormulaDTO.getComponentLevel() );
        pFormulaBO.setTriggerCondition( pFormulaDTO.getTriggerCondition() );
        pFormulaBO.setMeasureKinds( pFormulaDTO.getMeasureKinds() );
    }
}
