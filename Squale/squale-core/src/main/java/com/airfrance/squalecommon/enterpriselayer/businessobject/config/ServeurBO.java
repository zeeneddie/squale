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
package com.airfrance.squalecommon.enterpriselayer.businessobject.config;

/**
 * Serveur d'exécution de Squalix
 * 
 * @hibernate.class table="Serveur" lazy="true"
 */
public class ServeurBO
{

    /**
     * Id du serveur
     */
    private long mServeurId = -1;

    /**
     * Nom du serveur
     */
    private String mName;

    /**
     * @return l'id du serveur
     * @hibernate.id generator-class="assigned" name="id" column="ServeurId" type="long" length="19" unsaved-value="-1"
     */
    public long getServeurId()
    {
        return mServeurId;
    }

    /**
     * @return le nom du serveur
     * @hibernate.property name="name" column="Name" type="string" not-null="true" unique="true"
     *                     update="true" insert="true"
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
