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

import javax.servlet.jsp.JspException;

/**
 * This tag renders its body-content if the current user's profile for a given application does not belong to a
 * determined list.
 * 
 * @author gfouquet
 */
public class IfNotHasProfileTag
    extends AbstractProfileCheckerTag
{

    private static final long serialVersionUID = -9125009672179532937L;

    @Override
    public int doStartTag()
        throws JspException
    {
        return isUserHasAnySpecifiedProfile() ? SKIP_BODY : EVAL_BODY_INCLUDE;
    }

}
