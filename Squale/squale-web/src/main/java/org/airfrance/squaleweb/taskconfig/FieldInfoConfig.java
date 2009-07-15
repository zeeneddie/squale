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
 * Info nécessaire pour un field d'une JSP
 */
public class FieldInfoConfig
    extends AbstractInfoConfig
{

    /** Taille du field */
    private String mSize;

    /** Champs obligatoire ? */
    private String mIsRequired;

    /** Le type de champs */
    private String mType;

    /**
     * constructeur
     * 
     * @param pKey Clé pour le label
     * @param pProperty Nom du champ
     * @param pIsRequired Champ obligatoire ?
     * @param pSize Taille du field
     * @param pType the type
     */
    public FieldInfoConfig( String pKey, String pProperty, String pIsRequired, String pSize, String pType )
    {
        super( pKey, pProperty );
        mSize = pSize;
        mIsRequired = pIsRequired;
        mType = pType;
    }

    /**
     * @return renvoi la taille du field
     */
    public String getSize()
    {
        return mSize;
    }

    /**
     * @param pSize insere la taille du field
     */
    public void setSize( String pSize )
    {
        mSize = pSize;
    }

    /**
     * @return indique si le champ est obligatoire
     */
    public String getIsRequired()
    {
        return mIsRequired;
    }

    /**
     * @param pIsRequired insere si le champ est obligatoire
     */
    public void setIsRequired( String pIsRequired )
    {
        mIsRequired = pIsRequired;
    }

    /**
     * @return retourne le type du field
     */
    public String getType()
    {
        return mType;
    }

    /**
     * @param pType insert le type du field
     */
    public void setType( String pType )
    {
        mType = pType;
    }

}
