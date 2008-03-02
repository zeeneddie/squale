package com.airfrance.squaleweb.applicationlayer.formbean.results;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Bean pour les items de transgression.
 */
public class RuleCheckingItemForm
    extends RootForm
{

    /** L'id du composant concerné par la transgression */
    private long mComponentId = -1;

    /** Nom du composant concerné par la transgression */
    private String mComponentName = "-";

    /** L'id du composant en relation avec la transgression */
    private long mComponentInvolvedId = -1;

    /** Nom du composant en relation avec la transgression */
    private String mComponentInvolvedName = "-";

    /** Détail de la transgression */
    private String mMessage;

    /**
     * @return l'id du composant concerné par la transgression
     */
    public long getComponentId()
    {
        return mComponentId;
    }

    /**
     * @return le nom du composant concerné par la transgression
     */
    public String getComponentName()
    {
        return mComponentName;
    }

    /**
     * @return l'id du composant en relation avec la transgression
     */
    public long getComponentInvolvedId()
    {
        return mComponentInvolvedId;
    }

    /**
     * @return le nom du composant en relation avec la transgression
     */
    public String getComponentInvolvedName()
    {
        return mComponentInvolvedName;
    }

    /**
     * @return le détail de la transgression
     */
    public String getMessage()
    {
        return mMessage;
    }

    /**
     * @param pComponentName le nom du composant concerné par la transgression
     */
    public void setComponentName( String pComponentName )
    {
        mComponentName = pComponentName;
    }

    /**
     * @param pComponentId l'id du composant concerné par la transgression
     */
    public void setComponentId( long pComponentId )
    {
        mComponentId = pComponentId;
    }

    /**
     * @param pComponentInvolvedId l'id du composant en relation avec la transgression
     */
    public void setComponentInvolvedId( long pComponentInvolvedId )
    {
        mComponentInvolvedId = pComponentInvolvedId;
    }

    /**
     * @param pComponentInvolvedName le nom du composant en relation avec la transgression
     */
    public void setComponentInvolvedName( String pComponentInvolvedName )
    {
        mComponentInvolvedName = pComponentInvolvedName;
    }

    /**
     * @param pMessage le détail de la transgression
     */
    public void setMessage( String pMessage )
    {
        mMessage = pMessage;
    }
}
