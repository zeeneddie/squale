package com.airfrance.squaleweb.applicationlayer.action.rulechecking;

import com.airfrance.squaleweb.transformer.rulechecking.CheckStyleRuleSetListTransformer;

/**
 * Action pour gerer les différents fichiers de configuration checkstyle
 * @author henix
 *
 */
public class CheckstyleAction extends AbstractRuleSetAction {


    /** 
     * @see com.airfrance.squaleweb.applicationlayer.action.rulechecking.AbstractRuleSetAction#getRuleSetListTransformer()
     * 
     * @return le transformer à utiliser
     */
    protected Class getRuleSetListTransformer() {
        return CheckStyleRuleSetListTransformer.class;
    }

    /** 
     * @see com.airfrance.squaleweb.applicationlayer.action.rulechecking.AbstractRuleSetAction#getAccessComponentName()
     * 
     * @return le nom utilisé
     */
    protected String getAccessComponentName() {
        return "CheckstyleAdmin";
    }
}
