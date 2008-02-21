package com.airfrance.squaleweb.applicationlayer.action.rulechecking;

/**
 * Action d'ajout de ruleset PMD
 */
public class PmdAddRuleSetAction extends AbstractAddRuleSetAction {

    /** 
     * @see com.airfrance.squaleweb.applicationlayer.action.rulechecking.AbstractAddRuleSetAction#getAccessComponentName()
     * {@inheritDoc}
     */
    protected String getAccessComponentName() {
        return "PmdAdmin";
    }

}
