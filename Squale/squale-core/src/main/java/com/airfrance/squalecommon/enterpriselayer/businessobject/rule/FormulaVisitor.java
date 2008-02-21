package com.airfrance.squalecommon.enterpriselayer.businessobject.rule;

/**
 * Visiteur de formule
 * Ce design pattern permet d'externaliser des traitements
 * sur les formules
 */
public interface FormulaVisitor {
    /**
     * 
     * @param pConditionFormula formule
     * @param pArgument argument
     * @return objet
     */
    public Object visit(ConditionFormulaBO pConditionFormula, Object pArgument);
    
    /**
     * 
     * @param pSimpleFormula formule
     * @param pArgument argument
     * @return objet
     */
    public Object visit(SimpleFormulaBO pSimpleFormula, Object pArgument);
    
}
