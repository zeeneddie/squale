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
//Source file: D:\\CC_VIEWS\\SQUALE_V0_0_ACT\\SQUALE\\SRC\\squaleCommon\\src\\com\\airfrance\\squalecommon\\datatransfertobject\\AuditDTO.java

package com.airfrance.squalecommon.datatransfertobject.component;

import java.io.Serializable;
import java.util.Date;

import com.airfrance.squalecommon.util.messages.CommonMessages;

/**
 */
public class AuditDTO
    implements Serializable
{

    /**
     * ID de l'audit en base
     */
    private long mID;

    /**
     * Application associée
     */
    private long mApplicationId;

    /**
     * Nom de l'pplication associée
     */
    private String mApplicationName;

    /**
     * Nom de l'audit (nom pour milestone ou date serialisée pour un suivi)
     */
    private String mName;

    /**
     * Date a laquelle a ete effectuée l'audit
     */
    private Date mDate;

    /**
     * Date formatée sous forme de chaine
     */
    private String mFormattedDate;

    /**
     * Milestone ou Suivi
     */
    private String mType;

    /** La version de squale */
    private double mSqualeVersion;

    /** Le nom du serveur de l'application */
    private String mServerName;

    /**
     * Statut de l'exécution : en attente, succès ou échec.
     */
    private int mStatus;

    /**
     * Date de version des sources (dans le cas d'un audit de jalon) Par défaut cette date = date de réalisation de
     * l'audit
     */
    private Date mHistoricalDate;

    /**
     * Constructeur par defaut
     */
    public AuditDTO()
    {
        // Par défaut la version courante
        mSqualeVersion = getCurrentSqualeVersion();
    }

    /**
     * Access method for the mName property.
     * 
     * @return the current value of the mName property
     * @roseuid 42CB8EB303AE
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Sets the value of the mName property.
     * 
     * @param pName the new value of the mName property
     * @roseuid 42CB8EB303B8
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * Access method for the mDate property.
     * 
     * @return the current value of the mDate property
     * @roseuid 42CB8EB40002
     */
    public Date getDate()
    {
        return mDate;
    }

    /**
     * Sets the value of the mDate property.
     * 
     * @param pDate the new value of the mDate property
     * @roseuid 42CB8EB40020
     */
    public void setDate( Date pDate )
    {
        mDate = pDate;
    }

    /**
     * Access method for the mType property.
     * 
     * @return the current value of the mType property
     * @roseuid 42CB8EB4003E
     */
    public String getType()
    {
        return mType;
    }

    /**
     * Sets the value of the mType property.
     * 
     * @param pType the new value of the mType property
     * @roseuid 42CB8EB40048
     */
    public void setType( String pType )
    {
        mType = pType;
    }

    /**
     * Access method for the mID property.
     * 
     * @return the current value of the mID property
     * @roseuid 42CB8EB40066
     */
    public long getID()
    {
        return mID;
    }

    /**
     * Sets the value of the mID property.
     * 
     * @param pID the new value of the mID property
     * @roseuid 42CB8EB40070
     */
    public void setID( long pID )
    {
        mID = pID;
    }

    /**
     * Access method for the mApplicationId property.
     * 
     * @return the current value of the mApplicationId property
     * @roseuid 42CB8EB40098
     */
    public long getApplicationId()
    {
        return mApplicationId;
    }

    /**
     * Sets the value of the mApplicationId property.
     * 
     * @param pApplicationId the new value of the mApplicationId property
     * @roseuid 42CB8EB400A2
     */
    public void setApplicationId( long pApplicationId )
    {
        mApplicationId = pApplicationId;
    }

    /**
     * Access method for the mStatus property.
     * 
     * @return the current value of the mStatus property
     */
    public int getStatus()
    {
        return mStatus;
    }

    /**
     * Sets the value of the mStatus property.
     * 
     * @param pStatus the new value of the mStatus property
     */
    public void setStatus( int pStatus )
    {
        mStatus = pStatus;
    }

    /**
     * @return la date de verion des sources
     */
    public Date getHistoricalDate()
    {
        return mHistoricalDate;
    }

    /**
     * @param pHistoricalDate la date de version des sources
     */
    public void setHistoricalDate( Date pHistoricalDate )
    {
        mHistoricalDate = pHistoricalDate;
    }

    /**
     * Récupère la date d'exécution de l'audit ou dans le cas d'un audit de jalon, sa date de version pour avoir une
     * cohérence dans l'ordre des audits par rapport au version du composant.
     * 
     * @return la date "réelle" de l'audit
     */
    public Date getRealDate()
    {
        Date real = mDate;
        if ( null != mHistoricalDate )
        {
            real = mHistoricalDate;
        }
        return real;
    }

    /**
     * @return la date formatée
     */
    public String getFormattedDate()
    {
        return mFormattedDate;
    }

    /**
     * @param pDate la nouvelle Date
     */
    public void setFormattedDate( String pDate )
    {
        mFormattedDate = pDate;
    }

    /**
     * @return le nom de l'application associée
     */
    public String getApplicationName()
    {
        return mApplicationName;
    }

    /**
     * @param pApplicationName le nom de l'application associée
     */
    public void setApplicationName( String pApplicationName )
    {
        mApplicationName = pApplicationName;
    }

    // Stats pour admins

    /**
     * La date réelle de commencement avec l'heure
     */
    private Date mRealBeginningDate;

    /**
     * la date à laquelle l'audit s'est terminé
     */
    private Date mEndDate;

    /**
     * la durée de l'audit sous forme XXhYYmZZs
     */
    private String mDuration;

    /**
     * La taille maximum du file system prise par l'audit
     */
    private Long mMaxFileSystemSize;

    /**
     * @return la durée de l'audit
     */
    public String getDuration()
    {
        return mDuration;
    }

    /**
     * @return la date de fin de l'audit
     */
    public Date getEndDate()
    {
        return mEndDate;
    }

    /**
     * @return la taille max du filesystem
     */
    public Long getMaxFileSystemSize()
    {
        return mMaxFileSystemSize;
    }

    /**
     * @return la date de début
     */
    public Date getRealBeginningDate()
    {
        return mRealBeginningDate;
    }

    /**
     * @param pDuration la durée de l'audit
     */
    public void setDuration( String pDuration )
    {
        mDuration = pDuration;
    }

    /**
     * @param pEndDate la date de fin
     */
    public void setEndDate( Date pEndDate )
    {
        mEndDate = pEndDate;
    }

    /**
     * @param pSize la taille du file system
     */
    public void setMaxFileSystemSize( Long pSize )
    {
        mMaxFileSystemSize = pSize;
    }

    /**
     * @param pRealBeginningDate la date réelle de début
     */
    public void setRealBeginningDate( Date pRealBeginningDate )
    {
        mRealBeginningDate = pRealBeginningDate;
    }

    /**
     * @return la version de squale
     */
    public double getSqualeVersion()
    {
        return mSqualeVersion;
    }

    /**
     * @param pVersion la verision de squale
     */
    public void setSqualeVersion( double pVersion )
    {
        mSqualeVersion = pVersion;
    }

    /**
     * @return la version courante de SQUALE
     */
    public static double getCurrentSqualeVersion()
    {
        return Double.parseDouble( CommonMessages.getString( "audit.squale.version" ) );
    }

    /**
     * @return le nom du serveur
     */
    public String getServerName()
    {
        return mServerName;
    }

    /**
     * @param pServerName le nom du serveur
     */
    public void setServerName( String pServerName )
    {
        mServerName = pServerName;
    }

}
