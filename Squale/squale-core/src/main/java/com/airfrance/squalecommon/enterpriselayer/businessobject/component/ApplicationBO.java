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
//Source file: D:\\cc_views\\squale_v0_0_act_M400843\\squale\\src\\squaleCommon\\src\\com\\airfrance\\squalecommon\\enterpriselayer\\businessobject\\component\\ApplicationBO.java

package com.airfrance.squalecommon.enterpriselayer.businessobject.component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.airfrance.squalecommon.enterpriselayer.businessobject.UnexpectedRelationException;
import com.airfrance.squalecommon.enterpriselayer.businessobject.access.UserAccessBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.AuditFrequencyBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ServeurBO;

/**
 * Représente une application Air France
 * 
 * @author m400842
 * @hibernate.subclass lazy="true" discriminator-value="Application"
 */
public class ApplicationBO
    extends AbstractComplexComponentBO
{

    /**
     * Fréquence d'audit en nombre de jour
     */
    private int mAuditFrequency;

    /**
     * Règle de purge
     */
    private int mResultsStorageOptions;

    /** indique si l'application était déja en production au moment de sa création dans SQUALE */
    private boolean mIsInProduction = false;

    /** indique si l'application a été développé en externe */
    private boolean mExternalDev = false;

    /**
     * L'application est en création (non validée)
     */
    public static final int IN_CREATION = 0;

    /**
     * L'application est validée.
     */
    public static final int VALIDATED = 1;

    /**
     * L'application est supprimée.
     */
    public static final int DELETED = 2;

    /**
     * Contient le status du projet (non validé, en création, ...)
     */
    private int mStatus;

    /**
     * Date de dernière modification du projet
     */
    private Date mLastUpdate;

    /**
     * Nom du dernier utilisateur ayant modifié l'application
     */
    private String mLastUser;

    /**
     * Contient le nom des trois derniers accès utilisateur non administrateur Squale
     */
    private List mUserAccesses = new ArrayList();

    /**
     * Site sur lequel les sources sont hébergées (Valbonne, ...)
     */
    private ServeurBO mServeurBO;

    /**
     * Application publique
     */
    private boolean mPublic;

    /**
     * List of all source code recovering termination task. Attribute use when the source code recovering optimization
     * is enabled
     */
    private HashSet<Object> sourceCodeTerminationTask;

    /**
     * Instancie un nouveau composant.
     * 
     * @param pName Nom du composant.
     * @roseuid 42AFF0B3011B
     */
    public ApplicationBO( final String pName )
    {
        super();
        setName( pName );
    }

    /**
     * Access method for the mAuditFrequency property.
     * 
     * @return the current value of the mAuditFrequency property
     * @hibernate.property name="auditFrequency" column="AuditFrequency" type="integer" length="10" not-null="false"
     *                     unique="false" insert="true" update="true"
     * @roseuid 42BACECB0380
     */
    public int getAuditFrequency()
    {
        return mAuditFrequency;
    }

    /**
     * Sets the value of the mAuditFrequency property.
     * 
     * @param pAuditFrequency the new value of the mAuditFrequency property
     * @roseuid 42BACECB0381
     */
    public void setAuditFrequency( int pAuditFrequency )
    {
        mAuditFrequency = pAuditFrequency;
    }

    /**
     * Access method for the mResultsStorageOptions property.
     * 
     * @return the current value of the mResultsStorageOptions property
     * @hibernate.property column="ResultsStorageOptions" type="integer" length="10" not-null="false" unique="false"
     *                     insert="true" update="true" name="resultsStorageOptions"
     * @roseuid 42BACECB0383
     */
    public int getResultsStorageOptions()
    {
        return mResultsStorageOptions;
    }

    /**
     * Sets the value of the mResultsStorageOptions property.
     * 
     * @param pResultsStorageOptions the new value of the mResultsStorageOptions property
     * @roseuid 42BACECB0390
     */
    public void setResultsStorageOptions( int pResultsStorageOptions )
    {
        mResultsStorageOptions = pResultsStorageOptions;
    }

    /**
     * Retourne le statut de l'application
     * 
     * @return the mStatus property is true
     * @hibernate.property name="status" column="Status" type="integer" length="10" unique="false" insert="true"
     *                     update="true"
     * @roseuid 42CAA72C0133
     */
    public int getStatus()
    {
        return mStatus;
    }

    /**
     * Sets the value of the mStatus property.
     * 
     * @param pStatus le status du projet
     * @roseuid 42CAA72C020E
     */
    public void setStatus( int pStatus )
    {
        mStatus = pStatus;
    }

    /**
     * Constructeur par défaut.
     * 
     * @roseuid 42CB9778032A
     */
    public ApplicationBO()
    {
        super();
        sourceCodeTerminationTask = new HashSet<Object>();
    }

    /**
     * Constructeur complet.
     * 
     * @param pName nom du composant
     * @param pAuditFrequency fréquence d'audit
     * @param pResultsStorageOptions options de stockage des résultats
     * @param pStatus projet validé ou non
     * @param pAudits Collection des audits
     * @param pChildren Collection d'enfants
     * @param pSiteId le site de l'application
     * @param pExternalDev le booléen indiquant si c'est un développement externe
     * @param pIsInProduction un booléen indiquant si l'application était déjà en production au moment de sa création
     *            dans SQUALE
     * @throws UnexpectedRelationException si la relation ne peut etre ajouté
     * @roseuid 42CB9779000D
     */
    public ApplicationBO( String pName, int pAuditFrequency, int pResultsStorageOptions, int pStatus,
                          Collection pAudits, Collection pChildren, long pSiteId, boolean pExternalDev,
                          boolean pIsInProduction )
        throws UnexpectedRelationException
    {
        super( pName, pChildren, null );
        mAuditFrequency = pAuditFrequency;
        mResultsStorageOptions = pResultsStorageOptions;
        mStatus = pStatus;
        mAudits = pAudits;
        mServeurBO = new ServeurBO();
        mServeurBO.setServeurId( pSiteId );
        mExternalDev = pExternalDev;
        mIsInProduction = pIsInProduction;
        sourceCodeTerminationTask = new HashSet<Object>();
    }

    /**
     * Récupère l'attribut mPublic
     * 
     * @return projet est-il public
     * @hibernate.property column="PublicApplication" type="boolean" unique="false" insert="true" update="true"
     *                     name="public"
     */
    public boolean getPublic()
    {
        return mPublic;
    }

    /**
     * Affecte pPublic à l'attribut mPublic.
     * 
     * @param pPublic projet est-il publique
     * @roseuid 42CE36C203DF
     */
    public void setPublic( boolean pPublic )
    {
        mPublic = pPublic;
    }

    /**
     * Récupère l'attribut mLastUpdate
     * 
     * @return la date de dernière modification.
     * @hibernate.property name="lastUpdate" column="LastUpdate" type="timestamp" not-null="false" unique="false"
     *                     insert="true" update="true"
     */
    public Date getLastUpdate()
    {
        return mLastUpdate;
    }

    /**
     * Modifie l'attribut mLastUpdate
     * 
     * @param pDate la date
     */
    public void setLastUpdate( Date pDate )
    {
        mLastUpdate = pDate;
    }

    /**
     * Affecte la date courante à l'attribut mLastUpdate
     */
    public void setLastUpdate()
    {
        Calendar cal = new GregorianCalendar();
        mLastUpdate = cal.getTime();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals( Object pObj )
    {
        boolean ret = false;
        if ( pObj instanceof ApplicationBO )
        {
            ApplicationBO appli = (ApplicationBO) pObj;
            ret =
                ( getName() != null ) && ( appli.getName() != null ) && getName().equals( appli.getName() )
                    && ( getStatus() == appli.getStatus() );
        }
        return ret;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return getName() == null ? super.hashCode() : getName().hashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        ToStringBuilder stringBuilder = new ToStringBuilder( this );
        stringBuilder.append( "Name", mName );
        return stringBuilder.toString();
    }

    /**
     * @return le booléen indiquant si le dev a été fait en externe ou pas
     * @hibernate.property column="EXTERNAL_DEV" type="boolean" unique="false" insert="true" update="true"
     *                     name="externalDev"
     */
    public boolean getExternalDev()
    {
        return mExternalDev;
    }

    /**
     * @return le booléen indiquant si l'application était déjà en production au moment de sa création dans squale
     * @hibernate.property name="inProduction" column="IN_PRODUCTION" type="boolean" unique="false" insert="true"
     *                     update="true"
     */
    public boolean getInProduction()
    {
        return mIsInProduction;
    }

    /**
     * @param pExternal le booléen indiquant si le dev a été fait en externe
     */
    public void setExternalDev( boolean pExternal )
    {
        mExternalDev = pExternal;
    }

    /**
     * @param pInProduction le booléen indiquant si l'application était déjà en production au
     */
    public void setInProduction( boolean pInProduction )
    {
        mIsInProduction = pInProduction;
    }

    /**
     * @return le dernier utilisateur ayant modifié l'application
     * @hibernate.property column="lastUser" type="string" length="1024" unique="false" insert="true" update="true"
     *                     name="lastUser"
     */
    public String getLastUser()
    {
        return mLastUser;
    }

    /**
     * @param pUser le dernier utilisateur ayant modifié l'application
     */
    public void setLastUser( String pUser )
    {
        mLastUser = pUser;
    }

    /**
     * Retourne le serveur de l'application
     * 
     * @return le serveur de l'application
     * @hibernate.many-to-one column="Serveur"
     *                        class="com.airfrance.squalecommon.enterpriselayer.businessobject.config.ServeurBO"
     *                        not-null="false" insert="true" update="true" cascade="none" outer-join="auto"
     */
    // name="serveurBO"
    public ServeurBO getServeurBO()
    {
        return mServeurBO;
    }

    /**
     * Modifie le serveur de l'application
     * 
     * @param pServeurBO le serveur de l'application
     */
    public void setServeurBO( ServeurBO pServeurBO )
    {
        mServeurBO = pServeurBO;
    }

    /**
     * @return les 3 derniers accès utilisateur
     * @hibernate.list table="UserAccess" cascade="all" lazy="false"
     * @hibernate.key column="ApplicationId"
     * @hibernate.index column="AccessIndex" type="long" length="19"
     * @hibernate.one-to-many class="com.airfrance.squalecommon.enterpriselayer.businessobject.access.UserAccessBO"
     */
    public List getUserAccesses()
    {
        return mUserAccesses;
    }

    /**
     * @param pAccessBOs les accès utilisateur
     */
    public void setUserAccesses( List pAccessBOs )
    {
        mUserAccesses = pAccessBOs;
    }

    /**
     * Ajoute un accès en supprimant le dernier élément du tableau et en décalant les autres élements afin d'ajouter
     * l'accès en premier
     * 
     * @param pAccessBO l'accès à ajouter
     * @param maxSize le nombre limite d'accès à conserver
     */
    public void addUserAccess( UserAccessBO pAccessBO, int maxSize )
    {
        // On n'ajoute l'élément seulement si il n'existe pas déjà
        if ( !mUserAccesses.contains( pAccessBO ) )
        {
            // On affecte l'application
            pAccessBO.setApplication( this );
            // On ajoute l'élément en début de liste
            mUserAccesses.add( 0, pAccessBO );
            // Si la taille max est atteinte, on supprime le dernier élément
            int stackSize = mUserAccesses.size();
            if ( stackSize > maxSize )
            {
                mUserAccesses.remove( stackSize - 1 );
            }
        }
    }

    /**
     * @param pFrequencies les fréquences max autorisées
     * @return true si on a changé la fréquence de l'application
     */
    public boolean changeFrequency( Collection pFrequencies )
    {
        boolean hasChanged = false;
        if ( null != mUserAccesses && mUserAccesses.size() > 0 )
        {
            // Le dernier accès utilisateur
            Date lastAccess = ( (UserAccessBO) mUserAccesses.get( 0 ) ).getDate();
            // On fait ensuite une recherche sur le nombre de jours max des fréquences
            // afin de vérifier que la fréquence de l'application respecte la configuration SQUALIX
            int nbDaysMax = findFrequency( lastAccess, pFrequencies );
            // On vérifie la fréquence (> 0 car peut avoir que des audits de jalon)
            if ( mAuditFrequency > 0 && mAuditFrequency < nbDaysMax )
            {
                // On change la fréquence
                setAuditFrequency( nbDaysMax );
                hasChanged = true;
            }
        }
        return hasChanged;
    }

    /**
     * @param lastAccess le dernier accès utilisateur
     * @param pFrequencies les fréquences. La collection ne doit pas être nulle ni vide.
     * @return la fréquence
     */
    private int findFrequency( Date lastAccess, Collection pFrequencies )
    {
        // initialisation
        int frequencyToChange = -1; // Si la fréquence ne doit pas être changée, on retourne -1
        // constantes pour le calcul du nombre de jour
        final int HOURS_IN_DAY = 24;
        final int MINUTES_IN_HOUR = 60;
        final int SECONDS_IN_MINUTE = 60;
        final int MILLI_IN_SECOND = 1000;
        // On récupère le nombre de jours depuis le dernier accès
        Calendar today = Calendar.getInstance();
        Calendar access = Calendar.getInstance();
        access.setTime( lastAccess );
        // on compte le nombre de jours qui séparent les deux dates
        int nbDays =
            new Long( ( today.getTimeInMillis() - access.getTimeInMillis() ) / HOURS_IN_DAY / MINUTES_IN_HOUR
                / SECONDS_IN_MINUTE / MILLI_IN_SECOND ).intValue();
        // On va créer la liste des fréquences triées par nombre de jours
        List frequencies = new ArrayList( pFrequencies );
        Collections.sort( frequencies );
        AuditFrequencyBO frequency = null;
        frequency = (AuditFrequencyBO) frequencies.get( 0 );
        int curFreq = frequency.getDays();
        if ( curFreq < nbDays )
        {
            boolean found = false;
            // Tant qu'on a pas trouvé la limite on itère
            for ( int i = 1; !found && i < frequencies.size(); i++ )
            {
                frequency = (AuditFrequencyBO) frequencies.get( i );
                if ( frequency.getDays() < nbDays )
                {
                    curFreq = frequency.getFrequency();
                }
                else
                {
                    found = true;
                }
            }
            frequencyToChange = curFreq;
        }
        return frequencyToChange;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO#accept(com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentVisitor,
     *      java.lang.Object)
     */
    public Object accept( ComponentVisitor pVisitor, Object pArgument )
    {
        return pVisitor.visit( this, pArgument );
    }

    /**
     * Get the list of source code recovering termination task which should be done at the end of the audit
     * 
     * @return The list of source code recovering termination task
     */
    public HashSet<Object> getSourceCodeTerminationTask()
    {
        return sourceCodeTerminationTask;
    }

    /**
     * Set the list of source code recovering termination task which should be done at the end of the audit
     * 
     * @param pSourceCodeTerminationTask The list of source code recovering termination task
     */
    public void setSourceCodeTerminationTask( HashSet<Object> pSourceCodeTerminationTask )
    {
        sourceCodeTerminationTask = pSourceCodeTerminationTask;
    }

}
