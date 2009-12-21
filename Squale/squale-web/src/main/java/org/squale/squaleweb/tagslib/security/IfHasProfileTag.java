package org.squale.squaleweb.tagslib.security;


import javax.servlet.jsp.JspException;


/**
 * This tag renders its body-content if the current user belongs to one of determined profiles fore some application.
 * 
 * @author gfouquet
 */
public class IfHasProfileTag
    extends AbstractProfileCheckerTag
{

    private static final long serialVersionUID = -1965871712088314963L;

    @Override
    public int doStartTag()
        throws JspException
    {
        return isUserHasAnySpecifiedProfile() ? EVAL_BODY_INCLUDE : SKIP_BODY;
    }

}
