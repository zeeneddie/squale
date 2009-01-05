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
package com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.cpptest;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.RuleSetBO;

/**
 * RuleSet de CppTest
 * 
 * @hibernate.subclass discriminator-value="CppTest"
 */
public class CppTestRuleSetBO
    extends RuleSetBO
{
    /** Nom des règles CppTest */
    private String mCppTestName;

    /**
     * @return nom
     * @hibernate.property name="cppTestName" column="CppTestName" not-null="false" type="string" unique="true"
     *                     update="true" insert="true"
     */
    public String getCppTestName()
    {
        return mCppTestName;
    }

    /**
     * @param pName nom
     */
    public void setCppTestName( String pName )
    {
        mCppTestName = pName;
    }

}
