package com.airfrance.squaleweb.applicationlayer.formbean.component;



/**
 * Données synthétiques d'un facteur
 */
public class CriteriumRuleForm extends QualityRuleForm {
    
    /** L'id de l'objet */
    private long mId;
    
    /** Pratiques */
    private PracticeListForm mPractices = new PracticeListForm();
    
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
     * @return pratiques
     */
    public PracticeListForm getPractices() {
        return mPractices;
    }

    /**
     * Ajout d'une pratique
     * @param pPractice pratique
     */
    public void addPractice(PracticeRuleForm pPractice) {
        mPractices.getList().add(pPractice);
    }
    /**
     * @param pPractices les pratiques
     */
    public void setPractices(PracticeListForm pPractices) {
        mPractices = pPractices;
    }
}
