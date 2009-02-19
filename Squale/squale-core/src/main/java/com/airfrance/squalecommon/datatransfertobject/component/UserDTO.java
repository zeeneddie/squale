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
//Source file: D:\\CC_VIEWS\\SQUALE_V0_0_ACT\\SQUALE\\SRC\\squaleCommon\\src\\com\\airfrance\\squalecommon\\datatransfertobject\\UserDTO.java

package com.airfrance.squalecommon.datatransfertobject.component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class UserDTO
    implements Serializable
{

    /**
     * email de l'utilisateur
     */
    private String mEmail;

    /**
     * Indique si l'utilisateur ne souhaite pas recevoir d'emails automatiques
     */
    private boolean mUnsubscribed;

    /**
     * nom complet de l'utilisateur
     */
    private String mFullName;

    /**
     * Verification si l'utilisateur est administrateur
     */
    private ProfileDTO mDefaultProfile;

    /**
     * Matricule de l'utilisateur
     */
    private String mMatricule;

    /**
     * Mot de passe de l'utilisateur
     */
    private String mPassword = "";

    /**
     * Couple applications sous forme de ComponentDTO / ProfileDTO
     */
    private Map mProfiles;

    /**
     * Applications en cours de validation sous forme de ComponentDTO
     */
    private List mApplicationsInCreation;

    /**
     * Applications publiques sous forme de ComponentDTO
     */
    private List mPublicApplications;

    /**
     * Identifiant de l'utilisateur
     */
    private long mID = -1;

    /**
     * Constructeur vide
     * 
     * @roseuid 42CD1F53032B
     */
    public UserDTO()
    {
        mProfiles = new HashMap();
    }

    /**
     * Access method for the mMatricule property.
     * 
     * @return the current value of the mMatricule property
     * @roseuid 42CD1F530335
     */
    public String getMatricule()
    {
        return mMatricule;
    }

    /**
     * Sets the value of the mMatricule property. pMatricule is transformed to lowercase
     * 
     * @param pMatricule the new value of the mMatricule property
     * @roseuid 42CD1F530336
     */
    public void setMatricule( String pMatricule )
    {
        if ( pMatricule != null )
        {
            mMatricule = pMatricule.toLowerCase();
        }
    }

    /**
     * Access method for the mPassword property.
     * 
     * @return the current value of the mPassword property
     * @roseuid 42CD1F53035E
     */
    public String getPassword()
    {
        return mPassword;
    }

    /**
     * Sets the value of the mPassword property.
     * 
     * @param pPassword the new value of the mPassword property
     * @roseuid 42CD1F530368
     */
    public void setPassword( String pPassword )
    {
        mPassword = pPassword;
    }

    /**
     * Access method for the mApplication property.
     * 
     * @return the current value of the mApplication property
     * @roseuid 42CD1F530386
     */
    public Map getProfiles()
    {
        return mProfiles;
    }

    /**
     * Sets the value of the mApplication property.
     * 
     * @param pProfiles the new value of the mApplication property
     * @roseuid 42CD1F530387
     */
    public void setProfiles( Map pProfiles )
    {
        mProfiles = pProfiles;
    }

    /**
     * @return la liste des applications en cours de validation
     */
    public List getApplicationsInCreation()
    {
        return mApplicationsInCreation;
    }

    /**
     * @return la liste des applications publiques
     */
    public List getPublicApplications()
    {
        return mPublicApplications;
    }

    /**
     * Modifie la liste des applications en cours de validation
     * 
     * @param pInCreation la liste des applications en cours de création
     */
    public void setApplicationsInCreation( List pInCreation )
    {
        mApplicationsInCreation = pInCreation;
    }

    /**
     * Modifie la liste des applications publiques
     * 
     * @param pPublicApplications la liste des applications publiques
     */
    public void setPublicApplications( List pPublicApplications )
    {
        mPublicApplications = pPublicApplications;

    }

    /**
     * Access method for the mID property.
     * 
     * @return the current value of the mID property
     */
    public long getID()
    {
        return mID;
    }

    /**
     * Sets the value of the mID property.
     * 
     * @param pID the new value of the mID property
     */
    public void setID( long pID )
    {
        mID = pID;
    }

    /**
     * Access method for the mDefaultProfile property.
     * 
     * @return the current value of the mDefaultProfile property
     */
    public ProfileDTO getDefaultProfile()
    {
        return mDefaultProfile;
    }

    /**
     * Sets the value of the mDefaultProfile property.
     * 
     * @param pDefaultProfile the new value of the mDefaultProfile property
     */
    public void setDefaultProfile( ProfileDTO pDefaultProfile )
    {
        mDefaultProfile = pDefaultProfile;
    }

    /**
     * Access method for the mEmail property.
     * 
     * @return the current value of the mEmail property
     */
    public String getEmail()
    {
        return mEmail;
    }

    /**
     * Sets the value of the mEmail property.
     * 
     * @param pEmail the new value of the mEmail property
     */
    public void setEmail( String pEmail )
    {
        mEmail = pEmail;
    }

    /**
     * Access method for the mFullName property.
     * 
     * @return the current value of the mFullName property
     */
    public String getFullName()
    {
        return mFullName;
    }

    /**
     * Sets the value of the mFullName property.
     * 
     * @param pFullName the new value of the mFullName property
     */
    public void setFullName( String pFullName )
    {
        mFullName = pFullName;
    }

    /**
     * @return true si l'utilisateur s'est désabonné de l'envoi d'email
     */
    public boolean isUnsubscribed()
    {
        return mUnsubscribed;
    }

    /**
     * @param pUnsubscribed true si l'utilisateur s'est désabonné de l'envoi d'email
     */
    public void setUnsubscribed( boolean pUnsubscribed )
    {
        mUnsubscribed = pUnsubscribed;
    }
}
