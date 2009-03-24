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
package com.airfrance.squaleweb.applicationlayer.formbean.reference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

import com.airfrance.squaleweb.applicationlayer.formbean.ActionIdFormSelectable;

/**
 * Contient une référence du portail Squale.
 * 
 * @author M400842
 */
public class ReferenceForm
    extends ActionIdFormSelectable
{

    // Constantes pour l'affichage

    /**
     * Masqué
     */
    public final static String HIDDEN = "reference.hidden";

    /**
     * Affiché
     */
    public final static String DISPLAYED = "reference.displayed";

    /**
     * Caractère public
     */
    private boolean mPublic;

    /** le type de l'audit */
    private String mAuditType;

    /**
     * Liste des facteurs du referentiel
     */
    private Collection mFactors = new ArrayList();

    /**
     * Liste des données de volumétrie du référentiel
     */
    private TreeMap<String,Integer> mVolumetry = new TreeMap<String,Integer>();
    
    /**
     * Technologie de la référence
     */
    private String mTechnology;

    /**
     * Nom du la référence
     */
    private String mName = "";

    /**
     * Spécifie si la référence est cachée
     */
    private boolean mHidden = false;
    
    /** Attribut inutilisé pour éviter des erreurs **/
    private String mValue="";
    
    /**
     * @return une valeur vide
     */
    public String getValue()
    {
    	return mValue;
    }

    /**
     * @return le nom
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @return la technologie
     */
    public String getTechnology()
    {
        return mTechnology;
    }

    /**
     * @param pName le nom
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * @param pTechnology la technologie
     */
    public void setTechnology( String pTechnology )
    {
        mTechnology = pTechnology;
    }

    /**
     * @return l'état de validation de la référence
     */
    public boolean isHidden()
    {
        return mHidden;
    }

    /**
     * @param pHidden indique si la référence est masquée ou pas
     */
    public void setHidden( boolean pHidden )
    {
        mHidden = pHidden;
    }

    /**
     * @return Valeur courante de mFactors
     */
    public Collection getFactors()
    {
        return mFactors;
    }

    /**
     * @param pPublic caractère public
     */
    public void setPublic( boolean pPublic )
    {
        mPublic = pPublic;
    }

    /**
     * @return caractère public
     */
    public boolean isPublic()
    {
        return mPublic;
    }

    /**
     * @param pFactor facteur
     */
    public void addFactor( Object pFactor )
    {
        mFactors.add( pFactor );

    }

    /**
     * @return le type de l'audit
     */
    public String getAuditType()
    {
        return mAuditType;
    }

    /**
     * @param pType le type de l'audit
     */
    public void setAuditType( String pType )
    {
        mAuditType = pType;
    }

    /**
     * string décrivant l'état, sert juste pour l'affichage
     */
    private String mState = "";

    /**
     * @param pState le nouvel état
     */
    public void setState( String pState )
    {
        mState = pState;
    }

    /**
     * Méthode renvoyant la string décrivant l'état, sert juste pour l'affichage
     * 
     * @return l'état
     */
    public String getState()
    {
        return mState;
    }
    
    /**
     * Méthode renvoyant les données de volumétrie
     * @return les données (Nb Classes,Méthodes,etc...)
     */
    public TreeMap getVolumetry()
    {
    	return mVolumetry;
    }
    
    public void setVolumetry(TreeMap pVolumetry)
    {
    	mVolumetry = pVolumetry;
    }
    
    public Integer getVolume(String pKey)
    {
    	Integer volume = mVolumetry.get(pKey);
    	return volume;
    }
    
}
