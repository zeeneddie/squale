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

public class HasProfileTagTest
    extends AbstractProfileCheckerTagTest
{
    private HasProfileTag testedObject;

    private final JspWriter out = mock( JspWriter.class );

    @Override
    protected AbstractProfileCheckerTag getTestedObject()
    {
        return testedObject;
    }

    @Before
    public void setUpTestedObject()
    {
        testedObject = new HasProfileTag();
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
        aManagerForCurrentApplicationIsLogged();
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
        aManagerForCurrentApplicationIsLogged();
        managerProfileIsRequired();
        testedObject.setVar( "expectedAttribute" );

        // when
        testedObject.doStartTag();

        // then
        verify( pageContext ).setAttribute( eq( "expectedAttribute" ), anyBoolean() );
    }
}
