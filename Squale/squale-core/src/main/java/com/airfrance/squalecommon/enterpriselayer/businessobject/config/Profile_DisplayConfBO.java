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

import com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.AbstractDisplayConfBO;

/**
 * @hibernate.class table="Profile_DisplayConfBO" lazy="true"
 */
public class Profile_DisplayConfBO
{

    /** L'id de l'objet */
    protected long mId = -1;

    /** le profil (lien inverse) */
    protected ProjectProfileBO mProfile;

    /** La configuration d'affichage associée au profil */
    protected AbstractDisplayConfBO mDisplayConf;

    /**
     * @return la configuration d'affichage
     * @hibernate.many-to-one class="com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.AbstractDisplayConfBO"
     *                        column="Profile_ConfId" not-null="false" cascade="none" outer-join="auto" update="true"
     *                        insert="true"
     */
    public AbstractDisplayConfBO getDisplayConf()
    {
        return mDisplayConf;
    }

    /**
     * @return l'id
     * @hibernate.id generator-class="native" type="long" column="ConfId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="profiledisplayconf_sequence"
     */
    public long getId()
    {
        return mId;
    }

    /**
     * @return le profil
     * @hibernate.many-to-one class="com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO"
     *                        column="ProfileId" not-null="false" cascade="none" outer-join="auto" update="true"
     *                        insert="true"
     */
    public ProjectProfileBO getProfile()
    {
        return mProfile;
    }

    /**
     * @param pConfBO la configuration d'affichage
     */
    public void setDisplayConf( AbstractDisplayConfBO pConfBO )
    {
        mDisplayConf = pConfBO;
    }

    /**
     * @param pId l'id
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * @param pProfileBO le profil
     */
    public void setProfile( ProjectProfileBO pProfileBO )
    {
        mProfile = pProfileBO;
    }

}
