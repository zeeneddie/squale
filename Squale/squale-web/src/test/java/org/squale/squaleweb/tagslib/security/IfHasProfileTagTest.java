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
