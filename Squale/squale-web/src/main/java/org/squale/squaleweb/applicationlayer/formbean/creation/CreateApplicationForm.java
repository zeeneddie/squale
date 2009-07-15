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
package org.squale.squaleweb.applicationlayer.formbean.creation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import org.squale.squalecommon.datatransfertobject.component.AuditDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squaleweb.applicationlayer.formbean.RootForm;
import org.squale.squaleweb.applicationlayer.formbean.access.AccessListForm;
import org.squale.squaleweb.applicationlayer.formbean.component.AuditForm;
import org.squale.squaleweb.applicationlayer.formbean.config.ServeurForm;

/**
 * Form bean for a Struts application.
 * 
 * @version 1.0
 * @author
 */
public class CreateApplicationForm
    extends RootForm
{

    /** Les accès utilisateur */
    private AccessListForm mAccessListForm;

    /**
     * Précise le site auquel appartient l'application
     */
    private ServeurForm mServeurForm = new ServeurForm();

    /**
     * Précise si les audits sont réalisés uniquement sur demande
     */
    private boolean mMilestone = false;

    /**
     * L'audit de jalon si l'application en a un programmé
     */
    private AuditForm mMilestoneAudit = new AuditForm();

    /**
     * Délai de purge par défaut
     */
    public static final int DEFAULT_PURGE_DELAY = 180;

    /**
     * Délai de purge par défaut
     */
    public static final int DEFAULT_AUDIT_FREQUENCY = 15;

    /**
     * Délai entre deux purges de l'application
     */
    private int mPurgeDelay = DEFAULT_PURGE_DELAY;

    /**
     * Délai entre deux purges de l'application
     */
    private int mAuditFrequency = DEFAULT_AUDIT_FREQUENCY;

    /**
     * Status de l'application
     */
    private int mStatus;

    /**
     * Liste des projets de l'application
     */
    private List mProjects = new ArrayList();

    /**
     * Liste des droits sur l'application
     */
    private Map mRights = new HashMap();

    /**
     * Caractère publique
     */
    private boolean mPublic;

    /** indique si l'application était déja en production au moment de sa création dans SQUALE */
    private boolean isInProduction = false;

    /** indique si l'application a été développé en externe */
    private boolean externalDev = false;

    /** Date de la dernière modification */
    private Date mLastUpdate;

    /** L'utilisateur ayant fait la dernière modification */
    private String mLastUser;

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
    public boolean getIsInProduction()
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
    public void setIsInProduction( boolean pInProduction )
    {
        isInProduction = pInProduction;
    }

    /**
     * @return le délai de purge
     */
    public int getPurgeDelay()
    {
        return mPurgeDelay;
    }

    /**
     * @param pPurgeDelay le délai de purge (en jour)
     */
    public void setPurgeDelay( int pPurgeDelay )
    {
        mPurgeDelay = pPurgeDelay;
    }

    /**
     * @return la fréquence d'audit en jours
     */
    public int getAuditFrequency()
    {
        return mAuditFrequency;
    }

    /**
     * @param pAuditFrequency la fréquence d'audit en jours
     */
    public void setAuditFrequency( int pAuditFrequency )
    {
        mAuditFrequency = pAuditFrequency;
    }

    /**
     * @return la valeur de mMilestone.
     */
    public boolean isMilestone()
    {
        return mMilestone;
    }

    /**
     * @param pMilestone la nouvelle valeur de mMilestone.
     */
    public void setMilestone( boolean pMilestone )
    {
        mMilestone = pMilestone;
    }

    /**
     * @return la liste des projets de l'application
     */
    public List getProjects()
    {
        return mProjects;
    }

    /**
     * @param pProjects la liste des projets de l'application
     */
    public void setProjects( List pProjects )
    {
        mProjects = pProjects;
    }

    /**
     * @return la map des droits utilisateur / droits
     */
    public Map getRights()
    {
        return mRights;
    }

    /**
     * @param pMap la map des droits utilisateur / droits
     */
    public void setRights( Map pMap )
    {
        mRights = pMap;
    }

    /**
     * @param pStatus status
     */
    public void setStatus( int pStatus )
    {
        mStatus = pStatus;
    }

    /**
     * @return status
     */
    public int getStatus()
    {
        return mStatus;
    }

    /**
     * Initialisation des champs Cette méthode permet de réinitialiser les champs autrement que par la méthode reset
     * classique car le formbean est utilisé en session et sous la forme d'un wizard
     */
    public void resetFields()
    {
        setApplicationId( "-1" );
        setApplicationName( null );
        mMilestone = false;
        mPurgeDelay = DEFAULT_PURGE_DELAY;
        mAuditFrequency = DEFAULT_AUDIT_FREQUENCY;
        mStatus = 0;
        mProjects = new ArrayList();
        mRights = new HashMap();
        mMilestoneAudit = new AuditForm();
        mMilestoneAudit.setType( AuditBO.MILESTONE );
        mServeurForm = null;
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
    public boolean isPublic()
    {
        return mPublic;
    }

    /**
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    public void reset( ActionMapping arg0, HttpServletRequest arg1 )
    {
        super.reset( arg0, arg1 );
        setMilestone( false );
        setPublic( false );
        setExternalDev( false );
        setIsInProduction( false );
    }

    /**
     * @return l'audit de jalon programmé
     */
    public AuditForm getMilestoneAudit()
    {
        return mMilestoneAudit;
    }

    /**
     * @param pAuditForm l'audit de jalon programmé
     */
    public void setMilestoneAudit( AuditForm pAuditForm )
    {
        mMilestoneAudit = pAuditForm;
    }

    /**
     * Remet à zéro l'audit de jalon.
     */
    public void resetMilestoneAudit()
    {
        mMilestoneAudit = new AuditForm();

    }

    /**
     * @param pAuditDTO l'audit sous forme dto
     */
    public void setMilestoneAudit( AuditDTO pAuditDTO )
    {
        AuditForm audit = new AuditForm( pAuditDTO );
        setMilestoneAudit( audit );
    }

    /**
     * @return la date de la dernière modification
     */
    public Date getLastUpdate()
    {
        return mLastUpdate;
    }

    /**
     * @param pDate la date de la dernière modification
     */
    public void setLastUpdate( Date pDate )
    {
        mLastUpdate = pDate;
    }

    /**
     * @return l'utilisateur ayant fait la dernière modification
     */
    public String getLastUser()
    {
        return mLastUser;
    }

    /**
     * @param pMatricule l'utilisateur ayant fait la dernière modification
     */
    public void setLastUser( String pMatricule )
    {
        mLastUser = pMatricule;
    }

    /**
     * Méthode de validation pour vérifier que le nom mis à l'application ne contient pas de caractères spéciaux. La
     * liste des caractères spéciaux est défini par le pattern situé dans la classe mère
     * 
     * @param pMapping le mapping hibernate
     * @param pRequest la requete
     */
    public void wValidate( ActionMapping pMapping, HttpServletRequest pRequest )
    {
        if ( getApplicationName().trim().length() == 0 )
        {
            addError( "applicationName", new ActionError( "error.application.name.empty" ) );
        }
        else if ( !isAValidName( getApplicationName() ) )
        {
            addError( "applicationName", new ActionError( "error.name.containsInvalidCharacter" ) );
        }
    }

    /**
     * @return le formulaire du Serveur
     */
    public ServeurForm getServeurForm()
    {
        return mServeurForm;
    }

    /**
     * @param pServeurForm le formulaire du Serveur
     */
    public void setServeurForm( ServeurForm pServeurForm )
    {
        mServeurForm = pServeurForm;
    }

    /**
     * @param pForm les accès
     */
    public void setAccessListForm( AccessListForm pForm )
    {
        mAccessListForm = pForm;
    }

    /**
     * @return les accès utilisateur
     */
    public AccessListForm getAccessListForm()
    {
        return mAccessListForm;
    }

}
