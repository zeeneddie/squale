package com.airfrance.squalecommon.datatransfertobject.rule;

import java.util.ArrayList;
import java.util.Collection;

/**
 * DTO d'une formule avec conditions
 */
public class ConditionFormulaDTO extends AbstractFormulaDTO {
    /** Condition associée à chaque note */
    private Collection mMarkConditions = new ArrayList();
    
    /**
     * @return conditions
     */
    public Collection getMarkConditions() {
        return mMarkConditions;
    }
    /**
     * @param pStrings conditions
     */
    public void setMarkConditions(Collection pStrings) {
        mMarkConditions = pStrings;
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
