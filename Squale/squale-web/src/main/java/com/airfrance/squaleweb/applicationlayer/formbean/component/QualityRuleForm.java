package com.airfrance.squaleweb.applicationlayer.formbean.component;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Données synthétiques d'un facteur
 */
public class QualityRuleForm
    extends RootForm
{
    /** séparateur des pondérartions */
    public static final String SEPARATOR = ", ";

    /** Nom du facteur */
    private String mName;

    /** Pondération */
    private String mPonderation;

    /** l'id */
    private long id;

    /**
     * @return nom
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @param pString nom
     */
    public void setName( String pString )
    {
        mName = pString;
    }

    /**
     * @return pondération
     */
    public String getPonderation()
    {
        return mPonderation;
    }

    /**
     * @param pString pondération
     */
    public void setPonderation( String pString )
    {
        mPonderation = pString;
    }

    /**
     * @return l'id
     */
    public long getId()
    {
        return id;
    }

    /**
     * @param newId la nouvelle valeur de l'id
     */
    public void setId( long newId )
    {
        id = newId;
    }

}
