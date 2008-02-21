package com.airfrance.squalecommon.datatransfertobject.transform.rule;

import java.util.List;

import com.airfrance.squalecommon.datatransfertobject.rule.AbstractFormulaDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.ConditionFormulaDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.FormulaDTOVisitor;
import com.airfrance.squalecommon.datatransfertobject.rule.SimpleFormulaDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.AbstractFormulaBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.ConditionFormulaBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FormulaVisitor;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.SimpleFormulaBO;

/**
 * Transformation d'une règle qualité
 */
public class AbstractFormulaTransform implements FormulaVisitor, FormulaDTOVisitor {
    /**
     * Conversion d'une formule
     * @param pFormula formule
     * @return formule
     */
    public static AbstractFormulaDTO bo2Dto(AbstractFormulaBO pFormula) {
        AbstractFormulaDTO result = (AbstractFormulaDTO) pFormula.accept(new AbstractFormulaTransform(),null);
        result.setId(pFormula.getId());
        result.setType(pFormula.getType());
        return result;
    }

    /** (non-Javadoc)
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FormulaVisitor#visit(com.airfrance.squalecommon.enterpriselayer.businessobject.rule.ConditionFormulaBO, java.lang.Object)
     */
    public Object visit(ConditionFormulaBO pConditionFormula, Object pArgument) {
        ConditionFormulaDTO dto = new ConditionFormulaDTO();
        setAttributes(pConditionFormula, dto);
        dto.setMarkConditions(pConditionFormula.getMarkConditions());
        return dto;
    }

    /** (non-Javadoc)
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FormulaVisitor#visit(com.airfrance.squalecommon.enterpriselayer.businessobject.rule.SimpleFormulaBO, java.lang.Object)
     */
    public Object visit(SimpleFormulaBO pSimpleFormula, Object pArgument) {
        SimpleFormulaDTO dto = new SimpleFormulaDTO();
        setAttributes(pSimpleFormula, dto);
        dto.setFormula(pSimpleFormula.getFormula());
        return dto;
    }

    /**
     * 
     * @param pFormulaBO formule
     * @param pFormulaDTO formule
     */
    private void setAttributes(AbstractFormulaBO pFormulaBO, AbstractFormulaDTO pFormulaDTO) {
        pFormulaDTO.setComponentLevel(pFormulaBO.getComponentLevel());
        pFormulaDTO.setTriggerCondition(pFormulaBO.getTriggerCondition());
        pFormulaDTO.setMeasureKinds(pFormulaBO.getMeasureKinds());
    }

    /**
     * @param pFormulaDTO la formule à transformer
     * @return la formule sous forme BO
     */
    public static AbstractFormulaBO dto2Bo(AbstractFormulaDTO pFormulaDTO) {
        AbstractFormulaBO result = (AbstractFormulaBO) pFormulaDTO.accept(new AbstractFormulaTransform());
        result.setId(pFormulaDTO.getId());
        return result;
    }

    /**
     * @see com.airfrance.squalecommon.datatransfertobject.rule.FormulaDTOVisitor#visit(com.airfrance.squalecommon.datatransfertobject.rule.ConditionFormulaDTO)
     */
    public Object visit(ConditionFormulaDTO pConditionFormula) {
        ConditionFormulaBO bo = new ConditionFormulaBO();
        setAttributes(pConditionFormula, bo);
        bo.setMarkConditions((List)pConditionFormula.getMarkConditions());
        return bo;
    }

    /**
     * @see com.airfrance.squalecommon.datatransfertobject.rule.FormulaDTOVisitor#visit(com.airfrance.squalecommon.datatransfertobject.rule.SimpleFormulaDTO)
     */
    public Object visit(SimpleFormulaDTO pSimpleFormula) {
        SimpleFormulaBO bo = new SimpleFormulaBO();
        setAttributes(pSimpleFormula, bo);
        bo.setFormula(pSimpleFormula.getFormula());
        return bo;
    }

    /**
     * 
     * @param pFormulaBO formule
     * @param pFormulaDTO formule
     */
    private void setAttributes(AbstractFormulaDTO pFormulaDTO, AbstractFormulaBO pFormulaBO) {
        pFormulaBO.setComponentLevel(pFormulaDTO.getComponentLevel());
        pFormulaBO.setTriggerCondition(pFormulaDTO.getTriggerCondition());
        pFormulaBO.setMeasureKinds(pFormulaDTO.getMeasureKinds());
    }
}
