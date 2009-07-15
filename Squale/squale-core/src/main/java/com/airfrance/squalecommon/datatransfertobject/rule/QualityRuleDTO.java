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
package com.airfrance.squalecommon.datatransfertobject.rule;

/**
 * DTO d'une règle qualité
 */
public class QualityRuleDTO
    implements Comparable
{
    /** Technical Id */
    private long mId;

    /** Rule name */
    private String mName;

    /**
     * The help key (Permit to recover the help associate to the practice)
     */
    private String helpKey;

    /**
     * The getter method for the attribute name
     * 
     * @return name The name of rule
     */
    public String getName()
    {
        return mName;
    }

    /**
     * The setter method for the attribute name
     * 
     * @param pString name The new name of the rule
     */
    public void setName( String pString )
    {
        mName = pString;
    }

    /**
     * Getter method for the attribute id
     * 
     * @return id The id of the rule
     */
    public long getId()
    {
        return mId;
    }
    
    /**
     * Setter method for the attribute id
     * 
     * @param id The new id of the rule
     */
    public void setId( long id )
    {
        mId = id;
    }

    /**
     * Getter method for the attribute helpKey
     * 
     * @return The help key
     */
    public String getHelpKey()
    {
        return helpKey;
    }

    /**
     * Setter method for the attribute helpKey
     * 
     * @param newKey The new help key
     */
    public void setHelpKey( String newKey )
    {
        helpKey = newKey;
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo( Object o )
    {
        int result = 0;
        if ( o instanceof QualityRuleDTO )
        {
            QualityRuleDTO rule = (QualityRuleDTO) o;
            if ( ( rule.getName() != null ) && ( getName() != null ) )
            {
                result = getName().compareTo( rule.getName() );
            }
        }
        return result;
    }

   

}
