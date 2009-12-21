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
package org.squale.squaleweb.tagslib.security;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * Exposes the page-scoped attribute set by a {@link HasProfileTag} or a {@link NotHasProfileTag} as a scripting
 * variable.
 * 
 * @author gfouquet
 */
public class HasProfileTei
    extends TagExtraInfo
{
    private static final VariableInfo[] NO_INFO = {};

    /**
     * Returns information about the scripting variable to be created.
     */
    public VariableInfo[] getVariableInfo( TagData data )
    {

        String var = (String) data.getAttribute( "var" );

        if ( var == null )
        {
            return NO_INFO;
        }

        return new VariableInfo[] { new VariableInfo( var, "java.lang.Boolean", true, VariableInfo.AT_END ) };

    }

}
