//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squaleCommon\\src\\com\\airfrance\\squalecommon\\datatransfertobject\\ApplicationConfDTO.java

package com.airfrance.squalecommon.datatransfertobject.component;

import com.airfrance.squalecommon.datatransfertobject.config.ServeurDTO;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Objet Application de configuration pour administrer / enregistrer une application
 */
public class ApplicationConfDTO
    implements Serializable
{

    /**
     * Frequence de l'audit (UNITE A PRECISER)
     */
    private int mAuditFrequency;

    /**
     * Regles de purge (VALEURS A DEFINIR)
     */
    private int mResultsStorageOptions;

    /**
     * ID de l'application (au sens technique)
     * 
     * @dev-squale Pour conserver le ApplicationDTO lors de la création de l'application
     */
    private long mId;

    /**
     * Application créée. En attente de validation par un administrateur
     */
    public static final int CREATED = 0;

    /**
     * Application validée par un administrateur
     */
    public static final int VALIDATED = 1;

    /**
     * Application supprimée
     */
    public static final int DELETED = 2;

    /**
     * Status de l'application en cours valeurs CREATED, VALIDATED ou DELETED
     */
    private int mStatus;

    /**
     * couple matricule d'utilisateurs / clé des droits
     */
    private HashMap mUsers;

    /**
     * Nom de l'application
     */
    private String mName;

    /**
     * Date de début de l'application
     */
    private Date mBeginDate;

    /**
     * Date de fin de l'application
     */
    private Date mEndDate;

    /**
     * Projets associés
     */
    private Collection mProjectConfList;

    /**
     * Site d'hebergement des sources de l'application
     */
    private ServeurDTO mServeurDTO;

    /**
     * Date de derniere modification par un utilisateur quelconque
     */
    private Date mLastUpdate;

    /**
     * Nom du dernier utilisateur ayant modifié l'application
     */
    private String mLastUser;

    /**
     * Caractère publique d'une application. Une application publique est visible dans le référentiel sans être anonyme
     */
    private boolean mPublic;

    /** les derniers accès utilisateur */
    private List mAccesses;

    /**
     * Access method for the mUsers property.
     * 
     * @return the current value of the mUsers property
     * @roseuid 42CB92BA0022
     */
    public HashMap getUsers()
    {
        return mUsers;
    }

    /**
     * Sets the value of the mUsers property.
     * 
     * @param pUsers the new value of the mUsers property
     * @roseuid 42CB92BA0054
     */
    public void setUsers( HashMap pUsers )
    {
        mUsers = pUsers;
    }

    /**
     * Access method for the mName property.
     * 
     * @return the current value of the mName property
     * @roseuid 42CB92BA0126
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Sets the value of the mName property.
     * 
     * @param pName the new value of the mName property
     * @roseuid 42CB92BA0162
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * Access method for the mBeginDate property.
     * 
     * @return the current value of the mBeginDate property
     * @roseuid 42CB92BA020D
     */
    public Date getBeginDate()
    {
        return mBeginDate;
    }

    /**
     * Sets the value of the mBeginDate property.
     * 
     * @param pBeginDate the new value of the mBeginDate property
     * @roseuid 42CB92BA0235
     */
    public void setBeginDate( Date pBeginDate )
    {
        mBeginDate = pBeginDate;
    }

    /**
     * Access method for the mEndDate property.
     * 
     * @return the current value of the mEndDate property
     * @roseuid 42CB92BA02D5
     */
    public Date getEndDate()
    {
        return mEndDate;
    }

    /**
     * Sets the value of the mEndDate property.
     * 
     * @param pEndDate the new value of the mEndDate property
     * @roseuid 42CB92BA0311
     */
    public void setEndDate( Date pEndDate )
    {
        mEndDate = pEndDate;
    }

    /**
     * Access method for the mAuditFrequency property.
     * 
     * @return the current value of the mAuditFrequency property
     * @roseuid 42CB92BA039D
     */
    public int getAuditFrequency()
    {
        return mAuditFrequency;
    }

    /**
     * Sets the value of the mAuditFrequency property.
     * 
     * @param pAuditFrequency the new value of the mAuditFrequency property
     * @roseuid 42CB92BA03E3
     */
    public void setAuditFrequency( int pAuditFrequency )
    {
        mAuditFrequency = pAuditFrequency;
    }

    /**
     * Access method for the mResultsStorageOptions property.
     * 
     * @return the current value of the mResultsStorageOptions property
     * @roseuid 42CB92BB007E
     */
    public int getResultsStorageOptions()
    {
        return mResultsStorageOptions;
    }

    /**
     * Sets the value of the mResultsStorageOptions property.
     * 
     * @param pResultsStorageOptions the new value of the mResultsStorageOptions property
     * @roseuid 42CB92BB00A6
     */
    public void setResultsStorageOptions( int pResultsStorageOptions )
    {
        mResultsStorageOptions = pResultsStorageOptions;
    }

    /**
     * Access method for the mId property.
     * 
     * @return the current value of the mId property
     * @roseuid 42CB92BB013C
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Sets the value of the mId property.
     * 
     * @param pId the new value of the mId property
     * @roseuid 42CB92BB0164
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * Access method for the mProjectConf property.
     * 
     * @return the current value of the mProjectConf property
     * @roseuid 42CB92BB01FA
     */
    public Collection getProjectConfList()
    {
        return mProjectConfList;
    }

    /**
     * Sets the value of the mProjectConf property.
     * 
     * @param pProjectConfList the new value of the mProjectConf property
     * @roseuid 42CB92BB024A
     */
    public void setProjectConf( Collection pProjectConfList )
    {
        mProjectConfList = pProjectConfList;
    }

    /**
     * Access method for the mLastUpdate property.
     * 
     * @return the current value of the mLastUpdate property
     */
    public Date getLastUpdate()
    {
        return mLastUpdate;
    }

    /**
     * Sets the value of the mLastUpdate property.
     * 
     * @param pLastUpdate the new value of the mLastUpdate property
     */
    public void setLastUpdate( Date pLastUpdate )
    {
        mLastUpdate = pLastUpdate;
    }

    /**
     * @roseuid 42D4C6AB001F
     */
    public ApplicationConfDTO()
    {
        setId( -1 );
    }

    /**
     * Access method for the mStatus property.
     * 
     * @return the current value of the mStatus property
     * @roseuid 42D4C6AC017F
     */
    public int getStatus()
    {
        return mStatus;
    }

    /**
     * Sets the value of the mStatus property.
     * 
     * @param pStatus the new value of the mStatus property
     * @roseuid 42D4C6AC01A8
     */
    public void setStatus( int pStatus )
    {
        mStatus = pStatus;
    }

    /**
     * @param pPublic caractère publique
     */
    public void setPublic( boolean pPublic )
    {
        mPublic = pPublic;
    }

    /**
     * @return caractère publique
     */
    public boolean getPublic()
    {
        return mPublic;
    }

    /** indique si l'application était déja en production au moment de sa création dans SQUALE */
    private boolean isInProduction = false;

    /** indique si l'application a été développé en externe */
    private boolean externalDev = false;

    /**
     * @return le booléen indiquant si le dev a été fait en externe ou pas
     */
    public boolean getExternalDev()
    {
        return externalDev;
    }

    /**
     * @return le booléen indiquant si l'application était déjà en production au moment de sa création dans squale
     */
    public boolean getInProduction()
    {
        return isInProduction;
    }

    /**
     * @param pExternal le booléen indiquant si le dev a été fait en externe
     */
    public void setExternalDev( boolean pExternal )
    {
        externalDev = pExternal;
    }

    /**
     * @param pInProduction le booléen indiquant si l'application était déjà en production au moment de sa création dans
     *            squale
     */
    public void setInProduction( boolean pInProduction )
    {
        isInProduction = pInProduction;
    }

    /**
     * @return le dernier utilisateur ayant modifié l'application
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
     * @return le nom du serveur
     */
    public ServeurDTO getServeurDTO()
    {
        return mServeurDTO;
    }

    /**
     * @param pServeurDTO le nom du serveur
     */
    public void setServeurDTO( ServeurDTO pServeurDTO )
    {
        mServeurDTO = pServeurDTO;
    }

    /**
     * @return les accès utilisateur
     */
    public List getAccesses()
    {
        return mAccesses;
    }

    /**
     * @param pAccessDTOs les accès utilisateur
     */
    public void setAccesses( List pAccessDTOs )
    {
        mAccesses = pAccessDTOs;
    }

}
