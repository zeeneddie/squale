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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.IterationTag;

import org.junit.Before;
import org.junit.Test;

public class IfHasProfileTagTest
    extends AbstractProfileCheckerTagTest
{
    private AbstractProfileCheckerTag testedObject;

    @Before
    public void setUpTestedObject()
    {
        testedObject = new IfHasProfileTag();
        testedObject.setPageContext( pageContext );
        testedObject.setApplicationId( currentApplicationId );
    }

    @Test
    public void shouldSkipBodyIfUserNotInProfile()
        throws JspException
    {
        // given
        aReaderForCurrentApplicationIsLogged();
        managerProfileIsRequired();

        // when
        int res = testedObject.doStartTag();

        // then
        assertThat( res, is( IterationTag.SKIP_BODY ) );
    }

    @Test
    public void shouldRenderBodyIfUserInProfile()
        throws JspException
    {
        // given
        aManagerForCurrentApplicationIsLogged();
        managerProfileIsRequired();

        // when
        int res = testedObject.doStartTag();

        // then
        assertThat( res, is( IterationTag.EVAL_BODY_INCLUDE ) );
    }

    @Test
    public void shouldRenderBodyIfUserInOneOfProfiles()
        throws JspException
    {
        // given
        aManagerForCurrentApplicationIsLogged();
        managerOrAdminProfileIsRequired();

        // when
        int res = testedObject.doStartTag();

        // then
        assertThat( res, is( IterationTag.EVAL_BODY_INCLUDE ) );
    }

    protected AbstractProfileCheckerTag getTestedObject()
    {
        return testedObject;
    }

}
