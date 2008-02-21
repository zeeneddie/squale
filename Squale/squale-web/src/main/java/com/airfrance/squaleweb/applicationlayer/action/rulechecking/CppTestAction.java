package com.airfrance.squaleweb.applicationlayer.action.rulechecking;

import com.airfrance.squaleweb.transformer.rulechecking.CppTestRuleSetListTransformer;

/**
 * Action sur les messages
 */
public class CppTestAction extends AbstractRuleSetAction {


    /** 
     * @see com.airfrance.squaleweb.applicationlayer.action.rulechecking.AbstractRuleSetAction#getRuleSetListTransformer()
     * 
     * @return le transformer à utiliser
     */
    protected Class getRuleSetListTransformer() {
        return CppTestRuleSetListTransformer.class;
    }

    /** 
     * @see com.airfrance.squaleweb.applicationlayer.action.rulechecking.AbstractRuleSetAction#getAccessComponentName()
     * 
     * @return le nom utilisé
     */
    protected String getAccessComponentName() {
        return "CppTestAdmin";
    }
}
