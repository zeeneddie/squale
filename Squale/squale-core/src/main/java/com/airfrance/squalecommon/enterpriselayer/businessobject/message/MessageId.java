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
     * @hibernate.property not-null="true"
     */
    public String getKey()
    {
        return mKey;
    }

    /**
     * @return locale
     * @hibernate.property not-null="true" length="6"
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