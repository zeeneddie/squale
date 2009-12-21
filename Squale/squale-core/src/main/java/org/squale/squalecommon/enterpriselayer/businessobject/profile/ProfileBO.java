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
//Source file: D:\\cc_views\\squale_v0_0_act_M400843\\squale\\src\\squaleCommon\\src\\org\\squale\\squalecommon\\enterpriselayer\\businessobject\\profile\\ProfileBO.java

package org.squale.squalecommon.enterpriselayer.businessobject.profile;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Décrit un type de comportement d'utilisateur.<br>
 * Trois profils sont identifiés actuellement : utilisateur, gestionnaire (administrateur de projet), administrateur
 * (super adminitrateur). + profil defaut
 * 
 * @author m400842
 * @hibernate.class table="ProfileBO" mutable="true"
 */
public class ProfileBO
    implements Serializable
{

    /** Nom du profil administrateur */
    public static final String ADMIN_PROFILE_NAME = "bo.profile.name.admin";

    /** Nom du profil manager */
    public static final String MANAGER_PROFILE_NAME = "bo.profile.name.manager";

    /** Name of auditor profile */
    public static final String AUDITOR_PROFILE_NAME = "bo.profile.name.auditor";

    /** Nom du profil reader */
    public static final String READER_PROFILE_NAME = "bo.profile.name.reader";

    /** Nom du profil default */
    public static final String DEFAULT_PROFILE_NAME = "bo.profile.name.default";

    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId;

    /**
     * Nom du profil
     */
    private String mName;

    /**
     * Liste des actions possibles sur les droits Welcom.<br>
     * Les droits Welcom sont les clés, et l'action la valeur : READONLY, READWRITE, NONE.
     */
    private Map mRights;

    /**
     * Constructeur complet. Crée un nouveau profil.
     * 
     * @param pName Nom du profil
     * @param pRights liste des droits
     * @roseuid 42AFECAF006B
     */
    public ProfileBO( final String pName, final Map pRights )
    {
        mId = -1;
        mName = pName;
        mRights = pRights;
    }

    /**
     * Access method for the mName property.
     * 
     * @return the current value of the mName property
     * @hibernate.property name="name" column="Name" type="string" update="true" insert="true"
     * @roseuid 42BACED70352
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Sets the value of the mName property.
     * 
     * @param pName the new value of the mName property
     * @roseuid 42BACED70353
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * Access method for the mRights property.
     * 
     * @return the current value of the mRights property
     * @hibernate.map table="Profile_Rights" lazy="true" cascade="none" sort="unsorted" //name="rights"
     * @hibernate.index-many-to-many column="AtomicRightsId"
     *                               class="org.squale.squalecommon.enterpriselayer.businessobject.profile.AtomicRightsBO"
     * @hibernate.key column="ProfileId"
     * @hibernate.element column="Rights_Value" type="string" not-null="false" unique="false"
     * @roseuid 42BACED70355
     */
    public Map getRights()
    {
        return mRights;
    }

    /**
     * Sets the value of the mRights property.
     * 
     * @param pRights the new value of the mRights property
     * @roseuid 42BACED70361
     */
    public void setRights( Map pRights )
    {
        mRights = pRights;
    }

    /**
     * Access method for the mId property.
     * 
     * @return the current value of the mId property Note: unsaved-value An identifier property value that indicates
     *         that an instance is newly instantiated (unsaved), distinguishing it from transient instances that were
     *         saved or loaded in a previous session. If not specified you will get an exception like this: another
     *         object associated with the session has the same identifier
     * @hibernate.id generator-class="native" type="long" column="ProfileId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="profile_sequence"
     * @roseuid 42BFEDEC02F2
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Sets the value of the mId property.
     * 
     * @param pId the new value of the mId property
     * @roseuid 42BFEDEC0302
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * Constructeur par défaut.
     * 
     * @roseuid 42CB8A7103BF
     */
    public ProfileBO()
    {
        mId = -1;
        mRights = new HashMap();
    }
}
