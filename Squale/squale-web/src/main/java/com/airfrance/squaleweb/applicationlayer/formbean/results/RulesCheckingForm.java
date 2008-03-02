package com.airfrance.squaleweb.applicationlayer.formbean.results;

import com.airfrance.squaleweb.applicationlayer.formbean.ActionIdForm;

/**
 * Bean pour une transgression
 */
public class RulesCheckingForm
    extends ActionIdForm
{

    /**
     * Nom la Règle
     */
    protected String mNameRule;

    /**
     * La sévérité de la règle
     */
    protected String mSeverity;

    /**
     * La sévérité de la règle pour l'affichage
     */
    protected String mSeverityLang;

    /**
     * La version de grille de règle
     */
    protected String mVersion;

    /**
     * Nombre de transgressions de la règle
     */
    protected int mTransgressionsNumber;

    /**
     * Id de la mesure
     */
    protected long mMeasureID;

    /**
     * Constructeur par défaut
     */
    public RulesCheckingForm()
    {
        mMeasureID = -1;
    }

    /**
     * @return l'id de la règle
     */
    public long getId()
    {
        return mId;
    }

    /**
     * @return nom de la règle
     */
    public String getNameRule()
    {
        return mNameRule;
    }

    /**
     * @return sevérité de la règle
     */
    public String getSeverity()
    {
        return mSeverity;
    }

    /**
     * @return sevérité de la règle traduite
     */
    public String getSeverityLang()
    {
        return mSeverityLang;
    }

    /**
     * @return le nombre de trasgression de la règle
     */
    public int getTransgressionsNumber()
    {
        return mTransgressionsNumber;
    }

    /**
     * @return la version
     */
    public String getVersion()
    {
        return mVersion;
    }

    /**
     * @return l'id de la mesure
     */
    public long getMeasureID()
    {
        return mMeasureID;
    }

    /**
     * @param pId l'id de la règle
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * @param pNameRule nom de la règle
     */
    public void setNameRule( String pNameRule )
    {
        mNameRule = pNameRule;
    }

    /**
     * @param pSeverity sevérité de la règle
     */
    public void setSeverity( String pSeverity )
    {
        mSeverity = pSeverity;
    }

    /**
     * @param pSeverityLang sevérité de la règle traduite
     */
    public void setSeverityLang( String pSeverityLang )
    {
        mSeverityLang = pSeverityLang;
    }

    /**
     * @param pTrasgressionNumber le nombre de trasgression de la règle
     */
    public void setTransgressionsNumber( int pTrasgressionNumber )
    {
        mTransgressionsNumber = pTrasgressionNumber;
    }

    /**
     * @param pVersion la version
     */
    public void setVersion( String pVersion )
    {
        mVersion = pVersion;
    }

    /**
     * @param pID l'id de la mesure
     */
    public void setMeasureID( long pID )
    {
        mMeasureID = pID;
    }
}
