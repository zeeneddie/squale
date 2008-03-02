package com.airfrance.squaleweb.applicationlayer.action.rulechecking;

/**
 * Action d'ajout de ruleset dans CppTest
 */
public class CppTestAddRuleSetAction
    extends AbstractAddRuleSetAction
{

    /**
     * @see com.airfrance.squaleweb.applicationlayer.action.rulechecking.AbstractAddRuleSetAction#getAccessComponentName()
     *      {@inheritDoc}
     */
    protected String getAccessComponentName()
    {
        return "CppTestAdmin";
    }
}
