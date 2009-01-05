/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.airfrance.squalecommon.datatransfertobject.config;

import java.io.Serializable;
import java.util.Collection;

import com.airfrance.squalecommon.enterpriselayer.businessobject.config.AdminParamsBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.AuditFrequencyBO;

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
     * The list of a configuration parameter (adminParamsBO)
     */
    private Collection<AdminParamsDTO> adminParams;

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
     * Setter method for the Frequencies Collection
     * 
     * @param pCollection The new Collection of max frequencies for the audit
     */
    public void setFrequencies( Collection pCollection )
    {
        mFrequencies = pCollection;
    }

    /**
     * Getter method for the frequencies Collection
     * 
     * @return the collection of max frequencies for the audit
     */
    public Collection getFrequencies()
    {
        return mFrequencies;
    }

    /**
     * Getter method for the Collection of adminParamsDTO
     * 
     * @return the adminParamsDTO Collection
     */
    public Collection<AdminParamsDTO> getAdminParams()
    {
        return adminParams;
    }

    /**
     * Setter method for the adminParams Collection
     * 
     * @param pAdminParams the new Collection of adminParamsDTO
     */
    public void setAdminParams( Collection<AdminParamsDTO> pAdminParams )
    {
        adminParams = pAdminParams;
    }
}
