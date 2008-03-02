package com.airfrance.squaleweb.applicationlayer.formbean.component;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Données synthétiques d'un facteur
 */
public class FormulaForm
    extends RootForm
{
    /** l'id */
    private long mId;

    /** Conditions de trigger */
    private String mTriggerCondition;

    /** Mesures */
    private String mMeasures;

    /** Niveau de composant */
    private String mComponentLevel;

    /** Conditions */
    private String[] mConditions;

    /**
     * @return componentn level
     */
    public String getComponentLevel()
    {
        return mComponentLevel;
    }

    /**
     * @return conditions
     */
    public String[] getConditions()
    {
        return mConditions;
    }

    /**
     * @return mesures
     */
    public String getMeasures()
    {
        return mMeasures;
    }

    /**
     * @return rigger
     */
    public String getTriggerCondition()
    {
        return mTriggerCondition;
    }

    /**
     * @param pString component level
     */
    public void setComponentLevel( String pString )
    {
        mComponentLevel = pString;
    }

    /**
     * @param pStrings conditions
     */
    public void setConditions( String[] pStrings )
    {
        mConditions = pStrings;
    }

    /**
     * @param pString mesures
     */
    public void setMeasures( String pString )
    {
        mMeasures = pString;
    }

    /**
     * @param pString trigger
     */
    public void setTriggerCondition( String pString )
    {
        mTriggerCondition = pString;
    }

    /**
     * @return l'id
     */
    public long getId()
    {
        return mId;
    }

    /**
     * @param pId l'id de la formule
     */
    public void setId( long pId )
    {
        mId = pId;
    }

}
