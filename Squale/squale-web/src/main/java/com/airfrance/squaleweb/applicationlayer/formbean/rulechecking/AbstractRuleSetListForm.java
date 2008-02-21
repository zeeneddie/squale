package com.airfrance.squaleweb.applicationlayer.formbean.rulechecking;

import java.util.List;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Formulaire d'une liste de RuleSet
 */
public abstract class AbstractRuleSetListForm extends RootForm {

    
    /** Jeux de règles disponibles */
       private List mRuleSets;
       /**
        * @return rulesets
        */
       public List getRuleSets() {
           return mRuleSets;
       }

       /**
        * @param pRuleSets rulesets
        */
       public void setRuleSets(List pRuleSets) {
           mRuleSets = pRuleSets;
       }
}
