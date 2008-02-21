package com.airfrance.squaleweb.applicationlayer.formbean.component;


/**
 * Données synthétiques d'une formule
 */
public class PracticeRuleForm extends QualityRuleForm {
    
    /** L'id de l'objet */
    private long mId;
    /** Formule */
    private FormulaForm mFormula;
    /** La fonction de pondération associée */
    private String mWeightingFunction;
    /** L'effort nécessaire à la correction */
    private int mEffort = 1;
    
    /**
     * @return id
     */
    public long getId() {
        return mId;
    }

    /**
     * @param pId id
     */
    public void setId(long pId) {
        mId = pId;
    }
    /**
     * @return formule
     */
    public FormulaForm getFormula() {
        return mFormula;
    }

    /**
     * @param pForm formule
     */
    public void setFormula(FormulaForm pForm) {
        mFormula = pForm;
    }

    /**
     * @return la fonction de pondération associée
     */
    public String getWeightingFunction() {
        return mWeightingFunction;
    }

    /**
     * @param pWeightingFunction la fonction de pondération associée
     */
    public void setWeightingFunction(String pWeightingFunction) {
        mWeightingFunction = pWeightingFunction;
    }

    /**
     * @return l'effort
     */
    public int getEffort() {
        return mEffort;
    }

    /**
     * @param pEffort l'effort
     */
    public void setEffort(int pEffort) {
        mEffort = pEffort;
    }

}
