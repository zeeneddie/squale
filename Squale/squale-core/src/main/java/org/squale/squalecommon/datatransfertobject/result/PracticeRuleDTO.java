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
//Source file: D:\\CC_VIEWS\\SQUALE_V0_0_ACT\\SQUALE\\SRC\\squaleCommon\\src\\org\\squale\\squalecommon\\datatransfertobject\\PracticeRuleDTO.java

package org.squale.squalecommon.datatransfertobject.result;

import java.io.Serializable;
import java.util.Collection;

/**
 */
public class PracticeRuleDTO
    implements Serializable
{

    /**
     * Clé du TRE associé
     */
    private String mKeyTRE;

    /**
     * Tableau des valeurs de pondération
     */
    private Collection mCoefficients;

    /**
     * Constructeur par defaut
     * 
     * @roseuid 42CB925F0137
     */
    public PracticeRuleDTO()
    {
    }

    /**
     * Access method for the mCoefficients property.
     * 
     * @return the current value of the mCoefficients property
     * @roseuid 42CB925F0169
     */
    public Collection getCoefficients()
    {
        return mCoefficients;
    }

    /**
     * Sets the value of the mCoefficients property.
     * 
     * @param pCoefficients the new value of the mCoefficients property
     * @roseuid 42CB925F0187
     */
    public void setCoefficients( Collection pCoefficients )
    {
        mCoefficients = pCoefficients;
    }

    /**
     * Access method for the mKeyTRE property.
     * 
     * @return the current value of the mKeyTRE property
     * @roseuid 42CB925F01C3
     */
    public String getKeyTRE()
    {
        return mKeyTRE;
    }

    /**
     * Sets the value of the mKeyTRE property.
     * 
     * @param pKeyTRE the new value of the mKeyTRE property
     * @roseuid 42CB925F01F5
     */
    public void setKeyTRE( String pKeyTRE )
    {
        mKeyTRE = pKeyTRE;
    }
}
