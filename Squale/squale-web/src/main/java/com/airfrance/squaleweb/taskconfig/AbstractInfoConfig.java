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
package com.airfrance.squaleweb.taskconfig;

/**
 * Classe définissant les infos générique nécessaire pour les champs d'une JSP
 */
public abstract class AbstractInfoConfig
{

    /** La clé du label */
    private String mKey;

    /** Le nom du champ */
    private String mProperty;

    /**
     * @return renvoi la clé pour le label
     */
    public String getKey()
    {
        return mKey;
    }

    /**
     * @return renvoi le nom du champ
     */
    public String getProperty()
    {
        return mProperty;
    }

    /**
     * @param pKey insert la clé pour le field
     */
    public void setKey( String pKey )
    {
        mKey = pKey;
    }

    /**
     * @param pProperty insert le nom du champ
     */
    public void setProperty( String pProperty )
    {
        mProperty = pProperty;
    }

    /**
     * constructeur
     * 
     * @param pKey Clé pour le label
     * @param pProperty Nom du champ
     */
    public AbstractInfoConfig( String pKey, String pProperty )
    {
        mKey = pKey;
        mProperty = pProperty;
    }

}
