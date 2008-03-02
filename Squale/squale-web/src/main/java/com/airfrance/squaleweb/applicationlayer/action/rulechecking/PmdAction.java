package com.airfrance.squaleweb.applicationlayer.action.rulechecking;

import com.airfrance.squaleweb.transformer.rulechecking.PmdRuleSetListTransformer;

/**
 * Actions de configuration PMD
 */
public class PmdAction
    extends AbstractRuleSetAction
{

    /**
     * @see com.airfrance.squaleweb.applicationlayer.action.rulechecking.AbstractRuleSetAction#getRuleSetListTransformer()
     *      {@inheritDoc}
     */
    protected Class getRuleSetListTransformer()
    {
        return PmdRuleSetListTransformer.class;
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.action.rulechecking.AbstractRuleSetAction#getAccessComponentName()
     *      {@inheritDoc}
     */
    protected String getAccessComponentName()
    {
        return "PmdAdmin";
    }
}
