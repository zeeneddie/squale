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
