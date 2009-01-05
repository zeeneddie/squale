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
    /** Identificateur */
    private long mId;

    /** Nom du facteur */
    private String mName;

    /**
     * la clé permettant de récupérer l'aide associée à la pratique
     */
    private String helpKey;

    /**
     * @return name
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @param pString name
     */
    public void setName( String pString )
    {
        mName = pString;
    }

    /**
     * @return id
     */
    public long getId()
    {
        return mId;
    }

    /**
     * @param pL id
     */
    public void setId( long pL )
    {
        mId = pL;
    }

    /**
     * @return la clé de l'aide
     */
    public String getHelpKey()
    {
        return helpKey;
    }

    /**
     * @param newKey la nouvelle clé
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
