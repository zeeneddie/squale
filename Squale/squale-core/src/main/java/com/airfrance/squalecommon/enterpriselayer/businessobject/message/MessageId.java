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
package com.airfrance.squalecommon.enterpriselayer.businessobject.message;

import java.io.Serializable;

/**
 * Identificateur de message
 */
public class MessageId
    implements Comparable, Serializable
{
    /** Clef */
    private String mKey;

    /** Locale */
    private String mLang;

    /**
     * @return clef
     * @hibernate.key-property column="MessageKey" type="java.lang.String"
     */
    public String getKey()
    {
        return mKey;
    }

    /**
     * @return locale
     * @hibernate.key-property length="6" type="java.lang.String" column="lang"
     */
    public String getLang()
    {
        return mLang;
    }

    /**
     * @param pKey clef
     */
    public void setKey( String pKey )
    {
        mKey = pKey;
    }

    /**
     * @param pLocale locale
     */
    public void setLang( String pLocale )
    {
        mLang = pLocale;
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo( Object o )
    {
        int result = 0;
        if ( o instanceof MessageId )
        {
            MessageId id = (MessageId) o;
            result = getLang().compareTo( id.getLang() );
            if ( result == 0 )
            {
                result = getKey().compareTo( id.getKey() );
            }
        }
        return result;
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals( Object obj )
    {
        boolean result;
        if ( obj instanceof MessageId )
        {
            MessageId id = (MessageId) obj;
            result = getLang().equals( id.getLang() ) && getKey().equals( id.getKey() );
        }
        else
        {
            result = super.equals( obj );
        }
        return result;
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return getLang().hashCode() + getKey().hashCode();
    }

}