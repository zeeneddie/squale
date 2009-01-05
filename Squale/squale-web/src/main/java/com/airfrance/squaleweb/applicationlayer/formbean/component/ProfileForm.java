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
package com.airfrance.squaleweb.applicationlayer.formbean.component;

import java.util.Map;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Représente un profil d'utilisateur sur une application.
 * 
 * @author M400842
 */
public class ProfileForm
    extends RootForm
{

    /**
     * Nom du profil
     */
    private String mName;

    /**
     * Les droits atomiques
     */
    private Map mRights;

    /**
     * @return le nom
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @return les droits atomiques
     */
    public Map getRights()
    {
        return mRights;
    }

    /**
     * @param pName le nom du profil
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * @param pRights les droits atomiques
     */
    public void setRights( Map pRights )
    {
        mRights = pRights;
    }

}
