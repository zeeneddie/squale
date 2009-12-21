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

import java.io.IOException;

import javax.servlet.jsp.JspException;

/**
 * Outputs or sets a page-scoped attribute to true if the current user does not belong to any of a list of forbidden
 * profiles for a given application.
 * 
 * @see HasProfileTag
 * @author gfouquet
 */
public class NotHasProfileTag
    extends AbstractProfileCheckerTag
{
    private static final long serialVersionUID = -5895354345138383669L;
    /**
     * Name of page-scoped variable that should be set.
     */
    private String var;

    @Override
    public int doStartTag()
        throws JspException
    {
        boolean hasProfile = !isUserHasAnySpecifiedProfile();

        if ( shouldExposeResultAsAScopedAttribute() )
        {
            exposeResult( hasProfile );
        }
        else
        {
            outputResult( hasProfile );
        }

        return SKIP_BODY;
    }

    public void setVar( String var )
    {
        this.var = var;
    }

    /**
     * exposes the result of tag evaluation as a page-scoped attribute
     * 
     * @param hasProfile
     */
    private void exposeResult( boolean hasProfile )
    {
        pageContext.setAttribute( var, hasProfile );

    }

    /**
     * outputs the result of tag evaluation to the page
     * 
     * @param hasProfile
     * @throws JspException wraps any {@link IOException}
     */
    private void outputResult( boolean hasProfile )
        throws JspException
    {
        try
        {
            pageContext.getOut().write( Boolean.toString( hasProfile ) );
        }
        catch ( IOException e )
        {
            throw new JspException( e );
        }
    }

    /**
     * @return <code>true</code> if the result of tag evaluation should be exposed as a scoped attribute.
     */
    private boolean shouldExposeResultAsAScopedAttribute()
    {
        return var != null;
    }

}
