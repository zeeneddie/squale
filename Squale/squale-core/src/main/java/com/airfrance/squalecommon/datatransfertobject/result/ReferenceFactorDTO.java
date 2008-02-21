package com.airfrance.squalecommon.datatransfertobject.result;

import java.io.Serializable;

import com.airfrance.squalecommon.datatransfertobject.rule.FactorRuleDTO;

/**
 * Facteur d'une référence
 */
public class ReferenceFactorDTO extends FactorRuleDTO implements Serializable {
    /** Valeur associée */
    private Float mValue;
    
    /**
     * @return valeur
     */
    public Float getValue() {
        return mValue;
    }

    /**
     * @param pFloat valeur
     */
    public void setValue(Float pFloat) {
        mValue = pFloat;
    }

}
