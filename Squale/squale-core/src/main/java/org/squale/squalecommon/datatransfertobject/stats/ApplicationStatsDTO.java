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
package org.squale.squalecommon.datatransfertobject.stats;

import java.util.Date;

/**
 * Permet de récupérer les statistiques par application
 */
public class ApplicationStatsDTO
{
    /** Le nom de l'application concernée */
    private String mApplicationName;

    /** Indique si l'application est validée */
    private boolean mValidatedApplication;

    /** true si le derniers audits exécutés est réussis */
    private boolean mLastAuditIsTerminated;

    /** false si aucun audit réussis présent dans les n derniers jours (90 par défaut) */
    private boolean mActivatedApplication;

    /** date du dernier audit réussis */
    private Date mLastTerminatedAuditDate;

    /** la durée en hh:mm du dernier audit */
    private String mLastAuditDuration;

    /** le nombre d'audit ( quelque soit leur état ) réalisés dutant les n derniers jours (10 par défaut) */
    private int mNbAudits;

    /** le nombre audits en réussis */
    private int mNbTerminatedAudits;

    /** le nombre audits partiel ou en échec */
    private int mNbPartialOrFaliedAudits;

    /** la date du dernier audit en échec */
    private Date mLastFailedAuditDate;

    /** la date du plus ancien audit réussis */
    private Date mFirstTerminatedAuditDate;

    /** le nom du serveur de l'application */
    private String mServerName;

    /** la fréquence de purge */
    private String mPurgeFrequency = "-";

    /** Date du dernier accès utilisateur */
    private Date mLastAccess;

    /** Indicate if application is archived (i.e. has no users) */
    private boolean mArchived;

    /**
     * @return false si aucun audit réussis présent dans les n derniers jours
     */
    public boolean isActivatedApplication()
    {
        return mActivatedApplication;
    }

    /**
     * @return true if application has no users
     */
    public boolean isArchived()
    {
        return mArchived;
    }

    /**
     * @return le nom de l'application concernée
     */
    public String getApplicationName()
    {
        return mApplicationName;
    }

    /**
     * @return la date du plus ancien audit réussis
     */
    public Date getFirstTerminatedAuditDate()
    {
        return mFirstTerminatedAuditDate;
    }

    /**
     * @return la date du dernier accès utilisateur
     */
    public Date getLastAccess()
    {
        return mLastAccess;
    }

    /**
     * @return la durée en hh:mm du dernier audit
     */
    public String getLastAuditDuration()
    {
        return mLastAuditDuration;
    }

    /**
     * @return la date du dernier audit en échec
     */
    public Date getLastFailedAuditDate()
    {
        return mLastFailedAuditDate;
    }

    /**
     * @return la date du dernier audit réussis
     */
    public Date getLastTerminatedAuditDate()
    {
        return mLastTerminatedAuditDate;
    }

    /**
     * @return le nombre d'audit ( quelque soit leur état ) réalisés dutant les n derniers jours
     */
    public int getNbAudits()
    {
        return mNbAudits;
    }

    /**
     * @return le nombre audits partiel ou en échec
     */
    public int getNbPartialOrFaliedAudits()
    {
        return mNbPartialOrFaliedAudits;
    }

    /**
     * @return le nombre audits en réussis
     */
    public int getNbTerminatedAudits()
    {
        return mNbTerminatedAudits;
    }

    /**
     * @return le nom du serveur de l'application
     */
    public String getServerName()
    {
        return mServerName;
    }

    /**
     * @return true si le derniers audits exécutés est réussis
     */
    public boolean isLastAuditIsTerminated()
    {
        return mLastAuditIsTerminated;
    }

    /**
     * @return true si l'application est validée
     */
    public boolean isValidatedApplication()
    {
        return mValidatedApplication;
    }

    /**
     * @param pActivated false si aucun audit réussis présent dans les n derniers jours
     */
    public void setActivatedApplication( boolean pActivated )
    {
        mActivatedApplication = pActivated;
    }

    /**
     * @param pArchived true if application has no users
     */
    public void setArchived( boolean pArchived )
    {
        mArchived = pArchived;
    }

    /**
     * @param pName le nom de l'application
     */
    public void setApplicationName( String pName )
    {
        mApplicationName = pName;
    }

    /**
     * @param pDate la date du plus ancien audit réussis
     */
    public void setFirstTerminatedAuditDate( Date pDate )
    {
        mFirstTerminatedAuditDate = pDate;
    }

    /**
     * @param pDate la date du dernier accès utilisateur
     */
    public void setLastAccess( Date pDate )
    {
        mLastAccess = pDate;
    }

    /**
     * @param pDuration la durée en hh:mm du dernier audit
     */
    public void setLastAuditDuration( String pDuration )
    {
        mLastAuditDuration = pDuration;
    }

    /**
     * @param pDate la date du dernier audit en échec
     */
    public void setLastFailedAuditDate( Date pDate )
    {
        mLastFailedAuditDate = pDate;
    }

    /**
     * @param pDate la date du dernier audit réussis
     */
    public void setLastTerminatedAuditDate( Date pDate )
    {
        mLastTerminatedAuditDate = pDate;
    }

    /**
     * @param pNbAudits le nombre d'audit ( quelque soit leur état ) réalisés dutant les n derniers jours
     */
    public void setNbAudits( int pNbAudits )
    {
        mNbAudits = pNbAudits;
    }

    /**
     * @param pNbAudits le nombre audits partiel ou en échec
     */
    public void setNbPartialOrFaliedAudits( int pNbAudits )
    {
        mNbPartialOrFaliedAudits = pNbAudits;
    }

    /**
     * @param pNbAudits le nombre audits en réussis
     */
    public void setNbTerminatedAudits( int pNbAudits )
    {
        mNbTerminatedAudits = pNbAudits;
    }

    /**
     * @param pServerName le nom du serveur de l'application
     */
    public void setServerName( String pServerName )
    {
        mServerName = pServerName;
    }

    /**
     * @param pTerminated true si le derniers audit exécuté est réussi
     */
    public void setLastAuditIsTerminated( boolean pTerminated )
    {
        mLastAuditIsTerminated = pTerminated;
    }

    /**
     * @param pValidated true si l'application est validée
     */
    public void setValidatedApplication( boolean pValidated )
    {
        mValidatedApplication = pValidated;
    }

    /**
     * @return la fréquence de purge
     */
    public String getPurgeFrequency()
    {
        return mPurgeFrequency;
    }

    /**
     * @param pFreq la fréquence de purge
     */
    public void setPurgeFrequency( int pFreq )
    {
        if ( pFreq >= 0 )
        {
            mPurgeFrequency = "" + pFreq;
        }
    }

}
