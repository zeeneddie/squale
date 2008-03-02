package com.airfrance.squaleweb.applicationlayer.action.rulechecking;

/**
 * Action pour permettre à squale de parser le fichier de configuration checkstyle
 * 
 * @author henix
 */
public class CheckstyleAddRulesetAction
    extends AbstractAddRuleSetAction
{

    /**
     * @see com.airfrance.squaleweb.applicationlayer.action.rulechecking.AbstractAddRuleSetAction#getAccessComponentName()
     * @return le nom utilisé
     */
    protected String getAccessComponentName()
    {
        return "CheckstyleAdmin";
    }
}
