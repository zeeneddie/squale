package com.airfrance.squalecommon.datatransfertobject.rule;

/**
 * DTO d'une pratique qualité
 */
public class PracticeRuleDTO extends QualityRuleDTO {
    
    /** Indication d'une pratique de type violation de règle */
    private boolean mRuleChecking = false;
    /** Formule */
    private AbstractFormulaDTO mFormula;
    /** La fonction de pondération */
    private String mWeightingFunction;
    /** parfois on a seulement besoin du type de la formule (graph de répartition) */
    private String mFormulaType;
    /** l'effort à fournie pour la correction */
    private int mEffort;
    
    
    
    /**
     * @return formule
     */
    public AbstractFormulaDTO getFormula() {
        return mFormula;
    }

    /**
     * @param pFormulaDTO formule
     */
    public void setFormula(AbstractFormulaDTO pFormulaDTO) {
        mFormula = pFormulaDTO;
    }

    /**
     * @return indicateur de rulechecking
     */
    public boolean isRuleChecking() {
        return mRuleChecking;
    }

    /**
     * @param pRuleChecking indicateur de règle
     */
    public void setRuleChecking(boolean pRuleChecking) {
        mRuleChecking = pRuleChecking;
    }

    /**
     * @return la fonction de pondération
     */
    public String getWeightingFunction() {
        return mWeightingFunction;
    }

    /**
     * @param pWeightingFormula la fonction de pondération
     */
    public void setWeightingFunction(String pWeightingFormula) {
        mWeightingFunction = pWeightingFormula;
    }

    /**
     * @return le type de la formule
     */
    public String getFormulaType() {
        return mFormulaType;
    }

    /**
     * @param pFormulaType le type de la formule
     */
    public void setFormulaType(String pFormulaType) {
        mFormulaType = pFormulaType;
    }

    /**
     * @return l'effort de correction
     */
    public int getEffort() {
        return mEffort;
    }

    /**
     * @param pEffort l'effort de correction
     */
    public void setEffort(int pEffort) {
        mEffort = pEffort;
    }

}
