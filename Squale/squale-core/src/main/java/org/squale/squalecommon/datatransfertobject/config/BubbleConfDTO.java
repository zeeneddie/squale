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
 */
public class BubbleConfDTO
{

    /** l'identifiant au sens technique de l'objet */
    private long mId;

    /**
     * le tre des abssices au sens métier
     */
    private String mXTre;

    /**
     * le tre des ordonnées au sens métier
     */
    private String mYTre;

    /**
     * La position de l'axe horizontal
     */
    private Long mHorizontalAxisPos;

    /**
     * la position de l'axe vertical
     */
    private Long mVerticalAxisPos;

    /**
     * @return le tre métier sur l'axe des x
     */
    public String getXTre()
    {
        return mXTre;
    }

    /**
     * @return le tre métier sur l'axe des y
     */
    public String getYTre()
    {
        return mYTre;
    }

    /**
     * @param pXtre la nouvelle valeur du tre au sens métier
     */
    public void setXTre( String pXtre )
    {
        mXTre = pXtre;
    }

    /**
     * @param pYtre la nouvelle valeur du tre au sens métier
     */
    public void setYTre( String pYtre )
    {
        mYTre = pYtre;
    }

    /**
     * @return l'id de la conf
     */
    public long getId()
    {
        return mId;
    }

    /**
     * @param pId le nouvel id
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * @return la position de l'axe horizontal
     */
    public Long getHorizontalAxisPos()
    {
        return mHorizontalAxisPos;
    }

    /**
     * @return la position de l'axe vertical
     */
    public Long getVerticalAxisPos()
    {
        return mVerticalAxisPos;
    }

    /**
     * @param pHorizontalPos la position de l'axe horizontal
     */
    public void setHorizontalAxisPos( Long pHorizontalPos )
    {
        mHorizontalAxisPos = pHorizontalPos;
    }

    /**
     * @param pVerticalPos la position de l'axe vertical
     */
    public void setVerticalAxisPos( Long pVerticalPos )
    {
        mVerticalAxisPos = pVerticalPos;
    }

}
