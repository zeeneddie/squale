package org.squale.squaleweb.tagslib.security;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.IterationTag;

import org.junit.Before;
import org.junit.Test;

public class IfNotHasProfileTagTest
    extends AbstractProfileCheckerTagTest
{
    private IfNotHasProfileTag testedObject;

    @Before
    public void setUpTestedObject()
    {
        testedObject = new IfNotHasProfileTag();
        testedObject.setPageContext( pageContext );
        testedObject.setApplicationId( currentApplicationId );
    }

    @Test
    public void shouldSkipBodyIfUserInProfile()
        throws JspException
    {
        // given
        aManagerForCurrentApplicationIsLogged();
        managerProfileIsRequired();

        // when
        int res = testedObject.doStartTag();

        // then
        assertThat( res, is( IterationTag.SKIP_BODY ) );
    }

    @Test
    public void shouldRenderBodyIfUserNotInProfile()
        throws JspException
    {
        // given
        aReaderForCurrentApplicationIsLogged();
        managerProfileIsRequired();

        // when
        int res = testedObject.doStartTag();

        // then
        assertThat( res, is( IterationTag.EVAL_BODY_INCLUDE ) );
    }

    @Test
    public void shouldRenderBodyIfUserNotInAnyProfile()
        throws JspException
    {
        // given
        aReaderForCurrentApplicationIsLogged();
        managerOrAdminProfileIsRequired();

        // when
        int res = testedObject.doStartTag();

        // then
        assertThat( res, is( IterationTag.EVAL_BODY_INCLUDE ) );
    }

    @Override
    protected AbstractProfileCheckerTag getTestedObject()
    {
        return testedObject;
    }

}
