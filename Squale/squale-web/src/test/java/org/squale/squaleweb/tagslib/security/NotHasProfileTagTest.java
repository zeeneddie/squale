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

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.junit.Before;
import org.junit.Test;

public class NotHasProfileTagTest
    extends AbstractProfileCheckerTagTest
{
    private NotHasProfileTag testedObject;

    /**
     * mocked jsp output.
     */
    private final JspWriter out = mock( JspWriter.class );

    @Override
    protected AbstractProfileCheckerTag getTestedObject()
    {
        return testedObject;
    }


    @Before
    public void setUpTestedObject()
    {
        testedObject = new NotHasProfileTag();
        testedObject.setPageContext( pageContext );
        testedObject.setApplicationId( currentApplicationId );
    }

    @Before
    public void setUpOutputStream()
    {
        when( pageContext.getOut() ).thenReturn( out );
    }

    @Test
    public void shouldOutputTrueWhenUserHasProfile()
        throws JspException, IOException
    {
        // given
        aReaderForCurrentApplicationIsLogged();
        managerProfileIsRequired();

        // when
        testedObject.doStartTag();

        // then
        verify( out ).write( "true" );
    }

    @Test
    public void shouldSetPageAttributeWhenVarIsSpecified()
        throws JspException
    {
        // given
        aReaderForCurrentApplicationIsLogged();
        managerProfileIsRequired();
        testedObject.setVar( "expectedAttribute" );

        // when
        testedObject.doStartTag();

        // then
        verify( pageContext ).setAttribute( eq( "expectedAttribute" ), anyBoolean() );
    }


}
