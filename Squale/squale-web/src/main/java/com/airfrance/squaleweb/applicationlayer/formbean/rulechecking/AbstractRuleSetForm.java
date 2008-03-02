package com.airfrance.squaleweb.applicationlayer.formbean.rulechecking;

import java.util.Date;

import com.airfrance.squaleweb.applicationlayer.formbean.ActionIdFormSelectable;

/**
 * Formulaire d'un ruleset
 */
public class AbstractRuleSetForm
    extends ActionIdFormSelectable
{
    /** Date de mise à jour */
    private Date mDateOfUpdate;

    /**
     * Nom du RuleSet
     */
    private String mName = "";

    /**
     * @return date de mise à jour
     */
    public Date getDateOfUpdate()
    {
        return mDateOfUpdate;
    }

    /**
     * @return le nom
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @param pDateOfUpdate date de mise à jour
     */
    public void setDateOfUpdate( Date pDateOfUpdate )
    {
        mDateOfUpdate = pDateOfUpdate;

    }

    /**
     * @param pName le nom
     */
    public void setName( String pName )
    {
        mName = pName;
    }

}
