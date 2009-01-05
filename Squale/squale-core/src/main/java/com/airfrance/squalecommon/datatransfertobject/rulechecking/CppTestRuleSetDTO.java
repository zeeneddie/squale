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
package com.airfrance.squalecommon.datatransfertobject.rulechecking;

/**
 * DTO pour CppTestRuleSet
 */
public class CppTestRuleSetDTO
    extends RuleSetDTO
{

    /** Nom des r�gles CppTest */
    private String mCppTestName;

    /**
     * @return nom
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
