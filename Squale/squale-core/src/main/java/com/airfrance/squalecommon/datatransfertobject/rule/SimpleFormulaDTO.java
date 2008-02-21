package com.airfrance.squalecommon.datatransfertobject.rule;

/**
 * DTO d'une formule simple
 */
public class SimpleFormulaDTO extends AbstractFormulaDTO {
    
    
    
    /** Formule de calcul */
    private String mFormula;
    /**
     * @return formule
     */
    public String getFormula() {
        return mFormula;
    }
    /**
     * @param pFormula formule
     */
    public void setFormula(String pFormula) {
        mFormula = pFormula;
    }
    
    /**
     * 
     * @param pVisitor visiteur
     * @return objet
     */
    public Object accept(FormulaDTOVisitor pVisitor) {
        return pVisitor.visit(this);
    }

}
