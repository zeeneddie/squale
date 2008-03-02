package com.airfrance.squaleweb.applicationlayer.formbean.component;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * @author M400842
 */
public class ErrorForm
    extends RootForm
{

    /**
     * Message traduit en version Squale si possible : il s'agit en fait de sa clé d'internationalisation.
     */
    private String mMessage;

    /** Nombre de fois que le message apparait pour cet audit et ce projet */
    private int nbOcc = 1;

    /**
     * Niveau de criticité
     */
    private String mLevel;

    /**
     * Nom de la tâche source
     */
    private String mTaskName;

    /**
     * Audit durant lequel l'erreur a été générée
     */
    private long mErrorAuditId;

    /**
     * Projet sur lequel l'erreur a été générée
     */
    private long mErrorProjectId;

    /**
     * Access method for the mMessage property.
     * 
     * @return the current value of the mMessage property
     */
    public String getMessage()
    {
        return mMessage;
    }

    /**
     * Sets the value of the mMessage property.
     * 
     * @param pMessage the new value of the mMessage property
     */
    public void setMessage( String pMessage )
    {
        mMessage = pMessage;
    }

    /**
     * Access method for the mLevel property.
     * 
     * @return the current value of the mLevel property
     */
    public String getLevel()
    {
        return mLevel;
    }

    /**
     * Sets the value of the mLevel property.
     * 
     * @param pLevel the new value of the mLevel property
     */
    public void setLevel( String pLevel )
    {
        mLevel = pLevel;
    }

    /**
     * Access method for the mTaskName property.
     * 
     * @return the current value of the mTaskName property
     */
    public String getTaskName()
    {
        return mTaskName;
    }

    /**
     * Sets the value of the mTaskName property.
     * 
     * @param pTaskName the new value of the mTaskName property
     */
    public void setTaskName( String pTaskName )
    {
        mTaskName = pTaskName;
    }

    /**
     * Access method for the mAudit property.
     * 
     * @return the current value of the mAudit property
     */
    public long getAuditId()
    {
        return mErrorAuditId;
    }

    /**
     * Sets the value of the mAudit property.
     * 
     * @param pAuditId the new value of the mAudit property
     */
    public void setAuditId( long pAuditId )
    {
        mErrorAuditId = pAuditId;
    }

    /**
     * Access method for the mProject property.
     * 
     * @return the current value of the mProject property
     */
    public long getErrorProjectId()
    {
        return mErrorProjectId;
    }

    /**
     * Sets the value of the mProject property.
     * 
     * @param pProjectId the new value of the mProjectId property
     */
    public void setErrorProjectId( long pProjectId )
    {
        mErrorProjectId = pProjectId;
    }

    /**
     * @return le nombre d'occurrences
     */
    public int getNbOcc()
    {
        return nbOcc;
    }

    /**
     * @param pNbOcc le nombre d'occurrences
     */
    public void setNbOcc( int pNbOcc )
    {
        nbOcc = pNbOcc;
    }

}
