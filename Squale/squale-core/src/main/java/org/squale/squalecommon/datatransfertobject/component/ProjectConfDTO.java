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
package org.squale.squalecommon.datatransfertobject.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.squale.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import org.squale.squalecommon.datatransfertobject.config.ProjectProfileDTO;
import org.squale.squalecommon.datatransfertobject.config.SourceManagementDTO;
import org.squale.squalecommon.datatransfertobject.rule.QualityGridDTO;
import org.squale.squalecommon.datatransfertobject.tag.TagDTO;

/**
 */
public class ProjectConfDTO
    implements Serializable
{

    /**
     * Id du projet
     */
    private long mId = -1;

    /**
     * @dev-squale mettre à jour les champs qui suivent : Peut contenir : - Partie JAVA : - BUILDXML chemin relatif du
     *             fichier ANT build.xml OU - WORKSPACE chemin relatif du workspace - EXCLUDEFROMCOMPIL liste des
     *             fichiers et répertoires à exclure de la compilation. - EXCLUDEFROMANALYSE liste des fichiers et
     *             répertoires à exclure de l'analyse. - CLASSPATHNAME nom du fichier classpath (".classpath" par
     *             defaut) - PARTIE C++ : - ----------------- A définir (Fichier makefile OU commande complète
     *             --------------- - - Liste des TREs à exclure / inclure de l'analyse : EXCLUDE_RESULTS - Liste des
     *             fichiers à exclure / inclure de la compilation : EXCLUDE_COMPIL
     */
    private MapParameterDTO mParameters;

    /**
     * Nom du projet
     */
    private String mName;

    /**
     * Nom de la VOB relative au projet
     */
    private String mLocation;

    /**
     * Type de l'application (Java, CPP, ...)
     */
    private ProjectProfileDTO mProfile;

    /**
     * Le statut du projet
     */
    private int mStatus;

    /**
     * Récupérateur de source
     */
    private SourceManagementDTO mSourceManager;

    /** Grille qualité */
    private QualityGridDTO mQualityGrid;
    
    /**
     * Tags concernant ce composant
     */
    private Collection<TagDTO> mTags;

    /**
     * Constructeur par defaut
     * 
     * @roseuid 42CB92BE01EA
     */
    public ProjectConfDTO()
    {

    }

    /**
     * Access method for the mPreRequisites property.
     * 
     * @return the current value of the mPreRequisites property
     * @roseuid 42CB92BE0213
     */
    public MapParameterDTO getParameters()
    {
        return mParameters;
    }

    /**
     * Sets the value of the mPreRequisites property.
     * 
     * @param pParameters the new value of the mPreRequisites property
     * @roseuid 42CB92BE0227
     */
    public void setParameters( MapParameterDTO pParameters )
    {
        mParameters = pParameters;
    }

    /**
     * Access method for the mProjectName property.
     * 
     * @return the current value of the mProjectName property
     * @roseuid 42CB92BE0277
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Sets the value of the mProjectName property.
     * 
     * @param pName the new value of the mProjectName property
     * @roseuid 42CB92BE028B
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * Access method for the mVOBName property.
     * 
     * @return the current value of the mVOBName property
     * @roseuid 42CB92BE02EF
     */
    public String getLocation()
    {
        return mLocation;
    }

    /**
     * Sets the value of the mLocation property.
     * 
     * @param pLocation the new value of the mLocation property
     * @roseuid 42CB92BE0303
     */
    public void setLocation( String pLocation )
    {
        mLocation = pLocation;
    }

    /**
     * Access method for the mId property.
     * 
     * @return the current value of the mId property
     * @roseuid 42CB92BE037B
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Sets the value of the mId property.
     * 
     * @param pId the new value of the mId property
     * @roseuid 42CB92BE03A3
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * Access method for the mProfile property.
     * 
     * @return the current value of the mProfile property
     */
    public ProjectProfileDTO getProfile()
    {
        return mProfile;
    }

    /**
     * Sets the value of the mProfile property.
     * 
     * @param pProfile the new value of the mProfile property
     */
    public void setProfile( ProjectProfileDTO pProfile )
    {
        mProfile = pProfile;
    }

    /**
     * @return grille qualité
     */
    public QualityGridDTO getQualityGrid()
    {
        return mQualityGrid;
    }

    /**
     * @param pGridDTO grille qualité
     */
    public void setQualityGrid( QualityGridDTO pGridDTO )
    {
        mQualityGrid = pGridDTO;
    }

    /**
     * @return le nom du récupérateur de ressources
     */
    public SourceManagementDTO getSourceManager()
    {
        return mSourceManager;
    }

    /**
     * @param pSourceManager le nouveau récupérateur de ressource
     */
    public void setSourceManager( SourceManagementDTO pSourceManager )
    {
        mSourceManager = pSourceManager;
    }

    /**
     * @return le statut du projet
     */
    public int getStatus()
    {
        return mStatus;
    }

    /**
     * @param pStatus le statut du projet
     */
    public void setStatus( int pStatus )
    {
        mStatus = pStatus;
    }

    /**
     * Récupère la valeur de l'attribut mTags
     * 
     * @return les tags attribués à ce composant
     */
    public Collection<TagDTO> getTags()
    {
        return mTags;
    }

    /**
     * Set la valeur de l'attribut mTags
     * 
     * @param pTags la collection de tags attribués à ce composant
     */
    public void setTags( Collection<TagDTO> pTags )
    {
        mTags = pTags;
    }

    /**
     * adds the value of pTag to the mTags list.
     * 
     * @param pTag the new value to add
     */
    public void addTag( TagDTO pTag )
    {
        if ( mTags == null )
        {
            mTags = new ArrayList<TagDTO>();
        }
        mTags.add( pTag );
    }

    /**
     * removes the value of pTag to the mTags list.
     * 
     * @param pTag the new value to add
     */
    public void removeTag( TagDTO pTag )
    {
        if ( mTags == null )
        {
            mTags = new ArrayList<TagDTO>();
        }
        mTags.remove( pTag );
    }
}
