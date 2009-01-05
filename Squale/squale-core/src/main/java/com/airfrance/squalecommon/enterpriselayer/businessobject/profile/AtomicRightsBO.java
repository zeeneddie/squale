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
//Source file: D:\\cc_views\\squale_v0_0_act_M400843\\squale\\src\\squaleCommon\\src\\com\\airfrance\\squalecommon\\enterpriselayer\\businessobject\\profile\\AtomicRightsBO.java

package com.airfrance.squalecommon.enterpriselayer.businessobject.profile;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Définit un droit atomique au sens Welcom, tel qu'utilisé dans les tags JSP pour les propriétés accesKey,...
 * 
 * @author m400842
 * @version 1.0
 * @hibernate.class table="AtomicRights" mutable="true"
 */
public class AtomicRightsBO
    implements Serializable
{

    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId;

    /**
     * Nom du droit atomique
     */
    private String mName;

    /**
     * Constructeur par défaut
     */
    public AtomicRightsBO()
    {
        mId = -1;
    }

    /**
     * Constructeur complet. Crée un nouveau droit atomique.
     * 
     * @param pName Nom du droit.
     * @roseuid 42AFE9C0006C
     */
    public AtomicRightsBO( final String pName )
    {
        mId = -1;
        mName = pName;
    }

    /**
     * Access method for the mName property.
     * 
     * @return the current value of the mName property
     * @hibernate.property name="name" column="Name" type="string" update="true" insert="true"
     * @roseuid 42BACED70267
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Sets the value of the mName property.
     * 
     * @param pName the new value of the mName property
     * @roseuid 42BACED70268
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * Access method for the mId property.
     * 
     * @return the current value of the mId property Note: unsaved-value An identifier property value that indicates
     *         that an instance is newly instantiated (unsaved), distinguishing it from transient instances that were
     *         saved or loaded in a previous session. If not specified you will get an exception like this: another
     *         object associated with the session has the same identifier
     * @hibernate.id generator-class="native" type="long" column="AtomicRightsId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="rigth_sequence"
     * @roseuid 42BFEDAA0003
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Sets the value of the mId property.
     * 
     * @param pId the new value of the mId property
     * @roseuid 42BFEDAA0012
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals( Object pObj )
    {
        boolean ret = false;
        AtomicRightsBO atomicRights = null;
        if ( pObj instanceof AtomicRightsBO )
        {
            atomicRights = (AtomicRightsBO) pObj;
            EqualsBuilder equalsBuilder = new EqualsBuilder();
            equalsBuilder.append( mName, atomicRights.getName() );
            ret = equalsBuilder.isEquals();
        }
        return ret;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        HashCodeBuilder hashBuilder = new HashCodeBuilder();
        hashBuilder.append( mName );
        return hashBuilder.toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        ToStringBuilder stringBuilder = new ToStringBuilder( this );
        stringBuilder.append( "Name", mName );
        return stringBuilder.toString();
    }

}
