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
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;

import org.squale.squalecommon.datatransfertobject.tag.TagDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.tag.TagBO;

/**
 * Composant
 */
public class ComponentDTO
    implements Serializable
{

    /**
     * ID en base du composant
     */
    private long mID = -1;

    /**
     * Nom du composant
     */
    private String mName = null;

    /**
     * Le nom complet du composant
     */
    private String mFullName = null;

    /**
     * Le numéro de ligne de la méthode dans le fichier (0 par défaut)
     */
    private String mStartLine = "0";

    /**
     * Type du composant (package, classe, méthode)
     */
    private String mType = null;

    /**
     * Nom du fichier dans le cas d'une méthode
     */
    private String mFileName = null;

    /**
     * identifiant du composant parent
     */
    private long mIDParent = -1;

    /**
     * Collection d'identifiants des composants fils
     */
    private int mNumberOfChildren = -1;

    /**
     * Technologie du composant s'il s'agit d'un projet
     */
    private String mTechnology = null;

    /**
     * Langage du composant s'il s'agit d'un projet
     */
    private String mLanguage = null;

    /** l'éventuelle justification associée au composant */
    private String justification;

    /** un booléen permettant de savoir si le composant est à exclure du plan d'action */
    private boolean excludedFromActionPlan;

    /** indique si le composant a des résultats */
    private boolean mHasResults;

    /**
     * Date de derniere modification par un utilisateur quelconque
     */
    private Date mLastUpdate;

    /**
     * Nom du dernier utilisateur ayant modifié l'application
     */
    private String mLastUser;

    /**
     * Tags concernant ce composant
     */
    private Collection<TagDTO> mTags;

    /**
     * Date of the last export. Null if not export was done. Only available for component of type application
     */
    private Date lastExportDate;

    /**
     * @return true si le composant est exclu du plan d'action
     */
    public boolean getExcludedFromActionPlan()
    {
        return excludedFromActionPlan;
    }

    /**
     * @return la justification du composant
     */
    public String getJustification()
    {
        return justification;
    }

    /**
     * @param pExcluded le booléen indiquant si il faut exclure le composant ou pas
     */
    public void setExcludedFromActionPlan( boolean pExcluded )
    {
        excludedFromActionPlan = pExcluded;
    }

    /**
     * @param pJustification la nouvelle valeur de la justification
     */
    public void setJustification( String pJustification )
    {
        justification = pJustification;
    }

    /**
     * Constructeur par défaut
     * 
     * @roseuid 42CB915F02F4
     */
    public ComponentDTO()
    {
        // initialisation des valeurs par défaut
        excludedFromActionPlan = false;
        justification = "";
    }

    /**
     * Access method for the mName property.
     * 
     * @return the current value of the mName property
     * @roseuid 42CB915F033A
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Sets the value of the mName property.
     * 
     * @param pName the new value of the mName property
     * @roseuid 42CB915F0344
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * Access method for the mType property.
     * 
     * @return the current value of the mType property
     * @roseuid 42CB9160001A
     */
    public String getType()
    {
        return mType;
    }

    /**
     * Sets the value of the mType property.
     * 
     * @param pType the new value of the mType property
     * @roseuid 42CB9160006A
     */
    public void setType( String pType )
    {
        mType = pType;
    }

    /**
     * Access method for the mFileName property.
     * 
     * @return the current value of the mFileName property
     */
    public String getFileName()
    {
        return mFileName;
    }

    /**
     * Sets the value of the mFileName property.
     * 
     * @param pFileName the new value of the mFileName property
     */
    public void setFileName( String pFileName )
    {
        mFileName = pFileName;
    }

    /**
     * Access method for the mID property.
     * 
     * @return the current value of the mID property
     * @roseuid 42CB916000A6
     */
    public long getID()
    {
        return mID;
    }

    /**
     * Sets the value of the mID property.
     * 
     * @param pID the new value of the mID property
     * @roseuid 42CB91600100
     */
    public void setID( long pID )
    {
        mID = pID;
    }

    /**
     * Permet de construire un ComponentDTO par rapport à un id et un composant. Doit etre fait pour manipuler des
     * projets et sous-projets en tant que composant.
     * 
     * @param pID ID du composant
     * @param pName Nom du composant
     * @roseuid 42D4C6A90348
     */
    public ComponentDTO( long pID, String pName )
    {
        setID( pID );
        setName( pName );
    }

    /**
     * Access method for the mIDParent property.
     * 
     * @return the current value of the mIDParent property
     */
    public long getIDParent()
    {
        return mIDParent;
    }

    /**
     * Sets the value of the mIDParent property.
     * 
     * @param pIDParent the new value of the mIDParent property
     */
    public void setIDParent( long pIDParent )
    {
        mIDParent = pIDParent;
    }

    /**
     * {@inheritDoc}
     * 
     * @param pObj le ComponentDTO à comparer
     * @return true si <code>this</code>et <code>pObj</code> sont égaux
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals( Object pObj )
    {
        boolean ret = false;
        EqualsBuilder eqBuilder = new EqualsBuilder();
        if ( pObj instanceof ComponentDTO )
        {
            ComponentDTO comp = (ComponentDTO) pObj;
            eqBuilder.append( mID, comp.getID() ); // pas beau
            eqBuilder.append( mIDParent, comp.getIDParent() );
            eqBuilder.append( mName, comp.getName() );
            eqBuilder.append( mType, comp.getType() );

            ret = eqBuilder.isEquals();
        }
        return ret;
    }

    /**
     * Constructeur
     * 
     * @param pId l'id
     * @param pIdParent l'id du parent
     * @param pName le nom
     * @param pType le type
     */
    public ComponentDTO( long pId, long pIdParent, String pName, String pType )
    {
        mID = pId;
        mIDParent = pIdParent;
        mName = pName;
        mType = pType;
    }

    /**
     * Access method for the mNumberOfChildren property.
     * 
     * @return the current value of the mNumberOfChildren property
     */
    public int getNumberOfChildren()
    {
        return mNumberOfChildren;
    }

    /**
     * Sets the value of the mNumberOfChildren property.
     * 
     * @param pNumberOfChildren the new value of the mNumberOfChildren property
     */
    public void setNumberOfChildren( int pNumberOfChildren )
    {
        mNumberOfChildren = pNumberOfChildren;
    }

    /**
     * @return technologie du composant s'il s'agit d'un projet
     */
    public String getTechnology()
    {
        return mTechnology;
    }

    /**
     * @param pTechnology du composant s'il s'agit d'un projet
     */
    public void setTechnology( String pTechnology )
    {
        mTechnology = pTechnology;
    }

    /**
     * @return langage du composant s'il s'agit d'un projet
     */
    public String getLanguage()
    {
        return mLanguage;
    }

    /**
     * @param pLanguage du composant s'il s'agit d'un projet
     */
    public void setLanguage( String pLanguage )
    {
        mLanguage = pLanguage;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     * @return le hashcode
     */
    public int hashCode()
    {
        int result;
        if ( getName() != null )
        {
            result = getName().hashCode();
        }
        else
        {
            result = super.hashCode();
        }
        return result;
    }

    /**
     * @return true si le composant a des résultats
     */
    public boolean getHasResults()
    {
        return mHasResults;
    }

    /**
     * @param pHasResults indique si le composant a des résultats
     */
    public void setHasResults( boolean pHasResults )
    {
        mHasResults = pHasResults;
    }

    /**
     * @return le nom complet
     */
    public String getFullName()
    {
        return mFullName;
    }

    /**
     * @param pFullName le nom complet
     */
    public void setFullName( String pFullName )
    {
        mFullName = pFullName;
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
     * @return la date de dernière modification
     */
    public Date getLastUpdate()
    {
        return mLastUpdate;
    }

    /**
     * @param pDate la date de dernière modification
     */
    public void setLastUpdate( Date pDate )
    {
        mLastUpdate = pDate;
    }

    /**
     * @return le numéro de ligne de la méthode dans le fichier
     */
    public String getStartLine()
    {
        return mStartLine;
    }

    /**
     * @param pLine le numéro de ligne de la méthode dans le fichier
     */
    public void setStartLine( String pLine )
    {
        mStartLine = pLine;
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

    /**
     * Verifies if the component already possesses the tag
     * 
     * @param pTag The given Tag that is compared with the components tag list
     * @return whether the comonent already possesses the Tag or not
     */
    public boolean possessTag( TagDTO pTag )
    {
        for ( TagDTO tagDTO : mTags )
        {
            if ( tagDTO.getName().equals( pTag.getName() ) )
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Getter for the attribute lastExportDate
     * 
     * @return The last export date
     */
    public Date getLastExportDate()
    {
        return lastExportDate;
    }

    /**
     * Setter for the attribute lastExportDate
     * 
     * @param pLastExportDate The new export date
     */
    public void setLastExportDate( Date pLastExportDate )
    {
        lastExportDate = pLastExportDate;
    }

}
