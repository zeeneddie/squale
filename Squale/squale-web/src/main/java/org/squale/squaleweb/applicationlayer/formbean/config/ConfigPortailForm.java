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
package com.airfrance.squaleweb.applicationlayer.formbean.config;

import java.util.List;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Configuration du portail par le choix des types de récupération des sources disponibles (CVS, FTP, ClearCase,...) et
 * par le choix des profiles possibles pour les projets (Java, C++,...)
 */
public class ConfigPortailForm
    extends RootForm
{

    /**
     * Liste des types de récupération des sources
     */
    private List mSourceManagements;

    /**
     * Liste des profiles disponibles
     */
    private List mProfiles;

    /**
     * @return la liste des profiles
     */
    public List getProfiles()
    {
        return mProfiles;
    }

    /**
     * @param pProfiles la liste des profiles
     */
    public void setProfiles( List pProfiles )
    {
        mProfiles = pProfiles;
    }

    /**
     * @return la liste des types de récupération des sources
     */
    public List getSourceManagements()
    {
        return mSourceManagements;
    }

    /**
     * @param pSourceManagements la liste des types de récupération des sources
     */
    public void setSourceManagements( List pSourceManagements )
    {
        mSourceManagements = pSourceManagements;
    }

}
