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
//Source file: D:\\CC_VIEWS\\SQUALE_V0_0_ACT\\SQUALE\\SRC\\squaleCommon\\src\\org\\squale\\squalecommon\\datatransfertobject\\GraphDTO.java

package org.squale.squalecommon.datatransfertobject.result;

import java.io.Serializable;

/**
 * Permet de contenir une image qui peut être : - une courbe des tendances - un kiviat - un scatterplot
 */
public class GraphDTO
    implements Serializable
{

    /**
     * Identifiant de l'audit associé, -1 si pas de liasion spécifique (ex : Histo)
     */
    private long mIDAudit;

    /**
     * Identifiant du composant associé, -1 si pas de liasion spécifique (ex : Histo)
     */
    private long mIDComponent;

    /**
     * Image sous forme de tableau de bytes
     */
    private byte[] mImage;

    /**
     * Clé du type du graphe
     */
    private String mType;

    /**
     * @roseuid 42CB925E0348
     */
    public GraphDTO()
    {
    }

    /**
     * Access method for the mIDAudit property.
     * 
     * @return the current value of the mIDAudit property
     * @roseuid 42CD1F490255
     */
    public long getIDAudit()
    {
        return mIDAudit;
    }

    /**
     * Sets the value of the mIDAudit property.
     * 
     * @param pIDAudit the new value of the mIDAudit property
     * @roseuid 42CD1F490256
     */
    public void setIDAudit( long pIDAudit )
    {
        mIDAudit = pIDAudit;
    }

    /**
     * Access method for the mIDComponent property.
     * 
     * @return the current value of the mIDComponent property
     * @roseuid 42CD1F490269
     */
    public long getIDComponent()
    {
        return mIDComponent;
    }

    /**
     * Sets the value of the mIDComponent property.
     * 
     * @param pIDComponent the new value of the mIDComponent property
     * @roseuid 42CD1F490273
     */
    public void setIDComponent( long pIDComponent )
    {
        mIDComponent = pIDComponent;
    }

    /**
     * Access method for the mImage property.
     * 
     * @return image sous forme de tableaux de bytes
     */
    public byte[] getImage()
    {
        return mImage;
    }

    /**
     * Sets the value of the mImage property.
     * 
     * @param pImage image sous forme de tableau de byte
     */
    public void setImage( byte[] pImage )
    {
        mImage = pImage;
    }

    /**
     * Access method for the mType property.
     * 
     * @return clé du type de graphe
     */
    public String getType()
    {
        return mType;
    }

    /**
     * Sets the value of the mType property.
     * 
     * @param pType clé du type de graphe
     */
    public void setType( String pType )
    {
        mType = pType;
    }

}
