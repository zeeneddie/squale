package com.airfrance.squaleweb.applicationlayer.formbean.results;

/**
 * Bean représentant les transgressions pour l'export PDF
 */
public class RuleCheckingPDFForm extends RulesCheckingForm {

    /** Détails de la règles */
    private RuleCheckingItemsListForm mDetails;
    
    /**
     * Constructeur par défaut
     */
    public RuleCheckingPDFForm() {
        this("", "", 0, new RuleCheckingItemsListForm());
    }
    
    /**
     * @param pNameRule le nom de la règle
     * @param pSeverity la sévérité de la règle
     * @param pTransgressionsNumber le nombre de transgressions
     * @param pItemsForm les détails de la règles
     */
    public RuleCheckingPDFForm(String pNameRule, String pSeverity, int pTransgressionsNumber, RuleCheckingItemsListForm pItemsForm) {
        mNameRule = pNameRule;
        mSeverity = pSeverity;
        mTransgressionsNumber = pTransgressionsNumber;
        mDetails = pItemsForm;
    }

    /**
     * @return les détails de la règles
     */
    public RuleCheckingItemsListForm getDetails() {
        return mDetails;
    }

    /**
     * @param pItems les détails de la règles
     */
    public void setDetails(RuleCheckingItemsListForm pItems) {
        mDetails = pItems;
    }

}
