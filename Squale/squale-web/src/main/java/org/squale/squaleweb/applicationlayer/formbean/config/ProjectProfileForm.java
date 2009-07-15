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
package org.squale.squaleweb.applicationlayer.formbean.config;

import org.squale.squalecommon.datatransfertobject.config.ProjectProfileDTO;
import org.squale.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Contient les données d'un profile Squalix
 */
public class ProjectProfileForm
    extends RootForm
{

    /** Nom du profile */
    private String mName;

    /** Id du profile en String pour le récupérer dans l'option */
    private String mId;

    /**
     * Constructeur pas défaut
     */
    public ProjectProfileForm()
    {
        mId = "-1";
    }

    /**
     * Constructeur
     * 
     * @param pProfile le DTO
     */
    public ProjectProfileForm( ProjectProfileDTO pProfile )
    {
        mName = pProfile.getName();
        mId = Long.toString( pProfile.getId() );
    }

    /**
     * Méthode d'accès à mId
     * 
     * @return l'identifiant du profile
     */
    public String getId()
    {
        return mId;
    }

    /**
     * Méthode d'accès à mName
     * 
     * @return le nom du profile
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Change la valeur de mId
     * 
     * @param pId le nouvel identifiant
     */
    public void setId( long pId )
    {
        mId = Long.toString( pId );
    }

    /**
     * Change la valeur de mId
     * 
     * @param pId le nouvel identifiant
     */
    public void setId( String pId )
    {
        mId = pId;
    }

    /**
     * Change la valeur de mName
     * 
     * @param pName le nouveau nom du profile
     */
    public void setName( String pName )
    {
        mName = pName;
    }
}
