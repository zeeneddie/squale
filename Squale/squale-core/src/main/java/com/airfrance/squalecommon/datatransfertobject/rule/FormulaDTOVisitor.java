package com.airfrance.squalecommon.datatransfertobject.rule;

/**
 * Visiteur de formule
 * Ce design pattern permet d'externaliser des traitements
 * sur les formules
 */
public interface FormulaDTOVisitor {
    /**
     * 
     * @param pConditionFormula formule
     * @return objet
     */
    public Object visit(ConditionFormulaDTO pConditionFormula);
    
    /**
     * 
     * @param pSimpleFormula formule
     * @return objet
     */
    public Object visit(SimpleFormulaDTO pSimpleFormula);
    
}
