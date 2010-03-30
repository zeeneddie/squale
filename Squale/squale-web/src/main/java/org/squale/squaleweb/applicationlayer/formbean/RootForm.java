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
package org.squale.squaleweb.applicationlayer.formbean;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.squalecommon.datatransfertobject.component.AuditDTO;
import org.squale.squalecommon.datatransfertobject.tag.TagDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.welcom.struts.bean.WActionFormSelectable;

/**
 */
public class RootForm
    extends WActionFormSelectable
{
    /**
     * Logger for this class
     */
    private static Log log = LogFactory.getLog( RootForm.class );

    /** Regexp used to validate application or project names */
    private static String VALIDATION_REGEXP;

    static
    {
        Properties props = new Properties();
        try
        {
            props.load( RootForm.class.getResourceAsStream( "RootForm.properties" ) );
            VALIDATION_REGEXP = props.getProperty( "applicationAndProjectNameRegexp" );
        }
        catch ( IOException e )
        {
            log.fatal( "Impossible to load the \"RootForm.properties\" file.", e );
        }
    }

    /** id de l'application */
    protected String mApplicationId = "";

    /** nom de l'application */
    protected String mApplicationName = "";

    /** Le nombre de projets appartenant à l'application */
    protected String mNumberOfChildren = "";

    /** la date de l'audit courant */
    protected String mAuditDate = "";

    /** la date de l'audit courant */
    protected String mPreviousAuditDate = "";

    /** le label de l'audit courant dans le cas d'un audit de jalon ou date sinon */
    protected String mAuditName = "";

    /** le label de l'audit précédent dans le cas d'un audit de jalon ou date sinon */
    protected String mPreviousAuditName = "";

    /** id du projet */
    protected String mProjectId = "";

    /** le nom du projet */
    protected String mProjectName = "";

    /** id de l'audit courant */
    protected String mCurrentAuditId = "";

    /** id de l'audit précédent */
    protected String mPreviousAuditId = "";

    /** Indique si les audits sont comparables */
    protected boolean mComparableAudits;

    /** Version de squale pour l'audit courant */
    protected String mAuditSqualeVersion = "";

    /** Liste des tags éventuels associés à l'application */
    protected Collection<TagDTO> mTags;

    /** Map du TopMenu pour le projet courant */
    protected HashMap mTopMenu = null;

    /**
     * @return l'id de l'application
     */
    public String getApplicationId()
    {
        return mApplicationId;
    }

    /**
     * @return l'id de l'audit courant
     */
    public String getCurrentAuditId()
    {
        return mCurrentAuditId;
    }

    /**
     * @return l'id de l'audit précédent
     */
    public String getPreviousAuditId()
    {
        return mPreviousAuditId;
    }

    /**
     * @return l'id du projet
     */
    public String getProjectId()
    {
        return mProjectId;
    }

    /**
     * @param pAppliId l'id de l'application
     */
    public void setApplicationId( String pAppliId )
    {
        mApplicationId = pAppliId;
    }

    /**
     * @param pCurrentAuditId l'id de l'audit courant
     */
    public void setCurrentAuditId( String pCurrentAuditId )
    {
        mCurrentAuditId = pCurrentAuditId;
    }

    /**
     * @param pPreviousAuditId l'id de l'audit précédent
     */
    public void setPreviousAuditId( String pPreviousAuditId )
    {
        mPreviousAuditId = pPreviousAuditId;
    }

    /**
     * @param pProjectId l'id du projet
     */
    public void setProjectId( String pProjectId )
    {
        mProjectId = pProjectId;
    }

    /**
     * @return le nom de l'application
     */
    public String getApplicationName()
    {
        return mApplicationName;
    }

    /**
     * @return le nom du projet
     */
    public String getProjectName()
    {
        return mProjectName;
    }

    /**
     * @param pApplicationName le nom de l'application
     */
    public void setApplicationName( String pApplicationName )
    {
        mApplicationName = pApplicationName;
    }

    /**
     * @param pProjectName le nom du projet
     */
    public void setProjectName( String pProjectName )
    {
        mProjectName = pProjectName;
    }

    /**
     * @return la date sous la forme d'une chaine
     */
    public String getAuditDate()
    {
        return mAuditDate;
    }

    /**
     * @return le label de l'audit courant
     */
    public String getAuditName()
    {
        return mAuditName;
    }

    /**
     * @param pLabel le label de l'audit courant
     */
    public void setAuditName( String pLabel )
    {
        mAuditName = pLabel;
    }

    /**
     * @return le label de l'audit précédent
     */
    public String getPreviousAuditName()
    {
        return mPreviousAuditName;
    }

    /**
     * @param pLabel le label de l'audit précédent
     */
    public void setPreviousAuditName( String pLabel )
    {
        mPreviousAuditName = pLabel;
    }

    /**
     * @param pDate la date sous la forme d'une chaine
     */
    public void setAuditDate( String pDate )
    {
        mAuditDate = pDate;
    }

    /**
     * @return le nombre de projets de l'application
     */
    public String getNumberOfChildren()
    {
        return mNumberOfChildren;
    }

    /**
     * @param pNumber le nombre de projets de l'application
     */
    public void setNumberOfChildren( String pNumber )
    {
        mNumberOfChildren = pNumber;
    }

    /**
     * @return la date de l'audit précédent
     */
    public String getPreviousAuditDate()
    {
        return mPreviousAuditDate;
    }

    /**
     * @param pPreviousAuditDate la date de l'audit
     */
    public void setPreviousAuditDate( String pPreviousAuditDate )
    {
        mPreviousAuditDate = pPreviousAuditDate;
    }

    /**
     * Méthode utilitaire permettant de copier les données d'un form dans un autre
     * 
     * @param pForm le form dont on veut récupérer les valeurs
     */
    public void copyValues( RootForm pForm )
    {
        mApplicationId = pForm.getApplicationId();
        mApplicationName = pForm.getApplicationName();
        mProjectId = pForm.getProjectId();
        mProjectName = pForm.getProjectName();
        mCurrentAuditId = pForm.getCurrentAuditId();
        mPreviousAuditId = pForm.getPreviousAuditId();
        mAuditDate = pForm.getAuditDate();
        mPreviousAuditDate = pForm.getPreviousAuditDate();
        mAuditName = pForm.getAuditName();
        mPreviousAuditName = pForm.getPreviousAuditName();
        mNumberOfChildren = pForm.getNumberOfChildren();
        mAuditSqualeVersion = pForm.getAuditSqualeVersion();
        mComparableAudits = pForm.getComparableAudits();
        mTopMenu = pForm.getTopMenu();
    }

    /**
     * Vérifie que le nom correspond bien au pattern
     * 
     * @param pName le nom (application ou projet) à vérifier
     * @return true si le nom est valide par rapport au pattern
     */
    protected boolean isAValidName( String pName )
    {
        // Si ça matche, alors c'est un nom valide
        return Pattern.matches( VALIDATION_REGEXP, pName );
    }

    /**
     * Méthode permettant de remettre à jour les propriétés d'un form avec les audits courants
     * 
     * @param pAudits la liste des audits (actuel et précédent)
     */
    public void resetAudits( List pAudits )
    {
        // Si il n'y a pas d'audits,ou pas d'audit courant, on reinitialise les 2 audits
        if ( pAudits == null || pAudits.size() < 1 || pAudits.get( 0 ) == null )
        {
            resetAudits();
        }
        else
        {
            // ici, l'audit courant ne peut pas etre null
            AuditDTO currentAudit = ( (AuditDTO) pAudits.get( 0 ) );
            setCurrentAuditId( "" + currentAudit.getID() );
            setAuditDate( "" + currentAudit.getFormattedDate() );
            setAuditName( getAuditDate() );
            // Formatage du nom dans le cas où il s'agit d'un audit de jalon
            if ( currentAudit.getType().equals( AuditBO.MILESTONE ) )
            {
                setAuditName( currentAudit.getName() + " (" + getAuditDate() + ")" );
            }
            // Gere l'audit précédent
            if ( pAudits.size() < 2 || pAudits.get( 1 ) == null )
            {
                // L'audit présédant n'existe pas
                setPreviousAuditId( "" );
                setPreviousAuditDate( "" );
                setPreviousAuditName( "" );
            }
            else
            {
                AuditDTO previousAudit = ( (AuditDTO) pAudits.get( 1 ) );
                setPreviousAuditId( "" + previousAudit.getID() );
                setPreviousAuditDate( "" + previousAudit.getFormattedDate() );
                setPreviousAuditName( getPreviousAuditDate() );
                // Formatage du nom dans le cas où il s'agit d'un audit de jalon
                if ( previousAudit.getType().equals( AuditBO.MILESTONE ) )
                {
                    setPreviousAuditName( previousAudit.getName() + " (" + getPreviousAuditDate() + ")" );
                }
            }
        }

    }

    /**
     * Efface les données pour les audits
     */
    private void resetAudits()
    {
        // L'audit courant
        setCurrentAuditId( "" );
        setAuditDate( "" );
        setAuditName( "" );
        // L'audit précédent
        setPreviousAuditId( "" );
        setPreviousAuditDate( "" );
        setPreviousAuditName( "" );
        setAuditSqualeVersion( "" );
    }

    /**
     * Efface les données de l'audit précédent
     */
    public void resetCache()
    {
        // L'application
        setApplicationId( "" );
        setApplicationName( "" );
        setNumberOfChildren( "" );
        setAuditSqualeVersion( "" );
        // L'id du projet
        setProjectId( "" );
        setProjectName( "" );
        // Le menu Top du projet
        setTopMenu( null );
        // Les audits
        resetAudits();
    }

    /**
     * @return la version de squale
     */
    public String getAuditSqualeVersion()
    {
        return mAuditSqualeVersion;
    }

    /**
     * @param pVersion la version de SQUALE
     */
    public void setAuditSqualeVersion( String pVersion )
    {
        mAuditSqualeVersion = pVersion;
    }

    /**
     * Access method for the mTags property.
     * 
     * @return l'utilisateur ayant fait la dernière modification
     */
    public Collection<TagDTO> getTags()
    {
        return mTags;
    }

    /**
     * Sets the value of the mTags property.
     * 
     * @param pTags l'utilisateur ayant fait la dernière modification
     */
    public void setTags( Collection<TagDTO> pTags )
    {
        mTags = pTags;
    }

    /**
     * @return le TopMenu du projet
     */
    public HashMap getTopMenu()
    {
        return mTopMenu;
    }

    /**
     * @param pTopMenu le TopMenu du projet
     */
    public void setTopMenu( HashMap pTopMenu )
    {
        mTopMenu = pTopMenu;
    }

    /**
     * @return true si les audits sont comparables
     */
    public boolean getComparableAudits()
    {
        return mComparableAudits;
    }

    /**
     * @param pComparable si les audits sont comparables
     */
    public void setComparableAudits( boolean pComparable )
    {
        mComparableAudits = pComparable;
    }

}
