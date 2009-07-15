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
package org.squale.squalecommon.enterpriselayer.businessobject.message;

/**
 * Message internationalisé
 * 
 * @hibernate.class table="Message" mutable="true"
 */
public class MessageBO
    implements Comparable
{
    /** Identificateur de message */
    private MessageId mId = new MessageId();

    /**
     * @return id de message
     * 
     * @hibernate.composite-id
     */
    public MessageId getId()
    {
        return mId;
    }

    /** Titre dans le cas d'une news */
    private String mTitle;

    /**
     * @return texte
     * @hibernate.property name="title" column="Title" type="string" length="4000" update="true" insert="true"
     */
    public String getTitle()
    {
        return mTitle;
    }

    /**
     * @param pTitle le nouveau titre
     */
    public void setTitle( String pTitle )
    {
        mTitle = pTitle;
    }

    /** Texte */
    private String mText;

    /**
     * @return texte
     * @hibernate.property name="text" column="Text" type="string" not-null="true" length="4000" update="true"
     *                     insert="true"
     */
    public String getText()
    {
        return mText;
    }

    /**
     * @param pText texte
     */
    public void setText( String pText )
    {
        mText = pText;
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals( Object obj )
    {
        boolean result;
        if ( obj instanceof MessageBO )
        {
            MessageBO message = (MessageBO) obj;
            result = getId().equals( message.getId() );
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
        int result;
        if ( getId() != null )
        {
            result = getId().hashCode();
        }
        else
        {
            result = super.hashCode();
        }
        return result;
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo( Object o )
    {
        int result = 0;
        if ( o instanceof MessageBO )
        {
            MessageBO message = (MessageBO) o;
            result = getId().compareTo( message.getId() );
        }
        return result;
    }

    /**
     * @param pId id
     */
    public void setId( MessageId pId )
    {
        mId = pId;
    }

    /**
     * @return langue
     */
    public String getLang()
    {
        return getId().getLang();
    }

    /**
     * @return clef
     */
    public String getKey()
    {
        return getId().getKey();
    }

    /**
     * @param pLocale langue
     */
    public void setLang( String pLocale )
    {
        getId().setLang( pLocale );
    }

    /**
     * @param pKey clef
     */
    public void setKey( String pKey )
    {
        getId().setKey( pKey );
    }

}
