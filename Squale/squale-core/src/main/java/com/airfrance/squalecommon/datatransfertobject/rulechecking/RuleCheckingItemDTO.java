package com.airfrance.squalecommon.datatransfertobject.rulechecking;

import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;

/**
 * DTO pour les items des transgressions
 */
public class RuleCheckingItemDTO {
    
    /** Détail de la transgression */
    private String mMessage;
    
    /** Composant concerné par la transgression */
    private ComponentDTO mComponent;
    
    /** Composant en relation avec la transgression */
    private ComponentDTO mComponentInvolved;
    
    /** Sévérité de la règle */
    private String mRuleSeverity;
    
    /** Code de la règle */
    private String mRuleCode;
    
    /** Chemin du fichier conserné */
    private String mComponentFile;
    
    /** Numéro de la ligne */
    private int mLine;

    /**
     * @return le détail de la transgession
     */
    public String getMessage() {
        return mMessage;
    }
    
    /**
     * @return le composant concerné par la transgression
     */
    public ComponentDTO getComponent() {
        return mComponent;
    }

    /**
     * @return le composant en relation avec la transgression
     */
    public ComponentDTO getComponentInvolved() {
        return mComponentInvolved;
    }

    /**
     * Modifie mMessage
     * 
     * @param pMessage le détail de la transgession
     */
    public void setMessage(String pMessage) {
        mMessage = pMessage;
    }

    /**
     * Modifie mComponent
     * 
     * @param pComponent le composant concerné par la transgression
     */
    public void setComponent(ComponentDTO pComponent) {
        mComponent = pComponent;
    }

    /**
     * Modifie mComponentInvolved
     * 
     * @param pComponentInvolved le composant en relation avec la transgression
     */
    public void setComponentInvolved(ComponentDTO pComponentInvolved) {
        mComponentInvolved = pComponentInvolved;
    }
    /**
     * @return le code de la règle
     */
    public String getRuleCode() {
        return mRuleCode;
    }

    /**
     * @return la sévérité de la règle
     */
    public String getRuleSeverity() {
        return mRuleSeverity;
    }

    /**
     * @param pRuleCode la code de la règle
     */
    public void setRuleCode(String pRuleCode) {
        mRuleCode = pRuleCode;
    }

    /**
     * @param pRuleSeverity la sévérité de la règle
     */
    public void setRuleSeverity(String pRuleSeverity) {
        mRuleSeverity = pRuleSeverity;
    }

    /**
     * @return le chemin du fichier
     */
    public String getComponentFile() {
        return mComponentFile;
    }

    /**
     * @return le numéro de ligne
     */
    public int getLine() {
        return mLine;
    }

    /**
     * @param pComponentFile le chemin du fichier
     */
    public void setComponentFile(String pComponentFile) {
        mComponentFile = pComponentFile;
    }

    /**
     * @param pLine le numéro de ligne
     */
    public void setLine(int pLine) {
        mLine = pLine;
    }
}

