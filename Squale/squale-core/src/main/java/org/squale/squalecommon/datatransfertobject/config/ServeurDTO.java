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
package org.squale.squalecommon.datatransfertobject.config;

/**
 * Serveur d'exécution de Squalix
 */
public class ServeurDTO
{

    /**
     * Id du serveur
     */
    private long mServeurId;

    /**
     * Nom du serveur
     */
    private String mName;

    /**
     * @return l'id du serveur
     */
    public long getServeurId()
    {
        return mServeurId;
    }

    /**
     * @return le nom du serveur
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @param pId l'id du serveur
     */
    public void setServeurId( long pId )
    {
        mServeurId = pId;
    }

    /**
     * @param pName le nom du serveur
     */
    public void setName( String pName )
    {
        mName = pName;
    }

}
