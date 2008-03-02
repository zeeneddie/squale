package com.airfrance.squalecommon.datatransfertobject.config;

import java.io.Serializable;
import java.util.Collection;

/**
 * Configuration Squalix
 */
public class SqualixConfigurationDTO
    implements Serializable
{

    /** La liste des dates limites de lancement d'audits */
    private Collection mStopTimes;

    /** La liste des fréquences max des audits en fonction du dernier accès utilisateur */
    private Collection mFrequencies;

    /** La liste des récupérateurs de sources */
    private Collection mSourceManagements;

    /** La liste des profils */
    private Collection mProfiles;

    /**
     * Méthode d'accès pour mStopTimes
     * 
     * @return les dates limites
     */
    public Collection getStopTimes()
    {
        return mStopTimes;
    }

    /**
     * Méthode d'accès pour mProfiles
     * 
     * @return les profils
     */
    public Collection getProfiles()
    {
        return mProfiles;
    }

    /**
     * Méthode d'accès pour mSourceManagements
     * 
     * @return les récupérateurs de sources
     */
    public Collection getSourceManagements()
    {
        return mSourceManagements;
    }

    /**
     * Change la valeur de mStopTimes
     * 
     * @param pStopTimes les nouvelles dates limites
     */
    public void setStopTimes( Collection pStopTimes )
    {
        mStopTimes = pStopTimes;
    }

    /**
     * Change la valeur de mProfiles
     * 
     * @param pProfiles les nouveaux profils
     */
    public void setProfiles( Collection pProfiles )
    {
        mProfiles = pProfiles;
    }

    /**
     * Change la valeur de mSourceManagements
     * 
     * @param pSourceManagements les nouveaux types de récupération des sources
     */
    public void setSourceManagements( Collection pSourceManagements )
    {
        mSourceManagements = pSourceManagements;
    }

    /**
     * @return la liste des fréquences max des audits
     */
    public Collection getFrequencies()
    {
        return mFrequencies;
    }

    /**
     * @param pCollection la liste des fréquences max des audits
     */
    public void setFrequencies( Collection pCollection )
    {
        mFrequencies = pCollection;
    }
}
