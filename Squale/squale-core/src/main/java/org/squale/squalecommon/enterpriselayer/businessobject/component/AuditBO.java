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
//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squaleCommon\\src\\org\\squale\\squalecommon\\enterpriselayer\\businessobject\\component\\AuditBO.java

package org.squale.squalecommon.enterpriselayer.businessobject.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.squale.squalecommon.util.messages.CommonMessages;

/**
 * Représente un audit exécuté ou à venir et ses paramètres
 * 
 * @author m400842
 * @hibernate.class table="AuditBO" mutable="true"
 */
public class AuditBO
    implements Serializable
{
    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId;

    /**
     * Statut de l'exécution : en attente, succès ou échec.
     */
    private int mStatus;

    /**
     * Pour récupérer tous les status d'audits (utilisé par FAcade et DAO)
     */
    public static final int ALL_STATUS = -1;

    /**
     * Audit non réalisé
     */
    public static final int NOT_ATTEMPTED = 0;

    /**
     * audit effectué sans erreur
     */
    public static final int TERMINATED = 1;

    /**
     * Une erreur à eu lieu sur l'audit
     */
    public static final int FAILED = 2;

    /**
     * Audit supprimé
     */
    public static final int DELETED = 3;

    /**
     * Audit partiel
     */
    public static final int PARTIAL = 4;

    /**
     * Audit partiel
     */
    public static final int RUNNING = 5;

    /**
     * Audit périodique dit normal
     */
    public static final String NORMAL = "audit.type.normal";

    /**
     * Audit de jalon
     */
    public static final String MILESTONE = "audit.type.milestone";

    /** Pour qu'on ne prenne pas en compte le type */
    public static final String ALL_TYPES = "audit.type.all";

    /**
     * Nom donné à l'audit dans le cas d'un milestone, ou date sérialisée sinon.
     */
    private String mName;

    /**
     * Date prévue ou effective de l'audit
     */
    private Date mDate;

    /**
     * Date de version des sources (dans le cas d'un audit de jalon) Par défaut cette date = date de réalisation de
     * l'audit
     */
    private Date mHistoricalDate;

    /**
     * Contient la clé du type d'audit : suivi ou milestone
     */
    private String mType;

    /** La version de Squale ayant généré l'audit */
    private String mSqualeVersion;

    /**
     * Résultats qualité générés durant l'audit
     */
    private Collection mQualityResults;

    /**
     * Composants liés à cet audit
     */
    private Collection mComponents;

    /**
     * Commentaire
     */
    private String mComments;

    /** Grilles qualité utilisées pour l'audit */
    private Collection mAuditGrids = new ArrayList();

    /** Configurations d'affcihage liées à l'audit */
    private Collection mAuditDisplayConfs = new ArrayList();

    /**
     * Constructeur. Crée un nouvel audit.
     * 
     * @param pName Nom de l'audit.
     * @param pDate Date prévue de réalisation de l'audit.
     * @param pType Type de l'audit : suivi ou milestone.
     * @param pComments Commentaires...
     * @param pStatus statut de l'audit
     * @param pQualityResults résultats qualité de l'audit
     */
    public AuditBO( final String pName, final Date pDate, final String pType, final String pComments,
                    final int pStatus, final Collection pQualityResults )
    {
        mId = -1;
        mName = pName;
        mDate = pDate;
        mType = pType;
        mComments = pComments;
        mStatus = pStatus;
        mQualityResults = pQualityResults;
        Collection emptyCol = new ArrayList( 0 );
        setComponents( emptyCol );
        mHistoricalDate = pDate;
        mSqualeVersion = getCurrentSqualeVersion();
    }

    /**
     * Constructeur complet. Crée un nouveal audit.
     * 
     * @param pName Nom de l'audit.
     * @param pDate Date prévue de réalisation de l'audit.
     * @param pType Type de l'audit : suivi ou milestone.
     * @param pComments Commentaires...
     * @param pStatus statut de l'audit
     * @param pQualityResults résultats qualité de l'audit
     * @param pHistoricDate la date de version des sources
     */
    public AuditBO( final String pName, final Date pDate, final String pType, final String pComments,
                    final int pStatus, final Collection pQualityResults, final Date pHistoricDate )
    {
        mId = -1;
        mName = pName;
        mDate = pDate;
        mType = pType;
        mComments = pComments;
        mStatus = pStatus;
        mQualityResults = pQualityResults;
        Collection emptyCol = new ArrayList( 0 );
        setComponents( emptyCol );
        mHistoricalDate = pHistoricDate;
        mSqualeVersion = getCurrentSqualeVersion();
    }

    /**
     * Access method for the mName property.
     * 
     * @return the current value of the mName property
     * @hibernate.property name="name" column="Name" type="string" update="true" insert="true"
     * @roseuid 42BACEF50362
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Sets the value of the mName property.
     * 
     * @param pName the new value of the mName property
     * @roseuid 42BACEF50363
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * Access method for the mDate property.
     * 
     * @return the current value of the mDate property
     * @hibernate.property name="date" column="auditDate" type="timestamp" update="true" insert="true"
     * @roseuid 42BACEF50372
     */
    public Date getDate()
    {
        return mDate;
    }

    /**
     * Sets the value of the mDate property.
     * 
     * @param pDate the new value of the mDate property
     * @roseuid 42BACEF50373
     */
    public void setDate( Date pDate )
    {
        mDate = pDate;
    }

    /**
     * Access method for the mType property.
     * 
     * @return the current value of the mType property
     * @hibernate.property name="type" column="auditType" type="string" update="true" insert="true"
     * @roseuid 42BACEF50382
     */
    public String getType()
    {
        return mType;
    }

    /**
     * Sets the value of the mType property.
     * 
     * @param pType the new value of the mType property
     * @roseuid 42BACEF50383
     */
    public void setType( String pType )
    {
        mType = pType;
    }

    /**
     * Access method for the mStatus property.
     * 
     * @return the current value of the mStatus property
     * @hibernate.property name="status" column="Status" type="integer" length="10" not-null="true" unique="false"
     *                     update="true" insert="true"
     * @roseuid 42BACEF50392
     */
    public int getStatus()
    {
        return mStatus;
    }

    /**
     * Sets the value of the mStatus property.
     * 
     * @param pStatus the new value of the mStatus property
     * @roseuid 42BACEF503A1
     */
    public void setStatus( int pStatus )
    {
        mStatus = pStatus;
    }

    /**
     * Access method for the mQualityResults property.
     * 
     * @return the current value of the mQualityResults property
     * @hibernate.bag lazy="true" cascade="none" inverse="true"
     * @hibernate.key column="AuditId"
     * @hibernate.one-to-many class="org.squale.squalecommon.enterpriselayer.businessobject.result.QualityResultBO"
     * @roseuid 42BACEF503B0
     */
    public Collection getQualityResults()
    {
        return mQualityResults;
    }

    /**
     * Sets the value of the mQualityResults property.
     * 
     * @param pQualityResults the new value of the mQualityResults property
     * @roseuid 42BACEF503B1
     */
    public void setQualityResults( Collection pQualityResults )
    {
        mQualityResults = pQualityResults;
    }

    /**
     * Access method for the mId property.
     * 
     * @return the current value of the mId property Note: unsaved-value An identifier property value that indicates
     *         that an instance is newly instantiated (unsaved), distinguishing it from transient instances that were
     *         saved or loaded in a previous session. If not specified you will get an exception like this: another
     *         object associated with the session has the same identifier
     * @hibernate.id generator-class="native" type="long" column="AuditId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="audit_sequence"
     * @roseuid 42BFDEB701B4
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Sets the value of the mId property.
     * 
     * @param pId the new value of the mId property
     * @roseuid 42BFDEB701F2
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * Access method for the mComments property.
     * 
     * @return the current value of the mComments property
     * @hibernate.property name="comments" column="Comments" type="string" update="true" insert="true"
     * @roseuid 42C94DA102DA
     */
    public String getComments()
    {
        return mComments;
    }

    /**
     * Sets the value of the mComments property.
     * 
     * @param pComments the new value of the mComments property
     * @roseuid 42C94DA10309
     */
    public void setComments( String pComments )
    {
        mComments = pComments;
    }

    /**
     * Constructeur par défaut.
     * 
     * @roseuid 42CB9F670080
     */
    public AuditBO()
    {
        mId = -1;
        mStatus = NOT_ATTEMPTED;
        mSqualeVersion = getCurrentSqualeVersion();
        Collection emptyCol = new ArrayList( 0 );
        setComponents( emptyCol );
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals( Object pObj )
    {
        boolean ret = false;
        AuditBO measure = null;
        if ( pObj instanceof AuditBO )
        {
            measure = (AuditBO) pObj;
            EqualsBuilder equalsBuilder = new EqualsBuilder();
            equalsBuilder.append( mName, measure.getName() );
            equalsBuilder.append( mDate, measure.getDate() );
            equalsBuilder.append( mId, measure.getId() ); // Attention: utilisation de l'id est
            // fortement déconseillée par Hibernate
            ret = equalsBuilder.isEquals();
        }
        return ret;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        HashCodeBuilder hashBuilder = new HashCodeBuilder();
        hashBuilder.append( mName );
        hashBuilder.append( mDate );
        hashBuilder.append( mId ); // Attention: utilisation de l'id est
        // fortement déconseillée par Hibernate
        return hashBuilder.toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        ToStringBuilder stringBuilder = new ToStringBuilder( this );
        stringBuilder.append( "Name", mName );
        stringBuilder.append( "Date", mDate );
        stringBuilder.append( "Id", mId ); // Attention: utilisation de l'id est
        // fortement déconseillée par Hibernate
        return stringBuilder.toString();
    }

    /**
     * Récupère les composants liés à cet audit
     * 
     * @return la liste des composants
     * @hibernate.bag table="Components_Audits" lazy="true" cascade="none" inverse="true"
     * @hibernate.key column="AuditId"
     * @hibernate.many-to-many 
     *                         class="org.squale.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO"
     *                         column="ComponentId" outer-join="auto"
     */
    public Collection getComponents()
    {
        return mComponents;
    }

    /**
     * Affecte les composant à l'audit
     * 
     * @param pCollection la collection de composants
     */
    private void setComponents( Collection pCollection )
    {
        mComponents = pCollection;
    }

    /**
     * Ajoute un composant à l'audit.
     * 
     * @param pComponent le composant à ajouter
     * @deprecated ne sera pas mis à jour en base
     */
    public void addComponent( AbstractComponentBO pComponent )
    {
        mComponents.add( pComponent );
    }

    /**
     * @return grilles qualité de l'audit
     * @hibernate.bag lazy="true" inverse="true" cascade="none"
     * @hibernate.key column="AuditId"
     * @hibernate.one-to-many class="org.squale.squalecommon.enterpriselayer.businessobject.component.AuditGridBO"
     */
    public Collection getAuditGrids()
    {
        return mAuditGrids;
    }

    /**
     * @param pAuditGrids grilles qualité de l'audit
     */
    public void setAuditGrids( Collection pAuditGrids )
    {
        mAuditGrids = pAuditGrids;
    }

    /**
     * Ajout d'une grille qualité relative à l'audit
     * 
     * @param pAuditGrid grille qualité d'audit
     */
    public void addAuditGrid( AuditGridBO pAuditGrid )
    {
        mAuditGrids.add( pAuditGrid );
    }

    /**
     * Obtention de la grille d'audit correspondant à un projet
     * 
     * @param pProject projet
     * @return grille d'audit correspondante ou null si non trouvée
     */
    public AuditGridBO getAuditGrid( ProjectBO pProject )
    {
        AuditGridBO result = null;
        Iterator auditGridsIt = getAuditGrids().iterator();
        // Parcours de chaque grille qualité à la recherche de celle
        // correspondant
        while ( auditGridsIt.hasNext() && ( result == null ) )
        {
            AuditGridBO auditGrid = (AuditGridBO) auditGridsIt.next();
            if ( auditGrid.getProject().getId() == pProject.getId() )
            {
                result = auditGrid;
            }
        }
        return result;
    }

    /**
     * @return la date de verion des sources
     * @hibernate.property name="historicalDate" column="historicalDate" type="timestamp" update="true" insert="true"
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
        if ( mType.equals( MILESTONE ) && null != mHistoricalDate )
        {
            real = mHistoricalDate;
        }
        return real;
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
     * @hibernate.property name="duration" column="Duration" type="string" update="true" insert="true" length="10"
     */
    public String getDuration()
    {
        return mDuration;
    }

    /**
     * @return la date de fin de l'audit
     * @hibernate.property name="endDate" column="END_DATE" type="timestamp" update="true" insert="true"
     */
    public Date getEndDate()
    {
        return mEndDate;
    }

    /**
     * @return la taille max du filesystem
     * @hibernate.property name="maxFSSize" column="MAX_FILE_SYSTEM_SIZE" type="long" update="true" insert="true"
     */
    public Long getMaxFileSystemSize()
    {
        return mMaxFileSystemSize;
    }

    /**
     * @return la date de début
     * @hibernate.property name="realBeginDate" column="BEGINNING_DATE" type="timestamp" update="true" insert="true"
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
     * Cette méthode calcule la durée d'un audit et la formate pour affichage Sert pour les stats sur les audits (niveau
     * admin)
     */
    public void calculeDuration()
    {
        // Les différents constantes permettant de formater
        final int nbMilliSecondsInAnHour = 3600000;
        final int nbMillSecondsInAMinute = 60000;
        final int nbOfMinutesInAnHour = 60;
        final int nbMillSecondsInASecond = 1000;
        // Calcule les différents valeurs pour le formatage
        // seulement si on a pu avoir le temps de l'audit
        String result = CommonMessages.getString( "audit.calculation.impossible" );
        if ( getRealBeginningDate() != null && getEndDate() != null )
        {
            long duration = getEndDate().getTime() - getRealBeginningDate().getTime(); // en ms
            long nbHours = duration / nbMilliSecondsInAnHour;
            long nbMinutes = ( duration / nbMillSecondsInAMinute ) % nbOfMinutesInAnHour;
            // Dans le cas des arrondis, on ajuste à la bonne valeur
            // Dans ce cas, vu l'arrondi en dessous il va manquer une minute, on en rajoute une pour que
            // l'affichage de la durée soit cohérent avec les dates.
            if ( ( ( getEndDate().getTime() / nbMillSecondsInASecond ) % nbOfMinutesInAnHour ) < ( ( getRealBeginningDate().getTime() / nbMillSecondsInASecond ) % nbOfMinutesInAnHour ) )
            {
                if ( nbMinutes == nbOfMinutesInAnHour - 1 )
                {
                    // Dans ce cas on change d'heure
                    nbHours++;
                    nbMinutes = 0;
                }
                else
                {
                    nbMinutes++;
                }
            }
            result = nbHours + ":";
            // pour afficher les minutes sur 2 chiffres
            if ( nbMinutes < 10 )
            {
                result += "0";
            }
            result += nbMinutes;
        }
        setDuration( result );

    }

    /**
     * @hibernate.property name="squaleVersion" column="squale_version" type="string" update="true" insert="true"
     *                     length="100"
     * @return la version de SQUALE
     */
    public String getSqualeVersion()
    {
        return mSqualeVersion;
    }

    /**
     * @param pVersion la version de SQUALE
     */
    public void setSqualeVersion( String pVersion )
    {
        mSqualeVersion = pVersion;
    }

    /**
     * @return la version courante de SQUALE
     */
    public static String getCurrentSqualeVersion()
    {
        return CommonMessages.getString( "audit.squale.version" );
    }

    /**
     * @return les configurations
     * @hibernate.bag lazy="true" inverse="true" cascade="none"
     * @hibernate.key column="AuditId"
     * @hibernate.one-to-many 
     *                        class="org.squale.squalecommon.enterpriselayer.businessobject.component.AuditDisplayConfBO"
     */
    public Collection getAuditDisplayConfs()
    {
        return mAuditDisplayConfs;
    }

    /**
     * @param pAuditDisplayConfs les configurations
     */
    public void setAuditDisplayConfs( Collection pAuditDisplayConfs )
    {
        mAuditDisplayConfs = pAuditDisplayConfs;
    }

    /**
     * Ajout de l'ensemble des configurations relative à l'audit
     * 
     * @param pAuditDisplayConf l'ensemble des configurations
     */
    public void addAuditDisplayConf( AuditDisplayConfBO pAuditDisplayConf )
    {
        mAuditGrids.add( pAuditDisplayConf );
    }

    /**
     * Obtention des configurations liées à l'audit correspondant à un projet
     * 
     * @param pProject projet
     * @return les configurations correspondantes ou null si non trouvées
     */
    public AuditDisplayConfBO getDisplayConf( ProjectBO pProject )
    {
        AuditDisplayConfBO result = null;
        Iterator auditConfsIt = getAuditDisplayConfs().iterator();
        // Parcours de configuration à la recherche de celle
        // correspondant
        while ( auditConfsIt.hasNext() && ( result == null ) )
        {
            AuditDisplayConfBO auditConf = (AuditDisplayConfBO) auditConfsIt.next();
            if ( auditConf.getProject().getId() == pProject.getId() )
            {
                result = auditConf;
            }
        }
        return result;
    }

}
